package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * increment the quantity when '+' is clicked every time by 1
     * */
    public void incrementQuantity(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(this, "Too many cups of coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);

    }

    /*
     * Decrement the quantity when '-' is clicked every time by 1
     * */
    public void decrementQuantity(View view) {
        if (quantity > 1) {
            quantity--;
        } else {
            Toast.makeText(this, "Too few cups of coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    //completes the order with current quantity
    public void submitOrder(View view) {
        //Get the customer name
        EditText nameField = findViewById(R.id.name_edit_text);
        String customerName = nameField.getText().toString();

        //Get the status of checkbox for whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Get the status of checkBox for chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Creating Intent for Email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(customerName,hasWhippedCream,hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*
     *calculates the price of order
     * @param hasWhippedCream tells if the user has added WhippedCream
     * @param hasChocolate tells if the user has added chocolate
     * @return the price of the order */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return price * quantity;
    }

    /*
     * Creates order summary
     * @param hasWhippedCream is whether user wants WhippedCream topping or not
     * @param hasChocolate is whether user wants chocolate topping or not
     * @return text summary
     * */
    private String createOrderSummary(String customerName, boolean hasWhippedCream, boolean hasChocolate) {

        String message = "Name: " + customerName + " \nAdd whipped cream? " + hasWhippedCream;
        message += "\nAdd chocolate? " + hasChocolate;
        message += "\nQuantity: " + quantity + "\nTotal: \u20B9" +
                calculatePrice(hasWhippedCream, hasChocolate) + "\nThank you!";
        return message;
    }

}
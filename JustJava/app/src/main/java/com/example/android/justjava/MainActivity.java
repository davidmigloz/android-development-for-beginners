package com.example.android.justjava;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String subject = getString(R.string.subject, getName());
        String msg = createOrderSummary(
                quantity,
                calculatePrice(quantity, hasCream(), hasChocolate()),
                hasCream(),
                hasChocolate(),
                getName());
        sendMsg(subject, msg);
    }

    /**
     * Open email app to send the order summary.
     */
    private void sendMsg(String subject, String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        // Check if there's some app in the system that can handle it
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(getString(R.string.quantity_value, number));
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(this, R.string.error_more, Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
        } else {
            Toast.makeText(this, R.string.error_less, Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, boolean hasCream, boolean hasChocolate) {
        int price = quantity * 5;
        if (hasCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return price;
    }

    private String createOrderSummary(int quantity, int price, boolean hasWhippedCream,
                                      boolean hasChocolate, String name) {
        String msg;
        msg = getString(R.string.eb_name, name);
        msg += getString(R.string.eb_cream, hasWhippedCream);
        msg += getString(R.string.eb_chocolate, hasChocolate);
        msg += getString(R.string.eb_quantity, quantity);
        msg += getString(R.string.eb_total,  NumberFormat.getCurrencyInstance().format(price));
        msg += getString(R.string.eb_thanks);
        return msg;
    }

    private boolean hasCream() {
        return ((CheckBox) findViewById(R.id.whipped_cream)).isChecked();
    }

    private boolean hasChocolate() {
        return ((CheckBox) findViewById(R.id.chocolate)).isChecked();
    }

    public String getName() {
        return ((EditText) findViewById(R.id.name_field)).getText().toString();
    }
}

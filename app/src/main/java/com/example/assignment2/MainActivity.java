package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //make payment
        Button button = findViewById(R.id.pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        //loading checkout
        Checkout.preload(getApplicationContext());

    }

    //making payment with razorpay
    private void startPayment() {

        final Activity activity = this;
        final Checkout checkout = new Checkout();

        try {
            JSONObject object = new JSONObject();
            object.put("name", "Razorpay Corp");
            object.put("description", "Demoing Payment");
            //You can omit the image option to fetch the image from dashboard
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("currency", "USD");
            object.put("amount", "420");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", "test@razorpay.com");
            jsonObject.put("contact", "123456789");

            object.put("prefill", jsonObject);

            checkout.open(activity, object);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    //razorpay methods
    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}

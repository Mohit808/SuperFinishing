package com.superfinishing.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
//import com.razorpay.Checkout;
//import com.razorpay.PaymentResultListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.superfinishing.R;
import com.superfinishing.ui.accounts.myAddress.MyAddressActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//PaymentResultListener
public class CartActivity extends AppCompatActivity implements PaymentResultListener {

    private static final int REQUEST_CODE_LOCATION = 111;
    int allTotal=0;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    private AdapterCart adapterCart;
    private TextView textViewBillTotal;
    private TextView textViewItems;
    private TextView textViewMrp;
    private Button buttonPayment;
    ConstraintLayout constraintPayment;
    private BroadcastReceiver broadcastReceiver;
    static final String AB = "0123456789";
    static SecureRandom rnd = new SecureRandom();
    private BroadcastReceiver broadcastReceiverAddress;
    private HashMap<String, String> hashAdress;

    String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Checkout.preload(getApplicationContext());

        textViewItems = findViewById(R.id.textView117);
        textViewBillTotal = findViewById(R.id.textView125);
        textViewMrp = findViewById(R.id.textView127);

        RecyclerView recyclerView = findViewById(R.id.recyclerView24c);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        adapterCart = new AdapterCart(CartActivity.this, list);
        recyclerView.setAdapter(adapterCart);


        constraintPayment = findViewById(R.id.cfcf);
        buttonPayment = findViewById(R.id.button19);
        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int payValue = allTotal * 100;

                if (allTotal!=0){
                    System.out.println("addressSelected.... "+hashAdress);
                    if(hashAdress!=null && !hashAdress.isEmpty()){
                        startPayment(String.valueOf(payValue));
                    }else {
                        Intent intent=new Intent(CartActivity.this, MyAddressActivity.class);
                        intent.putExtra("fromAddress","cart");
                        startActivity(intent);
//                        Toast.makeText(CartActivity.this, "Select Address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.imageView39).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        broadcastReceiverAddress=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                hashAdress = (HashMap<String, String>) intent.getSerializableExtra("data");
                System.out.println("Seleeeeeeee "+hashAdress);
            }
        };
        IntentFilter intentFilterAddress=new IntentFilter("addressSelected");
        registerReceiver(broadcastReceiverAddress,intentFilterAddress);

        setUp();

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setUp();
            }
        };
        IntentFilter intentFilter=new IntentFilter("cartUpdate");
        registerReceiver(broadcastReceiver,intentFilter);

        if (ContextCompat.checkSelfPermission(CartActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            // REQUEST_CODE_LOCATION should be defined on your app level
            ActivityCompat.requestPermissions(CartActivity.this, permissions, REQUEST_CODE_LOCATION);
        }

//        Uri uri =
//                new Uri.Builder()
//                        .scheme("upi")
//                        .authority("pay")
//                        .appendQueryParameter("pa", "your-merchant-vpa@xxx")       // virtual ID
//                        .appendQueryParameter("pn", "your-merchant-name")          // name
//                        .appendQueryParameter("mc", "your-merchant-code")          // optional
//                        .appendQueryParameter("tr", "your-transaction-ref-id")     // optional
//                        .appendQueryParameter("tn", "your-transaction-note")       // any note about payment
//                        .appendQueryParameter("am", "your-order-amount")           // amount
//                        .appendQueryParameter("cu", "INR")                         // currency
//                        .appendQueryParameter("url", "your-transaction-url")       // optional
//                        .build();
//
//        String GOOGLE_PAY_PACKAGE_NAME = "net.one97.paytm";
//        int GOOGLE_PAY_REQUEST_CODE = 123;
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
//
//        payUsingUpi("Amit","8858459011@ybl","Pay some money","1");
//        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
//        upiPayIntent.setData(uri);
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//        if(null != chooser.resolveActivity(getPackageManager())) {
//            startActivityForResult(chooser, UPI_PAYMENT);
//        } else {
//            Toast.makeText(CartActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
    }
    private void setUp(){
        list.clear();
        adapterCart.notifyDataSetChanged();
        allTotal=0;
        textViewBillTotal.setText("Rs. 0");
        SharedPreferences sharedPreferences= getSharedPreferences("cart", Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Iterator<String> keys = map.keySet().iterator();

        while (keys.hasNext()){
            Object string = map.get(keys.next());
            HashMap maping = new Gson().fromJson(string.toString(), HashMap.class);
            System.out.println("Maping..... "+maping);
            list.add(maping);
            adapterCart.notifyItemInserted(list.size());
            String Count = maping.get("Count").toString();
            int mrp = Integer.parseInt(maping.get("mrp").toString().replaceAll("[\\D]", ""));
            int total = Integer.valueOf(Count) * mrp;
            allTotal=allTotal+total;
        }

        textViewBillTotal.setText("Rs. "+String.valueOf(allTotal));
        textViewMrp.setText("Rs. "+String.valueOf(allTotal));
        textViewItems.setText(list.size()+" item(s)");

        adapterCart.notifyDataSetChanged();
        if (allTotal==0){
            buttonPayment.setVisibility(View.GONE);
            constraintPayment.setVisibility(View.GONE);

        }

    }

//    void payUsingUpi(  String name,String upiId, String note, String amount) {
//        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
//        Uri uri = Uri.parse("upi://pay").buildUpon()
//                .appendQueryParameter("pa", upiId)
//                .appendQueryParameter("pn", name)
//                //.appendQueryParameter("mc", "")
//                //.appendQueryParameter("tid", "02125412")
//                //.appendQueryParameter("tr", "25584584")
//                .appendQueryParameter("tn", note)
//                .appendQueryParameter("am", amount)
//                .appendQueryParameter("cu", "INR")
//                //.appendQueryParameter("refUrl", "blueapp")
//                .build();
//        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
//        upiPayIntent.setData(uri);
//        // will always show a dialog to user to choose an app
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//        // check if intent resolves
//        if(null != chooser.resolveActivity(getPackageManager())) {
//            startActivityForResult(chooser, 1);
//        } else {
//            Toast.makeText(CartActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        }
//    }
    public void startPayment(String allTotal){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_kxKfOXlBXkofJx");
        checkout.setImage(R.drawable.allcement);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", allTotal);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8858459011");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            System.out.println("Error in starting Razorpay Checkout");
            System.out.println(e);
        }
    }

//    @Override
//    public void onPaymentSuccess(String s) {
//        System.out.println("Succcess.. "+s);
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        System.out.println("Error.. "+s+"  int"+i);
//    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION && grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Location services are required in order to " +
                    "connect to a reader.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
//        switch (requestCode) {
//            case 1:
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.e("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.e("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    //when user simply back without payment
//                    Log.e("UPI", "onActivityResult: " + "Return data is null");
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
    }
//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (isConnectionAvailable(CartActivity.this)) {
//            String str = data.get(0);
//            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(CartActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "payment successfull: "+approvalRefNo);
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(CartActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
//            }
//            else {
//                Toast.makeText(CartActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "failed payment: "+approvalRefNo);
//            }
//        } else {
//            Log.e("UPI", "Internet issue: ");
//            Toast.makeText(CartActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public static boolean isConnectionAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void onPaymentSuccess(String s) {
//        SharedPreferences sharedPreferences= getSharedPreferences("myOrders",Context.MODE_PRIVATE);

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("count",String.valueOf(list.size()));
        hashMap.put("total",String.valueOf(allTotal));
        hashMap.put("orderDate",getDateTime());
        hashMap.put("deliveryTime",getDateTime());
        hashMap.put("status","delivered");
        hashMap.put("orderId","ORDER"+randomString(6));
        hashMap.put("address",hashAdress.get("address"));
        hashMap.put("addressType",hashAdress.get("type"));
        hashMap.put("addressName",hashAdress.get("name"));

        for (int i=0;i<=list.size()-1;i++){
            hashMap.put(String.valueOf(i), String.valueOf(new Gson().toJson(list.get(i))));
        }
        FirebaseDatabase.getInstance().getReference().child("myOrders").push().setValue(hashMap);


//        for (int i=0;i<=list.size()-1;i++){
            SharedPreferences sharedPreferencesCart= getSharedPreferences("cart",Context.MODE_PRIVATE);
            sharedPreferencesCart.edit().clear().commit();
            list.clear();
            adapterCart.notifyDataSetChanged();
            allTotal=0;
            textViewBillTotal.setText("Rs. "+allTotal);
            textViewItems.setText(list.size()+" item(s)");
//        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        System.out.println("errorr "+s);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverAddress);
    }
}
package com.example.reg_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String UPI_ID = "lunkaddivyank@oksbi";
    private static final int UPI_PAYMENT = 0;

    private TextInputLayout text_name, text_contact, text_mail;
    private TextInputEditText name, contact, mail;
    private CheckBox FE, SE, TE, BE;
    private String name1, contact1, mail1, year,branch,current_date;
    private Button register;
    private TextView tV_year, date;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_name = findViewById(R.id.layout_name);
        text_contact = findViewById(R.id.layout_contact);
        text_mail = findViewById(R.id.layout_mail);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        mail = findViewById(R.id.mail);
        FE = findViewById(R.id.FE);
        SE = findViewById(R.id.SE);
        TE = findViewById(R.id.TE);
        BE = findViewById(R.id.BE);
        register = findViewById(R.id.register);
        tV_year = findViewById(R.id.tV_year);
        date=findViewById(R.id.date);

        current_date= DateFormat.getDateInstance().format(new Date());
        date.append(current_date);

        mSpinner=findViewById(R.id.branch);
        ArrayAdapter<String> mAdapter=new ArrayAdapter<>(MainActivity.this,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.Branches));
        mAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinner.setAdapter(mAdapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckbox();
                text_name.setErrorEnabled(false);
                text_contact.setErrorEnabled(false);
                text_mail.setErrorEnabled(false);
                tV_year.setError(null);
                name1 = name.getText().toString().trim();
                contact1 = contact.getText().toString().trim();
                mail1 = mail.getText().toString().trim();
                branch=mSpinner.getSelectedItem().toString();

                final String[] payment = {"Paytm", "UPI", "Cash"};

                if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(contact1) && !TextUtils.isEmpty(mail1) && isEmailValid(mail1) && isContactValid(contact1) && !TextUtils.isEmpty(year)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("To pay:- 1500Rs")
                            .setCancelable(false)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(MainActivity.this);
                                    alertDialogBuilder2.setTitle("Choose a payment method:")
                                            .setItems(payment, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    switch (i) {
                                                        case 0:
                                                            Toast.makeText(MainActivity.this, "Working on it.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 1:
                                                            UPI_transaction();
                                                            break;
                                                        case 2:
                                                            Cash_transaction();
                                                            break;
                                                    }
                                                }
                                            })
                                            .setCancelable(true)
                                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialogInterface) {
                                                    Toast.makeText(MainActivity.this, "Cancelled by user.", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                }
                if (TextUtils.isEmpty(name1)) {
                    text_name.setError("This field cannot be empty.");
                }
                if (TextUtils.isEmpty(contact1)) {
                    text_contact.setError("This field cannot be empty.");
                } else if (!isContactValid(contact1)) {
                    text_contact.setError("Format is incorrect.");
                }
                if (TextUtils.isEmpty(mail1)) {
                    text_mail.setError("This field cannot be empty.");
                } else if (!isEmailValid(mail1)) {
                    text_mail.setError("Format is incorrect.");
                }
                if (TextUtils.isEmpty(year)) {
                    tV_year.setError("Mandatory");
                }
            }
        });

    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isContactValid(String contact) {
        if (contact.length() == 10)
            return true;
        else
            return false;
    }

    void onCheckbox() {
        if(FE.isChecked())
            year = "FE";
        else if(SE.isChecked())
            year = "SE";
        else if(TE.isChecked())
            year = "TE";
        else if(BE.isChecked())
            year = "BE";
        else
            year = null;
    }

    public void onCheckBoxClicked(View view) {
        switch (view.getId()) {
            case R.id.FE:
                SE.setChecked(false);
                TE.setChecked(false);
                BE.setChecked(false);
                break;
            case R.id.SE:
                FE.setChecked(false);
                TE.setChecked(false);
                BE.setChecked(false);
                break;
            case R.id.TE:
                FE.setChecked(false);
                SE.setChecked(false);
                BE.setChecked(false);
                break;
            case R.id.BE:
                FE.setChecked(false);
                SE.setChecked(false);
                TE.setChecked(false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                finish();
                startActivity(getIntent());
                break;
        }
        return true;
    }
    private void UPI_transaction() {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", UPI_ID)
                .appendQueryParameter("pn", "Divyank Lunkad")
                .appendQueryParameter("tn", "Trial")
                .appendQueryParameter("am", "1.00")
                .appendQueryParameter("cu", "INR")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with:");

        if (chooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(MainActivity.this, "No UPI found. Please install one to continue.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Cash_transaction() {
        LinearLayout linearLayout=new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Volunteer Credentials");

        final EditText v_mail = new EditText(MainActivity.this);
        v_mail.setHint("Volunteer's Mail-ID");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        v_mail.setLayoutParams(lp);
        linearLayout.addView(v_mail);

        final EditText v_pass = new EditText(MainActivity.this);
        v_pass.setHint("Password");
        v_pass.setLayoutParams(lp);
    linearLayout.addView(v_pass);
    alertDialog.setView(linearLayout);
        alertDialog.setPositiveButton("Register",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = v_mail.getText().toString();
                        String password = v_pass.getText().toString();
                        Toast.makeText(MainActivity.this, mail+"\n"+password, Toast.LENGTH_SHORT).show();
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String text = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(MainActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null)
                str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(MainActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(MainActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}

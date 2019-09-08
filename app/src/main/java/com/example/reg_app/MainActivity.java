package com.example.reg_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String UPI_ID = "lunkaddivyank@oksbi";

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
        String TransRefId = RefIDGen();
        String TransId = IDGen();
        //Create instance of EasyUpiPayment
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                .setPayeeVpa(UPI_ID)
                .setPayeeName("Divyank Lunkad")
                .setTransactionId(TransId)
                .setTransactionRefId(TransRefId)
                .setDescription("Trial")
                .setAmount("1.00")
                .build();

        easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(TransactionDetails transactionDetails) {
                Log.d("TransactionDetails", transactionDetails.toString());
            }

            @Override
            public void onTransactionSuccess() {
                Toast.makeText(MainActivity.this, "Payment Successful\nUser Registered to PASC", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionSubmitted() {
                Toast.makeText(MainActivity.this, "Pending | Submitted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionFailed() {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionCancelled() {
                Toast.makeText(MainActivity.this, "Cancelled by user", Toast.LENGTH_SHORT).show();
            }
        });
        easyUpiPayment.startPayment();
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

    String RefIDGen() {
        StringBuilder RefId = new StringBuilder();
        final int min = 1000;
        final int max = 9999;
        int i = 0;
        while (i < 3) {

            final int random = new Random().nextInt((max - min) + 1) + min;
            RefId.append(random);
            i++;
        }
        return RefId.toString();
    }

    String IDGen() {
        int i = 0;
        StringBuilder Id = new StringBuilder();
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();
        Random r = new Random();
        Id.append(alphabet.charAt(r.nextInt(N)));
        final int min = 1000;
        final int max = 9999;
        while (i < 5) {
            final int random = new Random().nextInt((max - min) + 1) + min;
            Id.append(random);
            i++;
        }
        final int min1 = 10;
        final int max1 = 99;
        final int random1 = new Random().nextInt((max1 - min1) + 1) + min1;
        Id.append(random1);
        return Id.toString();
    }
}

package com.example.prathamesh1.lavanya_makeup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.prathamesh1.lavanya_makeup.MainActivity.mainActivity;

public class Update_Page extends AppCompatActivity implements View.OnKeyListener {

    Button u_but_Update;
    EditText u_et_birthDate, u_et_annDate, u_et_name, u_et_phone, u_et_email, u_et_telNo;
    DatePickerDialog u_dpd1, u_dpd2;
    InputMethodManager u_imm;
    String old_mobileNo,name, mobileNo, emailId, dob, doa, lln;
    String old_name, old_mobNo, old_emailId, old_dob, old_doa, old_lln;
    Context ctx = this;
    String DB_birthDate,DB_AnnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_update_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar c = Calendar.getInstance();
        final int yer = c.get(Calendar.YEAR);
        final int mon = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        u_dpd1 = new DatePickerDialog(this, birthListener, yer, mon, day);
        u_dpd1.getDatePicker().setMaxDate((c.getTimeInMillis()));
        u_dpd2 = new DatePickerDialog(this, annListener, yer, mon, day);
        u_dpd2.getDatePicker().setMaxDate((c.getTimeInMillis()));

        u_et_name = (EditText) findViewById(R.id.U_TF_Name);
        u_et_phone = (EditText) findViewById(R.id.U_TF_Mobile);
        u_et_email = (EditText) findViewById(R.id.U_TF_Email);
        u_et_birthDate = (EditText) findViewById(R.id.U_DOB_field);
        u_et_annDate = (EditText) findViewById(R.id.U_DOA_field);
        u_et_telNo = (EditText) findViewById(R.id.U_TF_TelNo);
        u_but_Update = (Button) findViewById(R.id.U_But_update);

        u_et_name.setFocusable(false);
        u_et_phone.setFocusable(false);
        u_et_email.setFocusable(false);
        u_et_birthDate.setFocusable(false);
        u_et_annDate.setFocusable(false);
        u_et_telNo.setFocusable(false);
        u_et_phone.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        u_et_phone.setTransformationMethod(new NumberKeyBoardTransformationMethod());
        u_et_telNo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        u_et_telNo.setTransformationMethod(new NumberKeyBoardTransformationMethod());

        u_et_name.setOnKeyListener(this);
        u_et_email.setOnKeyListener(this);

        Bundle bundle = getIntent().getBundleExtra("Values");
        old_name = bundle.getString("name");
        old_mobNo = bundle.getString("mobile");
        old_dob = bundle.getString("DOB");
        old_emailId = bundle.getString("Email");
        old_doa = bundle.getString("DOA");
        old_lln = bundle.getString("lln");

        u_et_name.setText(old_name);
        u_et_phone.setText(old_mobNo);
        u_et_birthDate.setText(old_dob);
        u_et_email.setText(old_emailId);
        u_et_annDate.setText(old_doa);
        u_et_telNo.setText(old_lln);

        //This old_mobNo variable is to pass to the database
        old_mobileNo = bundle.getString("mobile");

        //Name field validation---------------------------------------------------------------------
        u_et_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(u_et_name);
            }
        });
        u_et_name.addTextChangedListener(new TextValidator(u_et_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 50)
                    u_et_name.setError("Length too short or too long for name.");
                else
                    u_et_name.setError(null);
            }
        });
        //------------------------------------------------------------------------------------------

        //Mobile no. validation---------------------------------------------------------------------
        u_et_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(u_et_phone);
            }
        });
        u_et_phone.addTextChangedListener(new TextValidator(u_et_phone) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 10) {
                    u_et_phone.setError("Please enter correct mobile no.");
                } else {
                    u_et_phone.setError(null);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        //Email-Id validation-----------------------------------------------------------------------
        u_et_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(u_et_email);
            }
        });
        u_et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    String text = u_et_email.getText().toString();
                    if (text.length() == 0 || text.isEmpty())
                        u_et_email.setError(null);
                    else
                        emailValidation(text,u_et_email);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        //Birthday field Validation-----------------------------------------------------------------
        u_et_birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusOff(u_et_name, u_et_phone, u_et_email, u_et_telNo);
                u_dpd1.show();
            }
        });
        u_et_birthDate.addTextChangedListener(new TextValidator(u_et_birthDate) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() != 0)
                    u_et_birthDate.setError(null);
            }
        });
        //------------------------------------------------------------------------------------------

        //Anniversary field validation--------------------------------------------------------------
        u_et_annDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusOff(u_et_name, u_et_phone, u_et_email, u_et_telNo);
                u_dpd2.show();
            }
        });
        //------------------------------------------------------------------------------------------

        //LandLine No. validation-------------------------------------------------------------------
        u_et_telNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(u_et_telNo);
            }
        });
        u_et_telNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    u_et_telNo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            final int length = u_et_telNo.getText().toString().length();
                            if (length > 8) {
                                u_et_telNo.setError("Telephone no. cannot exceed 8 digits");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                }
            }
        });

        //------------------------------------------------------------------------------------------

        u_but_Update.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    //database
                    emailId = doa = lln = null;
                    name = u_et_name.getText().toString();
                    mobileNo = u_et_phone.getText().toString();
                    emailId = u_et_email.getText().toString();
                    dob = u_et_birthDate.getText().toString();
                    doa = u_et_annDate.getText().toString();
                    lln = u_et_telNo.getText().toString();
                    BackgroundTask backgroundTask = new BackgroundTask(ctx);
                    backgroundTask.execute("update_details", mobileNo,name ,emailId, dob, doa,lln,old_mobileNo);
                    mainActivity.finish();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Toast.makeText(Update_Page.this, "Customer details updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void emailValidation(String email,EditText et_View) {
        //-------------------------------------------------Email Validation-------------------------------------------
        String emailregex = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@((?:[A-Z]{4,}|yahoo|ymail|gmail|rediff))+(\\.(?:[A-Z]{2,}|co)*(\\.(?:[A-Z]{2,}|us|in|ch|jp|uk)))+$";
        String emailregex2 = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@(([A-Z]{4,}|yahoo|ymail|gmail|rediff))+(\\.(?:[A-Z]{1,}|com))+$";
        Boolean result = email.matches(emailregex);
        Boolean result2 = email.matches(emailregex2);

        if (result2) {
            et_View.setError(null);
        } else if (result) {
            et_View.setError(null);
        } else {
            et_View.setError("Please enter a correct email address.");
        }

    }

    public boolean isValidate() {
        final boolean[] isValid = {true};
        String name, mobile, email, birthDate;
        final int mobLen, telNoLen;

        name = u_et_name.getText().toString();
        mobile = u_et_phone.getText().toString();
        email = u_et_email.getText().toString();
        birthDate = u_et_birthDate.getText().toString();

        mobLen = mobile.length();
        telNoLen = u_et_telNo.getText().toString().length();

        if (name.length() != 0 && name.length() < 2) {
            u_et_name.setError("Name cannot be single character long.");
            isValid[0] = false;
        }

        if (telNoLen != 0 && telNoLen < 8) {
            u_et_telNo.setError("Please enter correct telephone no.");
            isValid[0] = false;
        }

        if (mobLen != 0 && mobLen < 10) {
            u_et_phone.setError("Please enter correct telephone no.");
            isValid[0] = false;
        }

        if(!email.isEmpty()){
            emailValidation(email,u_et_email);
            isValid[0] = false;
        }

        if (name.isEmpty() ||mobile.isEmpty() || birthDate.isEmpty()) {
            if(name.isEmpty())
                setCustomError(u_et_name);
            if (mobile.isEmpty())
                setCustomError(u_et_phone);
            if (birthDate.isEmpty())
                setCustomError(u_et_birthDate);
            isValid[0] = false;
        }else if ((u_et_name.getError() == null) && (u_et_phone.getError() == null) && (u_et_email.getError() == null)
                && (u_et_birthDate.getError() == null) && (u_et_telNo.getError() == null)) {

            isValid[0] = true;
        }


        return isValid[0];
    }

    public void grabFocus(TextView tv) {
        tv.setFocusable(true);
        tv.setFocusableInTouchMode(true);
        tv.requestFocus();
        u_imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        u_imm.showSoftInput(tv, InputMethodManager.SHOW_IMPLICIT);
    }

    /*Following method takes away the focus from fields except DOB_field and DOA_field.
    * created for DOB_field and DOA_field*/
    public void setFocusOff(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setFocusable(false);
        tv2.setFocusable(false);
        tv3.setFocusable(false);
        tv4.setFocusable(false);
    }

    public void setCustomError(EditText et) {
        et.setError("Do not leave this field empty.");
    }

    private DatePickerDialog.OnDateSetListener birthListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day + "/" + (month + 1) + "/" + year;
            u_et_birthDate.setText(date);
            DB_birthDate = day + "/" + (month + 1) + "/" + year;
        }
    };

    private DatePickerDialog.OnDateSetListener annListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day + "/" + checkMonth(month + 1) + "/" + year;
            u_et_annDate.setText(date);
            DB_AnnDate = day + "/" + checkMonth(month + 1) + "/" + year;
        }
    };


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //This is to close keyboard on ENTER button
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER ){
            u_imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    public String checkMonth(int month){
        return month<=9?"0"+month:String.valueOf(month);
    }

}

package com.example.prathamesh1.lavanya_makeup;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by HI on 5/2/2017.
 */
//######################################################This is the one#############################
public class Add_Page extends AppCompatActivity implements View.OnKeyListener {

    Button but_Clear, but_Submit;
    EditText et_birthDate, et_annDate, et_name, et_phone, et_email, et_telNo;
    DatePickerDialog dpd1, dpd2;
    InputMethodManager imm;
    String DB_birthDate,DB_AnnDate;
    String name, mobileNo, emailId, dob, doa, lln;
    Context ctx = this;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_add_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Calendar c = Calendar.getInstance();
        final int yer = c.get(Calendar.YEAR);
        final int mon = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        dpd1 = new DatePickerDialog(this, birthListener, yer, mon, day);
        dpd1.getDatePicker().setMaxDate((c.getTimeInMillis()));
        dpd2 = new DatePickerDialog(this, annListener, yer, mon, day);
        dpd2.getDatePicker().setMaxDate((c.getTimeInMillis()));

        et_name = (EditText) findViewById(R.id.TF_Name);
        et_phone = (EditText) findViewById(R.id.TF_Mobile);
        et_email = (EditText) findViewById(R.id.TF_Email);
        et_birthDate = (EditText) findViewById(R.id.DOB_field);
        et_annDate = (EditText) findViewById(R.id.DOA_field);
        et_telNo = (EditText) findViewById(R.id.TF_TelNo);
        but_Clear = (Button) findViewById(R.id.But_clear);
        but_Submit = (Button) findViewById(R.id.But_submit);

        et_name.setFocusable(false);
        et_phone.setFocusable(false);
        et_email.setFocusable(false);
        et_birthDate.setFocusable(false);
        et_annDate.setFocusable(false);
        et_telNo.setFocusable(false);
        et_phone.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        et_phone.setTransformationMethod(new NumberKeyBoardTransformationMethod());
        et_telNo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        et_telNo.setTransformationMethod(new NumberKeyBoardTransformationMethod());

        et_name.setOnKeyListener(this);
        et_phone.setOnKeyListener(this);
        et_email.setOnKeyListener(this);

        //Name field validation---------------------------------------------------------------------
        et_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(et_name);
            }
        });
        et_name.addTextChangedListener(new TextValidator(et_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 50)
                    et_name.setError("Length too short or too long for name.");
                else
                    et_name.setError(null);
            }
        });
        //------------------------------------------------------------------------------------------

        //Mobile no. validation---------------------------------------------------------------------
        et_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(et_phone);
            }
        });
        et_phone.addTextChangedListener(new TextValidator(et_phone) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() > 10) {
                    et_phone.setError("Please enter correct mobile no.");
                } else {
                    et_phone.setError(null);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        //Email-Id validation-----------------------------------------------------------------------
        et_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(et_email);
            }
        });
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = et_email.getText().toString();
                    if (text.length() == 0 || text.isEmpty())
                        et_email.setError(null);
                    else
                        emailValidation(text,et_email);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        //Birthday field Validation-----------------------------------------------------------------
        et_birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusOff(et_name, et_phone, et_email, et_telNo);
                dpd1.show();
            }
        });
        et_birthDate.addTextChangedListener(new TextValidator(et_birthDate) {
            @Override
            public void validate(TextView textView, String text) {
                if(text.length() != 0)
                    et_birthDate.setError(null);
            }
        });
        //------------------------------------------------------------------------------------------

        //Anniversary field validation--------------------------------------------------------------
        et_annDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusOff(et_name, et_phone, et_email, et_telNo);
                dpd2.show();
            }
        });
        //------------------------------------------------------------------------------------------

        //LandLine No. validation-------------------------------------------------------------------
        et_telNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabFocus(et_telNo);
            }
        });
        et_telNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_telNo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            final int length = et_telNo.getText().toString().length();
                            if (length > 8) {
                                et_telNo.setError("Telephone no. cannot exceed 8 digits");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                }
            }
        });

        //------------------------------------------------------------------------------------------

        but_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),Add_Page.class));
            }
        });

        but_Submit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    //database
                    name = et_name.getText().toString();
                    mobileNo = et_phone.getText().toString();
                    emailId = et_email.getText().toString();
                    dob = et_birthDate.getText().toString();
                    doa = et_annDate.getText().toString();
                    lln = et_telNo.getText().toString();
                    BackgroundTask backgroundTask = new BackgroundTask(ctx);
                    backgroundTask.execute("add_details", mobileNo,name ,emailId, dob, doa,lln);
                    mainActivity.finish();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Toast.makeText(Add_Page.this, "Customer details added", Toast.LENGTH_SHORT).show();
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

        name = et_name.getText().toString();
        mobile = et_phone.getText().toString();
        email = et_email.getText().toString();
        birthDate = et_birthDate.getText().toString();

        mobLen = mobile.length();
        telNoLen = et_telNo.getText().toString().length();

        if (name.length() != 0 && name.length() < 2) {
            et_name.setError("Name cannot be single character long.");
            isValid[0] = false;
        }

        if (telNoLen != 0 && telNoLen < 8) {
            et_telNo.setError("Please enter correct telephone no.");
            isValid[0] = false;
        }

        if (mobLen != 0 && mobLen < 10) {
            et_phone.setError("Please enter correct telephone no.");
            isValid[0] = false;
        }

        if(!email.isEmpty()){
            emailValidation(email,et_email);
            isValid[0] = false;
        }

        if (name.isEmpty() ||mobile.isEmpty() || birthDate.isEmpty()) {
            if(name.isEmpty())
                setCustomError(et_name);
            if (mobile.isEmpty())
                setCustomError(et_phone);
            if (birthDate.isEmpty())
                    setCustomError(et_birthDate);
            isValid[0] = false;
        }else if ((et_name.getError() == null) && (et_phone.getError() == null) && (et_email.getError() == null)
                && (et_birthDate.getError() == null) && (et_telNo.getError() == null)) {

            isValid[0] = true;
        }


        return isValid[0];
    }

    public void grabFocus(TextView tv) {
        tv.setFocusable(true);
        tv.setFocusableInTouchMode(true);
        tv.requestFocus();
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tv, InputMethodManager.SHOW_IMPLICIT);
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
            String date = day + "/" + checkMonth(month+1) + "/" + year;
            et_birthDate.setText(date);
            DB_birthDate = day + "/" + checkMonth(month+1) + "/" + year;
        }
    };

    private DatePickerDialog.OnDateSetListener annListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day + "/" + checkMonth(month+1) + "/" + year;
            et_annDate.setText(date);
            DB_AnnDate = day + "/" + checkMonth(month+1) + "/" + year;
        }
    };


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //This is to close keyboard on ENTER button
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    public String checkMonth(int month){
        return month<=9?"0"+month:String.valueOf(month);
    }
}

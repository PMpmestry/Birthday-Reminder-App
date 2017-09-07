package com.example.prathamesh1.lavanya_makeup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.jar.Manifest;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email extends AppCompatActivity implements OnClickListener {

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg;
    String rec, subject, textMessage;
    Calendar c;
    String todayTime;
    StringBuilder email_Id = new StringBuilder();
    StringBuilder email_body = new StringBuilder();
    StringBuilder email_pass = new StringBuilder();
    StringBuilder email_subj = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        c = Calendar.getInstance();
        Date curTime = c.getTime();
        DateFormat df = new SimpleDateFormat("HH:mm a");
        todayTime = df.format(curTime);
        context = this;

        Button send = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        sub = (EditText) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);

        reciep.setEnabled(false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        email_Id.append(sp.getString("email","No Email Provided"));
        email_body.append(sp.getString("email_body","No Content"));
        msg.setText(email_body.toString());
        email_pass.append(sp.getString("email_pass","No Password Set"));
        email_subj.append(sp.getString("email_sub","No Subject"));
        sub.setText(email_subj.toString());

        final String receEmail = getIntent().getStringExtra("email");
        if(receEmail.isEmpty() || email_Id.toString().isEmpty())
            send.setEnabled(false);
        else {
            send.setEnabled(true);
        }
        if(!receEmail.isEmpty())
            reciep.setText(receEmail);

        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        rec = reciep.getText().toString();
        subject = sub.getText().toString();
        textMessage = msg.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email_Id.toString(), email_pass.toString());
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email_Id.toString()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");

                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

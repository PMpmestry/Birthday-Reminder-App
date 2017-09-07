package com.example.prathamesh1.lavanya_makeup;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HI on 5/10/2017.
 */

public class BackgroundTask extends AsyncTask<String, Customer, String> {

    Context context;
    CustomerAdapter customerAdapter;
    Activity activity;
    ListView listView;
    SimpleDateFormat sdf0 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
    Date birthObj,annObj;
    String DB_birth,DB_ann;
    JobSchedulerService jss = new JobSchedulerService();

    BackgroundTask(Context context){
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String old_mobileNo, mobileNo, name, emailId, dob, doa, lln;

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        if (method.equals("add_details")){
            mobileNo = params[1];
            name = params[2];
            emailId = params[3];
            dob = params[4];
            doa = params[5];
            lln = params[6];
            try {
                birthObj = sdf0.parse(dob);
                DB_birth = sdf1.format(birthObj);

                if(doa!=null) {
                    annObj = sdf0.parse(doa);
                    DB_ann = sdf1.format(annObj);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dataBaseHelper.openDB();
            long status = dataBaseHelper.addInformation(mobileNo, name, emailId,DB_birth,DB_ann, lln);
            dataBaseHelper.closeDB();
            if(status==-1){
                return "Customer Already Exist";
            }else {
                return "Customer Details Added";
            }
        }
        else if(method.equals("get_details")){
            listView = (ListView) activity.findViewById(R.id.display_listview);
            dataBaseHelper.openDB();
            Cursor cursor = dataBaseHelper.getInformation(null);
            customerAdapter = new CustomerAdapter(context,R.layout.item_listview);
            while (cursor.moveToNext()){
                mobileNo = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_MOBILENO));
                name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_NAME));
                emailId = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_EMAILID));
                dob = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DOB));
                doa = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DOA));
                lln= cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_LLN));
                try {
                    birthObj = sdf1.parse(dob);
                    DB_birth = sdf0.format(birthObj);

                    if(doa!=null) {
                        annObj = sdf1.parse(doa);
                        DB_ann = sdf0.format(annObj);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Customer customer = new Customer(mobileNo,name,emailId,DB_birth,DB_ann,lln);
                publishProgress(customer);
            }
            dataBaseHelper.closeDB();
            return "get_details";
        }else if(method.equals("update_details")){
            mobileNo = params[1];
            name = params[2];
            emailId = params[3];
            dob = params[4];
            doa = params[5];
            lln = params[6];
            old_mobileNo = params[7];
            try {
                birthObj = sdf0.parse(dob);
                DB_birth = sdf1.format(birthObj);
                if(doa!=null) {
                    annObj = sdf0.parse(doa);
                    DB_ann = sdf1.format(annObj);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dataBaseHelper.openDB();
            int status = dataBaseHelper.updateInformation(old_mobileNo,name,mobileNo,emailId,DB_birth,DB_ann,lln);
            dataBaseHelper.closeDB();
            if(status==1)
                return "Customer Details Updated";
            else
                return "Customer Already Exist";
        } else if(method.equals("getBirthdays")){
            dataBaseHelper.openDB();
            Cursor cursor = dataBaseHelper.getBirthdays();
            if (cursor != null)
                return "getBirthdays";
            else
                return null;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Customer... values) {
        customerAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("get_details")){
            listView.setAdapter(customerAdapter);
        } else if (result.equals("getBirthdays")) {
            jss.status = true;
        }
        else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}

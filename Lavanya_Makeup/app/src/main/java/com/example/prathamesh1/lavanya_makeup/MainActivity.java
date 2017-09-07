package com.example.prathamesh1.lavanya_makeup;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Locale;

/**
 * Created by Prathamesh 1 on 23-05-2017.
 */

public class MainActivity extends AppCompatActivity {

    public static Activity mainActivity;
    Context ctx = this;
    ListView listView;
    CustomerAdapter customerAdapter;
    Intent updateIntent,smsIntent,emailIntent;
    FloatingActionButton addFAB;
    //    TextView errorView;
    private boolean semaphore = true, isFolded = true;
    FloatingActionButton fab2, miniFab1, miniFab2, miniFab3;
    Animation miniFabAnim;
    private static final int MY_JOB_ID = 1;
    private JobScheduler js;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        js = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduleJob();

        mainActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addFAB = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        listView = (ListView) findViewById(R.id.display_listview);
        customerAdapter = new CustomerAdapter(this, R.layout.item_listview);

//        errorView.setVisibility(View.INVISIBLE);
        BackgroundTask bgt = new BackgroundTask(this);
        bgt.execute("get_details");


        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPageIntent = new Intent(getApplicationContext(), Add_Page.class);
                startActivity(addPageIntent);
            }
        });

        miniFab1 = (FloatingActionButton) findViewById(R.id.editFab);
        miniFab2 = (FloatingActionButton) findViewById(R.id.smsFab);
        miniFab3 = (FloatingActionButton) findViewById(R.id.emailFab);

        setVisible(miniFab1, miniFab2, miniFab3, View.INVISIBLE);
        fab2.setVisibility(View.INVISIBLE);

        setEnable(miniFab1, miniFab2, miniFab3, false);
        fab2.setEnabled(false);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFolded) {
                    miniFabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab1_anim);
                    miniFab1.startAnimation(miniFabAnim);
                    miniFabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab2_anim);
                    miniFab2.startAnimation(miniFabAnim);
                    miniFabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab3_anim);
                    miniFab3.startAnimation(miniFabAnim);

                    setVisible(miniFab1, miniFab2, miniFab3, View.VISIBLE);

                    setEnable(miniFab1, miniFab2, miniFab3, true);
                    isFolded = false;
                } else {
                    menu_FAB_Fold();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!semaphore) {
                    if(isFolded){
                    }else {
                        menu_FAB_Fold();
                    }
                    setVisible(miniFab1, miniFab2, miniFab3, View.INVISIBLE);
                    setEnable(miniFab1, miniFab2, miniFab3, false);
                    fab2.setEnabled(false);
                    Animation au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_down);
                    fab2.startAnimation(au);
                    fab2.setVisibility(View.INVISIBLE);
                    au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_up);
                    addFAB.setVisibility(View.VISIBLE);
                    addFAB.setEnabled(true);
                    addFAB.startAnimation(au);
                    semaphore = true;
                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (semaphore) {
                    addFAB.setEnabled(false);
                    Animation au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_down);
                    addFAB.startAnimation(au);
                    addFAB.setVisibility(View.INVISIBLE);
                    au = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_up);
                    fab2.setVisibility(View.VISIBLE);
                    fab2.setEnabled(true);
                    fab2.startAnimation(au);
                    semaphore = false;
                }
                TextView tv_name,tv_mobile,tv_email,tv_DOB,tv_DOA,tv_lln;
                tv_name = (TextView) view .findViewById(R.id.TV_Name);
                tv_mobile = (TextView) view.findViewById(R.id.TV_MobileNo);
                tv_email = (TextView) view.findViewById(R.id.TV_EmailId);
                tv_DOB = (TextView) view .findViewById(R.id.TV_DOB);
                tv_DOA = (TextView) view .findViewById(R.id.TV_DOA);
                tv_lln = (TextView) view .findViewById(R.id.TV_Lln);

                String name,mob,dob,eid,doa,lln;
                name= tv_name.getText().toString();
                mob = tv_mobile.getText().toString();
                dob = tv_DOB.getText().toString();
                eid = tv_email.getText().toString();
                doa = tv_DOA.getText().toString();
                lln = tv_lln.getText().toString();

                updateIntent = new Intent(getApplicationContext(),Update_Page.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("mobile",mob);
                bundle.putString("DOB",dob);
                bundle.putString("Email",eid);
                bundle.putString("DOA",doa);
                bundle.putString("lln",lln);
                updateIntent.putExtra("Values",bundle);

                smsIntent = new Intent(getApplicationContext(), Message_Class.class);
                smsIntent.putExtra("mobile", mob);

                emailIntent = new Intent(getApplicationContext(), Email.class);
                emailIntent.putExtra("email",eid);
                return true;
            }
        });

        miniFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(updateIntent);
            }
        });

        miniFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(smsIntent);
            }
        });

        miniFab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(emailIntent);
            }
        });

    }

    public void menu_FAB_Fold() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab1_down);
        miniFab1.setEnabled(false);
        miniFab1.startAnimation(anim);
        miniFab1.setVisibility(View.INVISIBLE);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab2_down);
        miniFab2.setEnabled(false);
        miniFab2.startAnimation(anim);
        miniFab2.setVisibility(View.INVISIBLE);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mini_fab3_down);
        miniFab3.setEnabled(false);
        miniFab3.startAnimation(anim);
        miniFab3.setVisibility(View.INVISIBLE);
        isFolded = true;
    }

    public void setVisible(FloatingActionButton f1, FloatingActionButton f2, FloatingActionButton f3, int visibility) {
        f1.setVisibility(visibility);
        f2.setVisibility(visibility);
        f3.setVisibility(visibility);
    }

    public void setEnable(FloatingActionButton f1, FloatingActionButton f2, FloatingActionButton f3, boolean value) {
        f1.setEnabled(value);
        f2.setEnabled(value);
        f3.setEnabled(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    getCustomer(newText);
                } else {
                    getCustomer(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent setting = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(setting);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getCustomer(String searchText) {
        String mobileNo, name, emailId, dob, doa, lln;
        Cursor cursor;
        searchText = searchText.toLowerCase(Locale.getDefault());
        DataBaseHelper dbh = new DataBaseHelper(ctx);
        dbh.openDB();
        customerAdapter.removeAll();

        if (searchText.length() == 0) {
            cursor = dbh.getInformation(null);
        } else if(TextUtils.isDigitsOnly(searchText)) {
            cursor = dbh.getInfo(searchText);
        } else {
            cursor = dbh.getInformation(searchText);
        }

        if (cursor == null) {
//            errorView.setVisibility(View.VISIBLE);
//            listView.setEmptyView(findViewById(android.R.id.empty));
        } else {
            while (cursor.moveToNext()) {
                mobileNo = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_MOBILENO));
                name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_NAME));
                emailId = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_EMAILID));
                dob = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DOB));
                doa = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_DOA));
                lln = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_LLN));
                Customer cust = new Customer(mobileNo, name, emailId, dob, doa, lln);
                customerAdapter.add(cust);
            }
        }
        dbh.closeDB();
        listView.setAdapter(customerAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        ComponentName serviceName = new ComponentName(this, JobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(MY_JOB_ID, serviceName)
                .setPeriodic(18000000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        js.schedule(builder.build());
    }
}

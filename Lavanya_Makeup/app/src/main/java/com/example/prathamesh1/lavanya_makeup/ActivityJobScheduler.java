package com.example.prathamesh1.lavanya_makeup;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Prathamesh 1 on 07-06-2017.
 */

public class ActivityJobScheduler extends AppCompatActivity {

    private static final int MY_JOB_ID = 1;
    private JobScheduler js;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        js = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        Button start = (Button) findViewById(R.id.start);
        scheduleJob();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        ComponentName serviceName = new ComponentName(this, JobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(MY_JOB_ID, serviceName)
                .setPeriodic(2000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        int test = js.schedule(builder.build());
    }
}

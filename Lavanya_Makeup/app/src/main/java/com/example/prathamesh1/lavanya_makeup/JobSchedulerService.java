package com.example.prathamesh1.lavanya_makeup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Prathamesh 1 on 07-06-2017.
 */

public class JobSchedulerService extends JobService {

    private JobParameters p;
    Context context = this;
    NotificationManager nm;
    Notification notification;
    DataBaseHelper dataBaseHelper;
    public boolean status = false;
    NotificationCompat.Builder builder;
    PendingIntent pd;
    Uri notifyTone;
    public Cursor c;

    @Override
    public void onCreate() {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Intent birthdayIntent = new Intent(context, Birthday.class);
        birthdayIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        pd = PendingIntent.getActivity(context, 100, birthdayIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notifyTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        pd = PendingIntent.getActivity(context, 100, birthdayIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(context);
        notification = builder.setContentText("Birthdays")
                .setTicker("There's a list of people who have birthday next week")
                .setContentIntent(pd)
                .setSound(notifyTone)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Birthday List")
                .setAutoCancel(true).build();
    }
    @Override
    public boolean onStartJob(JobParameters params) {
        p = params;
        dataBaseHelper.openDB();
        c =  dataBaseHelper.getBirthdays();
        if (c.getCount() > 0) {
            c.moveToFirst();
            nm.notify(100, notification);
        }
        dataBaseHelper.closeDB();
        jobFinished(p, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}

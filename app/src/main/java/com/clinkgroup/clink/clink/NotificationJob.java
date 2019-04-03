package com.clinkgroup.clink.clink;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Class for Managing the notification Job
 */
public class NotificationJob extends DailyJob {

    public static final String TAG = "Weekly Job";

    @Override
    @NonNull
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        if(isDay(1)) {
            // run your job here
            // notificationId is a unique int for each notification that you must define
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), MainActivity.CHANNEL_ID
            )
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getContext().getResources().getText(R.string.notification_title))
                    .setContentText(getContext().getResources().getText(R.string.notification_message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            int notificationId = 1;
            notificationManager.notify(notificationId, mBuilder.build());
        }
        return DailyJobResult.SUCCESS;
    }

    /**
     *
     * @param _day
     *  (0 = Saturday, 1 = Sunday, 2 = Monday, ..., 6 = Friday)
     * @return
     */
    public boolean isDay(int _day){
        // create a Pacific Standard Time time zone
        SimpleTimeZone est = new SimpleTimeZone(-5 * 60 * 60 * 1000, "EST");

        // set up rules for Daylight Saving Time
        est.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        est.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        // create a GregorianCalendar with the Pacific Daylight time zone
        // and the current date and time
        Calendar calendar = new GregorianCalendar(est);
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        return _day == calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

    }

    public static void scheduleJob() {
        DailyJob.schedule(new JobRequest.Builder(TAG), TimeUnit.HOURS.toMillis(14), TimeUnit.HOURS.toMillis(16));

    }
}

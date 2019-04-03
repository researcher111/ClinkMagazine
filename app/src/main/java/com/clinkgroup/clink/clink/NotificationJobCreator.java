package com.clinkgroup.clink.clink;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class NotificationJobCreator implements JobCreator {

    @Override

    public Job create(@NonNull String tag) {
        switch (tag) {
            case NotificationJob.TAG:
                return new NotificationJob();
            default:
                return null;
        }
    }
}

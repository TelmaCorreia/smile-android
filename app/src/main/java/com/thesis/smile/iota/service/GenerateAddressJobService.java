package com.thesis.smile.iota.service;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.iota.IotaTaskManager;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GenerateAddressJobService extends JobService {

    private static final String TAG = "JobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), PaymentService.class);
        getApplicationContext().startService(service);
        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}

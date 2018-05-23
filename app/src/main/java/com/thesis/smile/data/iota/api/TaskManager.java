package com.thesis.smile.data.iota.api;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.thesis.smile.BuildConfig;
import com.thesis.smile.data.iota.api.requests.ApiRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskManager {
    private static final HashMap<String, AsyncTask> runningTasks = new HashMap<>();
    private final Context context;

    public TaskManager(Context context) {
        this.context = context;
    }

    private static synchronized void addTask(RequestTask requestTask, ApiRequest ir) {
        String tag = ir.getClass().getName();
        if (!runningTasks.containsKey(tag)) {
            runningTasks.put(tag, requestTask);
            if (BuildConfig.DEBUG)
                Log.i("Added Task ", tag);
            requestTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ir);
        }
    }

    public static synchronized void removeTask(String tag) {
        if (runningTasks.containsKey(tag)) {
            if (BuildConfig.DEBUG)
                Log.i("Removed Task ", tag);
            runningTasks.remove(tag);
        }
    }

    public static void stopAndDestroyAllTasks(Context context) {
        Iterator<Map.Entry<String, AsyncTask>> it = runningTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, AsyncTask> entry = it.next();
            try {
                entry.getValue().cancel(true);
            } catch (IllegalStateException e) {
                e.getStackTrace();
            }
            it.remove();
        }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    public void startNewRequestTask(ApiRequest ir) {
        RequestTask rt = new RequestTask(context);
        TaskManager.addTask(rt, ir);
    }
}

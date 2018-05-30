package com.thesis.smile.iota;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.Constants;
import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.responses.ApiResponse;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

class RequestTask extends AsyncTask<ApiRequest, String, ApiResponse> {

    private WeakReference<Context> context;
    private EventBus bus;
    private long             start = 0;
    private SimpleDateFormat sdf   = new SimpleDateFormat("HH:mm:ss.sss");
    private String           tag   = "";


    public RequestTask(Context context) {
        this.context = new WeakReference<>(context);
        this.bus = EventBus.getDefault();
    }

    @Override
    protected ApiResponse doInBackground(ApiRequest... params) {

        Context context = this.context.get();

        if (context != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String protocol = prefs.getString(Constants.PREFERENCE_NODE_PROTOCOL, Constants.PREFERENCE_NODE_DEFAULT_PROTOCOL);
            String host = prefs.getString(Constants.PREFERENCE_NODE_IP, Constants.PREFERENCE_NODE_DEFAULT_IP);
            int port = Integer.parseInt(prefs.getString(Constants.PREFERENCE_NODE_PORT, Constants.PREFERENCE_NODE_DEFAULT_PORT));

            if (BuildConfig.DEBUG) {
                start = System.currentTimeMillis();
                Log.i("ApiRequest", protocol +"://"+ host +":"+ port + " at:" + sdf.format(new Date(start)));
            }

            ApiRequest apiRequest = params[0];
            tag = apiRequest.getClass().getName();

            ApiProvider apiProvider = new IotaApiProvider(protocol, host, port, context);

            return apiProvider.processRequest(apiRequest);

        }

        IotaTaskManager.removeTask(tag);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ApiResponse result) {
        if (this.isCancelled()) return;
        if (BuildConfig.DEBUG) {
            if (result != null)
                Log.i("ApiResponse", new Gson().toJson(result));
            Log.i("duration", (System.currentTimeMillis() - start) + " ms");
        }

        if (result != null) {
            bus.post(result);
        } else {
            bus = null;
        }

        IotaTaskManager.removeTask(tag);
    }
}

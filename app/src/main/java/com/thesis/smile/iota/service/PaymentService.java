package com.thesis.smile.iota.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.thesis.smile.Constants;
import com.thesis.smile.SmileApp;
import com.thesis.smile.data.preferences.SharedPrefs;
import com.thesis.smile.data.remote.services.TransactionsService;
import com.thesis.smile.domain.managers.IotaManager;
import com.thesis.smile.domain.managers.TransactionsManager;
import com.thesis.smile.iota.IotaTaskManager;

import javax.inject.Inject;

import jota.IotaAPI;
import jota.error.ArgumentException;
import jota.model.Transfer;

public class PaymentService extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private IotaManager iotaManager;
    private TransactionsManager transactionsManager;

    public PaymentService(){}

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        private IotaManager iotaManager;
        private TransactionsManager transactionsManager;

        public ServiceHandler(Looper looper, IotaManager iotaManager, TransactionsManager transactionsManager) {
            super(looper);
            this.iotaManager = new IotaManager(new IotaTaskManager(getApplicationContext()),  new SharedPrefs(getApplicationContext()));
        }
        @Override
        public void handleMessage(Message msg) {
            String seed = iotaManager.getSeed();
            Log.d(PaymentService.class.getCanonicalName(),  "Seed" + iotaManager.getSeed());
            String protocol = Constants.PREFERENCE_NODE_DEFAULT_PROTOCOL;
            String host = Constants.PREFERENCE_NODE_DEFAULT_IP;
            int port = Integer.parseInt(Constants.PREFERENCE_NODE_DEFAULT_PORT);
            final IotaAPI iotaApi = new IotaAPI.Builder().protocol(protocol).host(host).port(((Integer) port).toString()).build();
            String address = "IQIEXYFVLAFBJRSLWAULEWENEQEWARUSZWKCAIJIPAMHMUUXIJVGIAXK9O9ERT9YUBGDPZNYISNPDLHNCNPRSJQKCW";
            iotaManager.sendTransfer(address, seed, "1");
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {

        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper, iotaManager, transactionsManager);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "payment service starting", Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "payment service done", Toast.LENGTH_SHORT).show();
    }


}

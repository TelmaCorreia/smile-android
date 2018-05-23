package com.thesis.smile.data.remote.utils;

import com.thesis.smile.BuildConfig;
import com.thesis.smile.data.preferences.SharedPrefs;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class HeaderAuthenticationInterceptor implements Interceptor{

    private final SharedPrefs sharedPrefs;

    @Inject
    HeaderAuthenticationInterceptor(SharedPrefs sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String userToken = sharedPrefs.getUserAuthorizationHeader();

        Request.Builder builder = request.newBuilder();
        builder.addHeader("x-app-version", String.valueOf(BuildConfig.VERSION_BUILD));
        builder.addHeader("x-app-platform", "android");

        if(userToken != null) {
            builder.addHeader("Authorization", userToken);
        }

        return chain.proceed(builder.build());
    }
}

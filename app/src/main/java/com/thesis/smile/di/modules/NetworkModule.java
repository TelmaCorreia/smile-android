package com.thesis.smile.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.data.remote.services.base.ApiError;
import com.thesis.smile.data.remote.utils.HeaderAuthenticationInterceptor;
import com.thesis.smile.data.remote.utils.LocalDateDeserializer;
import com.thesis.smile.data.remote.utils.LocalDateSerializer;
import com.thesis.smile.data.remote.utils.LocalDateTimeDeserializer;
import com.thesis.smile.data.remote.utils.LocalDateTimeSerializer;
import com.thesis.smile.data.remote.utils.OffsetDateTimeDeserializer;
import com.thesis.smile.data.remote.utils.OffsetDateTimeSerializer;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thesis.smile.Constants.CONNECT_TIMEOUT;
import static com.thesis.smile.Constants.READ_TIMEOUT;
import static com.thesis.smile.Constants.WRITE_TIMEOUT;

@Module
public class NetworkModule {

    private static final String BASE_URL = "baseUrl";

    @Provides
    @Named(BASE_URL)
    String provideBaseUrl() {
        return BuildConfig.BASE_API_URL;
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    OkHttpClient.Builder provideHttpClientBuilder(HttpLoggingInterceptor loggingInterceptor, HeaderAuthenticationInterceptor headerInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor);
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(OkHttpClient.Builder builder) {
        return UnsafeOkHttpClient.getUnsafeOkHttpClient(builder);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, @Named(BASE_URL) String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeDeserializer())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
    }

    @Provides
    @Singleton
    ApiError provideApiError(){
        return new ApiError();
    }



}

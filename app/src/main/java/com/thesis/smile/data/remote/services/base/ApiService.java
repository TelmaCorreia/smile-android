package com.thesis.smile.data.remote.services.base;

import android.accounts.NetworkErrorException;

import com.thesis.smile.data.remote.exceptions.http.GenericErrorException;
import com.thesis.smile.data.remote.exceptions.http.InternetConnectionException;
import com.thesis.smile.data.remote.exceptions.http.NotFoundException;
import com.thesis.smile.data.remote.exceptions.http.ServerException;
import com.thesis.smile.data.remote.exceptions.http.UnauthorizedException;
import com.thesis.smile.data.remote.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.exceptions.api.InvalidCredentialsException;
import com.thesis.smile.data.remote.models.response.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiService {
    protected Retrofit retrofit;
    protected ApiError apiError;

    protected <T> SingleTransformer<Response<T>, Response<T>> networkTransform() {
        return upstream -> upstream
                .onErrorResumeNext(throwable -> Single.error(mapThrowable(throwable)))
                .flatMap(response -> {
                    if (!response.isSuccessful()) {
                        return Single.error(mapHttpThrowable(response.code(), response));
                    }
                    return Single.just(response);
                });
    }

    protected <T> SingleTransformer<Response<T>, T> networkMapTransform() {
        return upstream -> upstream
                .compose(networkTransform())
                .map(Response::body);
    }

    @NonNull
    private Throwable mapThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return mapHttpThrowable(throwable);
        } else if (throwable instanceof NetworkErrorException) {
            return new InternetConnectionException();
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
            return new ConnectionTimeoutException();
        } else {
            return new GenericErrorException();
        }
    }

    @NonNull
    private Throwable mapHttpThrowable(Throwable throwable) {
        HttpException httpException = (HttpException) throwable;
        return mapHttpThrowable(httpException.code(), httpException.response());
    }

    private Throwable mapHttpThrowable(int errorCode, Response response){
        Throwable exception;
        switch (errorCode) {
            case 401:
                exception = new UnauthorizedException();
                break;
            case 403:
                exception = new InvalidCredentialsException();
                break;
            case 404:
                exception = new NotFoundException();
                break;
            case 400:
            case 409:
                exception = mapApiErrorResponseToThrowable(response);
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
                exception = new ServerException();
                break;
            default:
                exception = new GenericErrorException();
        }
        return exception;
    }

    @NonNull
    private Throwable mapApiErrorResponseToThrowable(Response response) {
        ErrorResponse errorResponse;

        Converter<ResponseBody, ErrorResponse> converter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        try {
            if(response != null && response.errorBody() != null) {
                errorResponse = converter.convert(response.errorBody());
            }else {
                return new ServerException();
            }
        } catch (IOException e) {
            return new IOException(e);
        }

        return apiError.getThrowable(errorResponse.getCode());
    }
}

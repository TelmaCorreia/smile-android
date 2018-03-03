package com.thesis.smile.presentation.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.CallSuper;
import android.view.View;

import com.thesis.smile.BR;
import com.thesis.smile.BuildConfig;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.utils.exceptions.api.ForceUpdateException;
import com.thesis.smile.data.remote.utils.exceptions.http.ConnectionTimeoutException;
import com.thesis.smile.data.remote.utils.exceptions.http.GenericErrorException;
import com.thesis.smile.presentation.utils.actions.AppUpdatesEvents;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.utils.ResourceProvider;
import com.thesis.smile.utils.schedulers.SchedulerProvider;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel implements Observable{

    private ResourceProvider resourceProvider;
    private SchedulerProvider schedulerProvider;
    private UiEvents uiEvents;
    private AppUpdatesEvents appUpdatesEvents;

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int loadingVisibility = View.GONE;
    private int emptyViewVisibility = View.GONE;

    private boolean empty = false;
    private boolean error = false;

    public BaseViewModel(ResourceProvider resourceProvider, SchedulerProvider schedulerProvider, UiEvents uiEvents, AppUpdatesEvents appUpdatesEvents) {
        this.resourceProvider = resourceProvider;
        this.schedulerProvider = schedulerProvider;
        this.uiEvents = uiEvents;
        this.appUpdatesEvents = appUpdatesEvents;
    }

    protected ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    protected UiEvents getUiEvents() {
        return uiEvents;
    }

    protected void addDisposable(Disposable d) {
        compositeDisposable.add(d);
    }

    protected void addDisposables(Disposable... ds){
        compositeDisposable.addAll(ds);
    }

    protected AppUpdatesEvents getAppUpdatesEvents(){
        return appUpdatesEvents;
    }

    protected void onError(Throwable e){
        if(!(e instanceof ConnectionTimeoutException)) {
            if (e instanceof ForceUpdateException) {
                getAppUpdatesEvents().showForceUpdate();
            } else if (e instanceof GenericErrorException) {
                getUiEvents().showToast(getResourceProvider().getString(R.string.err_api_generic));
            } else if (BuildConfig.DEBUG) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    @CallSuper
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    @Bindable
    public int getLoadingVisibility() {
        return loadingVisibility;
    }

    public boolean isLoadingVisible() {
        return getLoadingVisibility() == View.VISIBLE;
    }

    public void toggleLoading(boolean show) {
        loadingVisibility = show ? View.VISIBLE : View.GONE;
        notifyPropertyChanged(BR.loadingVisibility);
    }

    @Bindable
    public boolean isError() {
        return error;
    }

    public void toggleError(boolean isError) {
        empty = isError;
        notifyPropertyChanged(BR.error);
    }

    protected <T> SingleTransformer<T, T> loadingTransformSingle() {
        return loadingTransformSingle(true);
    }

    protected <T> SingleTransformer<T, T> loadingTransformSingle(boolean predicate) {
        if (predicate) {
            return upstream -> upstream
                    .doOnSubscribe(disposable -> toggleLoading(true))
                    .doFinally(() -> toggleLoading(false));
        }
        return upstream -> upstream;
    }

    protected CompletableTransformer loadingTransformCompletable() {
        return upstream -> upstream
                .doOnSubscribe(disposable -> toggleLoading(true))
                .doFinally(() -> toggleLoading(false));
    }

    protected <T>FlowableTransformer<T, T> schedulersTransformFlowable(){
        return upstream -> upstream
                .observeOn(schedulerProvider.ui());
    }

    protected <T> FlowableTransformer<T, T> schedulersTransformFlowableIo(){
        return upstream -> upstream
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    protected <T> MaybeTransformer<T, T> schedulersTransformMaybe(){
        return upstream -> upstream
                .observeOn(schedulerProvider.ui());
    }

    protected <T> MaybeTransformer<T, T> schedulersTransformMaybeIo(){
        return upstream -> upstream
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    protected <T> ObservableTransformer<T, T> schedulersTransformObservable() {
        return upstream -> upstream
                .observeOn(schedulerProvider.ui());
    }

    protected <T> SingleTransformer<T, T> schedulersTransformSingle() {
        return upstream -> upstream
                .observeOn(schedulerProvider.ui());
    }

    protected <T> SingleTransformer<T, T> schedulersTransformSingleIo() {
        return upstream -> upstream
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    protected CompletableTransformer schedulersTransformCompletable() {
        return upstream -> upstream
                .observeOn(schedulerProvider.ui());
    }

    protected CompletableTransformer schedulersTransformCompletableIo(){
        return upstream -> upstream
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    private transient PropertyChangeRegistry mCallbacks;


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(onPropertyChangedCallback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(onPropertyChangedCallback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }
    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    public void notifyPropertiesChanged(int... fieldIds) {
        for (int fieldId : fieldIds) {
            notifyPropertyChanged(fieldId);
        }
    }
}

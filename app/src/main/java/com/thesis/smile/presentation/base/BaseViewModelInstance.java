package com.thesis.smile.presentation.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModelInstance extends ViewModel implements Observable {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseViewModelInstance(){}

    protected void addDisposable(Disposable d) {
        compositeDisposable.add(d);
    }

    protected void addDisposables(Disposable... ds){
        compositeDisposable.addAll(ds);
    }

    @Override
    @CallSuper
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    //<editor-fold desc="BaseObservable">
    private transient PropertyChangeRegistry mCallbacks;

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
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
    //</editor-fold>
}

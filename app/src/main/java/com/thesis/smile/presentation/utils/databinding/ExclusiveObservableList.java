package com.thesis.smile.presentation.utils.databinding;

import android.databinding.ObservableArrayList;

public class ExclusiveObservableList<T> extends ObservableArrayList<T> {

    @Override
    public boolean add(T object) {
        if(this.contains(object)){
            int index = this.indexOf(object);
            this.set(index, object);
            return true;
        }else{
            return super.add(object);
        }
    }
}

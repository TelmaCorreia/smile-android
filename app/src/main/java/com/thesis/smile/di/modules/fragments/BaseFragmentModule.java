package com.thesis.smile.di.modules.fragments;


import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;

@Module
abstract class BaseFragmentModule<F extends Fragment> {

    @Binds
    abstract Fragment fragment(F Fragment);
}

package com.thesis.smile.presentation.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.thesis.smile.BR;
import com.thesis.smile.presentation.utils.KeyboardUtils;
import com.thesis.smile.presentation.utils.transitions.FragmentTransitionManager;
import com.thesis.smile.utils.ResourceProvider;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<ViewBinding extends ViewDataBinding, ViewModel extends BaseViewModel>
        extends DaggerFragment {

    @Inject
    ResourceProvider resourceProvider;

    @Inject
    ViewModelFactory<ViewModel> viewModelFactory;

    @Inject
    FragmentTransitionManager fragmentTransitionManager;

    private ViewModel viewModel;
    private ViewBinding binding;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void initArguments(Bundle args) {
    }

    protected abstract int layoutResId();

    protected abstract Class<ViewModel> viewModelClass();

    protected boolean sharedViewModel(){
        return false;
    }

    @CallSuper
    protected void initBinding(final ViewBinding binding, final ViewModel viewModel) {
        binding.setVariable(BR.viewModel, viewModel);
    }

    protected abstract void initViews(final ViewBinding binding);

    protected void addDisposable(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onAttach(Context context) {
        initArguments(getArguments());
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutResId(), container, false);
        return getBinding().getRoot();
    }

    /**
     * See {@link FragmentTransitionManager} documentation.
     */

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (fragmentTransitionManager.hasOverriddenPopAnimation() && !enter) {
            return AnimationUtils.loadAnimation(getContext(), fragmentTransitionManager.getOverriddenPopAnimation());
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(sharedViewModel()) {
            viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(viewModelClass());
        }else{
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass());
        }

        initBinding(binding, this.viewModel);
        initViews(binding);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        registerObservables();
    }

    @Override
    @CallSuper
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @CallSuper
    protected void registerObservables() {
    }

    protected boolean useLightStatusBar() {
        return false;
    }

    public final ViewBinding getBinding() {
        return binding;
    }

    public final ViewModel getViewModel() {
        return viewModel;
    }

    protected ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public <T extends BaseViewModel> T getActivityViewModel(Class<T> clazz) {
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(clazz);
    }

    public <T extends BaseViewModel> T getSharedViewModel(ViewModelProvider.Factory factory, Class<T> clazz) {
        return ViewModelProviders.of(getActivity(), factory).get(clazz);
    }

    public final BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void setupUI(View view, Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    KeyboardUtils.hideKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

}

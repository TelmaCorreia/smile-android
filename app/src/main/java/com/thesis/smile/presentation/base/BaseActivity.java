package com.thesis.smile.presentation.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thesis.smile.BR;
import com.thesis.smile.presentation.utils.KeyboardUtils;
import com.thesis.smile.presentation.utils.actions.UiEvents;
import com.thesis.smile.presentation.utils.actions.UiMessages;
import com.thesis.smile.presentation.utils.transitions.ActivityTransition;
import com.thesis.smile.presentation.utils.transitions.FadeTransition;
import com.thesis.smile.presentation.utils.transitions.FragmentTransitionManager;
import com.thesis.smile.presentation.utils.transitions.TransitionManager;
import com.thesis.smile.utils.ResourceProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<ViewBinding extends ViewDataBinding, ViewModel extends BaseViewModel>
        extends DaggerAppCompatActivity {

    @Inject
    ResourceProvider resourceProvider;

    @Inject
    UiEvents uiEvents;

    @Inject
    UiMessages uiMessages;

    @Inject
    ViewModelFactory<ViewModel> viewModelFactory;

    @Inject
    FragmentTransitionManager fragmentTransitionManager;

    private ViewModel viewModel;
    private ViewBinding binding;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final CompositeDisposable uiActionsCompositeDisposable = new CompositeDisposable();

    private final TransitionManager transitionManager = TransitionManager.create(getActivityTransition());

    protected ActivityTransition getActivityTransition() {
        // To override if necessary
        return new FadeTransition();
    }

    protected void initArguments(Bundle args) {
    }

    protected abstract int layoutResId();

    protected abstract Class<ViewModel> viewModelClass();

    @CallSuper
    protected void initBinding(final ViewBinding binding, final ViewModel viewModel) {
        binding.setVariable(BR.viewModel, viewModel);
    }

    protected abstract void initViews(final ViewBinding binding);

    protected void addDisposable(Disposable d) {
        compositeDisposable.add(d);
    }

    protected void addUiActionsDisposable(Disposable d) {
        uiActionsCompositeDisposable.add(d);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initArguments(getIntent().getExtras());

        AndroidInjection.inject(this);

        transitionManager.enterTransition(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, layoutResId());

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass());

        initBinding(binding, this.viewModel);
        initViews(binding);
    }


    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        registerObservables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerUiActionsObservables();
    }

    @Override
    protected void onPause() {
        uiActionsCompositeDisposable.clear();
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @CallSuper
    protected void registerObservables() {
    }

    @CallSuper
    protected void registerUiActionsObservables() {
        uiEvents.observeToastEvent()
                .doOnSubscribe(this::addUiActionsDisposable)
                .subscribe(event -> uiMessages.showToast(event.getMessage()));

        uiEvents.observeCloseEvent()
                .doOnSubscribe(this::addUiActionsDisposable)
                .subscribe(event -> finish());

    }

    @Override
    public void finish() {
        super.finish();
        transitionManager.exitTransition(this);
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

    public FragmentTransitionManager getFragmentTransitionManager() {
        return fragmentTransitionManager;
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

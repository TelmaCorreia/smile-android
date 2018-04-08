package com.thesis.smile.presentation.base.toolbar;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.thesis.smile.R;
import com.thesis.smile.presentation.base.BaseActivity;

public abstract class BaseToolbarActivity<ViewBinding extends ViewDataBinding, ViewModel extends BaseToolbarViewModel>
        extends BaseActivity<ViewBinding, ViewModel> {

    private ToolbarActionType actionType = ToolbarActionType.NONE;

    public void initToolbar(Toolbar toolbar) {
        if (toolbar == null) {
            throw new IllegalArgumentException("toolbar must not be null.");
        }

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void initToolbar(Toolbar toolbar, boolean showToolbarNavigation) {
        if (toolbar == null) {
            throw new IllegalArgumentException("toolbar must not be null.");
        }
        if (showToolbarNavigation){
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

    }

    public void initToolbar(Toolbar toolbar, boolean showToolbarNavigation, ImageView ivAction, ToolbarActionType actionType) {
        initToolbar(toolbar, showToolbarNavigation);

        this.actionType = actionType;
        switch (actionType){
            case EDIT:
                ivAction.setImageDrawable(getResourceProvider().getDrawable(R.drawable.ic_edit));
            case NONE:
            default:

        }
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel()
                .observeAction()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> doAction());
    }

    protected void doAction(){}

}
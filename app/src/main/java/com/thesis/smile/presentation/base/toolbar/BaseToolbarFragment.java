package com.thesis.smile.presentation.base.toolbar;

import android.databinding.ViewDataBinding;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.thesis.smile.R;
import com.thesis.smile.presentation.base.BaseFragment;

public abstract class BaseToolbarFragment<ViewBinding extends ViewDataBinding, ViewModel extends BaseToolbarViewModel>
        extends BaseFragment<ViewBinding, ViewModel> {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private ToolbarActionType actionType = ToolbarActionType.NONE;

    public void initToolbar(AppBarLayout appBarLayout, Toolbar toolbar){
        initToolbar(appBarLayout, toolbar, true);
    }

    public void initToolbar(AppBarLayout appBarLayout, Toolbar toolbar, boolean showToolbarNavigation) {
        if (appBarLayout == null) {
            throw new IllegalArgumentException("appBarLayout must not be null.");
        }

        if (toolbar == null) {
            throw new IllegalArgumentException("toolbar must not be null.");
        }

        this.appBarLayout = appBarLayout;
        this.toolbar = toolbar;

        if (showToolbarNavigation) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }
    }

    public void initToolbar(AppBarLayout appBarLayout, Toolbar toolbar, boolean showToolbarNavigation, String title) {
        initToolbar(appBarLayout, toolbar, showToolbarNavigation);
        getViewModel().setToolbarTitle(title);
    }

    public void initToolbar(AppBarLayout appBarLayout, Toolbar toolbar, boolean showToolbarNavigation, ImageView ivAction, ToolbarActionType actionType, String title) {
        initToolbar(appBarLayout, toolbar, showToolbarNavigation, title);

        this.actionType = actionType;
        switch (actionType){
            case EDIT:
                ivAction.setImageDrawable(getResourceProvider().getDrawable(R.drawable.ic_edit));
            case HIDDEN_MENU:
                ivAction.setImageDrawable(getResourceProvider().getDrawable(R.drawable.ic_more));
            case NONE:
            default:

        }

    }
    @Override
    protected void registerObservables() {
        super.registerObservables();

        if (appBarLayout == null || toolbar == null) {
            throw new IllegalStateException("initToolbar must be called with non-null values.");
        }

    }

    protected void doAction(){}
}
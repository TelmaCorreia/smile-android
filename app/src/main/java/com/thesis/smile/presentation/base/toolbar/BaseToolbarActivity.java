package com.thesis.smile.presentation.base.toolbar;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
            case HIDDEN_MENU:
                toolbar.inflateMenu(R.menu.toolbar_menu);
                Menu menu = toolbar.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setOnMenuItemClickListener(menuItem -> {
                        doAction(item.getItemId());
                        return true;
                    });
                }
                case NONE:
            default:

        }
    }

    public void initToolbar( Toolbar toolbar, boolean showToolbarNavigation, ImageView ivAction, ToolbarActionType actionType, String title) {
        initToolbar(toolbar, showToolbarNavigation, ivAction, actionType);
        getViewModel().setToolbarTitle(title);

    }


    protected void doAction(int item){}

}
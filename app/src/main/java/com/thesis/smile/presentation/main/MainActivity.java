package com.thesis.smile.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.thesis.smile.R;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.base.toolbar.ToolbarActionType;
import com.thesis.smile.presentation.main.historical.HistoricalFragment;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.main.menu_events.MenuType;
import com.thesis.smile.databinding.ActivityMainBinding;
import com.thesis.smile.presentation.main.menu_events.OpenMenuEvent;
import com.thesis.smile.presentation.main.ranking.RankingFragment;
import com.thesis.smile.presentation.main.transactions.TransactionsFragment;


public class MainActivity extends BaseToolbarActivity<ActivityMainBinding, MainViewModel> {

    private MenuType currentMenuType;

    private static final String TAG_MENU_FRAGMENT = "menu";

    private HomeFragment homeFragment;
    private HistoricalFragment historicalFragment;
    private TransactionsFragment transactionsFragment;
    private RankingFragment rankingFragment;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> viewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void initViews(ActivityMainBinding binding) {
        this.homeFragment = HomeFragment.newInstance();
        this.historicalFragment = HistoricalFragment.newInstance();
        this.transactionsFragment = TransactionsFragment.newInstance();
        this.rankingFragment = RankingFragment.newInstance();
        initToolbar(binding.actionBar.toolbar, false, binding.actionBar.action, ToolbarActionType.HIDDEN_MENU, getResources().getString(R.string.home_title));

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getBinding().menu.home.post(
                () -> getViewModel()
                        .observeOpenMenu()
                        .doOnSubscribe(this::addDisposable)
                        .subscribe(this::openMenu)
        );

        getViewModel()
                .observeLogout()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    LoginActivity.launch(this);
                    finish();
                });
    }

    private void openMenu(OpenMenuEvent event) {
        MenuType menuType = event.getMenuType();
        if (currentMenuType == menuType) {
            return;
        }

        unselectAllMenus();

        Fragment fragment;
        switch (menuType) {
            case HOME:
            default:
                getBinding().menu.home.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.homeLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                getViewModel().setToolbarTitle(getResources().getString(R.string.home_title));
                fragment = homeFragment;
                break;
            case HISTORICAL:
                getBinding().menu.historical.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.historicalLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                getViewModel().setToolbarTitle(getResources().getString(R.string.historical_title));
                fragment = historicalFragment;
                break;
            case TRANSACTIONS:
                getBinding().menu.transactions.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.transactionsLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                getViewModel().setToolbarTitle(getResources().getString(R.string.transactions_title));
                fragment = transactionsFragment;
                break;
            case RANKING:
                getBinding().menu.ranking.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.rankingLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                getViewModel().setToolbarTitle(getResources().getString(R.string.ranking_title));
                fragment = rankingFragment;
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.stay, R.anim.exit_fade)
                .replace(R.id.fragment, fragment, TAG_MENU_FRAGMENT)
                .runOnCommit(() -> getFragmentTransitionManager().clearOverriddenPopAnimation())
                .commitAllowingStateLoss();

        this.currentMenuType = menuType;
    }

    @Override
    public void onBackPressed() {
        //TODO
        switch (currentMenuType){
            case HISTORICAL:
                //historicalFragment.onBackPressed();
                break;
            case HOME:
            case TRANSACTIONS:
            case RANKING:
            default:
                finish();
        }
    }

    private void unselectAllMenus() {
        getBinding().menu.home.setColorFilter(ContextCompat.getColor(this, R.color.colorBackgroundDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);
        getBinding().menu.homeLabel.setTextColor(getResources().getColor(R.color.colorBackgroundDisabled));
        getBinding().menu.historical.setColorFilter(ContextCompat.getColor(this, R.color.colorBackgroundDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);
        getBinding().menu.historicalLabel.setTextColor(getResources().getColor(R.color.colorBackgroundDisabled));
        getBinding().menu.transactions.setColorFilter(ContextCompat.getColor(this, R.color.colorBackgroundDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);
        getBinding().menu.transactionsLabel.setTextColor(getResources().getColor(R.color.colorBackgroundDisabled));
        getBinding().menu.ranking.setColorFilter(ContextCompat.getColor(this, R.color.colorBackgroundDisabled), android.graphics.PorterDuff.Mode.MULTIPLY);
        getBinding().menu.rankingLabel.setTextColor(getResources().getColor(R.color.colorBackgroundDisabled));

    }

    @Override
    protected void doAction(int item) {
        switch (item){
            case R.id.action_settings:
                break; //TODO
            case R.id.action_logout: {
                getViewModel().logout();
                break;
            }

        }
    }
}

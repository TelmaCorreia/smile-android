package com.thesis.smile.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.gson.Gson;
import com.thesis.smile.R;
import com.thesis.smile.data.remote.models.request.RegisterRequest;
import com.thesis.smile.iota.responses.GetAccountDataResponse;
import com.thesis.smile.iota.responses.GetNewAddressResponse;
import com.thesis.smile.iota.responses.SendTransferResponse;
import com.thesis.smile.iota.responses.error.NetworkError;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.authentication.register.energy.RegisterEquipmentActivity;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.base.toolbar.ToolbarActionType;
import com.thesis.smile.presentation.main.historical.HistoricalFragment;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.main.menu_events.MenuType;
import com.thesis.smile.databinding.ActivityMainBinding;
import com.thesis.smile.presentation.main.menu_events.OpenMenuEvent;
import com.thesis.smile.presentation.main.menu_events.OpenTransactionsEvent;
import com.thesis.smile.presentation.main.ranking.RankingFragment;
import com.thesis.smile.presentation.main.transactions.TransactionsFragment;
import com.thesis.smile.presentation.privacy_policy.PrivacyPolicyActivity;
import com.thesis.smile.presentation.settings.SettingsActivity;
import com.thesis.smile.presentation.utils.KeyboardUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;


public class MainActivity extends BaseToolbarActivity<ActivityMainBinding, MainViewModel> {

    private MenuType currentMenuType;

    private static final String TAG_MENU_FRAGMENT = "menu";
    public static final String TAG_FRAGMENT_TRANSACTIONS = "open_transactions";


    private HomeFragment homeFragment;
    private HistoricalFragment historicalFragment;
    private TransactionsFragment transactionsFragment;
    private RankingFragment rankingFragment;

    private boolean transactions;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TAG_FRAGMENT_TRANSACTIONS, false);
        context.startActivity(intent);
    }

    public static void launchTransactions(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TAG_FRAGMENT_TRANSACTIONS, true);
        context.startActivity(intent);
    }

    @Override
    protected void initArguments(Bundle args) {
        super.initArguments(args);
        this.transactions = args.getBoolean(TAG_FRAGMENT_TRANSACTIONS);

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
        if (transactions){menuType= MenuType.TRANSACTIONS; transactions=false;}

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
                SettingsActivity.launch(this);
                break;
            case R.id.action_privacy:
                PrivacyPolicyActivity.launch(this);
                break;
            case R.id.action_logout: {
                getViewModel().logout();
                break;
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        transactionsFragment.onActivityResult(requestCode, resultCode, data);

    }




}

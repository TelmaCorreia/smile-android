package com.thesis.smile.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.thesis.smile.R;
import com.thesis.smile.presentation.base.BaseActivity;
import com.thesis.smile.presentation.main.home.HomeFragment;
import com.thesis.smile.presentation.main.menu_events.MenuType;
import com.thesis.smile.databinding.ActivityMainBinding;
import com.thesis.smile.presentation.main.menu_events.OpenMenuEvent;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private MenuType currentMenuType;

    private static final String TAG_MENU_FRAGMENT = "menu";

    private HomeFragment homeFragment;
    private HomeFragment historicalFragment;
    private HomeFragment transactionsFragment;
    private HomeFragment rankingFragment;

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
        this.historicalFragment = HomeFragment.newInstance();
        this.transactionsFragment = HomeFragment.newInstance();
        this.rankingFragment = HomeFragment.newInstance();
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
                fragment = homeFragment;
                break;
            case HISTORICAL:
                getBinding().menu.historical.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.historicalLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                fragment = historicalFragment;
                break;
            case TRANSACTIONS:
                getBinding().menu.transactions.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.transactionsLabel.setTextColor(getResources().getColor(R.color.colorBackground));
                fragment = transactionsFragment;
                break;
            case RANKING:
                getBinding().menu.ranking.setColorFilter(ContextCompat.getColor(this, R.color.colorBackground), android.graphics.PorterDuff.Mode.MULTIPLY);
                getBinding().menu.rankingLabel.setTextColor(getResources().getColor(R.color.colorBackground));
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

}

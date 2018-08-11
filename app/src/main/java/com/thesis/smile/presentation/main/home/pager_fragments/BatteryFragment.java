package com.thesis.smile.presentation.main.home;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHomeBinding;
import com.thesis.smile.presentation.authentication.login.LoginActivity;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarFragment;
import com.thesis.smile.presentation.base.toolbar.ToolbarActionType;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> viewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected void initViews(FragmentHomeBinding binding) {
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel()
                .observeOpenHomeBoughtDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    HomeDetailsActivity.launch(getContext(), getResources().getString(R.string.details_bought_energy));
                });

        getViewModel()
                .observeOpenHomeSoldDetails()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    HomeDetailsActivity.launch(getContext(), getResources().getString(R.string.details_sold_energy));
                });
    }



}

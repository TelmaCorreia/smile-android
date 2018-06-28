package com.thesis.smile.presentation.iota_settings;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thesis.smile.R;
import com.thesis.smile.presentation.iota_settings.pager_fragments.DayIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.MonthIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.WeekIotaFragment;
import com.thesis.smile.utils.ResourceProvider;

public class TemporalIotaViewPagerAdapter extends FragmentPagerAdapter {

    private ResourceProvider resourceProvider;
    private DayIotaFragment dayIotaFragment;
    private WeekIotaFragment weekIotaFragment;
    private MonthIotaFragment monthIotaFragment;

    public TemporalIotaViewPagerAdapter(FragmentManager fm, ResourceProvider resourceProvider) {
        super(fm);
        this.resourceProvider = resourceProvider;
        this.dayIotaFragment = DayIotaFragment.newInstance();
        this.weekIotaFragment = WeekIotaFragment.newInstance();
        this.monthIotaFragment = MonthIotaFragment.newInstance();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return dayIotaFragment;
            case 1:
                return weekIotaFragment;
            case 2:
                return monthIotaFragment;
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return resourceProvider.getString(R.string.wallet_tab_day);
            case 1:
                return resourceProvider.getString(R.string.wallet_tab_week);
            case 2:
                return resourceProvider.getString(R.string.wallet_tab_month);
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    public MonthIotaFragment getMonthIotaFragment() {
        return monthIotaFragment;
    }

    public WeekIotaFragment getWeekIotaFragment(){
        return weekIotaFragment;
    }

    public DayIotaFragment getDayIotaFragment() {
        return dayIotaFragment;
    }




}

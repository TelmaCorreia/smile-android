package com.thesis.smile.presentation.main.historical;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thesis.smile.R;
import com.thesis.smile.presentation.iota_settings.pager_fragments.DayIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.MonthIotaFragment;
import com.thesis.smile.presentation.iota_settings.pager_fragments.WeekIotaFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.DayHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.MonthHistoricalFragment;
import com.thesis.smile.presentation.main.historical.temporal_fragments.WeekHistoricalFragment;
import com.thesis.smile.utils.ResourceProvider;

public class TemporalViewPagerAdapter extends FragmentPagerAdapter {

    private ResourceProvider resourceProvider;
    private DayHistoricalFragment dayHistoricalFragment;
    private WeekHistoricalFragment weekHistoricalFragment;
    private MonthHistoricalFragment monthHistoricalFragment;

    public TemporalViewPagerAdapter(FragmentManager fm, ResourceProvider resourceProvider) {
        super(fm);
        this.resourceProvider = resourceProvider;
        this.dayHistoricalFragment = DayHistoricalFragment.newInstance();
        this.weekHistoricalFragment = WeekHistoricalFragment.newInstance();
        this.monthHistoricalFragment = MonthHistoricalFragment.newInstance();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return dayHistoricalFragment;
            case 1:
                return weekHistoricalFragment;
            case 2:
                return monthHistoricalFragment;
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return resourceProvider.getString(R.string.tab_days);
            case 1:
                return resourceProvider.getString(R.string.tab_weeks);
            case 2:
                return resourceProvider.getString(R.string.tab_months);
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    public MonthHistoricalFragment getMonthHistoricalFragment() {
        return monthHistoricalFragment;
    }

    public WeekHistoricalFragment getWeekHistoricalFragment(){
        return weekHistoricalFragment;
    }

    public DayHistoricalFragment getDayHistoricalFragment() {
        return dayHistoricalFragment;
    }




}

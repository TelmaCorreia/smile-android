package com.thesis.smile.presentation.main.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thesis.smile.R;
import com.thesis.smile.presentation.main.home.pager_fragments.BatteryFragment;
import com.thesis.smile.presentation.main.home.pager_fragments.RenewableFragment;
import com.thesis.smile.presentation.main.home.pager_fragments.TransactionsHomeFragment;
import com.thesis.smile.utils.ResourceProvider;

public class HomePagerAdapter extends FragmentPagerAdapter
{
    private BatteryFragment batteryFragment;
    private RenewableFragment renewableFragment;
    private ResourceProvider resourceProvider;
    private TransactionsHomeFragment transactionsHomeFragment;

    public HomePagerAdapter(FragmentManager paramFragmentManager, ResourceProvider paramResourceProvider)
    {
        super(paramFragmentManager);
        this.resourceProvider = paramResourceProvider;
        this.batteryFragment = BatteryFragment.newInstance();
        this.transactionsHomeFragment = TransactionsHomeFragment.newInstance();
        this.renewableFragment = RenewableFragment.newInstance();
    }

    public BatteryFragment getBatteryFragment()
    {
        return this.batteryFragment;
    }

    public int getCount()
    {
        return 3;
    }

    public Fragment getItem(int paramInt)
    {
        switch (paramInt)
        {
            case 0:
                return this.batteryFragment;
            case 1:
                return this.transactionsHomeFragment;
            case 2:
                return this.renewableFragment;
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Nullable
    public CharSequence getPageTitle(int paramInt)
    {
        switch (paramInt)
        {
            case 0:
                return this.resourceProvider.getString(R.string.home_page_battery);
            case 1:
                return this.resourceProvider.getString(R.string.home_page_transactions);
            case 2:
                return this.resourceProvider.getString(R.string.home_page_renewables);
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    public RenewableFragment getRenewableFragment()
    {
        return this.renewableFragment;
    }

    public TransactionsHomeFragment getTransactionsHomeFragment()
    {
        return this.transactionsHomeFragment;
    }
}

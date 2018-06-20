package com.thesis.smile.presentation.settings.energy_settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentEnergySettingsBinding;
import com.thesis.smile.presentation.authentication.register.energy.info.AutomaticSettingsInfoActivity;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.settings.user_settings.ChangePasswordActivity;
import com.thesis.smile.presentation.utils.SortUtils;
import com.thesis.smile.presentation.utils.adapters.NothingSelectedSpinnerAdapter;

public class EnergySettingsFragment extends BaseFragment<FragmentEnergySettingsBinding, EnergySettingsViewModel> {

    public static EnergySettingsFragment newInstance() {
        EnergySettingsFragment f = new EnergySettingsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_energy_settings;
    }

    @Override
    protected Class<EnergySettingsViewModel> viewModelClass() {
        return EnergySettingsViewModel.class;
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().openLearMoreObservable()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event -> {
                    AutomaticSettingsInfoActivity.launch(getContext());
                });
    }

    @Override
    protected void initViews(FragmentEnergySettingsBinding binding) {
        String[] categories = getViewModel().getConfigs().getCategories().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterCategories = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, categories);
        adapterCategories.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spCategory.setAdapter(adapterCategories);
        binding.spCategory.setSelection(adapterCategories.getPosition(getViewModel().getCategory()));

        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getViewModel().setCategory(categories[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] powers = SortUtils.sortMapByKey(getViewModel().getConfigs().getPower()).toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterPowers = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, powers);
        adapterPowers.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spPower.setAdapter(adapterPowers);
        binding.spPower.setSelection(adapterPowers.getPosition(getViewModel().getPower()));
        binding.spPower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    getViewModel().setPower(powers[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        String[] tariffs = SortUtils.sortMapByKey(getViewModel().getConfigs().getTariff()).toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterTariffs = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, tariffs);
        adapterTariffs.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spTariff.setAdapter(adapterTariffs);
        binding.spTariff.setSelection(adapterTariffs.getPosition(getViewModel().getTariff()));
        binding.spTariff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    getViewModel().setTariff(tariffs[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] cycles = getViewModel().getConfigs().getCycle().values().toArray(new String[0]);
        ArrayAdapter<CharSequence> adapterCycles = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, cycles);
        adapterCycles.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spCycle.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapterCycles, R.layout.layout_spinner_item_nothing_selected_cycle,getContext()));

        binding.spCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setCycle(cycles[i-1]);

                }else {
                    getViewModel().setCycle(getResources().getString(R.string.no_cycle));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (!getViewModel().getCycle().equals(getString(R.string.no_cycle))){
            binding.spCycle.setSelection(adapterCycles.getPosition(getViewModel().getCycle())+1);
        }

    }




}

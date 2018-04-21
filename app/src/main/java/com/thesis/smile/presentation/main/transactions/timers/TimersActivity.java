package com.thesis.smile.presentation.main.transactions.timers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityTimersBinding;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class TimersActivity extends BaseToolbarActivity<ActivityTimersBinding, TimersViewModel> implements com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    private static final String RESULT_DATA_NAME = "resultDataName";
    private static final String TIMER = "timer";
    private boolean from = true;
    private String resultDataName;
    private TimeInterval timeInterval;

    public static void launchForResult(Activity activity, int code, String resultDataName) {
        Intent intent = new Intent(activity, TimersActivity.class);
        intent.putExtra(RESULT_DATA_NAME, resultDataName);
        activity.startActivityForResult(intent, code);
    }

    public static void launchForResult(Activity activity, int code, String resultDataName,  TimeInterval timeInterval) {
        Intent intent = new Intent(activity, TimersActivity.class);
        intent.putExtra(RESULT_DATA_NAME, resultDataName);
        intent.putExtra(TIMER, timeInterval);
        activity.startActivityForResult(intent, code);
    }


    @Override
    protected int layoutResId() {
        return R.layout.activity_timers;
    }

    @Override
    protected Class<TimersViewModel> viewModelClass() {
        return TimersViewModel.class;
    }

    @Override
    protected void initViews(ActivityTimersBinding binding) {
        initToolbar(binding.actionBar.toolbar, true,  getResources().getString(R.string.timers_title));
        binding.weekdays.setSelectedDays(new ArrayList<>());
        binding.weekdays.setOnWeekdaysChangeListener((view, i, list) -> {getViewModel().setSelectedDays(list); getViewModel().setSelectedDaysStrings(binding.weekdays.getSelectedDaysText());});
        if (timeInterval!= null){
            getViewModel().setFrom(timeInterval.getFrom());
            getViewModel().setTo(timeInterval.getTo());
            getViewModel().setSelectedDaysStrings(timeInterval.getWeekDaysString());
            getBinding().weekdays.setSelectedDays(timeInterval.getWeekdays());
        }
    }

    @Override
    protected void initArguments(Bundle args){
        super.initArguments(args);
        resultDataName = args.getString(RESULT_DATA_NAME);
        timeInterval = args.getParcelable(TIMER);

    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeTimerFromDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->{from = true; timerDialogEvent(event);});

        getViewModel().observeTimerToDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->{from = false; timerDialogEvent(event);});

        getViewModel().observeSave()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::saveTimeInterval);
    }

    private void saveTimeInterval(Event event) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(resultDataName, getViewModel().getTimeInterval());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void timerDialogEvent(DialogEvent event){
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                TimersActivity.this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
               true
        );
        timePickerDialog.show(getFragmentManager(), "TimePicker");
        timePickerDialog.setAccentColor(getResources().getColor(R.color.colorUnderline));
        timePickerDialog.setOkColor(getResources().getColor(R.color.colorUnderline));
        timePickerDialog.setCancelColor(getResources().getColor(R.color.colorUnderline));
    }

    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = String.format("%1$02d:%2$02d", hourOfDay, minute);
        if (from){
            getViewModel().setFrom(time);
        }else{
            getViewModel().setTo(time);
        }

    }
}

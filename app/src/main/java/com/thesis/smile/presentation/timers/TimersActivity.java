package com.thesis.smile.presentation.timers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thesis.smile.R;
import com.thesis.smile.databinding.ActivityTimersBinding;
import com.thesis.smile.domain.models.TimeInterval;
import com.thesis.smile.presentation.base.toolbar.BaseToolbarActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
        binding.weekDays.setSelectedDays(new ArrayList<>());
        binding.weekDays.setOnWeekdaysChangeListener((view, i, list) -> {getViewModel().setSelectedDays(list); getViewModel().setSelectedDaysStrings(binding.weekDays.getSelectedDaysText());});
        if (timeInterval!= null){
            getViewModel().setPreviousFrom(timeInterval.getFrom());
            getViewModel().setPreviousTo(timeInterval.getTo());
            getViewModel().setPreviousSelectedDays(timeInterval.getWeekDays());
            getViewModel().setId(timeInterval.getId());
            getViewModel().setFrom(timeInterval.getFrom());
            getViewModel().setTo(timeInterval.getTo());
            getViewModel().setSelectedDays(timeInterval.getWeekDays());
            getViewModel().setSelectedDaysStrings(timeInterval.getWeekDaysString());
            getViewModel().setActivated(timeInterval.isActivated());
            getBinding().weekDays.setSelectedDays(timeInterval.getWeekDays());
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
                .subscribe(event ->{
                    from = true;
                    if (timeInterval!= null){
                        timerDialogEvent(getHour(timeInterval.getFrom()), getMinute(timeInterval.getFrom()), event);
                    }else{
                        timerDialogEvent(0, 0, event);
                    }

                });

        getViewModel().observeTimerToDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->{
                    from = false;
                    if (timeInterval!= null){
                        timerDialogEvent(getHour(timeInterval.getTo()), getMinute(timeInterval.getTo()), event);
                    }else{
                        timerDialogEvent(0, 0, event);
                    }
                });

        getViewModel().observeSave()
                .doOnSubscribe(this::addDisposable)
                .subscribe(this::saveTimeInterval);
    }

    private int getHour(String timeInterval) {
        if (timeInterval!=null){
            LocalTime time = LocalTime.parse(timeInterval);
            return time.getHour();
        }
        return 0;
    }

    private int getMinute(String timeInterval) {
        if (timeInterval!=null){
            LocalTime time = LocalTime.parse(timeInterval);
            return time.getMinute();
        }
        return 0;
    }

    private void saveTimeInterval(Event event) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(resultDataName, getViewModel().getTimeInterval());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void timerDialogEvent(int hour, int minute, DialogEvent event){
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                TimersActivity.this,
                (hour!=0)?hour:now.get(Calendar.HOUR_OF_DAY),
                (minute!=0)?minute:now.get(Calendar.MINUTE),
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

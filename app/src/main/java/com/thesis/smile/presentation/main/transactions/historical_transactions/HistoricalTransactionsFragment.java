package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHistoricalTransactionsBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.list.HistoricalTransactionAdapter;
import com.thesis.smile.presentation.transaction_details.TransactionDetailsActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;

public class HistoricalTransactionsFragment extends BaseFragment<FragmentHistoricalTransactionsBinding, HistoricalTransactionsViewModel> implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{

    private String type;
    private boolean initialDate = true;

    public static HistoricalTransactionsFragment newInstance() {
        HistoricalTransactionsFragment f = new HistoricalTransactionsFragment();
        return f;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_historical_transactions;
    }

    @Override
    protected Class<HistoricalTransactionsViewModel> viewModelClass() {
        return HistoricalTransactionsViewModel.class;
    }

    @Override
    protected void initViews(FragmentHistoricalTransactionsBinding binding) {

        String[] timePeriodMenu = {getResources().getString(R.string.transactions_last_30_days), getResources().getString(R.string.transactions_specific_period)};
        ArrayAdapter<CharSequence> adapterTimePeriod = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, timePeriodMenu);
        adapterTimePeriod.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spPeriod.setAdapter(adapterTimePeriod);

        binding.spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setTimePeriod(timePeriodMenu[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getViewModel().setTimePeriod(timePeriodMenu[0]);
            }
        });

        String[] transactionsTypeMenu = {getResources().getString(R.string.transactions_menu_all), getResources().getString(R.string.transactions_menu_buy), getResources().getString(R.string.transactions_menu_sell)};
        ArrayAdapter<CharSequence> adapterTypeMenu = new ArrayAdapter(getContext(),R.layout.layout_spinner_item, transactionsTypeMenu);
        adapterTimePeriod.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spType.setAdapter(adapterTypeMenu);

        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    getViewModel().setType(transactionsTypeMenu[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getViewModel().setType(transactionsTypeMenu[0]);
            }
        });


        getViewModel().setType(getResources().getString(R.string.details_bought_energy)); //FIXME
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        HistoricalTransactionAdapter historicalTransactionAdapter = new HistoricalTransactionAdapter(getViewModel().getTransactions(), this::onTransactionSelected);
        binding.historicalTransactions.setLayoutManager(layoutManager);
        binding.historicalTransactions.setAdapter(historicalTransactionAdapter);

    }

    private void onTransactionSelected(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getResourceProvider().getString(R.string.date_format_transactions));
        transaction.setDateString(transaction.getDate().format(formatter));
        TransactionDetailsActivity.launch(getContext(), transaction);
    }

    @Override
    protected void registerObservables() {
        super.registerObservables();

        getViewModel().observeInitialDateCalendarDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->{initialDate = true; dateDialogEvent(event);});

        getViewModel().observeFinalDateCalendarDialog()
                .doOnSubscribe(this::addDisposable)
                .subscribe(event ->{initialDate = false; dateDialogEvent(event);});
    }



    private void dateDialogEvent(DialogEvent event){
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                HistoricalTransactionsFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "TimePicker");
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorUnderline));
        datePickerDialog.setOkColor(getResources().getColor(R.color.colorUnderline));
        datePickerDialog.setCancelColor(getResources().getColor(R.color.colorUnderline));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        if(initialDate){
            getViewModel().setInitialDate(date);
        } else {
            getViewModel().setFinalDate(date);
        }
    }

}

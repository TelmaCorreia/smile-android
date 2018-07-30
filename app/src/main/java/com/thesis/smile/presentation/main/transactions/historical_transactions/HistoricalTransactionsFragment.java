package com.thesis.smile.presentation.main.transactions.historical_transactions;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.thesis.smile.Constants;
import com.thesis.smile.R;
import com.thesis.smile.databinding.FragmentHistoricalTransactionsBinding;
import com.thesis.smile.domain.models.Transaction;
import com.thesis.smile.presentation.base.BaseFragment;
import com.thesis.smile.presentation.main.transactions.historical_transactions.list.HistoricalTransactionAdapter;
import com.thesis.smile.presentation.transaction_details.TransactionDetailsActivity;
import com.thesis.smile.presentation.utils.actions.events.DialogEvent;
import com.thesis.smile.presentation.utils.actions.events.Event;
import com.thesis.smile.presentation.utils.views.CustomItemDecoration;
import com.thesis.smile.presentation.utils.views.DefaultItemDecoration;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;

public class HistoricalTransactionsFragment extends BaseFragment<FragmentHistoricalTransactionsBinding, HistoricalTransactionsViewModel> implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{

    private String type;
    private boolean initialDate = true;
    private RecyclerView.OnScrollListener historicalTransactionsScrollListener = null;

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
                if (i >= 0) {
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
        adapterTypeMenu.setDropDownViewResource(R.layout.layout_spinner_dropdown);
        binding.spType.setAdapter(adapterTypeMenu);

        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    getViewModel().setType(transactionsTypeMenu[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getViewModel().setType(transactionsTypeMenu[0]);
            }
        });

        getViewModel().setType(transactionsTypeMenu[0]);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        HistoricalTransactionAdapter historicalTransactionAdapter = new HistoricalTransactionAdapter(getViewModel().getTransactions(), this::onTransactionSelected);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        DefaultItemDecoration itemDecoration = new DefaultItemDecoration(dividerDrawable);
        binding.historicalTransactions.setLayoutManager(layoutManager);
        binding.historicalTransactions.setAdapter(historicalTransactionAdapter);
        binding.historicalTransactions.addItemDecoration(itemDecoration);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(binding.clFilter);
        constraintSet.constrainPercentWidth(R.id.spPeriod, getViewModel().getUserTypeProsumer()==View.GONE?1.0f:0.5f);
        constraintSet.applyTo(binding.clFilter);

        historicalTransactionsScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!getViewModel().isLoading() && !getViewModel().isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= Constants.PAGE_SIZE) {
                        getViewModel().getTransactions(getViewModel().getType());
                    }
                }
            }
        };

        binding.historicalTransactions.addOnScrollListener(historicalTransactionsScrollListener);


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
            getViewModel().setFromDate(LocalDate.of(year, monthOfYear+1, dayOfMonth));
        } else {
            getViewModel().setFinalDate(date);
            getViewModel().setToDate(LocalDate.of(year, monthOfYear+1, dayOfMonth));
        }
    }



}

package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionsRemote {

    @Expose
    @SerializedName("transactions")
    private List<TransactionRemote> transactions;

    public TransactionsRemote(){}

    public List<TransactionRemote> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRemote> transactions) {
        this.transactions = transactions;
    }
}

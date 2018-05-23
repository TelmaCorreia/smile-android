package com.thesis.smile.data.iota.api.responses;

import com.thesis.smile.domain.models_iota.TransactionIota;

public class CoolTransactionResponse extends ApiResponse {
    private TransactionIota[] transactionIotas;

    public CoolTransactionResponse(TransactionIota[] transactionIotas) {
        this.transactionIotas = transactionIotas;
    }

    public TransactionIota[] getTransactionIotas() {
        return transactionIotas;
    }

    public void setTransactionIotas(TransactionIota[] transactionIotas) {
        this.transactionIotas = transactionIotas;
    }
}

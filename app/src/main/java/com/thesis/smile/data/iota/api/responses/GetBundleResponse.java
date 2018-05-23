package com.thesis.smile.data.iota.api.responses;

import com.thesis.smile.domain.models_iota.TransactionIota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetBundleResponse extends ApiResponse {

    private List<TransactionIota> transactionIotas = new ArrayList<>();

    public GetBundleResponse(jota.dto.response.GetBundleResponse apiResponse) {

        for (jota.model.Transaction transaction : apiResponse.getTransactions()) {

            String hash = transaction.getHash();
            String signatureFragments = transaction.getSignatureFragments();
            String address = transaction.getAddress();
            long value = transaction.getValue();
            String tag = transaction.getTag();
            String obsoleteTag = transaction.getObsoleteTag();
            long timestamp = transaction.getTimestamp();
            long attachmentTimestamp = transaction.getAttachmentTimestamp();
            long attachmentTimestampLowerBound = transaction.getAttachmentTimestampLowerBound();
            long attachmentTimestampUpperBound = transaction.getAttachmentTimestampUpperBound();
            long currentIndex = transaction.getCurrentIndex();
            long lastIndex = transaction.getLastIndex();
            String bundle = transaction.getBundle();
            String trunkTransaction = transaction.getTrunkTransaction();
            String branchTransaction = transaction.getBranchTransaction();
            String nonce = transaction.getNonce();
            Boolean persistence = transaction.getPersistence();

            transactionIotas.add(new TransactionIota(hash, signatureFragments, address, value, tag, obsoleteTag, timestamp, attachmentTimestamp, attachmentTimestampLowerBound, attachmentTimestampUpperBound, currentIndex, lastIndex, bundle, trunkTransaction, branchTransaction, nonce, persistence));
        }
        Collections.reverse(transactionIotas);

        setDuration(apiResponse.getDuration());

    }

    public List<TransactionIota> getTransactionIotas() {
        return transactionIotas;
    }

    public void setTransactionIotas(List<TransactionIota> transactionIotas) {
        this.transactionIotas = transactionIotas;
    }
}
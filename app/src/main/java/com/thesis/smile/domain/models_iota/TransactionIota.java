package com.thesis.smile.domain.models_iota;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionIota implements Parcelable {

    public static final Creator<TransactionIota> CREATOR = new Creator<TransactionIota>() {
        @Override
        public TransactionIota createFromParcel(Parcel in) {
            return new TransactionIota(in);
        }

        @Override
        public TransactionIota[] newArray(int size) {
            return new TransactionIota[size];
        }
    };
    private String hash;
    private String signatureFragments;
    private String address;
    private long value;
    private String tag;
    private String obsoleteTag;
    private long timestamp;
    private long attachmentTimestamp;
    private long attachmentTimestampLowerBound;
    private long attachmentTimestampUpperBound;
    private long currentIndex;
    private long lastIndex;
    private String bundle;
    private String trunkTransaction;
    private String branchTransaction;
    private String nonce;
    private Boolean persistence;

    public TransactionIota(String hash, String signatureFragments, String address, long value, String tag, String obsoleteTag, long timestamp,
                           long attachmentTimestamp, long attachmentTimestampLowerBound, long attachmentTimestampUpperBound, long currentIndex,
                           long lastIndex, String bundle, String trunkTransaction, String branchTransaction, String nonce, Boolean persistence) {

        this.hash = hash;
        this.signatureFragments = signatureFragments;
        this.address = address;
        this.value = value;
        this.tag = tag;
        this.obsoleteTag = obsoleteTag;
        this.timestamp = timestamp;
        this.attachmentTimestamp = attachmentTimestamp;
        this.attachmentTimestampLowerBound = attachmentTimestampLowerBound;
        this.attachmentTimestampUpperBound = attachmentTimestampUpperBound;
        this.currentIndex = currentIndex;
        this.lastIndex = lastIndex;
        this.bundle = bundle;
        this.trunkTransaction = trunkTransaction;
        this.branchTransaction = branchTransaction;
        this.nonce = nonce;
        this.persistence = persistence;
    }

    public TransactionIota(String address, long value, String tag, long timestamp) {
        this.address = address;
        this.value = value;
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public TransactionIota(Parcel in) {
        hash = in.readString();
        signatureFragments = in.readString();
        address = in.readString();
        value = in.readLong();
        tag = in.readString();
        obsoleteTag = in.readString();
        timestamp = in.readLong();
        attachmentTimestamp = in.readLong();
        attachmentTimestampLowerBound = in.readLong();
        attachmentTimestampUpperBound = in.readLong();
        currentIndex = in.readLong();
        lastIndex = in.readLong();
        bundle = in.readString();
        trunkTransaction = in.readString();
        branchTransaction = in.readString();
        nonce = in.readString();
        persistence = in.readInt() != 0;
    }

    public long getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(long lastIndex) {
        this.lastIndex = lastIndex;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSignatureFragments() {
        return signatureFragments;
    }

    public void setSignatureFragments(String signatureFragments) {
        this.signatureFragments = signatureFragments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getObsoleteTag() {
        return obsoleteTag;
    }

    public void setObsoleteTag(String obsoleteTag) {
        this.obsoleteTag = obsoleteTag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getAttachmentTimestamp() {
        return attachmentTimestamp;
    }

    public void setAttachmentTimestamp(long attachmentTimestamp) {
        this.attachmentTimestamp = attachmentTimestamp;
    }

    public long getAttachmentTimestampLowerBound() {
        return attachmentTimestampLowerBound;
    }

    public void setAttachmentTimestampLowerBound(long attachmentTimestampLowerBound) {
        this.attachmentTimestampLowerBound = attachmentTimestampLowerBound;
    }

    public long getAttachmentTimestampUpperBound() {
        return attachmentTimestampUpperBound;
    }

    public void setAttachmentTimestampUpperBound(long attachmentTimestampUpperBound) {
        this.attachmentTimestampUpperBound = attachmentTimestampUpperBound;
    }

    public long getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(long currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getTrunkTransaction() {
        return trunkTransaction;
    }

    public void setTrunkTransaction(String trunkTransaction) {
        this.trunkTransaction = trunkTransaction;
    }

    public String getBranchTransaction() {
        return branchTransaction;
    }

    public void setBranchTransaction(String branchTransaction) {
        this.branchTransaction = branchTransaction;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Boolean getPersistence() {
        return persistence;
    }

    public void setPersistence(Boolean persistence) {
        this.persistence = persistence;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hash);
        dest.writeString(signatureFragments);
        dest.writeString(address);
        dest.writeLong(value);
        dest.writeString(tag);
        dest.writeString(obsoleteTag);
        dest.writeLong(timestamp);
        dest.writeLong(attachmentTimestamp);
        dest.writeLong(attachmentTimestampLowerBound);
        dest.writeLong(attachmentTimestampUpperBound);
        dest.writeLong(currentIndex);
        dest.writeLong(lastIndex);
        dest.writeString(bundle);
        dest.writeString(trunkTransaction);
        dest.writeString(branchTransaction);
        dest.writeString(nonce);
        dest.writeInt((persistence != null ? persistence : false) ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}

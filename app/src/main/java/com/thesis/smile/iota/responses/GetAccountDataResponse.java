package com.thesis.smile.iota.responses;

import com.thesis.smile.domain.models_iota.Address;
import com.thesis.smile.domain.models_iota.Transfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jota.error.ArgumentException;
import jota.model.Bundle;
import jota.model.Transaction;
import jota.utils.Checksum;

public class GetAccountDataResponse extends ApiResponse {

    private List<String> attachedAddresses = new ArrayList<>();
    private List<Transfer> transfers = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

    private long totalValue;
    private long timestamp;
    private String address;
    private String hash;
    private Boolean persistence;
    private long value;
    private String tag;
    private String message = "";
    private String destinationAddress;
    private long balance;

    public GetAccountDataResponse(jota.dto.response.GetAccountDataResponse apiResponse) throws ArgumentException {

        attachedAddresses = apiResponse.getAddresses();
        Collections.reverse(attachedAddresses);

        Bundle[] transferBundle = apiResponse.getTransfers();

        if (transferBundle != null) {
            for (Bundle aTransferBundle : transferBundle) {

                totalValue = 0;

                for (Transaction trx : aTransferBundle.getTransactions()) {

                    address = trx.getAddress();
                    persistence = trx.getPersistence();
                    value = trx.getValue();

                    if (value != 0 && attachedAddresses.contains(Checksum.addChecksum(address)))
                        totalValue += value;

                    if (trx.getCurrentIndex() == 0) {
                        timestamp = trx.getAttachmentTimestamp() / 1000;
                        tag = trx.getTag();
                        destinationAddress = address;
                        hash = trx.getHash();
                    }

                    // check if sent transaction
                    if (attachedAddresses.contains(Checksum.addChecksum(address))) {
                        boolean isRemainder = (trx.getCurrentIndex() == trx.getLastIndex()) && trx.getLastIndex() != 0;
                        if (value < 0 && !isRemainder) {

                            if (addresses.contains(new Address(Checksum.addChecksum(address), false)))
                                addresses.remove(new Address(Checksum.addChecksum(address), false));

                            if (!addresses.contains(new Address(Checksum.addChecksum(address), true)))
                                addresses.add(new Address(Checksum.addChecksum(address), true));
                        } else {
                            if (!addresses.contains(new Address(Checksum.addChecksum(address), true)) &&
                                    !addresses.contains(new Address(Checksum.addChecksum(address), false)))
                                addresses.add(new Address(Checksum.addChecksum(address), false));
                        }
                    }
                }

                transfers.add(new Transfer(timestamp, destinationAddress, hash, persistence, totalValue, message, tag));

            }

            // sort the addresses
            final Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < attachedAddresses.toArray().length; ++i)
                map.put(attachedAddresses.get(i), i);
            Collections.sort(addresses, new Comparator<Address>() {
                @Override
                public int compare(Address add1, Address add2) {
                    return map.get(add1.getAddress()) - map.get(add2.getAddress());
                }
            });

            Collections.sort(transfers);

            setBalance(apiResponse.getBalance());
            setDuration(apiResponse.getDuration());
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

}

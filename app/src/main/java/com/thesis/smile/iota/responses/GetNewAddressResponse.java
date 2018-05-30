package com.thesis.smile.iota.responses;

import java.util.ArrayList;
import java.util.List;

public class GetNewAddressResponse extends ApiResponse {

    private List<String> addresses = new ArrayList<>();

    public GetNewAddressResponse(jota.dto.response.GetNewAddressResponse apiResponse) {
        addresses = apiResponse.getAddresses();
        setDuration(apiResponse.getDuration());
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

}

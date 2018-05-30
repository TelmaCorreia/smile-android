package com.thesis.smile.iota;


import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.responses.ApiResponse;

interface ApiProvider {
    ApiResponse processRequest(ApiRequest apiRequest);
}

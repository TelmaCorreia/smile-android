package com.thesis.smile.data.iota.api;


import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;

interface ApiProvider {
    ApiResponse processRequest(ApiRequest apiRequest);
}

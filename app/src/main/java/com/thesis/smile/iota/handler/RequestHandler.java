package com.thesis.smile.iota.handler;

import com.thesis.smile.iota.requests.ApiRequest;
import com.thesis.smile.iota.responses.ApiResponse;

public interface RequestHandler {
    Class<? extends ApiRequest> getType();
    ApiResponse handle(ApiRequest request);
}

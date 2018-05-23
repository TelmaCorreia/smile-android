package com.thesis.smile.data.iota.api.handler;

import com.thesis.smile.data.iota.api.requests.ApiRequest;
import com.thesis.smile.data.iota.api.responses.ApiResponse;

public interface RequestHandler {
    Class<? extends ApiRequest> getType();
    ApiResponse handle(ApiRequest request);
}

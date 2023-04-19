package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public  class RequestRootResponse extends RequestRootBase {

    RequestReponse request;

    public RequestRootResponse(){

    }

    public RequestRootResponse(String name, RequestReponse request) {
        super(name);
        this.request = request;
    }


    public RequestReponse getRequest() {
        return request;
    }

    public void setRequest(RequestReponse request) {
        this.request = request;
    }
}

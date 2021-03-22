package com.romaingoguet.android.blood.data.models;

import retrofit2.Response;

public class ResponseWrapper<T> {

    private boolean error;
    private Throwable t;
    private Response<T> response;

    public ResponseWrapper(Throwable t) {
        this.t = t;
        this.error = true;
    }

    public ResponseWrapper(Response<T> response) {
        this.error = false;
        this.response = response;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }
}

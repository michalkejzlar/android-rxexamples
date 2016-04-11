package cz.michalkejzlar.rxexamples;

import com.google.gson.annotations.Expose;

public class HttpError {

    @Expose
    public final String value;

    @Expose
    public final String message;

    public HttpError(String value, String message) {
        this.value = value;
        this.message = message;
    }
}

package cz.michalkejzlar.rxexamples.network;

import cz.michalkejzlar.rxexamples.HttpError;

public class InvalidInputException extends Exception {

    private final String errorKey;

    public InvalidInputException(HttpError error) {
        super(error.message);
        this.errorKey = error.value;
    }

    public InvalidInputException(String detailMessage) {
        super(detailMessage);
        this.errorKey = "unknown";
    }
}

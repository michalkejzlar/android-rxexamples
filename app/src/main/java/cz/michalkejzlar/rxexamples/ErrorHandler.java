package cz.michalkejzlar.rxexamples;

public interface ErrorHandler {

    Throwable handleError(Throwable e);
}

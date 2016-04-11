package cz.michalkejzlar.rxexamples;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.functions.Func1;

public final class ErrorTransformer<T> implements Observable.Transformer<T, T> {

    private final ErrorHandler errorHandler;

    public ErrorTransformer(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public Observable<T> call(Observable<T> sourceObservable) {
        return sourceObservable
                .onErrorResumeNext(errorFunction());
    }

    @NonNull
    private Func1<Throwable, Observable<? extends T>> errorFunction() {
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                return Observable.error(errorHandler.handleError(throwable));
            }
        };
    }

}

package cz.michalkejzlar.rxexamples.network;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import com.google.gson.Gson;
import cz.michalkejzlar.rxexamples.ErrorHandler;
import cz.michalkejzlar.rxexamples.HttpError;
import cz.michalkejzlar.rxexamples.R;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

import java.io.IOException;

class NetworkErrorHandler implements ErrorHandler {

    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int SERVER_ERROR = 500;

    private final Gson gson;
    private final Resources resources;

    public NetworkErrorHandler(Gson gson, Resources resources) {
        this.gson = gson;
        this.resources = resources;
    }

    @Override
    public Throwable handleError(Throwable e) {

        if (e instanceof HttpException) {
            return handleHttpException((HttpException) e);
        }

        if (e instanceof IOException) {
            return handleIOException();
        }

        throw new IllegalArgumentException("Cannot handle exception, sorry!");
    }

    private HttpError getHttpError(HttpException exception) throws IOException {
        ResponseBody errorBody = exception.response().errorBody();
        return gson.fromJson(errorBody.string(), HttpError.class);
    }

    private String res(@StringRes int resId) {
        return resources.getString(resId);
    }

    /**
     * Handle http exceptions returned from server. (usually domain specific exceptions)
     * @param e HTTP exception returned from server
     * @return Human readable exception
     */
    private Throwable handleHttpException(HttpException e) {
        switch (e.code()) {
            case BAD_REQUEST:
                return handleBadRequests(e);
            case UNAUTHORIZED:
                return new IllegalStateException("Not implemented yet");
            case SERVER_ERROR:
                return new IllegalStateException("Not implemented yet");
            default:
                return new IllegalStateException("Not implemented yet");
        }
    }

    private Throwable handleBadRequests(HttpException httpException) {
        try {
            HttpError error = getHttpError(httpException);
            return new InvalidInputException(error);
        } catch (IOException e) {
            return new InvalidInputException(res(R.string.network_error_bad_request));
        }
    }

    private Throwable handleIOException() {
        return new IOException(res(R.string.network_error_io));
    }
}

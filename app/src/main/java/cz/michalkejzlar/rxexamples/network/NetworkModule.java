package cz.michalkejzlar.rxexamples.network;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.michalkejzlar.rxexamples.ErrorHandler;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Module
public class NetworkModule {

    private final Application application;

    public NetworkModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public GithubRepository providesNetworkDataStorage(Retrofit retrofit, ErrorHandler errorHandler) {
        return new GithubRepository(retrofit, errorHandler);
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(GithubAPI.ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    public ErrorHandler provideNetworkErrorHandler(Gson gson) {
        return new NetworkErrorHandler(gson, application.getResources());
    }
}

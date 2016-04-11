package cz.michalkejzlar.rxexamples;

import android.app.Application;
import cz.michalkejzlar.rxexamples.network.DaggerNetworkComponent;
import cz.michalkejzlar.rxexamples.network.NetworkComponent;
import cz.michalkejzlar.rxexamples.network.NetworkModule;

public class RxApplication extends Application {

    private NetworkComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(this))
                .build();
    }

    public NetworkComponent component() {
        return component;
    }

}

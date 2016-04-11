package cz.michalkejzlar.rxexamples.network;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    GithubRepository repo();
}

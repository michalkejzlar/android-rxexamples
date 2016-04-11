package cz.michalkejzlar.rxexamples.network;

import cz.michalkejzlar.rxexamples.Contributor;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

interface GithubAPI {

    String ENDPOINT = "https://api.github.com/";

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(@Path("owner") String owner, @Path("repo") String repo);

}

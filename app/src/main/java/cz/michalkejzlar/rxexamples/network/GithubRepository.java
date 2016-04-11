package cz.michalkejzlar.rxexamples.network;

import cz.michalkejzlar.rxexamples.Contributor;
import cz.michalkejzlar.rxexamples.ErrorHandler;
import cz.michalkejzlar.rxexamples.ErrorTransformer;
import retrofit2.Retrofit;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

public final class GithubRepository {

    private final GithubAPI api;
    private final ErrorHandler errorHandler;

    @Inject
    public GithubRepository(Retrofit retrofit, ErrorHandler errorHandler) {
        api = retrofit.create(GithubAPI.class);
        this.errorHandler = errorHandler;
    }

    public Observable<List<Contributor>> listContributors(String owner, String repo) {
        return api.contributors(owner, repo)
                .compose(new ErrorTransformer<List<Contributor>>(errorHandler));
    }

}

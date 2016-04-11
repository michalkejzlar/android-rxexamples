package cz.michalkejzlar.rxexamples;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import cz.michalkejzlar.rxexamples.network.GithubRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.List;
import java.util.Locale;

public final class MainActivity extends AppCompatActivity {

    protected GithubRepository dataRepository;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectInstances();
        setContentView(R.layout.activity_main);
    }

    private void injectInstances() {
        dataRepository = ((RxApplication) getApplication()).component().repo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listContributors();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    protected void listContributors() {
        Subscription subscription = dataRepository.listContributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e);
                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        showContributors(contributors);
                    }
                });
        subscriptions.add(subscription);
    }

    void showContributors(List<Contributor> contributors) {
        final TextView contributorsTxt = (TextView) findViewById(R.id.contributors);

        if (contributorsTxt == null) {
            throw new RuntimeException("Layout does not provide widget to render results!");
        }

        if (contributors.isEmpty()) {
            contributorsTxt.setText(getString(R.string.no_contributors));
            return;
        }

        String txt = "";
        for (Contributor c : contributors) {
            txt += String.format(Locale.ENGLISH, "%s; %d contributions\n", c.login, c.contributions);
        }
        contributorsTxt.setText(txt);
    }

    void showError(Throwable e) {
        Snackbar.make(findViewById(android.R.id.content),
                e.getMessage(),
                Snackbar.LENGTH_LONG).show();
    }

}

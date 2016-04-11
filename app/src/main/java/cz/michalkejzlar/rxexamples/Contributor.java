package cz.michalkejzlar.rxexamples;

import com.google.gson.annotations.Expose;

public class Contributor {

    @Expose
    public final String login;

    @Expose
    public final int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}

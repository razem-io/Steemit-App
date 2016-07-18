package io.razem.steemitapp.model;

import java.io.Serializable;

/**
 * Created by julia on 16.07.2016.
 */
public class Discussion implements Serializable {
    private String title;
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "title='" + title + '\'' +
                '}';
    }
}

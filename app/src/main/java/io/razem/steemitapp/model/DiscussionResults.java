package io.razem.steemitapp.model;

import java.util.List;

/**
 * Created by julia on 16.07.2016.
 */
public class DiscussionResults {
    private int id;
    private List<Discussion> result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Discussion> getResult() {
        return result;
    }

    public void setResult(List<Discussion> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DiscussionResults{" +
                "id=" + id +
                ", result=" + result +
                '}';
    }
}

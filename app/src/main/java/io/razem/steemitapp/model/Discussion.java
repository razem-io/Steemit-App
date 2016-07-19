package io.razem.steemitapp.model;

import java.io.Serializable;

import io.razem.steemitapp.controller.Markdown;

/**
 * Created by julia on 16.07.2016.
 */
public class Discussion implements Serializable {
    private String title;
    private String body;
    private String bodyShort;
    private String previewImageUrl;
    private Integer children;
    private Integer netVotes;
    private String pendingPayoutValue;
    private String totalPendingPayoutValue;

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

        this.bodyShort = Markdown.toText(body);
        this.previewImageUrl = Markdown.getFirstImageUrl(body);
    }

    public String getBodyShort() {
        return bodyShort;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getPendingPayoutValue() {
        return pendingPayoutValue;
    }

    public void setPendingPayoutValue(String pendingPayoutValue) {
        this.pendingPayoutValue = pendingPayoutValue;
    }

    public String getTotalPendingPayoutValue() {
        return totalPendingPayoutValue;
    }

    public void setTotalPendingPayoutValue(String totalPendingPayoutValue) {
        this.totalPendingPayoutValue = totalPendingPayoutValue;
    }

    public Integer getNetVotes() {
        return netVotes;
    }

    public void setNetVotes(Integer netVotes) {
        this.netVotes = netVotes;
    }
}

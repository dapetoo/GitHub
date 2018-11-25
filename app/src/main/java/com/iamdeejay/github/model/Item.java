package com.iamdeejay.github.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    @SerializedName("avatar")
    @Expose
    private String avatarUrl;

    public Item(String login, String htmlUrl, String avatarUrl) {
        this.login = login;
        this.htmlUrl = htmlUrl;
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

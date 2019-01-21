package com.erproject.busgo.data.data.simpleData;

import com.google.gson.annotations.SerializedName;

public class InterestingPhrase {
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

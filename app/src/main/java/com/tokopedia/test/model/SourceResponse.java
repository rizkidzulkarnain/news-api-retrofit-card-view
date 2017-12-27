package com.tokopedia.test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 27.03.2017.
 */
public class SourceResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("sources")
    private List<Source2> sources;

    public SourceResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source2> getSources() {
        return sources;
    }

    public void setSources(List<Source2> sources) {
        this.sources = sources;
    }
}

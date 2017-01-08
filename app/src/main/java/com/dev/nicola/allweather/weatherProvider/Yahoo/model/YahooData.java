package com.dev.nicola.allweather.provider.Yahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with http://www.jsonschema2pojo.org/
 */

public class YahooData {

    @SerializedName("query")
    @Expose
    private Query query;


    public Query getQuery() {
        return query;
    }

}

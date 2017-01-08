package com.dev.nicola.allweather.provider.Yahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("channel")
    @Expose
    private Channel channel;


    public Channel getChannel() {
        return channel;
    }

}

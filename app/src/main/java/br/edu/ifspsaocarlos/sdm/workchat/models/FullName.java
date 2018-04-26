package br.edu.ifspsaocarlos.sdm.workchat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zigui on 26/04/2018.
 */

public class FullName implements Serializable {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

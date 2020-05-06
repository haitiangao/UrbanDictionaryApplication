
package com.example.urbandictionaryapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefinitionResults {

    @SerializedName("list")
    @Expose
    private java.util.List<Definition> list = null;

    public java.util.List<Definition> getList() {
        return list;
    }

    public void setList(java.util.List<Definition> list) {
        this.list = list;
    }

}

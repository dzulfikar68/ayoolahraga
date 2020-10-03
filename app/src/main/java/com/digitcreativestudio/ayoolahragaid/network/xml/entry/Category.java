package com.digitcreativestudio.ayoolahragaid.network.xml.entry;

import org.simpleframework.xml.Attribute;

public class Category {
    @Attribute
    private String term;

    @Attribute
    private String scheme;

    public String getTerm() {
        return term;
    }
}

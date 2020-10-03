package com.digitcreativestudio.ayoolahragaid.network.xml;

import org.simpleframework.xml.Attribute;

public class Category {
    @Attribute
    private String term;

    public String getTerm() {
        return term;
    }
}

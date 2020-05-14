package com.digitcreativestudio.ayoolahraga.network.xml;

import org.simpleframework.xml.Attribute;

public class Enclosure {
    @Attribute(required = false)
    private String url;

    public String getUrl() {
        return url;
    }
}

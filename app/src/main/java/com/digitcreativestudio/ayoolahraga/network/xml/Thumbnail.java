package com.digitcreativestudio.ayoolahraga.network.xml;

import org.simpleframework.xml.Attribute;

public class Thumbnail {
    @Attribute(required = false)
    private String media;

    @Attribute(required = false)
    private String url;

    @Attribute(required = false)
    private String height;

    @Attribute(required = false)
    private String width;

    public String getUrl() {
        return url;
    }
}

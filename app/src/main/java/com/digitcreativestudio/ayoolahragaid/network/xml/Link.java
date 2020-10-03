package com.digitcreativestudio.ayoolahragaid.network.xml;

import org.simpleframework.xml.Attribute;

public class Link {
    public static String VAL_REL = "alternate";

    @Attribute(required = false)
    private String rel;

    @Attribute(required = false)
    private String type;

    @Attribute(required = false)
    private String href;

    @Attribute(required = false)
    private String title;

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}

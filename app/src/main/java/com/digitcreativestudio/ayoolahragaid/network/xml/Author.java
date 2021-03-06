package com.digitcreativestudio.ayoolahragaid.network.xml;

import org.simpleframework.xml.Element;

public class Author {
    @Element(required = false)
    private String name;

    @Element(required = false)
    private String uri;

    @Element(required = false)
    private String email;

    @Element(required = false)
    private String image;
}

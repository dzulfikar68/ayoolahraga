package com.digitcreativestudio.ayoolahraga.network.xml;

import org.simpleframework.xml.Attribute;

public class Generator {
    @Attribute(required = false)
    private String version;

    @Attribute(required = false)
    private String uri;
}

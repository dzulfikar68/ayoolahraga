package com.digitcreativestudio.ayoolahragaid.network.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "title", strict = false)
public class Title{
    @Attribute(required = false)
    private String type;
}

package com.digitcreativestudio.ayoolahraga.network.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Item {
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String link;
    @Element(required = false)
    private Enclosure enclosure;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }
}

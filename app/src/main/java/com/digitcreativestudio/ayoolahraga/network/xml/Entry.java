package com.digitcreativestudio.ayoolahraga.network.xml;

import com.digitcreativestudio.ayoolahraga.network.xml.entry.Category;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

//@Root(name="entry", strict=false)
public class Entry {
    @Element(required = false)
    private String id;

    public String getId() {
        return id;
    }

    @Element(required = false)
    private String published;

    public String getPublished() {
        return published;
    }

    @Element(required = false)
    private String updated;

    @ElementList(name = "category", inline = true, required = false)
    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    //    @ElementList(name = "title", inline = true, required = false)
    @Element(required = false)
//    @Namespace(reference = "http://www.w3.org/2005/Atom")
    private String title;

    public String getTitle() {
        return title;
    }

//    @ElementList(inline = true, required = false)
    @Element(required = false)
    private String summary;

    public String getSummary() {
        return summary;
    }

    @ElementList(inline = true, required = false)
    private List<Link> link;

    public List<Link> getLink() {
        return link;
    }

    @Element(name = "author")
    private Author author;

    @ElementList(inline = true, required = false)
    private List<Thumbnail> thumbnail;

    public List<Thumbnail> getThumbnail() {
        return thumbnail;
    }

    @Element(name = "total")
    private String total;
}

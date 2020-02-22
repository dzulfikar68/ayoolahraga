package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.network.xml.*;
import org.simpleframework.xml.*;

import java.util.List;

//@Root(name="feed", strict=false)
public class BlogResponse {
    @ElementList(inline = true, required = false)
    List<Entry> entry;

    @Element(name = "id")
    String id;

    @Element(name = "updated")
    String updated;

//    @ElementListUnion({
//            @ElementList(inline = true, required = false, type = TestCase.class, name = "TestCase"),
//            @ElementList(inline = true, required = false, type = TestSuite.class, name = "TestSuite")
//    })
//    @Element(name = "title")
    @ElementList(name = "title", inline = true, required = false)
    List<Title> title;

    @Element(name = "subtitle")
    String subtitle;

    @ElementList(inline = true, required = false)
    List<Link> link;

    @ElementList(inline = true, required = false)
    List<Category> category;

    @Element(name = "author")
    Author author;

    @ElementList(inline = true, required = false)
    List<Generator> generator;

    @Element(name = "totalResults")
    Author totalResults;

    @Element(name = "startIndex")
    Author startIndex;

    @Element(name = "itemsPerPage")
    Author itemsPerPage;

    public List<Entry> getEntry() {
        return entry;
    }

    public String getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public List<Title> getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<Link> getLink() {
        return link;
    }

    public List<Category> getCategory() {
        return category;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Generator> getGenerator() {
        return generator;
    }

    public Author getTotalResults() {
        return totalResults;
    }

    public Author getStartIndex() {
        return startIndex;
    }

    public Author getItemsPerPage() {
        return itemsPerPage;
    }
}

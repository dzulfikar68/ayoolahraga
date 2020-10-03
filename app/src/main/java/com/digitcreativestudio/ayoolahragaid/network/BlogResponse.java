package com.digitcreativestudio.ayoolahragaid.network;

import com.digitcreativestudio.ayoolahragaid.network.xml.Author;
import com.digitcreativestudio.ayoolahragaid.network.xml.Category;
import com.digitcreativestudio.ayoolahragaid.network.xml.Entry;
import com.digitcreativestudio.ayoolahragaid.network.xml.Generator;
import com.digitcreativestudio.ayoolahragaid.network.xml.Link;
import com.digitcreativestudio.ayoolahragaid.network.xml.Title;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

//@Root(name="feed", strict=false)
public class BlogResponse {
    @ElementList(inline = true, required = false)
    private
    List<Entry> entry;

    @Element(name = "id")
    private
    String id;

    @Element(name = "updated")
    private String updated;

//    @ElementListUnion({
//            @ElementList(inline = true, required = false, type = TestCase.class, name = "TestCase"),
//            @ElementList(inline = true, required = false, type = TestSuite.class, name = "TestSuite")
//    })
//    @Element(name = "title")
    @ElementList(name = "title", inline = true, required = false)
    private List<Title> title;

    @Element(name = "subtitle")
    private String subtitle;

    @ElementList(inline = true, required = false)
    private List<Link> link;

    @ElementList(inline = true, required = false)
    private List<Category> category;

    @Element(name = "author")
    private Author author;

    @ElementList(inline = true, required = false)
    private List<Generator> generator;

    @Element(name = "totalResults")
    private Author totalResults;

    @Element(name = "startIndex")
    private Author startIndex;

    @Element(name = "itemsPerPage")
    private Author itemsPerPage;

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

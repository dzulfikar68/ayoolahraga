package com.digitcreativestudio.ayoolahragaid.network.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Channel {

    @ElementList(inline = true, required = false)
    private List<Item> itemList;

    public List<Item> getItemList() {
        return itemList;
    }

}

package com.digitcreativestudio.ayoolahraga.network;

import com.digitcreativestudio.ayoolahraga.network.xml.Channel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class NewBlogResponse {

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}

package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.model.Link;
import com.howtographql.hackernews.model.LinkFilter;
import java.util.List;

public interface LinkRepository {

    Link findById(String id);

    List<Link> getAllLinks(LinkFilter filter, int skip, int first);

    void saveLink(Link link);
}

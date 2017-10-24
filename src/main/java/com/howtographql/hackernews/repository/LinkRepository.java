package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.model.Link;
import java.util.List;

public interface LinkRepository {

    Link findById(String id);

    List<Link> getAllLinks();

    void saveLink(Link link);
}

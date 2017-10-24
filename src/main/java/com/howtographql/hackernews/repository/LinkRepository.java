package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.model.Link;
import java.util.List;

public interface LinkRepository {

    List<Link> getAllLinks();

    void saveLink(Link link);
}

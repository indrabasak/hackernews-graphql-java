package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.model.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code LinkRepository} manages link persistence in memory. It isolatse the
 * concern of saving and loading links from the storage. This also makes future
 * extensions and refactoring a lot easier.
 */
public class LinkInMemoryRepository implements LinkRepository {

    private final List<Link> links;

    public Link findById(String id) {
        return links.stream().filter(
                l -> l.getId().equals(id)).findFirst().get();
    }

    public LinkInMemoryRepository() {
        links = new ArrayList<>();
        //add some links to start off with
        links.add(new Link("http://howtographql.com",
                "Your favorite GraphQL page", "ex1"));
        links.add(new Link("http://graphql.org/learn/", "The official docks",
                "ex2"));
    }

    public List<Link> getAllLinks() {
        return links;
    }

    public void saveLink(Link link) {
        links.add(link);
    }
}

package com.howtographql.hackernews.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.howtographql.hackernews.model.Link;
import com.howtographql.hackernews.model.LinkFilter;
import com.howtographql.hackernews.repository.LinkRepository;
import java.util.List;

/**
 * {@code Query}  models behavior, as it contains the resolver for the allLinks
 * query.
 */
public class Query implements GraphQLQueryResolver {

    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks(LinkFilter filter, Number skip, Number first) {
        return linkRepository.getAllLinks(filter, skip.intValue(),
                first.intValue());
    }
}

package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.model.Link;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class LinkMongoDBRepository implements LinkRepository {
    private final MongoCollection<Document> links;

    public LinkMongoDBRepository(MongoCollection<Document> links) {
        this.links = links;
    }

    public Link findById(String id) {
        Document doc = links.find(eq("_id", new ObjectId(id))).first();
        return link(doc);
    }

    public List<Link> getAllLinks() {
        System.out.println("-------------- LinkMongoDBRepository.getAllLinks()");
        List<Link> allLinks = new ArrayList<>();
        for (Document doc : links.find()) {
            Link lnk = link(doc);
            System.out.println(lnk);
            allLinks.add(lnk);
        }
        return allLinks;
    }

    public void saveLink(Link link) {
        System.out.println("-------------- LinkMongoDBRepository.saveLink()");
        Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        doc.append("postedBy", link.getUserId());
        links.insertOne(doc);
    }

    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"),
                doc.getString("postedBy"));
    }
}

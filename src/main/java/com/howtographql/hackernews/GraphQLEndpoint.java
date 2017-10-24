package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.howtographql.hackernews.model.User;
import com.howtographql.hackernews.repository.LinkMongoDBRepository;
import com.howtographql.hackernews.repository.LinkRepository;
import com.howtographql.hackernews.repository.UserRepository;
import com.howtographql.hackernews.resolver.LinkResolver;
import com.howtographql.hackernews.resolver.Mutation;
import com.howtographql.hackernews.resolver.Query;
import com.howtographql.hackernews.resolver.SigninResolver;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code GraphQLEndpoint} represents the servlet which acts as the GraphQL
 * endpoint.
 */
@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepository linkRepository;

    private static final UserRepository userRepository;

    static {
        //Change to `new MongoClient("mongodb://<host>:<port>/hackernews")`
        //if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
        linkRepository =
                new LinkMongoDBRepository(mongo.getCollection("links"));
        userRepository = new UserRepository(mongo.getCollection("users"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    /**
     * Registers resolvers with the {@code SchemaParser}
     *
     * @return
     */
    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository),
                        new Mutation(linkRepository, userRepository),
                        new SigninResolver(),
                        new LinkResolver(userRepository))
                .build()
                .makeExecutableSchema();
    }

    /**
     * Checks if the Authorization header is present and if so,
     * trim the Bearer prefix and use the remainder as an id to fetch the user
     * by. The user will then be stored in the custom context.
     * AuthContext will be accessible to all resolvers that need it.
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
            Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }
}
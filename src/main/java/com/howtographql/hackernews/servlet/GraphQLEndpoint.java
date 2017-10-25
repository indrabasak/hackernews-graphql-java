package com.howtographql.hackernews.servlet;

import com.coxautodev.graphql.tools.SchemaParser;
import com.howtographql.hackernews.schema.Scalars;
import com.howtographql.hackernews.error.CustomGraphQLErrorHandler;
import com.howtographql.hackernews.model.User;
import com.howtographql.hackernews.model.VoteResolver;
import com.howtographql.hackernews.repository.LinkMongoDBRepository;
import com.howtographql.hackernews.repository.LinkRepository;
import com.howtographql.hackernews.repository.UserRepository;
import com.howtographql.hackernews.repository.VoteRepository;
import com.howtographql.hackernews.resolver.LinkResolver;
import com.howtographql.hackernews.resolver.Mutation;
import com.howtographql.hackernews.resolver.Query;
import com.howtographql.hackernews.resolver.SigninResolver;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLErrorHandler;
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

    private static final VoteRepository voteRepository;

    static {
        //Change to `new MongoClient("mongodb://<host>:<port>/hackernews")`
        //if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
        linkRepository =
                new LinkMongoDBRepository(mongo.getCollection("links"));
        userRepository = new UserRepository(mongo.getCollection("users"));
        voteRepository = new VoteRepository(mongo.getCollection("votes"));
    }

    private CustomGraphQLErrorHandler errorHandler;

    public GraphQLEndpoint() {
        super(buildSchema());
        errorHandler = new CustomGraphQLErrorHandler();
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
                        new Mutation(linkRepository, userRepository,
                                voteRepository),
                        new SigninResolver(),
                        new LinkResolver(userRepository),
                        new VoteResolver(linkRepository, userRepository))
                .scalars(Scalars.dateTime) //register the new scalar
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
        System.out.println(
                "---------------------- GraphQLEndpoint.createContext()");
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        System.out.println("user:" + user);
        return new AuthContext(user, request, response);
    }

    @Override
    protected GraphQLErrorHandler getGraphQLErrorHandler() {
        return errorHandler;
    }
}
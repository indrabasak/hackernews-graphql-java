package com.howtographql.hackernews.servlet;

import com.howtographql.hackernews.model.User;
import graphql.servlet.GraphQLContext;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthContext extends GraphQLContext {

    private final User user;

    public AuthContext(User user, Optional<HttpServletRequest> request,
            Optional<HttpServletResponse> response) {
        super(request, response);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

package com.howtographql.hackernews.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ExceptionWhileDataFetching;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;
import java.util.List;
import java.util.Map;

public class SanitizedError extends ExceptionWhileDataFetching {

    public SanitizedError(ExceptionWhileDataFetching inner) {
        super(ExecutionPath.fromList(inner.getPath()), inner.getException(),
                inner.getLocations().get(0));
    }

    @JsonIgnore
    @Override
    public List<Object> getPath() {
        return super.getPath();
    }

    @JsonIgnore
    @Override
    public Throwable getException() {
        return super.getException();
    }

    @JsonIgnore
    @Override
    public List<SourceLocation> getLocations() {
        return super.getLocations();
    }

    @JsonIgnore
    @Override
    public Map<String, Object> getExtensions() {
        return super.getExtensions();
    }
}

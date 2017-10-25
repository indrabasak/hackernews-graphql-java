[![Build Status][travis-badge]][travis-badge-url]

![](./img/graphql.png)

GraphQL Example
=================
Examples related to my answers in [stackoverflow](https://stackoverflow.com/users/8012379/indra-basak)

### Build
Execute the following command from the parent directory:
```
mvn clean install
```

### Run

```
ibasa-mb-37434:hackernews-graphql-java indra.basak$ mvn jetty:run
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building hackernews-graphql-java Maven Webapp 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> jetty-maven-plugin:9.4.6.v20170531:run (default-cli) > test-compile @ hackernews-graphql-java >>>
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ hackernews-graphql-java ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.5.1:compile (default-compile) @ hackernews-graphql-java ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ hackernews-graphql-java ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/indra.basak/Development/examples/hackernews-graphql-java/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.5.1:testCompile (default-testCompile) @ hackernews-graphql-java ---
[INFO] No sources to compile
[INFO] 
[INFO] <<< jetty-maven-plugin:9.4.6.v20170531:run (default-cli) < test-compile @ hackernews-graphql-java <<<
[INFO] 
[INFO] --- jetty-maven-plugin:9.4.6.v20170531:run (default-cli) @ hackernews-graphql-java ---
[INFO] Configuring Jetty for project: hackernews-graphql-java Maven Webapp
[INFO] webAppSourceDirectory not set. Trying src/main/webapp
[INFO] Reload Mechanic: automatic
[INFO] Classes = /Users/indra.basak/Development/examples/hackernews-graphql-java/target/classes
[INFO] Logging initialized @2151ms to org.eclipse.jetty.util.log.Slf4jLog
[INFO] Context path = /
[INFO] Tmp directory = /Users/indra.basak/Development/examples/hackernews-graphql-java/target/tmp
[INFO] Web defaults = org/eclipse/jetty/webapp/webdefault.xml
[INFO] Web overrides =  none
[INFO] web.xml file = null
[INFO] Webapp directory = /Users/indra.basak/Development/examples/hackernews-graphql-java/src/main/webapp
[INFO] jetty-9.4.6.v20170531
[INFO] Scanning elapsed time=558ms
[INFO] DefaultSessionIdManager workerName=node0
[INFO] No SessionScavenger set, using defaults
[INFO] Scavenging every 600000ms
[INFO] Started o.e.j.m.p.JettyWebAppContext@153f66e7{/,file:///Users/indra.basak/Development/examples/hackernews-graphql-java/src/main/webapp/,AVAILABLE}{file:///Users/indra.basak/Development/examples/hackernews-graphql-java/src/main/webapp/}
[INFO] Started ServerConnector@148c7c4b{HTTP/1.1,[http/1.1]}{0.0.0.0:8080}
[INFO] Started @3334ms
[INFO] Started Jetty Server
```

### Usage

#### Query All Links
```javascript
http://localhost:8080/graphql?query={allLinks{url}}
```

```javascript
{
  "data": {
    "allLinks": [
      {
        "url": "http://howtographql.com"
      },
      {
        "url": "http://graphql.org/learn/"
      }
    ]
  }
}
```
#### Create Links
type Mutation {
    createLink(url: String!, description: String!): Link
}

```
http://localhost:8080/graphql?mutation={createLink(url: "http://www.graph.cool", description: "Serverless GraphQL Backend") {url description}}
http://localhost:8080/?query=mutation%20createLink%20%7B%0A%20%20createLink(url%3A%20%22http%3A%2F%2Fwww.graph.cool%22%2C%20description%3A%20%22Serverless%20GraphQL%20Backend%22)%20%7B%0A%20%20%20%20url%0A%20%20%20%20description%0A%20%20%7D%0A%7D%0A&operationName=createLink

http://localhost:8080/graphql?mutation createLink={createLink(url:"http://www.graph.cool",description:"Serverless GraphQL Backend"){url description}}
```

GraphiQL
```
mutation createLink {
  createLink(url: "http://www.graph.cool", description: "Serverless GraphQL Backend") {
    url
    description
  }
}
```

```
{
  "data": {
    "createLink": {
      "url": "http://www.graph.cool",
      "description": "Serverless GraphQL Backend"
    }
  }
}
```

```graphql
{
  allLinks {
    url
  }
}
```

```json
{
  "data": {
    "allLinks": [
      {
        "url": "http://howtographql.com"
      },
      {
        "url": "http://graphql.org/learn/"
      },
      {
        "url": "http://www.graph.cool"
      }
    ]
  }
}
```

#### After MongoDB
```graphql
{
  allLinks {
    url
  }
}
```

```json
{
  "data": {
    "allLinks": []
  }
}
```

```graphql
mutation createLink {
  createLink(url: "http://www.graph.cool", description: "Serverless GraphQL Backend") {
    url
    description
  }
}
```

```json
{
  "data": {
    "createLink": {
      "url": "http://www.graph.cool",
      "description": "Serverless GraphQL Backend"
    }
  }
}
```

```graphql
{
  allLinks {
    url
  }
}
```

```json
{
  "data": {
    "allLinks": [
      {
        "url": "http://www.graph.cool"
      }
    ]
  }
}
```

#### Create User
```graphql
mutation createUser {
  createUser(name: "Bojack Horseman", authProvider: {email: "bojack@example.com", password: "secret"}) {
    id
    name
  }
}
```

```json
{
  "data": {
    "createUser": {
      "id": "59ef9a1a81804f6d247ff753",
      "name": "Bojack Horseman"
    }
  }
}
```

#### Authentication
```graphql
mutation signIn {
  signinUser(auth: {email: "bojack@example.com", password: "secret"}) {
    token
    user {
      id
      name
    }
  }
}
```

```json
{
  "data": {
    "signinUser": {
      "token": "59ef9a1a81804f6d247ff753",
      "user": {
        "id": "59ef9a1a81804f6d247ff753",
        "name": "Bojack Horseman"
      }
    }
  }
}
```

```graphql
mutation link {
  createLink(url: "https://en.wikipedia.org/wiki/Bojack_Horseman", description: "Bojack's wiki entry") {
    url
  }
}
```

```json
{
  "data": {
    "createLink": {
      "url": "https://en.wikipedia.org/wiki/Bojack_Horseman"
    }
  }
}
```

```graphql
query all {
  allLinks {
    id
    url
    description
    postedBy {
    name
    }
  }
}
```

```json
{
  "data": {
    "allLinks": [
      {
        "id": "59ef94e281804f6cb9f4dc68",
        "url": "http://www.graph.cool",
        "description": "Serverless GraphQL Backend",
        "postedBy": null
      },
      {
        "id": "59efc92c79bdda735ef97f1b",
        "url": "https://en.wikipedia.org/wiki/Bojack_Horseman",
        "description": "Bojack's wiki entry",
        "postedBy": {
          "name": "Bojack Horseman"
        }
      }
    ]
  }
}
```

#### Voting for Links

```graphql
mutation vote {
  createVote(linkId: "59efc92c79bdda735ef97f1b", userId: "59efc91179bdda735ef97f1a") {
    createdAt
    link {
      url
    }
    user {
      name
    }
  }
}
```

```json
{
  "data": {
    "createVote": {
      "createdAt": "2017-10-24T23:37:19.589Z",
      "link": {
        "url": "https://en.wikipedia.org/wiki/Bojack_Horseman"
      },
      "user": {
        "name": "Indra basak"
      }
    }
  }
}
```
#### Error Handling

```graphql
mutation signIn {
  signinUser(auth: {email: "bojack@example.com", password: "secretx"}) {
    token
    user {
      id
      name
    }
  }
}
```

```json
{
  "data": {
    "signinUser": null
  },
  "errors": [
    {
      "message": "Internal Server Error(s) while executing query",
      "path": null,
      "extensions": null
    }
  ]
}
```

```json
{
  "data": {
    "signinUser": null
  },
  "errors": [
    {
      "message": "Exception while fetching data (/signinUser) : Invalid credentials",
      "errorType": "DataFetchingException"
    }
  ]
}
```

#### Filtering
```graphql
query links {
  allLinks(filter: {description_contains: "back", url_contains: "cool"}) {
    description
    url
  }
}
```

```json
{
  "data": {
    "allLinks": [
      {
        "description": "Serverless GraphQL Backend",
        "url": "http://www.graph.cool"
      }
    ]
  }
}
```

[travis-badge]: https://travis-ci.org/indrabasak/hackernews-graphql-java.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/hackernews-graphql-java/
package org.example.java;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

public class ElasticsearchClientBase extends ElasticsearchClient {

    protected SearchResponse  twitterPrepareSearch(QueryBuilder queryBuilder) {
        SearchResponse searchResponse = transportClient.prepareSearch("twitter")
                .setTypes("tweet")
                .setQuery(queryBuilder)
                .get();
        return searchResponse;
    }


}

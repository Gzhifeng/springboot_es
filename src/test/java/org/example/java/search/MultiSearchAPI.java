package org.example.java.search;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

/**
 * multi search API 允许在同一API中执行多个搜索请求
 */
public class MultiSearchAPI extends ElasticsearchClientBase {

    @Test
    public void testForMultiSearch() {
        SearchRequestBuilder srb1 = transportClient.prepareSearch()
                .setQuery(QueryBuilders.queryStringQuery("elasticsearch"))
                .setSize(1);
        SearchRequestBuilder srb2 = transportClient.prepareSearch()
                .setQuery(QueryBuilders.matchQuery("user", "bulk01"))
                .setSize(1);

        MultiSearchResponse items = transportClient.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        //从MultiSearchResponse#getResponses()获得所有单独的响应
        long nbHits = 0;
        for (MultiSearchResponse.Item response : items.getResponses()) {
            nbHits = nbHits + response.getResponse().getHits().totalHits;
            for (SearchHit hit : response.getResponse().getHits()) {
                System.out.println(hit.getSourceAsString());
            }
        }
        System.out.println("nbHits: " + nbHits);
    }

}

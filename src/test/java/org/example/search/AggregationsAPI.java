package org.example.search;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.example.ElasticsearchClientBase;
import org.junit.Test;

/**
 * 在搜索中添加聚合
 * todo 测试存疑
 */
public class AggregationsAPI extends ElasticsearchClientBase {

    @Test
    public void testForAggregationsAPI() {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("agg").setTypes("tweet");
        AggregationBuilder termsBuilder = AggregationBuilders.terms("term").field("tweet.group");
//        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("term").field("tweegroup");
        SearchResponse searchResponse = searchRequestBuilder.addAggregation(termsBuilder).get();
        Terms term = searchResponse.getAggregations().get("term");
//        println(searchResponse);
        System.out.println("term: " + term.getName());
        System.out.println("term:" + term.getBuckets().toString());
    }

}

package org.example.java.search;

import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

/**
 * Scrolls API 允许我们检索大量数据
 */
public class ScrollsAPI extends ElasticsearchClientBase {

    private String scrollId;

    @Test
    public void testForScrollsAPI(){
        SearchResponse twitterResponses = transportClient.prepareSearch("twitter")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                //为了使用 scroll，初始搜索请求应该在查询中指定 scroll 参数，
                //告诉 Elasticsearch 需要保持搜索的上下文环境多长时间（滚动时间）
                .setScroll(new TimeValue(6000))
                //query 查询条件
                .setQuery(QueryBuilders.termQuery("user", "li"))
                //
                .setSize(5)
                .get();

        scrollId = twitterResponses.getScrollId();
        for (SearchHit hit : twitterResponses.getHits().getHits()) {
            System.out.println("1: " + hit.getSourceAsString());
        }
    }

    @Override
    public void tearDown() throws Exception {
        ClearScrollRequestBuilder clearScrollRequestBuilder = transportClient.prepareClearScroll();
        clearScrollRequestBuilder.addScrollId(scrollId);
        ClearScrollResponse response = clearScrollRequestBuilder.get();

        if (response.isSucceeded()) {
            System.out.println("成功清除");
        }
        super.tearDown();
    }

}

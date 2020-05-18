package org.example.java.query;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 全文搜索
 */
public class FullTextQueries extends ElasticsearchClientBase {

    /**
     * 用于执行全文查询的标准查询，精确匹配
     *
     * @throws Exception
     */
    @Test
    public void testMatchQuery() throws Exception {
        QueryBuilder qb = matchQuery(
                "message",                  //field 字段
                "Elasticsearch"   // text
        );

        SearchResponse searchResponse = twitterPrepareSearch(qb);
        println(searchResponse);

    }

    /**
     * 全文检测，在多个属性中检索
     * @throws Exception
     */
    @Test
    public void testMultiMatchQuery() throws Exception {
        QueryBuilder qb = multiMatchQuery(
                "li kimchy", // text
                "user", "message"       //fields 多个字段
        );

        SearchResponse searchResponse = twitterPrepareSearch(qb);
        println(searchResponse);
    }

    /**
     * 常用术语查询
     * @throws Exception
     */
    @Test
    public void testCommonTermsQuery() throws Exception {
        QueryBuilder qb = commonTermsQuery(
                "user",    //field 字段
                "kimchy");  // value

        SearchResponse searchResponse = twitterPrepareSearch(qb);
        println(searchResponse);
    }
}

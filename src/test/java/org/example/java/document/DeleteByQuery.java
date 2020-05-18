package org.example.java.document;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

/**
 * 查询删除
 */
public class DeleteByQuery extends ElasticsearchClientBase {

    @Test
    public void testForDeleteByQuery() {
        BulkByScrollResponse status = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                .filter(QueryBuilders.termQuery("user", "kimchy"))
                .source("twitter")
                .execute().actionGet();
        System.out.println("删除成功！" + status);
    }


    /**
     * todo 异步删除测试有问题
     */
    @Test
    public void testForAsyncDeleteByQuery() {
        DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                .filter(QueryBuilders.matchQuery("user", "kimchy"))
                .source("twitter")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                        long deleted = bulkByScrollResponse.getDeleted();
                        System.out.println("异步删除成功！" + deleted);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("异步删除失败" + e.getMessage());
                    }
                });
    }

}


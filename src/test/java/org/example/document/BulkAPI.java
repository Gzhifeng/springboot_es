package org.example.document;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.example.ElasticsearchClientBase;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 批量生成插入
 */
public class BulkAPI extends ElasticsearchClientBase {

    /**
     * 测试批量添加
     *
     * @throws IOException
     */
    @Test
    public void testForBulkAPI() throws IOException {
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        XContentBuilder bulk01 = jsonBuilder()
                .startObject()
                .field("name", "bulk01")
                .field("group", "test1")
                .field("message", "message bulk01")
                .endObject();
        bulkRequestBuilder.add(transportClient.prepareIndex("agg", "tweet", "1").setSource(bulk01));

        XContentBuilder bulk02 = jsonBuilder()
                .startObject()
                .field("name", "bulk02")
                .field("group", "test1")
                .field("message", "message bulk02")
                .endObject();
        bulkRequestBuilder.add(transportClient.prepareIndex("agg", "tweet", "2").setSource(bulk02));
        XContentBuilder bulk03 = jsonBuilder()
                .startObject()
                .field("name", "bulk03")
                .field("group", "test2")
                .field("message", "message bulk03")
                .endObject();
        bulkRequestBuilder.add(transportClient.prepareIndex("agg", "tweet", "3").setSource(bulk03));
        //不调用get()方法，不生成index
        BulkResponse bulkItemResponses = bulkRequestBuilder.get();
        //处理失败
        if (bulkItemResponses.hasFailures()) {
            System.out.println("处理失败");
        }
        System.out.println("testForBulkAPI");
    }
}

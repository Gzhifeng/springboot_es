package org.example.java.document;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 更新文档
 */
public class UpdateAPI extends ElasticsearchClientBase {

    /**
     * 测试 UpdateRequest 方式更新文档
     * 只能更新存在的文档，如果文挡不存在则抛出异常
     *
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testForUpdateRequest() throws IOException, ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("twitter");
        updateRequest.type("tweet");
        updateRequest.id("1");
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "http://quanke.name")
                .field("gender", "male")
                .endObject();
        updateRequest.doc(builder);
        UpdateResponse updateResponse = transportClient.update(updateRequest).get();
        System.out.println("testForUpdateRequest: " + updateResponse);
    }

    /**
     * 测试 UpdateRequest
     * 如果不存在此文档 ，就增加 `indexRequest`
     *
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testForUpdateRequestAndIndex() throws IOException, ExecutionException, InterruptedException {

        XContentBuilder indexBuilder = jsonBuilder()
                .startObject()
                .field("user", "indexRequest")
                .field("postDate", new Date())
                .field("message", "testForUpdateRequestAndIndex")
                .endObject();
        IndexRequest indexRequest = new IndexRequest("twitter", "tweet", "4")
                .source(indexBuilder);

        XContentBuilder updateBuilder = jsonBuilder()
                .startObject()
                .field("user", "updateRequest")
                .field("gender", "male")
                .endObject();
        UpdateRequest updateRequest = new UpdateRequest("twitter", "tweet", "4")
                .doc(updateBuilder)
                //如果不存在此文档 ，就增加 `indexRequest`
                .upsert(indexRequest);
        UpdateResponse updateResponse = transportClient.update(updateRequest).get();
        System.out.println("testForUpdateRequestAndIndex: " + updateResponse);
    }


}

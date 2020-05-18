package org.example.java.document;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Index API 可以存储一个JSON格式的文档
 */
public class IndexAPI extends ElasticsearchClientBase {

    /**
     * 使用json字符串来创建文档内容
     */
    @Test
    public void testForUseStr() {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2019-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse indexResponse = transportClient.prepareIndex("twitter", "tweet", "10")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println("testForUseStr twitter 创建成功");
    }

    /**
     * 使用map创建文档内容
     */
    @Test
    public void testForUseMap() {

        HashMap<String, Object> map = new HashMap<>(3);
        map.put("user", "li");
        map.put("postDate", "2019-01-30");
        map.put("message", "trying out Elasticsearch");
        IndexResponse indexResponse = transportClient.prepareIndex("twitter", "tweet", "2")
                .setSource(map).get();
        System.out.println("testForUseMap: " + indexResponse );
    }

    /**
     * 使用elasticsearch官方提供的json构造器来构造文档内容
     *
     * @throws IOException
     */
    @Test
    public void testForUseXContentBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "nau")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();
        transportClient.prepareIndex("twitter", "tweet", "3")
                .setSource(builder);
        System.out.println("testForUseXContentBuilder twitter 创建成功");
    }
}

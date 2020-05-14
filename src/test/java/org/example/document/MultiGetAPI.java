package org.example.document;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.example.ElasticsearchClientBase;
import org.junit.Test;

/**
 * 一次获取多个文档
 */
public class MultiGetAPI extends ElasticsearchClientBase {

    @Test
    public void testForMultiGetAPI() {
        MultiGetResponse multiGetItemResponses = transportClient.prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("twitter", "tweet", "2")
                .add("twitter", "tweet", "3")
                .get();
        for (MultiGetItemResponse multiGetItemRespons : multiGetItemResponses) {
            GetResponse response = multiGetItemRespons.getResponse();
            if (response.isExists()) {
                System.out.println("_source: " + response.getSourceAsString());
            }
        }

    }

}

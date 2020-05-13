package org.example.document;

import org.elasticsearch.action.get.GetResponse;
import org.example.ElasticsearchClientBase;
import org.junit.Test;

/**
 * 获取文档内容
 */
public class GetAPI extends ElasticsearchClientBase {

    @Test
    public void testForGetAPI() {
        GetResponse fields = transportClient
                .prepareGet("twitter", "tweet", "4")
                .get();
        if (fields.isExists()) {
            System.out.println(fields.getSource());
        } else {
            System.out.println("没有文档");
        }
    }

}

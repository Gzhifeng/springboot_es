package org.example.java.document;

import org.elasticsearch.action.delete.DeleteResponse;
import org.example.java.ElasticsearchClientBase;
import org.junit.Test;

/**
 * 删除文档
 */
public class DeleteAPI extends ElasticsearchClientBase {

    @Test
    public void testForUseDeleteAPI() {
        DeleteResponse deleteResponse = transportClient.prepareDelete("twitter", "tweet", "1")
                .get();
        System.out.println("testForUseDelete: " + deleteResponse);
    }

}

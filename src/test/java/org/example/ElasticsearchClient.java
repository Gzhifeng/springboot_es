package org.example;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;

import java.net.InetSocketAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 客户端
 * 作为一个外部访问者，请求ES的集群，对于集群而言，它是一个外部因素
 */
public class ElasticsearchClient {

    protected TransportClient transportClient;

    @Before
    public void setUp() throws UnknownHostException {
        Settings settings = Settings.builder()
                //设置ES实例的名称
                .put("cluster.name", "elasticsearch_ganzhifeng")
                .put("client.transport.sniff", true)
                .build();


        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300);
        transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(transportAddress);

        System.out.println("ElasticsearchClient 连接成功");
    }

    @After
    public void tearDown() {
        if (transportClient != null) {
            transportClient.close();
        }
    }

    protected void println(SearchResponse searchResponse) {

    }

}

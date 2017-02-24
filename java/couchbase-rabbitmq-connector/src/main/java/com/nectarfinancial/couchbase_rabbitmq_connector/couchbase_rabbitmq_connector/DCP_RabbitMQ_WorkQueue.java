package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import java.io.IOException;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class DCP_RabbitMQ_WorkQueue implements DCPEventListener {
	
	private Channel channel;
	public DCP_RabbitMQ_WorkQueue(String host, String channelName) throws IOException {
		Connection connection = RabbitMQ.createConnection(host);
		this.channel = RabbitMQ.createTopicChannel(connection, channelName);
	}
	

	public void onEvent(ByteBuf event) {
		// TODO Auto-generated method stub
		
	}

}

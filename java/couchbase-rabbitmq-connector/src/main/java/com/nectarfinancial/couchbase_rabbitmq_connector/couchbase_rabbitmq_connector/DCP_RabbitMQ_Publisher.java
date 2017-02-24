package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import java.io.IOException;

import com.couchbase.client.dcp.message.DcpDeletionMessage;
import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class DCP_RabbitMQ_Publisher implements DCPEventListener {

	private Channel channel;
	private static final String EXCHANGE_NAME = "dcp_events";
	
	public DCP_RabbitMQ_Publisher(String host, String channelName) throws IOException {
		Connection connection = RabbitMQ.createConnection(host);
		this.channel = RabbitMQ.createTopicChannel(connection, channelName);
	}
	
	public void onEvent(ByteBuf event) {
		if (DcpMutationMessage.is(event)) {
            System.out.println("Mutation: " + DcpMutationMessage.toString(event));
            try {
				channel.basicPublish(EXCHANGE_NAME, "mutation", null, DcpMutationMessage.content(event).toString(CharsetUtil.UTF_8).getBytes("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else if (DcpDeletionMessage.is(event)) {
            System.out.println("Deletion: " + DcpDeletionMessage.toString(event));
        }	
	}
}

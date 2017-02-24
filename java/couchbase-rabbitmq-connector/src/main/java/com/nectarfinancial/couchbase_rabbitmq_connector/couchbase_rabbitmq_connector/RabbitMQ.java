package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQ {
	
	private static Map<String, Connection> connections = new HashMap<String, Connection>();
	private static Map<String, Channel> channels = new HashMap<String, Channel>();
		
	public static Connection createConnection(String host) {
		if (!connections.containsKey(host)) {
			Connection connection;
			try {
		      ConnectionFactory factory = new ConnectionFactory();
		      factory.setHost(host);
		      connection = factory.newConnection();
		      return connection;
		    }
		    catch  (Exception e) {
		      e.printStackTrace();
		    }
			return null;
		}
		return connections.get(host);
	}
	
	public static Channel createTopicChannel(Connection connection, String channelName) throws IOException {
		if (!channels.containsKey(channelName)) {
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(channelName, "topic");
			channels.put(channelName, channel);
			return channel;
		}	
		return channels.get(channelName);
	}
}

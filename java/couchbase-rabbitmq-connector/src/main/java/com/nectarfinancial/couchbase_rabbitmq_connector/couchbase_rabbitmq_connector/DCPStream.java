package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import com.couchbase.client.dcp.Client;
import com.couchbase.client.dcp.DefaultConnectionNameGenerator;
import com.couchbase.client.dcp.StreamFrom;
import com.couchbase.client.dcp.StreamTo;
import com.couchbase.client.dcp.conductor.HttpStreamingConfigProvider;
import com.couchbase.client.dcp.config.ClientEnvironment;
import com.couchbase.client.dcp.config.DcpControl;
import com.couchbase.client.deps.io.netty.channel.nio.NioEventLoopGroup;

import java.util.List;


public class DCPStream {
	private Client client;
	
	public void init(List<String> clusters, String bucket, int httpDirectPort, int dcpDirectPort) {
		ClientEnvironment ce = ClientEnvironment
				.builder()
				.setClusterAt(clusters)
		        .setConnectionNameGenerator(DefaultConnectionNameGenerator.INSTANCE)
		        .setBucket(bucket)
		        .setBootstrapHttpDirectPort(httpDirectPort)
		        .setDcpDirectPort(dcpDirectPort)
		        .setPassword("")
		        .setDcpControl(new DcpControl())
		        .setEventLoopGroup(new NioEventLoopGroup(), false)
		        .setBufferAckWatermark(0)
		        .setBufferPooling(true)
		        .setConnectTimeout(ClientEnvironment.DEFAULT_CONNECT_TIMEOUT)
		        .setBootstrapTimeout(ClientEnvironment.DEFAULT_BOOTSTRAP_TIMEOUT)
		        .setSocketConnectTimeout(ClientEnvironment.DEFAULT_SOCKET_CONNECT_TIMEOUT)
		        .setConfigProviderReconnectDelay(ClientEnvironment.DEFAULT_CONFIG_PROVIDER_RECONNECT_DELAY)
		        .setConfigProviderReconnectMaxAttempts(ClientEnvironment.DEFAULT_CONFIG_PROVIDER_RECONNECT_MAX_ATTEMPTS)
		        .setDcpChannelsReconnectDelay(ClientEnvironment.DEFAULT_DCP_CHANNELS_RECONNECT_DELAY)
		        .setDcpChannelsReconnectMaxAttempts(ClientEnvironment.DEFAULT_DCP_CHANNELS_RECONNECT_MAX_ATTEMPTS)
		        .setEventBus(null)
		        .setSslEnabled(ClientEnvironment.DEFAULT_SSL_ENABLED)
		        .setSslKeystoreFile(null)
		        .setSslKeystorePassword(null)
		        .setSslKeystore(null)
		        .build();
		HttpStreamingConfigProvider hscp = new HttpStreamingConfigProvider(ce);
		
		this.client = Client.configure()
				.configProvider(hscp)
				.build();
		
		// Don't do anything with control events in this example
		this.client.controlEventHandler(new RabbitMQControlEventHandler());
		
        // Print out Mutations and Deletions
        this.client.dataEventHandler(new RabbitMQDataEventHandler());
	}
	
	public void start() {
		// Connect the sockets
        this.client.connect().await();

        // Initialize the state (start now, never stop)
        this.client.initializeState(StreamFrom.NOW, StreamTo.INFINITY).await();

        // Start streaming on all partitions
        this.client.startStreaming().await();
	}
	
	public void stop() {
        client.disconnect().await();
	}


}

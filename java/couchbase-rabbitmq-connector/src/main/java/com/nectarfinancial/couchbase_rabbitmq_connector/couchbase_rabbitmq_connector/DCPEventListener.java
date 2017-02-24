package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;

public interface DCPEventListener {
	
	public void onEvent(ByteBuf event);

}

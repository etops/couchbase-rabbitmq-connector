package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import com.couchbase.client.dcp.ControlEventHandler;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;

public class RabbitMQControlEventHandler implements ControlEventHandler {

	public void onEvent(ByteBuf event) {
        event.release();
    }

}

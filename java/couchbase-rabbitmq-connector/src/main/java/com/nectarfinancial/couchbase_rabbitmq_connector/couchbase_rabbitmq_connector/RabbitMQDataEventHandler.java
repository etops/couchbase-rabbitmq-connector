package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.client.dcp.DataEventHandler;
import com.couchbase.client.dcp.message.DcpDeletionMessage;
import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;

public class RabbitMQDataEventHandler implements DataEventHandler {
	
	
	private List<DCPEventListener> listeners = new ArrayList<DCPEventListener>();
	
	
	public void addEventListener(DCPEventListener listener) {
		listeners.add(listener);
	}
	

	public void onEvent(ByteBuf event) {
		Iterator<DCPEventListener> eventListerIterator = listeners.iterator();
		while(eventListerIterator.hasNext()) {
			eventListerIterator.next().onEvent(event);
		}
        event.release();
    }

}

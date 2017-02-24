package com.nectarfinancial.couchbase_rabbitmq_connector.couchbase_rabbitmq_connector;

import com.couchbase.client.dcp.DataEventHandler;
import com.couchbase.client.dcp.message.DcpDeletionMessage;
import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;

public class RabbitMQDataEventHandler implements DataEventHandler {

	public void onEvent(ByteBuf event) {
        if (DcpMutationMessage.is(event)) {
            System.out.println("Mutation: " + DcpMutationMessage.toString(event));
            // You can print the content via DcpMutationMessage.content(event).toString(CharsetUtil.UTF_8);
            //System.out.println(DcpMutationMessage.content(event).toString(CharsetUtil.UTF_8));
            
        } else if (DcpDeletionMessage.is(event)) {
            System.out.println("Deletion: " + DcpDeletionMessage.toString(event));
        }
        event.release();
    }

}

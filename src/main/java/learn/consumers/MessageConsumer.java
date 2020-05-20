package learn.consumers;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class MessageConsumer implements Handler<Message<JsonObject>>{

	@Override
	public void handle(Message<JsonObject> message) {
		System.out.println("Message received: " + message.body());
		message.reply("ACK");		
	}

}

package learn.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class EchoHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext rc) {
		System.out.println(rc.getBodyAsJson());
		rc.vertx().eventBus().request("my.queue", rc.getBodyAsJson(), result -> {
			System.out.println("Reply received: "+result.result().body());
		});
		rc.response().end("Hello world..");
	}
}

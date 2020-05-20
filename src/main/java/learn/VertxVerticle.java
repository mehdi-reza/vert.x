package learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import learn.config.RouteHelper;
import learn.consumers.MessageConsumer;
import learn.handlers.EchoHandler;

public class VertxVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Vertx vertx = getVertx();
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		// setup routes
		RouteHelper.route(router, EchoHandler.class);
		
		// setup consumers
		vertx.eventBus().consumer("my.queue", new MessageConsumer());

		server.requestHandler(router).listen(8080);

		vertx.getOrCreateContext().runOnContext(v -> {
			System.out.println("--- > " + context.deploymentID() + " < --- started");
		});

	}

	public static void main(String[] args) {
		Vertx.vertx().deployVerticle(VertxVerticle.class.getName());
	}
}

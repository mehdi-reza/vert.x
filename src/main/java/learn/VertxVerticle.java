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
import learn.handlers.EchoHandler;

public class VertxVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Vertx vertx = getVertx();
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(routes(Router.router(vertx))).listen(8080);

		final Context context = vertx.getOrCreateContext();

		vertx.getOrCreateContext().runOnContext(v -> {
			System.out.println("--- > "+ context.deploymentID()+" < --- started");
		});
		
		Handler<Message<JsonObject>> queueHandler = message -> {
			System.out.println("Message received: "+message.body());
			message.reply("ACK");
		};
		
		vertx.eventBus().consumer("my.queue", queueHandler);
	}

	public static Router routes(Router router) throws ReflectiveOperationException {
		
		router.route("/echo").handler(BodyHandler.create());
		handler(router.route(HttpMethod.POST, "/echo"), false, EchoHandler.class);
		
		return router;
	}

	public static void handler(Route route, boolean blocking, Class<? extends Handler<RoutingContext>> clazz)
			throws ReflectiveOperationException {
		try {
			Handler<RoutingContext> handler = clazz.newInstance();
			if (blocking)
				route.blockingHandler(handler);
			else
				route.handler(handler);
		} catch (InstantiationException | IllegalAccessException e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		Vertx.vertx().deployVerticle(VertxVerticle.class.getName());
	}
}

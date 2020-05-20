package learn.handlers;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import learn.annotations.Api;
import learn.annotations.Blocking;
import learn.config.BaseHandler;

@Api(methods= {HttpMethod.GET, HttpMethod.POST}, uri="/echo")
public class EchoHandler implements BaseHandler {

	@Override
	@Blocking
	public void post(JsonObject json, RoutingContext rc) {
		rc.vertx().eventBus().request("my.queue", json, result -> {
			System.out.println("Reply received: "+result.result().body());
		});
		rc.response().end("Hello world..");
	}
	
	@Override
	public void get(RoutingContext rc) {
		rc.response().end("Hello world..");
	}
}

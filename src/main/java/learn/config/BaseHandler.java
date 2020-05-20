package learn.config;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public interface BaseHandler extends Handler<RoutingContext>{

	@Override
	default void handle(RoutingContext rc) {
		
		if(rc.request().method()==HttpMethod.GET)
			get(rc);
		if(rc.request().method()==HttpMethod.POST)
			post(rc.getBodyAsJson(), rc);
		if(rc.request().method()==HttpMethod.PUT)
			put(rc.getBodyAsJson(), rc);
		if(rc.request().method()==HttpMethod.PATCH)
			patch(rc.getBodyAsJson(), rc);
		if(rc.request().method()==HttpMethod.DELETE)
			delete(rc);
		
	}

	default void get(RoutingContext rc) {
		rc.response().end("Not yet implemented");
	}
	
	default void post(JsonObject json, RoutingContext rc) {
		rc.response().end("Not yet implemented");
	}
	
	default void put(JsonObject json, RoutingContext rc) {
		rc.response().end("Not yet implemented");
	}
	
	default void patch(JsonObject json, RoutingContext rc) {
		rc.response().end("Not yet implemented");
	}
	
	default void delete(RoutingContext rc) {
		rc.response().end("Not yet implemented");
	}
}

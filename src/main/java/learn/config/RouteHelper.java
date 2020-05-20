package learn.config;

import java.lang.reflect.Method;
import java.util.Arrays;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import learn.annotations.Api;
import learn.annotations.Blocking;

public class RouteHelper {

	public static void route(Router router, Class<? extends BaseHandler> clazz) {

		Api api = clazz.getAnnotation(Api.class);

		HttpMethod[] httpMethods = api.methods();

		Arrays.stream(httpMethods).forEach(httpMethod -> {

			Route route = router.route(httpMethod, api.uri());
			boolean withEntity = false;

			if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
				route.handler(BodyHandler.create());
				withEntity = true;
			}

			Method method;

			try {
				if (withEntity)
					method = clazz.getMethod(httpMethod.toString().toLowerCase(), JsonObject.class, RoutingContext.class);
				else
					method = clazz.getMethod(httpMethod.toString().toLowerCase(), RoutingContext.class);

				boolean isBlocking = method.getAnnotation(Blocking.class) != null;

				if (isBlocking)
					route.blockingHandler(clazz.newInstance());
				else
					route.handler(clazz.newInstance());
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}

		});
	}
}

package learn.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.vertx.core.http.HttpMethod;

@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

	HttpMethod[] methods();
	String uri();

}

package org.iglooproject.autoprefixer.internal;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.iglooproject.autoprefixer.AutoprefixerException;
import org.iglooproject.autoprefixer.IAutoprefixer;
import org.mozilla.javascript.JavaScriptException;

public class GraalAutoprefixerImpl implements IAutoprefixer {

	private static final String AUTOPREFIXER_JS_CLASSPATH = "/autoprefixer/autoprefixer-new.js"; // NOSONAR

	@Override
	public String process(String css) throws AutoprefixerException {
		// prepare javaGetCss method as a callback to provide css code
		try (Context context = Context.create()) {
			context.eval(Source.newBuilder("js", loadAutoprefixerScriptAsString(), "autoprefixer.js").build());
			Value empty = context.eval("js", "({cascade: true, overrideBrowserslist: ['last 1 versions']})");
			long time = System.currentTimeMillis();
			Value returnValue = context.getBindings("js").getMember("autoprefixer").getMember("process").execute(css, empty);
			System.out.println(String.format("Elasped: %d ms.", System.currentTimeMillis() - time));
			return returnValue.getMember("css").asString();
		} catch (IOException e) {
			// error loading autoprefixer runtime is considered as internal
			throw new IllegalStateException("Error initializing autoprefixer script", e);
		} catch (JavaScriptException cssException) {
			throw new AutoprefixerException("Error (graal) processing css", cssException);
		}
	}

	private String loadAutoprefixerScriptAsString() throws IOException {
		return IOUtils.toString(getClass().getResourceAsStream(AUTOPREFIXER_JS_CLASSPATH));
	}

}

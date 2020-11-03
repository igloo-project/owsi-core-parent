package test.autoprefixer;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.iglooproject.autoprefixer.Autoprefixer;
import org.iglooproject.autoprefixer.AutoprefixerException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class TestAutoprefixer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestAutoprefixer.class);

	@Test
	public void autoprefixer() throws AutoprefixerException {
		String processed = Autoprefixer.simple().process(
			"@supports (position: sticky) {\n" + 
			"  .sticky-top {\n" + 
			"    position: sticky;\n" + 
			"    top: 0;\n" + 
			"    z-index: 1020;\n" + 
			"  }\n" + 
			"}"
		);
		Assertions.assertThat(processed).isEqualTo(
			"@supports ((position: -webkit-sticky) or (position: sticky)) {\n" + 
			"  .sticky-top {\n" + 
			"    position: -webkit-sticky;\n" + 
			"    position: sticky;\n" + 
			"    top: 0;\n" + 
			"    z-index: 1020;\n" + 
			"  }\n" + 
			"}"
		);
	}

	@Test
	public void syntaxError() {
		Assertions.assertThatExceptionOfType(AutoprefixerException.class)
			.isThrownBy(() -> Autoprefixer.simple().process("css invalid"));
	}

	@Test
//	@Ignore
	public void bootstrap() throws AutoprefixerException, IOException {
		String css = IOUtils.toString(getClass().getResourceAsStream("/css/bootstrap.css"));
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Autoprefixer.simple().process(css);
		
		stopWatch.stop();
		
		LOGGER.debug("Autoprefixer (rhino) process time: {}s", stopWatch.getTotalTimeSeconds());
	}

	@Test
//	@Ignore
	public void graalBootstrap() throws AutoprefixerException, IOException {
		String css = IOUtils.toString(getClass().getResourceAsStream("/css/bootstrap.css"));
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Autoprefixer.graal().process(css);
		
		stopWatch.stop();
		
		LOGGER.debug("Autoprefixer (graal) process time: {}s", stopWatch.getTotalTimeSeconds());
	}

}

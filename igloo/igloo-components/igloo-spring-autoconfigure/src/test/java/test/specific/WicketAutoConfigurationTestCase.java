package test.specific;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.wicket.protocol.http.WebApplication;
import org.igloo.spring.autoconfigure.EnableIglooAutoConfiguration;
import org.igloo.spring.autoconfigure.IglooAutoConfigurationImportSelector;
import org.igloo.spring.autoconfigure.bootstrap.IglooBootstrap3AutoConfiguration;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Base class used to check that {@link EnableIglooAutoConfiguration} triggers IglooJpaAutoConfiguration properly. 
 * 
 * This class uses ApplicationContextRunner to initialize contexts with suitable configurations,
 * which are declared at the bottom of the file.
 *  
 */
public class WicketAutoConfigurationTestCase {

	/**
	 * Check that autoconfiguration from {@link WebApplication} is triggered with EnableIglooAutoConfiguration
	 */
	@Test
	public void testIglooWicketAutoConfigure() {
		new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(TestConfig.class))
			.withPropertyValues(String.format("%s=%s",
					IglooAutoConfigurationImportSelector.PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE,
					IglooBootstrap3AutoConfiguration.class.getName()))
			.run(
				(context) -> { assertThat(context).hasSingleBean(WebApplication.class); }
			);
	}
	
	@Configuration
	@EnableIglooAutoConfiguration
	public static class TestConfig {}

}
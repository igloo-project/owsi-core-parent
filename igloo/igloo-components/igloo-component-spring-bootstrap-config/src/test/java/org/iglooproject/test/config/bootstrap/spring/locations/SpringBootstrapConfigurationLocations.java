package org.iglooproject.test.config.bootstrap.spring.locations;

import static org.assertj.core.api.Assertions.assertThat;

import org.iglooproject.test.config.bootstrap.spring.util.AbstractTestCase;
import org.iglooproject.test.config.bootstrap.spring.util.SpringWithConfigurationLocationsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(inheritProperties = true, properties = {
	"igloo.profile=development",
	"user.name=username"
})
@ContextConfiguration(
		inheritLocations = true,
		classes = SpringWithConfigurationLocationsConfig.class
)
public class SpringBootstrapConfigurationLocations extends AbstractTestCase {

	@Value("${default:}")
	private String default_;
	@Value("${configuration:}")
	private String configuration;
	@Value("${override:}")
	private String override;
	@Value("${applicationName.property:}")
	private String applicationNameProperty;
	@Value("${igloo.applicationName:}")
	private String applicationName;
	@Value("${placeholder1:}")
	private String placeholder1;
	@Value("${placeholder2:}")
	private String placeholder2;

	@Test
	public void configurationLocationsOrder() {
		// test loading by order on @ConfigurationLocations
		assertThat(default_).isEqualTo("default");
		assertThat(configuration).isEqualTo("configuration");
		assertThat(override).isEqualTo("override");
	}

	@Test
	public void configurationLocationsApplicationName() {
		// test loading with ${igloo.applicationName}
		assertThat(applicationNameProperty).isEqualTo("app-application-test");
		
		// test ${igloo.applicationName} PropertySource injection with @ApplicationDescription
		assertThat(applicationName).isEqualTo("application-test");
	}

	@Test
	public void configurationLocationsPlaceholder() {
		// test placeholder across files
		assertThat(placeholder1).isEqualTo("default.configuration.override");
		assertThat(placeholder2).isEqualTo("override");
	}

	@Test
	public void configurationLocationsSystemProperty() {
		// test loading with ${user.name}
		assertThat(applicationNameProperty).isEqualTo("app-application-test");
	}
}
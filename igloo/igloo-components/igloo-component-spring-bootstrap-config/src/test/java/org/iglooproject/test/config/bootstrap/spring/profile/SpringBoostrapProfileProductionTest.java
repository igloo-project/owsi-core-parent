package org.iglooproject.test.config.bootstrap.spring.profile;

import org.assertj.core.api.Assertions;
import org.iglooproject.test.config.bootstrap.spring.util.AbstractSpringBoostrapProfileTest;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;


/**
 * <p>Test with default bootstrap configuration (selection with igloo.profile, and switch with
 * recursive resolve in properties)</p>
 * 
 * <p>i.e., igloo.configurationLocations=${igloo.${igloo.profile}.configurationLocations}</p>
 * 
 * <p><b>user.name<b> is overriden with <em>username<em></p>
 */
@TestPropertySource(inheritProperties = true, properties = {
	"igloo.profile=production"
})
public class SpringBoostrapProfileProductionTest extends AbstractSpringBoostrapProfileTest {

	/**
	 * <p>Test override precedence. default -&gt; preproduction</p>
	 * <p>configuration-user-username.properties file is <b>not</b> used in preproduction profile</p>
	 */
	@Override
	@Test
	public void testOverrides() {
		// this value is not overriden
		Assertions.assertThat(default_).isEqualTo("default");
		Assertions.assertThat(deployment).isEqualTo("deployment");
		
		// this value is overriden
		Assertions.assertThat(production).isEqualTo("production");
		
		// these profiles are not loaded !
		Assertions.assertThat(test).isEqualTo("default");
		Assertions.assertThat(development).isEqualTo("default");
		Assertions.assertThat(preproduction).isEqualTo("default");
		
		// user configuration is not loaded
		Assertions.assertThat(user).isEqualTo("default");
	}

}

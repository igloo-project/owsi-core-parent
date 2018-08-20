package org.iglooproject.test.rest.jersey2.client.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.iglooproject.jpa.config.spring.AbstractConfiguredJpaConfig;
import org.iglooproject.jpa.config.spring.provider.JpaPackageScanProvider;
import org.iglooproject.test.rest.jersey2.business.RestTestBusinessPackage;

@Configuration
@EnableAspectJAutoProxy
public class RestClientTestJpaConfig extends AbstractConfiguredJpaConfig {

	@Override
	public JpaPackageScanProvider applicationJpaPackageScanProvider() {
		return new JpaPackageScanProvider(RestTestBusinessPackage.class.getPackage());
	}

}

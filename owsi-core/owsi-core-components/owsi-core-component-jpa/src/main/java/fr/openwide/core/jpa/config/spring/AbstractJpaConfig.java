package fr.openwide.core.jpa.config.spring;

import static fr.openwide.core.jpa.property.JpaPropertyIds.LUCENE_BOOLEAN_QUERY_MAX_CLAUSE_COUNT;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.lucene.search.BooleanQuery;
import org.flywaydb.core.Flyway;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import fr.openwide.core.jpa.batch.CoreJpaBatchPackage;
import fr.openwide.core.jpa.business.generic.CoreJpaBusinessGenericPackage;
import fr.openwide.core.jpa.config.spring.provider.JpaPackageScanProvider;
import fr.openwide.core.jpa.more.config.util.FlywayConfiguration;
import fr.openwide.core.jpa.search.CoreJpaSearchPackage;
import fr.openwide.core.jpa.util.CoreJpaUtilPackage;
import fr.openwide.core.spring.property.service.IPropertyService;

/**
 * L'implémentation de cette classe doit être annotée {@link EnableAspectJAutoProxy}
 */
@ComponentScan(
	basePackageClasses = {
			CoreJpaBatchPackage.class,
			CoreJpaBusinessGenericPackage.class,
			CoreJpaSearchPackage.class,
			CoreJpaUtilPackage.class
	},
	excludeFilters = @Filter(Configuration.class)
)
public abstract class AbstractJpaConfig {
	
	@Autowired
	@Lazy
	private IPropertyService propertyService;
	
	@PostConstruct
	public void init() {
		BooleanQuery.setMaxClauseCount(propertyService.get(LUCENE_BOOLEAN_QUERY_MAX_CLAUSE_COUNT));
	}

	@Bean(initMethod = "migrate", value = { "flyway", "databaseInitialization" })
	@Profile("flyway")
	public Flyway flyway(DataSource dataSource, FlywayConfiguration flywayConfiguration) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setSchemas(flywayConfiguration.getSchemas()); 
		flyway.setTable(flywayConfiguration.getTable());
		flyway.setLocations(flywayConfiguration.getLocations());
		flyway.setBaselineOnMigrate(true);
		return flyway;
	}

	@Bean
	@Profile("flyway")
	public FlywayConfiguration flywayConfiguration() {
		return new FlywayConfiguration();
	}

	/**
	 * Placeholder when flyway is not enabled
	 */
	@Bean(value = { "flyway", "databaseInitialization" })
	@Profile("!flyway")
	public Object notFlyway() {
		return new Object();
	}

	@Bean
	@DependsOn("databaseInitialization")
	public abstract LocalContainerEntityManagerFactoryBean entityManagerFactory();

	/**
	 * Il est important de déterminer la destroyMethod sur l'annotation {@link Bean}. Spring prend en compte
	 * auto-magiquement la méthode close() si présente si pas de configuration.
	 */
	@Bean
	public abstract DataSource dataSource();

	@Bean
	public abstract JpaPackageScanProvider applicationJpaPackageScanProvider();

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public Advisor transactionAdvisor(PlatformTransactionManager transactionManager) {
		return JpaConfigUtils.defaultTransactionAdvisor(transactionManager);
	}

	@Bean
	public JpaPackageScanProvider coreJpaPackageScanProvider() {
		return new JpaPackageScanProvider(CoreJpaBusinessGenericPackage.class.getPackage());
	}

}

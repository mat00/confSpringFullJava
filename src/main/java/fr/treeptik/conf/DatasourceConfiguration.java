package fr.treeptik.conf;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.Driver;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(value="fr.treeptik.conf.dao")
public class DatasourceConfiguration {

	@Bean
	public DataSource dataSource() throws SQLException {
		SimpleDriverDataSource config = new SimpleDriverDataSource();
		Driver driver = new Driver();
		driver.acceptsURL("com.mysql.jdbc.Driver");
		config.setDriver(driver);
		config.setUrl("jdbc:mysql://localhost:3306/confSpringFJ");
		config.setUsername("root");
		config.setPassword("root");
		return config;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setPersistenceProvider(new HibernatePersistence());
		lcemfb.setPersistenceUnitName("persistenceUnit");
		lcemfb.setDataSource(dataSource());
		lcemfb.setJpaDialect(new HibernateJpaDialect());
		lcemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lcemfb.setPackagesToScan("fr.treeptik.springjavaconf.model");
		lcemfb.afterPropertiesSet();
		return lcemfb.getObject();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager()
			throws SQLException {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
		return jpaTransactionManager;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter
				.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}

//	@Bean
//	public HibernateExceptionTranslator hibernateExceptionTranslator() {
//		return new HibernateExceptionTranslator();
//	}

}

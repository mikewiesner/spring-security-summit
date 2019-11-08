package pu.pto.config.web;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pu.pto.config.core.MethodSecurityConfig;

@SpringBootApplication(scanBasePackages = { "pu.pto.web", "pu.pto.domain", "pu.pto.backend" })
@EnableJpaRepositories(basePackages= "pu.pto.domain")
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class SimpleSpringWebMvcConfig implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringWebMvcConfig.class, args);
	}

	@Bean
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("pu.pto.domain");
		factory.setDataSource(dataSource());
		return factory;
	}
	
	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		FilterRegistrationBean hiddenHttpMethodFilter = new FilterRegistrationBean(new HiddenHttpMethodFilter());
		hiddenHttpMethodFilter.addUrlPatterns("/*");
		return hiddenHttpMethodFilter;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}




}

package br.com.cta.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
//import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "br.com.cta")
public class MappingConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean(name = "MappingPackages")
	public ViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setApplicationContext(applicationContext);
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");

		return thymeleafViewResolver;
	}

	// configurando resource handlers para arquivos estaticos: css, imagens
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new Interceptor());
	}

	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/pages/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setEnableSpringELCompiler(true);
		Set<IDialect> additionalDialects = new HashSet<IDialect>();
//		additionalDialects.add(dialect());
//		additionalDialects.add(timeDialect());
		templateEngine.setAdditionalDialects(additionalDialects);
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

//	@Bean
//	public SpringSecurityDialect dialect() {
//		return new SpringSecurityDialect();
//	}

//	@Bean
//	public Java8TimeDialect timeDialect() {
//		return new Java8TimeDialect();
//	}
}

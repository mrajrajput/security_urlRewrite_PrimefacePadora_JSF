package com.rajputLife;

//import javax.faces.webapp.FacesServlet;
//import org.ocpsoft.rewrite.servlet.RewriteFilter;
import com.rajputLife.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
//@ComponentScan({"com.auth0.samples.bootfaces"})

@SpringBootApplication
@ComponentScan(basePackages = {"com.rajputLife",
//				"com.rajputLife/*",  //don't remove this!!
		"org.primefaces",
//		"org.primefaces.pandora",
		//"org.primefaces.pandora.view/*",

//		"com.rajputLife.controller/*",
//		"com.rajputLife.controller",
//		"com.rajputLife.entity.security",
//		"com.rajputLife.security.configuration",
//		"com.rajputLife.security.oauth",
//		"com.rajputLife.security.userDetails",
//		"com.rajputLife.repository",
// 		"com.rajputLife.repository.security"
}

			)
public class Application { //extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public ServletRegistrationBean servletRegistrationBean() {
//		FacesServlet servlet = new FacesServlet();
//		return new ServletRegistrationBean(new FacesServlet(), "*.jsf");
//	}
//
////	@Bean
////	public ServletRegistrationBean servletRegistrationBean() {
////		ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new FacesServlet());
////		servletRegistration.addUrlMappings("*.xhtml");
////		servletRegistration.setLoadOnStartup(1);
////		return servletRegistration;
////	}
//
//	@Bean
//	public FilterRegistrationBean rewriteFilter() {
//		FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
//		rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
//				DispatcherType.ASYNC, DispatcherType.ERROR));
//		rwFilter.addUrlPatterns("/*");
//		return rwFilter;
//	}
}

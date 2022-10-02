package com.rajputLife.config;

import com.sun.faces.config.ConfigureListener;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.faces.application.ProjectStage;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Configuration
public class JsfConfig implements ServletContextAware {

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		return new ServletRegistrationBean(new FacesServlet(), "*.jsf");
	}

//	@Bean
//	public ServletRegistrationBean servletRegistrationBean() {
//		ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new FacesServlet());
//		servletRegistration.addUrlMappings("*.xhtml");
//		servletRegistration.setLoadOnStartup(1);
//		return servletRegistration;
//	}

	@Bean
	public FilterRegistrationBean rewriteFilter() {
		FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
		rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
				DispatcherType.ASYNC, DispatcherType.ERROR));
		rwFilter.addUrlPatterns("/*");
		return rwFilter;
	}


//    @Bean
//    public static CustomScopeConfigurer viewScope() {
//
//        return new CustomScopeConfigurer();
//    }


//    @Bean
//    public ServletRegistrationBean<FacesServlet> servletRegistraiton() {
//
//        ServletRegistrationBean bean = new ServletRegistrationBean(new FacesServlet(), "*.xhtml");
//        bean.setLoadOnStartup(1);
//        return bean;
//    }

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new FacesServlet());
//        servletRegistration.addUrlMappings("*.jsf");
//        servletRegistration.setLoadOnStartup(1);
//        return servletRegistration;
//    }
//
//    @Bean
//    public FilterRegistrationBean rewriteFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RewriteFilter());
//        filterRegistrationBean.setDispatcherTypes(
//                EnumSet.of(DispatcherType.FORWARD,
//                        DispatcherType.REQUEST,
//                        DispatcherType.ASYNC,
//                        DispatcherType.ERROR));
//        filterRegistrationBean.addUrlPatterns( "/*");
//        return filterRegistrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean<LoginFilter> loginFilter() {
//        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<>();
//        registration.addUrlPatterns("/");
//        registration.setFilter(new LoginFilter());
//        return registration;
//    }



    @Override
    public void setServletContext(ServletContext sc) {
        sc.addListener(ConfigureListener.class);
        sc.setInitParameter("com.sun.faces.compressJavaScript", FALSE.toString());
        sc.setInitParameter("com.sun.faces.enableClientStateDebugging", TRUE.toString());
        sc.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");
        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", TRUE.toString());
        sc.setInitParameter("com.sun.faces.sendPoweredByHeader", TRUE.toString());
        sc.setInitParameter("facelets.DEVELOPMENT", TRUE.toString());
        sc.setInitParameter("Javax.faces.CONFIG_FILES", "/WEB-INF/faces-config.xml");
        sc.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
        sc.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", FALSE.toString());
        sc.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", TRUE.toString());
        sc.setInitParameter("javax.faces.PROJECT_STAGE", ProjectStage.Development.toString());
        sc.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
        sc.setInitParameter("javax.faces.TRANSFORM_METADATA", FALSE.toString());
        sc.setInitParameter("primefaces.THEME", "nova-light");
    }

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new FacesServlet());
//        servletRegistration.addUrlMappings("*.xhtml");
//        servletRegistration.setLoadOnStartup(1);
//        return servletRegistration;
//    }
//
//    @Bean
//    public FilterRegistrationBean rewriteFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RewriteFilter());
//        filterRegistrationBean.setDispatcherTypes(
//                EnumSet.of(DispatcherType.FORWARD,
//                           DispatcherType.REQUEST,
//                           DispatcherType.ASYNC,
//                           DispatcherType.ERROR));
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
//        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
//        servletContext.setInitParameter("javax.faces.FACELETS_BUFFER_SIZE", "65535"); // 64 kB
//        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
//        servletContext.setInitParameter("primefaces.THEME", "ultima-grey");
//        servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-ultima.taglib.xml");
//        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
//        servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
//        servletContext.setAttribute("javax.faces.STATE_SAVING_METHOD", "client");
//
//    }
}

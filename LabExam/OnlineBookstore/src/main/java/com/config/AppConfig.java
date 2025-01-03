package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.application.Application;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

@Configuration
public class AppConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(Application.class);
		context.setServletContext(servletContext);
		servletContext.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new com.question8.DispatcherServlet());
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

	}
}

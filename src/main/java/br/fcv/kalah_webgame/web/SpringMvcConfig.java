package br.fcv.kalah_webgame.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Module newHibernate5Module() {
		// It seems Jackson's Hibernate module is not automatically registered
		// by Spring Boot.
		// Registering it to avoid issues with lazy loaded properties like, for example an exception with message:
		// `Failed to write HTTP message: org.springframework.http.converter.HttpMessageNotWritableException: Could not write content: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: br.fcv.kalah_webgame.core.Game["player1Board"]->br.fcv.kalah_webgame.core.PlayerBoard_$$_jvstfef_0["handler"]); nested exception is com.fasterxml.jackson.databind.JsonMappingException: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: br.fcv.kalah_webgame.core.Game["player1Board"]->br.fcv.kalah_webgame.core.PlayerBoard_$$_jvstfef_0["handler"])`
		//
		// spring boot supports registering additional modules by exposing them as @Bean
		// see https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring#with-spring-boot
		// see more about configuring Jackson Object Mapper through propertie file at
		// http://docs.spring.io/spring-boot/docs/1.4.2.RELEASE/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper
		Module module = new Hibernate5Module();
		return module;
	}
}

package com.kakao.bookSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan
@Configuration
@SpringBootApplication
public class BookSearchApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}
	
	/**
	 * View Mapping 하기 위한 설정
	 * TODO : resource - application.properties에 설정이 되지 않아 Bean으로 설정 하였다.
	 * @return
	 */
	@Bean
    public InternalResourceViewResolver setupViewResolver() {
 
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
 
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}

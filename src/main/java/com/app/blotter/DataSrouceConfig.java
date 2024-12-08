package com.app.blotter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSrouceConfig {

	@Bean
	AreaCascadeSaveMongoEventListener areaCascadingMongoEventListener() {
		return new AreaCascadeSaveMongoEventListener();
	}
}

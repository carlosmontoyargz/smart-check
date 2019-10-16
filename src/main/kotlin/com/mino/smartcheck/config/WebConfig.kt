package com.mino.smartcheck.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer
{
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(false)
				.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type")
	}

	override fun addFormatters(registry: FormatterRegistry) {
		val dateTimeFormatter = DateTimeFormatterRegistrar()
		dateTimeFormatter.setUseIsoFormat(true)
		dateTimeFormatter.registerFormatters(registry)
	}
}

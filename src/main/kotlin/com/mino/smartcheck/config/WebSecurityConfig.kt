package com.mino.smartcheck.config

import com.mino.smartcheck.security.JwtAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig
	@Autowired constructor(var jwtAuthorizationFilter: JwtAuthorizationFilter):
		WebSecurityConfigurerAdapter()
{
	override fun configure(http: HttpSecurity?) {
		http!!
				.csrf().disable()
				.addFilterAt(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/", "/index.html").permitAll() // FIXME authorize angular
				.antMatchers(HttpMethod.POST, "/users/authenticate", "/users/register").permitAll()
				//.antMatchers(HttpMethod.OPTIONS, "/users/authenticate", "/users/register").permitAll()
				.antMatchers("/users/**", "/checks/**").authenticated()
				.and().cors()
	}

//	@Bean
//	fun corsConfigurationSource(): CorsConfigurationSource =
//			UrlBasedCorsConfigurationSource().apply {
//				registerCorsConfiguration("/**", CorsConfiguration().apply {
//					allowedOrigins = listOf("*")
//					allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
//					// setAllowCredentials(true) is important, otherwise:
//					// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
//					allowCredentials = false
//					// setAllowedHeaders is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
//					allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
//				})
//			}
}

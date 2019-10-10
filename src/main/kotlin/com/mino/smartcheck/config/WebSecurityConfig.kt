package com.mino.smartcheck.config

import com.mino.smartcheck.security.JwtAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig
	@Autowired constructor(var jwtAuthorizationFilter: JwtAuthorizationFilter):
		WebSecurityConfigurerAdapter()
{
	override fun configure(http: HttpSecurity?) {
		http!!.csrf().disable()
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated()
	}
}

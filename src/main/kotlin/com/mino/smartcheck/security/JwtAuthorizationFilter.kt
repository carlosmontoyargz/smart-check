package com.mino.smartcheck.security

import com.mino.smartcheck.config.SmartCheckProperties
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class JwtAuthorizationFilter
	@Autowired constructor(val smartCheckProperties: SmartCheckProperties): OncePerRequestFilter()
{
	private val header = "Authorization"
	private val prefix = "Bearer "

	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain)
	{
		try {
			if (existeJWTToken(request, response)) {
				val claims = validateToken(request)
				if (claims["authorities"] != null) {
					setUpSpringAuthentication(claims)
				} else {
					SecurityContextHolder.clearContext()
				}
			}
			filterChain.doFilter(request, response)
		}
		catch (e: Exception) {
			response.status = HttpServletResponse.SC_FORBIDDEN
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
			return
		}
	}

	private fun validateToken(request: HttpServletRequest): Claims =
			Jwts.parser()
				.setSigningKey(smartCheckProperties.secretKey.toByteArray())
				.parseClaimsJws(request.getHeader(header).replace(prefix, ""))
				.body

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 *
	 * @param claims
	 */
	private fun setUpSpringAuthentication(claims: Claims) {
		SecurityContextHolder.getContext().authentication =
				UsernamePasswordAuthenticationToken(
						claims.subject,
						null,
						(claims["authorities"] as List<*>)
								.stream()
								.map { SimpleGrantedAuthority(it as String) }
								.collect(Collectors.toList()))
	}

	private fun existeJWTToken(request: HttpServletRequest, res: HttpServletResponse): Boolean {
		val authenticationHeader = request.getHeader(header)
		return !(authenticationHeader == null || !authenticationHeader.startsWith(prefix))
	}
}

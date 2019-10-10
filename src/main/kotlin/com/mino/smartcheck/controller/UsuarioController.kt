package com.mino.smartcheck.controller

import com.mino.smartcheck.config.SmartCheckProperties
import com.mino.smartcheck.dto.UsuarioDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors

@RestController
class UsuarioController
	@Autowired constructor(val smartCheckProperties: SmartCheckProperties)
{
	@PostMapping("/user")
	fun login(@RequestParam("user") username: String,
			  @RequestParam("password") password: String) =
			UsuarioDto(user = username, token = getJwtToken(username))

	private fun getJwtToken(username: String) =
			"Bearer ${Jwts.builder()
					.setId("minoJWT")
					.setSubject(username)
					.claim("authorities", AuthorityUtils
							.commaSeparatedStringToAuthorityList("ROLE_USER")
							.stream()
							.map { it.authority }
							.collect(Collectors.toList()))
					.setIssuedAt(Date(System.currentTimeMillis()))
					.setExpiration(Date(System.currentTimeMillis() + 600000))
					.signWith(SignatureAlgorithm.HS512, smartCheckProperties.secretKey.toByteArray())
					.compact()}"
}

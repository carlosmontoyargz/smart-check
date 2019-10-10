package com.mino.smartcheck.controller

import com.mino.smartcheck.config.SmartCheckProperties
import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.service.UsuarioService
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
	@Autowired constructor(
			val usuarioService: UsuarioService,
			val smartCheckProperties: SmartCheckProperties)
{
	@PostMapping("/user")
	fun login(@RequestParam("user") username: String,
			  @RequestParam("password") password: String): UsuarioDto?
	{
		val usuario = usuarioService.cargarUsuario(username, password)
		return if (usuario == null) null
		else UsuarioDto(
				user = usuario.correo,
				token = getJwtToken(usuario.correo))
	}

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

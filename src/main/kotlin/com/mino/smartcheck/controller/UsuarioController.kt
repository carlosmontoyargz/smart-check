package com.mino.smartcheck.controller

import com.mino.smartcheck.config.SmartCheckProperties
import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Rol
import com.mino.smartcheck.model.Usuario
import com.mino.smartcheck.service.UsuarioService
import com.mino.smartcheck.service.UsuarioServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/users")
class UsuarioController
	@Autowired constructor(
			val usuarioService: UsuarioService,
			val smartCheckProperties: SmartCheckProperties)
{
	@GetMapping("")
	fun obtenerTodos(): List<UsuarioDto> =
			usuarioService
					.obtenerTodos()
					.map { usuario -> UsuarioDto(
							username = usuario.correo,
							password = usuario.contrasena,
							firstName = usuario.correo + " nombre",
							lastName = usuario.correo + " apellido") }

	@PostMapping("/authenticate")
	fun login(@RequestBody request: UsuarioDto): UsuarioDto?
	{
		val usuario = usuarioService.obtenerUsuario(request.username!!, request.password!!)
		return if (usuario == null) null
		else UsuarioDto(
				username = usuario.correo,
				firstName = "FirstName",
				lastName = "LastName",
				token = getJwtToken(usuario.correo))
	}

	@PostMapping("/register")
	fun register(@RequestBody request: UsuarioDto): ResponseEntity<*>
	{
		return try {
			val usuario = Usuario()
			usuario.correo = request.username
			usuario.contrasena = request.password
			val rol = Rol()
			rol.nombre = "admin"
			usuario.rol = rol

			usuarioService.registrarUsuario(usuario)

			ResponseEntity.ok().body("Usuario creado correctamente")
		}
		catch (e: SignUpException) { ResponseEntity.badRequest().body(e.message) }
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

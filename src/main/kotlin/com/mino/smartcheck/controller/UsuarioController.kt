package com.mino.smartcheck.controller

import com.mino.smartcheck.config.SmartCheckProperties
import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Rol
import com.mino.smartcheck.model.Usuario
import com.mino.smartcheck.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsuarioController
	@Autowired constructor(
			val usuarioService: UsuarioService,
			val modelMapper: ModelMapper,
			val smartCheckProperties: SmartCheckProperties)
{
	@GetMapping("/{id}") // FIXME retornar DTO
	fun getUser(@PathVariable("id") id: Int) =
			usuarioService.obtenerUsuario(id).apply { this!!.contrasena = "" }

	@GetMapping("")
	fun obtenerTodos(): List<UsuarioDto> = usuarioService
			.obtenerTodos()
			.map { usuario -> UsuarioDto(
					username = usuario.correo,
					rolNombre = usuario.rol.nombre) }

	@PostMapping("/authenticate")
	fun login(@Valid @RequestBody request: UsuarioDto): UsuarioDto?
	{
		val usuario = usuarioService.obtenerUsuario(request.username!!, request.password!!)
		return if (usuario == null) null
		else UsuarioDto(
				username = usuario.correo,
				token = getJwtToken(usuario.correo))
	}

	@PostMapping("/register")
	fun register(@Valid @RequestBody usuarioDto: UsuarioDto, uriBuilder: UriComponentsBuilder):
			ResponseEntity<*>
	{
		return try {
			val usuario = Usuario()
			usuario.correo = usuarioDto.username
			usuario.contrasena = usuarioDto.password
			usuario.rol = Rol()
			usuario.rol.nombre = usuarioDto.rolNombre

			val r = usuarioService.registrarUsuario(usuario)
			ResponseEntity
					.created(uriBuilder.path("/users/${r.id}").build().toUri())
					.build<URI>()
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

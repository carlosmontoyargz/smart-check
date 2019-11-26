package com.mino.smartcheck.controller

import com.mino.smartcheck.config.SmartCheckProperties
import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Usuario
import com.mino.smartcheck.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/")
@CrossOrigin("*")
class RegistroController
	@Autowired constructor(val usuarioService: UsuarioService,
						   val modelMapper: ModelMapper)
{
	@PostMapping("/registro")
	fun register(@Valid @RequestBody usuarioDto: UsuarioDto,
				 uriBuilder: UriComponentsBuilder): ResponseEntity<*> {
		return try {
			val u = usuarioService
					.registrarUsuario(
							modelMapper.map(usuarioDto, Usuario::class.java))
			ResponseEntity
					.created(uriBuilder
							.path("/usuarios/${u.id}").build()
							.toUri())
					.build<Any>()
		} catch (e: SignUpException) {
			ResponseEntity.badRequest().body(e.message)
		}
	}

//	@GetMapping("")
//	fun obtenerTodos(): List<UsuarioDto> = usuarioService
//			.obtenerTodos()
//			.map { modelMapper.map(it, UsuarioDto::class.java) }
//
//	@GetMapping("/{id}")
//	fun getById(@PathVariable("id") id: Int): UsuarioDto? =
//			usuarioService
//					.obtenerUsuario(id)
//					.map { modelMapper.map(it, UsuarioDto::class.java) }
//					.orElse(null)
//
//	@PostMapping("/authenticate1")
//	fun login(@Valid @RequestBody request: UsuarioDto): UsuarioDto? =
//			usuarioService
//					.obtenerUsuario(request.username!!, request.password!!)
//					.map { modelMapper
//							.map(it, UsuarioDto::class.java)
//							.apply { token = getJwtToken(username!!) }
//					}
//					.orElse(null)
//
//	private fun getJwtToken(username: String) =
//			"Bearer ${Jwts.builder()
//					.setId("minoJWT")
//					.setSubject(username)
//					.claim("authorities", AuthorityUtils
//							.commaSeparatedStringToAuthorityList("ROLE_USER")
//							.stream()
//							.map { it.authority }
//							.collect(Collectors.toList()))
//					.setIssuedAt(Date(System.currentTimeMillis()))
//					.setExpiration(Date(System.currentTimeMillis() + 60000000))
//					.signWith(SignatureAlgorithm.HS512, smartCheckProperties.secret.toByteArray())
//					.compact()}"
}

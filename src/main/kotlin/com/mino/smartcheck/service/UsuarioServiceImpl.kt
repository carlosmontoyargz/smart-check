package com.mino.smartcheck.service

import com.mino.smartcheck.data.HorasTrabajoRepository
import com.mino.smartcheck.data.OrganizacionRepository
import com.mino.smartcheck.data.RolRepository
import com.mino.smartcheck.data.UsuarioRepository
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Usuario
import jdk.nashorn.internal.runtime.options.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class UsuarioServiceImpl
	@Autowired constructor(
			val usuarioRepository: UsuarioRepository,
			val rolRepository: RolRepository,
			val organizacionRepository: OrganizacionRepository,
			val passwordEncoder: PasswordEncoder)
	: UsuarioService
{
	override fun obtenerUsuario(id: Int) = usuarioRepository.findById(id)

	override fun obtenerUsuario(username: String, password: String): Optional<Usuario> =
			usuarioRepository
					.findByUsername(username)
					.filter { u -> u.password == password }

	@Throws(SignUpException::class)
	override fun registrarUsuario(usuario: Usuario): Usuario
	{
		if (usuarioRepository.existsByUsername(usuario.username!!))
			throw SignUpException("El usuario ya existe")

		usuario.rol = rolRepository.findByNombre(usuario.rol?.nombre.toString())
				.orElseThrow { SignUpException("No existe el rol especificado") }

		usuario.organizacion = organizacionRepository.findById(1)
				.orElseThrow { RuntimeException("No existen datos de organizacion") }

		usuario.password = passwordEncoder.encode(usuario.password)

		return usuarioRepository.save(usuario)
	}
}

package com.mino.smartcheck.service

import com.mino.smartcheck.data.RolRepository
import com.mino.smartcheck.data.UsuarioRepository
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Usuario
import jdk.nashorn.internal.runtime.options.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioServiceImpl
	@Autowired constructor(
			val usuarioRepository: UsuarioRepository,
			val rolRepository: RolRepository) : UsuarioService
{
	override fun obtenerTodos(): List<Usuario> = usuarioRepository.findAll()

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

		usuario.rol = rolRepository
				.findByNombre(usuario.rol?.nombre.toString())
				.orElseThrow { SignUpException("No existe el rol especificado") }

		return usuarioRepository.save(usuario)
	}
}

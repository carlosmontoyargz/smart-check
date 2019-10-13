package com.mino.smartcheck.service

import com.mino.smartcheck.data.RolRepository
import com.mino.smartcheck.data.UsuarioRepository
import com.mino.smartcheck.error.SignUpException
import com.mino.smartcheck.model.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl
	@Autowired constructor(
			val usuarioRepository: UsuarioRepository,
			val rolRepository: RolRepository) : UsuarioService
{
	override fun obtenerTodos(): List<Usuario> = usuarioRepository.findAll()

	override fun obtenerUsuario(id: Int): Usuario? =
			usuarioRepository.findById(id).orElse(null)

	override fun obtenerUsuario(username: String, password: String): Usuario?
	{
		val usuario = usuarioRepository
				.findByCorreo(username)
				.orElse(null) ?: return null
		return if (usuario.contrasena == password) usuario else null
	}

	@Throws(SignUpException::class)
	override fun registrarUsuario(usuario: Usuario): Usuario
	{
		if (usuarioRepository.existsByCorreo(usuario.correo))
			throw SignUpException("El usuario ya existe")

		usuario.rol = rolRepository
				.findByNombre(usuario.rol.nombre)
				.orElseThrow { SignUpException("No existe el rol especificado") }

		return usuarioRepository.save(usuario)
	}
}

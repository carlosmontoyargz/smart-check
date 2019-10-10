package com.mino.smartcheck.service

import com.mino.smartcheck.data.UsuarioRepository
import com.mino.smartcheck.model.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService
	@Autowired constructor(val usuarioRepository: UsuarioRepository)
{
	fun cargarUsuario(username: String, password: String): Usuario? {
		val usuario = usuarioRepository
				.findByCorreo(username)
				.orElse(null) ?: return null
		return if (usuario.contrasena == password) usuario else null
	}
}

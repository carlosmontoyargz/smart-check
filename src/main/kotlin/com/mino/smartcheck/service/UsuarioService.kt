package com.mino.smartcheck.service

import com.mino.smartcheck.model.Usuario
import java.util.*

interface UsuarioService
{
	fun obtenerTodos(): List<Usuario>
	fun obtenerUsuario(id: Int): Optional<Usuario>
	fun obtenerUsuario(username: String, password: String): Optional<Usuario>
	fun registrarUsuario(usuario: Usuario): Usuario
}

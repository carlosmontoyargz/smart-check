package com.mino.smartcheck.service

import com.mino.smartcheck.model.Usuario

interface UsuarioService
{
	fun obtenerTodos(): List<Usuario>
	fun obtenerUsuario(id: Int): Usuario?
	fun obtenerUsuario(username: String, password: String): Usuario?
	fun registrarUsuario(usuario: Usuario): Usuario
}

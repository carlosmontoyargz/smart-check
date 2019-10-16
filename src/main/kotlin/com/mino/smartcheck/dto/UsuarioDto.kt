package com.mino.smartcheck.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Email

open class UsuarioDto
{
	var id: Int? = null

	@NotNull
	@Email
	var username: String? = null

	@NotNull
	open var password: String? = null

	var firstName: String? = ""
	var lastName: String? = ""
	var token: String? = null
	var rolNombre: String? = null

	override fun toString(): String {
		return "UsuarioDto(id=$id, username=$username, password=$password, firstName=$firstName, lastName=$lastName, token=$token, rolNombre=$rolNombre)"
	}
}

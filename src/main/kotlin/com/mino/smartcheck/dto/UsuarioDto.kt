package com.mino.smartcheck.dto

import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Email

data class UsuarioDto
(
	@NotNull
	@Email
	var username: String? = null,

	@NotNull
	var password: String? = null,
	var firstName: String? = "",
	var lastName: String? = "",
	var token: String? = null,
	var rolNombre: String? = null
)
/*
data class RolDto
(
	var nombre: String? = "",
	var descripcion: String? = ""
)
*/

package com.mino.smartcheck.model

import javax.persistence.*

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
class Usuario
{
	@Id @GeneratedValue
	val id: Int = 0

	@Column(name = "correo", unique = true, nullable = false)
	var username: String? = null

	@Column(name = "contrasena", nullable = false)
	var password: String? = null

	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
	var rol: Rol? = null

	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
	var organizacion: Organizacion? = null

	override fun toString(): String {
		return "Usuario(id=$id, username=$username, password=$password, rol=$rol, organizacion=$organizacion)"
	}
}

package com.mino.smartcheck.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
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

	@JsonIgnore
	@Column(name = "contrasena", nullable = false)
	var password: String? = null

	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
	var rol: Rol? = null

	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
	var organizacion: Organizacion? = null

//	@CreatedDate
	var creado: LocalDateTime? = LocalDateTime.now()

	override fun toString(): String {
		return "Usuario(id=$id, username=$username, password=$password, rol=$rol, organizacion=$organizacion)"
	}
}

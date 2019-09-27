package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Data
public class Usuario
{
	@Id
	@GeneratedValue
	private Integer id;
	private String correo;
	private String contrasena;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Organizacion organizacion;

	@ManyToOne
	private Rol rol;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Usuario persona = (Usuario) o;

		return id != null ? id.equals(persona.id) : persona.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

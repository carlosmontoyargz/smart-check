package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Persona
{
	@Id
	@GeneratedValue
	private Integer id;
	private String correo;
	private String contrasena;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Organizacion organizacion;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Persona persona = (Persona) o;

		return id != null ? id.equals(persona.id) : persona.id == null;
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

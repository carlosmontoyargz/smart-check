package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario
{
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Integer id;
	private String correo;
	private String contrasena;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Organizacion organizacion;

	@ManyToOne
	private Rol rol;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getCorreo()
	{
		return correo;
	}

	public void setCorreo(String correo)
	{
		this.correo = correo;
	}

	public String getContrasena()
	{
		return contrasena;
	}

	public void setContrasena(String contrasena)
	{
		this.contrasena = contrasena;
	}

	public Organizacion getOrganizacion()
	{
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion)
	{
		this.organizacion = organizacion;
	}

	public Rol getRol()
	{
		return rol;
	}

	public void setRol(Rol rol)
	{
		this.rol = rol;
	}
}

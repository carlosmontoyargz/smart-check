package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Carlos Montoya
 * @since 27/09/2019
 */
@Entity
public class Rol
{
	@Id
	@GeneratedValue
	private Integer id;

	private String nombre;
	private String descripcion;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "rol_privilegio",
			joinColumns = @JoinColumn(name = "rol_id"),
			inverseJoinColumns = @JoinColumn(name = "privilegio_id"))
	private Set<Privilegio> privilegios;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public Set<Privilegio> getPrivilegios()
	{
		return privilegios;
	}

	public void setPrivilegios(Set<Privilegio> privilegios)
	{
		this.privilegios = privilegios;
	}

	@Override
	public String toString()
	{
		return "Rol{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", privilegios=" + privilegios +
				'}';
	}
}

package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
public class Organizacion
{
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private LocalTime horaEntrada;

	@Column(nullable = false)
	private LocalTime horaSalida;

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

	public LocalTime getHoraEntrada()
	{
		return horaEntrada;
	}

	public void setHoraEntrada(LocalTime horaEntrada)
	{
		this.horaEntrada = horaEntrada;
	}

	public LocalTime getHoraSalida()
	{
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida)
	{
		this.horaSalida = horaSalida;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organizacion that = (Organizacion) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

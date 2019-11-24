package com.mino.smartcheck.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Carlos Montoya
 * @since 16/11/2019
 */
@Entity
public class HorasTrabajo
{
	@Id
	@GeneratedValue
	private int id;

	private LocalDate fechaInicio;

	private LocalDate fechaFinal;

	@ManyToOne
	private Usuario usuario;

	private Long minutos;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public LocalDate getFechaInicio()
	{
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio)
	{
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal()
	{
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal)
	{
		this.fechaFinal = fechaFinal;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public Long getMinutos()
	{
		return minutos;
	}

	public void setMinutos(Long minutos)
	{
		this.minutos = minutos;
	}
}

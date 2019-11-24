package com.mino.smartcheck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
public class SmartCheck
{
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Integer id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario empleado;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoCheck tipo;

	@Column(nullable = false)
	private LocalDateTime creado;

	private LocalTime horaBase;

	private Long diferenciaMinutos;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private HorasTrabajo horasTrabajo;

	public void asignarDiferenciaMinutos()
	{
		if (horaBase != null)
			diferenciaMinutos = Duration
					.between(horaBase, creado.toLocalTime())
					.toMinutes();
		else
			diferenciaMinutos = 0L;
	}

	public HorasTrabajo getHorasTrabajo()
	{
		return horasTrabajo;
	}

	public void setHorasTrabajo(HorasTrabajo horasTrabajo)
	{
		this.horasTrabajo = horasTrabajo;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Usuario getEmpleado()
	{
		return empleado;
	}

	public void setEmpleado(Usuario empleado)
	{
		this.empleado = empleado;
	}

	public TipoCheck getTipo()
	{
		return tipo;
	}

	public void setTipo(TipoCheck tipo)
	{
		this.tipo = tipo;
	}

	public LocalDateTime getCreado()
	{
		return creado;
	}

	public void setCreado(LocalDateTime creado)
	{
		this.creado = creado;
	}

	public LocalTime getHoraBase()
	{
		return horaBase;
	}

	public void setHoraBase(LocalTime horaBase)
	{
		this.horaBase = horaBase;
	}

	public Long getDiferenciaMinutos()
	{
		return diferenciaMinutos;
	}

	public void setDiferenciaMinutos(Long diferenciaMinutos)
	{
		this.diferenciaMinutos = diferenciaMinutos;
	}
}

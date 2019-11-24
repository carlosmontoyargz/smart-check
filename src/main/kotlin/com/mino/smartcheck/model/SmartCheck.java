package com.mino.smartcheck.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private LocalDate fecha;

	@Column(nullable = false)
	private LocalTime hora;

	private LocalTime horaBase;

	private Long diferenciaMinutos;

	public void asignarDiferenciaMinutos()
	{
		if (horaBase != null)
			diferenciaMinutos = Duration.between(horaBase, hora).toMinutes();
		else
			diferenciaMinutos = 0L;
	}

	public LocalDateTime getLocalDateTime() {
		return LocalDateTime.of(fecha, hora);
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

	public LocalDate getFecha()
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha)
	{
		this.fecha = fecha;
	}

	public LocalTime getHora()
	{
		return hora;
	}

	public void setHora(LocalTime hora)
	{
		this.hora = hora;
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

	public TipoCheck getTipo()
	{
		return tipo;
	}

	public void setTipo(TipoCheck tipo)
	{
		this.tipo = tipo;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SmartCheck that = (SmartCheck) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

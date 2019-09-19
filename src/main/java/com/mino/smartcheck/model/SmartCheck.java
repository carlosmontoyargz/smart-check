package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Data
public class SmartCheck
{
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empleado empleado;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime time;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoCheck tipoCheck;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SmartCheck check = (SmartCheck) o;

		return Objects.equals(id, check.id);
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

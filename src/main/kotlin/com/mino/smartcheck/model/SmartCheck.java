package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
	private LocalDate fecha;

	@Column(nullable = false)
	private LocalTime hora;

	private LocalTime horaBase;

	private Long diferenciaMinutos;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoCheck tipoCheck;

	@PreUpdate
	public void asignarDiferenciaMinutos()
	{
		diferenciaMinutos = Duration.between(horaBase, hora).toMinutes();
	}
}

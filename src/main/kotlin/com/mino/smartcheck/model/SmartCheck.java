package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoCheck tipoCheck;
}

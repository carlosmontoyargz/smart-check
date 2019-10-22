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
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
}

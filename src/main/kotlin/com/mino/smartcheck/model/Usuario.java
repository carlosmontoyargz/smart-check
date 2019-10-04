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
@Data
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
}

package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Carlos Montoya
 * @since 27/09/2019
 */
@Entity
@Data
public class Rol
{
	@Id
	@GeneratedValue
	private Integer id;

	private String nombre;
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Privilegio> privilegios;
}

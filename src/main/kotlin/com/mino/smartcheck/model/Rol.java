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

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "rol_privilegio",
			joinColumns = @JoinColumn(name = "rol_id"),
			inverseJoinColumns = @JoinColumn(name = "privilegio_id"))
	private Set<Privilegio> privilegios;
}

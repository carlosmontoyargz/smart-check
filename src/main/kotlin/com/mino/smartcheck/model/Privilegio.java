package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Carlos Montoya
 * @since 27/09/2019
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Privilegio
{
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Integer id;

	private String clave;
}

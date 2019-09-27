package com.mino.smartcheck.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Carlos Montoya
 * @since 27/09/2019
 */
@Entity
public class Privilegio
{
	@Id
	@GeneratedValue
	private Integer id;

	private String clave;
}

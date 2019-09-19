package com.mino.smartcheck.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Data
public class Organizacion
{
	@Id
	@GeneratedValue
	private Integer id;

	private String nombre;

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organizacion that = (Organizacion) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}

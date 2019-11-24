package com.mino.smartcheck.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Carlos Montoya
 * @since 16/11/2019
 */
@Entity
public class TiempoTrabajo
{
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private SmartCheck checkEntrada;

	@ManyToOne
	private SmartCheck checkSalida;

	private Integer minutosTrabajados;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SmartCheck getCheckEntrada()
	{
		return checkEntrada;
	}

	public void setCheckEntrada(SmartCheck checkEntrada)
	{
		this.checkEntrada = checkEntrada;
	}

	public SmartCheck getCheckSalida()
	{
		return checkSalida;
	}

	public void setCheckSalida(SmartCheck checkSalida)
	{
		this.checkSalida = checkSalida;
	}

	public Integer getMinutosTrabajados()
	{
		return minutosTrabajados;
	}

	public void setMinutosTrabajados(Integer minutosTrabajados)
	{
		this.minutosTrabajados = minutosTrabajados;
	}
}

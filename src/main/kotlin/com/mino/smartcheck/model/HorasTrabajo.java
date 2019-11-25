package com.mino.smartcheck.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Montoya
 * @since 16/11/2019
 */
@Entity
public class HorasTrabajo
{
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private Usuario usuario;

	@OneToMany(
			mappedBy = "horasTrabajo",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<SmartCheck> checks = new ArrayList<>();

	private LocalDate fechaInicio;
	private LocalDate fechaFinal;
	private Long minutos = 0L;
	private Long extras = 0L;
	private Long retrasos = 0L;
	private Long salidasTemprano = 0L;
	private Long entradasTemprano = 0L;

	public void addCheck(SmartCheck check) {
		checks.add(check);
		check.setHorasTrabajo(this);
	}

	public List<SmartCheck> getChecks() {
		return checks;
	}

	public void setChecks(List<SmartCheck> checks) {
		this.checks = checks;
	}

	public Long getEntradasTemprano() {
		return entradasTemprano;
	}

	public void setEntradasTemprano(Long entradasTemprano) {
		this.entradasTemprano = entradasTemprano;
	}

	public Long getRetrasos() {
		return retrasos;
	}

	public void setRetrasos(Long retrasos) {
		this.retrasos = retrasos;
	}

	public Long getSalidasTemprano() {
		return salidasTemprano;
	}

	public void setSalidasTemprano(Long salidasTemprano) {
		this.salidasTemprano = salidasTemprano;
	}

	public Long getExtras() {
		return extras;
	}

	public void setExtras(Long extras) {
		this.extras = extras;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal)
	{
		this.fechaFinal = fechaFinal;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public Long getMinutos()
	{
		return minutos;
	}

	public void setMinutos(Long minutos)
	{
		this.minutos = minutos;
	}
}

package com.mino.smartcheck.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalTime;

/**
 * @author Carlos Montoya
 * @since 23/11/2019
 */
@Projection(name = "datos", types = { Usuario.class })
public interface UsuarioDatos
{
	Integer getId();

	String getUsername();

	@Value("#{target.username}")
	String getFirstName();

	@Value("#{target.username}")
	String getLastName();

	@Value("#{target.rol.nombre}")
	String getRolNombre();

	@Value("#{target.organizacion.nombre}")
	String getOrganizacionNombre();

	@Value("#{target.organizacion.horaEntrada}")
	LocalTime getOrganizacionHoraEntrada();

	@Value("#{target.organizacion.horaSalida}")
	LocalTime getOrganizacionHoraSalida();
}

package com.mino.smartcheck.data

import com.mino.smartcheck.model.HorasTrabajo
import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.util.Supplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.time.Duration

import java.time.LocalDateTime
import kotlin.math.absoluteValue

/**
 * @author Carlos Montoya
 * @since 16/11/2019
 */
@Component
@RepositoryEventHandler
class BeforeCheckSaveHandler
	@Autowired constructor(
			val usuarioRepository: UsuarioRepository,
			val checkRepository: CheckRepository,
			val horasTrabajoRepository: HorasTrabajoRepository)
{
	@HandleBeforeCreate
	fun handleCheckSave(check: SmartCheck) {
		asignarDatosCheck(check)
		actualizarEstadisticasTrabajo(check)
	}

	private fun asignarDatosCheck(check: SmartCheck) {
		check.creado = LocalDateTime.now()
		check.empleado = usuarioRepository
				.findByUsername(
						(SecurityContextHolder.getContext()
								.authentication.principal as User).username)
				.orElseThrow { RuntimeException("No se encontro el usuario") }
		check.horaBase = when (check.tipo) {
			ENTRADA -> check.empleado.organizacion!!.horaEntrada
			SALIDA -> check.empleado.organizacion!!.horaSalida
			else -> null
		}
		val firstDayMonth = check.creado.toLocalDate().withDayOfMonth(1)
		check.horasTrabajo = horasTrabajoRepository
				.findByFechaInicioAndPrincipal(firstDayMonth)
				.orElseGet {
					HorasTrabajo().apply {
						usuario = check.empleado
						fechaInicio = firstDayMonth
						fechaFinal = firstDayMonth.withDayOfMonth(
								firstDayMonth.lengthOfMonth())
					}
				}
		check.asignarDiferenciaMinutos()
	}

	private fun actualizarEstadisticasTrabajo(check: SmartCheck) {
		// Se guardan las estadisticas
		log.info("Inicia actualizacion de horas de trabajo")
		if (check.tipo == ENTRADA) {
			if (check.diferencia < 0) {
				check.horasTrabajo.entradasTemprano +=
						check.diferencia.absoluteValue
			}
			else if (check.diferencia > 0) {
				check.horasTrabajo.retrasos += check.diferencia
			}
		}
		else if (check.tipo == SALIDA) {
			if (check.diferencia < 0) {
				check.horasTrabajo.salidasTemprano +=
						check.diferencia.absoluteValue
			}
			else if (check.diferencia > 0) {
				check.horasTrabajo.extras += check.diferencia
			}
			// Se guardan las horas de trabajo
			val chkEntrada = checkRepository
					.findFirstByTipoOrderByCreadoDesc(ENTRADA)
					.orElseThrow { val ex = RuntimeException("No hay check de entrada"); ex }
			check.horasTrabajo.minutos += Duration
					.between(chkEntrada.creado, check.creado).toMinutes()

			log.info("Minutos de trabajo actualizados {}", Supplier { check.horasTrabajo.minutos })
		}
	}

	companion object { private val log = LogManager.getLogger() }
}

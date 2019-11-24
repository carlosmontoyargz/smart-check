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
		val now = LocalDateTime.now()
		val firstDayMonth = now.toLocalDate().withDayOfMonth(1)
		val usuario = usuarioRepository
				.findByUsername(
						(SecurityContextHolder.getContext()
								.authentication.principal as User)
								.username)
				.orElseThrow { RuntimeException("No se encontro el usuario") }

		// Se asignan las horas del check
		check.creado = now
		check.empleado = usuario
		check.horaBase = when (check.tipo) {
			ENTRADA -> check.empleado.organizacion!!.horaEntrada
			SALIDA -> check.empleado.organizacion!!.horaSalida
			else -> null
		}
		check.horasTrabajo = horasTrabajoRepository
				.findFirstByFechaInicioAndUsuario(firstDayMonth, usuario)
				.orElseGet {
					HorasTrabajo().apply {
						minutos = 0
						this.usuario = usuario
						fechaInicio = firstDayMonth
						fechaFinal = firstDayMonth
								.withDayOfMonth(firstDayMonth.lengthOfMonth())
					}
				}
		check.asignarDiferenciaMinutos()

		if (check.tipo == SALIDA) {
			log.info("Inicia guardado de horas de trabajo")
			val chkEntrada = checkRepository
					.findFirstByTipoOrderByCreadoDesc(ENTRADA)
					.orElseThrow { val ex = RuntimeException("No hay check de entrada"); ex }

			check.horasTrabajo.minutos += Duration
					.between(chkEntrada.creado, check.creado)
					.toMinutes()
			log.info("Minutos de trabajo actualizados {}", Supplier { check.horasTrabajo.minutos })
		}
	}

	companion object {
		private val log = LogManager.getLogger()
	}
}

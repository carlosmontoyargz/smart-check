package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TiempoTrabajo
import com.mino.smartcheck.model.TipoCheck.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.stereotype.Component
import java.time.Duration

import java.time.LocalDate
import java.time.LocalTime

/**
 * @author Carlos Montoya
 * @since 16/11/2019
 */
@Component
@RepositoryEventHandler
class CheckEventHandler
	@Autowired constructor(
			val tiempoTrabajoRepository: TiempoTrabajoRepository)
{
	@HandleBeforeCreate
	fun handleCheckSave(check: SmartCheck) {
		// Se asignan las horas del check
		check.fecha = LocalDate.now()
		check.hora = LocalTime.now()
		check.horaBase = when (check.tipo) {
			ENTRADA -> check.empleado.organizacion!!.horaEntrada
			SALIDA -> check.empleado.organizacion!!.horaSalida
			else -> null
		}
		check.asignarDiferenciaMinutos()

		// Se registra la entrada en el tiempo de trabajo
		if (check.tipo == ENTRADA) {
			// Si el check es de entrada se crea el tiempo de trabajo
			tiempoTrabajoRepository.save(TiempoTrabajo().apply {
				checkEntrada = check
				minutosTrabajados = null
			})
		}
		else if (check.tipo == SALIDA) {
			// Si el check es de salida se actualizan las horas trabajadas
			val tt = tiempoTrabajoRepository
					.findFirstByCheckEntrada_EmpleadoAndMinutosTrabajadosIsNull(check.empleado)
					.orElseThrow { RuntimeException("No existe el check de entrada") }
					.apply {
						checkSalida = check
						minutosTrabajados = Duration
								.between(checkEntrada.localDateTime, check.localDateTime)
								.toMinutes().toInt()
					}
			tiempoTrabajoRepository.save(tt)
		}
	}
}

package com.mino.smartcheck

import com.mino.smartcheck.data.*
import com.mino.smartcheck.model.*
import com.mino.smartcheck.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class LoadSampleData
	@Autowired constructor(
			val usuarioService: UsuarioService,
			val checkRepository: CheckRepository,
			val usuarioRepository: UsuarioRepository,
			val rolRepository: RolRepository,
			val checkSaveHandler: BeforeCheckSaveHandler,
			val organizacionRepository: OrganizacionRepository)
	: ApplicationListener<ApplicationReadyEvent>
{
	val adminUsername = "carlos.montoya.rdgz@gmail.com"

	@Transactional
	override fun onApplicationEvent(event: ApplicationReadyEvent) {
		guardarOrganizacion()
		guardarDatos()
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected fun guardarOrganizacion() {
		organizacionRepository
				.save(Organizacion().apply {
					nombre = "BUAP - FCC"
					horaEntrada = LocalTime.of(8, 0)
					horaSalida = LocalTime.of(17, 0)
				})
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	protected fun guardarDatos() {
		// Roles
		rolRepository.save(Rol().apply {
			nombre = "ADMIN"
			descripcion = "Administrador"
		})
		rolRepository.save(Rol().apply {
			nombre = "EMPLEADO"
			descripcion = "Empleado"
		})

		// Administrador
		usuarioService
				.registrarUsuario(Usuario().apply {
					username = adminUsername
					password = "sipirili"
					rol = Rol().apply { nombre = "ADMIN" }
				})

		// Empleados
		for (i in 1 until 12)
			usuarioService
					.registrarUsuario(Usuario().apply {
						username = "empleado$i@gmail.com"
						password = "empleado$i"
						rol = Rol().apply { nombre = "EMPLEADO" }
					})

		// Checks
		usuarioRepository.findAll().forEach { u ->
			for (i in 1..25) {
//				guardarCheckEntrada(u, i)
				val entrada = SmartCheck().apply {
					empleado = u
					creado = LocalDateTime.of(
							LocalDate.now().withDayOfMonth(i),
							u.organizacion!!.horaEntrada.plusMinutes(
									(-28..28).shuffled().first().toLong()))
					tipo = TipoCheck.ENTRADA
					horaBase = u.organizacion!!.horaEntrada
					asignarDiferenciaMinutos()
				}
				checkSaveHandler.handleCheckSaveSinValidar(entrada)
				checkRepository.save(entrada)

				val salida = SmartCheck().apply {
					empleado = u
					creado = LocalDateTime.of(
							LocalDate.now().withDayOfMonth(i),
							u.organizacion!!.horaSalida.plusMinutes(
									(-28..28).shuffled().first().toLong()))
					tipo = TipoCheck.SALIDA
					horaBase = u.organizacion!!.horaSalida
					asignarDiferenciaMinutos()
				}
				checkSaveHandler.handleCheckSaveSinValidar(salida)
				checkRepository.save(salida)
			}
		}
	}

	private fun guardarCheckEntrada(u: Usuario, i: Int) {}
}

package com.mino.smartcheck.data

import com.mino.smartcheck.model.HorasTrabajo
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(path = "horasTrabajo", collectionResourceRel = "horasTrabajo")
@CrossOrigin(origins = ["*"])
interface HorasTrabajoRepository: JpaRepository<HorasTrabajo, Int>
{
	@Query("""
		FROM HorasTrabajo ht
		WHERE
			ht.fechaInicio = :inicio AND 
			ht.usuario.username = ?#{principal.username}
	""")
	fun findByFechaInicioAndPrincipal(
			@DateTimeFormat(iso = ISO.DATE) inicio: LocalDate): Optional<HorasTrabajo>

//	@Query("SELECT SUM(tt.minutos) " +
//			"FROM HorasTrabajo tt WHERE tt.checkEntrada.fecha >= :desde")
//	fun findTotalMinutosTrabajados(desde: LocalDate): Int
}

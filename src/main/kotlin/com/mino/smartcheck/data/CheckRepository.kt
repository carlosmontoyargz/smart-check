package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(path = "checks", collectionResourceRel = "checks")
@CrossOrigin(origins = ["*"])
interface CheckRepository: JpaRepository<SmartCheck, Int>
{
	fun findFirstByTipoOrderByCreadoDesc(tipo: TipoCheck): Optional<SmartCheck>

	@Query("""
		SELECT sc
		FROM SmartCheck sc
		WHERE 
			sc.creado > :#{#fecha.atStartOfDay()} AND 
			sc.creado < :#{#fecha.plusDays(1).atStartOfDay()} AND
			sc.empleado.username = ?#{principal.username}
	""")
	fun findMyChecksFrom(fecha: LocalDate): List<SmartCheck>

	@Query("SELECT SUM(sc.diferenciaMinutos) " +
			"FROM SmartCheck sc " +
			"WHERE sc.diferenciaMinutos > 0 AND sc.tipo = :tipo")
	fun findTotalPositivo(tipo: TipoCheck): Int

	//fun findByCreadoGreaterThanEqual(from: LocalDate): List<SmartCheck>
}

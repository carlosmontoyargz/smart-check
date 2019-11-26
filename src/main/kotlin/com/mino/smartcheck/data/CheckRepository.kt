package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RepositoryRestResource(path = "checks", collectionResourceRel = "checks")
@CrossOrigin(origins = ["*"])
interface CheckRepository: JpaRepository<SmartCheck, Int>
{
	@Query("""
		SELECT sc
		FROM SmartCheck sc
		WHERE 
			sc.creado > :#{#fecha.atStartOfDay()} AND 
			sc.creado < :#{#fecha.plusDays(1).atStartOfDay()} AND
			sc.empleado.username = ?#{principal.username}
	""")
	fun findMyChecksFrom(@DateTimeFormat(iso = ISO.DATE) fecha: LocalDate)
			: List<SmartCheck>

	fun findFirstByTipoAndEmpleadoOrderByCreadoDesc(tipo: TipoCheck, usuario: Usuario)
			: Optional<SmartCheck>

	fun findByCreadoGreaterThanEqual(@DateTimeFormat(iso = ISO.DATE_TIME) fecha: LocalDateTime)
			: List<SmartCheck>
}

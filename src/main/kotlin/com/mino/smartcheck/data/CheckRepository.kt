package com.mino.smartcheck.data

import com.mino.smartcheck.model.Organizacion
import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.CrossOrigin
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(path = "checks", collectionResourceRel = "checks")
@CrossOrigin(origins = ["*"])
interface CheckRepository: JpaRepository<SmartCheck, Int>
{
	fun findFirstByTipoOrderByCreadoDesc(tipo: TipoCheck): Optional<SmartCheck>

	@Query("SELECT SUM(sc.diferenciaMinutos) " +
			"FROM SmartCheck sc " +
			"WHERE sc.diferenciaMinutos > 0 AND sc.tipo = :tipo")
	fun findTotalPositivo(tipo: TipoCheck): Int

	//fun findByCreadoGreaterThanEqual(from: LocalDate): List<SmartCheck>
}

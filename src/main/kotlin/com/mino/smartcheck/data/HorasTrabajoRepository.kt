package com.mino.smartcheck.data

import com.mino.smartcheck.model.HorasTrabajo
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(exported = false)
@CrossOrigin(origins = ["*"])
interface HorasTrabajoRepository: JpaRepository<HorasTrabajo, Int>
{
	fun findFirstByFechaInicioAndUsuario(inicio: LocalDate, usuario: Usuario)
			: Optional<HorasTrabajo>

//	@Query("SELECT SUM(tt.minutos) " +
//			"FROM HorasTrabajo tt WHERE tt.checkEntrada.fecha >= :desde")
//	fun findTotalMinutosTrabajados(desde: LocalDate): Int
}
package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TiempoTrabajo
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(exported = false)
@CrossOrigin(origins = ["*"])
interface TiempoTrabajoRepository : JpaRepository<TiempoTrabajo, Int>
{
	fun findFirstByCheckEntrada_EmpleadoAndMinutosTrabajadosIsNull(usuario: Usuario)
			: Optional<TiempoTrabajo>

	@Query("SELECT SUM(tt.minutosTrabajados) " +
			"FROM TiempoTrabajo tt " +
			"WHERE tt.checkEntrada.fecha >= :desde")
	fun findTotalMinutosTrabajados(desde: LocalDate): Int
}

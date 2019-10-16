package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck
import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate

@CrossOrigin("*")
@RepositoryRestResource(path = "checks", collectionResourceRel = "checks")
interface CheckRepository: JpaRepository<SmartCheck, Int>
{
	fun findByEmpleadoAndFecha(empleado: Usuario, fecha: LocalDate): List<SmartCheck>
}

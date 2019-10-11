package com.mino.smartcheck.data

import com.mino.smartcheck.model.Rol
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RolRepository: JpaRepository<Rol, Int>
{
	fun findByNombre(nombre: String): Optional<Rol>
}

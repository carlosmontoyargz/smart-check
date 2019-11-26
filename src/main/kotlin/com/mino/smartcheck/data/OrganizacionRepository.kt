package com.mino.smartcheck.data

import com.mino.smartcheck.model.Organizacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

@RepositoryRestResource(path = "organizaciones", collectionResourceRel = "organizaciones")
@CrossOrigin(origins = ["*"])
interface OrganizacionRepository: JpaRepository<Organizacion, Int>
{
	fun findFirstByNombre(nombre: String): Optional<Organizacion>

	fun findFirstBy(): Optional<Organizacion>
}

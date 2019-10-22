package com.mino.smartcheck.data

import com.mino.smartcheck.model.Organizacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface OrganizacionRepository: JpaRepository<Organizacion, Int>

package com.mino.smartcheck.data

import com.mino.smartcheck.model.Organizacion
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizacionRepository: JpaRepository<Organizacion, Int>

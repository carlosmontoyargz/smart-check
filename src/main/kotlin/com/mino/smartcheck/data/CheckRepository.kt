package com.mino.smartcheck.data

import com.mino.smartcheck.model.SmartCheck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "checks", collectionResourceRel = "checks")
interface CheckRepository: JpaRepository<SmartCheck, Int>

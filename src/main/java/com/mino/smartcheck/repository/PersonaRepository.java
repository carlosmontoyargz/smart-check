package com.mino.smartcheck.repository;

import com.mino.smartcheck.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@RepositoryRestResource(path = "personas", collectionResourceRel = "personas")
public interface PersonaRepository extends JpaRepository<Persona, Integer>
{
}

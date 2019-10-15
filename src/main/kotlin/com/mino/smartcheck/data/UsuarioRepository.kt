package com.mino.smartcheck.data

import com.mino.smartcheck.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*
import javax.swing.text.html.Option

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@RepositoryRestResource(path = "personas", collectionResourceRel = "personas")
interface UsuarioRepository: JpaRepository<Usuario, Int>
{
	fun findByUsername(username: String): Optional<Usuario>

	fun existsByUsername(username: String): Boolean
}

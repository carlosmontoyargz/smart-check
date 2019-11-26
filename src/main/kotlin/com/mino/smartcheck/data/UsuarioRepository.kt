package com.mino.smartcheck.data

import com.mino.smartcheck.model.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*
import javax.swing.text.html.Option

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@RepositoryRestResource(path = "usuarios", collectionResourceRel = "usuarios")
@CrossOrigin(origins = ["*"])
interface UsuarioRepository: JpaRepository<Usuario, Int>
{
	@PreAuthorize("hasRole('ADMIN')")
	override fun findAll(pageable: Pageable): Page<Usuario>

	fun findByUsername(username: String): Optional<Usuario>

	fun existsByUsername(username: String): Boolean
}

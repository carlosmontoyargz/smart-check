package com.mino.smartcheck.config

import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.model.Usuario
import org.apache.logging.log4j.LogManager
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class ModelMapperConfigTest
{
	@Autowired var modelMapper: ModelMapper? = null
	private val log = LogManager.getLogger()

	@Test
	fun usuarioDtoToEntity() {
		/*val usuarioDto = UsuarioDto(
				username = "Carlos",
				password = "sipirili",
				firstName = "Carlos",
				lastName = "Montoya",
				rolNombre = "ROL_ADMIN")

		val usuario = modelMapper!!.map(usuarioDto, Usuario::class.java)
		log.info(usuario)*/
	}
}

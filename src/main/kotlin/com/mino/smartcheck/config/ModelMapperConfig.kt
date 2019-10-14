package com.mino.smartcheck.config

import com.mino.smartcheck.dto.UsuarioDto
import com.mino.smartcheck.model.Usuario
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig
{
	@Bean
	fun modelMapper(): ModelMapper {
		val mapper = ModelMapper()
		mapper
				.createTypeMap(UsuarioDto::class.java, Usuario::class.java)
				.addMapping(UsuarioDto::username, Usuario::setCorreo)
				.addMapping(UsuarioDto::password, Usuario::setContrasena)
		return mapper;
	}
}

package com.mino.smartcheck.config;

import com.mino.smartcheck.dto.UsuarioDto;
import com.mino.smartcheck.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Carlos Montoya
 * @since 14/10/2019
 */
@Configuration
public class ModelMapperConfig
{
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(Usuario.class, UsuarioDto.class)
				.addMappings(mapping -> mapping.skip(UsuarioDto::setPassword)); // password must be open
		return mapper;
	}
}

package com.mino.smartcheck.service;

import com.mino.smartcheck.data.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Carlos Montoya
 * @since 23/11/2019
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Primary
public class JwtUserDetailsService implements UserDetailsService
{
	private final UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return usuarioRepository
				.findByUsername(username)
				.map(usuario -> new User(
						username,
						Objects.requireNonNull(usuario.getPassword()),
						Collections.singletonList(
								new SimpleGrantedAuthority("ROLE_" + Objects.requireNonNull(
										usuario.getRol()).getNombre().toUpperCase()))))
				.orElseThrow(() ->
						new UsernameNotFoundException(
								"User not found with username: " + username));

	}
}

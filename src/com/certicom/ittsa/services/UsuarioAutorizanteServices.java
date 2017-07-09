package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.UsuarioAutorizante;
import com.certicom.ittsa.mapper.UsuarioAutorizanteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UsuarioAutorizanteServices implements UsuarioAutorizanteMapper{
	
	UsuarioAutorizanteMapper usuarioAutorizanteMapper = (UsuarioAutorizanteMapper)ServiceFinder.findBean("usuarioAutorizanteMapper");

	@Override
	public List<UsuarioAutorizante> findAll() throws Exception {
		return usuarioAutorizanteMapper.findAll();
	}

	@Override
	public UsuarioAutorizante findById(Integer IdAutorizante) throws Exception {
		return usuarioAutorizanteMapper.findById(IdAutorizante);
	}

	@Override
	public void registrarUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) throws Exception {
		usuarioAutorizanteMapper.registrarUsuarioAutorizante(usuarioAutorizante);
	}

	@Override
	public void actualizarUsuarioAutorizante(UsuarioAutorizante usuarioAutorizante) throws Exception {
		usuarioAutorizanteMapper.actualizarUsuarioAutorizante(usuarioAutorizante);
	}

	@Override
	public void eliminarUsuarioAutorizante(Integer IdAutorizante) throws Exception {
		usuarioAutorizanteMapper.eliminarUsuarioAutorizante(IdAutorizante);
	}

	@Override
	public List<UsuarioAutorizante> listaUsuarioAutorizanteActivos() throws Exception {
		
		return usuarioAutorizanteMapper.listaUsuarioAutorizanteActivos();
	}







}

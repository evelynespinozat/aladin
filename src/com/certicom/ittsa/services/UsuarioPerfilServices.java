package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.mapper.UsuarioPerfilMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UsuarioPerfilServices implements UsuarioPerfilMapper{

	UsuarioPerfilMapper usuarioPerfilMapper = (UsuarioPerfilMapper)ServiceFinder.findBean("usuarioPerfilMapper");

	@Override
	public List<UsuarioPerfil> listarPerfilesPorUsuario(Integer usuarioId)
			throws Exception {
		// TODO Auto-generated method stub
		return usuarioPerfilMapper.listarPerfilesPorUsuario(usuarioId);
	}

	@Override
	public UsuarioPerfil buscarPerfilUsuario(Integer idusuario,Integer idperfil) throws Exception {
		return usuarioPerfilMapper.buscarPerfilUsuario(idusuario, idperfil);
	}

	@Override
	public void eliminarPerfilUsuario(Integer idusuario, Integer idperfil) throws Exception {
		usuarioPerfilMapper.eliminarPerfilUsuario(idusuario, idperfil);
	}

	@Override
	public void insertUsuarioPeril(UsuarioPerfil usuarioPerfil) throws Exception{
		usuarioPerfilMapper.insertUsuarioPeril(usuarioPerfil);
	}

	@Override
	public Integer countPerfilxId(Integer cod_perfil) throws Exception {
		// TODO Auto-generated method stub
		return usuarioPerfilMapper.countPerfilxId(cod_perfil);
	}

}

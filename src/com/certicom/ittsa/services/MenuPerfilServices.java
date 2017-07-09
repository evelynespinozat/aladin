package com.certicom.ittsa.services;

import org.apache.ibatis.annotations.Param;

import com.certicom.ittsa.domain.MenuXPerfil;
import com.certicom.ittsa.mapper.MenuPerfilMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class MenuPerfilServices implements MenuPerfilMapper{

	MenuPerfilMapper menuPerfilMapper = (MenuPerfilMapper)ServiceFinder.findBean("menuPerfilMapper");
	
	//busca
	@Override
	public MenuXPerfil buscarMenuPerfil(Long idMenu, Integer idPerfil) throws Exception {
		return menuPerfilMapper.buscarMenuPerfil(idMenu, idPerfil);
	}

	@Override
	public void crearMenuPerfil(Long idMenu, Integer idperfil, Boolean activo) throws Exception {
		menuPerfilMapper.crearMenuPerfil(idMenu, idperfil, activo);
	}

	@Override
	public void eliminarPerfilUsuario(Long idMenu,Integer idperfil) throws Exception {
		
		menuPerfilMapper.eliminarPerfilUsuario(idMenu, idperfil);
		
	}

	@Override
	public int obtenerMenusxId(Long cod_menu) {

		return menuPerfilMapper.obtenerMenusxId(cod_menu);
	}



}

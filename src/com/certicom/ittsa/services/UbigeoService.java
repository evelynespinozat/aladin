package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.mapper.UbigeoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UbigeoService implements UbigeoMapper{
	
	UbigeoMapper ubigeoMapper = (UbigeoMapper)ServiceFinder.findBean("ubigeoMapper");

	@Override
	public List<Ubigeo> findAll() throws Exception {
		return ubigeoMapper.findAll();
	}

	@Override
	public List<Ubigeo> listarDepartamentos() {
		return ubigeoMapper.listarDepartamentos();
	}

	@Override
	public List<Ubigeo> listarProvincias(String sdepartamento) {
		return ubigeoMapper.listarProvincias(sdepartamento);
	}

	@Override
	public List<Ubigeo> listardistritos(String sdepartamento, String sprovincia) {
		return ubigeoMapper.listardistritos(sdepartamento, sprovincia);
	}

	@Override
	public Ubigeo obtenerUbigeoById(String sid_ubigeo) {
		// TODO Auto-generated method stub
		return ubigeoMapper.obtenerUbigeoById(sid_ubigeo);
	}

	@Override
	public List<Ubigeo> listardistritosByProvincia(String sprovincia) {
		// TODO Auto-generated method stub
		return ubigeoMapper.listardistritosByProvincia(sprovincia);
	}


	
	

}

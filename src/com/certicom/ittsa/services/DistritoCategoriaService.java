package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.DistritoCategoria;
import com.certicom.ittsa.mapper.DistritoCategoriaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DistritoCategoriaService implements DistritoCategoriaMapper{
	
	DistritoCategoriaMapper distritoCategoriaMapper = (DistritoCategoriaMapper) ServiceFinder.findBean("distritoCategoriaMapper");

	@Override
	public void registrarDistritoCategoria(DistritoCategoria distritoCategoria) throws Exception{
		distritoCategoriaMapper.registrarDistritoCategoria(distritoCategoria);
	}

	@Override
	public List<DistritoCategoria> listarCategoriaxDistrito(String sid_ubigeo) {
		return distritoCategoriaMapper.listarCategoriaxDistrito(sid_ubigeo);
	}

	@Override
	public void eliminarDistritoCategoria(Integer id) throws Exception {
		distritoCategoriaMapper.eliminarDistritoCategoria(id);
	}

}

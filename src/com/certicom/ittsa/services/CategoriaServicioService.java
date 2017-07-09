package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.mapper.CategoriaServicioMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CategoriaServicioService implements CategoriaServicioMapper{
	
	CategoriaServicioMapper categoriaServicioMapper = (CategoriaServicioMapper)ServiceFinder.findBean("categoriaServicioMapper");

	@Override
	public List<CategoriaServicio> findAll() throws Exception {

		return categoriaServicioMapper.findAll();
	}

	@Override
	public CategoriaServicio findById(Integer idCategoriaServicio) throws Exception {

		return categoriaServicioMapper.findById(idCategoriaServicio);
	}

	@Override
	public void eliminarCategoriaServicio(Integer idCategoriaServicio) throws Exception {

		categoriaServicioMapper.eliminarCategoriaServicio(idCategoriaServicio);
	}

	@Override
	public void crearCategoriaServicio(CategoriaServicio categoriaServicio) throws Exception {

		categoriaServicioMapper.crearCategoriaServicio(categoriaServicio);
	}

	@Override
	public void actualizarCategoriaServicio(CategoriaServicio categoriaServicio) throws Exception {
		categoriaServicioMapper.actualizarCategoriaServicio(categoriaServicio);
		
	}

	@Override
	public List<CategoriaServicio> listaCatServicioActivos() throws Exception {
		// TODO Auto-generated method stub
		return categoriaServicioMapper.listaCatServicioActivos();
	}

	@Override
	public CategoriaServicio findByNombre(String descripcion)
			throws Exception {
		return categoriaServicioMapper.findByNombre(descripcion);
	}

}

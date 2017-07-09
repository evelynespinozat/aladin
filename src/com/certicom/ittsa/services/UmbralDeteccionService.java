package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.UmbralDeteccion;
import com.certicom.ittsa.mapper.UmbralDeteccionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UmbralDeteccionService implements UmbralDeteccionMapper{
	UmbralDeteccionMapper umbralDeteccionMapper =(UmbralDeteccionMapper) ServiceFinder.findBean("umbralDeteccionMapper");

	@Override
	public List<UmbralDeteccion> findAll() throws Exception {
		
		return this.umbralDeteccionMapper.findAll();
	}

	@Override
	public UmbralDeteccion findById(Integer id_deteccion) throws Exception {
		
		return this.umbralDeteccionMapper.findById(id_deteccion);
	}

	@Override
	public void eliminarUmbralDeteccion(Integer id_deteccion) throws Exception {
		this.umbralDeteccionMapper.eliminarUmbralDeteccion(id_deteccion);
		
	}

	@Override
	public void crearUmbralDeteccion(UmbralDeteccion umbralDeteccion)
			throws Exception {
		this.umbralDeteccionMapper.crearUmbralDeteccion(umbralDeteccion);
		
	}

	@Override
	public void actualizarUmbralDeteccion(UmbralDeteccion umbralDeteccion)
			throws Exception {
		this.umbralDeteccionMapper.actualizarUmbralDeteccion(umbralDeteccion);
		
	}

	@Override
	public List<UmbralDeteccion> listaUmbralDeteccionActivos() throws Exception {
		
		return this.umbralDeteccionMapper.listaUmbralDeteccionActivos();
	}

	@Override
	public UmbralDeteccion findByEstado(Boolean estado) throws Exception {
		
		return this.umbralDeteccionMapper.findByEstado(estado);
	}

}

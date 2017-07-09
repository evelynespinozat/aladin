package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.RutaServicio;
import com.certicom.ittsa.mapper.RutaServicioMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class RutaServicioService implements RutaServicioMapper{
	
	RutaServicioMapper rutaServicioMapper = (RutaServicioMapper)ServiceFinder.findBean("rutaServicioMapper");
	
	@Override
	public List<RutaServicio> findAll() throws Exception {
		return rutaServicioMapper.findAll();
	}

	@Override
	public RutaServicio findById(String rutaServicioId) throws Exception {
		return rutaServicioMapper.findById(rutaServicioId);
	}

	@Override
	public void eliminarRutaServicio(Integer rutaServicioId) throws Exception {
		rutaServicioMapper.eliminarRutaServicio(rutaServicioId);	
	}

	@Override
	public void crearRutaServicio(RutaServicio rutaServicio) throws Exception {
		rutaServicioMapper.crearRutaServicio(rutaServicio);
	}

	@Override
	public void actualizarRutaServicio(RutaServicio rutaServicio)
			throws Exception {
		rutaServicioMapper.actualizarRutaServicio(rutaServicio);
	}

	@Override
	public List<RutaServicio> listaRutaServicioActivas() throws Exception {
		return rutaServicioMapper.listaRutaServicioActivas();
	}

}

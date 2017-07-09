package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Enlace;
import com.certicom.ittsa.mapper.EnlaceMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EnlaceService implements EnlaceMapper{
	
	EnlaceMapper enlaceMapper = (EnlaceMapper)ServiceFinder.findBean("enlaceMapper");

	@Override
	public List<Enlace> findAll() throws Exception {

		return enlaceMapper.findAll();
	}

	@Override
	public Enlace findById(Integer idEnlace) throws Exception {

		return enlaceMapper.findById(idEnlace);
	}

	@Override
	public void eliminarEnlace(Integer idEnlace) throws Exception {

		enlaceMapper.eliminarEnlace(idEnlace);
	}

	@Override
	public void crearEnlace(Enlace enlace) throws Exception {

		enlaceMapper.crearEnlace(enlace);
	}

	@Override
	public void actualizarEnlace(Enlace agencia) throws Exception {
		
		enlaceMapper.actualizarEnlace(agencia);
	}

	@Override
	public List<Enlace> listaEnlacesActivas() throws Exception {
		
		return enlaceMapper.listaEnlacesActivas();
	}

	@Override
	public List<Enlace> listaEnlaces() throws Exception {
		
		return enlaceMapper.listaEnlaces();
	}

	@Override
	public List<Enlace> listarTramosServicio(Integer IdServicio)
			throws Exception {
		return enlaceMapper.listarTramosServicio(IdServicio);
	}

	@Override
	public void eliminarEnlacesByServicio(Integer idServicio) throws Exception {
		// TODO Auto-generated method stub
		enlaceMapper.eliminarEnlacesByServicio(idServicio);
	}

	@Override
	public Integer obtenerCorrelativo() throws Exception {
		return enlaceMapper.obtenerCorrelativo();
	}

	@Override
	public void actualizarCorrelativo(Integer idCorrelativo,
			Integer intCorrelativo) throws Exception {
		// TODO Auto-generated method stub
		enlaceMapper.actualizarCorrelativo(idCorrelativo, intCorrelativo);
	}

}

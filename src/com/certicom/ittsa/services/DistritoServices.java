package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Distrito;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.DistritoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DistritoServices implements DistritoMapper{
	
	DistritoMapper distritoMapper = (DistritoMapper)ServiceFinder.findBean("distritoMapper");

	@Override
	public List<Distrito> findAll() throws Exception {
		// TODO Auto-generated method stub
		return distritoMapper.findAll();
	}

	@Override
	public Agencia findById(Integer IdDistrito) throws Exception {
		// TODO Auto-generated method stub
		return distritoMapper.findById(IdDistrito); 
	}

	@Override
	public Agencia findByDescripcion(String descripcion) throws Exception {
		// TODO Auto-generated method stub
		return distritoMapper.findByDescripcion(descripcion);
	}

	@Override
	public void eliminarDistrito(Integer idDistrito) throws Exception {
		// TODO Auto-generated method stub
		distritoMapper.eliminarDistrito(idDistrito);
	}

	@Override
	public void crearDistrito(Distrito distrito) throws Exception {
		// TODO Auto-generated method stub
		distritoMapper.crearDistrito(distrito);
	}

	@Override
	public void actualizarDistrito(Distrito distrito) throws Exception {
		// TODO Auto-generated method stub
		distritoMapper.actualizarDistrito(distrito); 
	}

	@Override
	public List<Distrito> findByAgencia(Integer idAgencia) throws Exception {
		// TODO Auto-generated method stub
		return distritoMapper.findByAgencia(idAgencia); 
	}



}

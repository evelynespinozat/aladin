package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.mapper.AutomotorMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AutomotorService implements AutomotorMapper{
	
	AutomotorMapper automotorMapper = (AutomotorMapper)ServiceFinder.findBean("automotorMapper");

	@Override
	public List<Automotor> findAll() throws Exception {
		return automotorMapper.findAll();
	}

	@Override
	public Automotor findById(Integer agenciaID) throws Exception {
		return automotorMapper.findById(agenciaID);
	}

	@Override
	public void eliminarAutomotor(Integer agenciaID) throws Exception {
		automotorMapper.eliminarAutomotor(agenciaID);
	}

	@Override
	public void crearAutomotor(Automotor agencia) throws Exception {
		automotorMapper.crearAutomotor(agencia);
	}

	@Override
	public void actualizarAutomotor(Automotor agencia) throws Exception {
		automotorMapper.actualizarAutomotor(agencia);
		
	}

	@Override
	public List<Automotor> listaAutomotorsActivas() throws Exception {
		return automotorMapper.listaAutomotorsActivas();
	}
	
}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Transbordo;
import com.certicom.ittsa.mapper.TransbordoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TransbordoService implements TransbordoMapper{
	
	TransbordoMapper transbordoMapper = (TransbordoMapper)ServiceFinder.findBean("transbordoMapper");

	@Override
	public List<Transbordo> findAll() throws Exception {

		return transbordoMapper.findAll();
	}

	@Override
	public Transbordo findById(Integer agenciaID) throws Exception {

		return transbordoMapper.findById(agenciaID);
	}

	@Override
	public void eliminarTransbordo(Integer agenciaID) throws Exception {

		transbordoMapper.eliminarTransbordo(agenciaID);
	}

	@Override
	public void crearTransbordo(Transbordo transbordo) throws Exception {

		transbordoMapper.crearTransbordo(transbordo);
	}

	@Override
	public void actualizarTransbordo(Transbordo transbordo) throws Exception {
		
		transbordoMapper.actualizarTransbordo(transbordo);
		
	}

	@Override
	public List<Transbordo> listaTransbordos() {
		
		return transbordoMapper.listaTransbordos();
	}

}

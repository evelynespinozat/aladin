package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.TipoCombustible;
import com.certicom.ittsa.mapper.TipoCombustibleMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TipoCombustibleService implements TipoCombustibleMapper{
	
	TipoCombustibleMapper tipoCombustibleMapper = (TipoCombustibleMapper)ServiceFinder.findBean("tipoCombustibleMapper");

	@Override
	public List<TipoCombustible> findAll() throws Exception {

		return tipoCombustibleMapper.findAll();
	}

	@Override
	public TipoCombustible findById(Integer idTipoCombustible) throws Exception {

		return tipoCombustibleMapper.findById(idTipoCombustible);
	}

	@Override
	public void eliminarTipoCombustible(Integer idTipoCombustible) throws Exception {

		tipoCombustibleMapper.eliminarTipoCombustible(idTipoCombustible);
	}

	@Override
	public void crearTipoCombustible(TipoCombustible tipoCombustible) throws Exception {

		tipoCombustibleMapper.crearTipoCombustible(tipoCombustible);
	}

	@Override
	public void actualizarTipoCombustible(TipoCombustible tipoCombustible) throws Exception {
		tipoCombustibleMapper.actualizarTipoCombustible(tipoCombustible);
		
	}

	@Override
	public List<TipoCombustible> listaTipoCombustibleActivas() throws Exception {

		return tipoCombustibleMapper.listaTipoCombustibleActivas();
	}
	
	

}

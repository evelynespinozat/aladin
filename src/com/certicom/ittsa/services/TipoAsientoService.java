package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.mapper.TipoAsientoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TipoAsientoService implements TipoAsientoMapper{
	TipoAsientoMapper tipoAsientoMapper = (TipoAsientoMapper)ServiceFinder.findBean("tipoAsientoMapper");

	@Override
	public List<TipoAsiento> findAll() throws Exception {
		return tipoAsientoMapper.findAll();
	}

	@Override
	public TipoAsiento findById(Integer idtipoasiento) throws Exception {
		// TODO Auto-generated method stub
		return tipoAsientoMapper.findById(idtipoasiento);
	}

	@Override
	public void eliminarTipoAsiento(Integer idTipoAsiento) throws Exception {
		// TODO Auto-generated method stub
		tipoAsientoMapper.eliminarTipoAsiento(idTipoAsiento);
		
	}

	@Override
	public void crearTipoAsiento(TipoAsiento tipoAsiento) throws Exception {
		// TODO Auto-generated method stub
		tipoAsientoMapper.crearTipoAsiento(tipoAsiento);
		
	}

	@Override
	public void actualizarTipoAsiento(TipoAsiento tipoAsiento) throws Exception {
		// TODO Auto-generated method stub
		tipoAsientoMapper.actualizarTipoAsiento(tipoAsiento);
	}

	@Override
	public List<TipoAsiento> listaTipoAsientosActivas() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

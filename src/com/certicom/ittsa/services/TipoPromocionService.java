package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.TipoPromocion;
import com.certicom.ittsa.mapper.TipoPromocionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TipoPromocionService implements TipoPromocionMapper{
	
	TipoPromocionMapper tipoPromocionMapper = (TipoPromocionMapper)ServiceFinder.findBean("tipoPromocionMapper");

	@Override
	public List<TipoPromocion> findAll() throws Exception {

		return tipoPromocionMapper.findAll();
	}

	@Override
	public TipoPromocion findById(Integer agenciaID) throws Exception {

		return tipoPromocionMapper.findById(agenciaID);
	}

	@Override
	public void eliminarTipoPromocion(Integer agenciaID) throws Exception {

		tipoPromocionMapper.eliminarTipoPromocion(agenciaID);
	}

	@Override
	public void crearTipoPromocion(TipoPromocion agencia) throws Exception {

		tipoPromocionMapper.crearTipoPromocion(agencia);
	}

	@Override
	public void actualizarTipoPromocion(TipoPromocion agencia) throws Exception {
		tipoPromocionMapper.actualizarTipoPromocion(agencia);
		
	}

}

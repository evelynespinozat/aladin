package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.TipoFlota;
import com.certicom.ittsa.mapper.TipoFlotaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TipoFlotaService implements TipoFlotaMapper{
	
	TipoFlotaMapper tipoFlotaMapper = (TipoFlotaMapper)ServiceFinder.findBean("tipoFlotaMapper");

	@Override
	public List<TipoFlota> findAll() throws Exception {

		return tipoFlotaMapper.findAll();
	}

	@Override
	public TipoFlota findById(Integer TipoFlotaID) throws Exception {

		return tipoFlotaMapper.findById(TipoFlotaID);
	}

	@Override
	public void eliminarTipoFlota(Integer TipoFlotaID) throws Exception {

		tipoFlotaMapper.eliminarTipoFlota(TipoFlotaID);
	}

	@Override
	public void crearTipoFlota(TipoFlota TipoFlota) throws Exception {

		tipoFlotaMapper.crearTipoFlota(TipoFlota);
	}

	@Override
	public void actualizarTipoFlota(TipoFlota TipoFlota) throws Exception {
		tipoFlotaMapper.actualizarTipoFlota(TipoFlota);
		
	}

	@Override
	public List<TipoFlota> listaTipoFlotasActivas() throws Exception {

		return tipoFlotaMapper.listaTipoFlotasActivas();
	}

}

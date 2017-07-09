package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.FlotaVacia;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.FlotaVaciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FlotaVaciaService implements FlotaVaciaMapper{
	
	FlotaVaciaMapper flotaVaciaMapper = (FlotaVaciaMapper)ServiceFinder.findBean("flotaVaciaMapper");

	@Override
	public List<FlotaVacia> findAll() throws Exception {
		return flotaVaciaMapper.findAll();
	}

	@Override
	public FlotaVacia findById(Integer idFlotaVacia) throws Exception {
		// TODO Auto-generated method stub
		return flotaVaciaMapper.findById(idFlotaVacia);
	}

	@Override
	public void eliminarFlotaVacia(Integer idFlotaVacia) throws Exception {
		// TODO Auto-generated method stub
		flotaVaciaMapper.eliminarFlotaVacia(idFlotaVacia);
	}

	@Override
	public void crearFlotaVacia(FlotaVacia flotaVacia) throws Exception {
		// TODO Auto-generated method stub
		flotaVaciaMapper.crearFlotaVacia(flotaVacia);
	}

	@Override
	public void actualizarFlotaVacia(FlotaVacia flotaVacia) throws Exception {
		// TODO Auto-generated method stub
		flotaVaciaMapper.actualizarFlotaVacia(flotaVacia);
	}

	@Override
	public List<FlotaVacia> listaFlotaVaciasActivas() throws Exception {
		// TODO Auto-generated method stub
		return flotaVaciaMapper.listaFlotaVaciasActivas();
	}

	@Override
	public List<FlotaVacia> rptUnidadVaciasxFiltros(FlotaVacia flotaVacia)
			throws Exception {
		// TODO Auto-generated method stub
		return flotaVaciaMapper.rptUnidadVaciasxFiltros(flotaVacia);
	}

	

}

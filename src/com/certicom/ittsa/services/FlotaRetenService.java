package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.FlotaReten;
import com.certicom.ittsa.mapper.FlotaRetenMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FlotaRetenService implements FlotaRetenMapper{
	
	FlotaRetenMapper flotaRetenMapper = (FlotaRetenMapper)ServiceFinder.findBean("flotaRetenMapper");

	@Override
	public FlotaReten findById(Integer IdBusReten) throws Exception {
		return flotaRetenMapper.findById(IdBusReten);
	}

	@Override
	public void eliminarFlotaReten(Integer IdBusReten) throws Exception {
		flotaRetenMapper.eliminarFlotaReten(IdBusReten);
	}

	@Override
	public void crearFlotaReten(FlotaReten flotaReten) throws Exception {
		flotaReten.setFRegistro(new Date());
		flotaRetenMapper.crearFlotaReten(flotaReten);
	}

	@Override
	public void actualizarFlotaReten(FlotaReten flotaReten) throws Exception {
		flotaRetenMapper.actualizarFlotaReten(flotaReten);
	}

	@Override
	public List<FlotaReten> listaFlotaRetensActivas() throws Exception {
		return flotaRetenMapper.listaFlotaRetensActivas();
	}


}

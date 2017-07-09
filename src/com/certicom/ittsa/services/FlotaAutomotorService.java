package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.mapper.FlotaAutomotorMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FlotaAutomotorService implements FlotaAutomotorMapper{
	
	FlotaAutomotorMapper flotaAutomotorMapper = (FlotaAutomotorMapper)ServiceFinder.findBean("flotaAutomotorMapper");

	@Override
	public void eliminarFlotaAutomotor(Integer agenciaID) throws Exception {

		flotaAutomotorMapper.eliminarFlotaAutomotor(agenciaID);
	}

	@Override
	public void crearFlotaAutomotor(FlotaAutomotor agencia) throws Exception {

		flotaAutomotorMapper.crearFlotaAutomotor(agencia);
	}

	@Override
	public List<FlotaAutomotor> listaAutomotorxFlota(Integer idBus)
			throws Exception {
		// TODO Auto-generated method stub
		return flotaAutomotorMapper.listaAutomotorxFlota(idBus);
	}

	@Override
	public int verificarSistema(Integer idBus, Integer idAutomotor)
			throws Exception {
		// TODO Auto-generated method stub
		return flotaAutomotorMapper.verificarSistema(idBus, idAutomotor);
	}


}

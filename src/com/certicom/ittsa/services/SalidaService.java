package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.Salida;
import com.certicom.ittsa.mapper.SalidaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SalidaService implements SalidaMapper {
	
	SalidaMapper salidaMapper = (SalidaMapper)ServiceFinder.findBean("salidaMapper");
	
	@Override
	public void registrarSalidaProductos(Salida salida) throws Exception {
		 salidaMapper.registrarSalidaProductos(salida);
	}

	@Override
	public Integer getIDbyProgramacion(Integer IdProgramacion) {
		return salidaMapper.getIDbyProgramacion(IdProgramacion);
	}

	@Override
	public void actualizarFlagLLegada(Integer IdProgramacion, String flagLlegada) {
		salidaMapper.actualizarFlagLLegada(IdProgramacion, flagLlegada);
	}

}

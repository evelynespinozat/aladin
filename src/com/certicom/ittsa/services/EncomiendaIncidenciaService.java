package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.EncomiendaIncidencia;
import com.certicom.ittsa.mapper.EncomiendaIncidenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EncomiendaIncidenciaService implements EncomiendaIncidenciaMapper{

	EncomiendaIncidenciaMapper encomiendaIncidenciaMapper = (EncomiendaIncidenciaMapper) ServiceFinder.findBean("encomiendaIncidenciaMapper");
	
	@Override
	public void registrarEncomiendaIncidencia(EncomiendaIncidencia encomiendaIncidencia) throws Exception{
		encomiendaIncidenciaMapper.registrarEncomiendaIncidencia(encomiendaIncidencia);
	}	

}

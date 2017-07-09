package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.mapper.SalidaAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SalidaAutoparteService implements SalidaAutoparteMapper{
	
	SalidaAutoparteMapper salidaAutoparteMapper = (SalidaAutoparteMapper)ServiceFinder.findBean("salidaAutoparteMapper");

	@Override
	public void crearSalidaAutoparte(SalidaAutoparte salidaAutoparte)
			throws Exception {
		salidaAutoparteMapper.crearSalidaAutoparte(salidaAutoparte);
	}

	@Override
	public List<SalidaAutoparte> listarAutopartePendientes(
			SalidaAutoparte salidaAutoparte) throws Exception {
		// TODO Auto-generated method stub
		return salidaAutoparteMapper.listarAutopartePendientes(salidaAutoparte);
	}

	@Override
	public void actualizarEstado(Integer IdSalidaAutoparte, Integer Estado)
			throws Exception {
		// TODO Auto-generated method stub
		salidaAutoparteMapper.actualizarEstado(IdSalidaAutoparte, Estado);
	}

	@Override
	public List<SalidaAutoparte> listarCostosxBus(Date fecIni, Date fecFin,
			Integer nroBus) {
		return salidaAutoparteMapper.listarCostosxBus(fecIni,fecFin,nroBus);
	}

}

package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.EncomiendaAlmacen;
import com.certicom.ittsa.mapper.EncomiendaAlmacenMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EncomiendaAlmacenservice implements EncomiendaAlmacenMapper {

	EncomiendaAlmacenMapper encomiendaAlmacenMapper = (EncomiendaAlmacenMapper) ServiceFinder.findBean("encomiendaAlmacenMapper");
	
	@Override
	public void registrarEncomiendaAlmacen(EncomiendaAlmacen encomiendaAlmacen)
			throws Exception {
		encomiendaAlmacenMapper.registrarEncomiendaAlmacen(encomiendaAlmacen);
	}

	@Override
	public void actualizarExistenciaEncomienda(Integer idEncomienda)
			throws Exception {
		encomiendaAlmacenMapper.actualizarExistenciaEncomienda(idEncomienda);
	}

}

package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.SubManifiesto;
import com.certicom.ittsa.mapper.SubManifiestoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class SubManifiestoService implements SubManifiestoMapper{

	SubManifiestoMapper subManifiestoMapper = (SubManifiestoMapper) ServiceFinder.findBean("subManifiestoMapper");
	
	@Override
	public void registrarSubManifiesto(SubManifiesto subManifiesto)
			throws Exception {
		subManifiestoMapper.registrarSubManifiesto(subManifiesto);
	}

	@Override
	public Integer lastSubManifiesto() {
		return subManifiestoMapper.lastSubManifiesto();
	}

}

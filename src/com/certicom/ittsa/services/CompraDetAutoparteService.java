package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.CompraDetAutoparte;
import com.certicom.ittsa.mapper.CompraDetAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CompraDetAutoparteService implements CompraDetAutoparteMapper {

	CompraDetAutoparteMapper compraDetAutoparteMapper =(CompraDetAutoparteMapper) ServiceFinder.findBean("compraDetAutoparteMapper");

	@Override
	public void registrarCompraDetalle(CompraDetAutoparte compraDetAutoparte) {
		compraDetAutoparteMapper.registrarCompraDetalle(compraDetAutoparte);
	}
}

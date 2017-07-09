package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.CompraAutoparte;
import com.certicom.ittsa.mapper.CompraAutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CompraAutoparteService implements CompraAutoparteMapper{

	CompraAutoparteMapper compraAutoparteMapper = (CompraAutoparteMapper)ServiceFinder.findBean("compraAutoparteMapper");
	
	@Override
	public void registrarCompra(CompraAutoparte compraAutoparte) {
		compraAutoparteMapper.registrarCompra(compraAutoparte);
	}

	@Override
	public List<CompraAutoparte> findAll() {
		return compraAutoparteMapper.findAll();
	}

	@Override
	public Integer getlastId() {
		return compraAutoparteMapper.getlastId();
	}

}

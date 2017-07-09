package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Compra;
import com.certicom.ittsa.mapper.CompraMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CompraService implements CompraMapper{

	CompraMapper compraMapper = (CompraMapper)ServiceFinder.findBean("compraMapper");
	
	@Override
	public void registrarCompra(Compra compra) {
		compraMapper.registrarCompra(compra);
	}

	@Override
	public List<Compra> findAll() {
		return compraMapper.findAll();
	}

	@Override
	public Integer getlastId() {
		return compraMapper.getlastId();
	}

	@Override
	public List<Compra> listarCompraxProveedor(Integer idProveedor) throws Exception {
		return compraMapper.listarCompraxProveedor(idProveedor);
	}

}

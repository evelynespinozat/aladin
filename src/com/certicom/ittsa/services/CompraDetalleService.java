package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.CompraDetalle;
import com.certicom.ittsa.mapper.CompraDetalleMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CompraDetalleService implements CompraDetalleMapper {

	CompraDetalleMapper compraDetalleMapper =(CompraDetalleMapper) ServiceFinder.findBean("compraDetalleMapper");

	@Override
	public void registrarCompraDetalle(CompraDetalle compraDetalle) {
		compraDetalleMapper.registrarCompraDetalle(compraDetalle);
	}

	@Override
	public List<CompraDetalle> listarxIdCompra(Integer idCompra) throws Exception {
		return compraDetalleMapper.listarxIdCompra(idCompra);
	}

	@Override
	public List<CompraDetalle> listaProductosXProveedor(Integer IdProveedor)
			throws Exception {
		return compraDetalleMapper.listaProductosXProveedor(IdProveedor);
	}
}

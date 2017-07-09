package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.mapper.Producto_DetalleEncMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Producto_DetalleEncService implements Producto_DetalleEncMapper{
	
	Producto_DetalleEncMapper producto_DetalleEncMapper = (Producto_DetalleEncMapper)ServiceFinder.findBean("producto_DetalleEncMapper");

	@Override
	public List<Producto_DetalleEnc> findAll() throws Exception {
		// TODO Auto-generated method stub
		return producto_DetalleEncMapper.findAll();
	}

	@Override
	public Producto_DetalleEnc findById(Integer idProducto_detalleEnc)
			throws Exception {
		// TODO Auto-generated method stub
		return producto_DetalleEncMapper.findById(idProducto_detalleEnc); 
	}

	@Override
	public void eliminarProducto_DetalleEnc(Integer idProducto_detalleEnc)
			throws Exception {
		// TODO Auto-generated method stub
		producto_DetalleEncMapper.eliminarProducto_DetalleEnc(idProducto_detalleEnc); 
	}

	@Override
	public void crearProducto_DetalleEnc(Producto_DetalleEnc producto_DetalleEnc)
			throws Exception {
		// TODO Auto-generated method stub
		producto_DetalleEncMapper.crearProducto_DetalleEnc(producto_DetalleEnc); 
	}

	@Override
	public void actualizarProducto_DetalleEnc(
			Producto_DetalleEnc producto_DetalleEnc) throws Exception {
		// TODO Auto-generated method stub
		producto_DetalleEncMapper.actualizarProducto_DetalleEnc(producto_DetalleEnc);
	}

	@Override
	public List<Producto_DetalleEnc> listarPorIdEncomienda(Integer idEncomienda)
			throws Exception {
		return producto_DetalleEncMapper.listarPorIdEncomienda(idEncomienda);
	}

	@Override
	public Integer obtenerIdEncomiendaxCodigoBarras(String _codigoBarras)
			throws Exception {
		return producto_DetalleEncMapper.obtenerIdEncomiendaxCodigoBarras(_codigoBarras);
	}

	@Override
	public List<Producto_DetalleEnc> obtenerProductoDetallexIdEncomiendaxIdDetalle(
			Integer idEncomienda, Integer idDetalle) throws Exception {
		return producto_DetalleEncMapper.obtenerProductoDetallexIdEncomiendaxIdDetalle(idEncomienda, idDetalle);
	} 

	

}

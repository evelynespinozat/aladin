package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.mapper.ProductoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ProductoService implements ProductoMapper{
	
	ProductoMapper productoMapper = (ProductoMapper)ServiceFinder.findBean("productoMapper");

	@Override
	public List<Producto> findAll() throws Exception {
		return productoMapper.findAll();
	}

	@Override
	public Producto findById(Integer IdProducto) throws Exception {
		return productoMapper.findById(IdProducto);
	}

	@Override
	public void eliminarProducto(Integer IdProducto) throws Exception {
		productoMapper.eliminarProducto(IdProducto);
		
	}

	@Override
	public void crearProducto(Producto producto) throws Exception {
		productoMapper.crearProducto(producto);	
	}

	@Override
	public void actualizarProducto(Producto producto) throws Exception {
		productoMapper.actualizarProducto(producto);
	}

	@Override
	public List<Producto> listaProductosActivos() throws Exception {
		return productoMapper.listaProductosActivos();
	}

	@Override
	public Integer cantProductoAsignados(Integer IdProducto) throws Exception {
		return null;
	}

	@Override
	public void actualizarCostoProducto(Integer idProducto, double costoUni)
			throws Exception {
		// TODO Auto-generated method stub
		productoMapper.actualizarCostoProducto(idProducto, costoUni);
	}


}

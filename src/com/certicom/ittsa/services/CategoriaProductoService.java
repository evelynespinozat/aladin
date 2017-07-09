package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.CategoriaProducto;
import com.certicom.ittsa.mapper.CategoriaProductoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CategoriaProductoService implements CategoriaProductoMapper{
	
	CategoriaProductoMapper categoriaProductoMapper = (CategoriaProductoMapper)ServiceFinder.findBean("categoriaProductoMapper");

	@Override
	public List<CategoriaProducto> findAll() throws Exception {
		return categoriaProductoMapper.findAll();
	}

	@Override
	public CategoriaProducto findById(Integer idCatProducto) throws Exception {
		return categoriaProductoMapper.findById(idCatProducto);
	}

	@Override
	public void eliminarCategoriaProducto(Integer idCatProducto)
			throws Exception {
		categoriaProductoMapper.eliminarCategoriaProducto(idCatProducto);
	}

	@Override
	public void crearCategoriaProducto(CategoriaProducto categoriaProducto)
			throws Exception {
		categoriaProductoMapper.crearCategoriaProducto(categoriaProducto);
	}

	@Override
	public void actualizarCategoriaProducto(CategoriaProducto categoriaProducto)
			throws Exception {
		categoriaProductoMapper.actualizarCategoriaProducto(categoriaProducto);
	}

	@Override
	public List<CategoriaProducto> listaCategoriaProductosActivas()
			throws Exception {
		return categoriaProductoMapper.listaCategoriaProductosActivas();
	}

	@Override
	public Integer cantCategoriaProducto(Integer idCatProducto)
			throws Exception {
		return categoriaProductoMapper.cantCategoriaProducto(idCatProducto);
	}


	
	

}

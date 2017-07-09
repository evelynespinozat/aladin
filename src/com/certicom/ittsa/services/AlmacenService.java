package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.mapper.AlmacenMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AlmacenService implements AlmacenMapper{
	
	AlmacenMapper almacenMapper = (AlmacenMapper)ServiceFinder.findBean("almacenMapper");

	@Override
	public List<Almacen> findAll() throws Exception {
		return almacenMapper.findAll();
	}

	@Override
	public Almacen findById(Integer IdAlmacen) throws Exception {
		return almacenMapper.findById(IdAlmacen);
	}

	@Override
	public void eliminarAlmacen(Integer IdAlmacen) throws Exception {
		almacenMapper.eliminarAlmacen(IdAlmacen);
	}

	@Override
	public void crearAlmacen(Almacen almacen) throws Exception {
		almacenMapper.crearAlmacen(almacen);
	}

	@Override
	public void actualizarAlmacen(Almacen almacen) throws Exception {
		almacenMapper.actualizarAlmacen(almacen);
	}

	@Override
	public List<Almacen> listaAlmacensActivas() throws Exception {
		return almacenMapper.listaAlmacensActivas();
	}

	@Override
	public List<Almacen> listAlmacenxOficina(Integer IdOficina) {
		return almacenMapper.listAlmacenxOficina(IdOficina);
	}

	@Override
	public Integer cantAlmacen(String descripcion) throws Exception {
		return almacenMapper.cantAlmacen(descripcion);
	}

	@Override
	public List<Almacen> listaAlmacenNoAsignados() throws Exception {
		return almacenMapper.listaAlmacenNoAsignados();
	}

	@Override
	public void actualizarAsignacionAlmacen(boolean asignado, Integer IdAlmacen)
			throws Exception {
		almacenMapper.actualizarAsignacionAlmacen(asignado, IdAlmacen);
	}

}

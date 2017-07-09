package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.ObjEncontrados;
import com.certicom.ittsa.mapper.ObjEncontradosMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ObjEncontradosService implements ObjEncontradosMapper{
	
	ObjEncontradosMapper objEncontradosMapper = (ObjEncontradosMapper)ServiceFinder.findBean("objEncontradosMapper");

	@Override
	public List<ObjEncontrados> findAll() throws Exception {
		return objEncontradosMapper.findAll();
	}

	@Override
	public ObjEncontrados findById(Integer idObjeto) throws Exception {
		return objEncontradosMapper.findById(idObjeto);
	}

	@Override
	public ObjEncontrados findByDescripcion(String Descripcion)
			throws Exception {
		return objEncontradosMapper.findByDescripcion(Descripcion);
	}

	@Override
	public void eliminarObjEncontrados(Integer idObjeto) throws Exception {
		objEncontradosMapper.eliminarObjEncontrados(idObjeto);
	}

	@Override
	public void crearObjEncontrados(ObjEncontrados ObjEncontrados)
			throws Exception {
		objEncontradosMapper.crearObjEncontrados(ObjEncontrados);
	}

	@Override
	public void actualizarObjEncontrados(ObjEncontrados ObjEncontrados)
			throws Exception {
		objEncontradosMapper.actualizarObjEncontrados(ObjEncontrados);
	}

	@Override
	public List<ObjEncontrados> findByProgramacion(Integer IdProgramacion) {
		return objEncontradosMapper.findByProgramacion(IdProgramacion);
	}

	@Override
	public List<ObjEncontrados> listarObjetosEncontrados(
			ObjEncontrados objEncontrados) throws Exception {
		return objEncontradosMapper.listarObjetosEncontrados(objEncontrados);
	}

}

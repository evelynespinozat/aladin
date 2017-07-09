package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.IngresoEgreso;
import com.certicom.ittsa.mapper.IngresoEgresoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class IngresoEgresoService implements IngresoEgresoMapper{
	
	IngresoEgresoMapper ingresoEgresoMapper = (IngresoEgresoMapper)ServiceFinder.findBean("ingresoEgresoMapper");

	@Override
	public List<IngresoEgreso> findAll() throws Exception {
		return ingresoEgresoMapper.findAll();
	}

	@Override
	public IngresoEgreso findById(Integer idingresoegreso) throws Exception {
		return ingresoEgresoMapper.findById(idingresoegreso);
	}

	@Override
	public void eliminarIngresoEgreso(Integer idingresoegreso) throws Exception {
		ingresoEgresoMapper.eliminarIngresoEgreso(idingresoegreso);
	}

	@Override
	public void crearIngresoEgreso(IngresoEgreso ingresoEgreso)
			throws Exception {
		ingresoEgresoMapper.crearIngresoEgreso(ingresoEgreso);
	}

	@Override
	public void actualizarIngresoEgreso(IngresoEgreso ingresoEgreso)
			throws Exception {
		ingresoEgresoMapper.actualizarIngresoEgreso(ingresoEgreso);
	}

	@Override
	public List<IngresoEgreso> listaIngresoEgresoxFechasOperacion(
			IngresoEgreso ingresoEgreso) throws Exception {
		return ingresoEgresoMapper.listaIngresoEgresoxFechasOperacion(ingresoEgreso);
	}

}

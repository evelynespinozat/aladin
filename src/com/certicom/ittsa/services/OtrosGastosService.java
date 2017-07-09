package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.OtrosGastos;
import com.certicom.ittsa.mapper.OtrosGastosMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class OtrosGastosService implements OtrosGastosMapper{

	OtrosGastosMapper otrosGastosMapper = (OtrosGastosMapper) ServiceFinder.findBean("otrosGastosMapper");
	@Override
	public List<OtrosGastos> findAll() throws Exception {
		return this.otrosGastosMapper.findAll();
	}

	@Override
	public OtrosGastos findById(Integer IdOtrosGastos) throws Exception {
		return this.otrosGastosMapper.findById(IdOtrosGastos);
	}

	@Override
	public void eliminarOtrosGastos(Integer IdOtrosGastos) throws Exception {
		this.otrosGastosMapper.eliminarOtrosGastos(IdOtrosGastos);
		
	}

	@Override
	public void crearOtrosGastos(OtrosGastos otrosGastos) throws Exception {
		this.otrosGastosMapper.crearOtrosGastos(otrosGastos);
		
	}

	@Override
	public void actualizarOtrosGastos(OtrosGastos otrosGastos) throws Exception {
		this.otrosGastosMapper.actualizarOtrosGastos(otrosGastos);
		
	}

	@Override
	public List<OtrosGastos> listaOtrosGastosActivas() throws Exception {
		return this.otrosGastosMapper.listaOtrosGastosActivas();
	}

}

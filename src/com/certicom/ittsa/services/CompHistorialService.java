package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.CompHistorial;
import com.certicom.ittsa.mapper.CompHistorialMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CompHistorialService implements CompHistorialMapper{
	
	CompHistorialMapper compHistorialMapper = (CompHistorialMapper)ServiceFinder.findBean("compHistorialMapper");

	@Override
	public CompHistorial findById(Integer IdHistComp) throws Exception {
		return compHistorialMapper.findById(IdHistComp);
	}

	@Override
	public void eliminarCompHistorial(Integer IdHistComp) throws Exception {
		compHistorialMapper.eliminarCompHistorial(IdHistComp);
	}

	@Override
	public void crearCompHistorial(CompHistorial compHistorial) throws Exception {
		compHistorial.setFRegistro(new Date());
		compHistorialMapper.crearCompHistorial(compHistorial);
	}

	@Override
	public void actualizarCompHistorial(CompHistorial compHistorial) throws Exception {
		compHistorialMapper.actualizarCompHistorial(compHistorial);
	}

	@Override
	public List<CompHistorial> listaHistorialxComPV(Integer IdHistComp) throws Exception {
		return compHistorialMapper.listaHistorialxComPV(IdHistComp);
	}

	@Override
	public Integer cantCompxHistorial(Integer Idcomponente) throws Exception {
		return compHistorialMapper.cantCompxHistorial(Idcomponente);
	}



}

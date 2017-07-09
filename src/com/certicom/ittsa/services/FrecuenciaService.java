package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Frecuencia;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.FrecuenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FrecuenciaService implements FrecuenciaMapper{
	
	FrecuenciaMapper frecuenciaMapper = (FrecuenciaMapper)ServiceFinder.findBean("frecuenciaMapper");

	@Override
	public List<Frecuencia> findAll() throws Exception {

		return frecuenciaMapper.findAll();
	}

	@Override
	public Frecuencia findById(Integer idFrecuencia) throws Exception {

		return frecuenciaMapper.findById(idFrecuencia);
	}

	@Override
	public void eliminarFrecuencia(Integer idFrecuencia) throws Exception {
		
		frecuenciaMapper.eliminarFrecuencia(idFrecuencia);
	}

	@Override
	public void crearFrecuencia(Frecuencia frecuencia) throws Exception {
		
		frecuenciaMapper.crearFrecuencia(frecuencia);
	}

	@Override
	public void actualizarFrecuencia(Frecuencia frecuencia) throws Exception {
		
		frecuenciaMapper.actualizarFrecuencia(frecuencia);
	}

	@Override
	public Integer cantServicios(Integer idFrecuencia) {
		return frecuenciaMapper.cantServicios(idFrecuencia);
	}

	@Override
	public List<Frecuencia> listaFrecuenciasActivas() throws Exception {
		// TODO Auto-generated method stub
		return frecuenciaMapper.listaFrecuenciasActivas();
	}

}

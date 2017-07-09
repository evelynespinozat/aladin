package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.UmbralVerificacion;
import com.certicom.ittsa.mapper.UmbralVerificacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UmbralVerificacionService implements UmbralVerificacionMapper{
	UmbralVerificacionMapper umbralVerificacionMapper = (UmbralVerificacionMapper)ServiceFinder.findBean("umbralVerificacionMapper");

	@Override
	public List<UmbralVerificacion> findAll() throws Exception {
		
		return this.umbralVerificacionMapper.findAll();
	}

	@Override
	public UmbralVerificacion findById(Integer id_verificacion)
			throws Exception {
		
		return this.umbralVerificacionMapper.findById(id_verificacion);
	}

	@Override
	public void eliminarUmbralVerificacion(Integer id_verificacion)
			throws Exception {
		this.umbralVerificacionMapper.eliminarUmbralVerificacion(id_verificacion);
		
	}

	@Override
	public void crearUmbralVerificacion(UmbralVerificacion umbralVerificacion)
			throws Exception {
		this.umbralVerificacionMapper.crearUmbralVerificacion(umbralVerificacion);
		
	}

	@Override
	public void actualizarUmbralVerificacion(
			UmbralVerificacion umbralVerificacion) throws Exception {
		this.umbralVerificacionMapper.actualizarUmbralVerificacion(umbralVerificacion);
		
	}

	@Override
	public List<UmbralVerificacion> listaUmbralVerificacionActivos()
			throws Exception {
		
		return this.umbralVerificacionMapper.listaUmbralVerificacionActivos();
	}

	@Override
	public UmbralVerificacion findByEstado(Boolean estado) throws Exception {
		return this.umbralVerificacionMapper.findByEstado(estado);
	}

	@Override
	public int findMinUmbral() throws Exception {
		return this.umbralVerificacionMapper.findMinUmbral();
	}

}

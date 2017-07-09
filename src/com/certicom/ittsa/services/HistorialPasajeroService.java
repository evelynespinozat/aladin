package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.HistorialPasajero;
import com.certicom.ittsa.mapper.HistorialPasajeroMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class HistorialPasajeroService implements HistorialPasajeroMapper{
	
	HistorialPasajeroMapper historialPasajeroMapper = (HistorialPasajeroMapper)ServiceFinder.findBean("historialPasajeroMapper");



	@Override
	public void eliminarHistorialPasajero(Integer idHistorialPasajero) throws Exception {

		historialPasajeroMapper.eliminarHistorialPasajero(idHistorialPasajero);
	}

	@Override
	public void crearHistorialPasajero(HistorialPasajero historialPasajero) throws Exception {

		historialPasajeroMapper.crearHistorialPasajero(historialPasajero);
	}

	@Override
	public List<HistorialPasajero> getNumberTravel(String dni) throws Exception {
		return historialPasajeroMapper.getNumberTravel(dni);
	}
	
	@Override
	public HistorialPasajero findByNrocDNI(String dni) throws Exception {

		return historialPasajeroMapper.findByNrocDNI(dni);
	}

	@Override
	public void actualizarOperacionPosterga(HistorialPasajero historialPasajero)
			throws Exception {
		// TODO Auto-generated method stub
		historialPasajeroMapper.actualizarOperacionPosterga(historialPasajero);
	}

	@Override
	public List<HistorialPasajero> getDetalleHistorialPasajero(
			Integer idHistorialPasajero) throws Exception {
		// TODO Auto-generated method stub
		return historialPasajeroMapper.getDetalleHistorialPasajero(idHistorialPasajero);
	}

	@Override
	public List<HistorialPasajero> getHistorialByDniAsientoVentaId(String dni,
			Integer asientoVentaId) throws Exception {
		// TODO Auto-generated method stub
		return historialPasajeroMapper.getHistorialByDniAsientoVentaId(dni, asientoVentaId);
	}
	
}

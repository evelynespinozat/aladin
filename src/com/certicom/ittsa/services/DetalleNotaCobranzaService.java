package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.DetalleNotaCobranza;
import com.certicom.ittsa.mapper.DetalleNotaCobranzaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DetalleNotaCobranzaService implements DetalleNotaCobranzaMapper{
	
	DetalleNotaCobranzaMapper detalleNotaCobranzaMapper = (DetalleNotaCobranzaMapper)ServiceFinder.findBean("detalleNotaCobranzaMapper");

	@Override
	public List<DetalleNotaCobranza> findAll() throws Exception {
		// TODO Auto-generated method stub
		return detalleNotaCobranzaMapper.findAll();
	}

	@Override
	public DetalleNotaCobranza findById(Integer idDetNotaCobranza)
			throws Exception {
		// TODO Auto-generated method stub
		return detalleNotaCobranzaMapper.findById(idDetNotaCobranza);
	}

	@Override
	public void eliminarDetalleNotaCobranza(Integer idDetNotaCobranza)
			throws Exception {
		// TODO Auto-generated method stub
		detalleNotaCobranzaMapper.eliminarDetalleNotaCobranza(idDetNotaCobranza);
	}

	@Override
	public void crearDetalleNotaCobranza(DetalleNotaCobranza detalleNotaCobranza)
			throws Exception {
		// TODO Auto-generated method stub
		detalleNotaCobranzaMapper.crearDetalleNotaCobranza(detalleNotaCobranza);
	}

	@Override
	public void actualizarDetalleNotaCobranza(
			DetalleNotaCobranza detalleNotaCobranza) throws Exception {
		// TODO Auto-generated method stub
		detalleNotaCobranzaMapper.actualizarDetalleNotaCobranza(detalleNotaCobranza);
	}

	@Override
	public List<DetalleNotaCobranza> obtenerDetalleNotaCobranza(
			Integer idNotaCobranza) throws Exception {
		// TODO Auto-generated method stub
		return detalleNotaCobranzaMapper.obtenerDetalleNotaCobranza(idNotaCobranza);
	}

	
}

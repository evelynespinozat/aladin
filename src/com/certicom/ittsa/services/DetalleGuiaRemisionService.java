package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.DetalleGuiaRemision;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.DetalleGuiaRemisionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DetalleGuiaRemisionService implements DetalleGuiaRemisionMapper{
	
	DetalleGuiaRemisionMapper detalleGuiaRemisionMapper = (DetalleGuiaRemisionMapper)ServiceFinder.findBean("detalleGuiaRemisionMapper");

	@Override
	public List<DetalleGuiaRemision> findAll() throws Exception {
		return detalleGuiaRemisionMapper.findAll();
	}

	@Override
	public DetalleGuiaRemision findById(Integer idDetGuiaRemision)
			throws Exception {
		return detalleGuiaRemisionMapper.findById(idDetGuiaRemision);
	}

	@Override
	public void eliminarDetalleGuiaRemision(Integer idDetGuiaRemision)
			throws Exception {
		detalleGuiaRemisionMapper.eliminarDetalleGuiaRemision(idDetGuiaRemision);
	}

	@Override
	public void crearDetalleGuiaRemision(DetalleGuiaRemision detalleGuiaRemision)
			throws Exception {
		detalleGuiaRemisionMapper.crearDetalleGuiaRemision(detalleGuiaRemision);
	}

	@Override
	public void actualizarDetalleGuiaRemision(
			DetalleGuiaRemision detalleGuiaRemision) throws Exception {
		detalleGuiaRemisionMapper.actualizarDetalleGuiaRemision(detalleGuiaRemision);
	}

	
}

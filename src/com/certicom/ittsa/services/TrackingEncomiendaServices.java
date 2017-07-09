package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.TrackingEncomiendaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TrackingEncomiendaServices implements TrackingEncomiendaMapper{
	
	TrackingEncomiendaMapper trackingEncomiendaMapper = (TrackingEncomiendaMapper)ServiceFinder.findBean("trackingEncomiendaMapper");

	@Override
	public List<TrackingEncomienda> findAll() throws Exception {
		// TODO Auto-generated method stub
		return trackingEncomiendaMapper.findAll();
	}

	@Override
	public TrackingEncomienda findById(Integer idEncomienda) throws Exception {
		// TODO Auto-generated method stub
		return trackingEncomiendaMapper.findById(idEncomienda); 
	}

	@Override
	public void eliminarTrackingEncomienda(Integer idEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		trackingEncomiendaMapper.eliminarTrackingEncomienda(idEncomienda); 
	}

	@Override
	public void crearTrackingEncomienda(TrackingEncomienda trackingEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		trackingEncomiendaMapper.crearTrackingEncomienda(trackingEncomienda); 
	}

	@Override
	public void actualizarTrackingEncomienda(
			TrackingEncomienda trackingEncomienda) throws Exception {
		// TODO Auto-generated method stub
		trackingEncomiendaMapper.actualizarTrackingEncomienda(trackingEncomienda); 
	}

	@Override
	public void actualizarEstadoAnterior(Integer idEncomienda) throws Exception {
		// TODO Auto-generated method stub
		trackingEncomiendaMapper.actualizarEstadoAnterior(idEncomienda);
	}

	

}

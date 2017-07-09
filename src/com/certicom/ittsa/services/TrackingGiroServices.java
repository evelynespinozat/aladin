package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.TrackingGiro;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.TrackingEncomiendaMapper;
import com.certicom.ittsa.mapper.TrackingGiroMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TrackingGiroServices implements TrackingGiroMapper{
	
	TrackingGiroMapper trackingGiroMapper = (TrackingGiroMapper)ServiceFinder.findBean("trackingGiroMapper");

	@Override
	public List<TrackingGiro> findAll() throws Exception {
		// TODO Auto-generated method stub
		return trackingGiroMapper.findAll();
	}

	@Override
	public TrackingGiro findById(Integer idGiro) throws Exception {
		// TODO Auto-generated method stub
		return trackingGiroMapper.findById(idGiro); 
	}

	@Override
	public void eliminarTrackingGiro(Integer idEncomienda) throws Exception {
		// TODO Auto-generated method stub
		trackingGiroMapper.eliminarTrackingGiro(idEncomienda); 
	}

	@Override
	public void crearTrackingGiro(TrackingGiro trackingGiro)
			throws Exception {
		// TODO Auto-generated method stub
		trackingGiroMapper.crearTrackingGiro(trackingGiro); 
	}

	@Override
	public void actualizarTrackingGiro(TrackingGiro trackingGiro)
			throws Exception {
		// TODO Auto-generated method stub
		trackingGiroMapper.actualizarTrackingGiro(trackingGiro); 
	}


}

package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.TrackingBoleto;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.TrackingBoletoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TrackingBoletoService implements TrackingBoletoMapper{

	TrackingBoletoMapper trackingMapper = (TrackingBoletoMapper)ServiceFinder.findBean("trackingBoletoMapper");

	@Override
	public void creartrack(TrackingBoleto tracking) throws Exception {
		trackingMapper.creartrack(tracking);
	}

	@Override
	public List<TrackingBoleto> findByNroSerieNumeroPersona(String numeroDoc)
			throws Exception {
		return trackingMapper.findByNroSerieNumeroPersona(numeroDoc);
	}

	@Override
	public List<TrackingBoleto> findBySerieNumeroBoleto(String serie,
			String numeroBoleto) throws Exception {
		return trackingMapper.findBySerieNumeroBoleto(serie, numeroBoleto);
	}

	@Override
	public TrackingBoleto findByServicioProgramacionNumeroPersona(
			Integer idservicio, Integer idprogramacion, Integer idasientoventa,
			String nrodocumento) throws Exception {
		return trackingMapper.findByServicioProgramacionNumeroPersona(idservicio, idprogramacion, idasientoventa, nrodocumento);
	}

	@Override
	public void actualizarTracking(TrackingBoleto tracking) throws Exception {
		trackingMapper.actualizarTracking(tracking);
	}

	@Override
	public List<TrackingBoleto> findByPersona(String nrodocumento) throws Exception {
		return trackingMapper.findByPersona(nrodocumento);
	}

	@Override
	public TrackingBoleto searchBySerieNumeroBoleto(String serie,
			String numeroBoleto) throws Exception {
		return trackingMapper.searchBySerieNumeroBoleto(serie, numeroBoleto);
	}
	

	@Override
	public TrackingBoleto searchByAsientoVenta(Integer idasientoventa,
			Integer idAsiento) throws Exception {
		return trackingMapper.searchByAsientoVenta(idasientoventa,idAsiento);
	}

	@Override
	public List<TrackingBoleto> getTicketsCanceled(Integer idOficina, Date fechaInicio, Date fechaFin)
			throws Exception {
		return trackingMapper.getTicketsCanceled(idOficina, fechaInicio, fechaFin);
	}

	public List<TrackingBoleto> buscarReservado(String numero, String estadoReserva,
			String documentoPersona, Integer idProgramacion, Integer idServicio) throws Exception {
		// TODO Auto-generated method stub
		return trackingMapper.buscarReservado(numero, estadoReserva,documentoPersona, idProgramacion,idServicio);
	}


	@Override
	public List<TrackingBoleto> findBySerieAndNumero(String serie,
			String numeroBoleto) throws Exception {
		return trackingMapper.findBySerieAndNumero(serie, numeroBoleto);
	}


	@Override
	public void actualizarTrackingReferencia(String numeroRelacionado,
			String serieRelacionado,
			Integer IdTrackingBoleto) throws Exception {
		trackingMapper.actualizarTrackingReferencia(numeroRelacionado, serieRelacionado, IdTrackingBoleto);
	}


}

package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.Historial_AsigPCTMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Historial_AsigPCTServices implements Historial_AsigPCTMapper{
	
	Historial_AsigPCTMapper historialMapper = (Historial_AsigPCTMapper)ServiceFinder.findBean("historial_asigPCTMapper");

	@Override
	public List<Historial_AsigPCT> findAll() throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findAll();
	}

	@Override
	public Historial_AsigPCT findById(Integer idHistorial) throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findById(idHistorial);
	}

	@Override
	public void eliminarHistorial(Integer idHistorial) throws Exception {
		// TODO Auto-generated method stub
		historialMapper.eliminarHistorial(idHistorial); 
	}

	@Override
	public void crearHistorial_Piloto(Historial_AsigPCT historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.crearHistorial_Piloto(historial); 
	}

	@Override
	public void crearHistorial_Copiloto(Historial_AsigPCT historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.crearHistorial_Copiloto(historial); 
	}

	@Override
	public void actualizarHistorial(Historial_AsigPCT historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.actualizarHistorial(historial); 
	}

	@Override
	public void actualizarEstadoHistorial_Piloto(Historial_AsigPCT historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.actualizarEstadoHistorial_Piloto(historial); 
	}

	@Override
	public void actualizarEstadoHistorial_Copiloto(Historial_AsigPCT historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.actualizarEstadoHistorial_Copiloto(historial); 
	}

	@Override
	public List<Historial_AsigPCT> findHistorialByFechas(Integer idBus,
			Date fInicio, Date fFin) throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findHistorialByFechas(idBus, fInicio, fFin); 
	}

	@Override
	public List<Historial_AsigPCT> findHistorialPilotoByFechas(
			Integer idPiloto, Date fInicio, Date fFin) throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findHistorialPilotoByFechas(idPiloto, fInicio, fFin); 
	}

	

}

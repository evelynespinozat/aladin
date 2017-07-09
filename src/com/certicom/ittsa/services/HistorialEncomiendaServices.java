package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.HistorialEncomiendaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class HistorialEncomiendaServices implements HistorialEncomiendaMapper{
	
	HistorialEncomiendaMapper historialEncomiendaMapper = (HistorialEncomiendaMapper)ServiceFinder.findBean("historialEncomiendaMapper");

	@Override
	public List<HistorialEncomienda> findAll() throws Exception {
		// TODO Auto-generated method stub
		return historialEncomiendaMapper.findAll();
	}

	@Override
	public HistorialEncomienda findById(Integer idHistorial) throws Exception {
		// TODO Auto-generated method stub
		return historialEncomiendaMapper.findById(idHistorial);	
	}

	@Override
	public void eliminarHistorialEncomienda(Integer idHistorial)
			throws Exception {
		// TODO Auto-generated method stub
		historialEncomiendaMapper.eliminarHistorialEncomienda(idHistorial); 
	}

	@Override
	public void crearHistorialEncomienda(HistorialEncomienda historialEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		historialEncomiendaMapper.crearHistorialEncomienda(historialEncomienda); 
	}

	@Override
	public void actualizarHistorialEncomienda(
			HistorialEncomienda historialEncomienda) throws Exception {
		// TODO Auto-generated method stub
		historialEncomiendaMapper.actualizarHistorialEncomienda(historialEncomienda); 
	}

	@Override
	public List<HistorialEncomienda> findByDNIRemitente(String dniRemitente)
			throws Exception {
		// TODO Auto-generated method stub
		return historialEncomiendaMapper.findByDNIRemitente(dniRemitente); 
	}

	@Override
	public HistorialEncomienda findByIdEncomienda(Integer idEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		return historialEncomiendaMapper.findByIdEncomienda(idEncomienda); 
	}



}

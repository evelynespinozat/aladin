package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.EncomiendaMapper;
import com.certicom.ittsa.mapper.GiroMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class GiroServices implements GiroMapper{
	
	GiroMapper giroMapper = (GiroMapper)ServiceFinder.findBean("giroMapper");

	@Override
	public List<Giro> findAll() throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.findAll();
	}

	@Override
	public Giro findById(Integer idGiro) throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.findById(idGiro); 
	}

	@Override
	public Giro findLastGiroByPV(Integer idPuntoVenta) throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.findLastGiroByPV(idPuntoVenta); 
	}

	@Override
	public void eliminarGiro(Integer idGiro) throws Exception {
		// TODO Auto-generated method stub
		giroMapper.eliminarGiro(idGiro);
	}

	@Override
	public void crearGiro(Giro giro) throws Exception {
		// TODO Auto-generated method stub
		giroMapper.crearGiro(giro);
	}

	@Override
	public void actualizarGiro(Giro giro) throws Exception {
		// TODO Auto-generated method stub
		giroMapper.actualizarGiro(giro);
	}

	@Override
	public void actualizarEstadoGiro(Giro giro) throws Exception {
		// TODO Auto-generated method stub
		giroMapper.actualizarEstadoGiro(giro); 
	}

	@Override
	public List<Giro> findGirosxCobrarByFecha(Integer idOrigen,
			Integer idDestino, Date fechaInicio, Date fechaFin)
			throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.findGirosxCobrarByFecha(idOrigen, idDestino, fechaInicio, fechaFin);
	}

	@Override
	public List<Giro> findGirosxCobrarByOrig_Dest(Integer idOrigen,
			Integer idDestino) throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.findGirosxCobrarByOrig_Dest(idOrigen, idDestino);
	}

	@Override
	public List<Giro> girosxCobrar() throws Exception {
		// TODO Auto-generated method stub
		return giroMapper.girosxCobrar();
	}

	@Override
	public List<Giro> buscarGirosxCobrar(Giro giro) throws Exception {
		return giroMapper.buscarGirosxCobrar(giro);
	}

	
}

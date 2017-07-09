package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Incidencia;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.IncidenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class IncidenciaServices implements IncidenciaMapper{
	
	IncidenciaMapper incidenciaMapper = (IncidenciaMapper)ServiceFinder.findBean("incidenciaMapper");

	@Override
	public List<Incidencia> findAll() throws Exception {
		// TODO Auto-generated method stub
		return incidenciaMapper.findAll();
	}

	@Override
	public Agencia findById(Integer incidenciaId) throws Exception {
		// TODO Auto-generated method stub
		return incidenciaMapper.findById(incidenciaId);
	}

	@Override
	public void eliminarIncicencia(Integer incidenciaId) throws Exception {
		// TODO Auto-generated method stub
		incidenciaMapper.eliminarIncicencia(incidenciaId);
	}

	@Override
	public void crearIncidencia_Piloto(Incidencia incidencia) throws Exception {
		// TODO Auto-generated method stub
		incidenciaMapper.crearIncidencia_Piloto(incidencia);
	}

	@Override
	public void crearIncidencia_Terramoza(Incidencia incidencia)
			throws Exception {
		// TODO Auto-generated method stub
		incidenciaMapper.crearIncidencia_Terramoza(incidencia); 
	}

	@Override
	public List<Incidencia> findByPilotoId(Integer idPiloto) throws Exception {
		// TODO Auto-generated method stub
		return incidenciaMapper.findByPilotoId(idPiloto); 
	}

	@Override
	public List<Incidencia> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return incidenciaMapper.findByTerramozaId(idTerramoza); 
	}

	
	

}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.domain.Historial_Laboral;
import com.certicom.ittsa.mapper.CapacitacionMapper;
import com.certicom.ittsa.mapper.Historial_LaboralMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Historial_LaboralService implements Historial_LaboralMapper{

	Historial_LaboralMapper historialMapper = (Historial_LaboralMapper)ServiceFinder.findBean("historialMapper");

	@Override
	public List<Historial_Laboral> findAll() throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findLast();
	}

	@Override
	public Historial_Laboral findById(Integer idHistorial) throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findById(idHistorial); 
	}

	@Override
	public List<Historial_Laboral> findByPilotoId(Integer idPiloto)
			throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findByPilotoId(idPiloto); 
	}

	@Override
	public List<Historial_Laboral> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return historialMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void eliminarHistorial(Integer idHistorial) throws Exception {
		// TODO Auto-generated method stub
		historialMapper.eliminarHistorial(idHistorial); 
	}

	@Override
	public void crearHistorial_Piloto(Historial_Laboral historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.crearHistorial_Piloto(historial); 
	}

	@Override
	public void crearHistorial_Terramoza(Historial_Laboral historial)
			throws Exception {
		// TODO Auto-generated method stub
		historialMapper.crearHistorial_Terramoza(historial); 
	}

	
	


}

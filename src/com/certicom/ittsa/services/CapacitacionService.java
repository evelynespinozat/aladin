package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.mapper.CapacitacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class CapacitacionService implements CapacitacionMapper{

	CapacitacionMapper capacitacionMapper = (CapacitacionMapper)ServiceFinder.findBean("capacitacionMapper");
	
	@Override
	public List<Capacitacion> findAll() throws Exception {
		return capacitacionMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		return capacitacionMapper.findLast();
	}

	@Override
	public Capacitacion findById(Integer capacitacionId) throws Exception {
		return capacitacionMapper.findById(capacitacionId);
	}

	@Override
	public void eliminarCapacitacion(Integer capacitacionId) throws Exception {
		capacitacionMapper.eliminarCapacitacion(capacitacionId);
	}

	@Override
	public List<Capacitacion> findByPilotoId(Integer idPiloto) throws Exception {
		return capacitacionMapper.findByPilotoId(idPiloto);
	}

	@Override
	public List<Capacitacion> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return capacitacionMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void crearCapacitacion_Piloto(Capacitacion capacitacion)
			throws Exception {
		// TODO Auto-generated method stub
		capacitacionMapper.crearCapacitacion_Piloto(capacitacion);
	}

	@Override
	public void crearCapacitacion_Terramoza(Capacitacion capacitacion)
			throws Exception {
		// TODO Auto-generated method stub
		capacitacionMapper.crearCapacitacion_Terramoza(capacitacion); 
	}


}

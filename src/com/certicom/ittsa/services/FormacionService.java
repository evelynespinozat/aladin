package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Formacion;
import com.certicom.ittsa.mapper.EvaluacionMapper;
import com.certicom.ittsa.mapper.FormacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class FormacionService implements FormacionMapper{

	FormacionMapper formacionMapper = (FormacionMapper)ServiceFinder.findBean("formacionMapper");
	
	@Override
	public List<Formacion> findAll() throws Exception {
		return formacionMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		return formacionMapper.findLast();
	}

	@Override
	public Formacion findById(Integer formacionId) throws Exception {
		return formacionMapper.findById(formacionId);
	}

	@Override
	public void eliminarFormacion(Integer formacionId) throws Exception {
		formacionMapper.eliminarFormacion(formacionId);
	}

	@Override
	public List<Formacion> findByPilotoId(Integer idPiloto) throws Exception {
		return formacionMapper.findByPilotoId(idPiloto);
	}

	@Override
	public List<Formacion> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return formacionMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void crearFormacion_Piloto(Formacion formacion) throws Exception {
		// TODO Auto-generated method stub
		formacionMapper.crearFormacion_Piloto(formacion); 
	}

	@Override
	public void crearFormacion_Terramoza(Formacion formacion) throws Exception {
		// TODO Auto-generated method stub
		formacionMapper.crearFormacion_Terramoza(formacion); 
	}


}

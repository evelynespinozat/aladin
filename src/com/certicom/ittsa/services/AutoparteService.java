package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.mapper.AutoparteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AutoparteService implements AutoparteMapper{
	
	AutoparteMapper autoparteMapper = (AutoparteMapper)ServiceFinder.findBean("autoparteMapper");

	@Override
	public List<Autoparte> findAll() throws Exception {

		return autoparteMapper.findAll();
	}

	@Override
	public Autoparte findById(Integer autoparteID) throws Exception {

		return autoparteMapper.findById(autoparteID);
	}

	@Override
	public void eliminarAutoparte(Integer autoparteID) throws Exception {

		autoparteMapper.eliminarAutoparte(autoparteID);
	}

	@Override
	public void crearAutoparte(Autoparte autoparte) throws Exception {

		autoparteMapper.crearAutoparte(autoparte);
	}

	@Override
	public void actualizarAutoparte(Autoparte autoparte) throws Exception {
		autoparteMapper.actualizarAutoparte(autoparte);
		
	}

	@Override
	public List<Autoparte> listaAutopartesActivas() throws Exception {

		return autoparteMapper.listaAutopartesActivas();
	}

	@Override
	public int countxAutomotor(Integer idAutomotor) throws Exception {
		// TODO Auto-generated method stub
		return autoparteMapper.countxAutomotor(idAutomotor);
	}
	
	

}

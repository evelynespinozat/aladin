package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.mapper.AutopartesAutomotorMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AutopartesAutomotorService implements AutopartesAutomotorMapper{
	
	AutopartesAutomotorMapper autopartesAutomotorMapper = (AutopartesAutomotorMapper)ServiceFinder.findBean("autopartesAutomotorMapper");

	@Override
	public void eliminarAutopartesAutomotor(Integer partesAutomotorID) throws Exception {

		autopartesAutomotorMapper.eliminarAutopartesAutomotor(partesAutomotorID);
	}

	@Override
	public void crearAutopartesAutomotor(AutoparteAutomotor partesAutomotor) throws Exception {

		autopartesAutomotorMapper.crearAutopartesAutomotor(partesAutomotor);
	}

	@Override
	public List<AutoparteAutomotor> listaAutopartesxAutomotor(Integer idAutomotor)
			throws Exception {
		// TODO Auto-generated method stub
		return autopartesAutomotorMapper.listaAutopartesxAutomotor(idAutomotor);
	}

	@Override
	public int verificarAutopartesxAutomotor(Integer idAutomotor, Integer idParte)
			throws Exception {
		// TODO Auto-generated method stub
		return autopartesAutomotorMapper.verificarAutopartesxAutomotor(idAutomotor, idParte);
	}
	

}

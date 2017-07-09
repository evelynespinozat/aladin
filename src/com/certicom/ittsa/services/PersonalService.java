package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Personal;
import com.certicom.ittsa.mapper.PersonalMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PersonalService implements PersonalMapper{
	
	PersonalMapper personalMapper = (PersonalMapper) ServiceFinder.findBean("personalMapper");

	@Override
	public List<Personal> listarPersonalActivo() throws Exception {
		return personalMapper.listarPersonalActivo();
	}

	@Override
	public Personal findById(Integer idPersonal) throws Exception {
		return personalMapper.findById(idPersonal);
	}

	@Override
	public void crearPersonal(Personal personal) throws Exception {
		personalMapper.crearPersonal(personal);
	}

	@Override
	public void actualizarPersonal(Personal personal) throws Exception {
		personalMapper.actualizarPersonal(personal);
	}

	@Override
	public List<Personal> findAll() throws Exception {
		return personalMapper.findAll();
	}

	@Override
	public void deletePersonal(Integer idPersonal) throws Exception {
		System.out.println("el id del perosnal es " + idPersonal);
		personalMapper.deletePersonal(idPersonal);
	}

}

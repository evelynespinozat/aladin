package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.mapper.PersonaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PersonaServices implements PersonaMapper{
	
	PersonaMapper personaMapper = (PersonaMapper)ServiceFinder.findBean("personaMapper");

	@Override
	public Persona findByNroDocumento(String Dni) throws Exception {
		return personaMapper.findByNroDocumento(Dni);
	}

	@Override
	public void crearPersona(Persona persona) throws Exception {
		personaMapper.crearPersona(persona);
		
	}

	@Override
	public void actualizarPersona(Persona persona) throws Exception {
		System.out.println("ACTUALIZAR PERSONA SERIVCE");
		personaMapper.actualizarPersona(persona);
		
	}

	@Override
	public List<Persona> findByApellidos(String aPaterno, String aMaterno)
			throws Exception {
		// TODO Auto-generated method stub
		return personaMapper.findByApellidos(aPaterno, aMaterno); 
	}

	@Override
	public List<Persona> findByApellidoPaterno(String aPaterno)
			throws Exception {
		// TODO Auto-generated method stub
		return personaMapper.findByApellidoPaterno(aPaterno);
	}

	@Override
	public List<Persona> findByApellidoMaterno(String aMaterno)
			throws Exception {
		// TODO Auto-generated method stub
		return personaMapper.findByApellidoMaterno(aMaterno);
	}

	@Override
	public void actualizaHuella(Persona persona) throws Exception {
		// TODO Auto-generated method stub
		personaMapper.actualizaHuella(persona);
	}

	@Override
	public Object actualizarPasajeroFrecuente(String dni) {
		// TODO Auto-generated method stub
		return personaMapper.actualizarPasajeroFrecuente(dni);
	}
	
}

package com.certicom.ittsa.services;

import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.PersonaTrama;
import com.certicom.ittsa.mapper.PersonaMapper;
import com.certicom.ittsa.mapper.PersonaTramaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;



public class PersonaTramaServices implements PersonaTramaMapper{

	PersonaTramaMapper personaTramaMapper = (PersonaTramaMapper)ServiceFinder.findBean("personaTramaMapper");
	
	@Override
	public PersonaTrama findByNroDocumento(String Dni) throws Exception {
		
		return this.personaTramaMapper.findByNroDocumento(Dni);
	}

	@Override
	public void crearPersonaTrama(PersonaTrama persona) throws Exception {
		this.personaTramaMapper.crearPersonaTrama(persona);
		
	}

	@Override
	public void actualizarPersonaTrama(PersonaTrama persona) throws Exception {
		this.personaTramaMapper.actualizarPersonaTrama(persona);
		
	}

	@Override
	public void actualizaHuella(PersonaTrama persona) throws Exception {
		this.actualizaHuella(persona);
		
	}




}

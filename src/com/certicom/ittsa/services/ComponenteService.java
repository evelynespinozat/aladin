package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Componente;
import com.certicom.ittsa.mapper.ComponenteMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ComponenteService implements ComponenteMapper{
	
	ComponenteMapper componenteMapper = (ComponenteMapper)ServiceFinder.findBean("componenteMapper");

	@Override
	public Componente findById(Integer Idcomponente) throws Exception {
		return componenteMapper.findById(Idcomponente);
	}

	@Override
	public void eliminarComponente(Integer Idcomponente) throws Exception {
		componenteMapper.eliminarComponente(Idcomponente);
	}

	@Override
	public void crearComponente(Componente componente) throws Exception {
		componenteMapper.crearComponente(componente);
	}

	@Override
	public void actualizarComponente(Componente Componente) throws Exception {
		componenteMapper.actualizarComponente(Componente);
	}

	@Override
	public List<Componente> listaComponentesxPV(Integer IdPuntoVenta) throws Exception {
		return componenteMapper.listaComponentesxPV(IdPuntoVenta);
	}


}

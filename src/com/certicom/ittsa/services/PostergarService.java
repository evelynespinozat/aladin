package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.PostergacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PostergarService implements PostergacionMapper{
	
	PostergacionMapper postergacionMapper = (PostergacionMapper)ServiceFinder.findBean("postergacionMapper");

	@Override
	public void crearPostergacion(Postergacion postergacion) throws Exception {
		//System.out.println("persisitiendo en la bed Postergar");
		postergacionMapper.crearPostergacion(postergacion);
		
	}

	@Override
	public List<Postergacion> listaPostergacion(String p_nroDoc)
			throws Exception {
		return postergacionMapper.listaPostergacion(p_nroDoc);
	}

	@Override
	public void actualizarPostergacion(Postergacion postergacion)
			throws Exception {
		postergacionMapper.actualizarPostergacion(postergacion);
		
	}

	@Override
	public List<Postergacion> listaPostergacionxFiltros(Postergacion filter)
			throws Exception {
		return postergacionMapper.listaPostergacionxFiltros(filter);
	}

	public Postergacion findByIdAsientoVenta(Integer idasientoventa) {
		// TODO Auto-generated method stub
		return postergacionMapper.findByIdAsientoVenta(idasientoventa);
	}
	
}

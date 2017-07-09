package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.UmbralCaptura;
import com.certicom.ittsa.mapper.UmbralCapturaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class UmbralCapturaService implements UmbralCapturaMapper{
	
	UmbralCapturaMapper umbralCapturaMapper=(UmbralCapturaMapper)ServiceFinder.findBean("umbralCapturaMapper");

	@Override
	public List<UmbralCaptura> findAll() throws Exception {
		
		return this.umbralCapturaMapper.findAll();
	}

	@Override
	public UmbralCaptura findById(Integer id_captura) throws Exception {
		
		return this.findById(id_captura);
	}

	@Override
	public void eliminarUmbralCaptura(Integer id_captura) throws Exception {
		this.umbralCapturaMapper.eliminarUmbralCaptura(id_captura);
		
	}

	@Override
	public void crearUmbralCaptura(UmbralCaptura umbralCaptura)
			throws Exception {
		this.umbralCapturaMapper.crearUmbralCaptura(umbralCaptura);
		
	}

	@Override
	public void actualizarUmbralCaptura(UmbralCaptura umbralCaptura)
			throws Exception {
		this.umbralCapturaMapper.actualizarUmbralCaptura(umbralCaptura);
		
	}

	@Override
	public List<UmbralCaptura> listaUmbralCapturaActivos() throws Exception {
		
		return this.umbralCapturaMapper.listaUmbralCapturaActivos();
	}

	@Override
	public UmbralCaptura findByEstado(Boolean estado) throws Exception {
		return this.umbralCapturaMapper.findByEstado(estado);
	}

}

package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.mapper.TerramozaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TerramozaService implements TerramozaMapper{
	
	TerramozaMapper terramozaMapper = (TerramozaMapper)ServiceFinder.findBean("terramozaMapper");

	@Override
	public List<Terramoza> findAll() throws Exception {

		return terramozaMapper.findAll();
	}

	@Override
	public Terramoza findById(Integer idTerramoza) throws Exception {

		return terramozaMapper.findById(idTerramoza);
	}

	@Override
	public void eliminarTerramoza(Integer idTerramoza) throws Exception {

		terramozaMapper.eliminarTerramoza(idTerramoza);
	}

	@Override
	public void crearTerramoza(Terramoza terramoza) throws Exception {

		terramozaMapper.crearTerramoza(terramoza);
	}

	@Override
	public void actualizarTerramoza(Terramoza terramoza) throws Exception {
		
		terramozaMapper.actualizarTerramoza(terramoza);
		
	}

	@Override
	public List<Terramoza> listaTerramozaActivas() throws Exception {

		return terramozaMapper.listaTerramozaActivas();
	}

	@Override
	public Terramoza findByDNI(String dni) throws Exception {
		// TODO Auto-generated method stub
		return terramozaMapper.findByDNI(dni); 
	}

	@Override
	public void actualizarTerramozaSnFoto(Terramoza terramoza) throws Exception {
		terramozaMapper.actualizarTerramozaSnFoto(terramoza);
	}

	@Override
	public Integer verificarDniCarnetRepetidos(String dni) throws Exception {
		return terramozaMapper.verificarDniCarnetRepetidos(dni);
	}
	
	

}

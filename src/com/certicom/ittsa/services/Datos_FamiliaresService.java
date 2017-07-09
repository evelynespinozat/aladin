package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Datos_Familiares;
import com.certicom.ittsa.mapper.Datos_FamiliaresMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class Datos_FamiliaresService implements Datos_FamiliaresMapper{

	Datos_FamiliaresMapper datosFMapper = (Datos_FamiliaresMapper)ServiceFinder.findBean("datosFMapper");

	@Override
	public List<Datos_Familiares> findAll() throws Exception {
		// TODO Auto-generated method stub
		return datosFMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		// TODO Auto-generated method stub
		return datosFMapper.findLast();
	}

	@Override
	public Datos_Familiares findById(Integer idDatosF) throws Exception {
		// TODO Auto-generated method stub
		return datosFMapper.findById(idDatosF); 
	}

	@Override
	public List<Datos_Familiares> findByPilotoId(Integer idPiloto)
			throws Exception {
		// TODO Auto-generated method stub
		return datosFMapper.findByPilotoId(idPiloto); 
	}

	@Override
	public List<Datos_Familiares> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return datosFMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void eliminarDatosF(Integer idDatosF) throws Exception {
		// TODO Auto-generated method stub
		datosFMapper.eliminarDatosF(idDatosF); 
	}

	@Override
	public void crearDatosF_Piloto(Datos_Familiares datosF) throws Exception {
		// TODO Auto-generated method stub
		datosFMapper.crearDatosF_Piloto(datosF); 
	}

	@Override
	public void crearDatosF_Terramoza(Datos_Familiares datosF) throws Exception {
		// TODO Auto-generated method stub
		datosFMapper.crearDatosF_Terramoza(datosF); 
	}

	@Override
	public void actualizarDatosF_Terramoza(Datos_Familiares datosF)
			throws Exception {
		// TODO Auto-generated method stub
		datosFMapper.actualizarDatosF_Terramoza(datosF); 
	}



}

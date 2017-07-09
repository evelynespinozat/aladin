package com.certicom.ittsa.services;

import java.io.Serializable;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class AgenciaService implements AgenciaMapper, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1181435911334972658L;
	AgenciaMapper agenciaMapper = (AgenciaMapper)ServiceFinder.findBean("agenciaMapper");

	@Override
	public List<Agencia> findAll() throws Exception {

		return agenciaMapper.findAll();
	}

	@Override
	public Agencia findById(Integer agenciaID) throws Exception {

		return agenciaMapper.findById(agenciaID);
	}

	@Override
	public void eliminarAgencia(Integer agenciaID) throws Exception {

		agenciaMapper.eliminarAgencia(agenciaID);
	}

	@Override
	public void crearAgencia(Agencia agencia) throws Exception {

		agenciaMapper.crearAgencia(agencia);
	}

	@Override
	public void actualizarAgencia(Agencia agencia) throws Exception {
		agenciaMapper.actualizarAgencia(agencia);
		
	}

	@Override
	public List<Agencia> listaAgenciasActivas() throws Exception {

		return agenciaMapper.listaAgenciasActivas();
	}

	@Override
	public Agencia findByDescripcion(String Descripcion) throws Exception {
		return agenciaMapper.findByDescripcion(Descripcion);
	}

	@Override
	public void actualizarCombustible(Agencia agencia) throws Exception {
		agenciaMapper.actualizarCombustible(agencia);
	}

	@Override
	public void actualizarAgenciaNroDias(Agencia agencia) throws Exception {
		// TODO Auto-generated method stub
		agenciaMapper.actualizarAgenciaNroDias(agencia);
	}

	@Override
	public List<Agencia> listarAsociados(Integer Origen) throws Exception {
		return agenciaMapper.listarAsociados(Origen);
		
	}
	
	
	

}

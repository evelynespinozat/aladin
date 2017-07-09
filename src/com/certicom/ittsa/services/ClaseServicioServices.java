package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.mapper.ClaseServicioMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ClaseServicioServices implements ClaseServicioMapper{
	
	ClaseServicioMapper claseServicioMapper = (ClaseServicioMapper)ServiceFinder.findBean("claseServicioMapper");

	@Override
	public List<ClaseServicio> findAll() throws Exception {
		return claseServicioMapper.findAll();
	}

	@Override
	public ClaseServicio findById(Integer idclase) throws Exception {
		return claseServicioMapper.findById(idclase);
	}

	@Override
	public void registrarClaseServicio(ClaseServicio claseServicio)
			throws Exception {
		claseServicioMapper.registrarClaseServicio(claseServicio);
		
	}

	@Override
	public void actualizarClaseServicio(ClaseServicio claseServicio)
			throws Exception {
		claseServicioMapper.actualizarClaseServicio(claseServicio);
		
	}

	@Override
	public void eliminarClaseServicio(Integer idclase) throws Exception {
		claseServicioMapper.eliminarClaseServicio(idclase);
	}

	@Override
	public List<ClaseServicio> listaCServiciosActivos() throws Exception {
		return claseServicioMapper.listaCServiciosActivos();
	}

	@Override
	public Integer findLast() throws Exception {
		return claseServicioMapper.findLast();
	}

	@Override
	public void actualizarColumnasClaseServicio(Integer nroColumnasPisoUno,
			Integer nroColumnasPisoDos, Integer asientos, Integer idclase)
			throws Exception {
		// TODO Auto-generated method stub
		claseServicioMapper.actualizarColumnasClaseServicio(nroColumnasPisoUno, nroColumnasPisoDos, asientos, idclase);
	}


	@Override
	public Integer cantClasexCServicio(Integer IdCatServicio) throws Exception {
		return claseServicioMapper.cantClasexCServicio(IdCatServicio);
	}

	@Override
	public Integer obtenerCantidadAsientos(Integer idClase) throws Exception {
		// TODO Auto-generated method stub
		return claseServicioMapper.obtenerCantidadAsientos(idClase);
	}

	@Override
	public Integer cantClaseServicioxTipoAsiento(Integer idTipoAsiento)
			throws Exception {
		// TODO Auto-generated method stub
		return claseServicioMapper.cantClaseServicioxTipoAsiento(idTipoAsiento);
	}


	@Override
	public ClaseServicio findByServicio(Integer IdServicio) throws Exception {
		return claseServicioMapper.findByServicio(IdServicio);
	}

	@Override
	public void actualizarNroAsientos(Integer asientos, Integer idclase)
			throws Exception {
		claseServicioMapper.actualizarNroAsientos(asientos, idclase);
		
	}

	@Override
	public ClaseServicio findByNroAsientos(Integer nroAsientos)
			throws Exception {
		return claseServicioMapper.findByNroAsientos(nroAsientos);
	}


}

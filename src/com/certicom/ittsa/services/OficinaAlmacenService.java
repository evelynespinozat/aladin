package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.OficinaAlmacen;
import com.certicom.ittsa.mapper.OficinaAlmacenMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class OficinaAlmacenService implements OficinaAlmacenMapper{
	
	OficinaAlmacenMapper oficinaAlmacenMapper = (OficinaAlmacenMapper)ServiceFinder.findBean("oficinaAlmacenMapper");

	@Override
	public OficinaAlmacen findById(Integer agenciaID) throws Exception {

		return oficinaAlmacenMapper.findById(agenciaID);
	}

	@Override
	public void eliminarOficinaAlmacen(Integer id) throws Exception {

		oficinaAlmacenMapper.eliminarOficinaAlmacen(id);
	}

	@Override
	public void crearOficinaAlmacen(OficinaAlmacen agencia) throws Exception {

		oficinaAlmacenMapper.crearOficinaAlmacen(agencia);
	}

	@Override
	public List<OficinaAlmacen> listaOficinasxAlmacen(Integer idOficina)
			throws Exception {
		// TODO Auto-generated method stub
		return oficinaAlmacenMapper.listaOficinasxAlmacen(idOficina);
	}

	@Override
	public int verificarAlmacenxOficina(Integer idOficina, Integer idAlmacen)
			throws Exception {
		// TODO Auto-generated method stub
		return oficinaAlmacenMapper.verificarAlmacenxOficina(idOficina, idAlmacen);
	}

}

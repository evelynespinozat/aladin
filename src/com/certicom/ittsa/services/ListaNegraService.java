package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.ListaNegra;
import com.certicom.ittsa.mapper.ListaNegraMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ListaNegraService  implements ListaNegraMapper{

	ListaNegraMapper listaNegraMapper = (ListaNegraMapper) ServiceFinder.findBean("listaNegraMapper");
	
	@Override
	public void registrarListaNegra(ListaNegra listaNegra) throws Exception {
		listaNegraMapper.registrarListaNegra(listaNegra);
	}

	@Override
	public void actualizarListaNegra(ListaNegra listaNegra) throws Exception {
		listaNegraMapper.actualizarListaNegra(listaNegra);
	}

	@Override
	public List<ListaNegra> findAll() throws Exception {
		return listaNegraMapper.findAll();
	}

	@Override
	public void actualizarEstado(Integer idListaNegra, boolean estado)
			throws Exception {
		listaNegraMapper.actualizarEstado(idListaNegra, estado);
	}

	@Override
	public void eliminarListaNegra(Integer idListaNegra) throws Exception {
		listaNegraMapper.eliminarListaNegra(idListaNegra);
	}

	@Override
	public ListaNegra findByIndividuo(String dni) throws Exception {
		return listaNegraMapper.findByIndividuo(dni);
	}

}

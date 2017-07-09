package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.mapper.OficinaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class OficinaService implements OficinaMapper{
	
	OficinaMapper oficinaMapper = (OficinaMapper)ServiceFinder.findBean("oficinaMapper");

	@Override
	public List<Oficina> findAll() throws Exception {
		return oficinaMapper.findAll();
	}

	@Override
	public Oficina findById(Integer idOficina) throws Exception {
		return oficinaMapper.findById(idOficina);
	}

	@Override
	public void eliminarOficina(Integer idOficina) throws Exception {
		oficinaMapper.eliminarOficina(idOficina);
	}

	@Override
	public void crearOficina(Oficina oficina) throws Exception {
		oficinaMapper.crearOficina(oficina);
	}

	@Override
	public void actualizarOficina(Oficina oficina) throws Exception {
		oficinaMapper.actualizarOficina(oficina);
		
	}
	
	@Override
	public List<Oficina> listaOficinas() throws Exception{
		return oficinaMapper.listaOficinas();
	}

	@Override
	public List<Oficina> listaOficinasActivas() throws Exception {

		return oficinaMapper.listaOficinasActivas();
	}

	@Override
	public List<Oficina> getOficinasxAgencia(Integer IdAgencia) {
		return oficinaMapper.getOficinasxAgencia(IdAgencia);
	}

	@Override
	public Integer cantOfixAgencia(Integer IdAgencia) {
		return oficinaMapper.cantOfixAgencia(IdAgencia);
	}

	@Override
	public List<Oficina> listaOficinasXAgencia(Integer IdAgencia)
			throws Exception {
		return oficinaMapper.listaOficinasXAgencia(IdAgencia);
	}

	@Override
	public Oficina findByNombre(String nombre,Integer idagencia) throws Exception {
		// TODO Auto-generated method stub
		return oficinaMapper.findByNombre(nombre,idagencia);
	}


}

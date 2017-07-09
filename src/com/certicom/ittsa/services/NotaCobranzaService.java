package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.NotaCobranza;
import com.certicom.ittsa.mapper.NotaCobranzaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class NotaCobranzaService implements NotaCobranzaMapper{
	
	NotaCobranzaMapper notaCobranzaMapper = (NotaCobranzaMapper)ServiceFinder.findBean("notaCobranzaMapper");

	@Override
	public List<NotaCobranza> findAll() throws Exception {
		return notaCobranzaMapper.findAll();
	}

	@Override
	public NotaCobranza findById(Integer idNotaCobranza) throws Exception {
		return notaCobranzaMapper.findById(idNotaCobranza);
	}

	@Override
	public void eliminarNotaCobranza(Integer idNotaCobranza) throws Exception {
		notaCobranzaMapper.eliminarNotaCobranza(idNotaCobranza);
	}

	@Override
	public void crearNotaCobranza(NotaCobranza notaCobranza) throws Exception {
		notaCobranzaMapper.crearNotaCobranza(notaCobranza);
	}

	@Override
	public void actualizarNotaCobranza(NotaCobranza notaCobranza)
			throws Exception {
		notaCobranzaMapper.actualizarNotaCobranza(notaCobranza); 
	}

	@Override
	public Integer findLast() throws Exception {
		return notaCobranzaMapper.findLast();
	}

	@Override
	public List<NotaCobranza> buscarNotaCobranzaPorCobrar(Integer idOrigen,
			Integer idDestino) throws Exception {
		return notaCobranzaMapper.buscarNotaCobranzaPorCobrar(idOrigen, idDestino);
	}

	@Override
	public void pagarNotaCobranza(Integer idNotaCobranza,String numeroFisico) throws Exception {
		notaCobranzaMapper.pagarNotaCobranza(idNotaCobranza,numeroFisico);
	}

	
}

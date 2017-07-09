package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.TipoDocumento;
import com.certicom.ittsa.mapper.TipoDocumentoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class TipoDocumentoServices implements TipoDocumentoMapper{
	
	TipoDocumentoMapper tipoDocumentoMapper = (TipoDocumentoMapper)ServiceFinder.findBean("tipoDocumentoMapper");

	@Override
	public List<TipoDocumento> findAll() throws Exception {
		return tipoDocumentoMapper.findAll();
	}

	@Override
	public List<TipoDocumento> listaTipDocActivos() throws Exception {
		return tipoDocumentoMapper.listaTipDocActivos();
	}

	@Override
	public TipoDocumento findById(Integer idtipodoc) throws Exception {
		return tipoDocumentoMapper.findById(idtipodoc);
	}

	@Override
	public void registrarTipoDocumento(TipoDocumento tipoDocumento)
			throws Exception {
		tipoDocumentoMapper.registrarTipoDocumento(tipoDocumento);
	}

	@Override
	public void actualizarTipoDocumento(TipoDocumento tipoDocumento)
			throws Exception {
		tipoDocumentoMapper.actualizarTipoDocumento(tipoDocumento);
	}

	@Override
	public void eliminarTipoDocumento(Integer idtipodoc) throws Exception {
		tipoDocumentoMapper.eliminarTipoDocumento(idtipodoc);
	}





}

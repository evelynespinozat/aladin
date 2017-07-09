package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Documento;
import com.certicom.ittsa.mapper.DocumentoMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DocumentoService implements DocumentoMapper{

	DocumentoMapper documentoMapper = (DocumentoMapper)ServiceFinder.findBean("documentoMapper");
	
	@Override
	public List<Documento> findAll() throws Exception {
		return documentoMapper.findAll();
	}

	@Override
	public Integer findLast() throws Exception {
		return documentoMapper.findLast();
	}

	@Override
	public Documento findById(Integer idDocumento) throws Exception {
		return documentoMapper.findById(idDocumento);
	}

	@Override
	public void eliminarDocumento(Integer idDocumento) throws Exception {
		documentoMapper.eliminarDocumento(idDocumento);
	}

	@Override
	public void actualizarDocumento(Documento documento) throws Exception {
		documentoMapper.actualizarDocumento(documento);
	}

	@Override
	public List<Documento> findByPilotoId(Integer idPiloto) throws Exception {
		return documentoMapper.findByPilotoId(idPiloto);
	}

	@Override
	public List<Documento> findByTerramozaId(Integer idTerramoza)
			throws Exception {
		// TODO Auto-generated method stub
		return documentoMapper.findByTerramozaId(idTerramoza); 
	}

	@Override
	public void crearDocumento_Piloto(Documento documento) throws Exception {
		// TODO Auto-generated method stub
		documentoMapper.crearDocumento_Piloto(documento); 
	}

	@Override
	public void crearDocumento_Terramoza(Documento documento) throws Exception {
		// TODO Auto-generated method stub
		documentoMapper.crearDocumento_Terramoza(documento); 
	}

}

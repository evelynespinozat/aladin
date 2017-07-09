package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.DetalleEncomiendaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class DetalleEncomiendaServices implements DetalleEncomiendaMapper{
	
	DetalleEncomiendaMapper detalleEncomiendaMapper = (DetalleEncomiendaMapper)ServiceFinder.findBean("detalleEncomiendaMapper");

	@Override
	public List<DetalleEncomienda> findAll() throws Exception {
		// TODO Auto-generated method stub
		return detalleEncomiendaMapper.findAll();
	}

	@Override
	public DetalleEncomienda findById(Integer idDetalle) throws Exception {
		// TODO Auto-generated method stub
		return detalleEncomiendaMapper.findById(idDetalle); 
	}

	@Override
	public void eliminarDetalleEncomienda(Integer idDetalle) throws Exception {
		// TODO Auto-generated method stub
		detalleEncomiendaMapper.eliminarDetalleEncomienda(idDetalle);
	}

	@Override
	public void crearDetalleEncomienda(DetalleEncomienda detalleEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		detalleEncomiendaMapper.crearDetalleEncomienda(detalleEncomienda); 
	}

	@Override
	public void actualizarDetalleEncomienda(DetalleEncomienda detalleEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		detalleEncomiendaMapper.actualizarDetalleEncomienda(detalleEncomienda); 
	}

	@Override
	public List<DetalleEncomienda> findByIdEncomienda(Integer idEncomienda)
			throws Exception {
		// TODO Auto-generated method stub
		return detalleEncomiendaMapper.findByIdEncomienda(idEncomienda); 
	}

	@Override
	public DetalleEncomienda findByDetalleByCodigoBarrasString(
			String codigoBarrasString) throws Exception {
		// TODO Auto-generated method stub
		return detalleEncomiendaMapper.findByDetalleByCodigoBarrasString(codigoBarrasString); 
	}

	@Override
	public DetalleEncomienda findLastDetalleEncomiendaByIdEnc(
			Integer idEncomienda) throws Exception {
		// TODO Auto-generated method stub
		return detalleEncomiendaMapper.findLastDetalleEncomiendaByIdEnc(idEncomienda); 
	}

	
	

}

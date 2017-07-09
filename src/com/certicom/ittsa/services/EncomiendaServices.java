package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.mapper.AgenciaMapper;
import com.certicom.ittsa.mapper.EncomiendaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class EncomiendaServices implements EncomiendaMapper{
	
	EncomiendaMapper encomiendaMapper = (EncomiendaMapper)ServiceFinder.findBean("encomiendaMapper");

	@Override
	public List<Encomienda> findAll() throws Exception {
		return encomiendaMapper.findAll();
	}

	@Override
	public Encomienda findById(Integer idEncomienda) throws Exception {
		return encomiendaMapper.findById(idEncomienda);
	}

	@Override
	public void eliminarEncomienda(Integer idEncomienda) throws Exception {
		encomiendaMapper.eliminarEncomienda(idEncomienda);
	}

	@Override
	public void crearEncomienda(Encomienda encomienda) throws Exception {
		encomiendaMapper.crearEncomienda(encomienda);
	}

	@Override
	public void actualizarEncomienda(Encomienda encomienda) throws Exception {
		encomiendaMapper.actualizarEncomienda(encomienda); 
	}

	@Override
	public Encomienda findLastEncomiendaByPV(Integer idPuntoVenta)
			throws Exception {
		return encomiendaMapper.findLastEncomiendaByPV(idPuntoVenta); 
	}

	@Override
	public void actualizarEstadoEncomienda(Encomienda encomienda)
			throws Exception {
		encomiendaMapper.actualizarEstadoEncomienda(encomienda); 
	}

	@Override
	public List<Encomienda> listarEncomiendasporFiltros(Encomienda encomienda) {
		return encomiendaMapper.listarEncomiendasporFiltros(encomienda);
	}

	@Override
	public List<Encomienda> listarEncomiendasRecibidas(Encomienda encomienda)
			throws Exception {
		return encomiendaMapper.listarEncomiendasRecibidas(encomienda);
	}

	@Override
	public void actualizarEstadoEncomienda2(Integer idencomienda, Integer estado,Integer idProgramacion)
			throws Exception {
		encomiendaMapper.actualizarEstadoEncomienda2(idencomienda, estado,idProgramacion);
	}

	@Override
	public List<Encomienda> findEncomiendaxCobrarByFecha(Integer idOrigen,
			Integer idDestino, Date fechaInicio, Date fechaFin)
			throws Exception {
		return encomiendaMapper.findEncomiendaxCobrarByFecha(idOrigen, idDestino, fechaInicio, fechaFin);
	}

	@Override
	public List<Encomienda> findEncomiendaxCobrarByOrig_Dest(Integer idOrigen,
			Integer idDestino) throws Exception {
		return encomiendaMapper.findEncomiendaxCobrarByOrig_Dest(idOrigen, idDestino);
	}

	@Override
	public List<Encomienda> listarEncomiendasEmbarcadas(Encomienda encomienda)
			throws Exception {
		return encomiendaMapper.listarEncomiendasEmbarcadas(encomienda);
	}

	@Override
	public List<Encomienda> listarEncomiendasDesembarcadasReparto(Encomienda encomienda)
			throws Exception {
		return encomiendaMapper.listarEncomiendasDesembarcadasReparto(encomienda);
	}

	@Override
	public void actualizarRepartidor(Integer idEncomienda,
			Integer idPersonalReparto) throws Exception  {
		encomiendaMapper.actualizarRepartidor(idEncomienda, idPersonalReparto);
	}

	@Override
	public List<Encomienda> listarEncomiendasDesembarcadas(Encomienda encomienda)
			throws Exception {
		return encomiendaMapper.listarEncomiendasDesembarcadas(encomienda);
	}

	@Override
	public void actualizarValoresRecepcion(Encomienda encomienda)
			throws Exception {
		encomiendaMapper.actualizarValoresRecepcion(encomienda);
	}

	@Override
	public List<Encomienda> rptListarEncomiendasEmbarcadas(Encomienda encomienda) {
		return encomiendaMapper.rptListarEncomiendasEmbarcadas(encomienda);
	}
	
	@Override
	public void actualizarUbicacionAlmacenEncomienda(Encomienda encomienda)
			throws Exception {
		encomiendaMapper.actualizarUbicacionAlmacenEncomienda(encomienda);
	}

	@Override
	public List<Encomienda> encomiendasxRepartidor(Encomienda encomienda) {
		return encomiendaMapper.encomiendasxRepartidor(encomienda);
	}

	@Override
	public List<Encomienda> listarTrackingEncomiendas(Encomienda encomienda) {
		return encomiendaMapper.listarTrackingEncomiendas(encomienda);
	}
	@Override
	public Encomienda obtenerDatosEncomienda(Integer idEncomienda)
			throws Exception {
		return encomiendaMapper.obtenerDatosEncomienda(idEncomienda);
	}

	@Override
	public List<Encomienda> listarEncomiendasInventario(Encomienda encomienda) {
		return encomiendaMapper.listarEncomiendasInventario(encomienda);
	}

	@Override
	public void actualizarEstadoDevueltoEncomienda(Encomienda encomienda)
			throws Exception {
		encomiendaMapper.actualizarEstadoDevueltoEncomienda(encomienda);
	}

	@Override
	public void actualizarTipoEntregaEncomienda(Encomienda encomienda)
			throws Exception {
		encomiendaMapper.actualizarTipoEntregaEncomienda(encomienda);
	}

	@Override
	public List<Encomienda> listarEncomiendaSubManifiesto(
			Programacion programacion) throws Exception  {
		return encomiendaMapper.listarEncomiendaSubManifiesto(programacion);
	}

	@Override
	public void actualizarSubManEncomienda(Integer idSubmanifiesto,
			Integer idEncomienda) throws Exception {
		encomiendaMapper.actualizarSubManEncomienda(idSubmanifiesto, idEncomienda);
	}

	@Override
	public void actualizarObsReparto(String obsReparto, Integer idEncomienda)
			throws Exception {
		encomiendaMapper.actualizarObsReparto(obsReparto, idEncomienda);
	}

	@Override
	public List<Encomienda> rptEncomiendasContraEntrega(Encomienda filter)
			throws Exception {
		
		return encomiendaMapper.rptEncomiendasContraEntrega(filter);
	}

	@Override
	public List<Encomienda> listaEncomiendasxOficinas(Encomienda filter)
			throws Exception {
		return encomiendaMapper.listaEncomiendasxOficinas(filter);
	}

	@Override
	public List<Encomienda> listaEncomiendasxOficinasxPersona(Encomienda filter)
			throws Exception {
		
		return encomiendaMapper.listaEncomiendasxOficinasxPersona(filter);
	}

}

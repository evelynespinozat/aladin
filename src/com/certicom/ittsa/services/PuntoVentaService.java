package com.certicom.ittsa.services;

import java.util.List;

import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.mapper.PuntoVentaMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class PuntoVentaService implements PuntoVentaMapper{
	
	PuntoVentaMapper puntoVentaMapper = (PuntoVentaMapper)ServiceFinder.findBean("puntoVentaMapper");

	@Override
	public List<PuntoVenta> findAll() throws Exception {
		return puntoVentaMapper.findAll();
	}

	@Override
	public PuntoVenta findById(Integer idPuntoVenta) throws Exception {
		return puntoVentaMapper.findById(idPuntoVenta);
	}

	@Override
	public void eliminarPuntoVenta(Integer idPuntoVenta) throws Exception {

		puntoVentaMapper.eliminarPuntoVenta(idPuntoVenta);
	}

	@Override
	public void crearPuntoVenta(PuntoVenta puntoVenta) throws Exception {
		puntoVentaMapper.crearPuntoVenta(puntoVenta);
	}

	@Override
	public void actualizarPuntoVenta(PuntoVenta puntoVenta) throws Exception {
		puntoVentaMapper.actualizarPuntoVenta(puntoVenta);
		
	}

	@Override
	public List<PuntoVenta> listaPuntoVenta() throws Exception {
		return puntoVentaMapper.listaPuntoVenta();
	}

	@Override
	public List<PuntoVenta> getPventasxOficina(Integer idoficina) throws Exception{
		return puntoVentaMapper.getPventasxOficina(idoficina);
	}

	@Override
	public PuntoVenta obtenerPuntoVenta(Integer idPuntoVenta) throws Exception{
		return puntoVentaMapper.obtenerPuntoVenta(idPuntoVenta);
	}

	@Override
	public void actualizarSeriePuntoVenta(PuntoVenta puntoVenta)
			throws Exception {
		puntoVentaMapper.actualizarSeriePuntoVenta(puntoVenta);
	}

	@Override
	public void actualizarUltimaBoletaPuntoVenta(PuntoVenta puntoVenta)
			throws Exception {
		puntoVentaMapper.actualizarUltimaBoletaPuntoVenta(puntoVenta);
 	}

	@Override
	public void actualizarUltimaFacturaPuntoVenta(PuntoVenta puntoVenta)
			throws Exception {
		puntoVentaMapper.actualizarUltimaFacturaPuntoVenta(puntoVenta); 
	}

	@Override
	public void actualizarUltimoBoleto(Integer idPuntoVenta,
			Integer ultimoBoleto) throws Exception {
		puntoVentaMapper.actualizarUltimoBoleto(idPuntoVenta, ultimoBoleto);
	}

	@Override
	public void actualizarUltimaGuiaRemisionPuntoVenta(PuntoVenta puntoVenta)
			throws Exception {
		puntoVentaMapper.actualizarUltimaGuiaRemisionPuntoVenta(puntoVenta);
		
	}

	
	@Override
	public void actualizarUltimaReserva(Integer idPuntoVenta,
			Integer ultimareserva) throws Exception {
		puntoVentaMapper.actualizarUltimaReserva(idPuntoVenta, ultimareserva);
		
	}

	@Override
	public void actualizarUltimaNotaCobranzaPuntoVenta(PuntoVenta puntoVenta) throws Exception {
		// TODO Auto-generated method stub
		puntoVentaMapper.actualizarUltimaNotaCobranzaPuntoVenta(puntoVenta);
	}

	@Override
	public void actualizarUltimaBoleta(Integer idPuntoVenta,
			Integer ultimaboleta) throws Exception {
		puntoVentaMapper.actualizarUltimaBoleta(idPuntoVenta, ultimaboleta);
		
	}

	@Override
	public PuntoVenta getPuntoVentaxUsuario(Integer idusuario) throws Exception {
		// TODO Auto-generated method stub
		return puntoVentaMapper.getPuntoVentaxUsuario(idusuario);
	}

	@Override
	public void actualizarUltimaPrepagadoPuntoVenta(PuntoVenta puntoVenta)
			throws Exception {
		puntoVentaMapper.actualizarUltimaPrepagadoPuntoVenta(puntoVenta);
		
	}

	public List<PuntoVenta> buscarIP(PuntoVenta puntoVenta) {
		// TODO Auto-generated method stub
		
		return puntoVentaMapper.getIP(puntoVenta.getNumeroIP());
		
	}

	@Override
	public List<PuntoVenta> getIP(String numeroIP) {
		return this.puntoVentaMapper.getIP(numeroIP);

	}

	@Override
	public PuntoVenta getImpresora(String nombreImpresora) {
		return this.puntoVentaMapper.getImpresora(nombreImpresora);
	}

	@Override
	public PuntoVenta validarIp(PuntoVenta puntoventa)
			throws Exception {
		return puntoVentaMapper.validarIp(puntoventa);
	}
	
	@Override
	public PuntoVenta validarImpresora(PuntoVenta puntoventa)
			throws Exception {
		return puntoVentaMapper.validarImpresora(puntoventa);
	}

	@Override
	public List<PuntoVenta> buscarIPDiferentes(Integer IdPuntoVenta,String NumeroIP) {
		
		return this.puntoVentaMapper.buscarIPDiferentes(IdPuntoVenta, NumeroIP);
	}

	@Override
	public List<PuntoVenta> buscarImpresoraDiferentes(Integer IdPuntoVenta,
			String nombreImpresora) {
		return this.puntoVentaMapper.buscarImpresoraDiferentes(IdPuntoVenta, nombreImpresora);
	}

	@Override
	public List<PuntoVenta> buscarImpresora(String nombreImpresora) {
		return this.puntoVentaMapper.buscarImpresora(nombreImpresora);
	}

	@Override
	public int obtenerAlmacen(Integer idOficina) throws Exception {
		// TODO Auto-generated method stub
		return this.puntoVentaMapper.obtenerAlmacen(idOficina);
	}
	
	
	@Override
	public int obtenerComponentesAsociados(Integer IdPuntoVenta) throws Exception {
		// TODO Auto-generated method stub
		return this.puntoVentaMapper.obtenerComponentesAsociados(IdPuntoVenta);
	}
	
	//inicio piscoya
	@Override
	public List<PuntoVenta> obtenerPuntoVentaxOficina(Integer IdOficina) throws Exception {
		return this.puntoVentaMapper.obtenerPuntoVentaxOficina(IdOficina);
	}	
	//fin piscoya
	
}

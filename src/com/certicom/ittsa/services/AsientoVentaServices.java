package com.certicom.ittsa.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.VentaDetalle;
import com.certicom.ittsa.mapper.AsientoVentaMapper;
import com.pe.certicom.ittsa.commons.BoletoFilter;
import com.pe.certicom.ittsa.commons.ConstanteVentas;
import com.pe.certicom.ittsa.commons.ServiceFinder;

@Service("asientoVentaService")
public class AsientoVentaServices implements AsientoVentaMapper {
	AsientoVentaMapper asientoVentaMapper = (AsientoVentaMapper) ServiceFinder.findBean("asientoVentaMapper");

	@Override
	public List<AsientoVenta> findAll() throws Exception {
		return asientoVentaMapper.findAll();
	}

	@Override
	public AsientoVenta findById(Integer idasientoventa) throws Exception {
		return asientoVentaMapper.findById(idasientoventa);
	}


	@Override
	public void actualizarVentaAsiento(AsientoVenta asientoVenta)
			throws Exception {
		//System.out.println("----- INGRESO A ACTUALIZAR VENTA ASIENTO");
		asientoVentaMapper.actualizarVentaAsiento(asientoVenta);

	}

	@Override
	public void editarVentaAsiento(AsientoVenta asientoVenta) throws Exception {
		asientoVentaMapper.editarVentaAsiento(asientoVenta);
	}

	@Override
	public AsientoVenta findAsientoByAsientoAndProgramacion(Integer IdAsiento,
			String numero, Integer IdProgramacion) throws Exception {
		return asientoVentaMapper.findAsientoByAsientoAndProgramacion(
				IdAsiento, numero, IdProgramacion);
	}

	@Override
	public AsientoVenta findEstadoAsientoByAsientoAndProgramacion(
			AsientoVenta asientoVenta) throws Exception {
		return asientoVentaMapper.findEstadoAsientoByAsientoAndProgramacion(asientoVenta);
	}

	@Override
	public void liberarAsiento(Integer idAsiento, Integer idProgramacion,
			String estado) throws Exception {
		asientoVentaMapper.liberarAsiento(idAsiento, idProgramacion, estado);
	}


	@Override
	public List<AsientoVenta> obtenerDeliverys(BoletoFilter filter) throws Exception {
		return asientoVentaMapper.obtenerDeliverys(filter);
	}

	@Override
	public List<AsientoVenta> listarManifiestoxProg(Integer idProgramacion)
			throws Exception {
		return asientoVentaMapper.listarManifiestoxProg(idProgramacion);
	}

	@Override
	public AsientoVenta buscarPasajeroxDni(Integer idProgramacion,
			String documentoPersona) throws Exception {
		return asientoVentaMapper.buscarPasajeroxDni(idProgramacion, documentoPersona);
	}

	@Override
	public void actualizarAbordoAsVenta(AsientoVenta asientoVenta)
			throws Exception {
		asientoVentaMapper.actualizarAbordoAsVenta(asientoVenta);
	}

	@Override
	public List<AsientoVenta> obtenerPrepagadosxFecha(BoletoFilter boletoFilter) throws Exception {
		return asientoVentaMapper.obtenerPrepagadosxFecha(boletoFilter);
	}

	@Override
	public List<AsientoVenta> obtenerReservasxEstado(Integer idOrigen,
			Integer idDestino, Integer idOficina, Date fecini, Date fecfin) throws Exception {
		return asientoVentaMapper.obtenerReservasxEstado(idOrigen, idDestino, idOficina, fecini, fecfin);
	}

	@Override
	public void liberarVentaAsiento(AsientoVenta asientoVenta) throws Exception {
		asientoVentaMapper.liberarVentaAsiento(asientoVenta);
		
	}

	@Override
	public List<AsientoVenta> verificandoVentaAsiento(Integer idProgramacion)
			throws Exception {
		return asientoVentaMapper.verificandoVentaAsiento(idProgramacion);
	}

	@Override
	public Integer countbyprogramacionAndEstado(Integer IdProgramacion,
			String estado, Boolean visibles) throws Exception {
		return asientoVentaMapper.countbyprogramacionAndEstado(IdProgramacion, estado, visibles);
	}

	@Override
	public Integer countbyprogramacionAndEstadoXPiso(Integer IdProgramacion,
			String estado, Integer piso, Boolean p_visible) throws Exception {
		return asientoVentaMapper.countbyprogramacionAndEstadoXPiso(IdProgramacion, estado, piso, p_visible);
	}

	@Override
	public void aumentarCapacidad(Integer idasientoventa) throws Exception {
		asientoVentaMapper.aumentarCapacidad(idasientoventa);
	}

	@Override
	public Integer countCapacidadVerdadera(Integer IdProgramacion,
			Boolean p_visible) throws Exception {

		return asientoVentaMapper.countCapacidadVerdadera(IdProgramacion, p_visible);
	}

	@Override
	public AsientoVenta obtenerAsientoxProgramacionNumero(
			Integer idProgramacion, String numero) throws Exception {
		return asientoVentaMapper.obtenerAsientoxProgramacionNumero(idProgramacion, numero);
	}

	@Override
	public void actualizarAsientoxProgramacionLiberar(AsientoVenta asientoVenta) throws Exception {
		asientoVentaMapper.actualizarAsientoxProgramacionLiberar(asientoVenta);
	}

	@Override
	public String updateAssociatedSeats(AsientoVenta asientoVenta)
			throws Exception {
		return asientoVentaMapper.updateAssociatedSeats(asientoVenta);
	}

	public AsientoVenta findInfoAsientoVenta(Integer idProgramacion,
			Integer oficinaId) {
		return asientoVentaMapper.findInfoAsientoVenta(idProgramacion,oficinaId);
	}

	@Override
	public void actualizarEstadoDelivery(AsientoVenta asientoVenta) throws Exception{
		asientoVentaMapper.actualizarEstadoDelivery(asientoVenta);
	}

	@Override
	public void actualizarEstadoImpreso(AsientoVenta asientoVenta)
			throws Exception {
		asientoVentaMapper.actualizarEstadoImpreso(asientoVenta);
		
	}

	@Override
	public AsientoVenta findPasajeroByProgramacion(Integer idProgramacion,
			String documentoPersona) throws Exception {
		return this.asientoVentaMapper.findPasajeroByProgramacion(idProgramacion, documentoPersona);
	}

	@Override
	public List<Integer> listarAsientosDisponibles(Integer piso, Integer idProgramacion)
			throws Exception {
		// TODO Auto-generated method stub
		return this.asientoVentaMapper.listarAsientosDisponibles(piso, idProgramacion);
	}

	@Override
	public List<AsientoVenta> listarManifiestoxProgNoEmbarcados(
			Integer idProgramacion) throws Exception {
		// TODO Auto-generated method stub
		return this.asientoVentaMapper.listarManifiestoxProgNoEmbarcados(idProgramacion);
	}

	@Override
	public List<AsientoVenta> listarTotalAsientosDisponibles(Integer piso,
			Integer idProgramacion) throws Exception {
		return asientoVentaMapper.listarTotalAsientosDisponibles(piso,idProgramacion);
	}

	@Override
	public void actualizarAsientoxProgramacionIdLiberar(Integer programacionId)
			throws Exception {
		asientoVentaMapper.actualizarAsientoxProgramacionIdLiberar(programacionId);
	}

	@Override
	public List<AsientoVenta> obtenerAsientoxProgramacion(Integer idProgramacion)
			throws Exception {
		// TODO Auto-generated method stub
		return asientoVentaMapper.obtenerAsientoxProgramacion(idProgramacion);
	}

	@Override
	public void nuevosAsientoVentaPorAmpliacion(AsientoVenta asientoVenta)
			throws Exception {
		asientoVentaMapper.nuevosAsientoVentaPorAmpliacion(asientoVenta);
		
	}

	@Override
	public void actualizarAsientoId(Integer idasiento, Integer idasientoVenta)
			throws Exception {
		asientoVentaMapper.actualizarAsientoId(idasiento, idasientoVenta);
		
	}

	@Override
	public Integer countEmbarcadosNoEmbarcados(Integer IdProgramacion,
			Boolean abordo) throws Exception {
		return asientoVentaMapper.countEmbarcadosNoEmbarcados(IdProgramacion, abordo);
	}

	@Override
	public Integer countLibres(Integer IdProgramacion) throws Exception {
		
		return asientoVentaMapper.countLibres(IdProgramacion);
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class,isolation=Isolation.SERIALIZABLE)
	//readOnly = false, propagation = Propagation.REQUIRED
	public Map<String,Object>  consultarVenta(Asiento as, Programacion programacionSelec, Usuario user)
	{
		System.out.println("consultando :"+programacionSelec.getIdProgramacion());
		Map<String,Object> mapa = new HashMap<>();
		AsientoVenta asv = null;
		String estado = null;
		try {
			asv = this.findEstadoAsientoByAsientoAndProgramacion(as.getAsientoVenta());
			
			asv.setfSalida(programacionSelec.getfSalida());
			asv.setAsiento(as);

			if (asv.getEstado().equals("LIBRE")) 
			{
				asv.setIdusuarioventa(user.getIdusuario());
				asv.setIdAsiento(as.getIdAsiento());
				asv.setNumero(as.getNumero());
				asv.setPiso(as.getPiso());
				asv.setPersona(new Persona());
				asv.setIdProgramacion(programacionSelec.getIdProgramacion());
				asv.setRucBoolean(Boolean.FALSE);

				// obteniendo el precio del asiento:
				if (as.getPiso().equals(1)) {
					asv.setMonto(programacionSelec.getServicio().getPrecio1());
					asv.setCostoRealAsiento(programacionSelec.getServicio().getPrecio1());
					//this.precioAsiento = this.asientoVenta.getMonto();
					//this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio1();
					
				} else {
					asv.setMonto(programacionSelec.getServicio().getPrecio2());
					asv.setCostoRealAsiento(programacionSelec.getServicio().getPrecio2());
					//this.precioAsiento = this.asientoVenta.getMonto();
					//this.precioAsientoPromocion = this.programacionSelec.getServicio().getPrecio2();
				}
				
				asv.setFechaventa(null);
				asv.setEstado(ConstanteVentas.ESTADO_PROCESO);
				asv.setSexo("-");
			
				this.actualizarVentaAsiento(asv);
				
				asv.setIdProgramacion(programacionSelec.getIdProgramacion());
				
				if (programacionSelec.getDesRutaCompartida().equals("SI")) {
					System.out.println("entro en ruta compartida si");
					this.updateAssociatedSeats(asv); //PROCEDIMIENTO ALMACENADO

				}else if(programacionSelec.getIntCorreEnlace().intValue() > 0){
					this.updateAssociatedSeats(asv);//PROCEDIMIENTO ALMACENADO

				}
				
				estado = "PROCESADO";
				
				
				
			}else{
				estado = "NOPROCESADO";
				
			}

			mapa.put("asientoVenta", asv);
			mapa.put("estado", estado);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return mapa;
	}
	
	
	
	

//	@Override
//	public List<AsientoVenta> obtenerDeliverys(Date fecha) throws Exception {
//		// TODO Auto-generated method stub
//		return asientoVentaMapper.obtenerDeliverys(fecha);
//	}

	@Override
	public Integer transac_block_seat(AsientoVenta as)
			throws Exception {
		 return asientoVentaMapper.transac_block_seat(as);
	}
	
	@Override
	public Integer transac_block_seat_before(Integer IdProgramacion, String numero, Integer idAsiento)
			throws Exception {
		 return asientoVentaMapper.transac_block_seat_before(IdProgramacion, numero, idAsiento);
	}

	@Override
	public List<AsientoVenta> obtenerPrepagadosxDNI(String dniPasajero)
			throws Exception {
		// TODO Auto-generated method stub
		 return asientoVentaMapper.obtenerPrepagadosxDNI(dniPasajero);
	}

	@Override
	public List<VentaDetalle> obtenerVentaDetalle(Date fInicio, Date fFin,
			String idPuntoVenta)
			throws Exception {
		// TODO Auto-generated method stub
		    return asientoVentaMapper.obtenerVentaDetalle(fInicio, fFin, idPuntoVenta);
		
	}

	@Override
	public void actualizarCambioDatosPasajero(String documentoPersona,
			String documentoEmpresa, String sexo, Integer idasientoventa)
			throws Exception {
		asientoVentaMapper.actualizarCambioDatosPasajero(documentoPersona, documentoEmpresa, sexo, idasientoventa);
	}

	@Override
	public void replicarAsientoVenta(Integer idasientoventa) throws Exception {
		asientoVentaMapper.replicarAsientoVenta(idasientoventa);
		
	}

	@Override
	public AsientoVenta findAsientoVentaFAById(Integer idasientoventa)
			throws Exception {
		return asientoVentaMapper.findAsientoVentaFAById(idasientoventa);
	}
	
	//INICIO PISCOYA	
	/*
	@Override
	public List<AsientoVenta> obtenerPrepagadosxDNI(String dniPasajero) throws Exception {
		return asientoVentaMapper.obtenerPrepagadosxDNI(dniPasajero);
	}
	
	@Override
	public List<VentaDetalle> obtenerVentaDetalle(Date fInicio,Date fFin,String idOficina,String idPuntoVenta, String idAgencia) throws Exception {
	
		return asientoVentaMapper.obtenerVentaDetalle(fInicio, fFin, idOficina, idPuntoVenta, idAgencia);
		
		
	}
	
	*/
	//FIN PISCOYA

}

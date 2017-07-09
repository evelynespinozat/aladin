package com.certicom.ittsa.services;

import java.util.Date;
import java.util.List;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Transbordo;
import com.certicom.ittsa.mapper.ProgramacionMapper;
import com.pe.certicom.ittsa.commons.ServiceFinder;

public class ProgramacionService implements ProgramacionMapper{
	
	ProgramacionMapper programacionMapper = (ProgramacionMapper)ServiceFinder.findBean("programacionMapper");

	@Override
	public List<Programacion> findAll() throws Exception {

		return programacionMapper.findAll();
	}

	@Override
	public Programacion findById(Integer idProgramacion) throws Exception {

		return programacionMapper.findById(idProgramacion);
	}

	@Override
	public void eliminarProgramacion(Integer idProgramacion) throws Exception {

		programacionMapper.eliminarProgramacion(idProgramacion);
	}

	@Override
	public String crearProgramacion(Programacion programacion) throws Exception {
		return programacionMapper.crearProgramacion(programacion);
	}

	@Override
	public void actualizarProgramacion(Programacion programacion) throws Exception {
		programacionMapper.actualizarProgramacion(programacion);
		
	}

	@Override
	public List<Agencia> listaProgramacionActivas() throws Exception {

		return programacionMapper.listaProgramacionActivas();
	}
	
	/**added by juan*/
	@Override
	public List<Programacion> listarProgFrecuente(Programacion filter) {
		return programacionMapper.listarProgFrecuente(filter);
	}
	
	@Override
	public void actualizarBusProgramacionAsig(Integer idBus, Integer idProgramacion)
			throws Exception {
		programacionMapper.actualizarBusProgramacionAsig(idBus, idProgramacion); 
	}
	
	@Override
	public void actualizarBusProgramacionNoAsig(Integer idProgramacion)
			throws Exception {
		programacionMapper.actualizarBusProgramacionNoAsig(idProgramacion); 
	}

	@Override
	public List<Programacion> obtenerHoraxServicio(Transbordo transbordo) throws Exception {
		return programacionMapper.obtenerHoraxServicio(transbordo);
	}

	@Override
	public int programacionxServicio(Integer idServicio) throws Exception {
		return programacionMapper.programacionxServicio(idServicio);
	}

	@Override
	public List<Programacion> listarProgDemanda(Programacion filter)
			throws Exception {
		return programacionMapper.listarProgDemanda(filter);
	}

	@Override
	public List<Programacion> findByOrigenDestinoAndFsalida(
			Integer origen, Integer destino, String fSalida)
			throws Exception {
		return programacionMapper.findByOrigenDestinoAndFsalida(origen, destino, fSalida);
	}
	
	@Override
	public List<Programacion> listarProgrPlantilla(Programacion programacion)
			throws Exception {
		return programacionMapper.listarProgrPlantilla(programacion);
	}

	@Override
	public void actualizarEstATBordo(Integer value, Integer IdProgramacion)
			throws Exception {
		programacionMapper.actualizarEstATBordo(value, IdProgramacion);
	}

	@Override
	public List<Programacion> findProgServCumplidosxBus(Date fechaIni,
			Date fechaFin, Integer idBus) throws Exception {
		return programacionMapper.findProgServCumplidosxBus(fechaIni, fechaFin, idBus);
	}
	
	@Override
	public List<Programacion> findProgServCumplidosFlota(Date fechaIni,
			Date fechaFin) throws Exception {
		return programacionMapper.findProgServCumplidosFlota(fechaIni, fechaFin); 
	}

	@Override
	public List<Programacion> listProgTripulacion(Date fechaProg,
			Integer idOrigen, Integer idDestino) throws Exception {
		return programacionMapper.listProgTripulacion(fechaProg, idOrigen, idDestino);
	}

	@Override
	public void actualizarTerramoza1(Integer IdProgramacion, Integer IdTerramoza)
			throws Exception {
		programacionMapper.actualizarTerramoza1(IdProgramacion, IdTerramoza);
	}

	@Override
	public void actualizarTerramoza2(Integer IdProgramacion,
			Integer IdTerramoza2) throws Exception {
		programacionMapper.actualizarTerramoza2(IdProgramacion, IdTerramoza2);
	}

	@Override
	public List<Programacion> findProgByFecha_orig_destKilometraje(Integer idBus)
			throws Exception {
		return programacionMapper.findProgByFecha_orig_destKilometraje(idBus);
	}

	@Override
	public List<Programacion> findProgByFecha_orig_dest(Date fechaProg,
			Integer idOrigen, Integer idDestino, Integer idCatServicio)
			throws Exception {
		return programacionMapper.findProgByFecha_orig_dest(fechaProg, idOrigen, idDestino, idCatServicio);
	}

	@Override
	public List<Programacion> findProgByFecha_orig_dest_Asig(Date fechaProg,
			Integer idOrigen, Integer idDestino, Integer idCatServicio)
			throws Exception {
		return programacionMapper.findProgByFecha_orig_dest_Asig(fechaProg, idOrigen, idDestino, idCatServicio);
	}

	@Override
	public List<Programacion> listProgDesembarque(Date fechaProg,
			Integer idOrigen, Integer idDestino) throws Exception {
		return programacionMapper.listProgDesembarque(fechaProg, idOrigen, idDestino);
	}

	@Override
	public void actualizarDesembarque(Integer idProgramacion, Date factual)
			throws Exception {
		programacionMapper.actualizarDesembarque(idProgramacion, factual);
	}

	@Override
	public List<Programacion> findProgByFecha_IdPiloto(Date fechaIni,
			Date fechaFin, Integer idPiloto) throws Exception {
		return programacionMapper.findProgByFecha_IdPiloto(fechaIni, fechaFin, idPiloto);
	}

	@Override
	public List<Programacion> findProgByFecha_IdTerramoza(Date fechaIni,
			Date fechaFin, Integer idTerramoza) throws Exception {
		return programacionMapper.findProgByFecha_IdTerramoza(fechaIni, fechaFin, idTerramoza);
	}

	@Override
	public List<Programacion> findProg_AsigByFecha_Bus(Date fechaIni,
			Date fechaFin, Integer idBus) throws Exception {
		return programacionMapper.findProg_AsigByFecha_Bus(fechaIni, fechaFin, idBus);
	}

	@Override
	public void actualizarEstKilometraje(Integer idProgramacion) {
		programacionMapper.actualizarEstKilometraje(idProgramacion);
	}

	@Override
	public List<Programacion> listProgrCostxServicio(Date fecIni, Date fecFin) {
		return programacionMapper.listProgrCostxServicio(fecIni, fecFin);
	}

	@Override
	public List<Programacion> findProgByFecha_orig_destKilometrajeConKm(
			Integer idBus) throws Exception {
		return programacionMapper.findProgByFecha_orig_destKilometrajeConKm(idBus);
	}
	
	@Override
	public List<Programacion> buscarProxServicioPiloto(Programacion programacion)
			throws Exception {
		return programacionMapper.buscarProxServicioPiloto(programacion);
	}

	@Override
	public List<Programacion> buscarProxServicioTerramoza(
			Programacion programacion) throws Exception {
		return programacionMapper.buscarProxServicioTerramoza(programacion);
	}

	@Override
	public List<Programacion> obtenerProgramacionesHijos(Servicio servicio) throws Exception {
		return programacionMapper.obtenerProgramacionesHijos(servicio);
	}

	@Override
	public List<Programacion> listarBusesProgDisponibles(Date fSalida,
			Integer origen, Integer destino) throws Exception {
		return programacionMapper.listarBusesProgDisponibles(fSalida, origen, destino);
	}

	@Override
	public List<Programacion> obtenerProgramacionesPadre(Servicio servicio)
			throws Exception {
		return programacionMapper.obtenerProgramacionesPadre(servicio);
	}

	@Override
	public void actualizarBusProgramacionConvCompartida(Integer idBus,
			Integer idProgramacion, Integer intCorre) throws Exception {
		programacionMapper.actualizarBusProgramacionConvCompartida(idBus, idProgramacion, intCorre);
	}

	@Override
	public void actualizarProgramacionPadreCorrelativo(Integer idProgramacion,
			Integer intCorre) throws Exception {
		programacionMapper.actualizarProgramacionPadreCorrelativo(idProgramacion, intCorre);
	}

	@Override
	public List<Programacion> findByIntCorreEnlace(Integer intCorreEnlace,
			Integer idProgramacion) throws Exception {
		return programacionMapper.findByIntCorreEnlace(intCorreEnlace, idProgramacion);
	}

	@Override
	public List<Programacion> listaFlotaAsiganadasProgramacion(
			Integer idOrigen, Integer idDestino) throws Exception {
		return programacionMapper.listaFlotaAsiganadasProgramacion(idOrigen, idDestino);
	}

	@Override
	public List<Programacion> listarProgSubManifiesto(Programacion filter)
			throws Exception {
		return programacionMapper.listarProgSubManifiesto(filter);
	}

	@Override
	public List<Programacion> buscarPorOrigenDestinoAndFsalida(Integer origen,
			Integer destino, String fSalida) throws Exception {
		return programacionMapper.buscarPorOrigenDestinoAndFsalida(origen, destino, fSalida);
	}

	@Override
	public List<Programacion> listarProgxSalida(Integer origen, Integer destino, Date fecha)
			throws Exception {
		return programacionMapper.listarProgxSalida(origen, destino, fecha);
	}

	@Override
	public void actualizarServicio(Integer idProgramacion, Integer idServicio,
			Integer idBus) throws Exception {
		programacionMapper.actualizarServicio(idProgramacion, idServicio, idBus);
	}

	

	@Override
	public Programacion programacionRutaCompartida(Date fSalida,
			Integer IdServicio, Integer IntCorreEnlace) throws Exception {
		return programacionMapper.programacionRutaCompartida(fSalida, IdServicio, IntCorreEnlace);
	}

	@Override
	public void actualizarProgramacionCorrelativo(Integer IntCorreEnlace,
			Integer idProgramacion) throws Exception {
		programacionMapper.actualizarProgramacionCorrelativo(IntCorreEnlace, idProgramacion);
	}

	@Override
	public List<Programacion> obtenerProgramacionesHijoxIdProgram(
			Integer idProgramacion) throws Exception {
		return programacionMapper.obtenerProgramacionesHijoxIdProgram(idProgramacion);
	}

	@Override
	public void actualizarHojaRuta(String hojaruta_mtc, Integer idProgramacion)
			throws Exception {
		programacionMapper.actualizarHojaRuta(hojaruta_mtc, idProgramacion);
	}

	@Override
	public List<Programacion> findByOrigenDestinoAndFsalidaAllDay(
			Integer origen, Integer destino, String fSalida) throws Exception {
		return programacionMapper.findByOrigenDestinoAndFsalidaAllDay(origen, destino, fSalida);
	}

	@Override
	public Programacion getStateSeats(Integer idProgramacion)
			throws Exception {
		return programacionMapper.getStateSeats(idProgramacion);
	}

	@Override
	public List<Programacion> obtenerProgramacionesHijoxIdProgramyDescripcion(
			Integer idProgramacion, Date fsalida) throws Exception {
		// TODO Auto-generated method stub
		return programacionMapper.obtenerProgramacionesHijoxIdProgramyDescripcion(idProgramacion, fsalida);
	}

	@Override
	public void actualizarAmpliacionBusProgramacion(Boolean ampliado,
			Integer idProgramacion) throws Exception {
		programacionMapper.actualizarAmpliacionBusProgramacion(ampliado, idProgramacion);
	}

	@Override
	public List<Programacion> findByCorreEnlace(Integer intCorreEnlace)
			throws Exception {
		// TODO Auto-generated method stub
		return programacionMapper.findByCorreEnlace(intCorreEnlace);
	}

	@Override
	public List<Programacion> findProgServCumplidosTodos(Date fechaIni,
			Date fechaFin) throws Exception {
		return programacionMapper.findProgServCumplidosTodos(fechaIni, fechaFin);
	}
	
	
	

	@Override
	public List<Programacion> trujilloLimaSalida(Date fSalida) throws Exception {
		return programacionMapper.trujilloLimaSalida(fSalida);
	}

	@Override
	public List<Programacion> trujilloLimaRetorno(Date fSalida) throws Exception {
		return programacionMapper.trujilloLimaRetorno(fSalida);
	}

	@Override
	public List<Programacion> trujilloNorteSalida(Date fSalida) throws Exception {
		return programacionMapper.trujilloNorteSalida(fSalida);
	}

	@Override
	public List<Programacion> trujilloNorteRetorno(Date fSalida) throws Exception {
		return programacionMapper.trujilloNorteRetorno(fSalida);
	}

	@Override
	public List<Programacion> findProgByFecha_orig_destVentas(Date fechaProg,
			Integer idOrigen, Integer idDestino, Integer idCatServicio)
			throws Exception {
		// TODO Auto-generated method stub
		return programacionMapper.findProgByFecha_orig_destVentas(fechaProg, idOrigen, idDestino, idCatServicio);
	}

	

}

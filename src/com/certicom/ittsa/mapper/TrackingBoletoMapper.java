package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.TrackingBoleto;

public interface TrackingBoletoMapper {

	@Select("select * from t_trackingboleto where  nrodocumento =#{p_nrodocumento} order by fregistro DESC")
	public List<TrackingBoleto> findByNroSerieNumeroPersona(
			@Param("p_nrodocumento") String numeroDoc) throws Exception;

	//@Select("select a.* from t_trackingboleto a where a.intsecuencia = (select distinct(b.intsecuencia) from t_trackingboleto b where b.serieBoleto =#{p_serieBoleto}  and b.numeroBoleto=#{p_numeroBoleto})")
	@Select("select a.* from t_trackingboleto a where a.serieBoleto =#{p_serieBoleto}  and a.numeroBoleto=#{p_numeroBoleto} order by fregistro ASC")
	public List<TrackingBoleto> findBySerieNumeroBoleto(
			@Param("p_serieBoleto") String serie,
			@Param("p_numeroBoleto") String numeroBoleto) throws Exception;

	@Insert("insert into t_trackingboleto (serieboleto,numeroboleto,fregistro,idusuario,idasiento,idasientoventa,estado,idprogramacion,idservicio,nrodocumento,nrodocempresa,intsecuencia,prepagado,fechaCaducidad,monto,montoReal,tipoInh,motivoInh,serieRelacionado,numeroRelacionado,dniNuevoPasajero,puntoVentaId) values (#{serieBoleto},#{numeroBoleto},#{fregistro},#{idusuario},#{idasiento},#{idasientoventa},#{estado},#{idprogramacion},#{idservicio},#{nrodocumento},#{nrodocempresa},#{intsecuencia},#{prepagado},#{fechaCaducidad},#{monto},#{montoReal},#{tipoInh},#{motivoInh},#{serieRelacionado},#{numeroRelacionado},#{dniNuevoPasajero},#{puntoVentaId})")
	public void creartrack(TrackingBoleto tracking) throws Exception;

	@Select("select a.* from t_trackingboleto a where a.IdTrackingBoleto = (select max(b.IdTrackingBoleto) from t_trackingboleto b where b.idservicio=#{p_idservicio} and b.idprogramacion=#{p_idprogramacion} and b.idasientoventa=#{p_idasientoventa} and b.nrodocumento =#{p_nrodocumento})")
	public TrackingBoleto findByServicioProgramacionNumeroPersona(
			@Param("p_idservicio") Integer idservicio,
			@Param("p_idprogramacion") Integer idprogramacion,
			@Param("p_idasientoventa") Integer idasientoventa,
			@Param("p_nrodocumento") String nrodocumento) throws Exception;

	@Select("select top 10 * from dbo.t_trackingboleto order by fregistro DESC")
	public List<TrackingBoleto> findByPersona(
			@Param("p_nrodocumento") String nrodocumento) throws Exception;

	@Update("update t_trackingboleto set estado = #{estado}, fregistro=#{fregistro}  where  IdTrackingBoleto = #{IdTrackingBoleto}")
	@Options(flushCache = true, useCache = true)
	public void actualizarTracking(TrackingBoleto tracking) throws Exception;

	@Select("select * from t_trackingboleto where  serieBoleto =#{p_serieBoleto}  and numeroBoleto=#{p_numeroBoleto} and estado = 'VENTA'")
	public TrackingBoleto searchBySerieNumeroBoleto(
			@Param("p_serieBoleto") String serie,
			@Param("p_numeroBoleto") String numeroBoleto) throws Exception;

	@Select("select top 1 * from t_trackingboleto where  idasiento =#{p_idAsiento}  and idasientoventa=#{p_idasientoventa} order by IdTrackingBoleto desc ")
	public TrackingBoleto searchByAsientoVenta(
			@Param("p_idasientoventa") Integer idasientoventa,
			@Param("p_idAsiento") Integer idAsiento) throws Exception;

	@Select("select t.fregistro, t.serieBoleto, t.numeroBoleto, (u.nombre + ' ' + u.apellido_paterno + ' ' + u.apellido_materno) as desUsuario, t.nrodocumento, v.numero as desNroAsiento, "
			+ "	   s.descripcion as desServicio, ao.Descripcion as desOrigen, ad.Descripcion as desDestino "
			+ " from (((((t_trackingboleto t inner join t_usuario u on t.idusuario = u.idusuario) "
			+ "						 left join t_asientoventa v on v.idasientoventa = t.idasientoventa) "
			+ "						 left join t_programacion p on p.IdProgramacion = v.IdProgramacion) "
			+ "						 left join t_servicio s on s.IdServicio = p.IdServicio) "
			+ "						 left join t_agencia ao on s.Origen = ao.Idagencia) "
			+ "						 left join t_agencia ad on s.Destino = ad.Idagencia "
			+ " where t.estado = 'ANULADO' AND t.fregistro >= #{fechaInicio} AND t.fregistro <= #{fechaFin} "
			+ " and u.idoficina = #{idOficina} ")
	public List<TrackingBoleto> getTicketsCanceled(
			@Param("idOficina") Integer idOficina,
			@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin) throws Exception;

	@Select("select a.* from t_trackingboleto a, t_asientoventa b "
			+ "where a.idasiento=b.IdAsiento and a.idasientoventa=b.idasientoventa"
			+ " and b.numero=#{numero} and a.estado=#{estadoReserva} "
			+ "and a.nrodocumento=#{documentoPersona} and a.idprogramacion=#{idProgramacion} and a.idservicio=#{idServicio} order by fregistro DESC")
	public List<TrackingBoleto> buscarReservado(@Param("numero") String numero,
			@Param("estadoReserva") String estadoReserva,
			@Param("documentoPersona") String documentoPersona,
			@Param("idProgramacion") Integer idProgramacion,
			@Param("idServicio") Integer idServicio) throws Exception;
	
	/*
	@Select("select * from dbo.t_trackingboleto where numeroBoleto =#{p_numeroBoleto} AND serieBoleto =#{p_serieBoleto} and estado = 'POSTERGADO' order by fregistro DESC")
	public List<TrackingBoleto> findBySerieNumeroBoletoPostergado(
			@Param("p_serieBoleto") String serie,
			@Param("p_numeroBoleto") String numeroBoleto) throws Exception;
	*/
	
	@Select("select * from dbo.t_trackingboleto where numeroBoleto =#{p_numeroBoleto} AND serieBoleto =#{p_serieBoleto}  order by fregistro DESC")
	public List<TrackingBoleto> findBySerieAndNumero(
			@Param("p_serieBoleto") String serie,
			@Param("p_numeroBoleto") String numeroBoleto) throws Exception;
	
	
	
	/*
	@Select("select * from dbo.t_trackingboleto where numeroBoleto =#{p_numeroBoleto} AND serieBoleto =#{p_serieBoleto} and estado = 'INHABILITADO' order by fregistro DESC")
	public List<TrackingBoleto> findBySerieNumeroBoletoInhabilitado(
			@Param("p_serieBoleto") String serie,
			@Param("p_numeroBoleto") String numeroBoleto) throws Exception;
	*/
	
	@Update("update t_trackingboleto set numeroRelacionado = #{p_numeroRelacionado}, serieRelacionado=#{p_serieRelacionado}  where  IdTrackingBoleto = #{p_IdTrackingBoleto}")
	@Options(flushCache = true, useCache = true)
	public void actualizarTrackingReferencia(@Param("p_numeroRelacionado") String numeroRelacionado,@Param("p_serieRelacionado") String serieRelacionado,@Param("p_IdTrackingBoleto") Integer IdTrackingBoleto) throws Exception;

}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.HistorialPasajero;

public interface HistorialPasajeroMapper {

	@Delete("delete from t_historialpasajero where IdHistorialPasajero = #{idHistorialPasajero}")
	@Options(flushCache = true)
	public void eliminarHistorialPasajero(
			@Param("idHistorialPasajero") Integer idHistorialPasajero)
			throws Exception;

//	@Insert("insert into t_historialpasajero (Dni, SerieBoleto, IdOficina, NumeroBoleto, FRegistro, nroRuc,asientoventaid,operacion,nroasiento,piso,idservicio,idprogramacion,monto,idpadre,puntoVentaId,fechaCaducidad,tipoEntrega,prepagado) values (#{dni},#{serieBoleto},#{idOficina},#{numeroBoleto},#{fRegistro}, #{nroRuc},#{asientoventaid},#{operacion},#{nroasiento},#{piso},#{idservicio},#{idprogramacion},#{monto},#{idpadre},#{puntoVentaId},#{fechaCaducidad},#{tipoEntrega},#{prepagado})")
	@Insert("insert into t_historialpasajero (Dni, SerieBoleto, IdOficina, NumeroBoleto, FRegistro, nroRuc,asientoventaid,operacion,nroasiento,piso,idservicio,idprogramacion,monto,idpadre,puntoVentaId,fechaCaducidad) values (#{dni},#{serieBoleto},#{idOficina},#{numeroBoleto},#{fRegistro}, #{nroRuc},#{asientoventaid},#{operacion},#{nroasiento},#{piso},#{idservicio},#{idprogramacion},#{monto},#{idpadre},#{puntoVentaId},#{fechaCaducidad})")
	public void crearHistorialPasajero(HistorialPasajero HistorialPasajero)
			throws Exception;

	@Select("select * from t_historialpasajero where idpadre is null and Dni = #{dni} order by FRegistro DESC ")
	public List<HistorialPasajero> getNumberTravel(@Param("dni") String dni)
			throws Exception;

//	@Select("select a.* from t_historialpasajero a "
//			+ "where a.IdHistorialPasajero = "
//			+ "(select MAX(b.IdHistorialPasajero) from t_historialpasajero b where b.Dni = #{ndni}) ")
	@Select("select top 1 * from t_historialpasajero  where Dni = #{ndni} and nroRuc is not null and nroRuc != '' order by FRegistro desc")
	public HistorialPasajero findByNrocDNI(@Param("ndni") String dni) throws Exception;
	
	
	@Update("update t_historialpasajero set operacion =#{operacion},puntoVentaId = #{puntoVentaId},fechaCaducidad = #{fechaCaducidad}  where IdHistorialPasajero=#{IdHistorialPasajero}")
	@Options(flushCache = true, useCache = true)
	public void actualizarOperacionPosterga(HistorialPasajero historialPasajero) throws Exception;
	
	
	@Select("select * from t_historialpasajero where idpadre = #{IdHistorialPasajero} ")
	public List<HistorialPasajero> getDetalleHistorialPasajero(@Param("IdHistorialPasajero") Integer idHistorialPasajero)
			throws Exception;
	
	
	@Select("select * from t_historialpasajero where Dni = #{dni} and  asientoventaid = #{asientoventaid} order by fregistro DESC ")
	public List<HistorialPasajero> getHistorialByDniAsientoVentaId(@Param("dni") String dni,@Param("asientoventaid") Integer asientoVentaId)
			throws Exception;

}

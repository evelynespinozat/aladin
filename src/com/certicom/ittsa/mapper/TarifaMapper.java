package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Tarifa;

public interface TarifaMapper {

	
	@Select("select * from t_tarifa")
	public List<Tarifa> findAll() throws Exception;
	
	@Select("select * from t_tarifa where Tipo='G'")
	public List<Tarifa> findAllGiros() throws Exception;
	
	@Select("select * from t_tarifa where Tipo=#{tipo} and idOrigen=#{idAgencia} order by IdTarifa asc ")
	public List<Tarifa> findGirosByTipo(@Param("idAgencia") Integer idAgencia, @Param("tipo") String tipo) throws Exception;
	
	@Select("select * from t_tarifa where Tipo=#{tipo} and idOrigen=#{idAgencia} and IdDestino=#{idDestino} order by IdTarifa asc ")
	public List<Tarifa> findRepartoByTipo(@Param("idAgencia") Integer idAgencia,@Param("idDestino") Integer idDestino, @Param("tipo") String tipo) throws Exception;
	
	@Select("select * from t_tarifa where IdTarifa = #{idTarifa}")
	public Tarifa findById(@Param("idTarifa") Integer idTarifa) throws Exception;
	
	@Select("select * from t_tarifa where IdOrigen = #{idorigen} and IdDestino = #{idDestino} and Tipo='E'")
	public Tarifa findTarifaEncomiendaByOrigenDestino(@Param("idorigen") Integer idOrigen,@Param("idDestino") Integer idDestino) throws Exception;
	
	@Select("select * from t_tarifa where IdDestino = #{idDestino} and Sdistrito = #{sdistrito}")
	public Tarifa findByDestProvincia(@Param("idDestino") Integer idDestino, @Param("sdistrito") String sdistrito) throws Exception;

	@Select("select * from t_tarifa where IdDestino = #{idDestino} and Sdistrito = #{sdistrito} and IdOrigen = #{IdOrigen} and Tipo ='R' ")
	public Tarifa findByOriDestProvincia(@Param("IdOrigen") Integer IdOrigen,@Param("idDestino") Integer idDestino, @Param("sdistrito") String sdistrito) throws Exception;
	
	@Delete("delete  from t_tarifa  where IdTarifa = #{IdTarifa}")
	@Options(flushCache=true)
	public void eliminarTarifa(@Param("IdTarifa") Integer IdTarifa) throws Exception;
	
	@Insert("insert into t_tarifa (Tipo,PrecioEnvioNormal,RangoInicio,RangoFin,FRegistro,Estado,Porcentual,IdOrigen) values (#{tipo},#{precioEnvioNormal},#{rangoInicio},#{rangoFin},#{fRegistro},#{estado},#{porcentual},#{idOrigen})")
	public void crearTarifaGiro(Tarifa tarifa) throws Exception;

	@Insert("insert into t_tarifa (Tipo,PrecioEnvioNormal,PrecioEnvioCorporativo,FRegistro,Estado,Porcentual,IdOrigen,IdDestino,PrecioKilo) values (#{tipo},#{precioEnvioNormal},#{precioEnvioCorporativo},#{fRegistro},#{estado},#{porcentual},#{idOrigen},#{idDestino},#{precioKilo})")
	public void crearTarifaEncomienda(Tarifa tarifa) throws Exception;
	
	@Insert("insert into t_tarifa (Tipo,PrecioEnvioNormal,PrecioExtraRapido,FRegistro,Estado,IdOrigen,IdDestino,Sdistrito,sid_ubigeo) values (#{tipo},#{precioEnvioNormal},#{precioExtraRapido},#{fRegistro},#{estado},#{idOrigen},#{idDestino},#{sdistrito},#{sid_ubigeo})")
	public void crearTarifaReparto(Tarifa tarifa) throws Exception;
	
	
	@Update("update t_tarifa set IdOrigen = #{idOrigen},Tipo = #{tipo},PrecioEnvioNormal=#{precioEnvioNormal},PrecioEnvioCorporativo=#{precioEnvioCorporativo},RangoInicio=#{rangoInicio}, RangoFin =#{rangoFin}, FRegistro = #{fRegistro}, Porcentual = #{porcentual},Estado = #{estado} where IdTarifa= #{idTarifa}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTarifaGiro(Tarifa tarifa) throws Exception;
	
	@Update("update t_tarifa set Tipo = #{tipo},PrecioEnvioNormal=#{precioEnvioNormal},PrecioEnvioCorporativo=#{precioEnvioCorporativo},PrecioExtraRapido=#{precioExtraRapido}, FRegistro = #{fRegistro}, Porcentual = #{porcentual},Estado = #{estado}, IdOrigen = #{idOrigen}, IdDestino = #{idDestino}, PrecioKilo = #{precioKilo} where IdTarifa= #{idTarifa}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTarifaEncomienda(Tarifa tarifa) throws Exception;
	
	@Update("update t_tarifa set Estado = #{estado} where IdTarifa= #{idTarifa}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstado(Tarifa tarifa) throws Exception;
	
	
}

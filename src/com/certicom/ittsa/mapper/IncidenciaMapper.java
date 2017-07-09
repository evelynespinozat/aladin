package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.domain.Incidencia;
import com.certicom.ittsa.domain.Oficina;

public interface IncidenciaMapper {

	
	@Select("select * from t_incidencia order by incidenciaId asc")
	public List<Incidencia> findAll() throws Exception;
	

	@Select("select * from t_incidencia where incidenciaId = #{p_incidenciaId}")
	public Agencia findById(@Param("p_incidenciaId") Integer incidenciaId) throws Exception;
	
	@Select("select * from t_incidencia where IdPiloto = #{idPiloto}")
	public List<Incidencia> findByPilotoId(@Param("idPiloto") Integer idPiloto) throws Exception;
	
	@Select("select * from t_incidencia where IdTerramoza = #{idTerramoza}")
	public List<Incidencia> findByTerramozaId(@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	@Delete("delete  from t_incidencia  where incidenciaId = #{p_incidenciaId}")
	@Options(flushCache=true)
	public void eliminarIncicencia(@Param("p_incidenciaId") Integer incidenciaId) throws Exception;
	
	@Insert("insert into t_incidencia (descripcion,fecha,tipo,IdPiloto,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{descripcion},#{fecha},#{tipo},#{idPiloto},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion})")
	public void crearIncidencia_Piloto(Incidencia incidencia) throws Exception;
	
	@Insert("insert into t_incidencia (descripcion,fecha,tipo,IdTerramoza,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{descripcion},#{fecha},#{tipo},#{idTerramoza},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion})")
	public void crearIncidencia_Terramoza(Incidencia incidencia) throws Exception;
	
	/*
	@Update("update t_agencia set Descripcion = #{Descripcion},Direccion=#{Direccion} ,Telefono1=#{Telefono1}, Telefono2 =#{Telefono2},Email = #{Email},Grupo = #{Grupo},Estado = #{Estado} where Idagencia= #{Idagencia}")
	//@Update("update t_cliente set razonsocial_cliente=#{razonsocial_cliente},direccion_cliente=#{direccion_cliente} ,contacto_cliente=#{direccion_cliente},telefono_cliente=#{telefono_cliente}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAgencia(Agencia agencia) throws Exception;
	
	 */
	
	
}

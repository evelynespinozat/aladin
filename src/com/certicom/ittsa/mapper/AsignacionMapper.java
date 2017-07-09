package com.certicom.ittsa.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Asignacion;

public interface AsignacionMapper {

	
	@Select("select * from t_agencia order by descripcion asc")
	public List<Asignacion> findAll() throws Exception;
	
	
	@Select("select * from t_asignacion where programacionId = #{idProgramacion}")
	public Asignacion findByProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	
	@Delete("delete  from t_liquidacion  where IdLiquidacion = #{idLiquidacion}")
	@Options(flushCache=true)
	public void eliminarAsignacion(@Param("idAsignacion") Integer idAsignacion) throws Exception;
	
	@Insert("insert into t_asignacion (monto,programacionId,idPiloto,fecha,idUsuario,agenciaOrigen) values (#{monto},#{programacionId},#{idPiloto},#{fecha},#{idUsuario},#{agenciaOrigen})")
	public void crearAsignacion(Asignacion asignacion) throws Exception;
	
	
	@Update("update t_asignacion set monto = #{monto}, programacionId=#{programacionId} ,idPiloto = #{idPiloto}, fecha = #{fecha}, idUsuario = #{idUsuario}, agenciaOrigen = #{agenciaOrigen} where idasignacion= #{idasignacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAsignacion(Asignacion asignacion) throws Exception;
	
	
	@Select("select sum(asig.monto) from t_asignacion asig where programacionId = #{programacionId}")
	public BigDecimal sumaAsignacionByProgramacion(@Param("programacionId") Integer programacionId) throws Exception;
}

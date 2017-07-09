package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Evaluacion;


public interface EvaluacionMapper {
	

	@Select("select * from t_evaluacion ")
	public List<Evaluacion> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_evaluacion')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_evaluacion where evaluacionId = #{evaluacionId}")
	public Evaluacion findById(@Param("evaluacionId") Integer evaluacionId) throws Exception;
	
	@Select("select * from t_evaluacion where IdPiloto = #{pilotoId}")
	public List<Evaluacion> findByPilotoId(@Param("pilotoId") Integer idPiloto) throws Exception;
	
	@Select("select * from t_evaluacion where IdTerramoza = #{terramozaId}")
	public List<Evaluacion> findByTerramozaId(@Param("terramozaId") Integer idTerramoza) throws Exception;
	
	
	@Delete("delete  from t_evaluacion  where evaluacionId = #{evaluacionId}")
	@Options(flushCache=true)
	public void eliminarEvaluacion(@Param("evaluacionId") Integer evaluacionId) throws Exception;
	
	@Insert("insert into t_evaluacion (nombre,observacion,imagen,IdPiloto,rutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nombre},#{observacion},#{imagen},#{pilotoId},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearEvaluacion_Piloto(Evaluacion evaluacion) throws Exception;
	
	@Insert("insert into t_evaluacion (nombre,observacion,imagen,IdTerramoza,rutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nombre},#{observacion},#{imagen},#{terramozaId},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearEvaluacion_Terramoza(Evaluacion evaluacion) throws Exception;
	

}

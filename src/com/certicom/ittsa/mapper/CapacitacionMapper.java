package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.domain.Evaluacion;


public interface CapacitacionMapper {
	

	@Select("select * from t_capacitacion ")
	public List<Capacitacion> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_capacitacion')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_capacitacion where CapacitacionId = #{capacitacionId}")
	public Capacitacion findById(@Param("capacitacionId") Integer capacitacionId) throws Exception;
	
	@Select("select * from t_capacitacion where IdPiloto = #{idPiloto}")
	public List<Capacitacion> findByPilotoId(@Param("idPiloto") Integer idPiloto) throws Exception;
	
	@Select("select * from t_capacitacion where IdTerramoza = #{idTerramoza}")
	public List<Capacitacion> findByTerramozaId(@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	
	@Delete("delete  from t_capacitacion  where CapacitacionId = #{capacitacionId}")
	@Options(flushCache=true)
	public void eliminarCapacitacion(@Param("capacitacionId") Integer capacitacionId) throws Exception;
	
	@Insert("insert into t_capacitacion (Curso,Fecha,Anio,Titulo,CentroEstudios,IdPiloto,Imagen,RutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{curso},#{fecha},#{anio},#{titulo},#{centroEstudios},#{idPiloto},#{imagen},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearCapacitacion_Piloto(Capacitacion capacitacion) throws Exception;
	
	@Insert("insert into t_capacitacion (Curso,Fecha,Anio,Titulo,CentroEstudios,IdTerramoza,Imagen,RutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{curso},#{fecha},#{anio},#{titulo},#{centroEstudios},#{idTerramoza},#{imagen},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearCapacitacion_Terramoza(Capacitacion capacitacion) throws Exception;
	

}

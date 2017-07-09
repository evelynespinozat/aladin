package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Formacion;


public interface FormacionMapper {
	

	@Select("select * from t_formacion ")
	public List<Formacion> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_formacion')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_formacion where FormacionId = #{formacionId}")
	public Formacion findById(@Param("formacionId") Integer formacionId) throws Exception;
	
	@Select("select * from t_formacion where IdPiloto = #{idPiloto}")
	public List<Formacion> findByPilotoId(@Param("idPiloto") Integer idPiloto) throws Exception;
	
	@Select("select * from t_formacion where IdTerramoza = #{idTerramoza}")
	public List<Formacion> findByTerramozaId(@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	
	@Delete("delete  from t_formacion  where FormacionId = #{formacionId}")
	@Options(flushCache=true)
	public void eliminarFormacion(@Param("formacionId") Integer formacionId) throws Exception;
	
	@Insert("insert into t_formacion (NivelAcademico,Anio,Especialidad,IdPiloto,Titulo,CentroEstudios,TipoFormacion,Imagen,RutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nivelAcademico},#{anio},#{especialidad},#{idPiloto},#{titulo},#{centroEstudios},#{tipoFormacion},#{imagen},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearFormacion_Piloto(Formacion formacion) throws Exception;
	
	@Insert("insert into t_formacion (NivelAcademico,Anio,Especialidad,IdTerramoza,Titulo,CentroEstudios,TipoFormacion,Imagen,RutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nivelAcademico},#{anio},#{especialidad},#{idTerramoza},#{titulo},#{centroEstudios},#{tipoFormacion},#{imagen},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearFormacion_Terramoza(Formacion formacion) throws Exception;

}

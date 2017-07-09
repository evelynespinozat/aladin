package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Papeleta;


public interface PapeletaMapper {
	

	@Select("select * from t_papeleta ")
	public List<Papeleta> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_papeleta')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_papeleta where Papeletaid = #{papeletaid}")
	public Papeleta findById(@Param("papeletaid") Integer papeletaid) throws Exception;
	
	@Select("select * from t_papeleta where IdPiloto = #{pilotoId}")
	public List<Papeleta> findByPilotoId(@Param("pilotoId") Integer idPiloto) throws Exception;
	
	
	@Delete("delete  from t_papeleta  where Papeletaid = #{papeletaid}")
	@Options(flushCache=true)
	public void eliminarPapeleta(@Param("papeletaid") Integer papeletaid) throws Exception;
	
	@Insert("insert into t_papeleta (FechaPapeleta,Lugar,IdBus,Motivo,Policia,IdPiloto,Imagen,RutaImagen,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{fechaPapeleta},#{lugar},#{idBus},#{motivo},#{policia},#{idPiloto},#{imagen},#{rutaImagen},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearPapeleta(Papeleta papeleta) throws Exception;
	

}

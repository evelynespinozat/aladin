package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Cese;

public interface CeseMapper {

	
	@Select("select * from t_cese where idCese = #{idCese}")
	public Cese findById(@Param("idCese") Integer idCese) throws Exception;
	
	@Delete("delete  from t_cese  where idCese = #{idCese}")
	@Options(flushCache=true)
	public void eliminarCese(@Param("idCese") Integer idCese) throws Exception;
	
	@Insert("insert into t_cese (motivo, observacion, IdTerramoza, IdPiloto,Fecha,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{motivo},#{observacion},#{IdTerramoza},#{IdPiloto},#{Fecha},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion})")
	public void crearCese(Cese cese) throws Exception;
	
	
	@Select("select * from t_cese where  IdPiloto = #{IdPiloto}")
	public List<Cese> listxPiloto(@Param("IdPiloto")Integer IdPiloto) throws Exception;

	@Select("select * from t_cese where IdTerramoza = #{IdTerramoza}")
	public List<Cese> listxTerramoza(@Param("IdTerramoza")Integer IdTerramoza) throws Exception;
	
	
}

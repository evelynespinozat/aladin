package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.DistritoCategoria;

public interface DistritoCategoriaMapper {

	@Insert("insert into t_distrito_categoria (sid_ubigeo,descripcion,valor,costoServicio) values (#{sid_ubigeo},#{descripcion},#{valor},#{costoServicio})")
	public void registrarDistritoCategoria(DistritoCategoria distritoCategoria) throws Exception;
	
	@Select("select * from t_distrito_categoria where sid_ubigeo = #{sid_ubigeo}")
	public List<DistritoCategoria>  listarCategoriaxDistrito(@Param("sid_ubigeo") String sid_ubigeo);
	
	@Delete("delete from t_distrito_categoria where id = #{id}")
	public void eliminarDistritoCategoria(@Param("id")Integer id) throws Exception;
	
	
}

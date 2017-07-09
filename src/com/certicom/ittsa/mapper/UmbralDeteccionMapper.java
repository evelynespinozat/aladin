package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.certicom.ittsa.domain.UmbralDeteccion;

public interface UmbralDeteccionMapper {

	@Select("select * from t_umbral_deteccion")
	public List<UmbralDeteccion> findAll() throws Exception;

	@Select("select * from t_umbral_deteccion where id_deteccion = #{id_deteccion}")
	public UmbralDeteccion findById(@Param("id_deteccion") Integer id_deteccion) throws Exception;
	
	@Delete("delete  from t_umbral_deteccion  where id_deteccion = #{id_deteccion}")
	@Options(flushCache=true)
	public void eliminarUmbralDeteccion(@Param("id_deteccion") Integer id_deteccion) throws Exception;
	
	@Insert("insert into t_umbral_deteccion (id_deteccion,descripcion,estado) "
			+ " values (#{id_deteccion},#{descripcion},#{estado})")
	public void crearUmbralDeteccion(UmbralDeteccion umbralDeteccion) throws Exception;
	
	
	@Update("update t_umbral_deteccion set estado =#{estado} where id_deteccion= #{id_deteccion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarUmbralDeteccion(UmbralDeteccion umbralDeteccion) throws Exception;
	
	
	@Select("select * from t_umbral_deteccion where Estado = 1 ")
	public List<UmbralDeteccion> listaUmbralDeteccionActivos() throws Exception;
	
	@Select("select * from t_umbral_deteccion where estado = #{estado} ")
	public UmbralDeteccion findByEstado(@Param("estado")Boolean estado) throws Exception;
}

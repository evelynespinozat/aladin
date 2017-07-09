package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.certicom.ittsa.domain.UmbralVerificacion;

public interface UmbralVerificacionMapper {
	
	@Select("select * from t_umbral_verificacion")
	public List<UmbralVerificacion> findAll() throws Exception;

	@Select("select * from t_umbral_verificacion where id_verificacion = #{id_verificacion}")
	public UmbralVerificacion findById(@Param("id_verificacion") Integer id_verificacion) throws Exception;
	
	@Delete("delete  from t_umbral_verificacion  where id_verificacion = #{id_verificacion}")
	@Options(flushCache=true)
	public void eliminarUmbralVerificacion(@Param("id_verificacion") Integer id_verificacion) throws Exception;
	
	@Insert("insert into t_umbral_verificacion (id_verificacion,descripcion,estado) "
			+ " values (#{id_verificacion},#{descripcion},#{estado})")
	public void crearUmbralVerificacion(UmbralVerificacion umbralVerificacion) throws Exception;
	
	
	@Update("update t_umbral_verificacion set estado =#{estado} where id_verificacion= #{id_verificacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarUmbralVerificacion(UmbralVerificacion umbralVerificacion) throws Exception;
	
	
	@Select("select * from t_umbral_verificacion where Estado = 1 ")
	public List<UmbralVerificacion> listaUmbralVerificacionActivos() throws Exception;
	
	@Select("select * from t_umbral_verificacion where estado = #{estado} ")
	public UmbralVerificacion findByEstado(@Param("estado")Boolean estado)throws Exception;

	@Select("select MIN (id_verificacion) from t_umbral_verificacion")
	public int findMinUmbral() throws Exception;
}

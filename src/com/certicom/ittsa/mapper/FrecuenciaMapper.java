package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Frecuencia;

public interface FrecuenciaMapper {

	@Select("select * from t_frecuencia order by Descripcion asc")
	public List<Frecuencia> findAll() throws Exception;
	

	@Select("select * from t_frecuencia where IdFrecuencia = #{idFrecuencia}")
	public Frecuencia findById(@Param("idFrecuencia") Integer idFrecuencia) throws Exception;
	
	@Delete("delete  from t_frecuencia  where IdFrecuencia = #{idFrecuencia}")
	@Options(flushCache=true)
	public void eliminarFrecuencia(@Param("idFrecuencia") Integer idFrecuencia) throws Exception;
	
	@Insert("insert into t_frecuencia (Descripcion, Estado) values (#{descripcion},#{estado})")
	public void crearFrecuencia(Frecuencia frecuencia) throws Exception;
	
	
	@Update("update t_frecuencia set Descripcion = #{descripcion},Estado = #{estado} where IdFrecuencia= #{idFrecuencia}")
	@Options(flushCache=true,useCache=true)
    public void actualizarFrecuencia(Frecuencia frecuencia) throws Exception;
	
	
	@Select("select count(*) from t_servicio where TipoFrecuencia = #{TipoFrecuencia}")
	public Integer cantServicios(Integer TipoFrecuencia);
	
	@Select("select * from t_frecuencia where Estado = 1 order by Descripcion asc")
	public List<Frecuencia> listaFrecuenciasActivas() throws Exception;
}

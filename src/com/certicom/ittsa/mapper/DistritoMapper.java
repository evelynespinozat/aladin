package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Distrito;
import com.certicom.ittsa.domain.Oficina;

public interface DistritoMapper {

	
	@Select("select * from t_distrito order by descripcion asc")
	public List<Distrito> findAll() throws Exception;
	
	@Select("select * from t_distrito where IdAgencia=#{idAgencia} order by descripcion asc ")
	public List<Distrito> findByAgencia(@Param ("idAgencia") Integer idAgencia) throws Exception;
	

	@Select("select * from t_distrito where IdDistrito = #{idDistrito}")
	public Agencia findById(@Param("IdDistrito") Integer IdDistrito) throws Exception;
	
	
	@Select("select * from t_distrito where Descripcion = #{descripcion}")
	public Agencia findByDescripcion(@Param("descripcion") String descripcion) throws Exception;
	
	
	@Delete("delete  from t_distrito  where IdDistrito = #{idDistrito}")
	@Options(flushCache=true)
	public void eliminarDistrito(@Param("idDistrito") Integer idDistrito) throws Exception;
	
	@Insert("insert into t_distrito (Descripcion, IdAgencia,Estado) values (#{descripcion},#{idAgencia},#{estado})")
	public void crearDistrito(Distrito distrito) throws Exception;
	
	
	@Update("update t_distrito set Descripcion = #{descripcion},IdAgencia=#{idAgencia},Estado = #{estado} where IdDistrito= #{idDistrito}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDistrito(Distrito distrito) throws Exception;
	
}

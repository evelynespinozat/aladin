package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.certicom.ittsa.domain.Automotor;

public interface AutomotorMapper {

	
	@Select("select * from t_automotor order by descripcion asc")
	public List<Automotor> findAll() throws Exception;
	
	@Select("select * from t_automotor where IdAutomotor = #{idAutomotor}")
	public Automotor findById(@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
	@Delete("delete  from t_automotor  where IdAutomotor = #{idAutomotor}")
	@Options(flushCache=true)
	public void eliminarAutomotor(@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
	@Insert("insert into t_automotor (Descripcion,Estado) values (#{descripcion},#{estado})")
	public void crearAutomotor(Automotor automotor) throws Exception;
	
	@Update("update t_automotor set Descripcion = #{descripcion},Estado = #{estado} where IdAutomotor= #{idAutomotor}")
	@Options(flushCache=true,useCache=true)
    public void actualizarAutomotor(Automotor automotor) throws Exception;
	
	@Select("select * from t_automotor where Estado = 1 order by descripcion asc")
	public List<Automotor> listaAutomotorsActivas() throws Exception;
	
	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.TipoAsiento;

public interface TipoAsientoMapper {

	
	@Select("select * from t_tipoasiento order by nombre asc")
	public List<TipoAsiento> findAll() throws Exception;
	

	@Select("select * from t_tipoasiento where IdTipoAsiento = #{idTipoAsiento}")
	public TipoAsiento findById(@Param("idTipoAsiento") Integer idTipoAsiento) throws Exception;
	
	@Delete("delete  from t_tipoasiento  where IdTipoAsiento = #{idTipoAsiento}")
	@Options(flushCache=true)
	public void eliminarTipoAsiento(@Param("idTipoAsiento") Integer idTipoAsiento) throws Exception;
	
	@Insert("insert into t_tipoasiento (nombre,Estado) values (#{nombre},#{estado})")
	public void crearTipoAsiento(TipoAsiento tipoAsiento) throws Exception;
	
	
	@Update("update t_tipoasiento set nombre = #{nombre},Estado = #{estado} where idtipoasiento= #{idtipoasiento}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTipoAsiento(TipoAsiento tipoAsiento) throws Exception;
	
	
	@Select("select * from t_tipoasiento where Estado = 1 order by nombre asc")
	public List<TipoAsiento> listaTipoAsientosActivas() throws Exception;
	
	
}

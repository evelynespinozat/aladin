package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.TipoFlota;

public interface TipoFlotaMapper {

	
	@Select("select * from t_tipoflota order by descripcion asc")
	public List<TipoFlota> findAll() throws Exception;
	

	@Select("select * from t_tipoflota where IdTipoFlota = #{idTipoFlota}")
	public TipoFlota findById(@Param("idTipoFlota") Integer idTipoFlota) throws Exception;
	
	@Delete("delete  from t_tipoflota  where IdTipoFlota = #{idTipoFlota}")
	@Options(flushCache=true)
	public void eliminarTipoFlota(@Param("idTipoFlota") Integer idTipoFlota) throws Exception;
	
	@Insert("insert into t_tipoflota (Descripcion,Estado) values (#{descripcion},#{estado})")
	public void crearTipoFlota(TipoFlota tipoFlota) throws Exception;
	
	
	@Update("update t_tipoflota set Descripcion = #{descripcion},Estado = #{estado} where IdTipoFlota= #{idTipoFlota}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTipoFlota(TipoFlota tipoFlota) throws Exception;
	
	
	@Select("select * from t_tipoflota where Estado = 1 order by descripcion asc")
	public List<TipoFlota> listaTipoFlotasActivas() throws Exception;
	
	
}

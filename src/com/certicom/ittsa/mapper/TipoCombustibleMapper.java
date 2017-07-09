package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.TipoCombustible;

public interface TipoCombustibleMapper {

	
	@Select("select * from t_tipocombustible order by descripcion asc")
	public List<TipoCombustible> findAll() throws Exception;
	

	@Select("select * from t_tipocombustible where IdTipoCombustible = #{idTipoCombustible}")
	public TipoCombustible findById(@Param("idTipoCombustible") Integer idTipoCombustible) throws Exception;
	
	@Delete("delete  from t_tipocombustible  where idTipoCombustible = #{idTipoCombustible}")
	@Options(flushCache=true)
	public void eliminarTipoCombustible(@Param("idTipoCombustible") Integer idTipoCombustible) throws Exception;
	
	@Insert("insert into t_tipocombustible (Descripcion, Tipo, Estado) values (#{descripcion},#{tipo},#{estado})")
	public void crearTipoCombustible(TipoCombustible tipoCombustible) throws Exception;
	
	
	@Update("update t_tipocombustible set Descripcion = #{descripcion}, Tipo=#{tipo} ,Estado=#{estado} where IdTipoCombustible= #{idTipoCombustible}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTipoCombustible(TipoCombustible tipoCombustible) throws Exception;
	
	
	@Select("select * from t_tipocombustible where Estado = 1 order by descripcion asc")
	public List<TipoCombustible> listaTipoCombustibleActivas() throws Exception;
	
	
}

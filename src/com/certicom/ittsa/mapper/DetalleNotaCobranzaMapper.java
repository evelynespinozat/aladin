package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.certicom.ittsa.domain.DetalleNotaCobranza;

public interface DetalleNotaCobranzaMapper {

	
	@Select("select * from t_detallenotacobranza order by Descripcion asc")
	public List<DetalleNotaCobranza> findAll() throws Exception;
	
	@Select("select * from t_detallenotacobranza where IdDetNotaCobranza = #{idDetNotaCobranza}")
	public DetalleNotaCobranza findById(@Param("idDetNotaCobranza") Integer idDetNotaCobranza) throws Exception;
	
	@Delete("delete from t_detallenotacobranza where IdDetNotaCobranza = #{idDetNotaCobranza}")
	@Options(flushCache=true)
	public void eliminarDetalleNotaCobranza(@Param("idDetNotaCobranza") Integer idDetNotaCobranza) throws Exception;
	
	@Insert("insert into t_detallenotacobranza (IdNotaCobranza, Descripcion, Peso, Cantidad, PrecioEnvio) values (#{idNotaCobranza},#{descripcion},#{peso},#{cantidad},#{precioEnvio})")
	public void crearDetalleNotaCobranza(DetalleNotaCobranza detalleNotaCobranza) throws Exception;
	
	@Update("update t_detallenotacobranza set IdNotaCobranza = #{idNotaCobranza},Descripcion=#{descripcion},Peso=#{peso},Cantidad =#{cantidad},PrecioEnvio=#{precioEnvio} where IdDetNotaCobranza= #{idDetNotaCobranza}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDetalleNotaCobranza(DetalleNotaCobranza detalleNotaCobranza) throws Exception;

	@Select("select * from t_detallenotacobranza where IdNotaCobranza = #{idNotaCobranza}")
	public List<DetalleNotaCobranza> obtenerDetalleNotaCobranza(@Param("idNotaCobranza")Integer idNotaCobranza)throws Exception;
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Oficina;

public interface DetalleEncomiendaMapper {

	
	@Select("select * from t_encomiendaDetalle order by IdDetalle asc")
	public List<DetalleEncomienda> findAll() throws Exception;
	
	@Select("SELECT TOP 1 * FROM t_encomiendaDetalle where IdEncomienda=#{idEncomienda} ORDER BY IdDetalle DESC ")
	public DetalleEncomienda findLastDetalleEncomiendaByIdEnc(@Param("idEncomienda") Integer idEncomienda) throws Exception;

	@Select("select * from t_encomiendaDetalle where IdDetalle = #{idDetalle}")
	public DetalleEncomienda findById(@Param("idDetalle") Integer idDetalle) throws Exception;
	
	@Select("select * from t_encomiendaDetalle where IdEncomienda = #{idEncomienda}")
	public  List<DetalleEncomienda> findByIdEncomienda(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	
	@Delete("delete  from t_encomiendaDetalle  where IdDetalle = #{idDetalle}")
	@Options(flushCache=true)
	public void eliminarDetalleEncomienda(@Param("idDetalle") Integer idDetalle) throws Exception;
	
	@Insert("insert into t_encomiendaDetalle (IdEncomienda,TipoEnvio,Peso,Importe,Cantidad,Descripcion,PrecioKilo,CodigoBarras,CodigoBarrasString) "
			+ "values (#{idEncomienda},#{tipoEnvio},#{peso},#{importe},#{cantidad},#{descripcion},#{precioKilo},#{codigoBarras},#{codigoBarrasString})")
	public void crearDetalleEncomienda(DetalleEncomienda detalleEncomienda) throws Exception;
	
	
	@Update("update t_encomiendaDetalle set IdEncomienda = #{idEncomienda},TipoEnvio=#{tipoEnvio} ,Peso=#{peso}, Importe =#{importe},Cantidad = #{cantidad},"
			+ "Descripcion = #{descripcion},PrecioKilo = #{precioKilo},CodigoBarras = #{codigoBarras},CodigoBarrasString = #{codigoBarrasString}"
			+ "where IdEncomienda= #{idEncomienda}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDetalleEncomienda(DetalleEncomienda detalleEncomienda) throws Exception;
	
	@Select("select * from t_encomiendaDetalle where CodigoBarrasString = #{codigoBarrasString}")
	public DetalleEncomienda findByDetalleByCodigoBarrasString(@Param("codigoBarrasString") String codigoBarrasString) throws Exception;
	
	
}

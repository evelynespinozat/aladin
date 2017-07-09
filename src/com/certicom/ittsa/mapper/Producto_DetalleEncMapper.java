package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto_DetalleEnc;

public interface Producto_DetalleEncMapper {

	
	@Select("select * from t_productos_detalleEnc order by IdDetalle asc")
	public List<Producto_DetalleEnc> findAll() throws Exception;
	

	@Select("select * from t_productos_detalleEnc where IdProducto_detalleEnc = #{idProducto_detalleEnc}")
	public Producto_DetalleEnc findById(@Param("idProducto_detalleEnc") Integer idProducto_detalleEnc) throws Exception;
	
	@Delete("delete  from t_productos_detalleEnc  where IdProducto_detalleEnc = #{idProducto_detalleEnc}")
	@Options(flushCache=true)
	public void eliminarProducto_DetalleEnc(@Param("idProducto_detalleEnc") Integer idProducto_detalleEnc) throws Exception;
	
	@Insert("insert into t_productos_detalleEnc (IdDetalle, IdEncomienda, CodigoBarrasString, CodigoBarras, Estado,CodigoControl,IdCompuesto,Descripcion) "
			+ "values (#{idDetalle},#{idEncomienda},#{codigoBarrasString},#{codigoBarras},#{estado},#{codigoControl},#{IdCompuesto},#{descripcion})")
	public void crearProducto_DetalleEnc(Producto_DetalleEnc producto_DetalleEnc) throws Exception;
	
	
	@Update("update t_productos_detalleEnc set IdDetalle = #{idDetalle},IdEncomienda=#{idEncomienda} ,CodigoBarrasString=#{codigoBarrasString}, CodigoBarras =#{codigoBarras},Estado = #{estado} where IdProducto_detalleEnc= #{idProducto_detalleEnc}")
	@Options(flushCache=true,useCache=true)
    public void actualizarProducto_DetalleEnc(Producto_DetalleEnc producto_DetalleEnc) throws Exception;
	
	
	@Select("select  pde.IdProducto_detalleEnc as IdProducto_detalleEnc, pde.CodigoBarrasString as CodigoBarrasString, "
			+ "ed.Descripcion as desProducto, e.IdEncomienda as idEncomienda, ed.IdDetalle as idDetalle, "
			+ "e.TipoDocumento as tipoDocumento from t_productos_detalleEnc pde inner join t_encomienda e "
			+ "on pde.IdEncomienda = e.IdEncomienda inner join t_encomiendaDetalle ed "
			+ "	on ed.IdDetalle = pde.IdDetalle where pde.IdEncomienda = #{idEncomienda}")
	public List<Producto_DetalleEnc> listarPorIdEncomienda(@Param("idEncomienda") Integer idEncomienda) throws Exception;
	
	@Select("select IdEncomienda from t_productos_detalleEnc where REPLACE(CodigoBarrasString,' ', '') = #{codigoBarra}")
	public Integer obtenerIdEncomiendaxCodigoBarras(@Param("codigoBarra")String _codigoBarras) throws Exception;

	@Select("select * from t_productos_detalleEnc where IdDetalle = #{idDetalle} and IdEncomienda = #{idEncomienda}")
	public List<Producto_DetalleEnc> obtenerProductoDetallexIdEncomiendaxIdDetalle(@Param("idEncomienda")Integer idEncomienda, @Param("idDetalle")Integer idDetalle) throws Exception;
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.CompraDetalle;

public interface CompraDetalleMapper {
	
	public void registrarCompraDetalle(CompraDetalle compraDetalle);
	
	@Select("select cd.IdCompDet, cd.IdCompra, p.IdProducto , cd.fRegistro, p.Descripcion as desProducto,"
			+ " p.CostoUni as costoUni, cd.cantIngreso "
			+ "from t_compra_detalle cd inner join t_producto p on cd.IdProducto =  p.IdProducto "
			+ " where cd.IdCompra = #{idCompra} ")
	public List<CompraDetalle> listarxIdCompra(@Param("idCompra") Integer idCompra) throws Exception;
	
	@Select("select distinct cpd.IdProducto ,pr.Descripcion as desProducto "
			+ "from t_compra cp inner join t_compra_detalle cpd on cp.IdCompra = cpd.IdCompra "
			+ "inner join t_producto pr on cpd.IdProducto = pr.IdProducto where cp.IdProveedor = #{IdProveedor}")
	public List<CompraDetalle> listaProductosXProveedor(@Param("IdProveedor")Integer IdProveedor)  throws Exception;

}

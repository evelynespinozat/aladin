package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.CompraAutoparte;

public interface CompraAutoparteMapper {
	
	@Insert("insert into t_compraautoparte (ordenCompra,Idagencia,IdOficina,IdProveedor,fRegistro,fechaCompra,tipDocumento,moneda,tipoCambio)"
			+ " values(#{ordenCompra},#{Idagencia},#{IdOficina},#{IdProveedor},#{fRegistro},#{fechaCompra},#{tipDocumento},#{moneda},#{tipoCambio})")
	public void registrarCompra(CompraAutoparte compraAutoparte) ;
	
	
	@Select("select c.IdCompra, c.ordenCompra,c.Idagencia,a.Descripcion as desAgencia,c.IdOficina, o.Nombre as desOficina, "
			+ "c.IdProveedor, p.RazonSocial as desProveedor, c.fechaCompra,c.fRegistro ,c.tipDocumento " 
			+ "from t_compraautoparte c, t_agencia a , t_agenciaoficina o,t_proveedor p where c.Idagencia = a.Idagencia " 
			+ "and c.IdOficina = o.IdOficina and c.IdProveedor = p.IdProveedor order by c.fechaCompra desc ")
	public List<CompraAutoparte> findAll();
	
	@Select("select MAX(IdCompra) from t_compraautoparte ")
	public Integer getlastId();
}

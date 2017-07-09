package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Compra;

public interface CompraMapper {
	
	@Insert("insert into t_compra (ordenCompra,Idagencia,IdOficina,IdProveedor,fRegistro,fechaCompra,tipDocumento)"
			+ " values(#{ordenCompra},#{Idagencia},#{IdOficina},#{IdProveedor},#{fRegistro},#{fechaCompra},#{tipDocumento})")
	public void registrarCompra(Compra compra) ;
	
	
	@Select("select c.IdCompra, c.ordenCompra,c.Idagencia,a.Descripcion as desAgencia,c.IdOficina, o.Nombre as desOficina, "
			+ "c.IdProveedor, p.RazonSocial as desProveedor, c.fechaCompra,c.fRegistro ,c.tipDocumento " 
			+ "from t_compra c, t_agencia a , t_agenciaoficina o,t_proveedor p where c.Idagencia = a.Idagencia " 
			+ "and c.IdOficina = o.IdOficina and c.IdProveedor = p.IdProveedor order by c.fechaCompra desc ")
	public List<Compra> findAll();
	
	@Select("select MAX(IdCompra) from t_compra ")
	public Integer getlastId();
	
	@Select("select c.IdCompra, c.ordenCompra, c.fechaCompra, c.tipDocumento, "
			+ "(select Descripcion from t_agencia where Idagencia = c.Idagencia) as desAgencia, "
			+ "(select Nombre from t_agenciaoficina where IdOficina = c.IdOficina) as desOficina "
			+ "from t_compra c where c.IdProveedor = #{idProveedor} order by c.fRegistro desc ")
	public List<Compra> listarCompraxProveedor(@Param("idProveedor") Integer idProveedor) throws Exception;
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.ProveedorAgencia;

public interface ProveedorAgenciaMapper {

	@Select("select * from t_proveedor_agencia")
	public List<ProveedorAgencia> findAll() throws Exception;
	

	@Select("select * from t_proveedor_agencia where Id = #{Id}")
	public ProveedorAgencia findById(@Param("Id") Integer Id) throws Exception;

	
	@Insert("insert into t_proveedor_agencia (Idagencia, IdOficina, IdProveedor) "
			+ " values (#{Idagencia},#{IdOficina},#{IdProveedor})")
	public void crearProveedorAgencia(ProveedorAgencia proveedorAgencia) throws Exception;
	
	
	public List<ProveedorAgencia> listOfixProveedor(Integer IdProveedor);
	
	@Delete("delete from t_proveedor_agencia where Id = #{Id}")
	public void deleteOficinaFromProveedor(Integer Id);
	
	@Select("select count(*) from t_proveedor_agencia where Idagencia =#{Idagencia} and IdOficina=#{IdOficina} and IdProveedor=#{IdProveedor}")
	public Integer getCantProvAgencia(ProveedorAgencia proveedorAgencia);
	
	@Select("select pa.IdProveedor,p.razonSocial from t_proveedor_agencia pa , t_proveedor p where p.IdProveedor = pa.IdProveedor and pa.IdOficina=#{IdOficina} ")
	public List<ProveedorAgencia> listarProveedorxOficina(@Param("IdOficina")Integer IdOficina);
	
	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Proveedor;

public interface ProveedorMapper {

	public List<Proveedor> findAll() throws Exception;
	
	@Select("select p.*,(select (sdepartamento + '-' + sprovincia + '-' + sdistrito)  from t_ubigeo where sid_ubigeo = p.codUbigeo) as desUbigeo "
			+ "	 from t_proveedor p where p.tipo = 'ATB' ")
	public List<Proveedor> listarProveedorATB() throws Exception;
	
	@Select("select p.*,(select (sdepartamento + '-' + sprovincia + '-' + sdistrito)  from t_ubigeo where sid_ubigeo = p.codUbigeo) as desUbigeo "
			+ "	 from t_proveedor p where p.tipo = 'MAN' ")
	public List<Proveedor> listarProveedorMAN() throws Exception;
	

	@Select("select * from t_proveedor where IdProveedor = #{IdProveedor}")
	public Proveedor findById(@Param("IdProveedor") Integer IdProveedor) throws Exception;
	
	@Delete("delete  from t_proveedor  where IdProveedor = #{IdProveedor}")
	@Options(flushCache=true)
	public void eliminarProveedor(@Param("IdProveedor") Integer IdProveedor) throws Exception;
	
	@Insert("insert into t_proveedor (RUC,RazonSocial,Direccion,Telefono,Email,codUbigeo,Estado,Contacto,telContacto,tipo) "
			+ " values (#{RUC},#{RazonSocial},#{Direccion},#{Telefono},#{Email},#{codUbigeo},#{Estado},#{Contacto},#{telContacto},#{tipo})")
	public void crearProveedor(Proveedor proveedor) throws Exception;
	
	
	@Update("update t_proveedor set RUC = #{RUC},RazonSocial=#{RazonSocial} ,Direccion=#{Direccion}, Telefono =#{Telefono},"
			+ "Email = #{Email},codUbigeo = #{codUbigeo},Estado = #{Estado}, Contacto =#{Contacto}, telContacto =#{telContacto}, tipo = #{tipo} "
			+ " where IdProveedor= #{IdProveedor}")
	@Options(flushCache=true,useCache=true)
    public void actualizarProveedor(Proveedor proveedor) throws Exception;
	
	
	@Select("select * from t_proveedor where Estado = 1 ")
	public List<Proveedor> listaProveedoresActivos() throws Exception;

	@Select("select * from t_proveedor where Estado = 1 and tipo = 'ATB' ")
	public List<Proveedor> listaProveedoresActivosATB() throws Exception;
	
	@Select("select * from t_proveedor where Estado = 1  and tipo = 'MAN' ")
	public List<Proveedor> listaProveedoresActivosMAN() throws Exception;

	@Select("select count(*) from t_producto where IdProveedor = #{IdProveedor}")
	public Integer cantProveedoresxProducto(Integer IdProveedor) throws Exception;

	@Select("select count(*) from t_autoparte where IdProveedor = #{IdProveedor}")
	public Integer cantProveedoresxAutoparte(Integer IdProveedor) throws Exception;
	
	
}

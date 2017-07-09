package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Empresa;


public interface EmpresaMapper {

	@Select("select * from t_empresa where RUC = #{RUC}")
	public Empresa findByNroRUC(@Param("RUC") String  RUC) throws Exception;
	
	@Select("select * from t_empresa where RazonSocial like '%' + #{p_razsoc} + '%'")
	public List<Empresa> findByRazSoc(@Param("p_razsoc") String  RazonSocial) throws Exception;
	
	@Insert("insert into t_empresa (RUC,RazonSocial, Direccion, Telefono, Email, Estado,FRegistro,Telefono2,PaginaWeb,PersonaContacto) values (#{RUC},#{RazonSocial},#{Direccion},#{Telefono},#{Email},#{Estado},#{FRegistro},#{Telefono2},#{PaginaWeb},#{PersonaContacto})")
	public void crearEmpresa(Empresa empresa) throws Exception;
	
	
	@Update("update t_empresa.dbo.M_Persona_RENIEC set tipodocumento = #{tipodocumento},apaterno = #{apaterno},amaterno=#{amaterno} ,nombres=#{nombres}, sexo =#{sexo},nacimiento = #{nacimiento} where dni= #{dni}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEmpresa(Empresa empresa) throws Exception;
	
	
	@Update("update t_empresa set RazonSocial = #{RazonSocial},Direccion = #{Direccion},Telefono=#{Telefono} ,Email=#{Email}, Estado =#{Estado},FRegistro = #{FRegistro},Telefono2 = #{Telefono2},PaginaWeb = #{PaginaWeb},PersonaContacto = #{PersonaContacto} where RUC= #{RUC}")
	@Options(flushCache=true,useCache=true)
    public void updateEmpresa(Empresa empresa) throws Exception;
	
	
	@Update("update t_empresa set Estado = #{Estado} where RUC= #{RUC}")
	@Options(flushCache=true,useCache=true)
    public void updateEstadoEmpresa(Empresa empresa) throws Exception;
	
	@Select("select * from t_empresa")
	public List<Empresa> findAllEmpresas() throws Exception;
	
	@Delete("delete  from t_empresa  where RUC = #{ruc}")
	@Options(flushCache=true)
	public void deleteEmpresa(@Param("ruc") String ruc) throws Exception; 
	
	
}

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
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Terramoza;

public interface TerramozaMapper {

	
	@Select("select  * from t_terramoza order by Apellidos asc")
	public List<Terramoza> findAll() throws Exception;
	

	@Select("select * from t_terramoza where IdTerramoza = #{idTerramoza}")
	public Terramoza findById(@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	@Select("select * from t_terramoza where DNI = #{dNI}")
	public Terramoza findByDNI(@Param("dNI") String dni) throws Exception;
	
	@Delete("delete  from t_terramoza  where IdTerramoza = #{idTerramoza}")
	@Options(flushCache=true)
	public void eliminarTerramoza(@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	@Insert("insert into t_terramoza (DNI, Apellidos, Nombres, FNacimiento, CodigoAnterior,Telefono, Estado, FIngreso, Residencia, Imagen,NroCell,Correo,Terminal,Ocupacion,GrdoInstruccion,Condicion,Sexo,Cargo,Disponibilidad,sid_ubigeo,FechaCreacion,usuarioCreacion) "
			+ "				  values (#{dNI},#{apellidos},#{nombres},#{fNacimiento},#{codigoAnterior},#{telefono},#{estado},#{fIngreso},#{residencia},#{imagen},#{nroCell},#{correo},#{terminal},#{ocupacion},#{grdoInstruccion},#{condicion},#{sexo},#{cargo},#{disponibilidad},#{sid_ubigeo},#{FechaCreacion},#{usuarioCreacion})")
	public void crearTerramoza(Terramoza terramoza) throws Exception;
	
	
	@Update("update t_terramoza set DNI = #{dNI},Apellidos=#{apellidos} ,Nombres=#{nombres},FNacimiento = #{fNacimiento},CodigoAnterior = #{codigoAnterior},Telefono = #{telefono},Estado = #{estado},FIngreso = #{fIngreso},Residencia = #{residencia},Imagen = #{imagen}, NroCell = #{nroCell}, "
			+ "Correo = #{correo}, Terminal = #{terminal}, Ocupacion = #{ocupacion}, GrdoInstruccion = #{grdoInstruccion},Condicion = #{condicion},Sexo = #{sexo},Cargo = #{cargo} where IdTerramoza= #{idTerramoza}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTerramoza(Terramoza terramoza) throws Exception;
	
	@Update("update t_terramoza set DNI = #{dNI},Apellidos=#{apellidos} ,Nombres=#{nombres},FNacimiento = #{fNacimiento},CodigoAnterior = #{codigoAnterior},Telefono = #{telefono},Estado = #{estado},FIngreso = #{fIngreso},Residencia = #{residencia}, NroCell = #{nroCell}, "
			+ "Correo = #{correo}, Terminal = #{terminal}, Ocupacion = #{ocupacion}, GrdoInstruccion = #{grdoInstruccion},Condicion = #{condicion},Sexo = #{sexo},Cargo = #{cargo} where IdTerramoza= #{idTerramoza}")
	@Options(flushCache=true,useCache=true)
    public void actualizarTerramozaSnFoto(Terramoza terramoza) throws Exception;
	
	
	@Select("select * from t_terramoza where Estado = 1 order by Apellidos asc")
	public List<Terramoza> listaTerramozaActivas() throws Exception;
	
	@Select("select COUNT(*) from t_terramoza where DNI = #{dni} ")
	public Integer verificarDniCarnetRepetidos(@Param("dni") String dni)throws Exception;
}
package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Datos_Familiares;
import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Terramoza;


public interface Datos_FamiliaresMapper {
	

	@Select("select * from t_datos_familiares ")
	public List<Datos_Familiares> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_datos_familiares')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_datos_familiares where IdDatosF = #{idDatosF}")
	public Datos_Familiares findById(@Param("idDatosF") Integer idDatosF) throws Exception;
	
	@Select("select * from t_datos_familiares where IdPiloto = #{pilotoId}")
	public List<Datos_Familiares> findByPilotoId(@Param("pilotoId") Integer idPiloto) throws Exception;
	
	@Select("select * from t_datos_familiares where IdTerramoza = #{terramozaId}")
	public List<Datos_Familiares> findByTerramozaId(@Param("terramozaId") Integer idTerramoza) throws Exception;
	
	@Update("update t_datos_familiares set IdTerramoza = #{idTerramoza}, EstadoCivil=#{estadoCivil},Nombres=#{nombres} ,Apellidos=#{apellidos},DNI=#{dni}, FechaNac =#{fechaNac}, NroContacto = #{nroContacto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDatosF_Terramoza( Datos_Familiares datosF) throws Exception;
	
	@Delete("delete  from t_datos_familiares  where IdDatosF = #{idDatosF}")
	@Options(flushCache=true)
	public void eliminarDatosF(@Param("idDatosF") Integer idDatosF) throws Exception;
	
	@Insert("insert into t_datos_familiares (IdPiloto,EstadoCivil,Nombres,Apellidos,DNI,FechaNac,NroContacto,Parentesco,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{idPiloto},#{estadoCivil},#{nombres},#{apellidos},#{dni},#{fechaNac},#{nroContacto},#{parentesco},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearDatosF_Piloto(Datos_Familiares datosF) throws Exception;
	
	@Insert("insert into t_datos_familiares (IdTerramoza,EstadoCivil,Nombres,Apellidos,DNI,FechaNac,NroContacto,Parentesco,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{idTerramoza},#{estadoCivil},#{nombres},#{apellidos},#{dni},#{fechaNac},#{nroContacto},#{parentesco},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion}) ")
	public void crearDatosF_Terramoza(Datos_Familiares datosF) throws Exception;
	

}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.PersonaTrama;

public interface PersonaTramaMapper {


	@Select("select * from PERSONAS.dbo.M_Trama where dni = cast(#{p_nroDocumento} as varchar)")
	public PersonaTrama findByNroDocumento(@Param("p_nroDocumento") String  Dni) throws Exception;
	
	
	
	@Insert("insert into PERSONAS.dbo.M_Trama (dni,trama,descripcion,imagen) values (#{Dni},#{trama},#{descripcion},#{imagen})")
	public void crearPersonaTrama(PersonaTrama persona) throws Exception;
	
	
	@Update("update PERSONAS.dbo.M_Trama set dni = #{Dni},trama =#{trama},descripcion = #{descripcion}, imagen=#{imagen} where dni= #{Dni}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPersonaTrama(PersonaTrama persona) throws Exception;
	
	@Update("update PERSONAS.dbo.M_Trama set trama = #{trama},imagen={imagen} where dni= #{Dni}")
	@Options(flushCache=true,useCache=true)
    public void actualizaHuella(PersonaTrama persona) throws Exception;
}

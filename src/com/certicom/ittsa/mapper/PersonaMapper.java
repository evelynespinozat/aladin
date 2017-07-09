package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;


import com.certicom.ittsa.domain.Persona;

public interface PersonaMapper {
	

	@Select("select * from PERSONAS.dbo.M_Persona_RENIEC where dni = cast(#{p_nroDocumento} as varchar)")
	public Persona findByNroDocumento(@Param("p_nroDocumento") String  Dni) throws Exception;
	
	@Select("select * from PERSONAS.dbo.M_Persona_RENIEC where APaterno = #{aPaterno} and AMaterno = #{aMaterno}")
	public List<Persona> findByApellidos(@Param("aPaterno") String  aPaterno, @Param("aMaterno") String  aMaterno) throws Exception;
	
	@Select("select * from PERSONAS.dbo.M_Persona_RENIEC where APaterno = #{aPaterno}")
	public List<Persona> findByApellidoPaterno(@Param("aPaterno") String  aPaterno) throws Exception;
	
	@Select("select * from PERSONAS.dbo.M_Persona_RENIEC where AMaterno = #{aMaterno}")
	public List<Persona> findByApellidoMaterno(@Param("aMaterno") String  aMaterno) throws Exception;
	//<INICIO> JPISCOYA
	@Insert("insert into PERSONAS.dbo.M_Persona_RENIEC (dni,tipodocumento, apaterno, amaterno, nombres, sexo,nacimiento,digito,email,telefono,fNacimiento,telefonoMovil,direccion) values (#{Dni},#{tipodocumento},#{APaterno},#{AMaterno},#{Nombres},#{sexo},#{Nacimiento},#{Digito},#{email},#{telefono},#{fNacimiento},#{telefonoMovil},#{direccion})")
	public void crearPersona(Persona persona) throws Exception;
	
	@Update("update PERSONAS.dbo.M_Persona_RENIEC set apaterno = #{APaterno},amaterno = #{AMaterno},nombres = #{nombres},telefono = #{telefono},telefonoMovil = #{telefonoMovil},direccion = #{direccion},email = #{email} where dni= CAST(#{Dni} as varchar(14))")
	@Options(flushCache=true,useCache=true)
    public void actualizarPersona(Persona persona) throws Exception;
	//<FIN> JPISCOYA
	@Update("update PERSONAS.dbo.M_Persona_RENIEC set imagen = #{imagen}, trama = #{trama} where dni= #{Dni}")
	@Options(flushCache=true,useCache=true)
    public void actualizaHuella(Persona persona) throws Exception;
	
	@Select(value= "{ CALL SPU_PASAJERO_FRECUENTE(#{dni, mode=IN, jdbcType=VARCHAR})}")
	@Options(statementType=StatementType.CALLABLE)
	public Object actualizarPasajeroFrecuente(@Param("dni") String dni);
	
	//INICIO JPISCOYA
	/*@Update("update PERSONAS.dbo.M_Persona_RENIEC set APaterno = #{APaterno}, AMaterno = #{AMaterno}, Nombres = #{nombres}, Telefono = #{telefono}, telefonoMovil = #{telefonoMovil} where dni= #{Dni}")
	@Options(flushCache=true,useCache=true)
	public void updatePersona(Persona persona) throws Exception;
	*/
	//FIN JPISCOYA
}

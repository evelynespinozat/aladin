package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Documento;


public interface DocumentoMapper {
	
	@Select("select * from t_documento")
	public List<Documento> findAll() throws Exception;
	
	
	@Select("SELECT IDENT_CURRENT('t_documento')")
	public Integer findLast() throws Exception;

	
	@Select("select * from t_documento where documentoId = #{documentoId}")
	public Documento findById(@Param("documentoId") Integer idDocumento) throws Exception;
	
	@Select("select * from t_documento where IdPiloto = #{pilotoId}")
	public List<Documento> findByPilotoId(@Param("pilotoId") Integer idPiloto) throws Exception;
	
	@Select("select * from t_documento where IdTerramoza = #{terramozaId}")
	public List<Documento> findByTerramozaId(@Param("terramozaId") Integer idTerramoza) throws Exception;
	
	
	@Delete("delete  from t_documento  where documentoId = #{documentoId}")
	@Options(flushCache=true)
	public void eliminarDocumento(@Param("documentoId") Integer documentoId) throws Exception;
	
	@Insert("insert into t_documento (nombre,observacion,imagen,IdPiloto,rutaImagen,fechaCaducidad,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nombre},#{observacion},#{imagen},#{IdPiloto},#{rutaImagen},#{fechaCaducidad},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion})")
	public void crearDocumento_Piloto(Documento documento) throws Exception;
	
	@Insert("insert into t_documento (nombre,observacion,imagen,IdTerramoza,rutaImagen,fechaCaducidad,FechaUltimaActualizacion,UsuarioUltimaActualizacion) values (#{nombre},#{observacion},#{imagen},#{IdTerramoza},#{rutaImagen},#{fechaCaducidad},#{FechaUltimaActualizacion},#{UsuarioUltimaActualizacion})")
	public void crearDocumento_Terramoza(Documento documento) throws Exception;
	
	
	@Update("update t_documento set DNI = #{dNI},ApellidoPaterno=#{apellidoPaterno},ApellidoMaterno=#{apellidoMaterno} ,Nombres=#{nombres}, Licencia =#{licencia},FNacimiento = #{fNacimiento},CodigoAnterior = #{codigoAnterior},Telefono = #{telefono},Estado = #{estado},Fingreso = #{fingreso},Residencia = #{residencia},Cargo = #{cargo},Terminal = #{terminal},Ocupacion = #{ocupacion},GrdoInstruccion = #{grdoInstruccion},NroCell= #{nroCell},Correo= #{correo},Imagen = #{imagen} where IdPiloto= #{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarDocumento(Documento documento) throws Exception;


}

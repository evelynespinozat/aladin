package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.TipoDocumento;

public interface TipoDocumentoMapper {

	
	@Select("select * from dbo.t_tipodocumento ")
	public List<TipoDocumento> findAll() throws Exception;
	
	@Select("select * from dbo.t_tipodocumento")
	public List<TipoDocumento> listaTipDocActivos() throws Exception;
	
	@Select("select * from t_tipodocumento where idtipodoc=#{idtipodoc}")
	public TipoDocumento findById(@Param("idtipodoc") Integer idtipodoc) throws Exception;
	
	public void registrarTipoDocumento(TipoDocumento tipoDocumento) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarTipoDocumento(TipoDocumento tipoDocumento) throws Exception;
	
	@Delete("delete from dbo.t_tipodocumento where idtipodoc=#{idtipodoc}")
	public void eliminarTipoDocumento(@Param("idtipodoc") Integer idtipodoc) throws Exception;
	
}

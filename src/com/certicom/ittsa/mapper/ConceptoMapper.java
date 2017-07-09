package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Concepto;

public interface ConceptoMapper {

	
	@Select("select * from dbo.t_concepto  order by Descripcion asc")
	public List<Concepto> findAll() throws Exception;
	
	@Select("select * from t_concepto where IdConcepto=#{p_IdConcepto}")
	public Concepto findById(@Param("p_IdConcepto") Integer IdConcepto) throws Exception;
	
	public void registrarConcepto(Concepto concepto) throws Exception;
	
	@Options(flushCache=true,useCache=true)
	public void actualizarConcepto(Concepto concepto) throws Exception;
	
	@Delete("delete from dbo.t_concepto where IdConcepto=#{p_IdConcepto}")
	public void eliminarConcepto(@Param("p_IdConcepto") Integer IdConcepto) throws Exception;
	
}

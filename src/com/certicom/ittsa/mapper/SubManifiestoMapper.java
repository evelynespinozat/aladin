package com.certicom.ittsa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.SubManifiesto;

public interface SubManifiestoMapper {

	@Insert("insert into t_submanifiesto (idProgramacion, fRegistro, usucrea) values (#{idProgramacion},#{fRegistro},#{usucrea})")
	public void registrarSubManifiesto(SubManifiesto subManifiesto) throws Exception;
	
	@Select("select MAX(idSubmanifiesto) from t_submanifiesto")
	public Integer lastSubManifiesto();
	
}

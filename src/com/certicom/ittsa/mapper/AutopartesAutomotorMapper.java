package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.AutoparteAutomotor;

public interface AutopartesAutomotorMapper {

	@Delete("delete  from t_autopartesautomotor  where IdParteAutomotor = #{idParteAutomotor}")
	@Options(flushCache=true)
	public void eliminarAutopartesAutomotor(@Param("idParteAutomotor") Integer idParteAutomotor) throws Exception;
	
	@Insert("insert into t_autopartesautomotor (IdAutomotor, IdAutoparte) values (#{idAutomotor},#{idAutoparte})")
	public void crearAutopartesAutomotor(AutoparteAutomotor agencia) throws Exception;
	
	@Select("select pa.IdParteAutomotor, pa.IdAutomotor, pa.IdAutoparte, p.Descripcion as desPartes, p.KmCambio as desKm from t_autopartesautomotor pa inner join t_autoparte p on pa.IdAutoparte = p.IdAutoparte where pa.IdAutomotor = #{idAutomotor} ")
	public List<AutoparteAutomotor> listaAutopartesxAutomotor(@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
	@Select("select count(*) from t_autopartesautomotor where IdAutomotor = #{idAutomotor} and IdAutoparte = #{idAutoparte}")
	public int verificarAutopartesxAutomotor(@Param("idAutomotor")Integer idAutomotor,@Param("idAutoparte") Integer idAutoparte)throws Exception;
}

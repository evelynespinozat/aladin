package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.domain.FlotaAutoparte;

public interface FlotaAutomotorMapper {


	@Delete("delete  from t_flotaautomotor  where IdFlotaAutomotor = #{idFlotaAutomotor}")
	@Options(flushCache=true)
	public void eliminarFlotaAutomotor(@Param("idFlotaAutomotor") Integer idFlotaAutomotor) throws Exception;
	
	@Insert("insert into t_flotaautomotor (IdBus, IdAutomotor) values (#{idBus},#{idAutomotor})")
	public void crearFlotaAutomotor(FlotaAutomotor flotaAutomotor) throws Exception;
	
	@Select("select f.IdFlotaAutomotor, f.IdBus, f.IdAutomotor, a.Descripcion as desAutomotor "+
			"from t_flotaautomotor f inner join t_automotor a on f.IdAutomotor = a.IdAutomotor "+
			"where f.IdBus = #{idBus}")
	public List<FlotaAutomotor> listaAutomotorxFlota(Integer idBus) throws Exception;
	
	@Select("select count(*) from t_flotaautomotor where IdBus = #{idBus} and IdAutomotor = #{idAutomotor}")
	public int verificarSistema(@Param("idBus") Integer idBus,@Param("idAutomotor") Integer idAutomotor) throws Exception;
	
}

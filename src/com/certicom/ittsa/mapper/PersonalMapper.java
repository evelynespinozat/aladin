package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import org.apache.ibatis.mapping.StatementType;

import com.certicom.ittsa.domain.Personal;

public interface PersonalMapper {
	
	@Select("select * from t_personal ")
	public List<Personal> findAll()throws Exception;

	@Select("select * from t_personal where estado = 1 order by appaterno asc ,apmaterno asc, nombres asc")
	public List<Personal> listarPersonalActivo() throws Exception;
	
	@Select("select * from t_personal where idPersonal = #{idPersonal}")
	public Personal findById(@Param("idPersonal") Integer idPersonal) throws Exception;
	
	@Insert("insert into t_personal (appaterno,apmaterno,nombres,dni,tipo,estado) values (#{appaterno},#{apmaterno},#{nombres},#{dni},#{tipo},#{estado}) ")
	public void crearPersonal(Personal personal)throws Exception;
	
	@Update("update t_personal set appaterno=#{appaterno}, apmaterno=#{apmaterno}, nombres=#{nombres}, dni=#{dni}, tipo=#{tipo}, estado=#{estado} where idPersonal = #{idPersonal}")
	public void actualizarPersonal(Personal personal)throws Exception;
	
	@Delete("delete from t_personal where idPersonal = #{idPersonal} ")
	@Options(flushCache=true)
	public void deletePersonal(@Param("idPersonal")Integer idPersonal)throws Exception;
	
	

}

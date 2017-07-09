package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.certicom.ittsa.domain.Mecanico;

public interface MecanicoMapper {

	
	@Select("select * from t_mecanico order by nombre asc")
	public List<Mecanico> findAll() throws Exception;

	@Select("select * from t_mecanico where IdMecanico = #{idMecanico}")
	public Mecanico findById(@Param("idMecanico") Integer idMecanico) throws Exception;
	
	@Delete("delete  from t_mecanico  where IdMecanico = #{idMecanico}")
	@Options(flushCache=true)
	public void eliminarMecanico(@Param("idMecanico") Integer idMecanico) throws Exception;
	
	@Insert("insert into t_mecanico (Nombre, ApePaterno, ApeMaterno, Edad, Direccion, Especialidad, Dni, Estado) values (#{nombre},#{apePaterno},#{apeMaterno},#{edad},#{direccion},#{especialidad},#{dni},#{estado})")
	public void crearMecanico(Mecanico mecanico) throws Exception;
	
	@Update("update t_mecanico set Nombre = #{nombre}, ApePaterno=#{apePaterno}, ApeMaterno=#{apeMaterno}, Edad =#{edad}, Direccion = #{direccion}, Dni = #{dni}, Especialidad = #{especialidad}, Estado = #{estado} where IdMecanico= #{idMecanico}")
	@Options(flushCache=true,useCache=true)
    public void actualizarMecanico(Mecanico mecanico) throws Exception;
	
	@Select("select * from t_mecanico where Estado = 1 order by nombre asc")
	public List<Mecanico> listaMecanicosActivas() throws Exception;
	
}

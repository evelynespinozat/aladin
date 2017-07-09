package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Embarque;

public interface EmbarqueMapper {

	
	@Select("select * from t_incidenciaembarque order by descripcion asc")
	public List<Embarque> findAll() throws Exception;
	

	@Select("select * from t_incidenciaembarque where embarqueId = #{embarqueId}")
	public Embarque findById(@Param("embarqueId") Integer embarqueId) throws Exception;
	
	
	@Delete("delete  from t_incidenciaembarque  where embarqueId = #{embarqueId}")
	@Options(flushCache=true)
	public void eliminarEmbarque(@Param("embarqueId") Integer embarqueId) throws Exception;
	//INICIO PISCOYA
	@Insert("insert into t_incidenciaembarque (descripcion,nombrePasajero,apellidoPasajero,dniPasajero,fIncidencia,oficina) values (#{descripcion},#{nombrePasajero},#{apellidoPasajero},#{dniPasajero},#{fIncidencia},#{oficina})")
	public void crearEmbarque(Embarque embarque) throws Exception;
	
	@Update("update t_incidenciaembarque set descripcion = #{descripcion},nombrePasajero = #{nombrePasajero},apellidoPasajero = #{apellidoPasajero},dniPasajero = #{dniPasajero},fIncidencia = #{fIncidencia},oficina = #{oficina} where embarqueId= #{embarqueId}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEmbarque(Embarque embarque) throws Exception;
	//FIN PISCOYA
	
	
	
}

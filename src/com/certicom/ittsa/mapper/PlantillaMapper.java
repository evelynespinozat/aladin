package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.Plantilla;

public interface PlantillaMapper {


	public List<Plantilla> findAll() throws Exception;

	@Select("select * from t_plantilla where IdPlantilla = #{IdPlantilla}")
	public Plantilla findById(@Param("IdPlantilla") Integer IdPlantilla) throws Exception;
	
	@Delete("delete  from t_plantilla  where IdPlantilla = #{IdPlantilla}")
	@Options(flushCache=true)
	public void eliminarPlantilla(@Param("IdPlantilla") Integer IdPlantilla) throws Exception;
	
	public void crearPlantilla(Plantilla agencia) throws Exception;
	
	@Options(flushCache=true,useCache=true)
    public void actualizarPlantilla(Plantilla agencia) throws Exception;

	public Integer cantProdxPlantilla(Integer idPlantilla);
	
	
	
}

package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.PlantillaDetalle;

public interface PlantillaDetalleMapper {
	
	public void registarPlantillaDetalle(PlantillaDetalle plantillaDetalle);
	
	public List<PlantillaDetalle>  listarPlantillaDetalle(Integer IdPlantilla);
	
	@Delete("delete from t_plantilla_detalle  where IdPlanDet = #{IdPlanDet}")
	public void eliminarPlantillaDetalle(Integer IdPlanDet);
	
	public Integer cantProductoxPlantilla(PlantillaDetalle plantillaDetalle);
	
	public List<PlantillaDetalle>  listarProductosxPlantilla(PlantillaDetalle plantillaDetalle);
	
	@Update("update t_plantilla_detalle set cantDefecto = #{cantDefecto} where IdPlanDet = #{IdPlanDet} ")
	@Options(flushCache=true,useCache=true)
	public void actualizarCantidad(@Param("IdPlanDet")Integer IdPlanDet,@Param("cantDefecto")Integer cantDefecto );
}

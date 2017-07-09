package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.SalidaDetalle;

public interface SalidaDetalleMapper {
	
	public void registrarSalidaDetalle(SalidaDetalle salidaDetalle);
	
	@Select("select s.IdSalida, sd.IDSalDet, s.IdProgramacion, sd.IdProducto, sd.cantSalida, "
			+ "(select p.Descripcion from t_producto p where p.IdProducto = sd.IdProducto) as desProducto, "
			+ "(select p.Medida from t_producto p where p.IdProducto = sd.IdProducto) as Medida,"
			+ "sd.costoUni, sd.costo, sd.cantDist, sd.costoxPasj ,sd.disgregacion "
			+ "from t_salida s inner join t_salida_detalle sd on s.IdSalida = sd.IdSalida where  s.IdProgramacion =#{IdProgramacion}")
	public List<SalidaDetalle> listLLegadaProductosxProg(@Param("IdProgramacion")Integer IdProgramacion);
	
	
	@Update("update t_salida_detalle set flagEntrega=#{flagEntrega},cantConsumida=#{cantConsumida} where  IDSalDet =#{IDSalDet} ")
	@Options(flushCache=true,useCache=true)
	public void actualizarEstadoEntrega(@Param("IDSalDet")Integer IDSalDet,@Param("flagEntrega") String flagEntrega,@Param("cantConsumida")Integer cantConsumida);
	
	
	@Select("select s.IdSalida,	sd.IDSalDet, p.IdProgramacion, pr.IdProducto, pr.Descripcion as desProducto, "
			+ "pr.Costo as precioCosto, pr.CostoUni as precioUni, (pr.Costo/b.Asientos) as costoIndiviual,pr.desgregacion,"
			+ "pr.cantDist, pr.costoxPasj  "
			+ "from t_salida_detalle sd inner join t_salida s on sd.IdSalida = s.IdSalida inner join t_programacion p "
			+ " on s.IdProgramacion = p.IdProgramacion inner join t_producto pr on pr.IdProducto = sd.IdProducto "
			+ "inner join t_flota b on b.IdBus = p.IdBus "
			+ "where s.IdSalida = #{IdSalida}")
	public List<SalidaDetalle> listaProductosxSalida(@Param("IdSalida")Integer IdSalida);
	

}

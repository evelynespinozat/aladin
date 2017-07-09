package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.SalidaAutoparte;

public interface SalidaAutoparteMapper {

	@Insert("insert into t_salidaautoparte (IdBus, IdMecanico, IdAutoparte, Cantidad, IdOficina, IdAgencia, Estado, FecPedido, TipoMantenimiento, Accion, IdAlmacen) "
			+ " values (#{idBus},#{idMecanico},#{idAutoparte},#{cantidad},#{idOficina},#{idAgencia},#{estado},#{fecPedido},#{tipoMantenimiento},#{accion},#{idAlmacen})")
	public void crearSalidaAutoparte(SalidaAutoparte salidaAutoparte) throws Exception;
	
	public List<SalidaAutoparte> listarAutopartePendientes(SalidaAutoparte salidaAutoparte) throws Exception;
	
	@Update("update t_salidaautoparte set Estado =#{estado} where IdSalidaAutoparte =#{idSalidaAutoparte}")
	@Options(flushCache=true)
	public void actualizarEstado(@Param("idSalidaAutoparte") Integer IdSalidaAutoparte,@Param("estado") Integer Estado ) throws Exception;

	
	@Select("select sa.IdSalidaAutoparte as idSalidaAutoparte,f.IdBus as idBus,f.Numero as nroBus,a.IdAutoparte as idAutoparte, a.Valorizacion as costo, "
			+ "sa.Cantidad as cantidad, a.Descripcion as descAutoparte, sa.FecPedido as fecPedido, sa.TipoMantenimiento as tipoMantenimiento, "
			+ "(sa.Cantidad * a.Valorizacion) as subtotal from 	t_salidaautoparte sa inner join t_flota f on sa.IdBus = f.IdBus inner join t_autoparte a "
			+ "	on sa.IdAutoparte = a.IdAutoparte where sa.IdBus = #{idBus} and sa.Estado = 2 and sa.FecPedido >=#{fecIni} and sa.FecPedido <= #{fecFin} ")
	public List<SalidaAutoparte> listarCostosxBus(@Param("fecIni")Date fecIni,@Param("fecFin") Date fecFin, @Param("idBus")Integer idBus);
	
}

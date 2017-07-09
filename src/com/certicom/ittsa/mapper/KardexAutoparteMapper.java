package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.KardexAutoparte;


public interface KardexAutoparteMapper {

	public void registrarKardex(KardexAutoparte kardex);
	
	public void deleteKardexbyProducto(KardexAutoparte kardex);

	public List<KardexAutoparte> getCantKardexxProducto(KardexAutoparte kardex);
	
	public List<KardexAutoparte> listarKardexporProducto(AgenciaAutoparte ap);
	
	@Select("select p.IdBus as idBus,f.Numero as numBus, k.IdProducto as idProducto, o.Descripcion as desProducto, " + 
				"( " +
				"select sum(d.cantConsumida) " +  
				"from ((t_salida_detalle d inner join t_salida s2 on d.IdSalida = s2.IdSalida) " + 
										 "inner join t_programacion x on s2.IdProgramacion = x.IdProgramacion) " + 
										 "inner join t_kardex x2 on s2.IdProgramacion = x2.IdProgramacion " +
				"where d.IdProducto = 2 and  x2.Salida > 0 and x2.FRegistro >= #{fecInicial} " +
									   "and x2.FRegistro <= #{fecFinal} and x2.IdProducto = #{idProducto} " + 
									   "and x.IdBus = p.IdBus " +
				") as cantConsumida " +
				"from ((((t_salida_detalle d inner join t_salida s on d.IdSalida = s.IdSalida) " + 
										 "inner join t_programacion p on s.IdProgramacion = p.IdProgramacion) " + 
										 "inner join t_kardex k on s.IdProgramacion = k.IdProgramacion) " +
										 "inner join t_flota f on f.IdBus = p.IdBus) " +
										 "inner join t_producto o on o.IdProducto = k.IdProducto " +    
				"where d.IdProducto = 2 and  k.Salida > 0 and k.FRegistro >= #{fecInicial} " +
									   "and k.FRegistro <= #{fecFinal} and k.IdProducto = #{idProducto} " + 
				"group by p.IdBus, f.Numero, k.IdProducto, o.Descripcion " +
				"order by f.Numero ")
	public List<KardexAutoparte> obtenerListaProductosxFecha(@Param("fecInicial") Date fecInicial,@Param("fecFinal") Date fecFinal,@Param("idProducto") Integer idProducto);
	
}

package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.ConsumoCombustible;

public interface ConsumoCombustibleMapper {

	
	@Select("select * from t_consumocombustible order by FecRegistro asc")
	public List<ConsumoCombustible> findAll() throws Exception;
	

	@Select("select * from t_consumocombustible where IdConCombus = #{idConCombus}")
	public ConsumoCombustible findById(@Param("idConCombus") Integer idConCombus) throws Exception;
	
	@Delete("delete  from t_consumocombustible  where IdConCombus = #{idConCombus}")
	@Options(flushCache=true)
	public void eliminarConsumoCombustible(@Param("idConCombus") Integer idConCombus) throws Exception;
	
	@Insert("insert into t_consumocombustible (IdProgramacion, KmInicial, KmFinal, KmRecorridos, Consumo, KmGalon, Idagencia, PrecioComb, CostoTotal, CostoKm, IdPiloto, IdCopiloto, IdBus, Activar, FecRegKilome) values (#{idProgramacion},#{kmInicial},#{kmFinal},#{kmRecorridos},#{consumo},#{kmGalon},#{idagencia},#{precioComb},#{costoTotal},#{costoKm},#{idPiloto},#{idCopiloto},#{idBus},#{activar},#{fecRegKilome})")
	public void crearConsumoCombustible(ConsumoCombustible agencia) throws Exception;
	
	@Update("update t_consumocombustible set FecRegCombus=#{fecRegCombus}, Consumo = #{consumo}, KmGalon = #{kmGalon}, CostoTotal =#{costoTotal}, CostoKm = #{costoKm},IdPiloto = #{idPiloto},IdCopiloto = #{idCopiloto},Activar = #{activar}, IdOrigenComb= #{idOrigenComb} where IdConCombus= #{idConCombus}")
	@Options(flushCache=true,useCache=true)
    public void actualizarConsumoCombustible(ConsumoCombustible consumoCombustible) throws Exception;
	
	@Select("select COUNT(*) from t_consumocombustible where IdProgramacion = #{idProgramacion}")
	public int verificarProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Select("select cc.IdConCombus, cc.IdProgramacion, cc.FecRegKilome, " +
			   "cc.KmInicial, cc.KmFinal, cc.KmRecorridos, cc.Consumo, " +
			   "cc.KmGalon, cc.Idagencia, cc.PrecioComb, cc.CostoTotal, cc.Activar, " +
			   "cc.CostoKm, cc.IdPiloto, cc.IdCopiloto, s.Descripcion as desServicio, " + 
			   "pt.Nombres +' '+ pt.ApellidoPaterno + ' '+pt.ApellidoMaterno as piloto1, " +
			   "f.Numero as numBus, pt2.Nombres +' '+ pt2.ApellidoPaterno + ' '+pt2.ApellidoMaterno as piloto2, " +
			   "a.Descripcion as desAgen " +
		"from (((((t_consumocombustible cc inner join t_programacion p on cc.IdProgramacion = p.IdProgramacion) " + 
									  " inner join t_servicio s on p.IdServicio = s.IdServicio) " +
									  " inner join t_piloto pt on cc.IdPiloto = pt.IdPiloto) " +
									  " inner join t_flota f on cc.IdBus = f.IdBus) " +
									  " inner join t_piloto pt2 on cc.IdCopiloto = pt2.IdPiloto) " + 
									  " inner join t_agencia a on cc.Idagencia = a.Idagencia " +
		"where cc.IdBus = #{idBus} " +
		"order by cc.FecRegKilome desc")
	public List<ConsumoCombustible> listarProgramacionescnKilometro(@Param("idBus") Integer idBus) throws Exception;
	
	@Select("select cc.IdConCombus, cc.IdProgramacion, cc.fecRegKilome, cc.fecRegCombus, " +  
			   "cc.KmInicial, cc.KmFinal, cc.KmRecorridos, cc.Consumo, " + 
			   "cc.KmGalon, cc.Idagencia, cc.PrecioComb, cc.CostoTotal, cc.Activar, " +  
			   "cc.CostoKm, cc.IdPiloto, cc.IdCopiloto, s.Descripcion as desServicio, " +   
			   "pt.Nombres +' '+ pt.ApellidoPaterno  +' '+pt.ApellidoMaterno as piloto1, " +  
			   "f.Numero as numBus, pt2.Nombres +' '+ pt2.ApellidoPaterno  +' '+pt2.ApellidoMaterno as piloto2, " +  
			   "a.Descripcion as desAgen,  " +
			   "a2.Descripcion as desAgenRecarga "+
		"from ((((((t_consumocombustible cc inner join t_programacion p on cc.IdProgramacion = p.IdProgramacion) " +   
									   "inner join t_servicio s on p.IdServicio = s.IdServicio) " + 
									   "inner join t_piloto pt on cc.IdPiloto = pt.IdPiloto) " +  
									   "inner join t_flota f on cc.IdBus = f.IdBus) " + 
									   "inner join t_piloto pt2 on cc.IdCopiloto = pt2.IdPiloto) " +   
									   "inner join t_agencia a on cc.Idagencia = a.Idagencia) " + 
									   "left join t_agencia a2 on cc.idOrigenComb = a2.Idagencia " +
		"where cc.IdBus = #{idBus} " +
				"and cc.FecRegCombus >= #{fecInicio} and cc.FecRegCombus <= #{fecFin} " +   
				"order by cc.FecRegKilome desc ")
	public List<ConsumoCombustible> listarConsumoCombustiblexFechas(@Param("fecInicio") Date fecInicio,@Param("fecFin") Date fecFin,@Param("idBus") Integer idBus);
}

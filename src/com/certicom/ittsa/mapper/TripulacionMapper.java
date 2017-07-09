package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Tripulacion;

public interface TripulacionMapper {

	
	@Select("select * from t_tripulacion order by IdTripulacion asc")
	public List<Tripulacion> findAll() throws Exception;
	

	@Select("select * from t_tripulacion where IdTripulacion = #{p_idTripulacion}")
	public Tripulacion findById(@Param("p_idTripulacion") Integer idTripulacion) throws Exception;
	
	@Select("select * from t_tripulacion where IdProgramacion = #{p_idProgramacion}")
	public Tripulacion findByIdProgramacion(@Param("p_idProgramacion") Integer idProgramacion) throws Exception;
	
	@Delete("delete  from t_tripulacion  where IdTripulacion = #{p_idTripulacion}")
	@Options(flushCache=true)
	public void eliminarTripulacion(@Param("p_idTripulacion") Integer p_idTripulacion) throws Exception;
	
	@Insert("insert into t_tripulacion (IdProgramacion, IdBus, IdPiloto, IdCopiloto, IdTerramoza, FRegistro,Tipo,Estado,IdTerramoza2) values (#{idProgramacion},#{idBus},#{idPiloto},#{idCopiloto},#{idTerramoza},#{fRegistro},#{tipo},#{estado},#{idTerramoza2})")
	public void crearTripulacion(Tripulacion tripulacion) throws Exception;
	
	
	@Update("update t_tripulacion set IdBus = #{p_idBus} where IdTripulacion= #{p_idTripulacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarIdBus(@Param("p_idTripulacion") Integer p_idTripulacion, @Param("p_idBus") Integer p_idBus) throws Exception;
	
	// 28-02-2014
	@Update("update t_tripulacion set IdPiloto = #{p_idPiloto}, flagTempPiloto = #{flag} where IdTripulacion= #{p_idTripulacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarIdPiloto(@Param("p_idTripulacion") Integer p_idTripulacion, @Param("p_idPiloto") Integer p_idPiloto,@Param("flag") boolean flag) throws Exception;
	
	// 28-02-2014
	@Update("update t_tripulacion set IdCopiloto = #{p_idCopiloto}, flagTempCopiloto=#{flag} where IdTripulacion= #{p_idTripulacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarIdCopiloto(@Param("p_idTripulacion") Integer p_idTripulacion, @Param("p_idCopiloto") Integer p_idPiloto,@Param("flag") boolean flag) throws Exception;
	
	@Update("update t_tripulacion set IdTerramoza = #{IdTerramoza} where IdTripulacion= #{IdTripulacion}")
	@Options(flushCache=true,useCache=true)
	public void actualizarTerramoza1(@Param("IdTripulacion")Integer IdTripulacion,@Param("IdTerramoza") Integer IdTerramoza)
			throws Exception;

	@Update("update t_tripulacion set IdTerramoza2 = #{IdTerramoza2} where IdTripulacion= #{IdTripulacion}")
	@Options(flushCache=true,useCache=true)
	public void actualizarTerramoza2(@Param("IdTripulacion")Integer IdTripulacion,@Param("IdTerramoza2") Integer IdTerramoza2) throws Exception;
	
	@Delete("delete from t_tripulacion where IdProgramacion = #{idprogramacion} ")
	@Options(flushCache=true,useCache=true)
	public void eliminarTripulacionXProgramacion(@Param("idprogramacion")Integer idprogramacion)throws Exception;
	
	
	@Select("select (p.Nombres + ' ' + p.ApellidoPaterno + ' ' + p.ApellidoMaterno) as piloto , tr.idPiloto, p.DNI as dnipiloto,p.Licencia as licPiloto,  "
			+ 	"(cp.Nombres +  ' '  + cp.ApellidoPaterno + ' ' +cp.ApellidoMaterno) as copiloto, cp.IdPiloto as idCopiloto, cp.DNI as dnicopiloto,cp.Licencia as licCopiloto, "
			+   "(t1.Nombres +  ' ' +  t1.Apellidos) as terramoza1,tr.idTerramoza ,t1.DNI as dniterramoza1,(t2.Nombres + ' '+  t2.Apellidos) as terramoza2, tr.idTerramoza2, "
			+  "t2.DNI as dniterramoza2, b.Numero as numeroBus, b.Placa as placabus, b.Modelo as modeloBus, b.Marca as marcaBus "
			+ " from ((((t_tripulacion tr left join t_piloto p on p.IdPiloto = tr.IdPiloto) left join t_piloto cp on cp.IdPiloto = tr.IdCopiloto ) "
			+ "	left join t_terramoza t1 on t1.IdTerramoza = tr.IdTerramoza) left join t_terramoza t2 on t2.IdTerramoza = tr.IdTerramoza2) "
			+ "	inner join t_flota b on tr.IdBus = b.IdBus 	  where IdProgramacion = #{idprogramacion} ")
	public Tripulacion listarTripulacionxProg(@Param("idprogramacion")Integer idprogramacion) throws Exception;
	
}

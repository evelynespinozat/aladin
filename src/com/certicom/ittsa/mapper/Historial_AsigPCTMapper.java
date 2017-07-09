package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Oficina;

public interface Historial_AsigPCTMapper {

	
	@Select("select * from t_historial_asigPCT order by fecha desc")
	public List<Historial_AsigPCT> findAll() throws Exception;
	

	@Select("select * from t_historial_asigPCT where idHistorial = #{p_idHistorial}")
	public Historial_AsigPCT findById(@Param("p_idHistorial") Integer idHistorial) throws Exception;
	
	@Select("select * from t_historial_asigPCT where IdBus=#{p_idBus} and  fecha between #{p_fInicio} and #{p_fFin} order by fecha desc")
	public List<Historial_AsigPCT> findHistorialByFechas(@Param("p_idBus") Integer idBus, @Param("p_fInicio") Date fInicio, @Param("p_fFin") Date fFin) throws Exception;
	
	@Select("select * from t_historial_asigPCT where (idPiloto=#{p_idPiloto} or idCopiloto=#{p_idPiloto}) and  fecha between #{p_fInicio} and #{p_fFin} order by fecha desc")
	public List<Historial_AsigPCT> findHistorialPilotoByFechas(@Param("p_idPiloto") Integer idPiloto, @Param("p_fInicio") Date fInicio, @Param("p_fFin") Date fFin) throws Exception;
	
	@Delete("delete  from t_historial_asigPCT  where idHistorial = #{p_idHistorial}")
	@Options(flushCache=true)
	public void eliminarHistorial(@Param("p_idHistorial") Integer idHistorial) throws Exception;
	
	@Insert("insert into t_historial_asigPCT (idBus,fecha,idPiloto,estado) values (#{idBus},#{fecha},#{idPiloto},#{estado})")
	public void crearHistorial_Piloto (Historial_AsigPCT historial) throws Exception;
	
	@Insert("insert into t_historial_asigPCT (idBus,fecha,idCopiloto,estado) values (#{idBus},#{fecha},#{idCopiloto},#{estado})")
	public void crearHistorial_Copiloto (Historial_AsigPCT historial) throws Exception;
	
	
	@Update("update t_historial_asigPCT set idBus = #{idBus},fecha=#{fecha} , idPiloto=#{idPiloto}, idCopiloto =#{idCopiloto}, estado = #{estado}, idTerramoza = #{idTerramoza} where idHistorial= #{idHistorial}")
	//@Update("update t_cliente set razonsocial_cliente=#{razonsocial_cliente},direccion_cliente=#{direccion_cliente} ,contacto_cliente=#{direccion_cliente},telefono_cliente=#{telefono_cliente}")
	@Options(flushCache=true,useCache=true)
    public void actualizarHistorial(Historial_AsigPCT historial) throws Exception;
	
	
	@Update("update t_historial_asigPCT set estado = #{estado} where idPiloto=#{idPiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstadoHistorial_Piloto(Historial_AsigPCT historial) throws Exception;
	
	@Update("update t_historial_asigPCT set estado = #{estado} where idCopiloto=#{idCopiloto}")
	@Options(flushCache=true,useCache=true)
    public void actualizarEstadoHistorial_Copiloto(Historial_AsigPCT historial) throws Exception;
	
	
	
	
	/*
	@Select("select * from t_historial_asigPCT where Estado = 1 order by descripcion asc")
	public List<Agencia> listaAgenciasActivas() throws Exception;
	*/
	
}

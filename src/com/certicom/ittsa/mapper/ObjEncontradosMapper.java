package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.certicom.ittsa.domain.ObjEncontrados;

public interface ObjEncontradosMapper {

	
	@Select("select * from t_objetosencontrados ")
	public List<ObjEncontrados> findAll() throws Exception;
	
	@Select("select * from t_objetosencontrados where IdProgramacion = #{IdProgramacion} and estado ='NO DEVUELTO' ")
	public List<ObjEncontrados> findByProgramacion(@Param("IdProgramacion")Integer IdProgramacion);
	

	@Select("select * from t_objetosencontrados where idObjeto = #{idObjeto}")
	public ObjEncontrados findById(@Param("idObjeto") Integer idObjeto) throws Exception;
	
	
	@Select("select * from t_objetosencontrados where descripcion = #{p_descripcion}")
	public ObjEncontrados findByDescripcion(@Param("p_descripcion") String Descripcion) throws Exception;
	
	
	@Delete("delete  from t_objetosencontrados  where idObjeto = #{idObjeto}")
	@Options(flushCache=true)
	public void eliminarObjEncontrados(@Param("idObjeto") Integer idObjeto) throws Exception;
	
	public void crearObjEncontrados(ObjEncontrados ObjEncontrados) throws Exception;
	
	@Options(flushCache=true,useCache=true)
    public void actualizarObjEncontrados(ObjEncontrados ObjEncontrados) throws Exception;
	
	@Select("select o.idObjeto, o.descripcion, o.nroAsiento, o.dniEntregado, o.nomEntregado, o.fechaEntrega, o.estado, p.HSalida as hSalida, p.FSalida as fSalida, "
			+ "f.Numero as numero, o.fRegistro, p.IdProgramacion, s.Descripcion as desServicio, p.FLlegadaReal as fLlegadaReal "+   
	  	    "from t_objetosencontrados o inner join t_programacion p on o.IdProgramacion = p.IdProgramacion "+ 
	  		"						inner join t_flota f on p.IdBus = f.IdBus "
	  		+ " inner join t_servicio s on s.IdServicio = p.IdServicio "+
	  	    "where p.Origen =  #{idOrigen} and p.Destino = #{idDestino} order by o.fRegistro desc ")
	public  List<ObjEncontrados> listarObjetosEncontrados(ObjEncontrados objEncontrados)throws Exception;
	
}

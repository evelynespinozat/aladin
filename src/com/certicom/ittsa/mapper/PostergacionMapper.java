package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;


import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Postergacion;

public interface PostergacionMapper {
	
	
	@Insert("insert into t_postergado (idasientoventa, fechapostergacion, fechacaducidad, estado, documentopersona,serie,fecharegistro,usuarioid,fechaAbierta,monto,origen,destino,servicioid) values (#{idasientoventa},#{fechapostergacion},#{fechacaducidad},#{estado},#{documentopersona},#{serie},getdate(),#{usuarioid},#{fechaAbierta},#{monto},#{origen},#{destino},#{servicioid})")
	public void crearPostergacion(Postergacion postergacion) throws Exception;
	
	
	@Select("select pos.idpostergado,pos.documentopersona,pos.idasientoventa,pos.fechaAbierta,(select ago.Descripcion from t_agencia ago where ago.Idagencia = pr.Origen ) as origenstr,(select agd.Descripcion from t_agencia agd where agd.Idagencia = pr.Destino ) as destinostr,pos.monto,srv.Descripcion as serviciostr,pos.fechapostergacion,pos.fechacaducidad,pos.estado from t_postergado pos, t_asientoventa vnt,t_programacion pr,t_servicio srv where pos.documentopersona = #{p_nroDoc} and pos.idasientoventa = vnt.idasientoventa and vnt.IdProgramacion = pr.IdProgramacion and pr.IdServicio = srv.IdServicio")
	public List<Postergacion> listaPostergacion(@Param("p_nroDoc") String p_nroDoc) throws Exception;
	
	
	@Update("update t_postergado set estado = #{estado},fechaactivacion = getdate() ,usuarioactiva=#{usuarioactiva} where idpostergado = #{idpostergado}")
	@Options(flushCache=true,useCache=true)
    public void actualizarPostergacion(Postergacion postergacion) throws Exception;

	@Select("select p.documentopersona,  p.idpostergado,p.fechapostergacion, CONVERT(VARCHAR(8),p.fechapostergacion,108) as horaPost,"
			+ "pr.FSalida, pr.HSalida, av.numero,av.monto, p.estado, p.fechaAbierta, DATEDIFF(DAY, GETDATE(),p.fechacaducidad) as tiempVencer, "
			+ "p.fechacaducidad,  s.Descripcion  as serviciostr "
			+ "from ((t_postergado p inner join t_asientoventa av on p.idasientoventa = av.idasientoventa) "
			+ "	inner join t_programacion pr on pr.IdProgramacion = av.IdProgramacion) "
			+ " inner join t_servicio s on s.IdServicio = pr.IdServicio "
			+ "where  p.origen = #{origen} and p.destino = #{destino} "
			+ "and av.oficinaId = #{idOficina} and p.fechaAbierta = #{tipoReserva}")
	public List<Postergacion> listaPostergacionxFiltros(Postergacion  filter) throws Exception;

	@Select("select * from t_postergado where idasientoventa=#{IdAsientoVenta}")
	public Postergacion findByIdAsientoVenta(@Param("IdAsientoVenta")Integer idasientoventa);

}

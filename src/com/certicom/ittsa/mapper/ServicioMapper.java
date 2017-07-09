package com.certicom.ittsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;

public interface ServicioMapper {

	@Select("select * from dbo.t_servicio  order by descripcion asc")
	public List<Servicio> findAll() throws Exception;
	
	@Select("select * from dbo.t_servicio where Estado = 1  order by descripcion asc")
	public List<Servicio> listaCServiciosActivos() throws Exception;
	
	@Select("select * from t_servicio where idservicio=#{p_idServicio}")
	public Servicio findById(@Param("p_idServicio") Integer idServicio) throws Exception;

	@Select("select serv.RutaCompartida, serv.IdServicio,serv.IdClase,serv.Descripcion,serv.Precio1,serv.Precio2,(select ag.descripcion from t_agencia ag where ag.idagencia = serv.Origen) as desOrigen,(select ag.descripcion from t_agencia ag where ag.idagencia = serv.Destino) as desDestino,serv.Origen,serv.Destino,serv.HSalida,serv.HorasViaje,serv.HLlegada,serv.Hora24,serv.Estado,serv.TipoFrecuencia,serv.idRutaDestino,serv.FRegistro,serv.FrecuenteSabado,csr.Descripcion DescripcionClase, "
			+ "(select COUNT(*) from t_enlace where IdServicioP = serv.IdServicio) as cantEnlaces, serv.IntCorre "
			+ "from t_servicio serv, t_clase cls,t_categoriaservicio csr "
			+ "where origen = #{p_origen} and destino = #{p_destino} "
			+ "and serv.idclase=cls.idclase "
			+ "and cls.idcatservicio=csr.idcatservicio "
			+ "and serv.GradoServ = 1 "
			+ "order by serv.Hora24 ASC")
	public List<Servicio> findByOrigenDestino(@Param("p_origen") Integer  origen, @Param("p_destino") Integer  destino) throws Exception;

	
	@Insert("insert into t_servicio (idclase,descripcion,precio1,precio2,origen,destino,hsalida,horasviaje,hllegada,hora24,estado,tipofrecuencia,idrutadestino,fregistro,frecuentesabado,RutaCompartida,IntCorre, GradoServ,rutaCompEnCaliente) values (#{IdClase},#{Descripcion},#{Precio1},#{Precio2},#{Origen},#{Destino},#{HSalida},#{HorasViaje},#{HLlegada},#{Hora24},#{Estado},#{TipoFrecuencia},#{idRutaDestino},#{FRegistro},#{FrecuenteSabado},#{rutaCompartida},#{IntCorre},#{GradoServ},#{rutaCompEnCaliente})")
	public void crearServicio(Servicio servicio) throws Exception;
	
	
	
	@Update("update t_servicio set idclase = #{IdClase},descripcion= #{Descripcion}, precio1=#{Precio1},precio2=#{Precio2} ,origen=#{Origen},destino=#{Destino},hsalida=#{HSalida},horasviaje=#{HorasViaje},hllegada=#{HLlegada},hora24=#{Hora24},estado=#{Estado},tipofrecuencia=#{TipoFrecuencia},idrutadestino=#{idRutaDestino},frecuentesabado=#{FrecuenteSabado},RutaCompartida=#{rutaCompartida},IntCorre=#{intCorre},rutaCompEnCaliente=#{rutaCompEnCaliente}  where idservicio= #{IdServicio}")
	@Options(flushCache=true,useCache=true)
	public void actualizarServicio(Servicio servicio) throws Exception;
	
	
	
	
	@Delete("delete from t_servicio where idservicio=#{p_idServicio}")
	public void eliminarServicio(@Param("p_idServicio") Integer idServicio) throws Exception;
	
	@Select("select * from t_servicio s inner join t_clase c on s.IdClase = c.IdClase where s.Origen = #{origen} and s.Destino = #{destino} and s.Estado = 1 and c.IdCatServicio = #{idCatServicio} order by s.Hora24")
	public List<Servicio> serviciosFilter(Programacion programacion) throws Exception;
	
	@Select("SELECT DISTINCT s.Descripcion, s.IdServicio , s.HSalida, s.Hora24, a.Descripcion as desOrigen, a2.Descripcion as desDestino, ca.Descripcion as DescripcionClase FROM (((t_servicio s inner join t_agencia a on s.Origen = a.Idagencia) inner join t_agencia a2 on s.Destino = a2.Idagencia) inner join t_clase c on s.IdClase = c.IdClase) inner join t_categoriaservicio ca on ca.IdCatServicio = c.IdCatServicio WHERE (s.Origen <> #{Origen} AND s.Destino = #{Destino}) AND s.IdClase = #{IdClase} AND s.idRutaDestino = #{idRutaDestino} AND s.Hora24 > (select x.Hora24 from t_servicio x where x.IdServicio = #{IdServicio}) ORDER BY Hora24")
	public List<Servicio> obtenerEnlaces(Servicio servicio) throws Exception;
	
	@Select("select count(*) from t_servicio where IdClase = #{idclase}")
	public int obtenerServicioClase(@Param("idclase") Integer idclase) throws Exception;
	
	@Select("select Descripcion from t_servicio where IdClase = #{idclase}")
	public List<Servicio> obtenerServicioByIdClase(@Param("idclase") Integer idclase) throws Exception;
	
	public List<Servicio> obtenerServicioporClase(@Param("idclase") Integer idclase) throws Exception;
	
	@Select("select srv.IdServicio, srv.idclase,srv.Descripcion,srv.Precio1,srv.precio2,srv.HSalida,srv.HLlegada from t_programacion pr, t_servicio srv,t_clase cls  where  Cast(pr.fsalida as Date) =#{p_fsalida} and pr.origen=#{p_origen}  and pr.destino = #{p_destino} and pr.HSalida = #{p_hsalida} and pr.idservicio = srv.idservicio and srv.idclase = cls.idclase and cls.idcatservicio = 4 and IdProgramacion = #{idProgramacion}")
	public Servicio serviciobyProgramacion(@Param("p_fsalida") String fSalida, @Param("p_origen") Integer origen,@Param("p_destino") Integer destino,@Param("p_hsalida") String hsalida,@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Select("select srv.IdServicio, srv.idclase,srv.Descripcion,srv.Precio1,srv.precio2,srv.HSalida,srv.HLlegada from t_programacion pr, t_servicio srv,t_clase cls  where pr.idservicio = srv.idservicio and srv.idclase = cls.idclase and cls.idcatservicio = 4 and IdProgramacion = #{idProgramacion}")
	public Servicio serviciobyIdProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	//AGREGADO 21-02-2014 JTORRES
	@Select("select * from t_servicio where Origen = #{Origen} and Destino = #{Destino} and IdClase = #{IdClase} and  Estado = 1 order by Hora24 asc ")
	public List<Servicio> getServiciosOriDesClase(@Param("Origen")Integer Origen,@Param("Destino") Integer Destino,@Param("IdClase") Integer IdClase) throws Exception;
		
	@Select("select serv.RutaCompartida, serv.IdServicio,serv.IdClase,serv.Descripcion,serv.Precio1,serv.Precio2,(select ag.descripcion from t_agencia ag where ag.idagencia = serv.Origen) as desOrigen,(select ag.descripcion from t_agencia ag where ag.idagencia = serv.Destino) as desDestino,serv.Origen,serv.Destino,serv.HSalida,serv.HorasViaje,serv.HLlegada,serv.Hora24,serv.Estado,serv.TipoFrecuencia,serv.idRutaDestino,serv.FRegistro,serv.FrecuenteSabado,csr.Descripcion DescripcionClase,serv.IntCorre, "
			+ "(select COUNT(*) from t_enlace where IdServicioP = serv.IdServicio) as cantEnlaces "
			+ "from t_servicio serv, t_clase cls,t_categoriaservicio csr "
			+ "where origen = #{p_origen} and destino = #{p_destino} and serv.RutaCompartida = 'SI' "
			+ "and serv.idclase=cls.idclase and cls.idcatservicio=csr.idcatservicio "
			+ "and serv.GradoServ = 1 "
			+ "order by serv.Hora24 asc ")
	public List<Servicio> findByOrigenDestinoRutaCompartida(@Param("p_origen") Integer  origen, @Param("p_destino") Integer  destino) throws Exception;
	
	@Update("update t_servicio set IntCorre = #{intCorre} where IdServicio= #{IdServicio} ")
	@Options(flushCache=true,useCache=true)
	public void actualizarServicioCorrelativo(Servicio servicio)throws Exception;
	
	@Update("update t_servicio set IntCorre = 0, GradoServ = 1 WHERE IdServicio = #{idServicio} ")
	@Options(flushCache = true, useCache = true)
	public void actualizarServicioPadre(@Param("idServicio")Integer idServicio) throws Exception;
	
	//@Delete("delete from t_servicio where intCorre = (Select IntCorre from t_servicio where IdServicio = #{idServicio}) and GradoServ = 2")
	@Delete("delete from t_servicio where intCorre = #{intCorre} and GradoServ = 2")
	public void eliminarServiciosHijos(@Param("intCorre")Integer intCorre) throws Exception;
	
	@Select("select * from t_servicio where IntCorre = (select s.IntCorre from t_servicio s where s.IdServicio = #{idServicio} ) and GradoServ !=1")
	public List<Servicio> obtenerServiciosHijos(@Param("idServicio")Integer idServicio) throws Exception;
	
	@Select("select * from t_servicio where IntCorre = (select s.IntCorre from t_servicio s where s.IdServicio = #{idServicio} ) and GradoServ = 1")
	public List<Servicio> obtenerServiciosPadre(@Param("idServicio")Integer idServicio) throws Exception;
	
	@Select("SELECT MAX(IdServicio) FROM t_servicio")
	public Integer obtenerIdUltimoRegistro();
	
	public List<Servicio> obtenerServicioporClaseDemanda(@Param("idclase") Integer idclase)throws Exception;
																																																																																	
	@Select("select IdServicio,idclase,(select descripcion from t_clase where idclase=serv.idclase) DescripcionClase,descripcion,origen,(select descripcion from dbo.t_agencia where Idagencia = serv.origen) desOrigen,destino,(select descripcion from dbo.t_agencia where Idagencia = serv.destino) desDestino,hsalida,horasviaje,RutaCompartida,IntCorre,(select descripcion from dbo.t_frecuencia where idfrecuencia=#{p_frecuencia}) TipoFrecuencia, GradoServ from dbo.t_servicio serv   where TipoFrecuencia = #{p_frecuencia} and  RutaCompartida = 'SI' and origen  =#{p_origen} and destino =#{p_destino} and serv.GradoServ = 1;")
	public List<Servicio> listaServiciosPadres(@Param("p_frecuencia") Integer frecuencia ,@Param("p_origen") Integer  origen,@Param("p_destino") Integer  destino) throws Exception;
	
	@Select("select IdServicio,idclase,(select descripcion from t_clase where idclase=serv.idclase) DescripcionClase,descripcion,origen,(select descripcion from dbo.t_agencia where Idagencia = serv.origen) desOrigen,destino,(select descripcion from dbo.t_agencia where Idagencia = serv.destino) desDestino,hsalida,horasviaje,RutaCompartida,IntCorre,(select descripcion from dbo.t_frecuencia where idfrecuencia=1) TipoFrecuencia,GradoServ,rutaCompEnCaliente from dbo.t_servicio serv where intcorre =#{p_intcorre}")
	public List<Servicio> listaHijosServiciosCompartidos( @Param("p_intcorre") Integer  correlativo) throws Exception;
	
	@Select("Select count(*) from t_servicio where IdClase = #{IdClase} and HSalida = #{HSalida} and Origen = #{Origen} and Destino = #{Destino}")
	public Integer validarServicioxHora(Servicio servicio)throws Exception;
	
	
	
	@Update("update t_servicio set idclase = #{IdClase},descripcion= #{pdescripcion} where idservicio= #{idServicio}")
	@Options(flushCache=true,useCache=true)
	public void actualizarServicioAmpliar( @Param("IdClase") Integer  idClase,@Param("pdescripcion") String   pdescripcion,@Param("idServicio") Integer  idServicio) throws Exception;
	
	
	@Select("SELECT MAX(Hora24) FROM t_servicio")
	public String obtenerUltimoRegistroPorHorasalida();
	
	
}

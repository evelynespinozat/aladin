package com.certicom.ittsa.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Transbordo;

public interface ProgramacionMapper {

	
	@Select("select * from t_programacion order by idProgramacion asc")
	public List<Programacion> findAll() throws Exception;
	

	@Select("select * from t_programacion where IdProgramacion = #{idProgramacion}")
	public Programacion findById(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Delete("delete  from t_programacion  where IdProgramacion = #{idProgramacion}")
	@Options(flushCache=true)
	public void eliminarProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Select("{CALL USP_INS_Programacion_Demanda(#{idServicio},#{idBus},#{fLiquidacion},#{fSalida},#{precioPiso1},#{precioPiso2}, " +
	                                    " #{hSalida},#{fcreacion},#{hora24},#{estado},#{estEmbarque}, " +
	                                    " #{estDesembarque},#{estManifiesto},#{usuarioRegistro},#{fechaRegistro},#{IntCorreEnlace})} ")
	public String crearProgramacion(Programacion programacion) throws Exception;
	
	@Options(flushCache=true,useCache=true)
    public void actualizarProgramacion(Programacion programacion) throws Exception;
	
	@Select("select * from t_programacion where Estado = 1 order by idProgramacion asc")
	public List<Agencia> listaProgramacionActivas() throws Exception;
	
//	@Select("select count(*) from t_programacion where IdServicio = #{idServicio}")
	@Select("select count(*) from t_programacion pr inner join t_asientoventa av on pr.IdProgramacion = av.IdProgramacion " +
			" where pr.IdServicio = #{idServicio} and (av.estado = 'VENTA' or av.estado = 'RESERVA')")
	public int programacionxServicio(@Param("idServicio") Integer idServicio) throws Exception;
	
//	@Select("select prg.idprogramacion,prg.IdServicio,prg.IdBus,prg.FLiquidacion,prg.FSalida,prg.PrecioPiso1,"
//			+ " prg.intCorreEnlace ,prg.PrecioPiso2,prg.HSalida,prg.Fcreacion,prg.Hora24,prg.Estado,prg.Origen,prg.Destino,prg.flagAtnBordo,srv.HLlegada,srv.HorasViaje, srv.RutaCompartida as desRutaCompartida, srv.GradoServ as desGradoServicio,prg.ampliado "
//			+ " from t_programacion prg, t_servicio srv, t_clase cls "
//			+ " where prg.IdServicio = srv.IdServicio and cls.IdClase = srv.IdClase and  "
//			+ " prg.origen = #{p_origen} and prg.destino = #{p_destino}   and cls.IdCatServicio = 4 "
//			+ " and prg.fsalida = CAST( #{p_fsalida} AS date) "
//			+ " and ( DATEDIFF(minute, Cast(prg.FSalida as varchar) +'  '+ Cast(prg.Hora24 as varchar), GETDATE())) < 0 "
//			+ " order by prg.hora24 asc")
			public List<Programacion> findByOrigenDestinoAndFsalida(@Param("p_origen") Integer origen,@Param("p_destino") Integer destino,@Param("p_fsalida") String fSalida)throws Exception;
	
//	@Select("select prg.idprogramacion,prg.IdServicio,prg.IdBus,prg.FLiquidacion,prg.FSalida,prg.PrecioPiso1,"
//			+ " prg.intCorreEnlace ,prg.PrecioPiso2,prg.HSalida,prg.Fcreacion,prg.Hora24,prg.Estado,prg.Origen,prg.Destino,prg.flagAtnBordo,srv.HLlegada,srv.HorasViaje, srv.RutaCompartida as desRutaCompartida, srv.GradoServ as desGradoServicio,prg.ampliado"
//			+ " from t_programacion prg, t_servicio srv, t_clase cls "
//			+ " where prg.IdServicio = srv.IdServicio and cls.IdClase = srv.IdClase and  "
//			+ " prg.origen = #{p_origen} and prg.destino = #{p_destino}   and cls.IdCatServicio = 4 "
//			+ " and prg.fsalida = CAST( #{p_fsalida} AS date) "
//			+ " order by prg.hora24 asc")
			public List<Programacion> findByOrigenDestinoAndFsalidaAllDay(@Param("p_origen") Integer origen,@Param("p_destino") Integer destino,@Param("p_fsalida") String fSalida)throws Exception;
	
	/*******************Para programacion de Flota******************/
	//@Select("SELECT * FROM t_programacion p WHERE EXISTS (SELECT * FROM t_tripulacion t WHERE t.IdProgramacion = p.IdProgramacion and t.IdPiloto=#{idPiloto}) and p.FSalida BETWEEN #{fechaIni} AND #{fechaFin} order by FSalida desc")
	@Select("select * from t_programacion pr, t_tripulacion trp,dbo.t_servicio serv where pr.IdProgramacion = trp.IdProgramacion and serv.IdServicio = pr.IdServicio and  (trp.IdCopiloto = #{idPiloto} or trp.IdPiloto = #{idPiloto})and serv.GradoServ in (0,1) and pr.FSalida BETWEEN #{fechaIni} AND #{fechaFin} order by pr.FSalida ASC, pr.Hora24 ASC")
	public List<Programacion> findProgByFecha_IdPiloto(@Param("fechaIni") Date fechaIni,@Param("fechaFin") Date fechaFin,@Param("idPiloto") Integer idPiloto) throws Exception;
	
	//@Select("SELECT * FROM t_programacion p WHERE EXISTS (SELECT * FROM t_tripulacion t WHERE t.IdProgramacion = p.IdProgramacion and t.IdTerramoza=#{idTerramoza}) and p.FSalida BETWEEN #{fechaIni} AND #{fechaFin} order by FSalida desc")
	@Select("select * from t_programacion pr, t_tripulacion trp,dbo.t_servicio serv where pr.IdProgramacion = trp.IdProgramacion and serv.IdServicio = pr.IdServicio and  (trp.IdTerramoza2 = #{idTerramoza} or trp.IdTerramoza = #{idTerramoza})and serv.GradoServ in (0,1) and pr.FSalida BETWEEN #{fechaIni} AND #{fechaFin} order by pr.FSalida ASC, pr.Hora24 ASC")
	public List<Programacion> findProgByFecha_IdTerramoza(@Param("fechaIni") Date fechaIni,@Param("fechaFin") Date fechaFin,@Param("idTerramoza") Integer idTerramoza) throws Exception;
	
	/*Modificado por daniel el cabro*/
	@Select("select p.IdProgramacion as idProgramacion,p.IdServicio as idServicio,s.Descripcion as descServicio, "
			+ "s.RutaCompartida as desRutaCompartida, "
			+ "p.idBus as IdBus, "
			+ "s.TipoFrecuencia as desTipoFrecuencia, " 
			+ "p.HSalida as hSalida, s.GradoServ as gradoServ, "
			+ "p.PrecioPiso1 as precioPiso1, "
			+ "p.PrecioPiso2 as precioPiso2, p.Estado as estado, "
			+ "s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, "
			+ "p.FechaRegistro as fechaRegistro, p.Origen as origen, "
			+ "p.Destino as destino, s.RutaCompartida as desRutaCompartida, "
			+ "p.FSalida, a1.Descripcion as desOrigen, a2.Descripcion as desDestino, p.FLiquidacion, p.intCorreEnlace,p.ampliado  "
			+ "from t_programacion p, t_servicio s , t_clase c, t_categoriaservicio cs, t_agencia a1, t_agencia a2 "
			+ " where p.FSalida = #{fechaProg} and p.Origen = #{idOrigen} and  p.Destino = #{idDestino} "
			+ "and (p.Estado=3 or p.Estado=0 or p.Estado=1) and s.GradoServ in (1,2) "
			+ " and p.Origen = a1.Idagencia "
			+ " and p.Destino = a2.Idagencia "
			+ " and p.idBus = 0 "
			+ " and p.IdServicio = s.IdServicio and s.IdClase = c.IdClase and c.IdCatServicio = cs.IdCatServicio and cs.IdCatServicio = #{idCatServicio} "
			+ "order by p.Hora24 asc")
	public List<Programacion> findProgByFecha_orig_dest(@Param("fechaProg") Date fechaProg,@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino,@Param("idCatServicio") Integer idCatServicio) throws Exception;
	
	@Select("select p.IdProgramacion as idProgramacion,p.IdServicio as idServicio,s.Descripcion as descServicio, p.HSalida as hSalida,p.FSalida ,p.PrecioPiso1 as precioPiso1,p.PrecioPiso2 as precioPiso2, p.Estado as estado, "+   
						  "s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, p.IdBus, p.FechaRegistro as fechaRegistro, f.Numero as nBus, f.Recorrido as recorridoBus, p.Destino, t.IdPiloto, t.IdCopiloto, " + 
						  "a.Descripcion as desOrigen, a2.Descripcion as desDestino  " +
			"from t_programacion p, t_servicio s , t_clase c, t_categoriaservicio cs, t_flota f, t_tripulacion t, t_agencia a, t_agencia a2  " +  
			"where p.Estado=1 and f.IdBus = p.IdBus and t.IdProgramacion = p.IdProgramacion " + 
						  "and p.IdServicio = s.IdServicio and s.IdClase = c.IdClase and c.IdCatServicio = cs.IdCatServicio " + 
						  "and cs.IdCatServicio = 4 and a.Idagencia = p.Origen and a2.Idagencia = p.Destino " +
						  "and p.IdBus= #{idBus} " + 
						  "and p.EstKilometraje != 1" + 
						  "order by p.FSalida asc, p.HSalida asc")
	public List<Programacion> findProgByFecha_orig_destKilometraje(@Param("idBus") Integer idBus) throws Exception;
	
		@Select("select TOP(5) p.IdProgramacion as idProgramacion,p.IdServicio as idServicio,s.Descripcion as descServicio, p.HSalida as hSalida,p.FSalida ,p.PrecioPiso1 as precioPiso1,p.PrecioPiso2 as precioPiso2, p.Estado as estado, "+   
				  "s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, p.IdBus, p.FechaRegistro as fechaRegistro, f.Numero as nBus, f.Recorrido as recorridoBus, p.Destino, t.IdPiloto, t.IdCopiloto, cb.Consumo as consumo, "
				  + "cb.IdConCombus as idConCombus, cb.KmRecorridos, cb.PrecioComb, cb.Activar as activar, cb.CostoTotal as costoTotal, cb.CostoKm as costoKm,  " + 
				  "a.Descripcion as desOrigen, a2.Descripcion as desDestino, cb.KmInicial as desKmInicial, cb.KmFinal as desKmFinal, cb.KmRecorridos as desKmRecorridos " +
	"from t_programacion p, t_servicio s , t_clase c, t_categoriaservicio cs, t_flota f, t_tripulacion t, t_agencia a, t_agencia a2, t_consumocombustible cb " +  
	"where p.Estado=1 and f.IdBus = p.IdBus and t.IdProgramacion = p.IdProgramacion " + 
				  "and cb.IdProgramacion = p.IdProgramacion  " +
				  "and p.IdServicio = s.IdServicio and s.IdClase = c.IdClase and c.IdCatServicio = cs.IdCatServicio " + 
				  "and cs.IdCatServicio = 4 and a.Idagencia = p.Origen and a2.Idagencia = p.Destino " +
				  "and p.IdBus= #{idBus} " + 
				  "and p.EstKilometraje = 1" + 
				  "order by p.FSalida desc, cb.KmFinal desc")
	public List<Programacion> findProgByFecha_orig_destKilometrajeConKm(@Param("idBus") Integer idBus) throws Exception;
	
	public List<Programacion> listarProgFrecuente(Programacion filter);

	@Select(" select p.IdProgramacion as idProgramacion,p.IdBus as idBus,p.IdServicio as idServicio, "
			+ " s.Descripcion as descServicio,p.HSalida as hSalida,p.PrecioPiso1 as precioPiso1, "
			+ " p.PrecioPiso2 as precioPiso2, p.Estado as estado, "
            + " s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, p.FechaRegistro as fechaRegistro, "
            + " p.Origen as origen, p.Destino as destino, s.RutaCompartida as desRutaCompartida, p.IntCorreEnlace, p.FSalida, p.ampliado,"
            + " (select COUNT(*) from t_asientoventa where IdProgramacion = p.IdProgramacion and visible = 0) as aumentarCapacidad"
            + " from t_programacion p, t_servicio s ,t_clase c, t_categoriaservicio cs "
            + " where p.FSalida = #{fechaProg} and p.Origen = #{idOrigen} and  p.Destino = #{idDestino} and p.Estado=1 "
            + " and p.IdServicio = s.IdServicio and s.IdClase = c.IdClase and c.IdCatServicio = cs.IdCatServicio and cs.IdCatServicio = #{idCatServicio}"
            + " order by p.Hora24 asc ")
    public List<Programacion> findProgByFecha_orig_dest_Asig(@Param("fechaProg") Date fechaProg,@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino, @Param("idCatServicio") Integer idCatServicio) throws Exception;
	
	@Update("update t_programacion set IdBus = #{idBus}, Estado = 1 where IdProgramacion=#{idProgramacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarBusProgramacionAsig(@Param("idBus") Integer idBus, @Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Update("update t_programacion set IdBus = #{idBus}, Estado = 1, IntCorreEnlace =#{intCorre} where IdProgramacion=#{idProgramacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarBusProgramacionConvCompartida(@Param("idBus") Integer idBus, @Param("idProgramacion") Integer idProgramacion,@Param("intCorre")Integer intCorre) throws Exception;
	
	
	@Update("update t_programacion set IdBus = 0, Estado = 3 where IdProgramacion=#{idProgramacion}")
	@Options(flushCache=true,useCache=true)
    public void actualizarBusProgramacionNoAsig(@Param("idProgramacion") Integer idProgramacion) throws Exception;

	public List<Programacion> obtenerHoraxServicio(Transbordo transbordo) throws Exception;
	
	public List<Programacion> listarProgDemanda(Programacion filter) throws Exception;
	
	public List<Programacion> listarProgrPlantilla(Programacion programacion) throws Exception;

	@Update("update t_programacion set flagAtnBordo = #{valor} where IdProgramacion =#{IdProgramacion}")
	public void actualizarEstATBordo(@Param("valor") Integer valor,@Param("IdProgramacion")Integer IdProgramacion) throws Exception;
	
	//REPORTES
	
	//@Select("Select * from t_programacion where Estado = 1 and IdBus = #{idBus} and FSalida  between #{fechaIni} and #{fechaFin} ")
	@Select("Select pr.IdProgramacion,pr.IdServicio,pr.IdBus,pr.FLiquidacion,pr.FSalida,pr.PrecioPiso1,pr.PrecioPiso2,pr.HSalida,pr.Fcreacion,pr.Fconfirmacion,pr.Fmodificacion,pr.Hora24,pr.Estado,pr.EstEmbarque,pr.EstDesembarque,pr.FSalidaReal,pr.FLlegadaReal,pr.Origen,pr.Destino,pr.EstManifiesto,pr.UsuarioRegistro,pr.fechaRegistro,pr.flagAtnBordo,pr.IdTerramoza,pr.IdTerramoza2,pr.EstKilometraje,pr.IntCorreEnlace,pr.ampliado,flt.Numero as numeroBus  from t_programacion pr, dbo.t_servicio serv, dbo.t_flota flt  where pr.IdServicio = serv.IdServicio and  flt.IdBus = pr.IdBus and serv.GradoServ =1 and pr.Estado = 1 and pr.IdBus=#{idBus} and pr.FSalida  between #{fechaIni} and #{fechaFin} order by  pr.FSalida, pr.Hora24 ASC")
	public List<Programacion> findProgServCumplidosxBus(@Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin, @Param("idBus") Integer idBus) throws Exception;
	
	
	@Select("Select pr.IdProgramacion,pr.IdServicio,pr.IdBus,pr.FLiquidacion,pr.FSalida,pr.PrecioPiso1,pr.PrecioPiso2,pr.HSalida,pr.Fcreacion,pr.Fconfirmacion,pr.Fmodificacion,pr.Hora24,pr.Estado,pr.EstEmbarque,pr.EstDesembarque,pr.FSalidaReal,pr.FLlegadaReal,pr.Origen,pr.Destino,pr.EstManifiesto,pr.UsuarioRegistro,pr.fechaRegistro,pr.flagAtnBordo,pr.IdTerramoza,pr.IdTerramoza2,pr.EstKilometraje,pr.IntCorreEnlace,pr.ampliado,flt.Numero as numeroBus from t_programacion pr, dbo.t_servicio serv,dbo.t_flota flt where pr.IdServicio = serv.IdServicio and  flt.IdBus = pr.IdBus and serv.GradoServ =1 and pr.Estado = 1 and pr.FSalida  between #{fechaIni} and #{fechaFin}  order by  pr.FSalida, pr.Hora24 ASC")
	public List<Programacion> findProgServCumplidosTodos(@Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin) throws Exception;
	

	@Select("Select IdBus, a.Descripcion as desOrigen, b.Descripcion as desDestino from ((t_programacion p inner join t_agencia a on p.Origen = a.Idagencia) "
            +"                  inner join t_agencia b on p.Destino = b.Idagencia) "
			+" where p.Estado = 1  and p.FSalida  between #{fechaIni} and #{fechaFin} group by p.IdBus, a.Descripcion, b.Descripcion order by p.IdBus " )
	public List<Programacion> findProgServCumplidosFlota(@Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin) throws Exception;
	
		@Select("select p.IdProgramacion as idProgramacion, p.IdBus as idBus, p.IdServicio as idServicio, s.Descripcion as descServicio, p.HSalida as hSalida, "
				+ "p.PrecioPiso1 as precioPiso1, p.PrecioPiso2 as precioPiso2, p.Estado as estado, 	s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, "
				+ "p.Origen as origen , p.Destino as destino, p.FSalida as fSalida, t.IdTripulacion, t.IdPiloto, t.IdCopiloto, p.IdTerramoza, p.IdTerramoza2, "
				+ "(select Nombres + ' ' + ApellidoPaterno + ' ' + ApellidoMaterno from t_piloto where IdPiloto = t.IdPiloto) as nomPiloto, "
				+ "(select Nombres + ' ' + ApellidoPaterno + ' ' + ApellidoMaterno from t_piloto where IdPiloto = t.IdCopiloto) as nomCopiloto, "
				+ "(select Nombres + ' ' + Apellidos  from t_terramoza te where te.IdTerramoza = p.IdTerramoza) as nomTerramoza, "
				+ "(select Nombres + ' ' + Apellidos  from t_terramoza te where te.IdTerramoza = p.IdTerramoza2) as nomTerramoza2,"
				+ "(select f.Numero from t_flota f where f.IdBus = t.IdBus) as numeroBus "
				+ "from t_programacion p left join t_tripulacion t on p.IdProgramacion = t.IdProgramacion inner join t_servicio s on s.IdServicio = p.IdServicio "
				+ "inner join t_clase c on c.IdClase = s.IdClase inner join t_categoriaservicio cs on cs.IdCatServicio = c.IdCatServicio "
				+ "	where p.Origen  = #{idOrigen} and p.Destino = #{idDestino} and p.FSalida = #{fechaProg} and cs.IdCatServicio = 4 order by p.Hora24 asc")
		public List<Programacion> listProgTripulacion(@Param("fechaProg") Date fechaProg,@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino) throws Exception;

		@Update("update t_programacion set IdTerramoza = #{IdTerramoza} where IdProgramacion=#{IdProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarTerramoza1(@Param("IdProgramacion")Integer IdProgramacion,@Param("IdTerramoza") Integer IdTerramoza) throws Exception;

		@Update("update t_programacion set IdTerramoza2 = #{IdTerramoza2} where IdProgramacion=#{IdProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarTerramoza2(@Param("IdProgramacion")Integer IdProgramacion,@Param("IdTerramoza2") Integer IdTerramoza2) throws Exception;
		
		@Select("select p.IdProgramacion as idProgramacion, p.IdBus as idBus, p.IdServicio as idServicio, s.Descripcion as descServicio, p.HSalida as hSalida, "
				+ "p.PrecioPiso1 as precioPiso1, p.PrecioPiso2 as precioPiso2, p.Estado as estado, 	s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, "
				+ "p.Origen as origen , p.Destino as destino, p.FSalida as fSalida, (select f.Numero from t_flota f where f.IdBus = p.IdBus) as numeroBus, "
				+ "p.FSalidaReal as fSalidaReal , p.FLlegadaReal as fLlegadaReal, p.EstDesembarque as estDesembarque, "
				+ "(select a.Descripcion from t_agencia a where a.Idagencia = p.Origen ) as desOrigen, "
				+ "(select a.Descripcion from t_agencia a where a.Idagencia = p.Destino ) as desDestino, "
				+ "sa.flagLlegada "
				+ "from t_programacion p inner join t_salida sa on p.IdProgramacion = sa.IdProgramacion	 inner join t_servicio s on s.IdServicio = p.IdServicio "
				+ "inner join t_clase c on c.IdClase = s.IdClase inner join t_categoriaservicio cs on cs.IdCatServicio = c.IdCatServicio "
				+ "where p.Origen  = #{idOrigen} and p.Destino = #{idDestino} and p.FSalida = #{fechaProg} and cs.IdCatServicio = 4 "
				+ "and p.Estado =1 order by p.Hora24 asc")
		public List<Programacion> listProgDesembarque(@Param("fechaProg") Date fechaProg,@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino) throws Exception;

		@Update("update t_programacion set EstDesembarque = 1,FLlegadaReal =#{factual} where IdProgramacion =#{idProgramacion} ")
		@Options(flushCache=true,useCache=true)
		public void actualizarDesembarque(@Param("idProgramacion") Integer idProgramacion,@Param("factual") Date factual) throws Exception;
		
		@Select("select *,(select Descripcion from t_agencia where Idagencia=p.Origen) as nombOrigen,"
				+ "(select Descripcion from t_agencia where Idagencia= p.Destino) as nombDestino, "
				+ "(select Descripcion from t_servicio where IdServicio= p.IdServicio) as descServicio,"
				+ "(select sprovincia from t_ubigeo where sid_ubigeo=(select sid_ubigeo from t_terramoza where IdTerramoza = p.IdTerramoza)) as ubigeoTerramoza1,"
				+ "(select sprovincia from t_ubigeo where sid_ubigeo=(select sid_ubigeo from t_terramoza where IdTerramoza = p.IdTerramoza2)) as ubigeoTerramoza2,"
				+ "(select sprovincia from t_ubigeo where sid_ubigeo=(select Sid_ubigeo from t_piloto where IdPiloto = (select IdPiloto from t_tripulacion where IdProgramacion= p.IdProgramacion))) as ubigeoPiloto,"
				+ "(select sprovincia from t_ubigeo where sid_ubigeo=(select Sid_ubigeo from t_piloto where IdPiloto = (select IdCopiloto from t_tripulacion where IdProgramacion= p.IdProgramacion))) as ubigeoCopiloto"
				+ " from t_programacion p where p.FSalida BETWEEN #{fechaIni} and #{fechaFin} and p.IdBus = #{idBus}"
				+ " order by p.FSalida ASC,p.hsalida ASC")
	    public List<Programacion> findProg_AsigByFecha_Bus(@Param("fechaIni") Date fechaIni,@Param("fechaFin") Date fechaFin, @Param("idBus") Integer idBus) throws Exception;
		
		@Update("update t_programacion set EstKilometraje = 1 where IdProgramacion = #{idProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarEstKilometraje(@Param("idProgramacion")Integer idProgramacion);
		
		@Select("select	p.IdProgramacion as idProgramacion, s.Descripcion as descServicio, p.HSalida as hSalida,p.FSalida as fSalida, p.Hora24 as hora24, "
				+ " p.IdBus as idBus, (select Asientos from t_clase where IdClase = s.IdClase) as numAsientos, sa.IdSalida, b.numero as numeroBus from t_programacion p, t_servicio s, t_flota b, t_salida sa "
				+ "where s.IdServicio =  p.IdServicio and b.IdBus = p.IdBus and sa.IdProgramacion = p.IdProgramacion "
				+ "and (p.FSalida between #{fecIni} and #{fecFin}) and p.Estado = 1 and p.EstDesembarque = 1  and p.flagAtnBordo = 1 "
				+ "	order by p.FSalida desc, p.Hora24 desc ")
		public List<Programacion> listProgrCostxServicio(@Param("fecIni")Date fecIni, @Param("fecFin")Date fecFin);
		
		//27-02-2014
		public List<Programacion> buscarProxServicioPiloto(Programacion programacion) throws Exception;
		
		public List<Programacion> buscarProxServicioTerramoza(Programacion programacion) throws Exception;
		
		@Select("SELECT * FROM t_programacion where IdServicio = #{IdServicio} and FSalida = #{fSalidaProgramacion} and Origen = #{Origen} and Destino = #{Destino} ")
		public List<Programacion> obtenerProgramacionesHijos(Servicio servicio)throws Exception;
		
		@Select("select p.IdProgramacion as idProgramacion, p.IdBus as idBus, p.IdServicio as idServicio, s.Descripcion as descServicio, p.HSalida as hSalida, "
				+ " p.PrecioPiso1 as precioPiso1, p.PrecioPiso2 as precioPiso2, p.Estado as estado, s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, "
				+ " p.Origen as origen , p.Destino as destino, p.FSalida as fSalida, t.IdTripulacion, t.IdPiloto, t.IdCopiloto, p.IdTerramoza, p.IdTerramoza2, "
				+ " (select Nombres + ' '+  ApellidoPaterno  + ' ' +  ApellidoMaterno from t_piloto where IdPiloto = t.IdPiloto) as nomPiloto, "
				+ " (select Nombres + ' ' + ApellidoPaterno + ' '  +ApellidoMaterno from t_piloto where IdPiloto = t.IdCopiloto) as nomCopiloto, "
				+ "	(select f.Numero from t_flota f where f.IdBus = t.IdBus) as numeroBus,"
				+ " (select a.Descripcion from t_agencia a where a.Idagencia = p.Origen) as desOrigen,"
				+ " (select a.Descripcion from t_agencia a where a.Idagencia = p.Destino) as desDestino "
				+ " from t_programacion p inner join t_tripulacion t on p.IdProgramacion = t.IdProgramacion inner join t_servicio s on s.IdServicio = p.IdServicio "
				+ " inner join t_clase c on c.IdClase = s.IdClase inner join t_categoriaservicio cs on cs.IdCatServicio = c.IdCatServicio "
				+ " where p.Origen  = #{origen} and p.Destino = #{destino} and p.FSalida = #{fSalida} and p.EstEmbarque is null order by p.Hora24 asc ")
		public List<Programacion> listarBusesProgDisponibles(@Param("fSalida")Date fSalida,@Param("origen") Integer origen,@Param("destino") Integer destino) throws Exception;
		
		@Select("SELECT * FROM t_programacion where IdServicio = #{IdServicio} and FSalida = #{fSalidaProgramacion} and Origen = #{Origen} and Destino = #{Destino}")
		public List<Programacion> obtenerProgramacionesPadre(Servicio servicio)throws Exception;
		
		@Update("update t_programacion set IntCorreEnlace = #{intCorre} where IdProgramacion=#{idProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarProgramacionPadreCorrelativo(@Param("idProgramacion")Integer idProgramacion, @Param("intCorre") Integer intCorre)throws Exception;

		@Select("select * from t_programacion where IntCorreEnlace = #{intCorreEnlace} and IdProgramacion != #{idProgramacion}")
		public List<Programacion> findByIntCorreEnlace(@Param("intCorreEnlace")Integer intCorreEnlace, @Param("idProgramacion") Integer idProgramacion)throws Exception;
		
		@Select("select * from t_programacion where IntCorreEnlace = #{intCorreEnlace}")
		public List<Programacion> findByCorreEnlace(@Param("intCorreEnlace")Integer intCorreEnlace)throws Exception;
		
		@Select("select p.IdProgramacion, p.IdBus, p.FSalida, p.HSalida, f.Numero as nBus, f.Placa as placaBus, " + 
				"pt.Nombres +' '+ pt.ApellidoPaterno +' '+ pt.ApellidoMaterno as nomPiloto, pt.Licencia as licPiloto, " +  
				"pt2.Nombres +' '+ pt2.ApellidoPaterno +' '+ pt2.ApellidoMaterno as nomCopiloto, pt2.Licencia as licCopiloto " +   
				"from (((t_programacion p inner join t_flota f on p.IdBus = f.IdBus) " + 
				"	   inner join t_tripulacion t on p.IdProgramacion = t.IdProgramacion) " + 
				"	   inner join t_piloto pt on t.IdPiloto = pt.IdPiloto ) " +
				"	   inner join t_piloto pt2 on t.IdCopiloto = pt2.IdPiloto " +
				"where p.FSalida = CONVERT (date, SYSDATETIME()) and p.Origen = #{idOrigen} and p.Destino = #{idDestino} and p.IdBus != 0 ")
		public List<Programacion> listaFlotaAsiganadasProgramacion(@Param("idOrigen")Integer idOrigen,@Param("idDestino") Integer idDestino)throws Exception;
		
		public List<Programacion> listarProgSubManifiesto(Programacion filter) throws Exception;
		
		@Select("select prg.idprogramacion,prg.IdServicio,prg.IdBus,prg.FLiquidacion,prg.FSalida,prg.PrecioPiso1,"
				+"prg.PrecioPiso2,prg.HSalida,prg.Fcreacion,prg.Hora24,prg.Estado,prg.Origen,prg.Destino,prg.flagAtnBordo, "
				+ "srv.HLlegada,srv.HorasViaje, srv.RutaCompartida as desRutaCompartida, srv.GradoServ as desGradoServicio, "
				+ "srv.Descripcion as descServicio "
				+"from t_programacion prg, t_servicio srv, t_clase cls  "
				+"where prg.IdServicio = srv.IdServicio and cls.IdClase = srv.IdClase and  "
				+"prg.origen = #{p_origen} and prg.destino = #{p_destino}   and cls.IdCatServicio = 4 "
				+"and prg.fsalida = CAST( #{p_fsalida} AS date) order by prg.hora24 asc")
				public List<Programacion> buscarPorOrigenDestinoAndFsalida(@Param("p_origen") Integer origen,@Param("p_destino") Integer destino,@Param("p_fsalida") String fSalida)throws Exception;

		@Select("select prg.idprogramacion,prg.IdServicio,prg.IdBus,prg.FLiquidacion,prg.FSalida,prg.PrecioPiso1,"
				+"prg.PrecioPiso2,prg.HSalida,prg.Fcreacion,prg.Hora24,prg.Estado,prg.Origen,prg.Destino,prg.flagAtnBordo, "
				+ "srv.HLlegada,srv.HorasViaje, srv.RutaCompartida as desRutaCompartida, srv.GradoServ as desGradoServicio, "
				+ "srv.Descripcion as descServicio, "
				+ "srv.idRutaDestino as idRutaDestino,"
				+ "s.code_mtc as terminalSalida, "
				+ "l.code_mtc as terminalLLegada "
				+"from t_programacion prg, t_servicio srv, t_clase cls, t_agencia s, t_agencia l "
				+"where prg.IdServicio = srv.IdServicio and cls.IdClase = srv.IdClase and  "
				+" prg.Origen = s.Idagencia and prg.Destino = l.Idagencia and "
				+"prg.origen = #{p_origen} and prg.destino = #{p_destino}   and cls.IdCatServicio = 4 "
				+"and prg.fsalida = #{fSalida} order by prg.hora24 asc")
		public List<Programacion> listarProgxSalida(@Param("p_origen") Integer origen,@Param("p_destino") Integer destino,@Param("fSalida") Date fecha)throws Exception;
		
		@Update("update t_programacion set idServicio = #{idServicio}, idBus = #{idBus} where IdProgramacion = #{idProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarServicio(@Param("idProgramacion") Integer idProgramacion, @Param("idServicio") Integer idServicio, @Param("idBus") Integer idBus)throws Exception;
		
		//public List<Programacion> listarSalidasProgramacion(Programacion programacion)throws Exception;
		
		
		@Select("select * from dbo.t_programacion where fsalida =#{fSalida}  and IdServicio =#{IdServicio} and IntCorreEnlace = #{IntCorreEnlace}")
		public Programacion programacionRutaCompartida(@Param("fSalida")Date fSalida,@Param("IdServicio") Integer IdServicio,@Param("IntCorreEnlace") Integer IntCorreEnlace) throws Exception;
		
		@Update("update t_programacion set IntCorreEnlace = #{IntCorreEnlace} where IdProgramacion = #{idProgramacion}")
		@Options(flushCache=true,useCache=true)
		public void actualizarProgramacionCorrelativo(@Param("IntCorreEnlace") Integer IntCorreEnlace, @Param("idProgramacion") Integer idProgramacion)throws Exception;
		
		@Select("select * from t_programacion p inner join t_servicio s on p.IdServicio = s.IdServicio " 
				+" where p.IdProgramacion != #{idProgramacion} "
				+" and p.IntCorreEnlace = (select o.IntCorreEnlace from t_programacion o where o.IdProgramacion = #{idProgramacion})")
		public List<Programacion> obtenerProgramacionesHijoxIdProgram(@Param("idProgramacion") Integer idProgramacion )throws Exception;
		
		@Update("update t_programacion set hojaruta_mtc=#{hojaruta_mtc} where idProgramacion = #{idProgramacion}")
		@Options(flushCache=true, useCache=true)
		public void actualizarHojaRuta(@Param("hojaruta_mtc") String hojaruta_mtc, @Param("idProgramacion") Integer idProgramacion)throws Exception;
		
		@Select("select "
				+" (select count(*) from t_asientoventa a where a.IdProgramacion = p.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'LIBRE' ) as disponibles, "
				+" (select count(*) from t_asientoventa a where a.IdProgramacion = p.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'VENTA' ) as vendidos, "
				+" (select count(*) from t_asientoventa a where a.IdProgramacion = p.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'RESERVA' ) as reservados, "
				+" (select count(*) from t_asientoventa a where a.IdProgramacion = p.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'SEPARADO' ) as separados, "
				+" (select count(*) from t_asientoventa av,dbo.t_asiento asi where asi.IdAsiento = av.IdAsiento and av.IdProgramacion = p.IdProgramacion and av.visible = 'TRUE' and  av.estado = 'LIBRE' and asi.Piso = 1 ) as disponibleP1, "
				+" (select count(*) from t_asientoventa av,dbo.t_asiento asi where asi.IdAsiento = av.IdAsiento and av.IdProgramacion = p.IdProgramacion and av.visible = 'TRUE' and  av.estado = 'LIBRE' and asi.Piso = 2 ) as disponibleP2  "
				+" from t_programacion p where p.IdProgramacion = #{idProgramacion} ")
		public Programacion getStateSeats(@Param("idProgramacion") Integer idProgramacion)throws Exception;
		
		@Select("select p.FSalida, s.Origen, s.Destino, s.HSalida, a1.Descripcion as desOrigen, a2.Descripcion as desDestino "  
				+" from ((t_programacion p inner join t_servicio s on p.IdServicio = s.IdServicio) "
				+"					   inner join t_agencia a1 on a1.Idagencia = s.Origen) "    
				+"					   inner join t_agencia a2 on a2.Idagencia = s.Destino "
				+" where p.IdProgramacion != #{idProgramacion} "
				+" and p.FSalida = #{fsalida} "
				+" and p.IntCorreEnlace = (select o.IntCorreEnlace from t_programacion o where o.IdProgramacion = #{idProgramacion})")
		public List<Programacion> obtenerProgramacionesHijoxIdProgramyDescripcion(@Param("idProgramacion") Integer idProgramacion, @Param("fsalida") Date fsalida )throws Exception;
		
		
		
		@Update("update t_programacion set ampliado = #{ampliado} where IdProgramacion=#{idProgramacion}")
		@Options(flushCache=true,useCache=true)
	    public void actualizarAmpliacionBusProgramacion(@Param("ampliado") Boolean ampliado, @Param("idProgramacion") Integer idProgramacion) throws Exception;
		
		
		/*salida retorno modificado*/
		//trujillo-lima : salida
		@Select("select serv.HSalida, serv.Descripcion as descServicio,((select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Origen)+'-'+(select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Destino)) as ruta, (select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'VENTA' and a.ventaOrigen = 'true' ) as asienVendidos,(select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'RESERVA' and a.ventaOrigen = 'true') as asienReserva,(select fl.Numero from t_flota fl where pr.IdBus = fl.IdBus ) as numeroBus from dbo.t_servicio serv, dbo.t_programacion pr where pr.IdServicio = serv.IdServicio and  pr.FSalida = #{fSalida} and serv.Destino in (3,6) order by serv.Hora24 ASC")
		public List<Programacion> trujilloLimaSalida(@Param("fSalida")Date fSalida)throws Exception;
		
		
		//trujillo-lima : retorno		
		@Select("select serv.HSalida, serv.Descripcion as descServicio,((select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Origen)+'-'+(select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Destino)) as ruta, (select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'VENTA' and a.ventaOrigen = 'true') as asienVendidos,(select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'RESERVA' and a.ventaOrigen = 'true') as asienReserva,(select fl.Numero from t_flota fl where pr.IdBus = fl.IdBus ) as numeroBus from dbo.t_servicio serv, dbo.t_programacion pr where pr.IdServicio = serv.IdServicio and  pr.FSalida = #{fSalida} and serv.Origen in (3,6) order by serv.Hora24 ASC")
		public List<Programacion> trujilloLimaRetorno(@Param("fSalida")Date fSalida)throws Exception;
		
		//trujillo-norte: salida
		@Select("select serv.HSalida, serv.Descripcion as descServicio,((select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Origen)+'-'+(select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Destino)) as ruta, (select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'VENTA' and a.ventaOrigen = 'true') as asienVendidos,(select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'RESERVA' and a.ventaOrigen = 'true') as asienReserva,(select fl.Numero from t_flota fl where pr.IdBus = fl.IdBus ) as numeroBus from dbo.t_servicio serv, dbo.t_programacion pr where pr.IdServicio = serv.IdServicio and  pr.FSalida = #{fSalida} and serv.Origen in (9,2) and serv.Destino not in (3,6)  order by serv.Hora24 ASC")
		public List<Programacion> trujilloNorteSalida(@Param("fSalida")Date fSalida)throws Exception;
		
		//trujillo-norte: retorno
		@Select("select serv.HSalida, serv.Descripcion as descServicio,((select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Origen)+'-'+(select ag.Grupo from t_agencia ag where ag.Idagencia = serv.Destino)) as ruta, (select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'VENTA' and a.ventaOrigen = 'true') as asienVendidos, (select count(*) from t_asientoventa a where a.IdProgramacion = pr.IdProgramacion and a.visible = 'TRUE' and  a.estado = 'RESERVA' and a.ventaOrigen = 'true') as asienReserva,(select fl.Numero from t_flota fl where pr.IdBus = fl.IdBus ) as numeroBus from dbo.t_servicio serv, dbo.t_programacion pr where pr.IdServicio = serv.IdServicio and  pr.FSalida = #{fSalida} and serv.Destino in (9,2) and serv.Origen not in (3,6)  order by serv.Hora24 ASC")
		public List<Programacion> trujilloNorteRetorno(@Param("fSalida")Date fSalida)throws Exception;
		
		
		@Select("select p.IdProgramacion as idProgramacion,p.IdServicio as idServicio,s.Descripcion as descServicio, "
				+ "s.RutaCompartida as desRutaCompartida, "
				+ "p.idBus as IdBus, "
				+ "s.TipoFrecuencia as desTipoFrecuencia, " 
				+ "p.HSalida as hSalida, s.GradoServ as gradoServ, "
				+ "p.PrecioPiso1 as precioPiso1, "
				+ "p.PrecioPiso2 as precioPiso2, p.Estado as estado, "
				+ "s.IdClase as idClase, p.UsuarioRegistro as usuarioRegistro, "
				+ "p.FechaRegistro as fechaRegistro, p.Origen as origen, "
				+ "p.Destino as destino, s.RutaCompartida as desRutaCompartida, "
				+ "p.FSalida, a1.Descripcion as desOrigen, a2.Descripcion as desDestino, p.FLiquidacion, p.intCorreEnlace,p.ampliado  "
				+ "from t_programacion p, t_servicio s , t_clase c, t_categoriaservicio cs, t_agencia a1, t_agencia a2 "
				+ " where p.FSalida = #{fechaProg} and p.Origen = #{idOrigen} and  p.Destino = #{idDestino} "
				+ "and (p.Estado=3 or p.Estado=0 or p.Estado=1) and s.GradoServ in (1,2) "
				+ " and p.Origen = a1.Idagencia "
				+ " and p.Destino = a2.Idagencia "
				+ " and p.IdServicio = s.IdServicio and s.IdClase = c.IdClase and c.IdCatServicio = cs.IdCatServicio and cs.IdCatServicio = #{idCatServicio} "
				+ "order by p.Hora24 asc")
		public List<Programacion> findProgByFecha_orig_destVentas(@Param("fechaProg") Date fechaProg,@Param("idOrigen") Integer idOrigen,@Param("idDestino") Integer idDestino,@Param("idCatServicio") Integer idCatServicio) throws Exception;
		
}




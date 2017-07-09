package com.certicom.ittsa.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.domain.VentaDetalle;
import com.pe.certicom.ittsa.commons.BoletoFilter;

@Repository("asientoVentaDao")
public interface AsientoVentaMapper {

	// @Select("select * from dbo.t_clase  order by descripcion asc")
	public List<AsientoVenta> findAll() throws Exception;

	@Select("select * from t_asientoventa where idAsientoventa=#{p_IdAsientoVenta}")
	public AsientoVenta findById(
			@Param("p_IdAsientoVenta") Integer idasientoventa) throws Exception;

	@Select("select * from t_asientoventa where IdAsiento=#{p_Idasiento} and numero = #{p_numero}  and IdProgramacion = #{p_idprogramacion}")
	public AsientoVenta findAsientoByAsientoAndProgramacion(
			@Param("p_Idasiento") Integer IdAsiento,
			@Param("p_numero") String numero,
			@Param("p_idprogramacion") Integer IdProgramacion) throws Exception;

	
	@Select("select a.idasientoventa,a.estado, a.sexo, a.documentoPersona,a.monto,a.montoReal,a.numero,a.documentoEmpresa, a.nrodocprepagado,a.IdAsiento,a.IdProgramacion, a.promocional,a.oficinaId,a.idusuarioautorizante, a.idusuarioventa, (u.apellido_paterno + ' ' + u.apellido_materno + ' ' + u.nombre) as completoVendedor, o.Nombre as desOficina  from (t_asientoventa a left join t_usuario u on a.idusuarioventa = u.idusuario) left join t_agenciaoficina o on a.oficinaId = o.IdOficina where a.IdAsiento=#{IdAsiento} and a.numero = #{numero}  and a.IdProgramacion = #{IdProgramacion}")
	public AsientoVenta findEstadoAsientoByAsientoAndProgramacion(
			AsientoVenta asientoVenta) throws Exception;

	@Update("update t_asientoventa set sexo=#{sexo},documentoPersona=#{documentoPersona},documentoEmpresa=#{documentoEmpresa}  where idprogramacion=#{IdProgramacion} and idasiento=#{IdAsiento}")
	@Options(flushCache = true, useCache = true)
	public void editarVentaAsiento(AsientoVenta asientoVenta) throws Exception;
	
	@Update("update t_asientoventa set estado=#{estado}, tipoventa= null,fechaventa=null,sexo='-',externo=null,oficinaId = null,promocional = null,documentoPersona=null,documentoEmpresa=null ,observacion =null,idusuarioventa = 0,horareserva= null,montoReal = null,ventaOrigen = 'false' where IdProgramacion=#{idProgramacion} and IdAsiento=#{idAsiento}")
	@Options(flushCache=true,useCache=true)
	public void liberarAsiento(@Param("idAsiento") Integer idAsiento,@Param("idProgramacion") Integer idProgramacion, @Param("estado") String estado) throws Exception;
	
//	@Select("select a.idasientoventa, a.monto, a.fechaventa, a.IdAsiento, "+ 
//			   "a.documentoPersona, a.oficinaId, a.tipdocdelivery, a.numero as nroAsiento,se.Descripcion as desServicio, ofi.Nombre as desOficina, "+
//			   "a.nrodocdelivery, a.Direccion, s.Numero as nroAsiento, p.FSalida as desFSalida,p.HSalida as desHsalida, o.Descripcion as desOrigen, d.Descripcion  as desDestino, "
//			   + "a.referenDelivery , a.telefDelivery, a.fechaEntregaDelivery,a.estado_delivery, a.nrodocdelivery, "
//			   + "(select top 1 tcb.serieBoleto + '-'+tcb.numeroBoleto from t_trackingboleto tcb where tcb.idasientoventa = a.idasientoventa order by tcb.IdTrackingBoleto desc )  as serieNumeroBoleto  "+  
//			   "from (((((t_asientoventa a inner join t_asiento s on a.IdAsiento = s.IdAsiento) "+
//			   "inner join t_programacion p on p.IdProgramacion = a.IdProgramacion) "+ 
//			   "inner join t_agencia o on p.Origen = o.Idagencia ) "+
//			   "inner join t_agencia d on p.Destino = d.Idagencia) "
//			   + "inner join t_servicio se on se.IdServicio = p.IdServicio) "
//			   + "inner join t_agenciaoficina ofi on ofi.IdOficina = a.oficinaId "+
//			   "where delivery = 1 and a.fechaventa >= #{finicio} and a.fechaventa <= #{ffin} "
//			   + " and o.Idagencia = #{origen}  and d.Idagencia = #{destino} and a.estado_delivery = #{estadoDelivery} "
//			   + "and a.oficinaId = #{idoficina} order by a.fechaventa desc")
	public List<AsientoVenta> obtenerDeliverys(BoletoFilter filter) throws Exception;
	
	@Select("select * from t_asientoventa  where IdProgramacion = #{idProgramacion} order by cast(numero as int) asc ")
	public List<AsientoVenta> listarManifiestoxProg(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	
	@Select("select * from t_asientoventa  where IdProgramacion = #{idProgramacion} and abordo = 'false' and (documentoPersona <> '-' or documentopersona is null) order by numero asc")
	public List<AsientoVenta> listarManifiestoxProgNoEmbarcados(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	//
	
	@Select("select CAST(av.numero AS INT) numeroAS from t_asientoventa av, t_asiento asi  where av.IdAsiento = asi.IdAsiento and  av.estado = 'LIBRE' and asi.Piso = #{piso} and  av.IdProgramacion = #{idProgramacion} order by cast(av.numero as int) asc")
	public List<Integer> listarAsientosDisponibles(@Param("piso") Integer piso,@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	@Select("select *  from t_asientoventa av, t_asiento asi  where av.IdAsiento = asi.IdAsiento and  av.estado = 'LIBRE' and asi.Piso = #{piso} and  av.IdProgramacion = #{idProgramacion} order by av.numero asc")
	public List<AsientoVenta> listarTotalAsientosDisponibles(@Param("piso") Integer piso,@Param("idProgramacion") Integer idProgramacion) throws Exception;

		
	@Select("select count(*) from t_asientoventa where IdProgramacion = #{p_idprogramacion}  and  estado = #{p_estado} and visible =#{p_visible}")
	public Integer countbyprogramacionAndEstado(
			@Param("p_idprogramacion") Integer IdProgramacion,
			@Param("p_estado") String estado,
			@Param("p_visible") Boolean visibles) throws Exception;

	@Select("select count(*) from t_asientoventa av,dbo.t_asiento asi where asi.IdAsiento = av.IdAsiento and av.IdProgramacion = #{p_idprogramacion} and  av.estado = #{p_estado} and asi.Piso = #{p_piso} and  av.Visible =#{p_visible}")
	public Integer countbyprogramacionAndEstadoXPiso(
			@Param("p_idprogramacion") Integer IdProgramacion,
			@Param("p_estado") String estado, @Param("p_piso") Integer piso,
			@Param("p_visible") Boolean p_visible) throws Exception;

	@Select("select count(*) from dbo.t_asientoventa  where IdProgramacion = #{p_idprogramacion}  and visible=#{p_visible}")
	public Integer countCapacidadVerdadera(
			@Param("p_idprogramacion") Integer IdProgramacion,
			@Param("p_visible") Boolean p_visible) throws Exception;
	
	
	//@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,isolation=Isolation.READ_COMMITTED)
	@Update("update t_asientoventa set monto=#{monto},estado=#{estado},tipoventa=#{tipoventa},sexo=#{sexo},fechaventa=SYSDATETIME(),documentoPersona=#{documentoPersona},documentoEmpresa=#{documentoEmpresa},observacion=#{observacion}, externo=#{externo},oficinaId =#{oficinaId},prepagado=#{prepagado} ,tipdocprepagado=#{tipdocprepagado},nrodocprepagado=#{nrodocprepagado},delivery=#{delivery},tipdocdelivery=#{tipdocdelivery},nrodocdelivery=#{nrodocdelivery} ,Direccion=#{direccion},idusuarioventa=#{idusuarioventa} ,promocional=#{promocional},idusuarioautorizante=#{idusuarioautorizante}, horareserva=#{horareserva}, "
			+ "estado_delivery = #{estado_delivery},fechaEntregaDelivery = #{fechaEntregaDelivery},referenDelivery = #{referenDelivery}, telefDelivery = #{telefDelivery}, idpromocion = #{idpromocion},transferenciaBanco= #{transferenciaBanco}, transferenciaNumero= #{transferenciaNumero},ventaOrigen = #{ventaOrigen},montoReal = #{montoReal},idAsientoVentaInhabiltd=#{idAsientoVentaInhabiltd} where idprogramacion=#{IdProgramacion} and idasiento=#{IdAsiento}")
	@Options(flushCache = true, useCache = true)
	public void actualizarVentaAsiento(AsientoVenta asientoVenta)
			throws Exception;

	@Update("update t_asientoVenta set estado ='LIBRE', tipoventa=null, fechaventa=null,sexo='-',documentopersona=null,documentoempresa=null,observacion=null,externo=null,oficinaId=null,prepagado=0,tipdocprepagado=null,nrodocprepagado=null, idusuarioventa = 0 where idprogramacion=#{IdProgramacion} and estado = 'PROCESO' and idusuarioventa =#{idusuarioventa}")
	@Options(flushCache = true, useCache = true)
	public void liberarVentaAsiento(AsientoVenta asientoVenta) throws Exception;

//	@Select("select a.idasientoventa, a.monto, a.fechaventa, a.IdAsiento, "
//			+ "a.documentoPersona, a.oficinaId, a.tipdocdelivery, "
//			+ "a.nrodocdelivery, a.Direccion, s.Numero as nroAsiento, p.FSalida as desFSalida, o.Descripcion as desOrigen, d.Descripcion  as desDestino "
//			+ "from (((t_asientoventa a inner join t_asiento s on a.IdAsiento = s.IdAsiento) "
//			+ "inner join t_programacion p on p.IdProgramacion = a.IdProgramacion) "
//			+ "inner join t_agencia o on p.Origen = o.Idagencia ) "
//			+ "inner join t_agencia d on p.Destino = d.Idagencia "
//			+ "where delivery = 1 and a.fechaventa = #{fecha} ")
//	public List<AsientoVenta> obtenerDeliverys(@Param("fecha") Date fecha)throws Exception;

	

	@Select("select * from t_asientoventa  where IdProgramacion = #{idProgramacion} and documentoPersona = #{documentoPersona}")
	public AsientoVenta buscarPasajeroxDni(
			@Param("idProgramacion") Integer idProgramacion,
			@Param("documentoPersona") String documentoPersona)
			throws Exception;

	@Update("update t_asientoventa set abordo = #{abordo}, numeroImagen=#{numeroImagen}, nombreImagen=#{nombreImagen} where  IdProgramacion = #{idProgramacion} and documentoPersona = #{documentoPersona}")
	@Options(flushCache = true, useCache = true)
	public void actualizarAbordoAsVenta(AsientoVenta asientoVenta)
			throws Exception;
	/*
	@Select("select a.idasientoventa, p.FSalida as desFSalida, p.HSalida as desHsalida, a.numero as nroAsiento, a.documentoPersona, "
			+ "a.nrodocprepagado, s.Descripcion as desServicio ,a.fechaventa, o.Descripcion as desOrigen, "
			+ "d.Descripcion as desDestino,a.monto,a.idusuarioventa,a.estado_delivery,ofi.Nombre as desOficina,  "
			+ "(select top 1 tcb.serieBoleto + '-'+tcb.numeroBoleto from t_trackingboleto tcb where tcb.idasientoventa = a.idasientoventa order by tcb.IdTrackingBoleto desc )  as serieNumeroBoleto "
			+ " from ((((t_asientoventa a inner join t_programacion p on a.IdProgramacion = p.IdProgramacion) "
			+ " inner join t_servicio s on s.IdServicio = p.IdServicio) "
			+ " inner join t_agencia o on o.Idagencia = p.Origen) "
			+ " inner join t_agencia d on d.Idagencia = p.Destino) "
			+ " inner join t_agenciaoficina ofi on ofi.IdOficina = a.oficinaId "
			+ "where a.prepagado = 'true' "
			+ "and p.Origen = #{origen} "
			+ "and p.Destino = #{destino}  "
			+ "and p.FSalida  >= #{finicio} and p.FSalida  <= #{ffin} "
			+ "and a.oficinaId =#{idoficina} "
			+ "and a.estado_delivery = #{estadoDelivery} order by a.fechaventa desc")*/
	
	@Select("select (select ag.Descripcion from dbo.t_agencia ag where ag.Idagencia = asv.oficinaId) as desOficina, "
			+"CASE asv.estado_delivery WHEN  'IMPRESO' then (tk.serieBoleto+'-'+tk.numeroBoleto) " 
            + "WHEN  'NO IMPRESO' then ' ' END as serieNumeroBoleto, "
			+ "(select serv.Descripcion from dbo.t_servicio serv  where serv.IdServicio = pr.IdServicio) as desServicio,"
			+ "pr.FSalida as desFSalida,pr.HSalida desHsalida,asv.numero nroAsiento,asv.nrodocprepagado,asv.documentoPersona,asv.estado_delivery "
			+ "from dbo.t_asientoventa asv, dbo.t_programacion pr,dbo.t_trackingboleto tk where asv.IdProgramacion = pr.IdProgramacion "
			+ "and tk.idprogramacion = pr.IdProgramacion and tk.idasientoventa = asv.idasientoventa and "
			+ "pr.Origen = #{origen} and pr.Destino = #{destino} and pr.FSalida = #{ffin} and asv.estado_delivery =#{estadoDelivery} and asv.prepagado = 'true' and tk.prepagado = 'true'")
	public List<AsientoVenta> obtenerPrepagadosxFecha(BoletoFilter boletoFilter) throws Exception;
	
	@Select("select a.idasientoventa, p.FSalida as desFSalida, p.HSalida  as desHsalida, a.numero as nroAsiento, "
			+ "a.documentoPersona, s.Descripcion as desServicio, ao.Descripcion as desOrigen, ad.Descripcion as desDestino, "
			+ "DATEDIFF(DAY, GETDATE(),p.FSalida) as diasRestantes, "
			+ "DATEDIFF(HOUR, GETDATE(), Cast(Cast(p.FSalida as varchar) +' ' +SUBSTRING(cast(p.Hora24 as varchar),1,12) as datetime)) - 4 as horasRestantes "
			+ "from ((((t_asientoventa a inner join t_programacion p on a.IdProgramacion = p.IdProgramacion) "
			+ "						inner join t_servicio s on s.IdServicio = p.IdServicio) "
			+ "						inner join t_agencia ao on s.Origen = ao.Idagencia) "
			+ "						inner join t_agencia ad on s.Destino = ad.Idagencia)  "
			+ "where p.Origen = #{idOrigen} "
			+ "and p.Destino = #{idDestino} "
			+ "and a.estado = 'RESERVA' " + "and a.oficinaId = #{idOficina} "
			+ "and p.FSalida >= #{Fecini} and p.FSalida <= #{Fecfin}")
	public List<AsientoVenta> obtenerReservasxEstado(
			@Param("idOrigen") Integer idOrigen,
			@Param("idDestino") Integer idDestino,
			@Param("idOficina") Integer idOficina,
			@Param("Fecini") Date fecini,
			@Param("Fecfin") Date fecfin) throws Exception;

	@Select("select * from t_asientoventa where estado = 'VENTA' and estado = 'RESERVA' and IdProgramacion = #{idProgramacion} ")
	public List<AsientoVenta> verificandoVentaAsiento(
			@Param("idProgramacion") Integer idProgramacion) throws Exception;

	@Update("update t_asientoventa set visible = 'TRUE' where idasientoventa = #{idasientoventa}")
	public void aumentarCapacidad(
			@Param("idasientoventa") Integer idasientoventa) throws Exception;

	@Select("select * from t_asientoventa where IdProgramacion = #{idProgramacion} and numero = #{numero} ")
	public AsientoVenta obtenerAsientoxProgramacionNumero(
			@Param("idProgramacion") Integer idProgramacion,
			@Param("numero") String numero) throws Exception;

	
	@Select("select * from t_asientoventa where IdProgramacion = #{idProgramacion}")
	public List<AsientoVenta> obtenerAsientoxProgramacion(@Param("idProgramacion") Integer idProgramacion) throws Exception;
	
	
	@Update("update t_asientoVenta set estado ='LIBRE', tipoventa=null, fechaventa=null,sexo='-',documentopersona=null,documentoempresa=null,observacion=null,externo=null,oficinaId=null,prepagado=0,tipdocprepagado=null,nrodocprepagado=null, idusuarioventa = 0 where idprogramacion=#{IdProgramacion} and estado = 'PROCESO' and idusuarioventa =#{idusuarioventa}")
	@Options(flushCache = true, useCache = true)
	public void actualizarAsientoxProgramacionLiberar(AsientoVenta asientoVenta)
			throws Exception;
	
																																																																															
	@Update("update t_asientoVenta set estado ='LIBRE', tipoventa=null, fechaventa=null,sexo='-',documentopersona=null,documentoempresa=null,observacion=null,externo=null,oficinaId=null,prepagado=0,tipdocprepagado=null,nrodocprepagado=null, idusuarioventa = 0 where idprogramacion=#{IdProgramacion} ")
	@Options(flushCache = true, useCache = true)																																																																								
	public void actualizarAsientoxProgramacionIdLiberar(Integer programacionId)
			throws Exception;
	
	
	

	@Select("{CALL USP_UPDATE_ASSOCIATED_SEATS(#{IdProgramacion},#{numero},#{estado},#{monto},#{tipoventa},#{sexo},#{documentoPersona},#{documentoEmpresa},#{idusuarioventa},#{fSalida},#{observacion},#{externo},#{oficinaId},#{prepagado},#{tipdocprepagado},#{nrodocprepagado},#{delivery},#{tipdocdelivery},#{nrodocdelivery},#{direccion},#{promocional},#{idusuarioautorizante},#{IdAsiento},#{horareserva})} ")
	public String updateAssociatedSeats(AsientoVenta asientoVenta)
			throws Exception;

	@Select("select a1.Descripcion desOrigen,a2.Descripcion desDestino,ao.Nombre desOficina from (t_programacion p1 inner join t_agencia a1 on p1.Origen = a1.Idagencia),"
			+ "(t_programacion p2 inner join t_agencia a2 on p2.Destino = a2.Idagencia), "
			+ "t_agenciaoficina ao"
			+ " where p1.IdProgramacion=#{idProgramacion} and p2.IdProgramacion=#{idProgramacion} and ao.IdOficina=#{oficinaId}")
	public AsientoVenta findInfoAsientoVenta(@Param("idProgramacion") Integer idProgramacion,
			@Param("oficinaId") Integer oficinaId);
	
	@Update("update t_asientoventa set fechaEntregaDelivery = #{fechaEntregaDelivery},estado_delivery = #{estado_delivery},personaRecibeDeliv = #{personaRecibeDeliv} where idasientoventa = #{idasientoventa} ")
	@Options(flushCache = true ,useCache = true)
	public void actualizarEstadoDelivery(AsientoVenta asientoVenta) throws Exception;

	@Update("update t_asientoventa set estado_delivery = #{estado_delivery} where idasientoventa = #{idasientoventa} ")
	@Options(flushCache = true ,useCache = true)
	public void actualizarEstadoImpreso(AsientoVenta asientoVenta) throws Exception;
	
	
	//modificacion 08-09-2014
	@Select("select * from t_asientoventa where IdProgramacion=#{idProgramacion} and documentoPersona=#{documentoPersona} ")
	public AsientoVenta findPasajeroByProgramacion(@Param("idProgramacion") Integer idProgramacion, @Param("documentoPersona") String documentoPersona) throws Exception;
	
	
	
	@Insert("insert into  t_asientoventa (monto,estado,IdAsiento,IdProgramacion,numero,tipdocprepagado,nrodocdelivery,abordo,idusuarioventa,visible) values (#{monto},#{estado},#{IdAsiento},#{idProgramacion},#{numero},0,0,0,0,1) ")
	@Options(flushCache = true, useCache = true)
	public void nuevosAsientoVentaPorAmpliacion(AsientoVenta asientoVenta)
			throws Exception;
	
	
	@Update("update t_asientoventa set IdAsiento = #{idasiento} where idasientoventa = #{idasientoVenta}")
	public void actualizarAsientoId(@Param("idasiento") Integer idasiento,@Param("idasientoVenta") Integer idasientoVenta) throws Exception;
	
	
	
	
	@Select("select count(*) from dbo.t_asientoventa  where IdProgramacion = #{p_idprogramacion}  and abordo = #{p_abordo} and (documentoPersona <>'-' or  documentoPersona is null)")
	public Integer countEmbarcadosNoEmbarcados(
			@Param("p_idprogramacion") Integer IdProgramacion, @Param("p_abordo")Boolean abordo) throws Exception;
	
	
	@Select("select count(*) from dbo.t_asientoventa  where IdProgramacion = #{p_idprogramacion}  and (documentoPersona = '-' or  documentoPersona is null)")
	public Integer countLibres(
			@Param("p_idprogramacion") Integer IdProgramacion) throws Exception;
	
	
	
	@Select(" {CALL USP_BLOCK_SEAT(#{IdProgramacion},#{numero},#{IdAsiento},#{retorno})} ")
	public Integer transac_block_seat(AsientoVenta as)
			throws Exception;
	
	
	@Select(" {CALL USP_BLOCK_SEAT_BEFORE(#{p_idProgramacion},#{p_numero},#{p_idAsiento})} ")
	public Integer transac_block_seat_before(@Param("p_idProgramacion") Integer IdProgramacion, 
											 @Param("p_numero") String numero,
											 @Param("p_idAsiento") Integer idAsiento)
			throws Exception;
	
	//INICIO PISCOYA
	@Select("select CASE asv.estado_delivery WHEN  'IMPRESO' then (tk.serieBoleto+'-'+tk.numeroBoleto) "
			+ "WHEN 'NO IMPRESO' then ' ' END as serieNumeroBoleto, "
			+ "(select ag.Descripcion from dbo.t_agencia ag where ag.Idagencia = asv.oficinaId) as desOficina, "
			+ "(select serv.Descripcion from dbo.t_servicio serv  where serv.IdServicio = pr.IdServicio) as desServicio, "
			+ "pr.FSalida as desFSalida,pr.HSalida desHsalida,asv.numero nroAsiento,asv.nrodocprepagado, "
			+ "asv.documentoPersona, asv.estado_delivery from dbo.t_asientoventa asv, dbo.t_programacion pr, "
			+"dbo.t_trackingboleto tk  where asv.IdProgramacion = pr.IdProgramacion "
			+"and tk.idprogramacion = pr.IdProgramacion and tk.idasientoventa = asv.idasientoventa "
			+"and asv.prepagado = 'true' AND asv.documentoPersona = #{dniPasajero}  ") 
	public List<AsientoVenta> obtenerPrepagadosxDNI(String dniPasajero) throws Exception;
	
    /*
	@Select("select av.fechaventa as fechaVenta,tb.numeroBoleto as nBoleto,tb.serieBoleto+' - '+tb.numeroBoleto as nComprobante, "
       +"tb.estado as estado,tb.montoReal as monto,"
       +"(select nombre from t_agenciaoficina af where af.IdOficina = av.oficinaId) as nomOficina, "
       +"(select Descripcion from t_agencia a where a.Idagencia = (select af.IdAgencia from t_agenciaoficina af where af.IdOficina = av.oficinaId) ) as nomAgencia, "
       +"(select NamePuntoVenta from t_puntoventa where IdPuntoVenta = tb.puntoVentaId) as nomPuntoVenta, "
       +"(select nombre +' '+apellido_paterno from t_usuario where idusuario = tb.idusuario) as nomUsuario "
       +"from t_asientoventa av inner join t_trackingboleto tb on av.idasientoventa = tb.idasientoventa "
       +"where tb.puntoVentaId=#{idPuntoVenta} and av.estado = 'VENTA' and av.fechaventa between #{fInicio} and #{fFin} order by av.fechaventa desc ")*/
	@Select("select "
			+" av.fechaventa as fechaVenta,"
			+" prog.HSalida as horaviaje,"
			+" prog.FSalida as fechasalida,"
			+" (select Descripcion from t_agencia where Idagencia = prog.Origen) + '-' +(select Descripcion from t_agencia where Idagencia = prog.Destino) as ruta,"
			+" asie.Numero as asiento,"
			+" tb.serieBoleto+'-'+tb.numeroBoleto as nComprobante,"
			+" av.documentoPersona,"
			+" av.montoReal as tarifa,"
			+" (select FormaPago from t_liquidacion_venta where Documento = (tb.serieBoleto+'-'+tb.numeroBoleto) ) as	formapago,"
			+" tb.estado as estado,"
			+" tb.montoReal as monto,"
			+" (select nombre from t_agenciaoficina af where af.IdOficina = av.oficinaId) as nomOficina," 
			+" (select Descripcion from t_agencia a where a.Idagencia = (select af.IdAgencia from t_agenciaoficina af where af.IdOficina = av.oficinaId) ) as nomAgencia," 
			+" (select NamePuntoVenta from t_puntoventa where IdPuntoVenta = tb.puntoVentaId) as nomPuntoVenta, "
			+" (select nombre +' '+apellido_paterno from t_usuario where idusuario = tb.idusuario) as nomUsuario "
			+" from t_asientoventa av "
			+" inner join t_trackingboleto tb on av.idasientoventa = tb.idasientoventa" 
			+" inner join t_programacion prog on prog.IdProgramacion = av.IdProgramacion"
			+" inner join t_asiento asie on asie.IdAsiento = av.IdAsiento"
			+" where tb.puntoVentaId=#{idPuntoVenta} and av.estado = 'VENTA' "
			+" and av.fechaventa between #{fInicio} and #{fFin} order by av.fechaventa desc ")
	public List<VentaDetalle> obtenerVentaDetalle(@Param("fInicio") Date fInicio,@Param("fFin") Date fFin,@Param("idPuntoVenta") String idPuntoVenta)
			throws Exception;
       		    //FIN PISCOYA
		
	
	@Update("update t_asientoventa set documentoPersona=#{documentoPersona},documentoEmpresa=#{documentoEmpresa},sexo=#{sexo} where idasientoventa=#{idasientoventa}")
	@Options(flushCache=true,useCache=true)
	public void actualizarCambioDatosPasajero(@Param("documentoPersona") String documentoPersona,@Param("documentoEmpresa") String documentoEmpresa, @Param("sexo") String sexo,@Param("idasientoventa") Integer idasientoventa) throws Exception;
	
	
	//espejo asientoVenta
	@Insert("INSERT INTO t_asientoVentaPostergadoFA SELECT * FROM t_asientoventa asv where asv.idasientoventa = #{idasientoventa}")
	public void replicarAsientoVenta(@Param("idasientoventa") Integer idasientoventa) throws Exception;
	
	@Select("select * from t_asientoVentaPostergadoFA where idAsientoventa=#{p_IdAsientoVenta}")
	public AsientoVenta findAsientoVentaFAById(
			@Param("p_IdAsientoVenta") Integer idasientoventa) throws Exception;
	
}

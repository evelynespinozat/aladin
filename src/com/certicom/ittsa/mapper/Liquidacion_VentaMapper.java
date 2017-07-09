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
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Oficina;

public interface Liquidacion_VentaMapper {

	
	@Select("select * from t_liquidacion_venta order by IdLiquidacion_venta desc")
	public List<Liquidacion_Venta> findAll() throws Exception;
	

	@Select("select * from t_liquidacion_venta where IdLiquidacion_venta = #{idLiquidacion_venta}")
	public Liquidacion_Venta findById(@Param("idLiquidacion_venta") Integer idLiquidacion_venta) throws Exception;
	
	@Insert("insert into t_liquidacion_venta (TipoDocumento,Documento,IdPuntoVenta, FDocumento, Area, SubTotal, Igv,Total,Estado,TotalEfectivo,TotalTarjeta,TotalCredito,Proceso,FormaPago,IdUsuario,OrigenPago,FormaEntrega,MovimientoCaja,TipoTarjeta) "
			+ "values (#{tipoDocumento},#{documento},#{idPuntoVenta},#{fDocumento},#{area},#{subTotal},#{igv},#{total},#{estado},#{totalEfectivo},#{totalTarjeta},#{totalCredito},#{proceso},#{formaPago},#{idUsuario},#{origenPago},#{formaEntrega},#{movimientoCaja},#{tipoTarjeta})")
	public void crearLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta) throws Exception;
	
	@Delete("delete  from t_liquidacion_venta  where IdLiquidacion_venta = #{idLiquidacion_venta}")
	@Options(flushCache=true)
	public void eliminarLiquidacion_Venta(@Param("idLiquidacion_venta") Integer idLiquidacion_venta) throws Exception;
	
	@Update("update t_liquidacion_venta set Documento = #{documento},IdPuntoVenta=#{idPuntoVenta} ,FDocumento=#{fDocumento}, Area =#{area}, SubTotal = #{subTotal}, Igv = #{igv},Total = #{total}, Estado = #{estado} where IdLiquidacion_venta= #{idLiquidacion_venta}")
	@Options(flushCache=true,useCache=true)
    public void actualizaLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta) throws Exception;
	
	@Update("update t_liquidacion_venta set TotalEfectivo = #{totalEfectivo}, TotalTarjeta = #{totalTarjeta},TotalCredito = #{totalCredito} where IdLiquidacion_venta= #{idLiquidacion_venta}")
	@Options(flushCache=true,useCache=true)
    public void anularLiquidacion_Venta(Liquidacion_Venta liquidacion_Venta) throws Exception;
	
	
	// 14/03/2014
	@Select("select distinct Proceso as proceso from t_liquidacion_venta where Estado = 1 and Proceso IS NOT null and Area = 'CARGO'")
	public List<Liquidacion_Venta>  listarProcesos() throws Exception;
	
	@Select("select distinct Proceso as proceso from t_liquidacion_venta where Estado = 1 and Proceso IS NOT null and Area = 'VENTAS'")
	public List<Liquidacion_Venta>  listarProcesosVenta() throws Exception;
	
	@Select("select FormaPago as formaPago,SUM( case when TotalEfectivo is not null and TotalTarjeta is null then TotalEfectivo else CASE WHEN  "
							+ " TotalTarjeta is not null and TotalEfectivo is null then TotalTarjeta else case when "
							+ "	TotalEfectivo is not null and TotalTarjeta is not null then TotalEfectivo +TotalTarjeta else "
							+ "	CASE WHEN TotalEfectivo is  null and TotalTarjeta is null then totalcredito end end end end) as totalxFPago "
							+ "from t_liquidacion_venta where Proceso = #{proceso} and Area = 'CARGO' and FormaPago is not null "
							+ "and cast(FDocumento as date) >= #{fecha} group by FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionxFPago(@Param("fecha") Date fecha ,@Param("proceso") String proceso ) throws Exception;
	
	@Select("select Proceso as proceso, FormaPago as formaPago, "
			+ "SUM( CASE when TotalEfectivo is not null and TotalTarjeta is null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalTarjeta is not null and TotalEfectivo is null then TotalTarjeta "
			+ "ELSE CASE WHEN TotalEfectivo is not null and TotalTarjeta is not null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalEfectivo is  null and TotalTarjeta is null then totalcredito "
			+ "END END END END) as totalxFPago "
			+ "from t_liquidacion_venta where Proceso = #{proceso} and Area = 'CARGO' and FormaPago is not null AND MovimientoCaja = 'INGRESO' "
			+ "and cast(FDocumento as date) >= #{fecha} and IdPuntoVenta = #{idPuntoVenta} and estLiqCounter IS NULL group by Proceso, FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionxFPagoPV(@Param("fecha") Date fecha ,@Param("proceso") String proceso,@Param("idPuntoVenta") Integer IdPuntoVenta ) throws Exception;
	
	@Select("select Proceso as proceso, CASE WHEN FormaPago = 'CREDITO' THEN 'NOTA COBRANZA' ELSE FormaPago END AS formaPago, "
			+ "SUM( CASE WHEN TotalEfectivo is not null and TotalTarjeta is null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalTarjeta is not null and TotalEfectivo is null then TotalTarjeta "
			+ "ELSE CASE WHEN TotalEfectivo is not null and TotalTarjeta is not null then TotalTarjeta "
			+ "ELSE	CASE WHEN TotalEfectivo is  null and TotalTarjeta is null then totalcredito end end end end) as totalxFPago "
			+ "FROM t_liquidacion_venta where Proceso = #{proceso} and Area = 'CARGO' and FormaPago is not null AND (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO') ) "
			+ "and cast(FDocumento as date) >= #{fecha} and IdPuntoVenta = #{idPuntoVenta} and estLiqCounter IS NULL group by Proceso, FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionxFPagoPVEgreso(@Param("fecha") Date fecha ,@Param("proceso") String proceso,@Param("idPuntoVenta") Integer IdPuntoVenta ) throws Exception;
	
	@Select("select l.Proceso as proceso, l.FormaPago as formaPago, "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE when l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalEfectivo "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito "
			+ "END END END END) as totalxFPago from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ "where l.Proceso = #{proceso} and Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' "
			+ "and cast(l.FDocumento as date) = #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL group by l.Proceso,l.FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionxFPagoOFI(@Param("fecha") Date fecha ,@Param("proceso") String proceso ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	@Select("select l.Proceso as proceso, CASE WHEN l.FormaPago = 'CREDITO' THEN 'NOTA COBRANZA' ELSE l.FormaPago END AS formaPago,  "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalTarjeta "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito end end end end) as totalxFPago "
			+ " from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ " where l.Proceso = #{proceso} and Area = 'CARGO' and l.FormaPago is not null AND (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO') ) "
			+ " and cast(l.FDocumento as date) >= #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL group by l.Proceso, l.FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionxFPagoOFIEgreso(@Param("fecha") Date fecha ,@Param("proceso") String proceso ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	@Update("update t_liquidacion_venta set estLiqCounter = 'PRE' where IdPuntoVenta = #{idPuntoVenta} and cast(FDocumento as date)>=#{fecha} and Area = 'CARGO' AND estLiqCounter is null ")
	@Options(flushCache=true)
	public void preliquidarxPuntoVentaCargo(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer IdPuntoVenta) throws Exception;
	
	
	@Select("select lv.TipoDocumento, u.nombre +' '+ u.apellido_paterno +' '+ u.apellido_materno as userPV, pv.NamePuntoVenta as namePV " +   
			"from ((t_liquidacion_venta lv inner join t_puntoventa pv on lv.IdPuntoVenta = pv.IdPuntoVenta) "+ 
			"inner join t_agenciaoficina ao on pv.IdOficina = ao.IdOficina) "+
			"inner join t_usuario u on pv.IdPuntoVenta = u.idpuntoventa " +
	        "where ao.IdOficina = #{idOficina} AND lv.Area = 'CARGO' AND cast(lv.FDocumento as date)>= #{fecha} AND estLiqCounter is null AND u.idusuario = lv.IdUsuario "+
	        "group by lv.TipoDocumento, u.nombre +' '+ u.apellido_paterno +' '+ u.apellido_materno, pv.NamePuntoVenta  ")
	public List<Liquidacion_Venta> listarPreLiquidaciones(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;

	@Update("update l set l.estLiqJCounter = 'LIQ' FROM t_liquidacion_venta l inner join t_puntoventa pv on pv.IdPuntoVenta = l.IdPuntoVenta  "
			+ " where pv.IdOficina = #{idOficina} and l.estLiqCounter = 'PRE' and cast(l.FDocumento as date)>= #{fecha} and  l.Area = 'CARGO' ")
	@Options(flushCache=true)
	public void liquidarxOficina(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;
	
	@Select("select * from t_liquidacion_venta where Documento = #{documento}")
	public Liquidacion_Venta findByDocumento(@Param("documento") String documento) throws Exception;
	
	@Select("SELECT SUM(l.TotalEfectivo) FROM t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "+  
			"where l.Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' and l.TotalEfectivo is not null "+
			"and cast(l.FDocumento as date) >= #{fecha} and estLiqJCounter IS NULL and pv.IdOficina = #{idOficina} ")
	public Double calcularEfectivoxOficina(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;

	@Select("SELECT SUM(l.TotalEfectivo) FROM t_liquidacion_venta l "+  
			"where l.Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' and l.TotalEfectivo is not null "+
			"and cast(l.FDocumento as date) >= #{fecha} and  l.IdPuntoVenta  = #{idPuntoVenta} and estLiqCounter IS NULL ")
	public Double calcularEfectivoxPuntoVenta(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;

	@Select("SELECT v.TipoDocumento, "+
	"(SELECT top 1 a.Documento FROM t_liquidacion_venta a WHERE a.TipoDocumento = v.TipoDocumento and cast(a.FDocumento as date) >= #{fecha} and a.Area = 'CARGO' and a.IdPuntoVenta = #{idPuntoVenta} and a.estLiqCounter IS NULL order by IdLiquidacion_venta asc) as minDocumento, "+
	"(SELECT top 1 a.Documento FROM t_liquidacion_venta a WHERE a.TipoDocumento = v.TipoDocumento and cast(a.FDocumento as date) >= #{fecha} and a.Area = 'CARGO' and a.IdPuntoVenta = #{idPuntoVenta} and a.estLiqCounter IS NULL order by IdLiquidacion_venta desc) as maxDocumento "+
	"FROM t_liquidacion_venta v "+
	"WHERE cast(v.FDocumento as date) >= #{fecha} and v.Area = 'CARGO' and v.IdPuntoVenta = #{idPuntoVenta} and v.estLiqCounter IS NULL GROUP BY v.TipoDocumento ")
	public List<Liquidacion_Venta> listarSeriesLiquidacion(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer idPuntoVenta)throws Exception;
	
	@Select("SELECT v.TipoDocumento, "+
			"(SELECT top 1 a.Documento FROM t_liquidacion_venta a WHERE a.TipoDocumento = v.TipoDocumento and cast(a.FDocumento as date) >= #{fecha} and a.Area = 'VENTAS' and a.IdPuntoVenta = #{idPuntoVenta} and a.estLiqCounter IS NULL order by IdLiquidacion_venta asc) as minDocumento, "+
			"(SELECT top 1 a.Documento FROM t_liquidacion_venta a WHERE a.TipoDocumento = v.TipoDocumento and cast(a.FDocumento as date) >= #{fecha} and a.Area = 'VENTAS' and a.IdPuntoVenta = #{idPuntoVenta} and a.estLiqCounter IS NULL order by IdLiquidacion_venta desc) as maxDocumento "+
			"FROM t_liquidacion_venta v "+
			"WHERE cast(v.FDocumento as date) >= #{fecha} and v.Area = 'VENTAS' and v.IdPuntoVenta = #{idPuntoVenta} and v.estLiqCounter IS NULL GROUP BY v.TipoDocumento ")
			public List<Liquidacion_Venta> listarSeriesLiquidacionVentas(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer idPuntoVenta)throws Exception;
	
	@Select("select p.NamePuntoVenta, v.FormaPago, SUM( CASE WHEN v.TotalEfectivo is not null and v.TotalTarjeta is null then v.TotalEfectivo "+ 
			" ELSE CASE WHEN v.TotalTarjeta is not null and v.TotalEfectivo is null then v.TotalTarjeta "+
			" ELSE CASE when v.TotalEfectivo is not null and v.TotalTarjeta is not null then v.TotalEfectivo "+ 
			" ELSE	CASE WHEN v.TotalEfectivo is  null and v.TotalTarjeta is null then v.totalcredito "+
			" END END END END) as totalxFPago "+
			"from t_liquidacion_venta v inner join t_puntoventa p on v.IdPuntoVenta = p.IdPuntoVenta "+ 
			"where v.MovimientoCaja = 'INGRESO' and cast(v.FDocumento as date) >= #{fecha}  and p.IdOficina = #{idOficina} "+ 
			"group by  p.NamePuntoVenta, v.FormaPago "+
			"order by  p.NamePuntoVenta, v.FormaPago")
	public List<Liquidacion_Venta> listaLiquidacionIngresoDetxOficina(@Param("fecha") Date fecha, @Param("idOficina") Integer idOficina )throws Exception;
	
	@Select("select p.NamePuntoVenta, v.FormaPago, SUM( CASE WHEN v.TotalEfectivo is not null and v.TotalTarjeta is null then v.TotalEfectivo "+ 
			" ELSE CASE WHEN v.TotalTarjeta is not null and v.TotalEfectivo is null then v.TotalTarjeta "+
			" ELSE CASE when v.TotalEfectivo is not null and v.TotalTarjeta is not null then v.TotalEfectivo "+ 
			" ELSE	CASE WHEN v.TotalEfectivo is  null and v.TotalTarjeta is null then v.totalcredito "+
			" END END END END) as totalxFPago "+
			"from t_liquidacion_venta v inner join t_puntoventa p on v.IdPuntoVenta = p.IdPuntoVenta "+ 
			"where (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO')) "+
			"and cast(v.FDocumento as date) >= #{fecha}  and p.IdOficina = #{idOficina}  "+
			"group by  p.NamePuntoVenta, v.FormaPago "+
			"order by  p.NamePuntoVenta, v.FormaPago")
	public List<Liquidacion_Venta> listaLiquidacionEgresoDetxOficina()throws Exception;
	
	// metodos para la liquidacion total por oficina ya sea el proceso Cargo o Encomienda
	
	@Select("select  l.Proceso as proceso, l.FormaPago as formaPago, "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE when l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalEfectivo "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito "
			+ "END END END END) as totalxFPago from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ "where  Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' "
			+ "and cast(l.FDocumento as date) >= #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL "
			+ "group by l.Proceso, l.FormaPago ")
	public List<Liquidacion_Venta>  rptLiquidacionxFPagoOFI(@Param("fecha") Date fecha ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	
	@Select("select l.Proceso as proceso, l.FormaPago as formaPago, "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalTarjeta "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito end end end end) as totalxFPago "
			+ " from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ " where Area = 'CARGO' and l.FormaPago is not null AND (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO') ) "
			+ " and cast(l.FDocumento as date) >= #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL group by l.Proceso, l.FormaPago ")
	public List<Liquidacion_Venta>  rptLiquidacionxFPagoOFIEgreso(@Param("fecha") Date fecha ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	@Select("select FormaPago as formaPago, "
			+ "SUM( CASE when TotalEfectivo is not null and TotalTarjeta is null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalTarjeta is not null and TotalEfectivo is null then TotalTarjeta "
			+ "ELSE CASE WHEN TotalEfectivo is not null and TotalTarjeta is not null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalEfectivo is  null and TotalTarjeta is null then totalcredito "
			+ "END END END END) as totalxFPago "
			+ "from t_liquidacion_venta where Proceso = #{proceso} and Area = 'VENTAS' and FormaPago is not null AND MovimientoCaja = 'INGRESO' "
			+ "and cast(FDocumento as date) >= #{fecha} and IdPuntoVenta = #{idPuntoVenta} and estLiqCounter IS NULL group by FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionVentasxFPagoPV(@Param("fecha") Date fecha ,@Param("proceso") String proceso,@Param("idPuntoVenta") Integer IdPuntoVenta ) throws Exception;
	
	@Select("SELECT SUM(l.TotalEfectivo) FROM t_liquidacion_venta l "+  
			"where l.Area = 'VENTAS' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' and l.TotalEfectivo is not null "+
			"and cast(l.FDocumento as date) >= #{fecha} and  l.IdPuntoVenta  = #{idPuntoVenta} and estLiqCounter IS NULL ")
	public Double calcularEfectivoVentasxPuntoVenta(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer idPuntoVenta) throws Exception;
	
	@Select("select l.FormaPago as formaPago, "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE when l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalEfectivo "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito "
			+ "END END END END) as totalxFPago from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ "where l.Proceso = #{proceso} and Area = 'VENTAS' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' "
			+ "and cast(l.FDocumento as date) >= #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL group by l.FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionVentasxFPagoOFI(@Param("fecha") Date fecha ,@Param("proceso") String proceso ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	@Select("SELECT SUM(l.TotalEfectivo) FROM t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "+  
			"where l.Area = 'VENTAS' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' and l.TotalEfectivo is not null "+
			"and cast(l.FDocumento as date) >= #{fecha} and estLiqJCounter IS NULL and pv.IdOficina = #{idOficina} ")
	public Double calcularEfectivoVentasxOficina(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;
	
	@Select("select FormaPago as formaPago, "
			+ "SUM( CASE WHEN TotalEfectivo is not null and TotalTarjeta is null then TotalEfectivo "
			+ "ELSE CASE WHEN TotalTarjeta is not null and TotalEfectivo is null then TotalTarjeta "
			+ "ELSE CASE WHEN TotalEfectivo is not null and TotalTarjeta is not null then TotalTarjeta "
			+ "ELSE	CASE WHEN TotalEfectivo is  null and TotalTarjeta is null then totalcredito end end end end) as totalxFPago "
			+ "FROM t_liquidacion_venta where Proceso = #{proceso} and Area = 'VENTAS' and FormaPago is not null AND (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO') ) "
			+ "and cast(FDocumento as date) >= #{fecha} and IdPuntoVenta = #{idPuntoVenta} and estLiqCounter IS NULL group by FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionVentasxFPagoPVEgreso(@Param("fecha") Date fecha ,@Param("proceso") String proceso,@Param("idPuntoVenta") Integer IdPuntoVenta ) throws Exception;
	
	@Select("select l.FormaPago as formaPago, "
			+ "SUM( CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is null then l.TotalEfectivo "
			+ "ELSE CASE WHEN l.TotalTarjeta is not null and l.TotalEfectivo is null then l.TotalTarjeta "
			+ "ELSE CASE WHEN l.TotalEfectivo is not null and l.TotalTarjeta is not null then l.TotalTarjeta "
			+ "ELSE	CASE WHEN l.TotalEfectivo is  null and l.TotalTarjeta is null then l.totalcredito end end end end) as totalxFPago "
			+ " from t_liquidacion_venta l inner join t_puntoventa pv on l.IdPuntoVenta = pv.IdPuntoVenta "
			+ " where l.Proceso = #{proceso} and Area = 'VENTAS' and l.FormaPago is not null AND (MovimientoCaja = 'EGRESO' OR (MovimientoCaja = 'INGRESO' AND FormaPago = 'MIXTO') ) "
			+ " and cast(l.FDocumento as date) >= #{fecha}  and pv.IdOficina = #{idOficina} and estLiqJCounter IS NULL group by l.FormaPago ")
	public List<Liquidacion_Venta>  listarLiquidacionVentasxFPagoOFIEgreso(@Param("fecha") Date fecha ,@Param("proceso") String proceso ,@Param("idOficina") Integer idOficina ) throws Exception;
	
	@Update("update t_liquidacion_venta set estLiqCounter = 'PRE' where IdPuntoVenta = #{idPuntoVenta} and cast(FDocumento as date)>=#{fecha} and Area = 'CARGO' AND estLiqCounter is null ")
	@Options(flushCache=true)
	public void preliquidarVentasxPuntoVentaCargo(@Param("fecha") Date fecha,@Param("idPuntoVenta") Integer IdPuntoVenta) throws Exception;
	
	@Select("select lv.TipoDocumento, u.nombre +' '+ u.apellido_paterno +' '+ u.apellido_materno as userPV, pv.NamePuntoVenta as namePV " +   
			"from ((t_liquidacion_venta lv inner join t_puntoventa pv on lv.IdPuntoVenta = pv.IdPuntoVenta) "+ 
			"inner join t_agenciaoficina ao on pv.IdOficina = ao.IdOficina) "+
			"inner join t_usuario u on pv.IdPuntoVenta = u.idpuntoventa " +
	        "where ao.IdOficina = #{idOficina} AND lv.Area = 'VENTAS' AND cast(lv.FDocumento as date)>= #{fecha} AND estLiqCounter is null AND u.idusuario = lv.IdUsuario "+
	        "group by lv.TipoDocumento, u.nombre +' '+ u.apellido_paterno +' '+ u.apellido_materno, pv.NamePuntoVenta  ")
	public List<Liquidacion_Venta> listarPreLiquidacionesVenta(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;
	
	@Update("update l set l.estLiqJCounter = 'LIQ' FROM t_liquidacion_venta l inner join t_puntoventa pv on pv.IdPuntoVenta = l.IdPuntoVenta  "
			+ " where pv.IdOficina = #{idOficina} and l.estLiqCounter = 'PRE' and cast(l.FDocumento as date)>= #{fecha} and  l.Area = 'CARGO' ")
	@Options(flushCache=true)
	public void liquidarVentasxOficina(@Param("fecha") Date fecha,@Param("idOficina") Integer idOficina) throws Exception;
	
	public List<Liquidacion_Venta> rptReporteMecanizado(Liquidacion_Venta liquidacion_Venta) throws Exception;
	
	@Select("select g.MontoGiro, g.TotalCobrado, l.MovimientoCaja " 
			+" from ((t_liquidacion_venta l inner join t_puntoventa p on l.IdPuntoVenta = p.IdPuntoVenta) "
			+"						    inner join t_giro g on g.Documento = l.Documento) "
			+"			 where l.Proceso = 'GIRO' and Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' " 
			+"			 and cast(l.FDocumento as date) = #{fecha} "
			+"			 and p.IdPuntoVenta = #{idPVenta} ")
	public List<Giro>  listarDisgregadoGiroPuntoVenta(@Param("fecha") Date fecha ,@Param("idPVenta") Integer idPVenta ) throws Exception;
	
	@Select("select g.MontoGiro, g.TotalCobrado, l.MovimientoCaja " 
			+" from ((t_liquidacion_venta l inner join t_puntoventa p on l.IdPuntoVenta = p.IdPuntoVenta) "
			+"						    inner join t_giro g on g.Documento = l.Documento) "
			+"			 where l.Proceso = 'GIRO' and Area = 'CARGO' and l.FormaPago is not null AND l.MovimientoCaja = 'INGRESO' " 
			+"			 and cast(l.FDocumento as date) = #{fecha} "
			+"			 and p.idOficina = #{idOficina} ")
	public List<Giro>  listarDisgregadoGiroOficina(@Param("fecha") Date fecha ,@Param("idOficina") Integer idOficina ) throws Exception;
	
}

package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Gasto;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Liquidacion;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.mapper.Historial_AsigPCTMapper;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsignacionService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.Detalle_LiquidacionServices;
import com.certicom.ittsa.services.ExcepcionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.GastoService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.LiquidacionServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TerramozaService;
import com.certicom.ittsa.services.TripulacionServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;

@ManagedBean(name = "liquidacionxServicioMB")
@ViewScoped
public class LiquidacionxServicioMB extends GenericBeans implements Serializable {

	private Programacion programacion;
	private Liquidacion liquidacion;
	private Detalle_Liquidacion detalle_liquidacion;
	private List<Programacion> listaProgramacionAsig;
	private List<Programacion> listaProgramacionAsigFilter;
	private List<Flota> listaBuses;
	private List<Flota> listaBusesFilter;
	private List<Piloto> listaPilotos;
	private List<Piloto> listaCopilotos;
	
	//27-02-2014
	private Programacion pilotoProxServ;
	private Programacion copilotoProxServ;
	private Programacion terramoza1ProxServ;
	private Programacion terramoza2ProxServ;
	
	private List<Detalle_Liquidacion> listaDetalleLiq;

	private Date fecInicio;
	private Date fecFin;
	private Integer idBus;
	private Boolean bandCerrar;
	private Boolean editar;

	// services
	private ProgramacionService programacionService;
	private ServicioService servicioServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private MenuServices menuServices;
	private FlotaService busServices;
	private PilotoService pilotoServices;
	private TerramozaService terramozaServices;
	private TripulacionServices tripulacionServices;
	private Detalle_LiquidacionServices detalle_liquidacionServices;
	private LiquidacionServices liquidacionServices;
	
	private GastoService gastoService;
	private ProgramacionService programacionServices;
	private AgenciaService agenciaServices;
	private FlotaService flotaServices;
	private TripulacionServices triulacionService;
	private PilotoService pilotoService;
	private AsignacionService asignacionService;
	// datos Log
	private Log log;
	private LogMB logmb;
	
	private Gasto gasto;
	private List<Gasto> listaGasto;
	private Boolean resumenGenerado;
	private BigDecimal totalEgreso;
	private BigDecimal totalIngreso;
	private BigDecimal totalSaldo;
	private Terramoza terramoza;
	private List<Programacion> listaProgramacion;

	private Date fechaInicio;
	private Date fechaFin;
	private Programacion programacionSelec;
	public LiquidacionxServicioMB() {
		;
	}

	@PostConstruct
	public void inicia() 
	{

		this.agenciaService = new AgenciaService();
		this.agenciaServices =  new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.menuServices = new MenuServices();
		this.busServices = new FlotaService();
		this.pilotoServices = new PilotoService();
		this.terramozaServices = new TerramozaService();
		this.tripulacionServices = new TripulacionServices();
		this.liquidacionServices = new LiquidacionServices();
		this.detalle_liquidacionServices = new Detalle_LiquidacionServices();
		this.servicioServices = new ServicioService();
		this.programacionServices = new ProgramacionService();
		this.agenciaService =  new AgenciaService();
		this.flotaServices = new FlotaService();
		this.triulacionService = new TripulacionServices();
		this.pilotoService = new PilotoService();
		this.asignacionService = new AsignacionService();


		
		this.gastoService = new GastoService();
		
		this.listaGasto = new ArrayList<>();
		this.nuevoGasto();
		this.editar = Boolean.FALSE;
		this.resumenGenerado = Boolean.FALSE;
		this.totalEgreso =  new BigDecimal(0);
		this.totalIngreso = new BigDecimal(0);
		this.totalSaldo = new BigDecimal(0);
		
		//this.programacion = new Programacion();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			this.listaBuses = busServices.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:progFlota");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public void buscarProgramacion()
	{
		try {	
				listaProgramacionAsig = programacionService.findProg_AsigByFecha_Bus(getFecInicio(), getFecFin(), getIdBus());
				
				for(Programacion p2 : listaProgramacionAsig)
				{
					Tripulacion t2 = tripulacionServices.findByIdProgramacion(p2.getIdProgramacion());
					System.out.println(p2.getIdTerramoza() + "  " + p2.getIdTerramoza2());
					if(t2!=null){
						if(t2.getIdBus()!=null){
							Flota f = busServices.findById(p2.getIdBus());
							p2.setFlota(f);
						}
						if(t2.getIdPiloto()!=null){
							Piloto pil = pilotoServices.findById(t2.getIdPiloto());
							p2.setNombPiloto(pil.getApellidos()+ " "+pil.getNombres()); 
							p2.setIdPiloto(t2.getIdPiloto());
						}
						if(t2.getIdCopiloto()!=null){
							Piloto cop = pilotoServices.findById(t2.getIdCopiloto());
							p2.setNombCopiloto(cop.getApellidos()+" "+cop.getNombres()); 
							p2.setIdCopiloto(t2.getIdCopiloto());
						}
					}
					
					if(t2.getIdTerramoza()!=null){
						Terramoza terr1 = terramozaServices.findById(t2.getIdTerramoza());
						p2.setNomTerramoza(terr1.getApellidos() + " "+terr1.getNombres()); 
					}
					if(t2.getIdTerramoza2()!=null){
						Terramoza terr2 = terramozaServices.findById(t2.getIdTerramoza2());
						p2.setNomTerramoza2(terr2.getApellidos() + " "+terr2.getNombres());
					}					
					
					Liquidacion l = liquidacionServices.findByIdProgramacion(p2.getIdProgramacion());
					if(l!=null){
						listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
						Integer count = detalle_liquidacionServices.countByEstado(l.getIdLiquidacion()); 
						if(listaDetalleLiq.size()== count){
							p2.setBandPorCerrar(Boolean.FALSE);
							if(l.isEstado()){
								p2.setBandCerrado(Boolean.TRUE); 
								p2.setBandPorCerrar(Boolean.TRUE);
							}
						}else p2.setBandPorCerrar(Boolean.TRUE);
					}else{
						p2.setBandPorCerrar(Boolean.TRUE);
						p2.setBandCerrado(Boolean.FALSE);
					}
					
					//sumando gastos
					BigDecimal totalGas = this.gastoService.sumaGastosByProgramacion(p2.getIdProgramacion());
					if( totalGas != null)
					{
						p2.setTotalGastos(totalGas);
					}else{
						p2.setTotalGastos(new BigDecimal(0));
					}
					
					//obteniendolos ingresos
					BigDecimal totalIng = this.asignacionService.sumaAsignacionByProgramacion(p2.getIdProgramacion());
					if(totalIng != null)
					{
						p2.setAsignacionViatico(totalIng);
					}else{
						p2.setAsignacionViatico(new BigDecimal(0));
					}
					
					
					/*<Inicio>jpiscoya 10.02.2015*/
					generarLiquidacionResumen();
					/*<Fin>jpiscoya 10.02.2015*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.resumenGenerado = Boolean.FALSE;
	}

	
	public void listarGastos(Programacion prog)
	{
		this.listaGasto.clear();
		this.gasto = new Gasto();
		this.programacionSelec = prog;
		this.gasto.setFecha(prog.getfSalida());
		this.gasto.setMonto(new BigDecimal(0));
		this.gasto.setConcepto(new String("PEAJE"));
		try {
			this.listaGasto = this.gastoService.findByProgramacion(prog.getIdProgramacion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void anadirGasto(Programacion program)
	{
		//buscarProgramacion();
		System.out.println("la progracion es:"+program.getOrigen());
		try {
			if(this.editar)
			{
				//actualizando
				this.gasto.setObservaciones(this.gasto.getObservaciones().trim().toUpperCase());
				this.gastoService.actualizarGasto(gasto);
			}else{
				//persisitendo
				this.gasto.setProgramacionId(program.getIdProgramacion());
				this.gasto.setObservaciones(this.gasto.getObservaciones().trim().toUpperCase());
				this.gastoService.crearGasto(this.gasto);
				this.listaGasto.clear();
				this.listaGasto = this.gastoService.findByProgramacion(program.getIdProgramacion());
			}
			
			
			BigDecimal suma = new BigDecimal(0);
			for(Gasto gas : this.listaGasto)
			{
				suma = suma.add(gas.getMonto());
			}
			
			program.setTotalGastos(suma);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.nuevoGasto();
		this.editar = Boolean.FALSE;
	}
	
	
	public void nuevoGasto()
	{
		this.gasto= new Gasto();
		//this.gasto.setFecha(new Date());
	}
	/*
	public void sumarGastoProgramacion()
	{
		BigDecimal suma = new BigDecimal(0);
		for(Gasto gas : this.listaGasto)
		{
			suma = suma.add(gas.getMonto());
		}
		this.programacionSelec.setTotalGastos(suma);

	}
	*/
	/*
	public void liquidacion(Programacion p)
	{

		this.programacion = p;
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		String fecha_ConFormato = formato.format(p.getfSalida());
		programacion.setfSalidaString(fecha_ConFormato); 
		
		System.out.println("IdProgramacion: "+ p.getIdProgramacion());
		Liquidacion l;
		try {
			l = liquidacionServices.findByIdProgramacion(p.getIdProgramacion());
			if(l!=null){
				listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
			}else listaDetalleLiq = new ArrayList<>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	
	
	/*
	public void nuevoDetalle(){
		this.editar = Boolean.FALSE;
		this.detalle_liquidacion = new Detalle_Liquidacion();
	}
	*/
	
	/*
	public void editarDetalle(Detalle_Liquidacion det){
		this.editar = Boolean.TRUE;
		this.detalle_liquidacion = det;
	}
	
	public void guardarDetalleLiq(){
		Liquidacion l;
		try {
			l = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			
			if(editar){
				detalle_liquidacion.setIdLiquidacion(l.getIdLiquidacion());
				detalle_liquidacionServices.actualizarDetalleLiq(this.detalle_liquidacion);
				listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
				FacesUtils.showFacesMessage("Detalle de liquidación actualizado correctamente.",Constante.INFORMACION);
			}else{
				if(l==null){
					Liquidacion liq = new Liquidacion();
					liq.setIdProgramacion(this.programacion.getIdProgramacion());
					liquidacionServices.crearLiquidacion(liq);
					liq = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
					detalle_liquidacion.setIdLiquidacion(liq.getIdLiquidacion());
					detalle_liquidacionServices.crearDetalleLiq(this.detalle_liquidacion);
					listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(liq.getIdLiquidacion());
				}else{
					detalle_liquidacion.setIdLiquidacion(l.getIdLiquidacion());
					detalle_liquidacionServices.crearDetalleLiq(this.detalle_liquidacion);
					listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
				}	
				
				FacesUtils.showFacesMessage("Detalle registrado correctamente.",Constante.INFORMACION);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	*/
	public void eliminarDetalle()
	{
		
		//remover d ela bd
		//...
		try {
			this.gastoService.eliminarGasto(this.gasto.getIdGasto());
			this.listaGasto.remove(this.gasto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.nuevoGasto();
		
		/*
		try {
			Liquidacion l = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			detalle_liquidacionServices.eliminarDetalleLiq(detalle_liquidacion.getIdDetalleLiq());
			listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
			FacesUtils.showFacesMessage("Detalle eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
	}
	
	public void generarLiquidacionResumen()
	{
		this.totalEgreso = new BigDecimal(0);
		this.totalIngreso = new BigDecimal(0);
		this.totalSaldo = new BigDecimal(0);
		
		BigDecimal sumaGastos = null;
		BigDecimal ingreso = null;
		try {
			
			for(Programacion prg : this.listaProgramacionAsig)
			{
				if(!this.gastoService.findByProgramacion(prg.getIdProgramacion()).isEmpty())
				{
					sumaGastos = this.gastoService.sumaGastosByProgramacion(prg.getIdProgramacion());
					ingreso = this.asignacionService.sumaAsignacionByProgramacion(prg.getIdProgramacion());
					
					if(sumaGastos == null || ingreso == null)
					{
						FacesUtils.showFacesMessage("Ingrese y/o asigne gastos a las programaciones listadas.", 1);
						break;
					}else{
						this.totalEgreso = sumaGastos.add(this.totalEgreso);
						//buscando los ingresos:
						this.totalIngreso = ingreso.add(this.totalIngreso);
					}
					
				}

			}
		
			this.totalSaldo = this.totalIngreso.subtract(this.totalEgreso);
			this.resumenGenerado = Boolean.TRUE;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public void cambiarEstado(Detalle_Liquidacion det){
		
		if(det.isEstado()){
			det.setEstado(Boolean.FALSE);
		}else{
			det.setEstado(Boolean.TRUE);
		}
		
		try {
			this.detalle_liquidacionServices.actualizarEstado(det);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	
	*/
	
	/*
	public void cerrarLiquidacion(){
		try {
			Liquidacion l = liquidacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
			listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
			Double total = 0.0;
			for(Detalle_Liquidacion det : listaDetalleLiq){
				total = total + det.getCosto().doubleValue();
			}
			BigDecimal big = new BigDecimal(total);
			l.setEstado(Boolean.TRUE); 
			l.setTotal(big); 
			liquidacionServices.actualizarLiquidacion(l); 
			
			listaProgramacionAsig = programacionService.findProg_AsigByFecha_Bus(getFecInicio(), getFecFin(), getIdBus());
			for(Programacion p2 : listaProgramacionAsig){
				Tripulacion t2 = tripulacionServices.findByIdProgramacion(p2.getIdProgramacion());
				if(t2!=null){
					if(t2.getIdBus()!=null){
						Flota f = busServices.findById(p2.getIdBus());
						p2.setFlota(f);						
					}
					if(t2.getIdPiloto()!=null){
						Piloto pil = pilotoServices.findById(t2.getIdPiloto());
						p2.setNombPiloto(pil.getApellidos()+ " "+pil.getNombres()); 
					}
					if(t2.getIdCopiloto()!=null){
						Piloto cop = pilotoServices.findById(t2.getIdCopiloto());
						p2.setNombCopiloto(cop.getApellidos()+" "+cop.getNombres()); 
					}
					if(t2.getIdTerramoza()!=null){
						Terramoza terr1 = terramozaServices.findById(t2.getIdTerramoza());
						p2.setNomTerramoza(terr1.getApellidos() + " "+terr1.getNombres()); 
					}
					if(t2.getIdTerramoza2()!=null){
						Terramoza terr2 = terramozaServices.findById(t2.getIdTerramoza2());
						p2.setNomTerramoza2(terr2.getApellidos() + " "+terr2.getNombres());
					}					
				}
				
				Liquidacion liq = liquidacionServices.findByIdProgramacion(p2.getIdProgramacion());
				if(liq!=null){
					if(liq.isEstado()){
						p2.setBandCerrado(Boolean.TRUE); 
						p2.setBandPorCerrar(Boolean.TRUE);
					}else p2.setBandCerrado(Boolean.FALSE); 
				}
		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	/*
	public void actualizarBanCerrar(){
		buscarProgramacion();
	}
	*/
	
	
	public void imprimirPDF() throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha_InicioConFormato = formato.format(getFecInicio());
			String fecha_InicioFinFormato = formato.format(getFecFin());
			
			Flota f = busServices.findById(getIdBus());

			buscarProgramacion();
			for(Programacion p : listaProgramacionAsig)
			{
				Liquidacion liq = liquidacionServices.findByIdProgramacion(p.getIdProgramacion());
				if(liq!=null){
					List<Detalle_Liquidacion> listaDet = detalle_liquidacionServices.findByIdLiquidacion(liq.getIdLiquidacion());
					p.setListaDet(listaDet); 
				}				
			}
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_FECHA", fecha_InicioConFormato+" - "+fecha_InicioFinFormato);
			input.put("P_LOGO", p_logo);
			input.put("P_BUS", f.getNumero().toString());
			/*<Inicio> jpiscoya 10.02.2015*/		
			input.put("P_INGRESO",this.totalIngreso.toString());
			input.put("P_EGRESO",this.totalEgreso.toString());
			input.put("P_SALDO",this.totalSaldo.toString());
			/*<Fin> jpiscoya 10.02.2015*/
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptLiquidacionxServicio.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaProgramacionAsig);

			ExportarArchivo.executePdf(bytes, "reporteLiquidaciones.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
	}
	
	public void consultarProximoServicio(Programacion p){
		
		try {
		//PILOTO
		Programacion filter = new Programacion();
		filter.setIdPiloto(p.getIdPiloto());
		filter.setVarTripulante("PILOTO");
		
		if(p.getIdPiloto() != null){
		
		System.out.println("piloto " + p.getNombPiloto());
			List<Programacion> pilotos = this.programacionService.buscarProxServicioPiloto(filter);
			if(pilotos !=null){
				this.pilotoProxServ = pilotos.get(0);
				this.pilotoProxServ.setNomPiloto(p.getNombPiloto());
			}
			
		}		
		
		//COPILOTO
		if(p.getIdCopiloto() != null){
		
		filter.setIdCopiloto(p.getIdCopiloto());
		filter.setVarTripulante("COPILOTO");
			List<Programacion> copilotos = this.programacionService.buscarProxServicioPiloto(filter);
			if(copilotos !=null) {
			this.copilotoProxServ = copilotos.get(0);
			this.copilotoProxServ.setNomCopiloto(p.getNombCopiloto());
			}
			
		}	
		
		//TERRAMOZA1
		if(p.getIdTerramoza() != null){
		filter.setIdTerramoza(p.getIdTerramoza());
		filter.setVarTripulante("TERRAMOZA1");
			List<Programacion> terramozas1 = this.programacionService.buscarProxServicioTerramoza(filter);
			if(terramozas1 !=null) {
				this.terramoza1ProxServ = terramozas1.get(0);
				this.terramoza1ProxServ.setNomTerramoza(p.getNomTerramoza2());
			}
				
		}
		
		//TERRAMOZA2	
		if(p.getIdTerramoza2() !=null){
		filter.setIdTerramoza(p.getIdTerramoza());
		filter.setVarTripulante("TERRAMOZA2");
				List<Programacion> terramozas2 = this.programacionService.buscarProxServicioTerramoza(filter);
				if(terramozas2 !=null){ 
					this.terramoza2ProxServ = terramozas2.get(0);
					this.terramoza2ProxServ.setNomTerramoza2(p.getNomTerramoza2());
					}
		
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setearTerramoza(Integer terraId)
	{
		try {
			this.terramoza = this.terramozaServices.findById(terraId);
			this.fechaInicio = Utiles.obtenerPrimerDiaDelMes();
			this.fechaFin = Utiles.obtenerUltimoDiaDelMes();
			this.listaProgramacion = new ArrayList<>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.bandBuscar = Boolean.FALSE;
	}
	
	
	public void consultarTracking()
	{
		
		try {
			listaProgramacion = programacionServices.findProgByFecha_IdTerramoza(this.fechaInicio, this.fechaFin, terramoza.getIdTerramoza());
			for(Programacion p : listaProgramacion)
			{
				Servicio s = servicioServices.findById(p.getIdServicio());
				p.setDescServicio(s.getDescripcion()); 
				Agencia orig = agenciaServices.findById(p.getOrigen());
				p.setNombOrigen(orig.getDescripcion());
				Agencia dest = agenciaServices.findById(p.getDestino());
				p.setNombDestino(dest.getDescripcion());
				
				
				//inicio nuevo 
				Tripulacion tripu = this.triulacionService.findByIdProgramacion(p.getIdProgramacion());
				Piloto pil = null;
				Piloto cop = null;
				Flota f = flotaServices.findById(p.getIdBus());
				p.setNumeroBus(f.getNumero().toString()); 
				
				if(tripu.getFlagTempPiloto() && tripu.getFlagTempCopiloto())
				{
					pil = pilotoService.findById(tripu.getIdPiloto());
					cop = pilotoService.findById(tripu.getIdCopiloto());
					
				}else if ( tripu.getFlagTempPiloto() && !tripu.getFlagTempCopiloto() ){
					
					pil = pilotoService.findById(tripu.getIdPiloto());
					cop = pilotoService.findById(f.getCopiloto());
					
				}else if(!tripu.getFlagTempPiloto() && tripu.getFlagTempCopiloto() ){
					
					pil = pilotoService.findById(f.getPiloto());
					cop = pilotoService.findById(tripu.getIdCopiloto());
					
				}else if(!tripu.getFlagTempPiloto() && !tripu.getFlagTempCopiloto() ){
					pil = pilotoService.findById(f.getPiloto());
					cop = pilotoService.findById(f.getCopiloto());
				}
				
				p.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				p.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres());
				//System.out.println("copiloto:"+p.getNombCopiloto());
				//fin nuevo

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
		
	/**********************Getter's and Seter's************************/

	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public ProgramacionService getProgramacionService() {
		return programacionService;
	}

	public void setProgramacionService(ProgramacionService programacionService) {
		this.programacionService = programacionService;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Piloto> getListaPilotos() {
		return listaPilotos;
	}

	public void setListaPilotos(List<Piloto> listaPilotos) {
		this.listaPilotos = listaPilotos;
	}

	public List<Piloto> getListaCopilotos() {
		return listaCopilotos;
	}

	public void setListaCopilotos(List<Piloto> listaCopilotos) {
		this.listaCopilotos = listaCopilotos;
	}

	public List<Programacion> getListaProgramacionAsig() {
		return listaProgramacionAsig;
	}

	public void setListaProgramacionAsig(List<Programacion> listaProgramacionAsig) {
		this.listaProgramacionAsig = listaProgramacionAsig;
	}

	public List<Flota> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Flota> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public List<Programacion> getListaProgramacionAsigFilter() {
		return listaProgramacionAsigFilter;
	}

	public void setListaProgramacionAsigFilter(
			List<Programacion> listaProgramacionAsigFilter) {
		this.listaProgramacionAsigFilter = listaProgramacionAsigFilter;
	}

	public Boolean getBandCerrar() {
		return bandCerrar;
	}

	public void setBandCerrar(Boolean bandCerrar) {
		this.bandCerrar = bandCerrar;
	}

	public List<Detalle_Liquidacion> getListaDetalleLiq() {
		return listaDetalleLiq;
	}

	public void setListaDetalleLiq(List<Detalle_Liquidacion> listaDetalleLiq) {
		this.listaDetalleLiq = listaDetalleLiq;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public Detalle_Liquidacion getDetalle_liquidacion() {
		return detalle_liquidacion;
	}

	public void setDetalle_liquidacion(Detalle_Liquidacion detalle_liquidacion) {
		this.detalle_liquidacion = detalle_liquidacion;
	}

	public Programacion getPilotoProxServ() {
		return pilotoProxServ;
	}

	public Programacion getCopilotoProxServ() {
		return copilotoProxServ;
	}

	public Programacion getTerramoza2ProxServ() {
		return terramoza2ProxServ;
	}

	public void setPilotoProxServ(Programacion pilotoProxServ) {
		this.pilotoProxServ = pilotoProxServ;
	}

	public void setCopilotoProxServ(Programacion copilotoProxServ) {
		this.copilotoProxServ = copilotoProxServ;
	}

	public void setTerramoza2ProxServ(Programacion terramoza2ProxServ) {
		this.terramoza2ProxServ = terramoza2ProxServ;
	}

	public Programacion getTerramoza1ProxServ() {
		return terramoza1ProxServ;
	}

	public void setTerramoza1ProxServ(Programacion terramoza1ProxServ) {
		this.terramoza1ProxServ = terramoza1ProxServ;
	}

	public Gasto getGasto() {
		return gasto;
	}

	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}

	public List<Gasto> getListaGasto() {
		return listaGasto;
	}

	public void setListaGasto(List<Gasto> listaGasto) {
		this.listaGasto = listaGasto;
	}

	public Boolean getResumenGenerado() {
		return resumenGenerado;
	}

	public void setResumenGenerado(Boolean resumenGenerado) {
		this.resumenGenerado = resumenGenerado;
	}

	public BigDecimal getTotalEgreso() {
		return totalEgreso;
	}

	public BigDecimal getTotalIngreso() {
		return totalIngreso;
	}

	public void setTotalEgreso(BigDecimal totalEgreso) {
		this.totalEgreso = totalEgreso;
	}

	public void setTotalIngreso(BigDecimal totalIngreso) {
		this.totalIngreso = totalIngreso;
	}

	public BigDecimal getTotalSaldo() {
		return totalSaldo;
	}

	public void setTotalSaldo(BigDecimal totalSaldo) {
		this.totalSaldo = totalSaldo;
	}


	public Terramoza getTerramoza() {
		return terramoza;
	}

	public void setTerramoza(Terramoza terramoza) {
		this.terramoza = terramoza;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Programacion getProgramacionSelec() {
		return programacionSelec;
	}

	public void setProgramacionSelec(Programacion programacionSelec) {
		this.programacionSelec = programacionSelec;
	}
	
	
	
	

}

package com.certicom.ittsa.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Tarifa;
import com.certicom.ittsa.domain.Tarifa_Producto;
import com.certicom.ittsa.domain.TipoDocumento;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.TrackingGiro;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Frecuencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.RutaServicio;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.EncomiendaAlmacenservice;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.GiroServices;
import com.certicom.ittsa.services.HistorialEncomiendaServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.RutaServicioService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TarifaServices;
import com.certicom.ittsa.services.Tarifa_ProductoServices;
import com.certicom.ittsa.services.TipoDocumentoServices;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.certicom.ittsa.services.TrackingGiroServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name="entregaEncomiendaMB")
@ViewScoped
public class EntregaEncomiendaMB  extends GenericBeans implements Serializable{

	/****************Atributos*******************/
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	private Agencia agencia;
	private Encomienda encomienda;
	private Encomienda encomiendaFilter;
	private Encomienda encFilterImpr;
	private List<Usuario> listaUsuarioRec;

	private Integer idOrigen;
	private Integer idDestino;
	private Date fechaInicio;
	private Date fechaFin;
	private String fechaEntrega;
	private String observacion;
	private String montoEfectivo;
	private String montoTarjeta;
	private List<Destino> listaAgDestinos;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Encomienda> listaRptEncomContr;
	private List<Oficina> listaOficinas;
	
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Destino> listaDestImpr;
	private List<Agencia> listaAgeImpr;
	private List<Oficina> listaOficImpr;
	
	private AgenciaService agenciaService;
	private DestinoServices destinoService;
	private EncomiendaServices encomiendaServices;
	private Liquidacion_VentaServices liquidacionServices;
	private EncomiendaAlmacenservice encomiendaAlmacenservice;
	private TrackingEncomiendaServices trackingEncomiendaServices;
    private UsuarioServices usuarioServices;
	private PersonaServices personaServives;
	private EmpresaService empresaService;
	private PuntoVentaService puntoVentaService;
	private OficinaService oficinaService;
	
	private String _tipoDocumento;
	private boolean _nombre;
	private boolean _razonSocial;
	
	private String formaPago;
	private boolean _efectivo;
	private boolean _tarjeta;
	
	public EntregaEncomiendaMB(){}
	
	@PostConstruct
	public void inicia()
	{
		this.encomiendaFilter = new Encomienda();
		this.encomiendaFilter.setFecIni(new Date());
		this.encomiendaFilter.setFecFin(new Date());
		this.encFilterImpr = new Encomienda();
		this._nombre = true;
		this._razonSocial = false;
		this.listaUsuarioRec = new ArrayList<>();
		this.encomienda = new Encomienda();
		this._efectivo = true;
		this._tarjeta = false;
		
		this.agenciaService = new AgenciaService();
		this.destinoService = new DestinoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.liquidacionServices = new Liquidacion_VentaServices();
		this.encomiendaAlmacenservice = new EncomiendaAlmacenservice();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.usuarioServices = new UsuarioServices();
		this.personaServives = new PersonaServices();
		this.empresaService = new EmpresaService();
		this.puntoVentaService = new PuntoVentaService();
		this.oficinaService = new OficinaService();
		
		this.listaOficImpr = new ArrayList<Oficina>();
		this.listaRptEncomContr = new ArrayList<>();
		this.listaOficinas = new ArrayList<>();
		
		try {
			//this.listaUsuarioRec = this.usuarioServices.buscarPorPerfil(getUsuarioLogin().getIdoficina(), getUsuarioLogin().getCod_perfil());
			this.listaUsuarioRec = this.usuarioServices.buscarPorOficina(getUsuarioLogin().getIdoficina());
			this.agencia = this.agenciaService.findById(usuarioLogin.getIdagencia());
			this.listaAgDestinos = destinoService.obtenerDestino(agencia.getIdagencia());
			
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listaAgeImpr = this.listaAgencias;	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date fechaHoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		this.fechaEntrega = formato.format(fechaHoy);  
	}
	
	public void evaluaTipDoc(){
		if(_tipoDocumento.equals("DNI")){
			this._nombre = true;
			this._razonSocial = false;
		}else{
			this._nombre = false;
			this._razonSocial = true;
		}
	}
	
	public void buscarEncomiendas(){
		this.listaEncomienda = new ArrayList<>();
		try {
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasDesembarcadas(this.encomiendaFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinas(){
		try {
			this.listaOficinas = new ArrayList<>();
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.encomiendaFilter.getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarEncomiendasAntigua(){
		this.listaEncomienda = new ArrayList<>();
		Utils u = new Utils();	
		try {
			if(getIdOrigen()!=null){
				if(getFechaInicio()!=null && getFechaFin()!=null){
					Date fechaFinal = u.sumaDias(fechaFin, 1);
					this.listaEncomienda = this.encomiendaServices.findEncomiendaxCobrarByFecha(getIdOrigen(),getIdDestino(), getFechaInicio(), fechaFinal);
					for(Encomienda e: listaEncomienda){
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
						String fRegistro = formato.format(e.getfRegistro());
						e.setfRegistroString(fRegistro); 
					}
				}else{
					this.listaEncomienda = this.encomiendaServices.findEncomiendaxCobrarByOrig_Dest(getIdOrigen(),getIdDestino());
					for(Encomienda e: listaEncomienda){
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
						String fRegistro = formato.format(e.getfRegistro());
						e.setfRegistroString(fRegistro); 
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void confirmarEntrega(){
		this.encomienda.setEstado(5); 
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			//this.encomiendaServices.actualizarEstadoEncomienda(this.encomienda);
			// estado 5 ENTREGADO
			this.encomiendaServices.actualizarEstadoEncomienda2(this.encomienda.getIdEncomienda(), 5,this.encomienda.getIdProgramacion());
			this.encomienda.setfRecojo(new Date());
			this.encomiendaServices.actualizarValoresRecepcion(this.encomienda);
			// actualizando el estado anterior en el tracking
			this.trackingEncomiendaServices.actualizarEstadoAnterior(this.encomienda.getIdEncomienda());
			// agregando el nuevo estado ...
			
			TrackingEncomienda tke = new TrackingEncomienda();
			tke.setIdEncomienda(this.encomienda.getIdEncomienda());
			tke.setIdEstado(5);
			tke.setIdBus(this.encomienda.getIdBus());
			tke.setEstadoActual(Boolean.TRUE);
			tke.setIdProgramacion(this.encomienda.getIdProgramacion());
			
			this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			this.encomiendaAlmacenservice.actualizarExistenciaEncomienda(this.encomienda.getIdEncomienda());
			
			context.execute("dlgEntregar.hide()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buscarEncomiendas();
	}
	
	public void confirmarPagoEntrega(){
		
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
	   	    
	   	    double totalPorCobrar = 0.00;
	   	    double cantIngreso = 0.00;
	   	    double mixtoTarjeta = 0.00;
	   	    double mixtoEfectivo = 0.00;
	   	    totalPorCobrar = this.encomienda.getTotalCobrado();
	   	    boolean completo = true;
	   	    boolean mixtoOk = true;
	   	    
	   		if(getFormaPago().equals("E")){
				  if(new Double(this.montoEfectivo) >= totalPorCobrar){
					 cantIngreso = totalPorCobrar;
					 completo = true; 
				  } else{
					  completo = false; 
				  }
			}else if(getFormaPago().equals("T")){
				if(new Double(this.montoTarjeta) >= totalPorCobrar){
					cantIngreso = totalPorCobrar;
					 completo = true; 
				  } else{
					  completo = false; 
				  }
			}else if(getFormaPago().equals("M")){
				if(new Double(this.montoEfectivo) + new Double(this.montoTarjeta) >= totalPorCobrar){
					 completo = true; 
					 if(new Double(this.montoEfectivo) >= totalPorCobrar){
						 FacesUtils.showFacesMessage("El monto efectivo es mayor o igual al total, la forma de pago deberia ser efectivo.",Constante.ERROR);
						 mixtoOk = false;
					 } else if(new Double(this.montoTarjeta) >= totalPorCobrar){
						 FacesUtils.showFacesMessage("El monto por tarjeta es mayor o igual al total, la forma de pago deberia ser por tarjeta.",Constante.ERROR);
						 mixtoOk = false;
					 } else{
						 mixtoOk = true;
						 mixtoEfectivo = new Double(this.montoEfectivo);
						 mixtoTarjeta = new Double(this.montoTarjeta);
					 }
					 
				  } else{
					  completo = false; 
				  }
			}
	   	    
	   	if(completo){  
	   		if(mixtoOk){
		 
		try {
			//Pagar encomienda 
			Liquidacion_Venta liquidacion = new Liquidacion_Venta();
			liquidacion.setDocumento(this.encomienda.getComprobante());
			
			liquidacion.setTipoDocumento(this.encomienda.getTipoDocumento());
			
			liquidacion.setSubTotal(0.0); 
			liquidacion.setIgv(0.0); 
			liquidacion.setTotal(this.encomienda.getTotalCobrado()); 
			liquidacion.setIdUsuario(this.usuarioLogin.idusuario);
			liquidacion.setIdPuntoVenta(this.usuarioLogin.getIdpuntoventa());
			
			if(getFormaPago().equals("E")){
				liquidacion.setFormaPago("EFECTIVO");
				liquidacion.setTotalEfectivo(cantIngreso);
				//liquidacion.setTotalEfectivo(new Double(this.montoEfectivo));
			}else if(getFormaPago().equals("T")){
				liquidacion.setFormaPago("TARJETA");
				liquidacion.setTotalTarjeta(cantIngreso);
				//liquidacion.setTotalTarjeta(new Double(this.montoTarjeta));
			}else if(getFormaPago().equals("M")){
				liquidacion.setFormaPago("MIXTO");
				liquidacion.setTotalEfectivo(mixtoEfectivo);
				liquidacion.setTotalTarjeta(mixtoTarjeta);
		//		liquidacion.setTotalEfectivo(new Double(this.montoEfectivo));
			//	liquidacion.setTotalTarjeta(new Double(this.montoTarjeta));
			}
			
			liquidacion.setfDocumento(new Date()); 
			liquidacion.setArea("CARGO"); 
			liquidacion.setProceso("ENCOMIENDA");
			liquidacion.setEstado(1); 
			liquidacion.setOrigenPago("OFICINA");
			liquidacion.setFormaEntrega("OFICINA");
			
			liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
			this.liquidacionServices.crearLiquidacion_Venta(liquidacion);
			//
			this.montoEfectivo = null;
			this.montoTarjeta = null;
			this.formaPago = "E";
			
			
			
			//-----------------------------------------------------------------------------
			// estado 5 ENTREGADO
			this.encomienda.setEstado(5);
			
			this.encomiendaServices.actualizarEstadoEncomienda2(this.encomienda.getIdEncomienda(), 5,this.encomienda.getIdProgramacion());
			this.encomienda.setfRecojo(new Date());
			this.encomiendaServices.actualizarValoresRecepcion(this.encomienda);
			// actualizando el estado anterior en el tracking
			this.trackingEncomiendaServices.actualizarEstadoAnterior(this.encomienda.getIdEncomienda());
			// agregando el nuevo estado ...
			
			TrackingEncomienda tke = new TrackingEncomienda();
			tke.setIdEncomienda(this.encomienda.getIdEncomienda());
			tke.setIdEstado(5);
			tke.setIdBus(this.encomienda.getIdBus());
			tke.setEstadoActual(Boolean.TRUE);
			tke.setIdProgramacion(this.encomienda.getIdProgramacion());
			
			this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			this.encomiendaAlmacenservice.actualizarExistenciaEncomienda(this.encomienda.getIdEncomienda());
			
			context.execute("dlgEntregar.hide()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buscarEncomiendas();
	   		} else{
	   			context.addCallbackParam("esValido", Boolean.FALSE );
	   		}
	   	} else{
	   		context.addCallbackParam("esValido", Boolean.FALSE );
	   		FacesUtils.showFacesMessage("las cantidad(es) ingresada no coincide con el total a pagar.",Constante.ERROR);
	   	}
		
	}
	
	public void evaluarTipoPago(){
		
		switch (this.formaPago) {
		case "E":
			    this._efectivo = true;
			    this._tarjeta = false;
			break;
        case "T":
        	    this._efectivo = false;
        	    this._tarjeta = true;
			break;
        case "M":
        	    this._efectivo = true;
    	        this._tarjeta = true;
			break;	
		}
		
	}
	
	
	public void setearEncomienda(Encomienda e){
		this.encomienda = e;
		this._tipoDocumento = "DNI";
		this.encomienda.setNumeroDocRecoge(this.encomienda.getDniDestinatario1());
		this.encomienda.setNombreRecoge(this.encomienda.getDestinatario1());
		//this.listaUsuarioRec = this.usuarioServices;
		this.encomienda.setIdUsuarioEntrega(getUsuarioLogin().getIdusuario());
		this.montoEfectivo = encomienda.getTotalCobrado().toString();
	}
	
	
	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoService.obtenerDestino(this.encomiendaFilter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinasxAgencia(){
		try {
		//	this.listaOficImpr = this.oficinaService.listaOficinasXAgencia(this.encFilterImpr.getIdAgencia());
			this.listaDestImpr = destinoService.obtenerDestino(this.encFilterImpr.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarPersona(){
		try {
			if(_tipoDocumento.equals("DNI")){
				Persona p = this.personaServives.findByNroDocumento(this.encomienda.getNumeroDocRecoge());
				if(p!=null) this.encomienda.setNombreRecoge(p.getNombres()+ " " +  p.getAPaterno()+ " " + p.getAMaterno());
			}else if (_tipoDocumento.equals("RUC")){
				Empresa e = this.empresaService.findByNroRUC(this.encomienda.getNumeroDocRecoge());
				if(e!=null) this.encomienda.setNombreRecoge(e.getRazonSocial());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resetearFiltro(){
		this.encFilterImpr = new Encomienda();
	}
	
	public void obtenerListarReporteContra(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		try {
			this.listaRptEncomContr = this.encomiendaServices.rptEncomiendasContraEntrega(this.encFilterImpr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirXLS(){
		String par_origen = "", par_destino = "",par_estado = "";
		for (int i = 0; i < this.listaAgeImpr.size(); i++) {
			Agencia r = this.listaAgeImpr.get(i);
			if(r.getIdagencia() == this.encFilterImpr.getIdAgencia()){
				par_origen = r.getDescripcion();
				break;
			}
		}
		
		for (int i = 0; i < this.listaDestImpr.size(); i++) {
			Destino h = this.listaDestImpr.get(i);
			if(h.getDestino() == this.encFilterImpr.getIdDestino()){
				par_destino = h.getDesDestino();
				break;
			}
		}
		
		switch (this.encFilterImpr.getEstado()) {
			case 0:
				par_estado = "TODOS";
				break;
			case 1:
				par_estado = "RECIBIDO";
				break;
			case 2:
				par_estado = "EMBARCADO";
				break;
			}
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaRptEncomContr.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_ORIGEN", par_origen);
			input.put("P_DESTINO", par_destino);
			input.put("P_ESTADO", par_estado);
			input.put("P_TOTAL", total.toString());
			input.put("P_FSISTEMA", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptEncomiendaCntrga.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportXls(path, input, this.listaRptEncomContr,false);
			ExportarArchivo.executeExccel(bytes, "EncomiendasContraEntrega.xls");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	/*############################-------setter y getter---------##############################3*/
	
	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public boolean is_efectivo() {
		return _efectivo;
	}

	public void set_efectivo(boolean _efectivo) {
		this._efectivo = _efectivo;
	}

	public boolean is_tarjeta() {
		return _tarjeta;
	}

	public void set_tarjeta(boolean _tarjeta) {
		this._tarjeta = _tarjeta;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String get_tipoDocumento() {
		return _tipoDocumento;
	}

	public void set_tipoDocumento(String _tipoDocumento) {
		this._tipoDocumento = _tipoDocumento;
	}

	public void setListaAgDestinos(List<Destino> listaAgDestinos) {
		this.listaAgDestinos = listaAgDestinos;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}
	
	public Encomienda getEncomienda() {
		return encomienda;
	}

	public void setEncomienda(Encomienda encomienda) {
		this.encomienda = encomienda;
	}

	public List<Encomienda> getListaEncomienda() {
		return listaEncomienda;
	}

	public void setListaEncomienda(List<Encomienda> listaEncomienda) {
		this.listaEncomienda = listaEncomienda;
	}

	public List<Encomienda> getListaEncomiendaFilter() {
		return listaEncomiendaFilter;
	}

	public void setListaEncomiendaFilter(List<Encomienda> listaEncomiendaFilter) {
		this.listaEncomiendaFilter = listaEncomiendaFilter;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public Encomienda getEncomiendaFilter() {
		return encomiendaFilter;
	}

	public void setEncomiendaFilter(Encomienda encomiendaFilter) {
		this.encomiendaFilter = encomiendaFilter;
	}

	public boolean is_nombre() {
		return _nombre;
	}

	public void set_nombre(boolean _nombre) {
		this._nombre = _nombre;
	}

	public boolean is_razonSocial() {
		return _razonSocial;
	}

	public void set_razonSocial(boolean _razonSocial) {
		this._razonSocial = _razonSocial;
	}

	public List<Usuario> getListaUsuarioRec() {
		return listaUsuarioRec;
	}

	public void setListaUsuarioRec(List<Usuario> listaUsuarioRec) {
		this.listaUsuarioRec = listaUsuarioRec;
	}

	public String getMontoEfectivo() {
		return montoEfectivo;
	}

	public void setMontoEfectivo(String montoEfectivo) {
		this.montoEfectivo = montoEfectivo;
	}

	public String getMontoTarjeta() {
		return montoTarjeta;
	}

	public void setMontoTarjeta(String montoTarjeta) {
		this.montoTarjeta = montoTarjeta;
	}

	public Encomienda getEncFilterImpr() {
		return encFilterImpr;
	}

	public void setEncFilterImpr(Encomienda encFilterImpr) {
		this.encFilterImpr = encFilterImpr;
	}

	public List<Agencia> getListaAgeImpr() {
		return listaAgeImpr;
	}

	public void setListaAgeImpr(List<Agencia> listaAgeImpr) {
		this.listaAgeImpr = listaAgeImpr;
	}

	public List<Oficina> getListaOficImpr() {
		return listaOficImpr;
	}

	public void setListaOficImpr(List<Oficina> listaOficImpr) {
		this.listaOficImpr = listaOficImpr;
	}

	public List<Encomienda> getListaRptEncomContr() {
		return listaRptEncomContr;
	}

	public void setListaRptEncomContr(List<Encomienda> listaRptEncomContr) {
		this.listaRptEncomContr = listaRptEncomContr;
	}

	public List<Destino> getListaDestImpr() {
		return listaDestImpr;
	}

	public void setListaDestImpr(List<Destino> listaDestImpr) {
		this.listaDestImpr = listaDestImpr;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}
	
}

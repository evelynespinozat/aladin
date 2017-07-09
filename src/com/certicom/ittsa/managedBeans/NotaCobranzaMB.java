package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.ListableBeanFactory;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DetalleNotaCobranza;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.NotaCobranza;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleNotaCobranzaService;
import com.certicom.ittsa.services.LiquidacionServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.NotaCobranzaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="notaCobranzaMB")
@ViewScoped
public class NotaCobranzaMB extends GenericBeans implements Serializable{

	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	@ManagedProperty(value="#{loginMB.desOficina}")
	private String oficinaUser;
	private NotaCobranza notaCobranza;
	private Integer _idOrigen;
	private Integer _idDestino;
	private List<Agencia> listaOrigen;
	private List<Destino> listaDestinos;
	private List<NotaCobranza> listaNotaCobranza;
	private List<DetalleNotaCobranza> listaDetalleNotaCobranza;
	private List<NotaCobranza> listaRptNotaCobranza;
	private String formaPago;
    private String montoPago;
    
    private boolean  btnImprimir = true;
	
	//services
	private DestinoServices destinoServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private NotaCobranzaService notaCobranzaService;
	private DetalleNotaCobranzaService detalleNotaCobranzaService;
	private Liquidacion_VentaServices liquidacionServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public NotaCobranzaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.listaOrigen = new ArrayList<>();
		this.listaDestinos = new ArrayList<>();
		this.listaDetalleNotaCobranza = new ArrayList<>();
		this.notaCobranza = new NotaCobranza();
		
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.notaCobranzaService = new NotaCobranzaService();
		this.detalleNotaCobranzaService = new DetalleNotaCobranzaService();
		this.liquidacionServices = new Liquidacion_VentaServices();
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaOrigen = this.agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:notaCobranza");
			
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obtenerDestino(){
		try {
			this.listaDestinos = this.destinoServices.obtenerDestino(this._idOrigen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarNotaCobranza(){
		try {
			this.listaNotaCobranza = new ArrayList<>();
			this.listaNotaCobranza = this.notaCobranzaService.buscarNotaCobranzaPorCobrar(this._idOrigen, this._idDestino);
			this.listaDetalleNotaCobranza = new ArrayList<>();
			this.listaRptNotaCobranza = new ArrayList<>();
			for (int i = 0; i < this.listaNotaCobranza.size(); i++) {
				NotaCobranza nc = this.listaNotaCobranza.get(i);
				if(nc.getEstado().equals("PAGADO")){
					this.listaRptNotaCobranza.add(nc);
				}
			}
			if(this.listaRptNotaCobranza.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarDetalleNotaCobranza(NotaCobranza nota){
		try {
			this.listaDetalleNotaCobranza = this.detalleNotaCobranzaService.obtenerDetalleNotaCobranza(nota.getIdNotaCobranza());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pagarNotaCobranza(){
		try {
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
	   	    
			this.notaCobranzaService.pagarNotaCobranza(this.notaCobranza.getIdNotaCobranza(),this.notaCobranza.getNumeroFisico());
			
			//registrando la liquidacion de la nota de cobranza
				Liquidacion_Venta liquidacion = new Liquidacion_Venta();
				liquidacion.setDocumento(this.notaCobranza.getSerieNotaCobranza() +"-" + this.notaCobranza.getNumeroNotaCobranza());
				liquidacion.setTipoDocumento("NOTA COBRANZA"); 
				liquidacion.setSubTotal(0.0); 
				liquidacion.setIgv(0.0); 
				liquidacion.setTotal(new  Double(this.montoPago)); 
				liquidacion.setIdUsuario(this.usuarioLogin.idusuario);
				liquidacion.setIdPuntoVenta(this.usuarioLogin.getIdpuntoventa());
				
				if(getFormaPago().equals("E")){
					liquidacion.setTotalEfectivo(liquidacion.getTotal());
					liquidacion.setFormaPago("EFECTIVO");
				}else if(getFormaPago().equals("T")){
					liquidacion.setFormaPago("TARJETA");
					liquidacion.setTotalTarjeta(liquidacion.getTotal());
				}
				
				liquidacion.setfDocumento(new Date()); 
				liquidacion.setArea("CARGO"); 
				liquidacion.setProceso("ENCOMIENDA");
				liquidacion.setEstado(1); 
				liquidacion.setOrigenPago("OFICINA");
				liquidacion.setFormaEntrega("OFICINA");
				
				liquidacion.setMovimientoCaja(Constante.MOVIMIENYO_CAJA_INGRESO);
				this.liquidacionServices.crearLiquidacion_Venta(liquidacion);
			//fin de liquidacion
			
//			this.listaNotaCobranza = new ArrayList<>();
//			this.listaNotaCobranza = this.notaCobranzaService.buscarNotaCobranzaPorCobrar(this._idOrigen, this._idDestino);
//			this.listaDetalleNotaCobranza = new ArrayList<>();
			buscarNotaCobranza();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaRptNotaCobranza.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_OFICINA", getOficinaUser());
			input.put("P_FECHA", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptNotaCobranza.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptNotaCobranza);
			ExportarArchivo.executePdf(bytes, "NotaCobranza.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	

	//**set an get
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(String montoPago) {
		this.montoPago = montoPago;
	}

	public List<NotaCobranza> getListaNotaCobranza() {
		return listaNotaCobranza;
	}

	public void setListaNotaCobranza(List<NotaCobranza> listaNotaCobranza) {
		this.listaNotaCobranza = listaNotaCobranza;
	}

	public List<Destino> getListaDestinos() {
		return listaDestinos;
	}

	public void setListaDestinos(List<Destino> listaDestinos) {
		this.listaDestinos = listaDestinos;
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

	public NotaCobranza getNotaCobranza() {
		return notaCobranza;
	}

	public void setNotaCobranza(NotaCobranza notaCobranza) {
		this.notaCobranza = notaCobranza;
	}

	public Integer get_idOrigen() {
		return _idOrigen;
	}

	public void set_idOrigen(Integer _idOrigen) {
		this._idOrigen = _idOrigen;
	}

	public Integer get_idDestino() {
		return _idDestino;
	}

	public void set_idDestino(Integer _idDestino) {
		this._idDestino = _idDestino;
	}

	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}

	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public List<DetalleNotaCobranza> getListaDetalleNotaCobranza() {
		return listaDetalleNotaCobranza;
	}

	public void setListaDetalleNotaCobranza(
			List<DetalleNotaCobranza> listaDetalleNotaCobranza) {
		this.listaDetalleNotaCobranza = listaDetalleNotaCobranza;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getOficinaUser() {
		return oficinaUser;
	}

	public void setOficinaUser(String oficinaUser) {
		this.oficinaUser = oficinaUser;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
	
	
	
}


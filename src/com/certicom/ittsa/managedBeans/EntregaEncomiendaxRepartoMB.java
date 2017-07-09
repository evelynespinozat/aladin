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
import com.certicom.ittsa.domain.EncomiendaDataModel;
import com.certicom.ittsa.domain.Giro;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Personal;
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
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PersonalService;
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

/**
 * @author fchuquilin
 *
 */
@ManagedBean(name="entEncomiendaxRepMB")
@ViewScoped
public class EntregaEncomiendaxRepartoMB  extends GenericBeans implements Serializable{

	public EntregaEncomiendaxRepartoMB(){}
	
	@PostConstruct
	public void inicia()
	{
		
		this.listaRepartidor = new ArrayList<Personal>();
		this.listaUsuarioRec = new ArrayList<>();
		this._nombre = true;
		this.encomienda = new Encomienda();
		
		this.agenciaService = new AgenciaService();
		this.destinoService = new DestinoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.liquidacionServices = new Liquidacion_VentaServices();
		this.personalService = new PersonalService();
		this.encomiendaFilter = new Encomienda();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.encomiendaAlmacenservice = new EncomiendaAlmacenservice();
		this.personaServives = new PersonaServices();
		this.empresaService = new EmpresaService();
		this.usuarioServices = new UsuarioServices();
		
		try {
			agencia = this.agenciaService.findById(usuarioLogin.getIdagencia());
			this.listaAgDestinos = destinoService.obtenerDestino(agencia.getIdagencia());
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listaUsuarioRec = this.usuarioServices.buscarPorPerfil(getUsuarioLogin().getIdoficina(), getUsuarioLogin().getCod_perfil());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date fechaHoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		this.fechaEntrega = formato.format(fechaHoy); 
		this.listaEncomienda = new ArrayList<>();
		
	}
	
	public void buscarEncomiendas(){
		this.listaEncomienda = new ArrayList<>();
		try {
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasDesembarcadasReparto(this.encomiendaFilter);
			this.encomiendaModel = new EncomiendaDataModel(this.listaEncomienda); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void recogerOficina(Encomienda e){
		this.encomienda = new Encomienda();
		this._tipoDocumento = "DNI";
		this.encomienda = e;
		this.encomienda.setNumeroDocRecoge(this.encomienda.getDniDestinatario1());
		this.encomienda.setNombreRecoge(this.encomienda.getDestinatario1());
	}
	
	//
	public void buscarEncomiendasAntigua(){
		this.listaEncomienda = new ArrayList<>();
		Utils u = new Utils();	
		try {
			if(getIdOrigen()!=null){
				if(getFechaInicio()!=null && getFechaFin()!=null){
					Date fechaFinal = u.sumaDias(fechaFin, 1);
					List<Encomienda> listaE = this.encomiendaServices.findEncomiendaxCobrarByFecha(getIdOrigen(),getIdDestino(), getFechaInicio(), fechaFinal);
					for(Encomienda e: listaE){
						if(e.getPrecioReparto()!=null && e.getPrecioReparto()>0){
							SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
							String fRegistro = formato.format(e.getfRegistro());
							e.setfRegistroString(fRegistro); 
							listaEncomienda.add(e);
						}
						
					}
					this.encomiendaModel = new EncomiendaDataModel(this.listaEncomienda); 
				}else{
					List<Encomienda> listaE = this.encomiendaServices.findEncomiendaxCobrarByOrig_Dest(getIdOrigen(),getIdDestino());
					for(Encomienda e: listaE){
						if(e.getPrecioReparto()!=null && e.getPrecioReparto()>0){
							SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
							String fRegistro = formato.format(e.getfRegistro());
							e.setfRegistroString(fRegistro);
							listaEncomienda.add(e);
						}
						 
					}
					this.encomiendaModel = new EncomiendaDataModel(this.listaEncomienda);
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addRepartidor(){
		RequestContext context = RequestContext.getCurrentInstance();
		System.out.println("Tamaño de lista seleccionada: " + this.listaEncomiendaSelected.length);
		this.bultosReparto = 0;
		this.pesototalReparto = 0.0;
		this.totalContEntrega = 0.0;
		this.totalcobradoReparto = 0.0;
		this.btnAsigEnc = Boolean.FALSE;
		this.btnImprimir = Boolean.TRUE;
		
		if(this.listaEncomiendaSelected.length >0){
		
		try {
			context.addCallbackParam("hayEncomiendas", Boolean.TRUE);
			
			this.listaRepartidor = this.personalService.listarPersonalActivo();
			
			for (int i = 0; i < this.listaEncomiendaSelected.length; i++) {
					Encomienda e = this.listaEncomiendaSelected[i];
//					if(){
//						sumar total productos contraentrega
//					}
					this.bultosReparto +=e.getNroBultos();
					this.pesototalReparto +=e.getPesoTotal().doubleValue();
					this.totalcobradoReparto +=e.getTotalCobrado().doubleValue();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		} else{
			FacesUtils.showFacesMessage("Seleccione por lo menos una encomienda.",Constante.ERROR);
			context.addCallbackParam("hayEncomiendas", Boolean.FALSE);
			context.update("sms");
		}
		
	}
	
	public void confirmarEntrega(){
		System.out.println("Encomienda: " + this.encomienda.getIdEncomienda());
		this.encomienda.setEstado(5); 
		try {
			this.encomiendaServices.actualizarEstadoEncomienda(this.encomienda);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buscarEncomiendas();
	}
	
	
	public void setearEncomienda(Encomienda e){
		this.encomienda = e;
	}
	
	
	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoService.obtenerDestino(this.encomiendaFilter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarRepartidorEncomienda(){
		this.listaRptReparto = new ArrayList<Encomienda>();
		
		try {
			this.personalSelected = this.personalService.findById(getIdPersonal());
			for (int i = 0; i < this.listaEncomiendaSelected.length; i++) {
				Encomienda e = this.listaEncomiendaSelected[i];
				
				// actualizando el estado a 6 --REPARTIDO
				this.encomiendaServices.actualizarEstadoEncomienda2(e.getIdEncomienda(),6,e.getIdProgramacion());
				this.encomiendaServices.actualizarRepartidor(e.getIdEncomienda(), this.personalSelected.getIdPersonal());
				// actualizando el estado anterior en el tracking
				this.trackingEncomiendaServices.actualizarEstadoAnterior(e.getIdEncomienda());
				// agregando el nuevo estado en el tracking
				TrackingEncomienda tke = new TrackingEncomienda();
				tke.setIdEncomienda(e.getIdEncomienda());
				tke.setIdEstado(6);
				tke.setIdBus(e.getIdBus());
				tke.setEstadoActual(Boolean.TRUE);
				tke.setIdProgramacion(e.getIdProgramacion());
				
				this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
				
				this.encomiendaAlmacenservice.actualizarExistenciaEncomienda(e.getIdEncomienda());
				
				this.listaRptReparto.add(e);
				
			}
			FacesUtils.showFacesMessage("Encomiendas asignadas al repartidor correctamente, proceda a imprimir.",Constante.INFORMACION);
			this.btnAsigEnc = Boolean.TRUE;
			this.btnImprimir = Boolean.FALSE;
			buscarEncomiendas();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha_reparto = formato.format(new Date());
			
			Integer total = this.listaRptReparto.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FEC_REPARTO", fecha_reparto);
			input.put("P_OFICINA", getOficinaUser());
			input.put("P_RESPONSABLE", this.personalSelected.getNomcompleto());
			input.put("P_IMPORTE_TOTAL", this.totalcobradoReparto.toString());
			input.put("P_TOTAL_ENC", this.bultosReparto.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
		//	input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptRepartoEncomienda.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptReparto);
			ExportarArchivo.executePdf(bytes, "EncomiendasReparto.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void imprimirDetalladoPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha_reparto = formato.format(new Date());
			
			Integer total = this.listaRptReparto.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FEC_REPARTO", fecha_reparto);
			input.put("P_OFICINA", getOficinaUser());
			input.put("P_RESPONSABLE", this.personalSelected.getNomcompleto());
			input.put("P_IMPORTE_TOTAL", this.totalcobradoReparto.toString());
			input.put("P_TOTAL_ENC", this.bultosReparto.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
		//	input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptRepartoEncomiendaDetalle.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaRptReparto);
			ExportarArchivo.executePdf(bytes, "EncomiendasRepartoDetalle.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void buscarPersona(){
		System.out.println("tipo doc " + _tipoDocumento);
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
	
	public void confirmarEntregaOficina(){
		this.encomienda.setEstado(5); 
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			//this.encomiendaServices.actualizarEstadoEncomienda(this.encomienda);
			// estado 5 ENTREGADO
			System.out.println("entto en este metodo  - ---  " + this.encomienda.getIdEncomienda() + " --  " +this.encomienda.getIdProgramacion() );
			this.encomiendaServices.actualizarEstadoEncomienda2(this.encomienda.getIdEncomienda(), 5,this.encomienda.getIdProgramacion());
			this.encomienda.setfRecojo(new Date());
			System.out.println("actualizo");
			this.encomiendaServices.actualizarValoresRecepcion(this.encomienda);
			// actualizando el estado anterior en el tracking
			this.trackingEncomiendaServices.actualizarEstadoAnterior(this.encomienda.getIdEncomienda());
			// agregando el nuevo estado ...
			
			TrackingEncomienda tke = new TrackingEncomienda();
			tke.setIdEncomienda(this.encomienda.getIdEncomienda());
			tke.setIdEstado(5);
			if(this.encomienda.getIdBus()!=null){
				tke.setIdBus(this.encomienda.getIdBus());
			}
			tke.setEstadoActual(Boolean.TRUE);
			tke.setIdProgramacion(this.encomienda.getIdProgramacion());
			
			this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			this.encomiendaAlmacenservice.actualizarExistenciaEncomienda(this.encomienda.getIdEncomienda());
			
			context.execute("dlgRecogoOfi.hide()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buscarEncomiendas();
	}
	
	/****************Atributos*******************/
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	@ManagedProperty(value="#{loginMB.desOficina}")
	private String oficinaUser;
	
	private Agencia agencia;
	private Encomienda encomienda;
	private Encomienda encomiendaFilter;

	private Integer idOrigen;
	private Integer idDestino;
	private Date fechaInicio;
	private Date fechaFin;
	private String fechaEntrega;
	private String observacion;
	
	private String _tipoDocumento;
	private boolean _nombre;
	private boolean _razonSocial;
	
	private List<Destino> listaAgDestinos;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private Encomienda[] listaEncomiendaSelected;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Personal> listaRepartidor;
	private List<Encomienda> listaRptReparto;
	private List<Usuario> listaUsuarioRec;
	private EncomiendaDataModel encomiendaModel;
	
	
	private AgenciaService agenciaService;
	private DestinoServices destinoService;
	private EncomiendaServices encomiendaServices;
	private Liquidacion_VentaServices liquidacionServices;
	private PersonalService personalService;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private EncomiendaAlmacenservice encomiendaAlmacenservice;
	private PersonaServices personaServives;
	private EmpresaService empresaService;
	private UsuarioServices usuarioServices;
	
	
	private Integer bultosReparto;
	private Double pesototalReparto = 0.0;
	private Double totalContEntrega = 0.0;
	private Double totalcobradoReparto = 0.0;
	
	private boolean btnAsigEnc = false;
	private boolean btnImprimir = true;
	
	private Integer idPersonal;
	private Personal personalSelected;
	
	
	/*############################-------setter y getter---------##############################3*/

	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public List<Usuario> getListaUsuarioRec() {
		return listaUsuarioRec;
	}

	public void setListaUsuarioRec(List<Usuario> listaUsuarioRec) {
		this.listaUsuarioRec = listaUsuarioRec;
	}

	public String get_tipoDocumento() {
		return _tipoDocumento;
	}

	public void set_tipoDocumento(String _tipoDocumento) {
		this._tipoDocumento = _tipoDocumento;
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

	public Encomienda[] getListaEncomiendaSelected() {
		return listaEncomiendaSelected;
	}

	public void setListaEncomiendaSelected(Encomienda[] listaEncomiendaSelected) {
		this.listaEncomiendaSelected = listaEncomiendaSelected;
	}

	public EncomiendaDataModel getEncomiendaModel() {
		return encomiendaModel;
	}

	public void setEncomiendaModel(EncomiendaDataModel encomiendaModel) {
		this.encomiendaModel = encomiendaModel;
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

	public List<Personal> getListaRepartidor() {
		return listaRepartidor;
	}

	public void setListaRepartidor(List<Personal> listaRepartidor) {
		this.listaRepartidor = listaRepartidor;
	}

	public Encomienda getEncomiendaFilter() {
		return encomiendaFilter;
	}

	public void setEncomiendaFilter(Encomienda encomiendaFilter) {
		this.encomiendaFilter = encomiendaFilter;
	}

	public Integer getBultosReparto() {
		return bultosReparto;
	}

	public Double getPesototalReparto() {
		return pesototalReparto;
	}

	public Double getTotalContEntrega() {
		return totalContEntrega;
	}

	public void setBultosReparto(Integer bultosReparto) {
		this.bultosReparto = bultosReparto;
	}

	public void setPesototalReparto(Double pesototalReparto) {
		this.pesototalReparto = pesototalReparto;
	}

	public void setTotalContEntrega(Double totalContEntrega) {
		this.totalContEntrega = totalContEntrega;
	}

	public Double getTotalcobradoReparto() {
		return totalcobradoReparto;
	}

	public void setTotalcobradoReparto(Double totalcobradoReparto) {
		this.totalcobradoReparto = totalcobradoReparto;
	}

	public boolean isBtnAsigEnc() {
		return btnAsigEnc;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnAsigEnc(boolean btnAsigEnc) {
		this.btnAsigEnc = btnAsigEnc;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public Integer getIdPersonal() {
		return idPersonal;
	}

	public void setIdPersonal(Integer idPersonal) {
		this.idPersonal = idPersonal;
	}

	public Personal getPersonalSelected() {
		return personalSelected;
	}

	public void setPersonalSelected(Personal personalSelected) {
		this.personalSelected = personalSelected;
	}

	public List<Encomienda> getListaRptReparto() {
		return listaRptReparto;
	}

	public void setListaRptReparto(List<Encomienda> listaRptReparto) {
		this.listaRptReparto = listaRptReparto;
	}

	public String getOficinaUser() {
		return oficinaUser;
	}

	public void setOficinaUser(String oficinaUser) {
		this.oficinaUser = oficinaUser;
	}

	

	
}

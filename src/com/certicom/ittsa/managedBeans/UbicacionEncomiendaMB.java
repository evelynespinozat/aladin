package com.certicom.ittsa.managedBeans;

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

import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.DetalleGuiaRemision;
import com.certicom.ittsa.domain.Empresa;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.GuiaRemision;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.DetalleGuiaRemisionService;
import com.certicom.ittsa.services.EmpresaService;
import com.certicom.ittsa.services.EncomiendaAlmacenservice;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.GuiaRemisionService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;

@ManagedBean(name = "ubicacionEncomiendaMB") 
@ViewScoped
public class UbicacionEncomiendaMB {

	private Encomienda filterEnc;
	private Programacion progSelected;
	
	private String _tipoDocumento;
	private boolean _nombre;
	private boolean _razonSocial;

	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Encomienda> listaRptEncomienda;
	private List<Encomienda> listaRptGuiaRemision;
	private List<Producto_DetalleEnc> listaRptCodigoBarra;
	
	private PuntoVenta puntoVenta;
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	private List<Programacion> listaBuses;
	private List<Programacion> listaBusesFilter;

	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private EncomiendaServices encomiendaServices;
	private ProgramacionService programacionService;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private GuiaRemisionService guiaRemisionService;
	private DetalleGuiaRemisionService detalleGuiaRemisionService;
	private PuntoVentaService puntoVentaService;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	private FlotaService flotaService;
	private PilotoService pilotoService;
	private Producto_DetalleEncService producto_DetalleEncService;
	private PersonaServices personaServives;
	private EmpresaService empresaService;
    private UsuarioServices usuarioServices;
    private EncomiendaAlmacenservice encomiendaAlmacenservice;
	
	private boolean btnGuardar = false;
	private boolean btnImprimir = true;
	private boolean btnImprimirGuia = false;
	
	private Double totalPeso = 0.0;
	private Integer totalBultos = 0;
	private Double totalImporte = 0.0;
	
	private Double totalPesoSel = 0.0;
	private Integer totalBultosSel = 0;
	private Double totalImporteSel = 0.0;

	private Double totalPesoGral = 0.0;
	private Integer totalBultosGral = 0;
	private Double totalImporteGral = 0.0;
	
	// nuevos agregados
	
	private String _codigoBarra= "";
	private Encomienda encConsultada;
	private List<DetalleEncomienda>  listaDetalle;
	private List<Usuario> listaUsuarioRec;

	@PostConstruct
	public void init() {
		
		this._codigoBarra = "";
		this.encConsultada = new Encomienda();
		this.listaDetalle = new ArrayList<DetalleEncomienda>();
		
		this.filterEnc = new Encomienda();
		
		this._nombre = true;
		this._razonSocial = false;
		this.progSelected = new Programacion();
		
		this.listaEncomienda = new ArrayList<Encomienda>();
		this.listaRptEncomienda = new ArrayList<Encomienda>();
		this.listaRptGuiaRemision = new ArrayList<Encomienda>();
		this.listaRptCodigoBarra = new ArrayList<Producto_DetalleEnc>();
		
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.programacionService = new ProgramacionService();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.guiaRemisionService = new GuiaRemisionService();
		this.detalleGuiaRemisionService = new DetalleGuiaRemisionService();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		this.puntoVentaService = new PuntoVentaService();
		this.flotaService = new FlotaService();
		this.pilotoService = new PilotoService();
		this.producto_DetalleEncService = new Producto_DetalleEncService();
		this.personaServives = new PersonaServices();
		this.empresaService = new EmpresaService();
		this.usuarioServices = new UsuarioServices();
		this.encomiendaAlmacenservice = new EncomiendaAlmacenservice();
		
		try {
			this.listaUsuarioRec = this.usuarioServices.buscarPorPerfil(getUsuarioLogin().getIdoficina(), getUsuarioLogin().getCod_perfil());
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultarxCodigoBarras(){
		try {
			Integer idEnc = this.producto_DetalleEncService.obtenerIdEncomiendaxCodigoBarras(this._codigoBarra);
			if(idEnc !=null){
				this.encConsultada = this.encomiendaServices.obtenerDatosEncomienda(idEnc);
				this.listaDetalle = this.detalleEncomiendaServices.findByIdEncomienda(idEnc);
			} else{
				 FacesUtils.showFacesMessage("No se encontró encomiendas para el codigo de barras ingresado.",Constante.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void hayEncomienda(){
		RequestContext context = RequestContext.getCurrentInstance();  
   	    if(this.encConsultada.getIdEncomienda()!= null && this.encConsultada.getIdEncomienda()!= 0){
   	    	if(this.encConsultada.getEstado() == 4){
   	    		context.addCallbackParam("esValido", Boolean.TRUE);
   	    		this._tipoDocumento = "DNI";
   	    		this.encConsultada.setNumeroDocRecoge(this.encConsultada.getDniDestinatario1());
   	    		this.encConsultada.setNombreRecoge(this.encConsultada.getDestinatario1());
   	    	} else{
   	    		FacesUtils.showFacesMessage("La encomienda aún no está desembarcada o no está en almacén.",Constante.ERROR);
   	   	     context.addCallbackParam("esValido", Boolean.FALSE);
   	    	}
   	    } else{
   	     FacesUtils.showFacesMessage("Ingrese el código de barras.",Constante.ERROR);
   	     context.addCallbackParam("esValido", Boolean.FALSE);
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
				Persona p = this.personaServives.findByNroDocumento(this.encConsultada.getNumeroDocRecoge());
				if(p!=null) this.encConsultada.setNombreRecoge(p.getNombres()+ " " +  p.getAPaterno()+ " " + p.getAMaterno());
			}else if (_tipoDocumento.equals("RUC")){
				Empresa e = this.empresaService.findByNroRUC(this.encConsultada.getNumeroDocRecoge());
				if(e!=null) this.encConsultada.setNombreRecoge(e.getRazonSocial());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void confirmarEntrega(){
		this.encConsultada.setEstado(5); 
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("esValido", Boolean.TRUE);
			//this.encomiendaServices.actualizarEstadoEncomienda(this.encomienda);
			// estado 5 ENTREGADO
			System.out.println("el usuario que entra es  " + this.encConsultada.getIdUsuarioEntrega());
			this.encomiendaServices.actualizarEstadoEncomienda2(this.encConsultada.getIdEncomienda(), 5,this.encConsultada.getIdProgramacion());
			this.encConsultada.setfRecojo(new Date());
			this.encomiendaServices.actualizarValoresRecepcion(this.encConsultada);
			// actualizando el estado anterior en el tracking
			this.trackingEncomiendaServices.actualizarEstadoAnterior(this.encConsultada.getIdEncomienda());
			// agregando el nuevo estado ...
			
			TrackingEncomienda tke = new TrackingEncomienda();
			tke.setIdEncomienda(this.encConsultada.getIdEncomienda());
			tke.setIdEstado(5);
			tke.setIdBus(this.encConsultada.getIdBus());
			tke.setEstadoActual(Boolean.TRUE);
			tke.setIdProgramacion(this.encConsultada.getIdProgramacion());
			
			this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			this.encomiendaAlmacenservice.actualizarExistenciaEncomienda(this.encConsultada.getIdEncomienda());
			FacesUtils.showFacesMessage("Se entregó la encomienda correctamente.",Constante.INFORMACION);
			context.update("sms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		consultarxCodigoBarras();
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

	public Encomienda getFilterEnc() {
		return filterEnc;
	}

	public void setFilterEnc(Encomienda filterEnc) {
		this.filterEnc = filterEnc;
	}

	public List<Encomienda> getListaEncomienda() {
		return listaEncomienda;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setListaEncomienda(List<Encomienda> listaEncomienda) {
		this.listaEncomienda = listaEncomienda;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
	}

	public List<Encomienda> getListaEncomiendaFilter() {
		return listaEncomiendaFilter;
	}

	public void setListaEncomiendaFilter(List<Encomienda> listaEncomiendaFilter) {
		this.listaEncomiendaFilter = listaEncomiendaFilter;
	}

	public List<Programacion> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Programacion> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Programacion> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Programacion> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public Programacion getProgSelected() {
		return progSelected;
	}

	public void setProgSelected(Programacion progSelected) {
		this.progSelected = progSelected;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public List<Encomienda> getListaRptEncomienda() {
		return listaRptEncomienda;
	}

	public void setListaRptEncomienda(List<Encomienda> listaRptEncomienda) {
		this.listaRptEncomienda = listaRptEncomienda;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public Double getTotalPeso() {
		return totalPeso;
	}

	public void setTotalPeso(Double totalPeso) {
		this.totalPeso = totalPeso;
	}

	public Integer getTotalBultos() {
		return totalBultos;
	}

	public void setTotalBultos(Integer totalBultos) {
		this.totalBultos = totalBultos;
	}

	public Double getTotalImporte() {
		return totalImporte;
	}

	public void setTotalImporte(Double totalImporte) {
		this.totalImporte = totalImporte;
	}

	public Double getTotalPesoGral() {
		return totalPesoGral;
	}

	public Integer getTotalBultosGral() {
		return totalBultosGral;
	}

	public Double getTotalImporteGral() {
		return totalImporteGral;
	}

	public void setTotalPesoGral(Double totalPesoGral) {
		this.totalPesoGral = totalPesoGral;
	}

	public void setTotalBultosGral(Integer totalBultosGral) {
		this.totalBultosGral = totalBultosGral;
	}

	public void setTotalImporteGral(Double totalImporteGral) {
		this.totalImporteGral = totalImporteGral;
	}

	public Double getTotalPesoSel() {
		return totalPesoSel;
	}

	public Integer getTotalBultosSel() {
		return totalBultosSel;
	}

	public Double getTotalImporteSel() {
		return totalImporteSel;
	}

	public void setTotalPesoSel(Double totalPesoSel) {
		this.totalPesoSel = totalPesoSel;
	}

	public void setTotalBultosSel(Integer totalBultosSel) {
		this.totalBultosSel = totalBultosSel;
	}

	public void setTotalImporteSel(Double totalImporteSel) {
		this.totalImporteSel = totalImporteSel;
	}

	public GuiaRemisionService getGuiaRemisionService() {
		return guiaRemisionService;
	}

	public void setGuiaRemisionService(GuiaRemisionService guiaRemisionService) {
		this.guiaRemisionService = guiaRemisionService;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public boolean isBtnImprimirGuia() {
		return btnImprimirGuia;
	}

	public void setBtnImprimirGuia(boolean btnImprimirGuia) {
		this.btnImprimirGuia = btnImprimirGuia;
	}

	public String get_codigoBarra() {
		return _codigoBarra;
	}

	public void set_codigoBarra(String _codigoBarra) {
		this._codigoBarra = _codigoBarra;
	}

	public Encomienda getEncConsultada() {
		return encConsultada;
	}

	public void setEncConsultada(Encomienda encConsultada) {
		this.encConsultada = encConsultada;
	}

	public List<DetalleEncomienda> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(List<DetalleEncomienda> listaDetalle) {
		this.listaDetalle = listaDetalle;
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

	public List<Usuario> getListaUsuarioRec() {
		return listaUsuarioRec;
	}

	public void setListaUsuarioRec(List<Usuario> listaUsuarioRec) {
		this.listaUsuarioRec = listaUsuarioRec;
	}
	
	

}

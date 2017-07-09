package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.HistorialEncomienda;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Producto_DetalleEnc;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.HistorialEncomiendaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.OficinaTurnoService;
import com.certicom.ittsa.services.Producto_DetalleEncService;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.certicom.ittsa.services.TurnoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="reenvioEncomMB")
@ViewScoped
public class ReenvioEncomiendaMB extends GenericBeans implements Serializable{

	private Encomienda encomiendaFilter;
	private Encomienda encomienda;
	private Encomienda encomiendaReenvio;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Agencia> listaAgeOrigen;
	private List<Destino> listaAgeDestino;
	private List<Oficina> listOficinaOri;
	private List<Oficina> listOficinaDes;
	private List<Agencia> listaAgeOrigenReenvio;
	private List<Destino> listaAgeDestinoReenvio;
	private List<Oficina> listOficinaDesReenvio;
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	//services
	private EncomiendaServices encomiendaServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private OficinaService oficinaService;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	private Producto_DetalleEncService producto_DetalleEncService;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private HistorialEncomiendaServices historialEncomiendaServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ReenvioEncomiendaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.encomiendaServices = new EncomiendaServices();
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.oficinaService = new OficinaService();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		this.producto_DetalleEncService = new Producto_DetalleEncService();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.historialEncomiendaServices = new HistorialEncomiendaServices();
		
		this.encomiendaFilter = new Encomienda();
		this.encomienda = new Encomienda();
		this.encomiendaReenvio = new Encomienda();
		this.listaAgeOrigen = new ArrayList<>();
		this.listaAgeDestino = new ArrayList<>();
		this.listOficinaOri = new ArrayList<>();
		this.listOficinaDes = new ArrayList<>();
		
		this.listaAgeOrigenReenvio = new ArrayList<>();
		this.listaAgeDestinoReenvio = new ArrayList<>();
		this.listOficinaDesReenvio = new ArrayList<>();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();

		try {
			this.listaAgeOrigen = this.agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void listarDestinosxOri(String val){
		try {
			if(val.equals("1")){
				this.listaAgeDestino = this.destinoServices.obtenerDestino(this.encomiendaFilter.getIdOrigen());
				this.listOficinaOri =  this.oficinaService.getOficinasxAgencia(this.encomiendaFilter.getIdOrigen());
			}else{
				this.listaAgeDestinoReenvio = this.destinoServices.obtenerDestino(this.encomiendaReenvio.getIdOrigen());
				System.out.println("entro en esta segunda parte ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinasDestino(String val){
		try {
			if(val.equals("1")){
				this.listOficinaDes =  this.oficinaService.getOficinasxAgencia(this.encomiendaFilter.getIdDestino());
			}else{
				this.listOficinaDesReenvio =  this.oficinaService.getOficinasxAgencia(this.encomiendaReenvio.getIdDestino());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarEncomiendas(){
		try {
			/*System.out.println("el origen que entra es " + this.encomiendaFilter.getIdOrigen());
			System.out.println("el origen que entra es " + this.encomiendaFilter.getIdDestino());
			System.out.println("el origen que entra es " + this.encomiendaFilter.);
			System.out.println("el origen que entra es " + this.encomiendaFilter.getIdDestino());*/
			this.listaEncomienda = this.encomiendaServices.listaEncomiendasxOficinas(this.encomiendaFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reenviarEncomienda(){
		try {
			Integer IdEncomiendaOriginal = this.encomienda.getIdEncomienda();
			this.encomienda = this.encomiendaServices.findById(IdEncomiendaOriginal);
			this.encomienda.setEstado(1);
			this.encomienda.setfRegistro(new Date());
			this.encomienda.setIdPuntoVentaOrigen(this.usuarioLogin.getIdpuntoventa());
			this.encomienda.setIdUsuario(this.usuarioLogin.getIdusuario());
			this.encomienda.setEstado(11);
			this.encomiendaServices.actualizarEstadoEncomienda(this.encomienda);
			this.encomienda.setEstado(1);
			this.encomienda.setIdOrigen(this.encomiendaReenvio.getIdOrigen());
			this.encomienda.setIdDestino(this.encomiendaReenvio.getIdDestino());
			this.encomienda.setIdOficina(this.encomiendaReenvio.getIdOficina());
			System.out.println("el id de la encomienda original " + this.encomienda.getIdEncomienda());
			this.encomiendaServices.crearEncomienda(this.encomienda);
			Encomienda enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());
			System.out.println("el id de la encomienda copia es " + enc.getIdEncomienda());
			List<DetalleEncomienda> listaDetalle = this.detalleEncomiendaServices.findByIdEncomienda(IdEncomiendaOriginal);
			for (int i = 0; i < listaDetalle.size(); i++) {
				DetalleEncomienda detEncom = listaDetalle.get(i);
				Integer valorDetalle = detEncom.getIdDetalle();
				detEncom.setIdEncomienda(enc.getIdEncomienda());
				this.detalleEncomiendaServices.crearDetalleEncomienda(detEncom);
				//obteniedo el detalle de encomienda ultimo registrado
				DetalleEncomienda detE = this.detalleEncomiendaServices.findLastDetalleEncomiendaByIdEnc(enc.getIdEncomienda());
				//trayendo el producto detalle
				List<Producto_DetalleEnc> listaProductoDetalle = this.producto_DetalleEncService.obtenerProductoDetallexIdEncomiendaxIdDetalle(IdEncomiendaOriginal, valorDetalle);
				for (int j = 0; j < listaProductoDetalle.size(); j++) {
					Producto_DetalleEnc prod = listaProductoDetalle.get(j);
					prod.setIdDetalle(detE.getIdDetalle());
					prod.setIdEncomienda(enc.getIdEncomienda());
					this.producto_DetalleEncService.crearProducto_DetalleEnc(prod);
				}
			}
			
			registrarTracking();
			registrarHistorialEncomienda(this.encomienda);
			consultarEncomiendas();
			
			RequestContext context = RequestContext.getCurrentInstance();
			FacesUtils.showFacesMessage("Se realizó el reenvío de encomienda correctamente.",Constante.INFORMACION);
			context.update("sms");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registrarTracking(){
		Encomienda enc;
		try {
			enc = this.encomiendaServices.findLastEncomiendaByPV(usuarioLogin.getIdpuntoventa());
			TrackingEncomienda t = new TrackingEncomienda();
			t.setIdEncomienda(enc.getIdEncomienda());
			t.setIdEstado(1); 
			t.setEstadoActual(Boolean.TRUE); 
			this.trackingEncomiendaServices.crearTrackingEncomienda(t);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void registrarHistorialEncomienda(Encomienda enc){
		try {
			HistorialEncomienda h = new HistorialEncomienda();
			h.setDniRemitente(enc.getDniRemitente());
			h.setApellidosRemitente(enc.getApellidosRemitente()); 
			h.setNombresRemitente(enc.getNombresRemitente()); 
			h.setTelefonoRemitente(enc.getTelefonoRemitente());
			h.setRucRemitente(enc.getRucRemitente()); 
			h.setRazonSocialRemitente(enc.getRazonSocialRemitente()); 
			h.setDniDestinatario1(enc.getDniDestinatario1());
			h.setApellidosDestinatario1(enc.getApellidosDestinatario1()); 
			h.setNombresDestinatario1(enc.getNombresDestinatario1()); 
			h.setTelefonoDestinatario1(enc.getTelefonoDestinatario1());
			h.setRucDestinatario1(enc.getRucDestinatario1());
			h.setRazonSocialDestinatario1(enc.getRazonSocialDestinatario1()); 
			h.setDniDestinatario2(enc.getDniDestinatario2());
			h.setApellidosDestinatario2(enc.getApellidosDestinatario2()); 
			h.setNombresDestinatario2(enc.getNombresDestinatario2()); 
			h.setTelefonoDestinatario2(enc.getTelefonoDestinatario2());
			h.setRucDestinatario2(enc.getRucDestinatario2());
			h.setRazonSocialDestinatario2(enc.getRazonSocialDestinatario2());
			h.setDireccionEnvio(enc.getDireccionEnvio()); 
			h.setIdEncomienda(enc.getIdEncomienda()); 
			this.historialEncomiendaServices.crearHistorialEncomienda(h); 			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarEncomienda(Encomienda encomiend){
		try {
			this.encomiendaReenvio = encomiend;
			this.encomiendaReenvio.setIdOrigen(null);
			this.encomiendaReenvio.setIdDestino(null);
			this.encomiendaReenvio.setIdOficina(null);	
			this.listaAgeOrigenReenvio = this.agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//set and Get
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Encomienda getEncomiendaReenvio() {
		return encomiendaReenvio;
	}

	public void setEncomiendaReenvio(Encomienda encomiendaReenvio) {
		this.encomiendaReenvio = encomiendaReenvio;
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

	public List<Agencia> getListaAgeOrigen() {
		return listaAgeOrigen;
	}

	public void setListaAgeOrigen(List<Agencia> listaAgeOrigen) {
		this.listaAgeOrigen = listaAgeOrigen;
	}

	public List<Destino> getListaAgeDestino() {
		return listaAgeDestino;
	}

	public void setListaAgeDestino(List<Destino> listaAgeDestino) {
		this.listaAgeDestino = listaAgeDestino;
	}

	public List<Oficina> getListOficinaOri() {
		return listOficinaOri;
	}

	public void setListOficinaOri(List<Oficina> listOficinaOri) {
		this.listOficinaOri = listOficinaOri;
	}

	public List<Oficina> getListOficinaDes() {
		return listOficinaDes;
	}

	public void setListOficinaDes(List<Oficina> listOficinaDes) {
		this.listOficinaDes = listOficinaDes;
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

	public Encomienda getEncomiendaFilter() {
		return encomiendaFilter;
	}

	public void setEncomiendaFilter(Encomienda encomiendaFilter) {
		this.encomiendaFilter = encomiendaFilter;
	}

	public EncomiendaServices getEncomiendaServices() {
		return encomiendaServices;
	}

	public void setEncomiendaServices(EncomiendaServices encomiendaServices) {
		this.encomiendaServices = encomiendaServices;
	}

	public Encomienda getEncomienda() {
		return encomienda;
	}

	public void setEncomienda(Encomienda encomienda) {
		this.encomienda = encomienda;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<Agencia> getListaAgeOrigenReenvio() {
		return listaAgeOrigenReenvio;
	}

	public void setListaAgeOrigenReenvio(List<Agencia> listaAgeOrigenReenvio) {
		this.listaAgeOrigenReenvio = listaAgeOrigenReenvio;
	}

	public List<Destino> getListaAgeDestinoReenvio() {
		return listaAgeDestinoReenvio;
	}

	public void setListaAgeDestinoReenvio(List<Destino> listaAgeDestinoReenvio) {
		this.listaAgeDestinoReenvio = listaAgeDestinoReenvio;
	}

	public List<Oficina> getListOficinaDesReenvio() {
		return listOficinaDesReenvio;
	}

	public void setListOficinaDesReenvio(List<Oficina> listOficinaDesReenvio) {
		this.listOficinaDesReenvio = listOficinaDesReenvio;
	}

}


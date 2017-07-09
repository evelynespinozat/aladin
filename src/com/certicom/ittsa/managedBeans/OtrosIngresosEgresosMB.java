package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.IngresoEgreso;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.IngresoEgresoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="otrosIngEgreMB")
@ViewScoped
public class OtrosIngresosEgresosMB extends GenericBeans implements Serializable{

	private IngresoEgreso ingresoEgreso;
	private IngresoEgreso ingresoEgresoFilter;
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<IngresoEgreso> listaIngresoEgreso;
	private List<IngresoEgreso> listaIngresoEgresoFilter;
	private Boolean editar;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	//services
	private AgenciaService agenciaSevice;
	private OficinaService oficinaService;
	private IngresoEgresoService ingresoEgresoService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public OtrosIngresosEgresosMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.ingresoEgreso = new IngresoEgreso();
		this.ingresoEgresoFilter = new IngresoEgreso();
		this.listaAgencias = new ArrayList<>();
		this.listaOficinas = new ArrayList<>();
		this.listaIngresoEgreso = new ArrayList<>();
		this.editar = Boolean.FALSE;
	
		this.agenciaSevice = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.ingresoEgresoService = new IngresoEgresoService();
		this.menuServices=new MenuServices();
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAgencias = this.agenciaSevice.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:otrosingresosegresos");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoIngresoEgreso(){
		this.ingresoEgreso = new IngresoEgreso();
	}
	
	public void guardarMovimiento(){
		try {
			
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
			
			this.ingresoEgreso.setUsuarioreg(this.usuarioLogin.getIdusuario());
			this.ingresoEgreso.setEstado(1);
			this.ingresoEgresoService.crearIngresoEgreso(this.ingresoEgreso);
			
			 FacesUtils.showFacesMessage("Movimiento registrado correctamente.",Constante.INFORMACION);
			 context.update("sms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.ingresoEgresoFilter.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasxAgenciaReg(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.ingresoEgreso.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obtenerIngresoEgreso(){
		try {
			this.listaIngresoEgreso = new ArrayList<>();
			this.listaIngresoEgreso = this.ingresoEgresoService.listaIngresoEgresoxFechasOperacion(this.ingresoEgresoFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
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

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public IngresoEgreso getIngresoEgreso() {
		return ingresoEgreso;
	}

	public void setIngresoEgreso(IngresoEgreso ingresoEgreso) {
		this.ingresoEgreso = ingresoEgreso;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public IngresoEgreso getIngresoEgresoFilter() {
		return ingresoEgresoFilter;
	}

	public void setIngresoEgresoFilter(IngresoEgreso ingresoEgresoFilter) {
		this.ingresoEgresoFilter = ingresoEgresoFilter;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<IngresoEgreso> getListaIngresoEgreso() {
		return listaIngresoEgreso;
	}

	public void setListaIngresoEgreso(List<IngresoEgreso> listaIngresoEgreso) {
		this.listaIngresoEgreso = listaIngresoEgreso;
	}

	public List<IngresoEgreso> getListaIngresoEgresoFilter() {
		return listaIngresoEgresoFilter;
	}

	public void setListaIngresoEgresoFilter(
			List<IngresoEgreso> listaIngresoEgresoFilter) {
		this.listaIngresoEgresoFilter = listaIngresoEgresoFilter;
	}
	
	
}


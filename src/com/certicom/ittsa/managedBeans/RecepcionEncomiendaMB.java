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

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.EncomiendaAlmacen;
import com.certicom.ittsa.domain.EncomiendaIncidencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.OficinaAlmacen;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.EncomiendaAlmacenservice;
import com.certicom.ittsa.services.EncomiendaIncidenciaService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.OficinaAlmacenService;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name = "recepcionEncomiendaMB") 
@ViewScoped
public class RecepcionEncomiendaMB {

	private Encomienda filterEnc;
	private Encomienda encoSelect;
	private EncomiendaIncidencia encomiendaIncidencia;
	
	private Programacion progSelected;

	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Flota> listaFlotas;
	
	private List<Encomienda> listaRptEncomienda;
	private List<Encomienda> listaUbicacionEncomienda;
	private List<Oficina> listaOficinaxAgencia;
	
	private List<Programacion> listaBuses;
	private List<Programacion> listaBusesFilter;
	private List<OficinaAlmacen> listaAlmacen;

	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private EncomiendaServices encomiendaServices;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private FlotaService flotaService;
	private EncomiendaIncidenciaService encomiendaIncidenciaService;
	private OficinaAlmacenService oficinaAlmacenService;
	private EncomiendaAlmacenservice encomiendaAlmacenservice;
	private OficinaService oficinaService;
	
	private ParametroServices parametroServices;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	
	private boolean btnGuardar = false;
	private boolean btnImprimir = false;

	@PostConstruct
	public void init() {
		
		this.filterEnc = new Encomienda();
		this.filterEnc.setfRegistro(new Date());
		this.progSelected = new Programacion();
		this.encoSelect = new Encomienda();
		
		
		this.listaEncomienda = new ArrayList<Encomienda>();
		this.listaUbicacionEncomienda = new ArrayList<Encomienda>();
		this.listaRptEncomienda = new ArrayList<Encomienda>();
		
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.encomiendaServices = new EncomiendaServices();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices();
		this.flotaService = new FlotaService();
		this.encomiendaIncidenciaService = new EncomiendaIncidenciaService();
		this.oficinaAlmacenService = new OficinaAlmacenService();
		this.encomiendaAlmacenservice = new EncomiendaAlmacenservice();
		this.oficinaService = new OficinaService();
		this.parametroServices = new ParametroServices();

		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaFlotas = flotaService.listaFlotaActivasOrderNum();
			//this.listaAlmacen = this.oficinaAlmacenService.listaOficinasxAlmacen(this.usuarioLogin.getIdoficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.filterEnc.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultarEncomiendas() {
		try {
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasEmbarcadas(this.filterEnc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setearUbicacion(Encomienda l){
		this.encoSelect = l;
	}
	
	public void registrarUbicacionEncomiendas(){
		RequestContext context = RequestContext.getCurrentInstance();  
		//this.listaUbicacionEncomienda.add(this.encoSelect);
		try {
			this.encomiendaServices.actualizarUbicacionAlmacenEncomienda(this.encoSelect);
			this.encoSelect = new Encomienda();
			FacesUtils.showFacesMessage("Ubicación registrada correctamente.",Constante.INFORMACION);
			context.execute("dlgUbicacion.hide()");
			context.update("sms");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void selectEnco(Encomienda e){
		this.encoSelect = e;
		this.encomiendaIncidencia = new EncomiendaIncidencia();
	}
	
	public void desembarcarEncomiendas() {
		Utils utils = new Utils();
		int count = 0;
		for (int i = 0; i < this.listaEncomienda.size(); i++) {
			Encomienda e = this.listaEncomienda.get(i);
			if(e.isSeleccionado()){
				count++;
			}
		}
		
		if(count >0){
		try {
			// dias maximo para abandono legal
			Integer diasMaxAbLeg = Integer.parseInt(this.parametroServices.findParametro_byNombre("DIAS_ABANDONO_LEGAL"));
			
			for (int i = 0; i < this.listaEncomienda.size(); i++) {
				Encomienda e = this.listaEncomienda.get(i);
				if(e.isSeleccionado()){
					// estado 4 desembarcado
					System.out.println("entro");
					System.out.println("id encomienda --> " +e.getIdEncomienda());
					this.encomiendaServices.actualizarEstadoEncomienda2(e.getIdEncomienda(), 4,e.getIdProgramacion());
					// actualizando el estado anterior en el tracking
					this.trackingEncomiendaServices.actualizarEstadoAnterior(e.getIdEncomienda());
					// agregando el nuevo estado
					
					TrackingEncomienda tke = new TrackingEncomienda();
					tke.setIdEncomienda(e.getIdEncomienda());
					tke.setIdEstado(4);
					tke.setIdBus(this.encoSelect.getIdBus());
					tke.setEstadoActual(Boolean.TRUE);
					tke.setIdProgramacion(e.getIdProgramacion());
					
					this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
					
					
					EncomiendaAlmacen ea = new EncomiendaAlmacen();
					ea.setIdEncomienda(e.getIdEncomienda());
					ea.setIdAlmacen(this.filterEnc.getIdAlmacen());
					ea.setfRegistro(new Date());
					ea.setIdoficina(this.filterEnc.getIdOficina());
					ea.setHabido(Boolean.TRUE);
					// SE CAMBIO POR UNA VARIABLE
					//ea.setfVencimiento(utils.sumaMes(ea.getfRegistro(), 6));
					ea.setfVencimiento(utils.sumaDiasVarios(ea.getfRegistro(), diasMaxAbLeg));
					
					this.encomiendaAlmacenservice.registrarEncomiendaAlmacen(ea);
					
					//agregando a la lista del reporte
					this.listaRptEncomienda.add(e);
				}
			}
			
			FacesUtils.showFacesMessage("Submanifiesto registrado correctamente.",Constante.INFORMACION);
			this.listaEncomienda = this.encomiendaServices.listarEncomiendasEmbarcadas(this.filterEnc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		} else{
			FacesUtils.showFacesMessage("Seleccione por lo menos una encomienda.",Constante.ERROR);
		}
		

	}
	
	public void registrarIncidencia(){
		try {
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
			 context.addCallbackParam("esValido", valido );
			this.encomiendaIncidencia.setIdUsuario(getUsuarioLogin().getIdusuario());
			this.encomiendaIncidencia.setIdAgencia(getUsuarioLogin().getIdagencia());
			this.encomiendaIncidencia.setIdOficina(getUsuarioLogin().getIdoficina());
			this.encomiendaIncidencia.setIdEncomienda(this.encoSelect.getIdEncomienda());
			
			this.encomiendaIncidenciaService.registrarEncomiendaIncidencia(this.encomiendaIncidencia);
			// actualizando el estado de encomienda a 7 OBSERVADO
			this.encomiendaServices.actualizarEstadoEncomienda2(this.encoSelect.getIdEncomienda(), 7,this.encoSelect.getIdProgramacion());
			// actualizando el estado anterior en el tracking
			this.trackingEncomiendaServices.actualizarEstadoAnterior(this.encoSelect.getIdEncomienda());
			// agregando el nuevo estado
			
			
			TrackingEncomienda tke = new TrackingEncomienda();
			tke.setIdEncomienda(this.encoSelect.getIdEncomienda());
			tke.setIdEstado(7);
			tke.setIdBus(this.encoSelect.getIdBus());
			tke.setEstadoActual(Boolean.TRUE);
			tke.setIdProgramacion(this.encoSelect.getIdProgramacion());
			
			this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			
			consultarEncomiendas();
			
			FacesUtils.showFacesMessage("Incidencia registrada correctamente.",Constante.INFORMACION);
			context.update("sms");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarOficinasAgencia(){
		try {
			this.listaOficinaxAgencia = this.oficinaService.getOficinasxAgencia(this.filterEnc.getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarAlmacenesxOficina(){
		try {
			this.listaAlmacen = this.oficinaAlmacenService.listaOficinasxAlmacen(this.filterEnc.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public List<Flota> getListaFlotas() {
		return listaFlotas;
	}

	public void setListaFlotas(List<Flota> listaFlotas) {
		this.listaFlotas = listaFlotas;
	}

	public Encomienda getEncoSelect() {
		return encoSelect;
	}

	public void setEncoSelect(Encomienda encoSelect) {
		this.encoSelect = encoSelect;
	}

	public EncomiendaIncidencia getEncomiendaIncidencia() {
		return encomiendaIncidencia;
	}

	public void setEncomiendaIncidencia(EncomiendaIncidencia encomiendaIncidencia) {
		this.encomiendaIncidencia = encomiendaIncidencia;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<OficinaAlmacen> getListaAlmacen() {
		return listaAlmacen;
	}

	public void setListaAlmacen(List<OficinaAlmacen> listaAlmacen) {
		this.listaAlmacen = listaAlmacen;
	}

	public List<Oficina> getListaOficinaxAgencia() {
		return listaOficinaxAgencia;
	}

	public void setListaOficinaxAgencia(List<Oficina> listaOficinaxAgencia) {
		this.listaOficinaxAgencia = listaOficinaxAgencia;
	}
	

}

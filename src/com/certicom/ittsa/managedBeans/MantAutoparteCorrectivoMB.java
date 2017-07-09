package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;
import org.springframework.dao.DataAccessException;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.HistorialMantenimiento;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Mecanico;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaAutoParteService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.HistorialMantenimientoService;
import com.certicom.ittsa.services.KardexAutoparteService;
import com.certicom.ittsa.services.MecanicoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.SalidaAutoparteService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="mantAutoparteCorrectivoMB")
@ViewScoped
public class MantAutoparteCorrectivoMB extends GenericBeans implements Serializable{

	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuariologin;
	
	private Flota flota;
	public List<FlotaAutoparte> listaAutopartesBus;
	private Boolean editar;

	private String desAutoparte;

	private HistorialMantenimiento historialMantenimiento;
	private HistorialMantenimiento historialMantenimiento2;
	private List<HistorialMantenimiento> listaHistorialAuto;
	private List<Mecanico> listaMecanicos;
	private Boolean mostrarDet;
	
	//services
	private MecanicoService mecanicoService;
	private FlotaAutoparteService flotaAutoparteService;
	private HistorialMantenimientoService historialMantenimientoService;
	private SalidaAutoparteService salidaAutoparteService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public MantAutoparteCorrectivoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota =(Flota) flash.get("flota");
		this.listaAutopartesBus = new ArrayList<>();
		this.editar = Boolean.FALSE;
		this.mostrarDet = false;
		
		this.listaHistorialAuto = new ArrayList<>();
		this.historialMantenimiento = new HistorialMantenimiento();
		this.historialMantenimiento2 = new HistorialMantenimiento();
		
		//services
		this.flotaAutoparteService = new FlotaAutoparteService();
		this.historialMantenimientoService = new HistorialMantenimientoService();
		this.mecanicoService = new MecanicoService();
		this.menuServices = new MenuServices();
		this.salidaAutoparteService = new SalidaAutoparteService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		
		try {
			rellenadoListas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:servicioCorrectivo");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rellenadoListas(){
		try {
			this.listaHistorialAuto = this.historialMantenimientoService.obtenerAutoConMecanicoCorrectivo(flota);
			List<FlotaAutoparte> lista = new ArrayList<>();
			lista = this.flotaAutoparteService.autopartesxBusAll(flota);
			this.listaAutopartesBus = lista;
			for(int i=0; i<this.listaHistorialAuto.size(); i++){
				HistorialMantenimiento historial = this.listaHistorialAuto.get(i);
				for(int j=0; j<this.listaAutopartesBus.size(); j++){
					FlotaAutoparte flot = this.listaAutopartesBus.get(j);
					if(flot.getIdAutoparte() == historial.getIdAutoparte() && flot.getIdAutomotor() == historial.getIdAutomotor()){
						this.listaAutopartesBus.remove(flot);
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 
	}
	
	public void asignarMecanico(FlotaAutoparte flotaAutoparte){
		
		try {
			this.desAutoparte = flotaAutoparte.getDesAutoparte();
			this.listaMecanicos = new ArrayList<>();
			this.listaMecanicos = this.mecanicoService.listaMecanicosActivas();
			this.historialMantenimiento.setIdFlotaAuto(flotaAutoparte.getIdFlotaAuto());
			this.historialMantenimiento.setKmRecibido(flotaAutoparte.getKmActual());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarObservaciones(HistorialMantenimiento historial){
		
		 try {
			 this.mostrarDet = true;
			this.historialMantenimiento2 = this.historialMantenimientoService.findById(historial.getIdHistoMant());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void guardarHistorial(){
			RequestContext context = RequestContext.getCurrentInstance();  
			if(this.desAutoparte != ""){
				try {	
					this.historialMantenimiento.setTipoServicio("CORRECTIVO");
					this.historialMantenimiento.setEstadoMecanico(1);
					this.historialMantenimiento.setFecAsignaMecanico(new Date());
					this.historialMantenimientoService.crearHistorialMantenimiento(this.historialMantenimiento);
					FacesUtils.showFacesMessage("Mantenimiento registrado correctamente.",Constante.INFORMACION);
					context.update("msgGeneral");
					context.execute("dlgNuevo.hide()"); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				FacesUtils.showFacesMessage("Seleccione una autoparte primero.",Constante.INFORMACION);
				context.update("msgGeneral");
			
			}
			rellenadoListas();
		
	}
	
	public void guardarObservaciones(){
		try {
			RequestContext context = RequestContext.getCurrentInstance(); 
			this.historialMantenimiento2.setEstadoMecanico(2);
			this.historialMantenimiento2.setFecMantenimiento(new Date());
			if(this.historialMantenimiento2.getTipMantEfectuado().equals("REMPLAZADO")){
				Integer idFloAuto = this.historialMantenimiento2.getIdFlotaAuto();
				FlotaAutoparte flotaAuto = this.flotaAutoparteService.findById(idFloAuto);
		
				SalidaAutoparte salidaAutoparte = new SalidaAutoparte();
				salidaAutoparte.setIdBus(flotaAuto.getIdBus());
				salidaAutoparte.setIdAutoparte(flotaAuto.getIdAutoparte());
				salidaAutoparte.setEstado(1);
				salidaAutoparte.setFecPedido(new Date());
				salidaAutoparte.setIdMecanico(this.historialMantenimiento2.getIdMecanico());
				salidaAutoparte.setCantidad(this.historialMantenimiento2.getCantidad());
				salidaAutoparte.setIdOficina(this.usuariologin.getIdoficina());
				salidaAutoparte.setIdAgencia(this.usuariologin.getIdagencia());
				salidaAutoparte.setAccion(this.historialMantenimiento2.getTipoAccion());
				salidaAutoparte.setTipoMantenimiento(this.historialMantenimiento2.getTipMantEfectuado());
				salidaAutoparte.setIdAlmacen(3);
				this.salidaAutoparteService.crearSalidaAutoparte(salidaAutoparte);
				
				//System.out.println("el km ultimo antes del cambio es " + );
				//cambiamos el km de la pieza a cero Y registramosel km ultimo antes del cambio
				flotaAuto.setKmUltimoCambio(this.historialMantenimiento2.getKmRecibido());
				this.flotaAutoparteService.kmCero(flotaAuto);
			}

				this.historialMantenimientoService.actualizarObsHistorialMantenimiento(this.historialMantenimiento2);
				this.historialMantenimiento2 = new HistorialMantenimiento();
				this.historialMantenimiento = new HistorialMantenimiento();
				
			
			ocultarObseraciones();
			borrarHistorial();
			rellenadoListas();
			FacesUtils.showFacesMessage("Observaciones registradas correctamente.",Constante.INFORMACION);
			context.update("msgGeneral");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ocultarObseraciones(){
		System.out.println("entro en el metodo que ocultar el panel");
		this.historialMantenimiento2 = new HistorialMantenimiento(); 
		this.mostrarDet = false;
	}
	
	public void borrarHistorial(){
		this.desAutoparte = "";
		this.listaMecanicos = new ArrayList<>();
		this.historialMantenimiento = new HistorialMantenimiento();
		
	}
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}
	
	public List<HistorialMantenimiento> getListaHistorialAuto() {
		return listaHistorialAuto;
	}

	public void setListaHistorialAuto(
			List<HistorialMantenimiento> listaHistorialAuto) {
		this.listaHistorialAuto = listaHistorialAuto;
	}

	public Usuario getUsuariologin() {
		return usuariologin;
	}

	public void setUsuariologin(Usuario usuariologin) {
		this.usuariologin = usuariologin;
	}

	public HistorialMantenimiento getHistorialMantenimiento2() {
		return historialMantenimiento2;
	}

	public void setHistorialMantenimiento2(
			HistorialMantenimiento historialMantenimiento2) {
		this.historialMantenimiento2 = historialMantenimiento2;
	}

	public Boolean getMostrarDet() {
		return mostrarDet;
	}

	public void setMostrarDet(Boolean mostrarDet) {
		this.mostrarDet = mostrarDet;
	}
	
	public String getDesAutoparte() {
		return desAutoparte;
	}

	public void setDesAutoparte(String desAutoparte) {
		this.desAutoparte = desAutoparte;
	}

	public HistorialMantenimiento getHistorialMantenimiento() {
		return historialMantenimiento;
	}

	public void setHistorialMantenimiento(
			HistorialMantenimiento historialMantenimiento) {
		this.historialMantenimiento = historialMantenimiento;
	}

	public List<Mecanico> getListaMecanicos() {
		return listaMecanicos;
	}

	public void setListaMecanicos(List<Mecanico> listaMecanicos) {
		this.listaMecanicos = listaMecanicos;
	}

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
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

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<FlotaAutoparte> getListaAutopartesBus() {
		return listaAutopartesBus;
	}

	public void setListaAutopartesBus(List<FlotaAutoparte> listaAutopartesBus) {
		this.listaAutopartesBus = listaAutopartesBus;
	}
	
}


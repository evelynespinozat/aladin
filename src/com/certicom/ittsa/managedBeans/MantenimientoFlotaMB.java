package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.domain.ConsumoCombustible;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AutopartesAutomotorService;
import com.certicom.ittsa.services.ConsumoCombustibleService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="mantFlotaMB")
@ViewScoped
public class MantenimientoFlotaMB extends GenericBeans {
	
	//bean principal
	private Flota flota;
	private Programacion programacionFilter;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Programacion> listaProgramciones;
	private List<Programacion> listaFilterProgramciones;
	private ConsumoCombustible consumoCombustible;
	private Integer _idOrigen;
	private List<Agencia> listaAgOrigen;

	private List<FlotaAutomotor> listaAutomotorxBus;
	private List<FlotaAutomotor> listaAutomotorxBusKilometraje;
	private FlotaAutomotorService flotaAutomotorService;
	private FlotaAutoparteService flotaAutoparteService;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private ProgramacionService programacionService;
	private FlotaService flotaService;
	private ConsumoCombustibleService consumoCombustibleService;
	private List<Programacion> listaProgramcionesconKilometraje;
	
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota =(Flota) flash.get("flota");
		this.programacionFilter = new Programacion();
		this.programacionFilter.setfSalida(new Date());
		this.listaAgencias = new ArrayList<>();
		this.consumoCombustible = new ConsumoCombustible();
		this.listaAgOrigen = new ArrayList<>();
		/*temporal*/
		this.listaAutomotorxBus = new ArrayList<>();
		this.listaProgramcionesconKilometraje = new ArrayList<>();
		this.flotaAutomotorService = new FlotaAutomotorService();
		this.flotaAutoparteService = new FlotaAutoparteService();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.flotaService = new FlotaService();
		this.consumoCombustibleService = new ConsumoCombustibleService();
		/*fin temporal */
		
		//SERVICES
		this.menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			/*temporal*/
			this.listaAgOrigen = this.agenciaService.listaAgenciasActivas();
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listaAutomotorxBus = this.flotaAutomotorService.listaAutomotorxFlota(this.flota.getIdBus());
			for(FlotaAutomotor floxAuto : this.listaAutomotorxBus){
				floxAuto.setListaFlotaAutoparte(this.flotaAutoparteService.listaAutopartesxAutomotor(this.flota.getIdBus(), floxAuto.getIdAutomotor()));
			}
			
			/*fin temporal*/
			BuscarProgramacion();
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaVehicular");
			log.setCod_menu(codMenu);
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void setearCombustible(Programacion program){
		this.consumoCombustible.setConsumo(program.getConsumo());
		this.consumoCombustible.setIdConCombus(program.getIdConCombus());
		this.consumoCombustible.setIdPiloto(program.getIdPiloto());
		this.consumoCombustible.setIdCopiloto(program.getIdCopiloto());
		this.consumoCombustible.setPrecioComb(program.getPrecioComb());
		this.consumoCombustible.setKmRecorridos(program.getKmRecorridos());
		this.consumoCombustible.setActivar(program.getActivar());
	}
	
	public void BuscarProgramacion(){

		try {
			this.listaProgramciones = new ArrayList<>();
			this.listaProgramciones = this.programacionService.findProgByFecha_orig_destKilometraje(this.flota.getIdBus());
			
			this.listaProgramcionesconKilometraje = this.programacionService.findProgByFecha_orig_destKilometrajeConKm(this.flota.getIdBus());
		    //ponemos el activar a todos 
			for(int i=0 ; i<listaProgramciones.size(); i++){
			  Programacion pro = listaProgramciones.get(i);
			  if(i==0){
				  pro.setActivar(Boolean.FALSE);
			  }else{
				  pro.setActivar(Boolean.TRUE);
			  }
			  
			}
			
			this.programacionFilter = new Programacion();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void guardarKilometrajeBus(Programacion program){
		try {
			//verficamos que la programacion no se encuentre ya registrada en la consumocombustible
			if(program.getRecorridoBus() < program.getKmAumento()){
				int count = this.consumoCombustibleService.verificarProgramacion(program.getIdProgramacion());  
				if(count == 0){
					
					//insertamos en la tabla consumoCombustible esta programacion 
				    ConsumoCombustible conCombus = new ConsumoCombustible();
				    conCombus.setIdProgramacion(program.getIdProgramacion());
				    conCombus.setIdPiloto(program.getIdPiloto());
				    conCombus.setIdCopiloto(program.getIdCopiloto());
				    conCombus.setKmInicial(program.getRecorridoBus());
				    conCombus.setKmFinal(program.getKmAumento());
				    int aument = program.getKmAumento() - program.getRecorridoBus();
				    conCombus.setKmRecorridos(aument);
				    conCombus.setIdagencia(program.getDestino());
				    conCombus.setActivar(Boolean.FALSE);
				    //obtenemos el costo combustible
				    Agencia gas = this.agenciaService.findById(conCombus.getIdagencia());
				    conCombus.setPrecioComb(gas.getCombustible());
				    conCombus.setIdPiloto(program.getIdPiloto());
				    conCombus.setIdCopiloto(program.getIdCopiloto());
				    conCombus.setIdBus(program.getIdBus());
				    conCombus.setFecRegKilome(new Date());  
				    this.consumoCombustibleService.crearConsumoCombustible(conCombus);
				    
				    //actualizamos la cantidad de km recorridos a las partes del bus
				    this.listaAutomotorxBusKilometraje = new ArrayList<>();
				    this.listaAutomotorxBusKilometraje = this.flotaAutomotorService.listaAutomotorxFlota(program.getIdBus());
					for(FlotaAutomotor floxAutomoto : this.listaAutomotorxBusKilometraje){
						floxAutomoto.setListaFlotaAutoparte(this.flotaAutoparteService.listaAutopartesxAutomotor(program.getIdBus(), floxAutomoto.getIdAutomotor()));
					}
					
					for(int i=0; i<this.listaAutomotorxBusKilometraje.size(); i++ ){
						 FlotaAutomotor flotaAutox = this.listaAutomotorxBusKilometraje.get(i);
						 List<FlotaAutoparte> lista = new ArrayList<>();
						 lista = flotaAutox.getListaFlotaAutoparte();
						 for(int j=0;j<lista.size();j++){
							FlotaAutoparte floPartex = lista.get(j);
							floPartex.setKmAdherir(aument);
							floPartex.setFecKmIncremento(new Date());
							this.flotaAutoparteService.incrementarKm(floPartex);
							 
						 }
				    }
				    
				    //actualizamos el estKilometraje de programacion
				    this.programacionService.actualizarEstKilometraje(program.getIdProgramacion());
				    
					//registro de kilometros a bus
					Flota flo = new Flota();
					flo.setIdBus(program.getIdBus());
					//se realiza la operacion de resta
					
					flo.setKmAdherir(aument); 
					this.flotaService.aumentarKilometraje(flo);
					this.listaProgramciones = new ArrayList<>();
				    FacesUtils.showFacesMessage("Kms del bus registrados correctamente.",Constante.INFORMACION);
					
				    this.flota.setRecorrido(program.getKmAumento());
				    
				    BuscarProgramacion();
				}else{
					FacesUtils.showFacesMessage("Ya se agregó los KM a esta programación.",Constante.INFORMACION);
				}
			}else{
				FacesUtils.showFacesMessage("Debe ingresar un Km. final mayor al recorrido actual.",Constante.INFORMACION);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarCombustible(Programacion programacion){
		this.consumoCombustible.setConsumo(programacion.getConsumo());
		this.consumoCombustible.setIdConCombus(programacion.getIdConCombus());
		this.consumoCombustible.setIdPiloto(programacion.getIdPiloto());
		this.consumoCombustible.setIdCopiloto(programacion.getIdCopiloto());
		this.consumoCombustible.setPrecioComb(programacion.getPrecioComb());
		this.consumoCombustible.setKmRecorridos(programacion.getKmRecorridos());
		this.consumoCombustible.setActivar(false);
		programacion.setActivar(false);
		
	}
	
	public void guardarConsumoCombustible(){
		try {
			//this.consumoCombustible = consumo;
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
			
			this.consumoCombustible.setKmGalon(Redondear(this.consumoCombustible.getKmRecorridos()/this.consumoCombustible.getConsumo()));
			this.consumoCombustible.setCostoTotal(Redondear(this.consumoCombustible.getConsumo()*this.consumoCombustible.getPrecioComb()));
			this.consumoCombustible.setCostoKm(Redondear(this.consumoCombustible.getCostoTotal()/this.consumoCombustible.getKmRecorridos()));
			this.consumoCombustible.setFecRegCombus(new Date());
			this.consumoCombustible.setActivar(Boolean.TRUE);
			this.consumoCombustible.setIdOrigenComb(this._idOrigen);
			this.consumoCombustibleService.actualizarConsumoCombustible(this.consumoCombustible);

			this.listaProgramcionesconKilometraje = this.programacionService.findProgByFecha_orig_destKilometrajeConKm(this.flota.getIdBus());
			FacesUtils.showFacesMessage("Combustible registrado correctamente.",Constante.INFORMACION);
			context.update("msgNuevo");
		} catch (Exception e) {
		   e.printStackTrace();
		}
	}
	
	public double Redondear(double numero){
	      return Math.rint(numero*1000)/1000;
	}
	
//	public void incrementarKm(){
//		 try {
//			 RequestContext context = RequestContext.getCurrentInstance(); 
//			boolean _pase = false; 
//			for(int i=0; i<listaAutomotorxBus.size(); i++ ){
//					 FlotaAutomotor flotaAuto = this.listaAutomotorxBus.get(i);
//					 List<FlotaAutoparte> lista = new ArrayList<>();
//					 lista = flotaAuto.getListaFlotaAutoparte();
//					 for(int j=0;j<lista.size();j++){
//						FlotaAutoparte floParte = lista.get(j);
//						 if(floParte.getKmAdherir()>0){
//								this.flotaAutoparteService.incrementarKm(floParte);
//								_pase=true;
//						 }
//						
//					}
//			}
//			
//			if(_pase){
//				 log.setAccion("INSERT");
//	             log.setDescripcion("Se inserto los Kilometrajes : ");
//	             logmb.insertarLog(log);
//				 FacesUtils.showFacesMessage("Kms registrados correctamente.",Constante.INFORMACION);
//				 context.update("msgNuevo");
//				 
//				 
//				 this.listaAgencias = this.agenciaService.listaAgenciasActivas();
//					this.listaAutomotorxBus = this.flotaAutomotorService.listaAutomotorxFlota(this.flota.getIdBus());
//					for(FlotaAutomotor floxAuto : this.listaAutomotorxBus){
//						floxAuto.setListaFlotaAutoparte(this.flotaAutoparteService.listaAutopartesxAutomotor(this.flota.getIdBus(), floxAuto.getIdAutomotor()));
//					}
//				 
//				 _pase=false;
//			}
//		 } catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		
//	}
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = new ArrayList<>();
			this.listaDestino = this.destinoServices.obtenerDestino(this.programacionFilter.getOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
// set and get
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<FlotaAutomotor> getListaAutomotorxBusKilometraje() {
		return listaAutomotorxBusKilometraje;
	}

	public void setListaAutomotorxBusKilometraje(
			List<FlotaAutomotor> listaAutomotorxBusKilometraje) {
		this.listaAutomotorxBusKilometraje = listaAutomotorxBusKilometraje;
	}

	public List<Programacion> getListaProgramcionesconKilometraje() {
		return listaProgramcionesconKilometraje;
	}

	public void setListaProgramcionesconKilometraje(
			List<Programacion> listaProgramcionesconKilometraje) {
		this.listaProgramcionesconKilometraje = listaProgramcionesconKilometraje;
	}

	public List<Programacion> getListaFilterProgramciones() {
		return listaFilterProgramciones;
	}

	public void setListaFilterProgramciones(
			List<Programacion> listaFilterProgramciones) {
		this.listaFilterProgramciones = listaFilterProgramciones;
	}

	public List<Programacion> getListaProgramciones() {
		return listaProgramciones;
	}

	public void setListaProgramciones(List<Programacion> listaProgramciones) {
		this.listaProgramciones = listaProgramciones;
	}

	public FlotaAutomotorService getFlotaAutomotorService() {
		return flotaAutomotorService;
	}

	public void setFlotaAutomotorService(FlotaAutomotorService flotaAutomotorService) {
		this.flotaAutomotorService = flotaAutomotorService;
	}

	public FlotaAutoparteService getFlotaAutoparteService() {
		return flotaAutoparteService;
	}

	public void setFlotaAutoparteService(FlotaAutoparteService flotaAutoparteService) {
		this.flotaAutoparteService = flotaAutoparteService;
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

	public ProgramacionService getProgramacionService() {
		return programacionService;
	}

	public void setProgramacionService(ProgramacionService programacionService) {
		this.programacionService = programacionService;
	}

	public Programacion getProgramacionFilter() {
		return programacionFilter;
	}

	public void setProgramacionFilter(Programacion programacionFilter) {
		this.programacionFilter = programacionFilter;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public List<FlotaAutomotor> getListaAutomotorxBus() {
		return listaAutomotorxBus;
	}

	public void setListaAutomotorxBus(List<FlotaAutomotor> listaAutomotorxBus) {
		this.listaAutomotorxBus = listaAutomotorxBus;
	}

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}
	
	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public ConsumoCombustible getConsumoCombustible() {
		return consumoCombustible;
	}

	public void setConsumoCombustible(ConsumoCombustible consumoCombustible) {
		this.consumoCombustible = consumoCombustible;
	}

	public Integer get_idOrigen() {
		return _idOrigen;
	}

	public void set_idOrigen(Integer _idOrigen) {
		this._idOrigen = _idOrigen;
	}

	public List<Agencia> getListaAgOrigen() {
		return listaAgOrigen;
	}

	public void setListaAgOrigen(List<Agencia> listaAgOrigen) {
		this.listaAgOrigen = listaAgOrigen;
	}
	
}

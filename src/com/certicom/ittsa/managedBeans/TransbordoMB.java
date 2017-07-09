package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Transbordo;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TransbordoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="transbordoMB")
@ViewScoped
public class TransbordoMB extends GenericBeans implements Serializable{

	private Transbordo transbordo;
	private Transbordo transbordoFilter;
	private List<Transbordo> listaTransbordo;
	private List<Transbordo> listaFiltroTransbordo;
	 private List<Programacion> lsServiciosHora;
	
	private Date fechaminima;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	
	private Integer opcion;
	private Boolean editar;
	private boolean muestraPaneles;
	
	//services
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private TransbordoService transbordoSevice;
	private MenuServices menuServices;
	private ServicioService servicioService;
	private ProgramacionService programacionService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TransbordoMB(){}
	
	@PostConstruct
	public void incia()
	{
		this.opcion = 1;
		this.fechaminima = new Date();
		this.muestraPaneles = true;
		this.listaAgencias = new ArrayList<>();
		this.lsServiciosHora = new ArrayList<>();
		
		//servicios
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices(); 
		this.transbordoSevice = new TransbordoService();
		this.menuServices = new MenuServices();	
		this.servicioService = new ServicioService();
		this.programacionService = new ProgramacionService();

		this.transbordo = new Transbordo();
		this.transbordoFilter = new Transbordo();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		//listando
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaTransbordo = transbordoSevice.listaTransbordos();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void guardarTransbordo()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {

			if(this.editar)
			{
				this.transbordo.setIdTransbordo(this.transbordo.getIdTransbordo());
				this.transbordoSevice.actualizarTransbordo(transbordo);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza Transbordo: " + transbordo.getTipo());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Transbordo actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.transbordoSevice.crearTransbordo(transbordo);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la Transbordo: " + transbordo.getTipo());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Agencia registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			//listaTransbordo = this.transbordoSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.transbordo = new Transbordo();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void capturarDestino(){
		System.out.println("el destino cpturado es " + this.transbordoFilter.getDestino());
	}
	
	public void obtenerNumbBus(){
		for(int i = 0 ; i<lsServiciosHora.size(); i++){
			if(lsServiciosHora.get(i).getIdProgramacion().equals(transbordoFilter.getIdProgramacion())){
				this.transbordoFilter.setNumBus(lsServiciosHora.get(i).getNumeroBus());
				System.out.println("el numero en el filter es " + transbordoFilter.getNumBus());
				break;
			}
		}
		
	}
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.transbordoFilter.getOrigen());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obtenerListaHoras(){
		
		try {
			this.lsServiciosHora = programacionService.obtenerHoraxServicio(this.transbordoFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    public void cambioPaneles() {
        setMuestraPaneles(!muestraPaneles);
    }
    
    public void mostrarPanel(){
    	RequestContext context = RequestContext.getCurrentInstance();  
    	context.execute("dlgNuevo.show()");
    }
	//**set an get

	public MenuServices getMenuServices() {
		return menuServices;
	}
	
	public ServicioService getServicioService() {
		return servicioService;
	}

	public void setServicioService(ServicioService servicioService) {
		this.servicioService = servicioService;
	}

	public ProgramacionService getProgramacionService() {
		return programacionService;
	}

	public void setProgramacionService(ProgramacionService programacionService) {
		this.programacionService = programacionService;
	}

	public List<Programacion> getLsServiciosHora() {
		return lsServiciosHora;
	}

	public void setLsServiciosHora(List<Programacion> lsServiciosHora) {
		this.lsServiciosHora = lsServiciosHora;
	}

	public Date getFechaminima() {
		
		int diferenciaEnDias = 1;
        Date fechaActual = Calendar.getInstance().getTime();
        long tiempoActual = fechaActual.getTime();
        long unDia = diferenciaEnDias * 24 * 60 * 60 * 1000;
        fechaminima = new Date(tiempoActual - unDia);
		
		return fechaminima;
	}

	public void setFechaminima(Date fechaminima) {
		this.fechaminima = fechaminima;
	}

	public Transbordo getTransbordoFilter() {
		return transbordoFilter;
	}

	public void setTransbordoFilter(Transbordo transbordoFilter) {
		this.transbordoFilter = transbordoFilter;
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

	public boolean isMuestraPaneles() {
		return muestraPaneles;
	}

	public void setMuestraPaneles(boolean muestraPaneles) {
		this.muestraPaneles = muestraPaneles;
	}

	public Transbordo getTransbordo() {
		return transbordo;
	}

	public void setTransbordo(Transbordo transbordo) {
		this.transbordo = transbordo;
	}

	public List<Transbordo> getListaTransbordo() {
		return listaTransbordo;
	}

	public void setListaTransbordo(List<Transbordo> listaTransbordo) {
		this.listaTransbordo = listaTransbordo;
	}

	public List<Transbordo> getListaFiltroTransbordo() {
		return listaFiltroTransbordo;
	}

	public void setListaFiltroTransbordo(List<Transbordo> listaFiltroTransbordo) {
		this.listaFiltroTransbordo = listaFiltroTransbordo;
	}

	public TransbordoService getTransbordoSevice() {
		return transbordoSevice;
	}

	public void setTransbordoSevice(TransbordoService transbordoSevice) {
		this.transbordoSevice = transbordoSevice;
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

	public Integer getOpcion() {
		return opcion;
	}

	public void setOpcion(Integer opcion) {
		this.opcion = opcion;
	}
	
	
}


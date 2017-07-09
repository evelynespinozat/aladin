package com.certicom.ittsa.managedBeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Salida;
import com.certicom.ittsa.domain.SalidaDetalle;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PlantillaDetalleService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.SalidaDetalleService;
import com.certicom.ittsa.services.SalidaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name ="salidaMB")
@ViewScoped
public class SalidaMB extends GenericBeans{
	
	private Programacion progFilter;
	private Programacion progSelect;
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Agencia> listaOrigen;
	private List<Destino> listaDestino;
	private List<ClaseServicio> listaCServicio;
	private List<Programacion> listaProg;
	private List<PlantillaDetalle> listdetallePlantilla;
	
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private DestinoServices destinoServices;
	private ClaseServicioServices claseServicioServices;
	private MenuServices menuServices;
	private ProgramacionService programacionService;
	private PlantillaDetalleService plantillaDetalleService;
	private SalidaService salidaService;
	private SalidaDetalleService salidaDetalleService;
	private KardexService kardexService;
	private AgenciaProductoService agenciaProductoService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	private Date fechaActual;
	private String infoProg;
	private boolean showPD;
	
	
	@PostConstruct
	public void init(){
		
		this.progFilter = new Programacion();
		this.fechaActual = new Date();
		showPD = false;
		this.listaProg = new ArrayList<Programacion>();
		
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.destinoServices = new DestinoServices();
		this.claseServicioServices = new ClaseServicioServices();
		this.programacionService = new ProgramacionService();
		this.plantillaDetalleService = new PlantillaDetalleService();
		this.salidaService = new SalidaService();
		this.salidaDetalleService = new SalidaDetalleService();
		this.kardexService = new KardexService();
		this.agenciaProductoService = new AgenciaProductoService();
	
		
	
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = this.menuServices.opcionMenuByPretty("pretty:salidaProductos");
			log.setCod_menu(codMenu);
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaOrigen = this.listaAgencias;
			this.listaCServicio = claseServicioServices.listaCServiciosActivos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.progFilter.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		limpiardatos();
		
	}
	
	private  String formatDate(Date Fecha){
		  String patron = "dd/MM/yyyy";
		    SimpleDateFormat formato = new SimpleDateFormat(patron);
		    // formateo
		    return formato.format(new Date());
	}
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = this.destinoServices.obtenerDestino(this.progFilter.getOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
		limpiardatos();
	}
	
	
	public void  obtenerProgramacion(){
		try {
			this.listaProg = programacionService.listarProgrPlantilla(this.progFilter);
			this.showPD = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarPlantillaProductos(Programacion pg){
		this.showPD = Boolean.TRUE;
		String info = pg.getIdProgramacion() + "-" +pg.getDescServicio() + "- " + formatDate(pg.getfSalida()) + "-" +pg.gethSalida() ;
		this.infoProg = info;
		//seteando la programacion seleccionada
		this.progSelect = pg;
		progSelect.setIdAgencia(this.progFilter.getIdAgencia());
		progSelect.setIdOficina(this.progFilter.getIdOficina());
		progSelect.setOrigen(this.progFilter.getOrigen());
		progSelect.setDestino(this.progFilter.getDestino());
		
		
		// bean para buscar
		PlantillaDetalle pd = new PlantillaDetalle();
		pd.setIdClase(this.progFilter.getIdClase());
		pd.setIdOrigen(this.progFilter.getOrigen());
		pd.setIdDestino(this.progFilter.getDestino());
		pd.setIdagencia(this.progFilter.getIdAgencia());
		pd.setIdOficina(this.progFilter.getIdOficina());
		pd.setIdServicio(this.progSelect.getIdServicio());
		
		try {
			this.listdetallePlantilla = plantillaDetalleService.listarProductosxPlantilla(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limpiardatos(){
		for (int i = 0; i < this.listaProg.size(); i++) {
				this.listaProg.remove(i);
		}
		this.showPD = Boolean.FALSE;
	}
	
	public void registrarSalidaProductos(){
		
		Salida s = new Salida();
		s.setIdagencia(this.progSelect.getIdAgencia());
		s.setIdOficina(this.progSelect.getIdOficina());
		s.setIdOrigen(this.progSelect.getOrigen());
		s.setIdDestino(this.progSelect.getDestino());
		s.setIdProgramacion(this.progSelect.getIdProgramacion());
		s.setFSalida(this.progSelect.getfSalida());
		s.setFRegistro(new Date());
		s.setFlagLlegada("0");
		
		Integer Id_Salida = 0;
		try {
		   	this.salidaService.registrarSalidaProductos(s);
		   	Id_Salida = this.salidaService.getIDbyProgramacion(s.getIdProgramacion());
		   	System.out.println("Id_Salida " + Id_Salida);
		   	
		   	for (int i = 0; i < this.listdetallePlantilla.size(); i++) {
				PlantillaDetalle pd  = this.listdetallePlantilla.get(i);
				if(pd.getCantSalida()>0){
					//objeto para guardar
					SalidaDetalle sd = new SalidaDetalle();
					sd.setIdSalida(Id_Salida);
					sd.setIdProducto(pd.getIdProducto());
					sd.setStockAnt(pd.getStock()); // guardando el stock de ese momento del producto
					sd.setCantSalida(pd.getCantSalida());
					sd.setFRegistro(new Date());
					sd.setFlagEntrega("0");
					sd.setCosto(pd.getCosto());
					sd.setCostoUni(pd.getCostoUni());
					sd.setDisgregacion(pd.getDisgregacion());
					sd.setCantDist(pd.getCantDist());
					sd.setCostoxPasj(pd.getCostoxPasj());
					
					// registrando en el detalle de salida
					this.salidaDetalleService.registrarSalidaDetalle(sd);
					
					//registrando en el kardex
					Kardex k = new Kardex();
					k.setIdagencia(pd.getIdagencia());
					k.setIdOficina(pd.getIdOficina());
					k.setIdProducto(pd.getIdProducto());
					k.setTipo("SALIDA");
					k.setSalida(pd.getCantSalida());
					k.setFRegistro(new Date());
					k.setIdProgramacion(this.progSelect.getIdProgramacion());
					k.setCosto(pd.getCosto());
					k.setCostoUni(pd.getCostoUni());
					k.setDisgregacion(pd.getDisgregacion());
					k.setCantDist(pd.getCantDist());
					k.setCostoxPasj(pd.getCostoxPasj());
					
					this.kardexService.registrarKardex(k);
					
					//actualizando la cantidad del producto en la agencia y oficina
					this.agenciaProductoService.actualizarStockProducto(pd);
					
				}
				
				// actualizando el estado atencion  a bordo de Programacion
			}

		   	this.programacionService.actualizarEstATBordo(1, this.progSelect.getIdProgramacion());
		   	log.setAccion("INSERT ");
		   	log.setDescripcion("Se registro la Salida para la programacion "+ this.progSelect.getIdProgramacion());
		   	logmb.insertarLog(log);
		   	FacesUtils.showFacesMessage("Salida registrada correctamente.",Constante.INFORMACION);
		   	this.listaProg = this.programacionService.listarProgrPlantilla(this.progFilter);
		   	this.showPD = Boolean.FALSE;
		   	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}


	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}


	public List<Destino> getListaDestino() {
		return listaDestino;
	}


	public List<ClaseServicio> getListaCServicio() {
		return listaCServicio;
	}


	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}


	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}


	public void setListaCServicio(List<ClaseServicio> listaCServicio) {
		this.listaCServicio = listaCServicio;
	}

	public Programacion getProgFilter() {
		return progFilter;
	}

	public void setProgFilter(Programacion progFilter) {
		this.progFilter = progFilter;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<Programacion> getListaProg() {
		return listaProg;
	}

	public void setListaProg(List<Programacion> listaProg) {
		this.listaProg = listaProg;
	}

	public String getInfoProg() {
		return infoProg;
	}

	public void setInfoProg(String infoProg) {
		this.infoProg = infoProg;
	}

	public List<PlantillaDetalle> getListdetallePlantilla() {
		return listdetallePlantilla;
	}

	public void setListdetallePlantilla(List<PlantillaDetalle> listdetallePlantilla) {
		this.listdetallePlantilla = listdetallePlantilla;
	}

	public boolean isShowPD() {
		return showPD;
	}

	public void setShowPD(boolean showPD) {
		this.showPD = showPD;
	}

	public Programacion getProgSelect() {
		return progSelect;
	}

	public void setProgSelect(Programacion progSelect) {
		this.progSelect = progSelect;
	}
	
	

}

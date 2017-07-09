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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaVacia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.FlotaVaciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PilotoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;

@ManagedBean(name="unidadesVaciasMB")
@ViewScoped
public class UnidadesVaciasMB extends GenericBeans implements Serializable{
	private String titulo;
	private FlotaVacia flotaVacia;
	private List<FlotaVacia> listaFlotaVacias;
	private List<FlotaVacia> listaFlotaVaciasFiltro;
	private List<FlotaVacia> listaFlotaVaciasrpt;
	private List<Agencia> listaOrigen;
	private List<Agencia> listaDestino;
	private List<Flota> listaBuses;
	private Boolean editar;
	
	//services
	private AgenciaService agenciaSevice;
	private FlotaService flotaService;
	private FlotaVaciaService flotaVaciaService;
	
	private MenuServices menuServices;
	
	private PilotoService pilotoService;
	
	private Boolean disF = false;
	private Boolean disP = false;
	private Boolean disCP = false;
	private List<Piloto> listaPiloto;
	private List<Piloto> listaCopiloto;
	private Boolean pilotoCopilotoRecuperado;
	private List<Piloto> listaFiltroPiloto;
	private List<Piloto> listaFiltroCopiloto;

	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	private boolean activarBtnImprimir;
	
	public UnidadesVaciasMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.listaFlotaVaciasrpt = new ArrayList<>();
		this.agenciaSevice = new AgenciaService();
		this.menuServices=new MenuServices();
		this.flotaService = new FlotaService();
		this.flotaVaciaService = new FlotaVaciaService();
		this.pilotoService = new PilotoService();
		
		this.flotaVacia = new FlotaVacia();
		
		
		this.pilotoCopilotoRecuperado = Boolean.FALSE;
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaOrigen = agenciaSevice.listaAgenciasActivas();
			this.listaDestino = this.listaOrigen;
			this.listaFlotaVacias = this.flotaVaciaService.findAll();
			this.listaBuses = this.flotaService.listaFlotaActivas();
			this.listaPiloto = pilotoService.listaPilotoActivas();
			this.listaCopiloto = this.listaPiloto;
			int codMenu = menuServices.opcionMenuByPretty("pretty:unidadesVacias");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prueba(){
		
	}
	
	public void cancelar(){
		this.flotaVacia = new FlotaVacia();
	}

	public void guardarFlotaVacia()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			this.flotaVacia.setMotivo(this.flotaVacia.getMotivo().toUpperCase());
			this.flotaVacia.setObservacion(this.flotaVacia.getObservacion().toUpperCase());
			
			if(this.flotaVacia.getPilotCopilotReasignado() == null)
			{
				this.flotaVacia.setPilotCopilotReasignado(Boolean.FALSE);
			}
			
			if(this.editar)
			{
				
				this.flotaVaciaService.actualizarFlotaVacia(this.flotaVacia);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la Flota vacia: " + this.flotaVacia.getIdBus());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Flota vacía actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.flotaVacia.setfRegistro(new Date());
				this.flotaVaciaService.crearFlotaVacia(flotaVacia);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se insertó la flota vacia: " + flotaVacia.getIdBus());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Flota vacía registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaFlotaVacias = this.flotaVaciaService.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.flotaVacia = new FlotaVacia();
		this.editar =  Boolean.FALSE;
		
	}
	
	
	public void eliminarUnidadVacia()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
				this.flotaVaciaService.eliminarFlotaVacia(flotaVacia.getIdFlotaVacia());
				listaFlotaVacias.remove(flotaVacia);
				FacesUtils.showFacesMessage("Flota vacía eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la flota vacía: " + flotaVacia.getIdFlotaVacia());
				logmb.insertarLog(log);
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.flotaVacia =  new FlotaVacia();
		
	}
	
	public void editarFlotaVacia(FlotaVacia fv)
	{
		this.editar = Boolean.TRUE;
		this.flotaVacia = fv;
		this.titulo = "Modificar unidad vacía";
		
	}
	
	public void nuevaFlotaVacia()
	{
		this.editar = Boolean.FALSE;
		this.flotaVacia = new FlotaVacia();
		this.flotaVacia.setFecIni(Utiles.obtenerPrimerDiaDelMes());
		this.flotaVacia.setFecFin(Utiles.obtenerUltimoDiaDelMes());
		this.activarBtnImprimir = Boolean.TRUE;
		this.titulo = "Registrar nueva unidad vacía";
	}
	
	public void obtenerListaReporte(){
		RequestContext context = RequestContext.getCurrentInstance();
		 context.addCallbackParam("esValido", Boolean.TRUE );
		try {
			this.listaFlotaVaciasrpt = this.flotaVaciaService.rptUnidadVaciasxFiltros(this.flotaVacia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void imprimirPDF()
	{

		String par_origen = "", par_destino = "", par_nroBus = "";
		System.out.println("entro en el metodo de imprmie");
		
		for (int k = 0; k < this.listaBuses.size(); k++) {
			Flota f = this.listaBuses.get(k);
			if(f.getIdBus() == this.flotaVacia.getIdBus()){
				par_nroBus = f.getNumero().toString();
				break;
			}
			
		}
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			
		//	List<FlotaVacia> listaRpt = this.flotaVaciaService.rptUnidadVaciasxFiltros(this.flotaVacia);
			
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
			String fecha = formato.format(new Date());

			SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaInicio = formato2.format(this.flotaVacia.getFecIni());
			String fechaFin = formato2.format(this.flotaVacia.getFecFin());
			
			Integer total = this.listaFlotaVaciasrpt.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FSISTEMA", fecha);
			input.put("P_FINICIO", fechaInicio);
			input.put("P_FFIN", fechaFin);
			input.put("P_NRO_BUS", par_nroBus);
			input.put("P_TOTAL", total.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptUnidadesVacias.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaFlotaVaciasrpt);
			ExportarArchivo.executePdf(bytes, "UnidadesVacias.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	public void mostrarDatosBus()
	{
		this.flotaVacia.setIdpiloto(null);
		this.flotaVacia.setNamePiloto("");
		this.flotaVacia.setDniPiloto("");
		this.flotaVacia.setIdcopiloto(null);
		this.flotaVacia.setNameCopiloto("");
		this.flotaVacia.setDniCopiloto("");
		
		
		
		for (int i = 0; i < this.listaBuses.size(); i++) 
		{
			Flota f = this.listaBuses.get(i);
			if(f.getIdBus() == this.flotaVacia.getIdBus()){
				this.flotaVacia.setNroBus(f.getNumero().toString());
				this.flotaVacia.setPlaca(f.getPlaca());
				if(f.getPiloto()!= null){
					for (int j = 0; j < this.listaPiloto.size(); j++) {
						Piloto pilo = this.listaPiloto.get(j);
						if(f.getPiloto().toString().equals(pilo.getIdPiloto().toString())){
							System.out.println("entro ");
							this.flotaVacia.setIdpiloto(pilo.getIdPiloto());
							this.flotaVacia.setDniPiloto(pilo.getdNI());
							this.flotaVacia.setNamePiloto(pilo.getNombres() + " " + pilo.getApellidoPaterno() + " " + pilo.getApellidoMaterno());
						}
					}
				}
				if(f.getCopiloto()!= null){
					for (int k = 0; k < this.listaCopiloto.size(); k++) {
						Piloto copilo = this.listaCopiloto.get(k);
						if(f.getCopiloto().toString().equals(copilo.getIdPiloto().toString())){
							this.flotaVacia.setIdcopiloto(copilo.getIdPiloto());
							this.flotaVacia.setDniCopiloto(copilo.getdNI());
							this.flotaVacia.setNameCopiloto(copilo.getNombres() + " " + copilo.getApellidoPaterno() + " " + copilo.getApellidoMaterno());
						}
					}
				}
			}
		}
		
		if(this.flotaVacia.getIdpiloto() != null && this.flotaVacia.getIdcopiloto() !=null)
		{
			this.pilotoCopilotoRecuperado = Boolean.TRUE;
		}
		
	}
	

	public void listarPilotosActivos()
	{
		try {
			this.listaPiloto = pilotoService.listaPilotoActivas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void listarCopilotosActivos()
	{
		this.listaCopiloto = null;
		
		try {
			this.listaCopiloto = this.pilotoService.copilotosDisponibles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void asignarPiloto(Piloto pilot)
	{
		System.out.println("piloto seleccionado: "+pilot.getApellidoPaterno());
		this.flotaVacia.setNamePiloto(pilot.getNombres()+" "+pilot.getApellidoPaterno()+" "+pilot.getApellidoPaterno());
		this.flotaVacia.setIdpiloto(pilot.getIdPiloto());
		this.flotaVacia.setPilotCopilotReasignado(Boolean.TRUE);
		
		/*
		
		Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
		
		if(!pilot.isFlagTemporal())
		{
			this.busServices.actualizarPiloto(pil.getIdPiloto(),t.getIdBus());
		}
		
		this.tripulacionServices.actualizarIdPiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());

		*/
		
	}
	
	public void asignarCopiloto(Piloto copilot)
	{
		System.out.println("copiloto seleccionado: "+copilot.getApellidoPaterno());
		this.flotaVacia.setNameCopiloto(copilot.getNombres()+" "+copilot.getApellidoPaterno()+" "+copilot.getApellidoPaterno());
		this.flotaVacia.setIdcopiloto(copilot.getIdPiloto());
		this.flotaVacia.setPilotCopilotReasignado(Boolean.TRUE);
		/*
		Tripulacion t = this.tripulacionServices.findByIdProgramacion(this.programacion.getIdProgramacion());
		
		if(!copilot.isFlagTemporal())
		{
			this.busServices.actualizarPiloto(pil.getIdPiloto(),t.getIdBus());
		}
		
		this.tripulacionServices.actualizarIdPiloto(t.getIdTripulacion(), pil.getIdPiloto(), pil.isFlagTemporal());
		 */
		
	}
	
	
	public FlotaVacia getFlotaVacia() {
		return flotaVacia;
	}

	public void setFlotaVacia(FlotaVacia flotaVacia) {
		this.flotaVacia = flotaVacia;
	}

	public List<FlotaVacia> getListaFlotaVacias() {
		return listaFlotaVacias;
	}

	public void setListaFlotaVacias(List<FlotaVacia> listaFlotaVacias) {
		this.listaFlotaVacias = listaFlotaVacias;
	}

	public List<FlotaVacia> getListaFlotaVaciasFiltro() {
		return listaFlotaVaciasFiltro;
	}

	public void setListaFlotaVaciasFiltro(List<FlotaVacia> listaFlotaVaciasFiltro) {
		this.listaFlotaVaciasFiltro = listaFlotaVaciasFiltro;
	}

	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}

	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public List<Agencia> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Agencia> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
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

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public boolean isActivarBtnImprimir() {
		return activarBtnImprimir;
	}

	public void setActivarBtnImprimir(boolean activarBtnImprimir) {
		this.activarBtnImprimir = activarBtnImprimir;
	}

	public List<FlotaVacia> getListaFlotaVaciasrpt() {
		return listaFlotaVaciasrpt;
	}

	public void setListaFlotaVaciasrpt(List<FlotaVacia> listaFlotaVaciasrpt) {
		this.listaFlotaVaciasrpt = listaFlotaVaciasrpt;
	}

	public Boolean getDisF() {
		return disF;
	}

	public Boolean getDisP() {
		return disP;
	}

	public Boolean getDisCP() {
		return disCP;
	}

	public void setDisF(Boolean disF) {
		this.disF = disF;
	}

	public void setDisP(Boolean disP) {
		this.disP = disP;
	}

	public void setDisCP(Boolean disCP) {
		this.disCP = disCP;
	}

	public List<Piloto> getListaPiloto() {
		return listaPiloto;
	}

	public List<Piloto> getListaCopiloto() {
		return listaCopiloto;
	}

	public void setListaPiloto(List<Piloto> listaPiloto) {
		this.listaPiloto = listaPiloto;
	}

	public void setListaCopiloto(List<Piloto> listaCopiloto) {
		this.listaCopiloto = listaCopiloto;
	}

	public Boolean getPilotoCopilotoRecuperado() {
		return pilotoCopilotoRecuperado;
	}

	public void setPilotoCopilotoRecuperado(Boolean pilotoCopilotoRecuperado) {
		this.pilotoCopilotoRecuperado = pilotoCopilotoRecuperado;
	}

	public List<Piloto> getListaFiltroPiloto() {
		return listaFiltroPiloto;
	}

	public List<Piloto> getListaFiltroCopiloto() {
		return listaFiltroCopiloto;
	}

	public void setListaFiltroPiloto(List<Piloto> listaFiltroPiloto) {
		this.listaFiltroPiloto = listaFiltroPiloto;
	}

	public void setListaFiltroCopiloto(List<Piloto> listaFiltroCopiloto) {
		this.listaFiltroCopiloto = listaFiltroCopiloto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	

	//**set an get 
	
	
	
	
	
}


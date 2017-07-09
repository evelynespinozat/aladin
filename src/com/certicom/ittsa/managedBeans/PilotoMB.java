package com.certicom.ittsa.managedBeans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.domain.Cese;
import com.certicom.ittsa.domain.Datos_Familiares;
import com.certicom.ittsa.domain.Documento;
import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Formacion;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Historial_Laboral;
import com.certicom.ittsa.domain.Incidencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Papeleta;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.CapacitacionService;
import com.certicom.ittsa.services.CeseService;
import com.certicom.ittsa.services.Datos_FamiliaresService;
import com.certicom.ittsa.services.DocumentoService;
import com.certicom.ittsa.services.EvaluacionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.FormacionService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.Historial_LaboralService;
import com.certicom.ittsa.services.IncidenciaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PapeletaService;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.UbigeoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;


@ManagedBean(name="pilotoMB")
@ViewScoped

public class PilotoMB extends GenericBeans implements Serializable{
	private String titulo;
	private Piloto piloto;
	private List<Piloto> listaPiloto;
	private List<Piloto> listaPilotoSelect;
	private List<Piloto> listaFiltroPiloto;
	private List<Historial_Laboral> listaHistorial;
	private List<Datos_Familiares> listaDatosF;
	private List<Documento> listaDocumentos;
	private List<Evaluacion> listaEvauacion;
	private List<Papeleta> listaPapeleta;
    private List<Capacitacion> listaCapacitacion;
    private List<Formacion> listaFormacion;
    private List<Flota> listaBuses;
    private List<Incidencia> listaIncidencias;
    private List<Cese> listaCeses;
    private List<Historial_AsigPCT> listaHistorial_asig;
    private List<Historial_AsigPCT> listaHistorial_asigFilter;
	private Boolean editar;
	private Boolean editarImagen;
	private Boolean pilotoGuardado;
	private String lastDNI;
	private Historial_Laboral historial;
	private Datos_Familiares datosF;
	private Documento documento;
	private Evaluacion evaluacion;
	private Papeleta papeleta;
	private Formacion formacion;
	private Capacitacion capacitacion;
	private Incidencia incidencia;
	private Cese cese;
	
	private Boolean actBtnEvaluacion;
	private Boolean actBtnDocumentos;
	private Boolean actBtnFormacion;
	private Boolean actBtnCapacitacion;
	private Boolean actBtnPapeletas;
	
	private String sdepartamento;
	private String sprovincia;
	private List<Ubigeo> listaDepartamentos;
	private List<Ubigeo> listaProvincias;
	private List<Ubigeo> listaDistritos;
	
	private UploadedFile archivo;
	private UploadedFile archivoDocumento;
	private UploadedFile archivoEvaluacion;
	private UploadedFile archivoPapeleta;
	private UploadedFile archivoFormacion;
	private UploadedFile archivoCapacitacion;
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	
	//services
	private PilotoService pilotoService;
	private MenuServices menuServices;
	private Historial_LaboralService historialService;
	private Datos_FamiliaresService datosFService;
	private DocumentoService documentoService;
	private EvaluacionService evaluacionService;
	private PapeletaService papeletaService;
	private FormacionService formacionService;
	private CapacitacionService capacitacionService;
	private IncidenciaServices incidenciaServices;
	private FlotaService flotaService;
	private UbigeoService ubigeoService;
	private CeseService ceseService;
	private Historial_AsigPCTServices historial_asigPCTServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	private String codigo_Obtener_Foto;
	
	
	/****Para el historial****/
	private Date fechaInicio;
	private Date fechaFin;
	

	private RequestContext context;
	
	public PilotoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.pilotoGuardado =  Boolean.FALSE;
		this.listaPilotoSelect =new ArrayList<Piloto>();
		
		this.pilotoService = new PilotoService();
		this.menuServices=new MenuServices();
		this.historialService = new Historial_LaboralService();
		this.datosFService = new Datos_FamiliaresService();
		this.documentoService = new DocumentoService();
		this.evaluacionService = new EvaluacionService();
		this.papeletaService = new PapeletaService();
		this.capacitacionService = new CapacitacionService();
		this.formacionService = new FormacionService();
		this.flotaService = new FlotaService();
		this.ubigeoService = new UbigeoService();
		this.incidenciaServices = new IncidenciaServices();
		this.ceseService = new CeseService();
		this.historial_asigPCTServices = new Historial_AsigPCTServices();
		
		this.piloto = new Piloto();
		this.historial = new Historial_Laboral();
		this.datosF = new Datos_Familiares();
		this.documento = new Documento();
		this.evaluacion = new Evaluacion();
		this.papeleta = new Papeleta();
		this.formacion = new Formacion();
		this.capacitacion  = new Capacitacion();
		this.incidencia = new Incidencia();
		this.cese = new Cese();
		
		setearValorbotonesFichas(Boolean.TRUE);
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPiloto = pilotoService.findAll();
			this.listaDepartamentos = ubigeoService.listarDepartamentos();
			int codMenu = menuServices.opcionMenuByPretty("pretty:piloto");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void setearValorbotonesFichas(boolean value){
		this.actBtnEvaluacion = value;
		this.actBtnDocumentos = value;
		this.actBtnFormacion = value;
		this.actBtnCapacitacion = value;
		this.actBtnPapeletas = value;
	}
	
	public void listarProvincias(){
		try {
			this.listaProvincias = this.ubigeoService.listarProvincias(this.sdepartamento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarDistritos(){
		try {
			this.listaDistritos  = this.ubigeoService.listardistritos(this.sdepartamento, this.sprovincia);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void limpiarValores(){
		//this.listaDepartamentos = ;
		this.listaProvincias = new ArrayList<>();
		this.listaDistritos= new ArrayList<>();
		this.sdepartamento = "";
		this.sprovincia = "";
	}
	
	public void cancelar(){
		this.piloto = new Piloto();
	}

	public void guardarPiloto(){
		
		 Boolean valido=Boolean.TRUE;
		 RequestContext context = RequestContext.getCurrentInstance();  
	   	 context.addCallbackParam("esValido", valido );
	   	 boolean _pase = false;
		try {
			piloto.setNombres(piloto.getNombres().toUpperCase().trim());
			piloto.setApellidoPaterno(piloto.getApellidoPaterno().toUpperCase().trim());
			piloto.setApellidoMaterno(piloto.getApellidoMaterno().toUpperCase().trim());
			if(this.editar)
			{
				boolean _paseEdit = false;
	   	 		
	   	 		Piloto pilo = this.pilotoService.findById(this.piloto.getIdPiloto());
	   	 		if(pilo.getdNI().equals(this.piloto.getdNI())){
	   	 			   _paseEdit = true;
	   	 		}else{
		   	 		int dniRep = this.pilotoService.verificarDniCarnetRepetidos(this.piloto.getdNI());
		   	 		if(dniRep>0){
			   	 		 FacesUtils.showFacesMessage("DNI del piloto se encuentra ya registrado.",Constante.INFORMACION);
			   	 		 context.update("msgNuevo");
			   	 		_paseEdit = false;
		   	 		}else{
		   	 		    _paseEdit = true;
		   	 		}
	   	 		}
				
				if(_paseEdit){
				
					this.piloto.setIdPiloto(this.piloto.getIdPiloto());
					if(this.archivo == null){
						this.pilotoService.actualizarPilotoSnFoto(piloto);
						 log.setAccion("UPDATE");
			             log.setDescripcion("Se actualiza el Piloto: " + piloto.getNombres() + " " + piloto.getApellidoPaterno() );
			             logmb.insertarLog(log);
						FacesUtils.showFacesMessage("Piloto actualizado correctamente.",Constante.INFORMACION);
						context.update("sms");
					}else{
						byte[] contenido = this.archivo.getContents();
						this.piloto.setImagen(contenido);
						this.pilotoService.actualizarPiloto(piloto);
						 log.setAccion("UPDATE");
			             log.setDescripcion("Se actualiza la Piloto: " + piloto.getNombres() + " " + piloto.getApellidoPaterno() );
			             logmb.insertarLog(log);
						FacesUtils.showFacesMessage("Piloto actualizado correctamente.",Constante.INFORMACION);	
						context.update("sms");
					}
					_paseEdit = false;
					_pase = true;
				}
				
			}else
			{
				//validamos el dni no se encuentre en base de datos
				int dniRep = this.pilotoService.verificarDniCarnetRepetidos(this.piloto.getdNI());
	   	 		if(dniRep>0){
		   	 		FacesUtils.showFacesMessage("DNI del piloto se encuentra ya registrado.",Constante.INFORMACION);
		   	 		context.update("msgNuevo");
		   	 		_pase = false;
	   	 		}else{
		   	 		this.piloto.setEstado(Boolean.TRUE);
					byte[] contenido = null;
					
					if(this.archivo !=null){
						contenido = this.archivo.getContents();
					}
					this.piloto.setImagen(contenido);
					this.piloto.setFechaCreacion(new Date());
					this.piloto.setUsuarioCreacion(this.login.getUsuario().getLogin());
					this.pilotoService.crearPiloto(piloto);
					this.pilotoGuardado = Boolean.TRUE;
					this.lastDNI = this.piloto.getdNI(); 
					log.setAccion("INSERT");
		            log.setDescripcion("Se inserta la piloto: " + piloto.getNombres() + "" +  piloto.getApellidoPaterno());
		            logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Piloto registrado correctamente.",Constante.INFORMACION);
					_pase = true;
	   	 		}
			}
			
			if(_pase){
				//this.listaDepartamentos = new ArrayList<>();
//				this.listaProvincias = new ArrayList<>();
//				this.listaDistritos= new ArrayList<>();
//				this.sdepartamento = "";
//				this.sprovincia = "";
				
				listaPiloto = this.pilotoService.findAll();
				this.piloto = new Piloto();
				this.editar =  Boolean.FALSE;
				_pase = false;
				
				//this.listaDepartamentos = this.ubigeoService.listarDepartamentos();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void actualizarDatosGenerales(Piloto pilt)
	{
		try {
			this.pilotoService.actualizarPiloto(pilt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarPiloto()
	{
		try {
			this.pilotoService.eliminarPiloto(piloto.getIdPiloto());
			listaPiloto.remove(piloto);
			FacesUtils.showFacesMessage("Piloto eliminada correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Piloto: " + piloto.getNombres() + "" + piloto.getApellidoPaterno());
	        logmb.insertarLog(log);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.piloto =  new Piloto();
		
	}

	
	
	public void subirImagen(FileUploadEvent event) {  
		
        FacesMessage msg = new FacesMessage("Ok", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        
        this.archivo = event.getFile();
        try {
        	this.piloto.setArchivoFoto(new DefaultStreamedContent(this.archivo.getInputstream(), "image/jpg"));
        	this.login.setPiloto(this.piloto);
        	this.editarImagen = Boolean.FALSE;
    
        	//this.login.setArchivoFoto(new DefaultStreamedContent(this.archivo.getInputstream(), "image/jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }  
	
	public void editarPiloto(Piloto plt)
	{
		
		try {
			setearValorbotonesFichas(Boolean.TRUE);
			this.editar = Boolean.TRUE;
			this.titulo = "Modificar piloto";
			this.editarImagen = Boolean.TRUE;
			this.piloto = plt;
			this.codigo_Obtener_Foto ="P"+ this.piloto.getIdPiloto();
			this.pilotoGuardado = Boolean.TRUE;
			this.lastDNI = this.piloto.getdNI();
			this.datosF = new Datos_Familiares();
			this.historial = new Historial_Laboral();
			this.documento = new Documento();
			this.capacitacion = new Capacitacion();
			this.evaluacion = new Evaluacion();
			this.formacion = new Formacion();
			this.listaHistorial = this.historialService.findByPilotoId(this.piloto.getIdPiloto());
		    this.listaDatosF = this.datosFService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaDocumentos = this.documentoService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaEvauacion = this.evaluacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaPapeleta = this.papeletaService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaFormacion = this.formacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaCapacitacion = this.capacitacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.listaIncidencias = this.incidenciaServices.findByPilotoId(this.piloto.getIdPiloto());
			this.listaBuses = this.flotaService.listaFlotaActivas();
			
			this.listaCeses = this.ceseService.listxPiloto(this.piloto.getIdPiloto());
			
			//obteniendo los datos de ubigeo 
			this.listaProvincias = new ArrayList<>();
			this.listaDepartamentos = new ArrayList<>();
			this.listaDistritos = new ArrayList<>();
			
			Ubigeo ubige = new Ubigeo();
			ubige = this.ubigeoService.obtenerUbigeoById(this.piloto.getSid_ubigeo());
			
			this.listaDepartamentos = this.ubigeoService.listarDepartamentos();
			this.listaProvincias = this.ubigeoService.listarProvincias(ubige.getSdepartamento());
			this.listaDistritos = this.ubigeoService.listardistritos(ubige.getSdepartamento(), ubige.getSprovincia());
			
			this.sdepartamento = ubige.getSdepartamento();
			this.sprovincia = ubige.getSprovincia();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoPiloto()
	{
		this.editar =  Boolean.FALSE;
		this.piloto = new Piloto();
		this.pilotoGuardado = Boolean.FALSE;
		codigo_Obtener_Foto = "P";
		setearValorbotonesFichas(Boolean.TRUE);
		this.titulo = "Registrar nuevo piloto";
	}
	
	/*
	public void actualizarLaboral()
	{
		try {
			//Integer id = this.pilotoService.findLast();
			this.piloto.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.piloto.setUltimaEmpresa(this.piloto.getUltimaEmpresa().trim().toUpperCase());
			this.piloto.setUltimoCargo(this.piloto.getUltimoCargo().trim().toUpperCase());
			this.piloto.setMotivoCese(this.piloto.getMotivoCese().trim().toUpperCase());
			this.pilotoService.actualizarPilotoLaboral(piloto);
			FacesUtils.showFacesMessage("Datos laborales del piloto actualizados correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	
	public void actualizarFamiliar()
	{
		System.out.println("actualizando lo familiar:"+this.piloto.getDniEsposa());
		try {
			this.piloto.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.piloto.setNombreEsposa(this.piloto.getNombreEsposa().trim().toUpperCase());
			this.piloto.setNombresHijo1(this.piloto.getNombresHijo1().trim().toUpperCase());
			this.piloto.setNombresHijo2(this.piloto.getNombresHijo2().trim().toUpperCase());
			this.pilotoService.actualizarPilotoFamiliar(piloto);
			FacesUtils.showFacesMessage("Datos familiares del piloto actualizados correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void actualizarEvaluaciones()
	{
		System.out.println("actualizando ecaluaciones");
		
	}
	*/
	
/**************HISTORIAL LABORAL****************/
	
	public void guardarHistorial()
	{

		try {
			this.historial.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.historial.setFechaUltimaActualizacion(new Date());
			this.historial.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.historialService.crearHistorial_Piloto(historial); 
			this.listaHistorial = this.historialService.findByPilotoId(this.historial.getIdPiloto());
			this.historial = new Historial_Laboral();
			FacesUtils.showFacesMessage("Historial registrado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void eliminarHistorial() throws IOException{
		try {
			historialService.eliminarHistorial(historial.getIdHistorial());
			this.listaHistorial = this.historialService.findByPilotoId(this.historial.getIdPiloto());
			FacesUtils.showFacesMessage("Historial eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
/**************DATOS FAMILIARES****************/
	
	public void guardarDatosF()
	{

		try {
			
			Piloto p = this.pilotoService.findByDNI(this.lastDNI);
			this.datosF.setIdPiloto(p.getIdPiloto());
			this.datosF.setFechaUltimaActualizacion(new Date());
			this.datosF.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.datosFService.crearDatosF_Piloto(datosF); 
			FacesUtils.showFacesMessage("Datos familiares registrados correctamente.",Constante.INFORMACION);
			this.listaDatosF = this.datosFService.findByPilotoId(this.datosF.getIdPiloto());
			this.datosF = new Datos_Familiares();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void eliminarDatosF(Datos_Familiares df) throws IOException{
		try {
			System.out.println("el id de evaluacion que se obtiene es ------------------> " + df.getIdDatosF());
			datosFService.eliminarDatosF(df.getIdDatosF());
			this.listaDatosF = this.datosFService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Familiar eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************EVALUACION****************/
	public void guardarEvaluacion() {
		System.out.println("actualizando evaluacion");
		try {
			byte[] contenido = this.archivoEvaluacion.getContents();
			this.evaluacion.setImagen(contenido);
			this.evaluacion.setPilotoId(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.evaluacion.setRutaImagen("/var/www/evaluacion/");
			this.evaluacion.setFechaUltimaActualizacion(new Date());
			this.evaluacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.evaluacionService.crearEvaluacion_Piloto(this.evaluacion);
			this.listaEvauacion = this.evaluacionService.findByPilotoId(this.evaluacion.getPilotoId());
			this.evaluacion = new Evaluacion();
			this.actBtnEvaluacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Evaluación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void guardarDocumentos()
	{
		System.out.println("actualizando documentos");
		try {
			byte[] contenido = this.archivoDocumento.getContents();
			this.documento.setImagen(contenido);
			this.documento.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.documento.setRutaImagen("/var/www/documentos/");
			this.documento.setFechaUltimaActualizacion(new Date());
			this.documento.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.documentoService.crearDocumento_Piloto(this.documento);
			this.listaDocumentos = this.documentoService.findByPilotoId(this.documento.getIdPiloto());
			this.documento = new Documento();
			this.actBtnDocumentos = Boolean.TRUE;
			FacesUtils.showFacesMessage("Documento registrado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void guardarPapeleta()
	{
		try {
			byte[] contenido = this.archivoPapeleta.getContents();
			this.papeleta.setImagen(contenido);
			this.papeleta.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.papeleta.setRutaImagen("/var/www/papeleta/");
			this.papeleta.setFechaUltimaActualizacion(new Date());
			this.papeleta.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.papeletaService.crearPapeleta(this.papeleta);
			this.listaPapeleta = this.papeletaService.findByPilotoId(this.papeleta.getIdPiloto());
			this.papeleta = new Papeleta();
			this.actBtnPapeletas = Boolean.FALSE;
			FacesUtils.showFacesMessage("Papeleta registrado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void guardarFormacion()
	{

		try {
			byte[] contenido = this.archivoFormacion.getContents();
			this.formacion.setImagen(contenido);
			this.formacion.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.formacion.setRutaImagen("/var/www/formacion/");
			this.formacion.setFechaUltimaActualizacion(new Date());
			this.formacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.formacionService.crearFormacion_Piloto(this.formacion);
			this.listaFormacion = this.formacionService.findByPilotoId(this.formacion.getIdPiloto());
			this.formacion = new Formacion();
			this.actBtnFormacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Formación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void guardarCapacitacion()
	{

		try {
			byte[] contenido = this.archivoCapacitacion.getContents();
			this.capacitacion.setImagen(contenido);
			this.capacitacion.setIdPiloto(this.pilotoService.findByDNI(this.lastDNI).getIdPiloto());
			this.capacitacion.setRutaImagen("/var/www/capacitacion/");
			this.capacitacion.setFechaUltimaActualizacion(new Date());
			this.capacitacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.capacitacionService.crearCapacitacion_Piloto(this.capacitacion);
			this.listaCapacitacion = this.capacitacionService.findByPilotoId(this.capacitacion.getIdPiloto());
			this.capacitacion = new Capacitacion();
			this.actBtnCapacitacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Capacitación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void subirDocumento(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Documento", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoDocumento = event.getFile();
        this.actBtnDocumentos = Boolean.FALSE;
    } 
	
	public void subirPapeleta(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Papeleta", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoPapeleta = event.getFile();
        this.actBtnPapeletas = Boolean.FALSE;
    } 
	
	public void subirFormacion(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Formacion", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoFormacion = event.getFile();
        this.actBtnFormacion = Boolean.FALSE;
    } 
	
	public void subirCapacitacion(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Capacitacion", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoCapacitacion = event.getFile();
        this.actBtnCapacitacion = Boolean.FALSE;
    } 
	
	public void recuperarDocumuento(Documento docu) throws IOException
	{
		System.out.println("el documento es:"+documento.getNombre());
		
		 // Prepare.
	    byte[] pdfData = docu.getImagen();
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"documento.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(pdfData);
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
		
	}

	public void subirEvaluacion(FileUploadEvent event) { 
		    FacesMessage msg = new FacesMessage("Ok, Documento", event.getFile().getFileName() + " esta cargado.");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	        this.archivoEvaluacion = event.getFile();
	        this.actBtnEvaluacion = Boolean.FALSE;
	}
	
	public void recuperarEvaluacion(Evaluacion eva) throws IOException{
		System.out.println("la evaluacion es:"+evaluacion.getNombre());
		
		 // Prepare.
	    byte[] pdfData = eva.getImagen();
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"evaluacion.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(pdfData);
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	}
	
	public void recuperarFormacion(Formacion forma) throws IOException{
		System.out.println("entro en formacion");
		
		 // Prepare.
	    byte[] pdfData = forma.getImagen();
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"formacion.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(pdfData);
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	}
	
	public void recuperarCapacitacion(Capacitacion cap) throws IOException{
		
		System.out.println("entro capacitacion");
		 // Prepare.
	    byte[] pdfData = cap.getImagen();
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"capacitacion.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(pdfData);
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	}
	
	public void recuperarPapeleta(Papeleta pap) throws IOException
	{
		System.out.println("entro papeleta");
		
		 // Prepare.
	    byte[] pdfData = pap.getImagen();
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    response.setHeader("Content-disposition", "attachment; filename=\"papeleta.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(pdfData);
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
		
	}
	
	
	
	public void eliminarEvaluacion(Evaluacion eva) throws IOException{
		try {
			System.out.println("el id de evaluacion que se obtiene es ------------------> " + eva.getEvaluacionId());
			evaluacionService.eliminarEvaluacion(eva.getEvaluacionId());
			this.listaEvauacion = this.evaluacionService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Evaluación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarDocumento(Documento doc) throws IOException{
		try {
			documentoService.eliminarDocumento(doc.getDocumentoId());
			this.listaDocumentos = this.documentoService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Documento eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarFormacion(Formacion forma) throws IOException{
		try {
			formacionService.eliminarFormacion(forma.getFormacionId());
			this.listaFormacion = this.formacionService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Formación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarCapacitacion(Capacitacion cap) throws IOException{
		try {
			capacitacionService.eliminarCapacitacion(cap.getCapacitacionId());
			this.listaCapacitacion = this.capacitacionService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Capacitación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarPapeleta(Papeleta pap) throws IOException{
		try {
			papeletaService.eliminarPapeleta(pap.getPapeletaid());
			this.listaPapeleta = this.papeletaService.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Papeleta eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cambiarEstado(Piloto pilot){
		
		   if(pilot.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   pilot.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   pilot.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.pilotoService.actualizarPiloto(pilot);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Pioto: " + piloto.getNombres());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de piloto actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
/**************INCIDENCIAS****************/
	
	public void guardarIncidencia()
	{

		try {			
			Piloto p = this.pilotoService.findByDNI(this.lastDNI);
			this.incidencia.setIdPiloto(p.getIdPiloto());
			this.incidencia.setFechaUltimaActualizacion(new Date());
			this.incidencia.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.incidenciaServices.crearIncidencia_Piloto(incidencia); 
			FacesUtils.showFacesMessage("Incidencia registrada correctamente.",Constante.INFORMACION);
			this.listaIncidencias = this.incidenciaServices.findByPilotoId(this.incidencia.getIdPiloto());
			this.incidencia = new Incidencia();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void eliminarIncidencia(Incidencia inc) throws IOException{
		try {
			System.out.println("el id de la incidencia que se obtiene es ------------------> " + inc.getIncidenciaID());
			incidenciaServices.eliminarIncicencia(inc.getIncidenciaID());
			this.listaIncidencias = this.incidenciaServices.findByPilotoId(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Incidencia eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/**************CESE****************/
	
	public void guardarCese()
	{

		try {			
			Piloto p = this.pilotoService.findByDNI(this.lastDNI);
			this.cese.setIdPiloto(p.getIdPiloto());
			this.cese.setFechaUltimaActualizacion(new Date());
			this.cese.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.ceseService.crearCese(cese); 
			FacesUtils.showFacesMessage("Cese registrado correctamente.",Constante.INFORMACION);
			this.listaCeses = this.ceseService.listxPiloto(this.piloto.getIdPiloto());
			this.cese = new Cese();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void eliminarCese(Cese c) throws IOException{
		try {
			System.out.println("el id del cese que se obtiene es ------------------> " + c.getIdCese());
			ceseService.eliminarCese(c.getIdCese());
			this.listaCeses = this.ceseService.listxPiloto(this.piloto.getIdPiloto());
			FacesUtils.showFacesMessage("Cese eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**Para obtener Historial de Buses asignados**/
	
	public void setearPiloto(Piloto p){
		this.piloto = p;
		this.listaHistorial_asig = new ArrayList<>();
		this.fechaInicio = new Date();
		this.fechaFin = new Date();
		//this.context = RequestContext.getCurrentInstance();
		//context.update("formHistorial");
	}

	public void obtenerHistorial(){
		try {
			Date fFin = sumaDias(getFechaFin(),1);
			this.listaHistorial_asig = historial_asigPCTServices.findHistorialPilotoByFechas(piloto.getIdPiloto(), getFechaInicio(),fFin);
			for(Historial_AsigPCT h : listaHistorial_asig){
				Flota f = flotaService.findById(h.getIdBus());
				h.setNumeroBus(f.getNumero());
				if(h.getIdPiloto()!=null){
					h.setFuncion("PILOTO");
				}else h.setFuncion("COPILOTO");
				
			}
			//this.listaHistorial_asigFilter = this.listaHistorial_asig;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, dias);
		return cal.getTime();
	}
	
	
	public void generarPDF(Piloto p){
		System.out.println(p.getFoto());
		try {
			this.piloto = p;
			this.listaPilotoSelect.clear();
			System.out.println(piloto.getIdPiloto());
			Ubigeo u = ubigeoService.obtenerUbigeoById(piloto.getSid_ubigeo());
			if(u!=null){
				this.piloto.setDepartamento(u.getSdepartamento());
				this.piloto.setProvincia(u.getSprovincia());
				this.piloto.setDistrito(u.getSdistrito());
			}
			
			this.listaHistorial = this.historialService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaHistorial(listaHistorial); 
			this.listaDatosF = this.datosFService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaDatosF(listaDatosF); 
			this.listaEvauacion = this.evaluacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaEvaluacion(listaEvauacion); 
			this.listaDocumentos = this.documentoService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaDocumentos(listaDocumentos); 
			this.listaFormacion = this.formacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaFormacion(listaFormacion); 
			this.listaCapacitacion = this.capacitacionService.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaCapacitacion(listaCapacitacion); 
			this.listaIncidencias = this.incidenciaServices.findByPilotoId(this.piloto.getIdPiloto());
			this.piloto.setListaIncidencias(listaIncidencias); 
			this.listaCeses = this.ceseService.listxPiloto(this.piloto.getIdPiloto());
			this.piloto.setListaCeses(listaCeses);
			this.listaPapeleta = this.papeletaService.findByPilotoId(this.piloto.getIdPiloto());
			for(Papeleta pa : listaPapeleta)
			{
				Flota f = flotaService.findById(pa.getIdBus());
				pa.setNroBus(f == null ? "-" :f.getNumero().toString());
			}
			this.piloto.setListaPapeletas(listaPapeleta);
			this.listaPilotoSelect.add(piloto); 
			this.imprimirPdf(listaPilotoSelect); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void imprimirPdf(List<Piloto> listaPilotoSelect) throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			Map<String, Object> input = new HashMap<String, Object>();
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			input.put("P_LOGO", p_logo);
			
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptFichaTrabajador.jasper");
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaPilotoSelect);

			ExportarArchivo.executePdf(bytes, "FichaTrabajador.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
	}
	
	
	//**set an get 

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public String getSprovincia() {
		return sprovincia;
	}

	public void setSprovincia(String sprovincia) {
		this.sprovincia = sprovincia;
	}

	public List<Ubigeo> getListaProvincias() {
		return listaProvincias;
	}

	public void setListaProvincias(List<Ubigeo> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}

	public List<Ubigeo> getListaDistritos() {
		return listaDistritos;
	}

	public void setListaDistritos(List<Ubigeo> listaDistritos) {
		this.listaDistritos = listaDistritos;
	}

	public List<Ubigeo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Ubigeo> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public String getSdepartamento() {
		return sdepartamento;
	}

	public void setSdepartamento(String sdepartamento) {
		this.sdepartamento = sdepartamento;
	}

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public FlotaService getFlotaService() {
		return flotaService;
	}

	public void setFlotaService(FlotaService flotaService) {
		this.flotaService = flotaService;
	}

	public List<Capacitacion> getListaCapacitacion() {
		return listaCapacitacion;
	}

	public void setListaCapacitacion(List<Capacitacion> listaCapacitacion) {
		this.listaCapacitacion = listaCapacitacion;
	}

	public List<Formacion> getListaFormacion() {
		return listaFormacion;
	}

	public void setListaFormacion(List<Formacion> listaFormacion) {
		this.listaFormacion = listaFormacion;
	}

	public Formacion getFormacion() {
		return formacion;
	}

	public void setFormacion(Formacion formacion) {
		this.formacion = formacion;
	}

	public Capacitacion getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(Capacitacion capacitacion) {
		this.capacitacion = capacitacion;
	}

	public UploadedFile getArchivoFormacion() {
		return archivoFormacion;
	}

	public void setArchivoFormacion(UploadedFile archivoFormacion) {
		this.archivoFormacion = archivoFormacion;
	}

	public UploadedFile getArchivoCapacitacion() {
		return archivoCapacitacion;
	}

	public void setArchivoCapacitacion(UploadedFile archivoCapacitacion) {
		this.archivoCapacitacion = archivoCapacitacion;
	}

	public EvaluacionService getEvaluacionService() {
		return evaluacionService;
	}

	public void setEvaluacionService(EvaluacionService evaluacionService) {
		this.evaluacionService = evaluacionService;
	}

	public PapeletaService getPapeletaService() {
		return papeletaService;
	}

	public void setPapeletaService(PapeletaService papeletaService) {
		this.papeletaService = papeletaService;
	}

	public FormacionService getFormacionService() {
		return formacionService;
	}

	public void setFormacionService(FormacionService formacionService) {
		this.formacionService = formacionService;
	}

	public CapacitacionService getCapacitacionService() {
		return capacitacionService;
	}

	public void setCapacitacionService(CapacitacionService capacitacionService) {
		this.capacitacionService = capacitacionService;
	}

	public List<Papeleta> getListaPapeleta() {
		return listaPapeleta;
	}

	public void setListaPapeleta(List<Papeleta> listaPapeleta) {
		this.listaPapeleta = listaPapeleta;
	}

	public UploadedFile getArchivoPapeleta() {
		return archivoPapeleta;
	}

	public void setArchivoPapeleta(UploadedFile archivoPapeleta) {
		this.archivoPapeleta = archivoPapeleta;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public List<Piloto> getListaPiloto() {

		return listaPiloto;
	}

	public void setListaPiloto(List<Piloto> listaPiloto) {
		this.listaPiloto = listaPiloto;
	}

	public List<Piloto> getListaFiltroPiloto() {
		return listaFiltroPiloto;
	}

	public void setListaFiltroPiloto(List<Piloto> listaFiltroPiloto) {
		this.listaFiltroPiloto = listaFiltroPiloto;
	}

	public PilotoService getPilotoService() {
		return pilotoService;
	}

	public void setPilotoService(PilotoService pilotoService) {
		this.pilotoService = pilotoService;
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


	public LoginMB getLogin() {
		return login;
	}

	public void setLogin(LoginMB login) {
		this.login = login;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public Boolean getPilotoGuardado() {
		return pilotoGuardado;
	}

	public void setPilotoGuardado(Boolean pilotoGuardado) {
		this.pilotoGuardado = pilotoGuardado;
	}

	/**
	 * @return the lastDNI
	 */
	public String getLastDNI() {
		return lastDNI;
	}

	/**
	 * @param lastDNI the lastDNI to set
	 */
	public void setLastDNI(String lastDNI) {
		this.lastDNI = lastDNI;
	}

	public List<Documento> getListaDocumentos() {
		
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<Evaluacion> getListaEvauacion() {
		return listaEvauacion;
	}

	public void setListaEvauacion(List<Evaluacion> listaEvauacion) {
		this.listaEvauacion = listaEvauacion;
	}

	public UploadedFile getArchivoDocumento() {
		return archivoDocumento;
	}

	public void setArchivoDocumento(UploadedFile archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}

	public UploadedFile getArchivoEvaluacion() {
		return archivoEvaluacion;
	}

	public void setArchivoEvaluacion(UploadedFile archivoEvaluacion) {
		this.archivoEvaluacion = archivoEvaluacion;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Boolean getEditarImagen() {
		return editarImagen;
	}

	public void setEditarImagen(Boolean editarImagen) {
		this.editarImagen = editarImagen;
	}

	public Papeleta getPapeleta() {
		return papeleta;
	}

	public void setPapeleta(Papeleta papeleta) {
		this.papeleta = papeleta;
	}

	public String getCodigo_Obtener_Foto() {
		return codigo_Obtener_Foto;
	}

	public void setCodigo_Obtener_Foto(String codigo_Obtener_Foto) {
		this.codigo_Obtener_Foto = codigo_Obtener_Foto;
	}

	public Historial_Laboral getHistorial() {
		return historial;
	}

	public void setHistorial(Historial_Laboral historial) {
		this.historial = historial;
	}

	public List<Historial_Laboral> getListaHistorial() {
		return listaHistorial;
	}

	public void setListaHistorial(List<Historial_Laboral> listaHistorial) {
		this.listaHistorial = listaHistorial;
	}

	public List<Datos_Familiares> getListaDatosF() {
		return listaDatosF;
	}

	public void setListaDatosF(List<Datos_Familiares> listaDatosF) {
		this.listaDatosF = listaDatosF;
	}

	public Datos_Familiares getDatosF() {
		return datosF;
	}

	public void setDatosF(Datos_Familiares datosF) {
		this.datosF = datosF;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public List<Incidencia> getListaIncidencias() {
		return listaIncidencias;
	}

	public void setListaIncidencias(List<Incidencia> listaIncidencias) {
		this.listaIncidencias = listaIncidencias;
	}

	public List<Cese> getListaCeses() {
		return listaCeses;
	}

	public void setListaCeses(List<Cese> listaCeses) {
		this.listaCeses = listaCeses;
	}

	public Cese getCese() {
		return cese;
	}

	public void setCese(Cese cese) {
		this.cese = cese;
	}

	public Historial_LaboralService getHistorialService() {
		return historialService;
	}

	public void setHistorialService(Historial_LaboralService historialService) {
		this.historialService = historialService;
	}

	public Datos_FamiliaresService getDatosFService() {
		return datosFService;
	}

	public void setDatosFService(Datos_FamiliaresService datosFService) {
		this.datosFService = datosFService;
	}

	public IncidenciaServices getIncidenciaServices() {
		return incidenciaServices;
	}

	public void setIncidenciaServices(IncidenciaServices incidenciaServices) {
		this.incidenciaServices = incidenciaServices;
	}

	public UbigeoService getUbigeoService() {
		return ubigeoService;
	}

	public void setUbigeoService(UbigeoService ubigeoService) {
		this.ubigeoService = ubigeoService;
	}

	public CeseService getCeseService() {
		return ceseService;
	}

	public void setCeseService(CeseService ceseService) {
		this.ceseService = ceseService;
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

	public List<Historial_AsigPCT> getListaHistorial_asig() {
		return listaHistorial_asig;
	}

	public void setListaHistorial_asig(List<Historial_AsigPCT> listaHistorial_asig) {
		this.listaHistorial_asig = listaHistorial_asig;
	}

	public List<Historial_AsigPCT> getListaHistorial_asigFilter() {
		return listaHistorial_asigFilter;
	}

	public void setListaHistorial_asigFilter(
			List<Historial_AsigPCT> listaHistorial_asigFilter) {
		this.listaHistorial_asigFilter = listaHistorial_asigFilter;
	}

	public List<Piloto> getListaPilotoSelect() {
		return listaPilotoSelect;
	}

	public void setListaPilotoSelect(List<Piloto> listaPilotoSelect) {
		this.listaPilotoSelect = listaPilotoSelect;
	}

	public Boolean getActBtnEvaluacion() {
		return actBtnEvaluacion;
	}

	public void setActBtnEvaluacion(Boolean actBtnEvaluacion) {
		this.actBtnEvaluacion = actBtnEvaluacion;
	}

	public Boolean getActBtnDocumentos() {
		return actBtnDocumentos;
	}

	public void setActBtnDocumentos(Boolean actBtnDocumentos) {
		this.actBtnDocumentos = actBtnDocumentos;
	}

	public Boolean getActBtnFormacion() {
		return actBtnFormacion;
	}

	public void setActBtnFormacion(Boolean actBtnFormacion) {
		this.actBtnFormacion = actBtnFormacion;
	}

	public Boolean getActBtnCapacitacion() {
		return actBtnCapacitacion;
	}

	public void setActBtnCapacitacion(Boolean actBtnCapacitacion) {
		this.actBtnCapacitacion = actBtnCapacitacion;
	}

	public Boolean getActBtnPapeletas() {
		return actBtnPapeletas;
	}

	public void setActBtnPapeletas(Boolean actBtnPapeletas) {
		this.actBtnPapeletas = actBtnPapeletas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	

	
	
	
}

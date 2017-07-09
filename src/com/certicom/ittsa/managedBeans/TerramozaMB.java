package com.certicom.ittsa.managedBeans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.model.SelectItem;
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
import com.certicom.ittsa.domain.Historial_Laboral;
import com.certicom.ittsa.domain.Incidencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.services.CapacitacionService;
import com.certicom.ittsa.services.CeseService;
import com.certicom.ittsa.services.Datos_FamiliaresService;
import com.certicom.ittsa.services.DocumentoService;
import com.certicom.ittsa.services.EvaluacionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.FormacionService;
import com.certicom.ittsa.services.Historial_LaboralService;
import com.certicom.ittsa.services.IncidenciaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PapeletaService;
import com.certicom.ittsa.services.TerramozaService;
import com.certicom.ittsa.services.UbigeoService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;


@ManagedBean(name="terramozaMB")
@ViewScoped

public class TerramozaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Terramoza terramoza;
	private List<Terramoza> listaTerramoza;
	private List<Terramoza> listaTerramozaSelect;
	private List<Terramoza> listaFiltroTerramoza;
	private List<Historial_Laboral> listaHistorial;
	private List<Datos_Familiares> listaDatosF;
	private List<Documento> listaDocumentos;
	private List<Evaluacion> listaEvaluacion;
    private List<Capacitacion> listaCapacitacion;
    private List<Formacion> listaFormacion;
    private List<Flota> listaBuses;
    private List<Incidencia> listaIncidencias;
    private List<Cese> listaCeses;
	private Boolean editar;
	private Boolean editarImagen;
	private Boolean terramozaGuardado;
	private String lastDNI;
	private Historial_Laboral historial;
	private Datos_Familiares datosF;
	private Documento documento;
	private Evaluacion evaluacion;
	private Formacion formacion;
	private Capacitacion capacitacion;
	private Incidencia incidencia;
	private Cese cese;
	
	private Boolean actBtnEvaluacion;
	private Boolean actBtnDocumentos;
	private Boolean actBtnFormacion;
	private Boolean actBtnCapacitacion;
	private Boolean actBtnIncidencias;
	
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
	private TerramozaService terramozaService;
	private MenuServices menuServices;
	private Historial_LaboralService historialService;
	private Datos_FamiliaresService datosFService;
	private DocumentoService documentoService;
	private EvaluacionService evaluacionService;
	private PapeletaService papeletaService;
	private FormacionService formacionService;
	private CapacitacionService capacitacionService;
	private FlotaService flotaService;
	private UbigeoService ubigeoService;
	private IncidenciaServices incidenciaServices;
	private CeseService ceseService;
	
	
	  private SelectItem[] listEstados;  
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	private String codigo_Obtener_Foto;
	
	public TerramozaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.terramozaGuardado =  Boolean.FALSE;
		this.listaTerramozaSelect =new ArrayList<Terramoza>();
		this.terramozaService = new TerramozaService();
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
		
		this.terramoza = new Terramoza();
		this.historial = new Historial_Laboral();
		this.datosF = new Datos_Familiares();
		this.documento = new Documento();
		this.evaluacion = new Evaluacion();
		this.formacion = new Formacion();
		this.capacitacion  = new Capacitacion();
		this.incidencia = new Incidencia();
		this.cese = new Cese();
		
		setearValorbotonesFichas(Boolean.TRUE);
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		listEstados = filterEstado();
		//listando
		try {
			this.listaTerramoza = terramozaService.findAll();
			this.listaDepartamentos = ubigeoService.listarDepartamentos();
			int codMenu = menuServices.opcionMenuByPretty("pretty:terramoza");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.terramoza = new Terramoza(); 
	}
	
	
	public void setearValorbotonesFichas(boolean value){
		this.actBtnEvaluacion = value;
		this.actBtnDocumentos = value;
		this.actBtnFormacion = value;
		this.actBtnCapacitacion = value;
		this.actBtnIncidencias = value;
	}

	public void guardarTerramoza(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    boolean _pase = false;
		try {
			terramoza.setNombres(terramoza.getNombres().toUpperCase().trim());
			terramoza.setApellidos(terramoza.getApellidos().toUpperCase().trim());

			if(this.editar)
			{
				boolean _paseEdit = false;
	   	 		Terramoza terra = this.terramozaService.findById(this.terramoza.getIdTerramoza());
	   	 		if(terra.getdNI().equals(this.terramoza.getdNI())){
	   	 			   _paseEdit = true;
	   	 		}else{
		   	 		int dniRep = this.terramozaService.verificarDniCarnetRepetidos(this.terramoza.getdNI());
		   	 		if(dniRep>0){
			   	 		 FacesUtils.showFacesMessage("DNI de la terramoza se encuentra ya registrado.",Constante.INFORMACION);
			   	 		 context.update("msgNuevo");
			   	 		_paseEdit = false;
		   	 		}else{
		   	 		    _paseEdit = true;
		   	 		}
	   	 		}
	   	 		
	   	 		if(_paseEdit){
				this.terramoza.setIdTerramoza(this.terramoza.getIdTerramoza());
				if(this.archivo == null){
					 this.terramozaService.actualizarTerramozaSnFoto(terramoza);
					 log.setAccion("UPDATE");
		             log.setDescripcion("Se actualiza la Terramoza: " + terramoza.getNombres() + " " + terramoza.getApellidos() );
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Terramoza actualizada correctamente.",Constante.INFORMACION);
					context.update("sms");
				}else{
					byte[] contenido = this.archivo.getContents();
					this.terramoza.setImagen(contenido);
					this.terramozaService.actualizarTerramoza(terramoza);
					 log.setAccion("UPDATE");
		             log.setDescripcion("Se actualiza la Terramoza: " + terramoza.getNombres() + " " + terramoza.getApellidos() );
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Terramoza actualizada correctamente.",Constante.INFORMACION);	
					context.update("sms");
				}
				_paseEdit = false;
				_pase = true;
	   	 		}
				
			}else
			{
				//validamos el dni no se encuentre en base de datos
				int dniRep = this.terramozaService.verificarDniCarnetRepetidos(this.terramoza.getdNI());
	   	 		if(dniRep>0){
		   	 		FacesUtils.showFacesMessage("DNI de la terramoza se encuentra ya registrado.",Constante.INFORMACION);
		   	 		context.update("msgNuevo");
		   	 		_pase = false;
	   	 		}else{
	   	 		this.terramoza.setDisponibilidad(1);
				this.terramoza.setEstado(Boolean.TRUE);
				byte[] contenido = null;
			
				if(this.archivo!=null){
				 contenido = this.archivo.getContents();
				}
				this.terramoza.setImagen(contenido);
				this.terramoza.setFechaCreacion(new Date());
				this.terramoza.setUsuarioCreacion(this.login.getUsuario().getLogin());
				this.terramozaService.crearTerramoza(terramoza);
				this.terramozaGuardado = Boolean.TRUE;
				this.lastDNI = this.terramoza.getdNI(); 
				this.terramoza = this.terramozaService.findByDNI(this.terramoza.getdNI());
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la terramoza: " + terramoza.getNombres() + "" +  terramoza.getApellidos());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Terramoza registrada correctamente.",Constante.INFORMACION);
				   _pase = true;
	   	 		}
				
			}
			
			if(_pase){
				listaTerramoza = this.terramozaService.findAll();
				this.terramoza = new Terramoza();
				this.editar =  Boolean.FALSE;
				_pase = false;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
	public void actualizarDatosGenerales(Terramoza pilt)
	{
		try {
			this.terramozaService.actualizarTerramoza(pilt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void cambiarEstado(Terramoza pilot){
		
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
			   this.terramozaService.actualizarTerramoza(pilot);
				   //this.sistemaServices.updateSistema(sistem);
			   FacesUtils.showFacesMessage("Estado de terramoza modificada correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarTerramoza()
	{
		try {
			this.terramozaService.eliminarTerramoza(terramoza.getIdTerramoza());
			listaTerramoza.remove(terramoza);
			FacesUtils.showFacesMessage("Terramoza eliminada correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Terramoza: " + terramoza.getNombres() + "" + terramoza.getApellidos());
	        logmb.insertarLog(log);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.terramoza =  new Terramoza();
		
	}

	
	
	public void subirImagen(FileUploadEvent event) {  
		
        FacesMessage msg = new FacesMessage("Ok", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        
        this.archivo = event.getFile();
        try {
        	this.terramoza.setArchivoFoto(new DefaultStreamedContent(this.archivo.getInputstream(), "image/jpg"));
        	this.login.setTerramoza(this.terramoza);
        	this.editarImagen = Boolean.FALSE;
    
        	//this.login.setArchivoFoto(new DefaultStreamedContent(this.archivo.getInputstream(), "image/jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }  
	
	public void editarTerramoza(Terramoza t)
	{
		
		try {
			this.titulo = "Modificar terramoza";
			this.editar = Boolean.TRUE;
			this.editarImagen = Boolean.TRUE;
			this.terramoza = t;
			this.codigo_Obtener_Foto ="T"+ this.terramoza.getIdTerramoza();
			this.terramozaGuardado = Boolean.TRUE;
			this.lastDNI = this.terramoza.getdNI();
			
			this.datosF = new Datos_Familiares();
			this.historial = new Historial_Laboral();
			this.documento = new Documento();
			this.capacitacion = new Capacitacion();
			this.evaluacion = new Evaluacion();
			this.formacion = new Formacion();
			this.listaHistorial = this.historialService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaDatosF = this.datosFService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaEvaluacion = this.evaluacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaDocumentos = this.documentoService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaFormacion = this.formacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaCapacitacion = this.capacitacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaIncidencias = this.incidenciaServices.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.listaCeses = this.ceseService.listxTerramoza(this.terramoza.getIdTerramoza());
			
			//obteniendo los datos de ubigeo 
			this.listaProvincias = new ArrayList<>();
			this.listaDepartamentos = new ArrayList<>();
			this.listaDistritos = new ArrayList<>();
			
			Ubigeo ubige = new Ubigeo();
			System.out.println("el valor del ubifgeo es " +this.terramoza.getSid_ubigeo());
			ubige = this.ubigeoService.obtenerUbigeoById(this.terramoza.getSid_ubigeo());
			System.out.println("el ubigeo qeu trae es " + ubige.getSdepartamento());

			this.listaDepartamentos = this.ubigeoService.listarDepartamentos();
			this.listaProvincias = this.ubigeoService.listarProvincias(ubige.getSdepartamento());
			this.listaDistritos = this.ubigeoService.listardistritos(ubige.getSdepartamento(), ubige.getSprovincia());
			
			this.sdepartamento = ubige.getSdepartamento();
			this.sprovincia = ubige.getSprovincia();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoTerramoza()
	{
		this.editar =  Boolean.FALSE;
		this.terramoza = new Terramoza();
		this.terramozaGuardado = Boolean.FALSE;
		this.titulo = "Registrar nueva terramoza";
		codigo_Obtener_Foto = "T";
		
		this.listaHistorial = new ArrayList<>();
		this.listaDatosF = new ArrayList<>();
		this.listaEvaluacion = new ArrayList<>();
		this.listaDocumentos = new ArrayList<>();
		this.listaFormacion = new ArrayList<>();
		this.listaCapacitacion = new ArrayList<>();
		this.listaIncidencias = new ArrayList<>();
		this.listaCeses = new ArrayList<>();
	}
	
	/**************HISTORIAL LABORAL****************/
	
	public void guardarHistorial()
	{

		try {
			
			terramoza =  this.terramozaService.findByDNI(this.lastDNI);
			//this.historial.setIdTerramoza(this.terramozaService.findByDNI(this.lastDNI).getIdTerramoza());
			this.historial.setIdTerramoza(terramoza.getIdTerramoza());
			this.historial.setFechaUltimaActualizacion(new Date());
			this.historial.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.historialService.crearHistorial_Terramoza(historial);			 
			this.listaHistorial = this.historialService.findByTerramozaId(this.historial.getIdTerramoza());
			//this.terramoza.setListaHistorial(listaHistorial);//Para el reporte
			this.historial = new Historial_Laboral();
			FacesUtils.showFacesMessage("Historial registrado correctamente.",Constante.INFORMACION);
			
			this.listaHistorial = this.historialService.findByTerramozaId(this.terramoza.getIdTerramoza());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void eliminarHistorial() throws IOException{
		try {
			historialService.eliminarHistorial(historial.getIdHistorial());
			this.listaHistorial = this.historialService.findByTerramozaId(this.historial.getIdTerramoza());
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
			
			Terramoza t = this.terramozaService.findByDNI(this.lastDNI);
			this.datosF.setIdTerramoza(t.getIdTerramoza());
			this.datosF.setFechaUltimaActualizacion(new Date());
			this.datosF.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.datosFService.crearDatosF_Terramoza(datosF); 
			FacesUtils.showFacesMessage("Datos familiares registrados correctamente.",Constante.INFORMACION);
			this.listaDatosF = this.datosFService.findByTerramozaId(this.datosF.getIdTerramoza());
			//this.terramoza.setListaDatosF(listaDatosF); 
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
			this.listaDatosF = this.datosFService.findByTerramozaId(this.terramoza.getIdTerramoza());
			FacesUtils.showFacesMessage("Familiar eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************EVALUACION****************/
	
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
	
	public void guardarEvaluacion() {
		System.out.println("actualizando evaluacion");
		try {
			byte[] contenido = this.archivoEvaluacion.getContents();
			this.evaluacion.setImagen(contenido);
			this.evaluacion.setTerramozaId(this.terramozaService.findByDNI(this.lastDNI).getIdTerramoza()); 
			this.evaluacion.setRutaImagen("/var/www/evaluacion/Terramoza/");
			this.evaluacion.setFechaUltimaActualizacion(new Date());
			this.evaluacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.evaluacionService.crearEvaluacion_Terramoza(this.evaluacion);
			this.listaEvaluacion = this.evaluacionService.findByTerramozaId(this.evaluacion.getTerramozaId());
			this.evaluacion = new Evaluacion();
			this.actBtnEvaluacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Evaluación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarEvaluacion(Evaluacion eva) throws IOException{
		try {
			System.out.println("el id de evaluacion que se obtiene es ------------------> " + eva.getEvaluacionId());
			evaluacionService.eliminarEvaluacion(eva.getEvaluacionId());
			this.listaEvaluacion = this.evaluacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			FacesUtils.showFacesMessage("Evaluación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************DOCUMENTO****************/
	
	public void subirDocumento(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Documento", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoDocumento = event.getFile();
        this.actBtnDocumentos = Boolean.FALSE;
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
	
	public void guardarDocumentos()
	{
		System.out.println("actualizando documentos");
		try {
			byte[] contenido = this.archivoDocumento.getContents();
			this.documento.setImagen(contenido);
			this.documento.setIdTerramoza(this.terramozaService.findByDNI(this.lastDNI).getIdTerramoza());
			this.documento.setRutaImagen("/var/www/documentos/Terramoza/");
			this.documento.setFechaUltimaActualizacion(new Date());
			this.documento.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.documentoService.crearDocumento_Terramoza(this.documento);
			this.listaDocumentos = this.documentoService.findByTerramozaId(this.documento.getIdTerramoza());
			this.documento = new Documento();
			this.actBtnDocumentos = Boolean.TRUE;
			FacesUtils.showFacesMessage("Documento registrado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eliminarDocumento(Documento doc) throws IOException{
		try {
			documentoService.eliminarDocumento(doc.getDocumentoId());
			this.listaDocumentos = this.documentoService.findByTerramozaId(this.terramoza.getIdTerramoza()); 
			FacesUtils.showFacesMessage("Documento eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************FORMACION****************/
	
	public void subirFormacion(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Formacion", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoFormacion = event.getFile();
        this.actBtnFormacion = Boolean.FALSE;
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
	
	public void guardarFormacion()
	{

		try {
			byte[] contenido = this.archivoFormacion.getContents();
			this.formacion.setImagen(contenido);
			this.formacion.setIdTerramoza(this.terramozaService.findByDNI(this.lastDNI).getIdTerramoza());
			this.formacion.setRutaImagen("/var/www/formacion/");
			this.formacion.setFechaUltimaActualizacion(new Date());
			this.formacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.formacionService.crearFormacion_Terramoza(this.formacion);
			this.listaFormacion = this.formacionService.findByTerramozaId(this.formacion.getIdTerramoza());
			this.formacion = new Formacion();
			this.actBtnFormacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Formación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void eliminarFormacion(Formacion forma) throws IOException{
		try {
			formacionService.eliminarFormacion(forma.getFormacionId());
			this.listaFormacion = this.formacionService.findByTerramozaId(this.terramoza.getIdTerramoza()); 
			FacesUtils.showFacesMessage("Formación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************CAPACITACION****************/
	public void subirCapacitacion(FileUploadEvent event) 
	{  
		
        FacesMessage msg = new FacesMessage("Ok, Capacitacion", event.getFile().getFileName() + " esta cargado.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        this.archivoCapacitacion = event.getFile();
        this.actBtnCapacitacion = Boolean.FALSE;
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
	
	public void guardarCapacitacion()
	{

		try {
			byte[] contenido = this.archivoCapacitacion.getContents();
			this.capacitacion.setImagen(contenido);
			this.capacitacion.setIdTerramoza(this.terramozaService.findByDNI(this.lastDNI).getIdTerramoza());
			this.capacitacion.setRutaImagen("/var/www/capacitacion/Terramoza");
			this.capacitacion.setFechaUltimaActualizacion(new Date());
			this.capacitacion.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.capacitacionService.crearCapacitacion_Terramoza(this.capacitacion);
			this.listaCapacitacion = this.capacitacionService.findByTerramozaId(this.capacitacion.getIdTerramoza());
			this.capacitacion = new Capacitacion();
			this.actBtnCapacitacion = Boolean.TRUE;
			FacesUtils.showFacesMessage("Capacitación registrada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void eliminarCapacitacion(Capacitacion cap) throws IOException{
		try {
			capacitacionService.eliminarCapacitacion(cap.getCapacitacionId());
			this.listaCapacitacion = this.capacitacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			FacesUtils.showFacesMessage("Capacitación eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
/**************INCIDENCIAS****************/
	
	public void guardarIncidencia()
	{

		try {			
			Terramoza t = this.terramozaService.findByDNI(this.lastDNI);
			this.incidencia.setIdTerramoza(t.getIdTerramoza());
			this.incidencia.setFechaUltimaActualizacion(new Date());
			this.incidencia.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.incidenciaServices.crearIncidencia_Terramoza(incidencia); 
			FacesUtils.showFacesMessage("Incidencia registrada correctamente.",Constante.INFORMACION);
			this.listaIncidencias = this.incidenciaServices.findByTerramozaId(this.incidencia.getIdTerramoza());
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
			this.listaIncidencias = this.incidenciaServices.findByTerramozaId(this.terramoza.getIdTerramoza());
			FacesUtils.showFacesMessage("Incidencia eliminada correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************CESES****************/
	
	
	public void guardarCese()
	{

		try {			
			Terramoza t = this.terramozaService.findByDNI(this.lastDNI);
			this.cese.setIdTerramoza(t.getIdTerramoza());
			this.cese.setFechaUltimaActualizacion(new Date());
			this.cese.setUsuarioUltimaActualizacion(this.login.getUsuario().getLogin());
			this.ceseService.crearCese(cese); 
			FacesUtils.showFacesMessage("Cese registrado correctamente.",Constante.INFORMACION);
			this.listaCeses = this.ceseService.listxTerramoza(this.terramoza.getIdTerramoza());
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
			this.listaCeses = this.ceseService.listxTerramoza(this.terramoza.getIdTerramoza());
			FacesUtils.showFacesMessage("Cese eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public SelectItem[] filterEstado(){
		SelectItem[] items ;
			items = new SelectItem[]{new SelectItem("","--Seleccione--"),
									 new SelectItem("Activo"),
					 				 new SelectItem("Inactivo")};
			return items;
	}
	
	
	public void generarPDF(Terramoza t){
		try {
			this.terramoza = t;
			this.listaTerramozaSelect.clear();
			System.out.println(terramoza.getIdTerramoza());
			Ubigeo u = ubigeoService.obtenerUbigeoById(terramoza.getSid_ubigeo());
			if(u!=null){
				this.terramoza.setDepartamento(u.getSdepartamento());
				this.terramoza.setProvincia(u.getSprovincia());
				this.terramoza.setDistrito(u.getSdistrito());
			}			 
			this.listaHistorial = this.historialService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaHistorial(listaHistorial); 
			this.listaDatosF = this.datosFService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaDatosF(listaDatosF); 
			this.listaEvaluacion = this.evaluacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaEvaluacion(listaEvaluacion); 
			this.listaDocumentos = this.documentoService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaDocumentos(listaDocumentos); 
			this.listaFormacion = this.formacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaFormacion(listaFormacion); 
			this.listaCapacitacion = this.capacitacionService.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaCapacitacion(listaCapacitacion); 
			this.listaIncidencias = this.incidenciaServices.findByTerramozaId(this.terramoza.getIdTerramoza());
			this.terramoza.setListaIncidencias(listaIncidencias); 
			this.listaCeses = this.ceseService.listxTerramoza(this.terramoza.getIdTerramoza());
			this.terramoza.setListaCeses(listaCeses); 	
			this.listaTerramozaSelect.add(terramoza);
			this.imprimirPdf(listaTerramozaSelect); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void imprimirPdf(List<Terramoza> listaTerramozaSelect) throws Exception {
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

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaTerramozaSelect);

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

	public String getSdepartamento() {
		return sdepartamento;
	}

	public void setSdepartamento(String sdepartamento) {
		this.sdepartamento = sdepartamento;
	}

	public String getSprovincia() {
		return sprovincia;
	}

	public void setSprovincia(String sprovincia) {
		this.sprovincia = sprovincia;
	}

	public List<Ubigeo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Ubigeo> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
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


	public UploadedFile getArchivoPapeleta() {
		return archivoPapeleta;
	}

	public void setArchivoPapeleta(UploadedFile archivoPapeleta) {
		this.archivoPapeleta = archivoPapeleta;
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

	public Terramoza getTerramoza() {
		return terramoza;
	}

	public void setTerramoza(Terramoza terramoza) {
		this.terramoza = terramoza;
	}

	public List<Terramoza> getListaTerramoza() {
		return listaTerramoza;
	}

	public void setListaTerramoza(List<Terramoza> listaTerramoza) {
		this.listaTerramoza = listaTerramoza;
	}

	public List<Terramoza> getListaFiltroTerramoza() {
		return listaFiltroTerramoza;
	}

	public void setListaFiltroTerramoza(List<Terramoza> listaFiltroTerramoza) {
		this.listaFiltroTerramoza = listaFiltroTerramoza;
	}

	public Boolean getTerramozaGuardado() {
		return terramozaGuardado;
	}

	public void setTerramozaGuardado(Boolean terramozaGuardado) {
		this.terramozaGuardado = terramozaGuardado;
	}

	public TerramozaService getTerramozaService() {
		return terramozaService;
	}

	public void setTerramozaService(TerramozaService terramozaService) {
		this.terramozaService = terramozaService;
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

	public List<Evaluacion> getListaEvaluacion() {
		return listaEvaluacion;
	}

	public void setListaEvaluacion(List<Evaluacion> listaEvaluacion) {
		this.listaEvaluacion = listaEvaluacion;
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

	public Datos_Familiares getDatosF() {
		return datosF;
	}

	public void setDatosF(Datos_Familiares datosF) {
		this.datosF = datosF;
	}

	public List<Incidencia> getListaIncidencias() {
		return listaIncidencias;
	}

	public void setListaIncidencias(List<Incidencia> listaIncidencias) {
		this.listaIncidencias = listaIncidencias;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public SelectItem[] getListEstados() {
		return listEstados;
	}

	public void setListEstados(SelectItem[] listEstados) {
		this.listEstados = listEstados;
	}

	public List<Cese> getListaCeses() {
		return listaCeses;
	}

	public Cese getCese() {
		return cese;
	}

	public CeseService getCeseService() {
		return ceseService;
	}

	public void setListaCeses(List<Cese> listaCeses) {
		this.listaCeses = listaCeses;
	}

	public void setCese(Cese cese) {
		this.cese = cese;
	}

	public void setCeseService(CeseService ceseService) {
		this.ceseService = ceseService;
	}

	public List<Terramoza> getListaTerramozaSelect() {
		return listaTerramozaSelect;
	}

	public void setListaTerramozaSelect(List<Terramoza> listaTerramozaSelect) {
		this.listaTerramozaSelect = listaTerramozaSelect;
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

	public Boolean getActBtnIncidencias() {
		return actBtnIncidencias;
	}

	public void setActBtnIncidencias(Boolean actBtnIncidencias) {
		this.actBtnIncidencias = actBtnIncidencias;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	

}


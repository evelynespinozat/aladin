package com.certicom.ittsa.managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.TipoDocumento;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.TipoDocumentoServices;
import com.certicom.ittsa.services.UsuarioPerfilServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="usuarioMB")
@ViewScoped
//@SessionScoped
public class UsuarioMB extends GenericBeans implements Serializable{
	private String titulo;
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	
	//private static Logger log = Logger.getLogger(UsuarioMB.class.getName());

	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Usuario> usuariosfilter;
	private int id_perfil;
	private Perfil perfil;
	private Integer lengthTdoc;
	private Boolean mostrarBoxC1;
	private Boolean mostrarBoxC2;
	
	private UsuarioServices usuarioServices;
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private PuntoVentaService  puntoVentaService;
	private TipoDocumentoServices tipoDocumentoServices;
	private List<Agencia> listAgencias;
	private List<Oficina> listOficina;
	private List<PuntoVenta> listpventas;
	private List<TipoDocumento> listTipDoc;
	
	
	private int id_centro_atencion;
	private Boolean editar;
	private String lastLogin;
	private Boolean docIdent;
	private Boolean docDefecto;
	private Boolean showPassword;
	
	RequestContext context;
	
	private Log log;
	private LogMB logmb;
	
	// Constructor
	public UsuarioMB() {		
	}

	@PostConstruct
	public void inicia(){ 
		this.editar = Boolean.FALSE;
		this.docIdent = Boolean.TRUE;
		this.docDefecto = Boolean.TRUE;
		this.mostrarBoxC1 = false;
		this.mostrarBoxC2 = false;
		this.showPassword = Boolean.FALSE;
		
		
		usuarioServices = new UsuarioServices();
		menuServices = new MenuServices();
		agenciaService = new AgenciaService();
		oficinaService = new OficinaService();
		puntoVentaService = new PuntoVentaService();
		tipoDocumentoServices = new TipoDocumentoServices();
		lengthTdoc = 8;
		
		
		usuario = new Usuario();
		usuarios = usuarioServices.getlistaUsuario();
		/*for(Usuario u : usuarios){
			try {
				Centro_Atencion c = new Centro_Atencion();
				c = centro_atencionServices.findById(u.getId_centro_atencion());
				u.setNombre_centro_atencion(c.getNombre()); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.context = RequestContext.getCurrentInstance(); 
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:usuarios");
			this.listAgencias = agenciaService.listaAgenciasActivas();
			this.listTipDoc = tipoDocumentoServices.listaTipDocActivos();
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * Desc: metodo que cambia de estado al plumazo
	 * auth:wilber
	 * @param perf
	 */
	
	public void mostrarBox(){
		if(this.usuario.getIdtipodoc() == 1){
			this.mostrarBoxC2 = false;
			this.mostrarBoxC1 = true;
		}else{
			this.mostrarBoxC1 = false;
			this.mostrarBoxC2 = true;
		}
	}
	
	public void cambiarEstado(Usuario user){
		
		if(user.getEstado()){
			user.setEstado(Boolean.FALSE);
		}else{
			user.setEstado(Boolean.TRUE);
		}
		
		try {
			this.usuarioServices.actualizarEstado(user);
			log.setAccion("UPDATE");
            log.setDescripcion("Se actualiza el estado a : " + user.getEstado() + " del usuario: "+this.usuario.getLogin());
            logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de usuario actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("erro actualizar estado usuario"+e.getMessage());	
			e.printStackTrace();
		}
		 
	 }
	

	
	// Para generar los mensajes de validacion esto debe ir en otra clase
	// pero por avanze se hizo aca
	private void addMessage(String key, FacesMessage.Severity severity,
			String message, String detail) {
		FacesMessage msg = new FacesMessage(severity, message, detail);
		FacesContext.getCurrentInstance().addMessage(key, msg);
	}

	public String addErrorMessage() {
		addMessage(null, FacesMessage.SEVERITY_ERROR,
				"Sistema Comercial Ittsa", "Verifique sus Datos");
		return null;
	}

	// Para Cerrar la Session
	public String closeSession() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);
		if (session == null) {
			return "invalid";
		} else {
			session.invalidate();
			return "login";
		}
	}

	public void registrarUsuario() {
		
		try {
			RequestContext context2 = RequestContext.getCurrentInstance();  	
	   	 	usuario.setEstado(true);
	   	 	if(this.editar){
	   	 	    //System.out.println("Entramos a editar");
	   	 		boolean _pase = false, _pasepventa;
	   	 		
	   	 		Usuario usu = this.usuarioServices.getUsuario_byIdUsuario(this.usuario.getIdusuario());
	   	 		if(usu.getDni().equals(this.usuario.getDni())){
		   	 		    _pase = true;
	   	 		}else{
		   	 		int dniRep = this.usuarioServices.verificarDniCarnetRepetidos(this.usuario.getDni());
		   	 		if(dniRep>0){
			   	 		 FacesUtils.showFacesMessage("DNI de usuario se encuentra ya registrado.",Constante.INFORMACION);
			   	 		 context.update("smsNuevoEdit");
			   	 		 _pase = false;
		   	 		}else{
		   	 			_pase = true;
		   	 		}		   	 		
	   	 		}
	   	 		
	   	 		System.out.println("idusuario: "+usu.getIdpuntoventa() + "idPuntoVenta :"+this.usuario.getIdpuntoventa());
	   	 		if(usu.getIdpuntoventa() == this.usuario.getIdpuntoventa()){
		   	 		
	   	 		_pasepventa = true;
	   	 		}else{
		   	 		//int pventaRep = this.usuarioServices.verificarPuntoVentaRepetidos(this.usuario.getIdusuario(), this.usuario.getIdpuntoventa());
	   	 			int pventaRep = this.usuarioServices.verificarPuntoVentaRepetidos(this.usuario.getIdpuntoventa());
	   	 			if(pventaRep>0){
			   	 		 FacesUtils.showFacesMessage("El punto de venta ya se encuentra asignado.",Constante.INFORMACION);
			   	 		 context.update("smsNuevoEdit");
			   	 		 _pasepventa = false;
		   	 		}else{
		   	 			_pasepventa = true;
		   	 		}
	   	 		}
	   	 		
	   	 		if(_pase && _pasepventa){
		   	 		if(!lastLogin.equals(usuario.getLogin())){
		   	 			usuario.setPassword(usuario.getLogin());
		   	 			this.usuarioServices.actualizarLogin(usuario);
		   	 		}
		   	 		this.usuario.setLogin(this.usuario.getDni());
		   	 		this.usuarioServices.actualizar(this.usuario);
		   	 		id_centro_atencion = 0;
			   	 	log.setAccion("UPDATE");
			        log.setDescripcion("Se actualizar el usuario: "+this.usuario.getLogin());
			        logmb.insertarLog(log);
			        this.editar = Boolean.FALSE;
			        this.usuarios = usuarioServices.getlistaUsuario();
			        FacesUtils.showFacesMessage("Usuario actualizado correctamente.",Constante.INFORMACION);
			        context2.execute("dlgNuevo.hide()");
			        context.update("msgGeneral");
			        _pase = false;
	   	 		}
	   	 		
	   	 	}else {
	   	 		//validando el dni no repetido
	   	 		int dniRep = this.usuarioServices.verificarDniCarnetRepetidos(this.usuario.getDni());
	   	 		int pventaRep = this.usuarioServices.verificarPuntoVentaRepetidos(this.usuario.getIdpuntoventa());
	   	 		
	   	 		if(dniRep>0){
		   	 		 FacesUtils.showFacesMessage("DNI de usuario se encuentra ya registrado.",Constante.INFORMACION);
		   	 		 context.update("smsNuevoEdit");
	   	 		}
	   	 		else{
	   	 			//Valida si el punto de venta ya fue asignado
		   	 		/*if(pventaRep>0){
			   	 		 FacesUtils.showFacesMessage("El punto de venta ya se encuentra asignado.",Constante.INFORMACION);
			   	 		 context.update("smsNuevoEdit");
		   	 		}else{*/
		   	 			usuario.setLogin(usuario.getDni());
			   	 		usuario.setPassword(this.usuario.getLogin());
			   	 		usuario.setEs_nuevo(Boolean.TRUE);
			   	 		usuario.setFecha_registro(new Date());
						usuarioServices.insertUsuario(usuario);
						id_centro_atencion = 0;
						log.setAccion("INSERT");
			            log.setDescripcion("Se insertó el usuario: "+ this.usuario.getLogin());
			            logmb.insertarLog(log);
			            FacesUtils.showFacesMessage("Usuario registrado correctamente.",Constante.INFORMACION);
			   	 		this.usuarios = usuarioServices.getlistaUsuario();
			   	 		this.editar = Boolean.FALSE;
				   	 	context2.execute("dlgNuevo.hide()");
				   	 	context.update("msgGeneral");
		   	 		//}
	   	 		}

	   	 	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void eliminarUsuario() {
		try {
			usuarioServices.deleteUsuario(usuario.getIdusuario());
			usuarios = usuarioServices.getlistaUsuario();
			log.setAccion("DELETE");
            log.setDescripcion("Se eliminó el usuario: "+this.usuario.getLogin());
            logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Usuario eliminado correctamente.", Constante.INFORMACION);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Usuario no se puede eliminar tiene perfiles asociados.", Constante.ERROR);
			e.printStackTrace();
		}
		
	}


	public void newInsert() {
		usuario = new Usuario();
		this.editar = Boolean.FALSE;
		this.showPassword = Boolean.FALSE;
		this.listOficina = new ArrayList<Oficina>();
		this.listpventas = new ArrayList<PuntoVenta>();
		this.mostrarBoxC1 =  Boolean.FALSE;
		this.mostrarBoxC2 = Boolean.FALSE;
		this.titulo = "Registrar nuevo usuario";
	}

	public void newUpdate(Integer idusuario) {
		this.mostrarBoxC1 =  Boolean.FALSE;
		this.mostrarBoxC2 = Boolean.FALSE;
		this.editar = Boolean.TRUE;
		this.showPassword = Boolean.TRUE;
		this.titulo = "Modificar usuario";
		
		this.listOficina = new ArrayList<Oficina>();
		this.listpventas = new ArrayList<PuntoVenta>();
		this.usuario = usuarioServices.buscarPorId(idusuario);
		switch (this.usuario.getIdtipodoc()) {
		case 1:
			this.mostrarBoxC1 =  Boolean.TRUE;
			break;
		case 2:
			this.mostrarBoxC2 = Boolean.TRUE;
			break;
		}
		try {
			this.listOficina = oficinaService.getOficinasxAgencia(this.usuario.getIdagencia());
			this.listpventas = puntoVentaService.getPventasxOficina(this.usuario.getIdoficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lastLogin = usuario.getLogin();
	}

	public void newDelete(Usuario user) {
		this.usuario = user;
	}

	/****************************/
	

	public String agregarPerfil(Usuario user) {
		String outcome=null;
		//log.info("entrando a agregar perfil");
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuario", user);
		System.out.println("enviando cadena de redireccion");
		outcome="pretty:addPerfilUsuario";
		return outcome;
		//return "/faces/control_acceso/addPerfilUsuario?faces-redirect=true";
			
	}
	
	
	public void getOficinasxAgencia(){
		this.listpventas = new ArrayList<PuntoVenta>();
		try {
			this.listOficina = oficinaService.getOficinasxAgencia(this.usuario.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getPventasxOficina(){
		try {
			this.listpventas = puntoVentaService.getPventasxOficina(this.usuario.getIdoficina());
		} catch (Exception e) {
		}
	}
	
	public void setLengthtdoc() {
		if (this.usuario.getIdtipodoc() != 0) {
			this.docIdent = Boolean.FALSE;
			this.docDefecto = Boolean.FALSE;
			System.out.println("entro");
			for (int i = 0; i < this.listTipDoc.size(); i++) {
				if (this.usuario.getIdtipodoc() == listTipDoc.get(i).getIdtipodoc()) {
					System.out.println("entro");
					this.lengthTdoc = listTipDoc.get(i).getLongitud();
					break;
				}

			}
		}
	}
	
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String numero = (String)arg2;
		if(lengthTdoc==8){
			try{
				   Integer num = Integer.parseInt(numero);
				   System.out.println(num);
			   }catch(Exception e){
				   throw new ValidatorException(new FacesMessage("Ingrese un numero de DNI correcto"));
			}
			if (numero.length()!=8) {
		         throw new ValidatorException(new FacesMessage("El DNI debe contener 8 numeros "));
		    }
		}else{
			if (numero.length()<9) {
		         throw new ValidatorException(new FacesMessage("Ingrese el carnet de extranjeria correctamente"));
			}	      
	   }
		
	   
	
	}
	
	
	
	// Getter and Setter
	
		public Boolean getMostrarBoxC1() {
			return mostrarBoxC1;
		}
	
		public void setMostrarBoxC1(Boolean mostrarBoxC1) {
			this.mostrarBoxC1 = mostrarBoxC1;
		}
	
		public Boolean getMostrarBoxC2() {
			return mostrarBoxC2;
		}
	
		public void setMostrarBoxC2(Boolean mostrarBoxC2) {
			this.mostrarBoxC2 = mostrarBoxC2;
		}
	
		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public UsuarioServices getUsuarioServices() {
			return usuarioServices;
		}

		public void setUsuarioServices(UsuarioServices usuarioServices) {
			this.usuarioServices = usuarioServices;
		}

		public void setUsuarios(List<Usuario> usuarios) {
			this.usuarios = usuarios;
		}

		public List<Usuario> getUsuarios() {
			return usuarios;
		}
		
		public Perfil getPerfil() {
			return perfil;
		}

		public void setPerfil(Perfil perfil) {
			this.perfil = perfil;
		}

		

		public int getId_perfil() {
			return id_perfil;
		}

		public void setId_perfil(int id_perfil) {
			this.id_perfil = id_perfil;
		}

		
		public void setLogin(LoginMB login) {
			this.login = login;
		}

		public LoginMB getLogin() {
			return login;
		}

		public Boolean getEditar() {
			return editar;
		}

		public void setEditar(Boolean editar) {
			this.editar = editar;
		}

		public int getId_centro_atencion() {
			return id_centro_atencion;
		}

		public void setId_centro_atencion(int id_centro_atencion) {
			this.id_centro_atencion = id_centro_atencion;
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

		public String getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(String lastLogin) {
			this.lastLogin = lastLogin;
		}

		public AgenciaService getAgenciaService() {
			return agenciaService;
		}

		public void setAgenciaService(AgenciaService agenciaService) {
			this.agenciaService = agenciaService;
		}

		public OficinaService getOficinaService() {
			return oficinaService;
		}

		public void setOficinaService(OficinaService oficinaService) {
			this.oficinaService = oficinaService;
		}

		public List<Agencia> getListAgencias() {
			return listAgencias;
		}

		public void setListAgencias(List<Agencia> listAgencias) {
			this.listAgencias = listAgencias;
		}

		public List<Oficina> getListOficina() {
			return listOficina;
		}

		public void setListOficina(List<Oficina> listOficina) {
			this.listOficina = listOficina;
		}

		public List<PuntoVenta> getListpventas() {
			return listpventas;
		}

		public void setListpventas(List<PuntoVenta> listpventas) {
			this.listpventas = listpventas;
		}


		public PuntoVentaService getPuntoVentaService() {
			return puntoVentaService;
		}

		public void setPuntoVentaService(PuntoVentaService puntoVentaService) {
			this.puntoVentaService = puntoVentaService;
		}

		public TipoDocumentoServices getTipoDocumentoServices() {
			return tipoDocumentoServices;
		}

		public void setTipoDocumentoServices(TipoDocumentoServices tipoDocumentoServices) {
			this.tipoDocumentoServices = tipoDocumentoServices;
		}

		public List<TipoDocumento> getListTipDoc() {
			return listTipDoc;
		}

		public void setListTipDoc(List<TipoDocumento> listTipDoc) {
			this.listTipDoc = listTipDoc;
		}

		public List<Usuario> getUsuariosfilter() {
			return usuariosfilter;
		}

		public void setUsuariosfilter(List<Usuario> usuariosfilter) {
			this.usuariosfilter = usuariosfilter;
		}

		public Integer getLengthTdoc() {
			return lengthTdoc;
		}

		public void setLengthTdoc(Integer lengthTdoc) {
			this.lengthTdoc = lengthTdoc;
		}

		public Boolean getDocIdent() {
			return docIdent;
		}

		public void setDocIdent(Boolean docIdent) {
			this.docIdent = docIdent;
		}

		public Boolean getDocDefecto() {
			return docDefecto;
		}

		public void setDocDefecto(Boolean docDefecto) {
			this.docDefecto = docDefecto;
		}

		public Boolean getShowPassword() {
			return showPassword;
		}

		public void setShowPassword(Boolean showPassword) {
			this.showPassword = showPassword;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	
	/*
	
	public void insertarPerfil(){
		
		Boolean valido=Boolean.FALSE;
		RequestContext context = RequestContext.getCurrentInstance();
		if(this.id_perfil==0){ //validando que seleccion dferente a "seleccione"
			addMessage(null, FacesMessage.SEVERITY_WARN,
					"Seleccione", "Seleccione un perfil valido");
			//System.out.println("seleccione un perfil valido");
			context.addCallbackParam("esValido", valido );
		}else{
			//System.out.println("verificando que no este repetido");
			try {
				this.usuarioPerfil= this.usuarioPerfilServices.buscarPerfilUsuario(this.usuario.getIdusuario(), this.id_perfil);
				if(this.usuarioPerfil==null){
					valido=Boolean.TRUE;
				}else{
					addMessage(null, FacesMessage.SEVERITY_WARN,"Perfil ya existe", "Seleccione un perfil válido");
				}
			} catch (Exception e) {
				e.printStackTrace();
			  }

			context.addCallbackParam("esValido", valido );
			
			if(valido){
				//System.out.println("Ahora si grabamos");
				try {
					this.perfil=this.perfilServices.findPerfilPorCodigo(this.id_perfil);
					UsuarioPerfil usrPerfil = new UsuarioPerfil();
					usrPerfil.setCod_perfil(this.id_perfil);
					usrPerfil.setIdusuario(this.usuario.getIdusuario());
					usrPerfil.setFecha_finvig(new Date());
					usrPerfil.setFecha_iniciovig(new Date());
					usrPerfil.setFecha_modif(new Date());
					usrPerfil.setUsuario_modif(this.login.getLogin());
					usrPerfil.setUsuario_registro(this.login.getLogin());
					usrPerfil.setFecha_registro(new Date());
					usrPerfil.setInd_activo(Boolean.TRUE);
					this.usuarioPerfilServices.insertUsuarioPeril(usrPerfil);
					this.listaPerfilUsuario=this.usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario());
				} catch (Exception e) {
					System.out.println("Error :"+e.getMessage());
					e.printStackTrace();
				}
				addMessage(null, FacesMessage.SEVERITY_INFO,
						"Guardado", "Perfil guardado correctamente");
				RequestContext.getCurrentInstance().update("grSms");
			}
		}
		
	}
	
	
	public void eliminarPerfil(){
		System.out.println("eliminar perfil: "+this.usuarioPerfil.getCod_perfil());
		try{
			this.usuarioPerfilServices.eliminarPerfilUsuario(this.usuario.getIdusuario(),this.usuarioPerfil.getCod_perfil());
			PerfilUsuario = this.usuarioPerfilServices.listarPerfilesPorUsuario(this.usuario.getIdusuario());
			addMessage(null, FacesMessage.SEVERITY_INFO,
					"Eliminacion", "Perfil eliminado");
			RequestContext.getCurrentInstance().update("grSms");
		}catch(Exception e){
			System.out.println("Error :"+e.getMessage());
		}
		
	}
*/
		
		
	

}
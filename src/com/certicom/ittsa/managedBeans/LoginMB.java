package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.log4j.Level;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.panelmenu.PanelMenu;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.extensions.model.layout.LayoutOptions;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.primefaces.model.StreamedContent;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AsientoPasajero;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.PerfilServices;
import com.certicom.ittsa.services.PuntoVentaService;
import com.certicom.ittsa.services.SistemaServices;
import com.certicom.ittsa.services.UsuarioPerfilServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB  extends GenericBeans implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private String login;
	private String password;
	private List<Perfil> listaPerfiles;
	private List<Sistema> listaModulos;
	private List<Menu> listaMenu;
	private MenuModel model;
	private MenuModel modelSalir;
	private String fechaAcceso;
	private String centroAtencion;
	private String nuevoPass;
	private String nuevoPassRe;
	private int tiempo_cambio_password;
	private int idUsuario;
	private String loginUsuario;
	private StreamedContent archivoFoto;
	private Piloto piloto;
	private Terramoza terramoza;
	private String desOficina;
	private String desTipo;
	private String tipLiquidacion;
	private Agencia agencia;
	public PuntoVenta pv;
	private Postergacion postergacion;
	private AsientoPasajero asientoPasajero;
	
	UsuarioServices usuarioServices;
	ParametroServices parametroServices;
	PerfilServices perfilServices;
	SistemaServices	sistemaServices;
	MenuServices menuServices;
	PuntoVentaService puntoVentaService;
	AgenciaService agenciaService;
	UsuarioPerfilServices usuarioPerfilServices;
	
	private Boolean impresoraAsociada;
	
	/*
	private String serieBoleto;
	private String numeroBoleto;
	/*
	private String serieBoletoInhabilitado;
	private String numeroBoletoInhabilitado;
	private Persona personaNueva;
	private Boolean cambioServicioPasajero;
	*/
	
	private Log log;
	private LogMB logmb;

	public LoginMB(){
		
	}

	@PostConstruct
	public void inicia()
	{
		usuario = new Usuario();
		usuarioServices = new UsuarioServices();
		perfilServices = new PerfilServices();
		sistemaServices=new SistemaServices();
		menuServices=new MenuServices();
		parametroServices = new ParametroServices();
		puntoVentaService = new PuntoVentaService();
		agenciaService = new AgenciaService();
		usuarioPerfilServices = new UsuarioPerfilServices();
		
		this.impresoraAsociada = Boolean.FALSE;
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, dias);
		return cal.getTime();
	}
	

	
	public String efectuarLogin() {
		String retorno = null;
		log = new Log();
		logmb = new LogMB();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getHeader("Proxy-Client-IP");     }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getHeader("WL-Proxy-Client-IP");  }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getHeader("HTTP_CLIENT_IP");      }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getHeader("HTTP_X_FORWARDED_FOR");}  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getRemoteAddr();                  }   

		//System.out.println("Host Cliente: " + hostClient);
		//getMacAddres(ipClient);

		try {
				String valor = parametroServices.findParametro_byNombre("TIEMPO_CAMBIO_PASSWORD");
				tiempo_cambio_password = Integer.parseInt(valor);			

				Usuario user = usuarioServices.buscarPorLogin(login);
				//String localHost= String.valueOf(InetAddress.getLocalHost().getHostAddress());
				getMacAddres(ip);
				/**Para Log**/
				log.setIdusuario(user.getIdusuario());
				log.setCod_menu(0);
				log.setAccion("LOGIN");
				log.setIp_client(ip);
				
				setBean(log);
				Log logEnSesion = (Log)getSpringBean(Constante.SESSION_LOG);
				PropertyUtils.copyProperties(logEnSesion, log);
				
				listaPerfiles = new ArrayList<Perfil>();
				listaModulos=new ArrayList<Sistema>();	
				listaMenu=new ArrayList<Menu>();
				
				// Validar si el usuario contiene perfiles sino no dejar ingresar
				
				List<UsuarioPerfil> listaUsuarioPerfil = usuarioPerfilServices.listarPerfilesPorUsuario(user.getIdusuario());
				if(listaUsuarioPerfil.isEmpty()){
					FacesUtils.showFacesMessage("El usuario no tiene asignado ningun perfil.",Constante.ERROR);
					log.setDescripcion("Usuario "+ login + " no pudo logearse");
					logmb.insertarLog(log);
				}
				else{
					List<Usuario> list = usuarioServices.buscarPorLoginClave(new Usuario(login, password));
					if (list.size() == 0) {
						FacesUtils.showFacesMessage("Usuario y contraseña incorrecta.",Constante.ERROR);
						log.setDescripcion("Usuario "+ login + " no pudo logearse");
						logmb.insertarLog(log);
					} else {						
						if (list.get(0).getEstado()) {
							usuario = (list.get(0));
							/******************************************************************************/
							setBean(usuario);
							idUsuario = usuario.getIdusuario();
							
							loginUsuario = usuario.getLogin();
							System.out.println("El id del usuario logeado es:" + idUsuario);
							System.out.println("El nomnbre de usuario logeado es:" + loginUsuario);
							System.out.println("es nuevo?:"+this.usuario.getEs_nuevo());
							Usuario usuarioEnSesion = (Usuario)getSpringBean(Constante.SESSION_USER);
							PropertyUtils.copyProperties(usuarioEnSesion, usuario);
							/******************************************************************************/
							
//							Centro_Atencion c = new Centro_Atencion();
//							c = centro_atencionServices.findById(usuario.getId_centro_atencion());
//							centroAtencion = c.getNombre();
							
			/** retirando centro de atencion*/		
							//PuntoVenta pv = this.puntoVentaService.findById(usuario.getIdpuntoventa());
							
							this.pv = this.puntoVentaService.obtenerPuntoVenta(usuario.getIdpuntoventa());
							this.agencia = this.agenciaService.findById(pv.getIdAgencia());
							centroAtencion = pv.getNamePuntoVenta();
							desOficina = pv.getDesOficina();
							desTipo = pv.getDesTipo();
							FacesUtils.setUsuarioLogueado(usuario);

							if(pv.getNombreImpresora() == null || pv.getNombreImpresora().isEmpty())
							{
								this.impresoraAsociada = Boolean.FALSE;
								
							}else if(pv.getNumeroIP() == null || pv.getNumeroIP().isEmpty()){
								
								this.impresoraAsociada = Boolean.FALSE;
								
							}else{
								this.impresoraAsociada = Boolean.TRUE;
							}
							
							System.out.println("fecha ultimo acceso" + this.usuario.getFecha_acceso());
							if(this.usuario.getFecha_acceso()!=null){
								String date =  sumaDias(this.usuario.getFecha_acceso(),tiempo_cambio_password).toString().substring(0, 10);
								String fechaActual = new Date().toString().substring(0, 10); 
								if(fechaActual.equals(date)){
									this.usuario.setEs_nuevo(Boolean.TRUE); 
									System.out.println("Es la misma fecha");
								}							
								//actualizar fecha de ultimo acceso
								DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
								this.fechaAcceso = df.format(usuario.getFecha_acceso());
								usuario.setFecha_acceso(new Date());
								usuarioServices.actualizarFechaAcceso(usuario);

							}else{
								usuario.setFecha_acceso(new Date());
								usuarioServices.actualizarFechaAcceso(usuario);
							}
							
							// Listar los perfiles del usuario
							listaPerfiles = perfilServices.listarPerfilesxUsuario(usuario);
							
							tipLiquidacion = getListaPerfiles().get(0).getLiquidacion();
							
							
							
							//Listar los modulos que tiene el usuario
							if(listaPerfiles.size()>0)
							{	
								
								/*inicio modificacion*/
								//gerando menu

							    this.generaMenu();
							   /*fin mdoficiacion*/ 
							    
								log.setDescripcion("Usuario "+ login + " Logeado correctamente");
								System.out.println("---------Log:-------- \n Usuario:" + log.getIdusuario()+"\n Ip: "+log.getIp_client());
								logmb.insertarLog(log);
							    
							    setModel(model);
								retorno = "pretty:principal";
			
							}else{
								FacesUtils.showFacesMessage("Usuario no cuenta ningun perfil asociado.",Constante.ERROR);
								return retorno;
								}	
							
							
						} else {
							System.out.println("usuario bloqueado");
							FacesUtils.showFacesMessage("Usuario bloqueado.",Constante.ERROR);
						}
					}
				}
				

			return retorno;			
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Ocurrió un problema de conexión, inténtelo mas tarde.",Constante.ERROR);
			e.printStackTrace();
			return retorno;
		}

	}
	
	//generando el menu
	
	public void generaMenu() throws Exception
	{
		List<Sistema> lista=new ArrayList<Sistema>();
		List<Menu> lista2=new ArrayList<Menu>();
		
		
		for(int k=0;k<listaPerfiles.size();k++)
		{
			lista=sistemaServices.listSistemaxPerfil(getListaPerfiles().get(k));
			lista2=menuServices.listMenuxSistema(getListaPerfiles().get(k));
			
			//Añadir cada modulo
				//listaModulos
			if(lista.size()>0){
				if(listaModulos.size()>0){
					//Comparar que no se repitan los modulos
					for(int p=0;p<lista.size();p++){
						for(int o=0;o<listaModulos.size();o++){											
							if(getListaModulos().get(o).getCod_sistema()==lista.get(p).getCod_sistema()){
								break;
							}else{
								if(o==(listaModulos.size()-1))listaModulos.add(lista.get(p));else continue;												
							}
						}										
					}
				}else{
					for(int m=0;m<lista.size();m++){
						listaModulos.add(lista.get(m));										
					}
				}
			}
											
			if(lista2.size()>0){
				if(listaMenu.size()>0){
					//Comparar que no se repitan los modulos
					for(int p=0;p<lista2.size();p++){
						for(int o=0;o<listaMenu.size();o++){											
							if(getListaMenu().get(o).getCod_menu()==lista2.get(p).getCod_menu()){
								break;
							}else{
								if(o==(listaMenu.size()-1))listaMenu.add(lista2.get(p));else continue;												
							}
						}										
					}
				}else {
					for(int m=0;m<lista2.size();m++){
						listaMenu.add(lista2.get(m));										
					}
				}
			}									
		}
		
		model = new DefaultMenuModel();
		Submenu submenu=new Submenu();
		MenuItem item = new MenuItem();
		List<Menu> listaSubMenu=new ArrayList();
		listaSubMenu=listaMenu;
		MethodExpression methodExpression;
		for(int k=0;k<listaModulos.size();k++)
		{
			
			Submenu menu=new Submenu();
			menu.setLabel(listaModulos.get(k).getNombre_sistema());
			
			for(int m=0;m<listaMenu.size();m++)
			{
				if(listaMenu.get(m).getCod_sistema()==listaModulos.get(k).getCod_sistema())
				{
					boolean genSubmenu=false,gen=false;
					submenu=new Submenu();
					
					for(int p=0;p<listaSubMenu.size();p++)
					{										
							if(listaSubMenu.get(p).getCod_menu_padre()==listaMenu.get(m).getCod_menu())
							{
								Menu men=new Menu();												
								MenuItem mi=new MenuItem();
								mi.setValue(listaSubMenu.get(p).getNombre());
								mi.setOutcome(listaSubMenu.get(p).getAction());
								mi.setIcon(listaSubMenu.get(p).getIcono());
								men=listaMenu.get(p);
								men.setEst(true);
								listaMenu.set(p, men);
								
								men=new Menu();
								men=listaMenu.get(m);
								men.setEst(true);
								listaMenu.set(m, men);
								genSubmenu=true;
								gen=true;
								submenu.getChildren().add(mi);
							}
						
							if(p==(listaSubMenu.size()-1))
							{
								if(gen)
								{												
										if(genSubmenu){										
											submenu.setLabel(listaMenu.get(m).getNombre());															
											submenu.setIcon(listaMenu.get(m).getIcono());
											menu.getChildren().add(submenu);
										}else{
											item = new MenuItem();
											item.setValue(listaMenu.get(m).getNombre());
											item.setOutcome(listaMenu.get(m).getAction());
											item.setIcon(listaMenu.get(m).getIcono());
											menu.getChildren().add(item);										
										}
									genSubmenu=false;
									gen=false;
								}else if(listaMenu.get(m).isEst()==false){
									item = new MenuItem();
									item.setValue(listaMenu.get(m).getNombre());
									item.setOutcome(listaMenu.get(m).getAction());
									item.setIcon(listaMenu.get(m).getIcono());
									menu.getChildren().add(item);		
								}
							}
					}								
				}						
			}
			model.addSubmenu(menu);
		}
		
		Submenu menu=new Submenu();
		menu.setLabel("Opciones");
		item = new MenuItem();
		item.setValue("Cambiar Password");item.setIcon("icon-password");										
		methodExpression = generarActionMenu("#{loginMB.iniciarCambioPassword_Voluntario}");
		item.setActionExpression(methodExpression);
	    menu.getChildren().add(item);
	    model.addSubmenu(menu);
		
		//Añadir el log off
		
		item = new MenuItem();
		item.setValue("Salir");item.setIcon("icon-exit");										
		methodExpression = generarActionMenu("#{loginMB.efectuarLogoff}");
		item.setActionExpression(methodExpression);
	    menu.getChildren().add(item);
	    model.addSubmenu(menu);

	    //Añadir Home
	    /*
		item = new MenuItem();
		item.setValue("Principal");item.setIcon("icon-home");item.setUrl("/");	
		menu.getChildren().add(item);
	    model.addSubmenu(menu);					  
	 	*/
	}
	
	
	
	public void getMacAddres(String ip){
		try {
            InetAddress address = InetAddress.getByName("10.162.170.103");

            NetworkInterface ni2 = 
                    NetworkInterface.getByName("eth0");
            
            NetworkInterface ni =
                    NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        System.out.format("Mac: %02X%s",
                                mac[i], (i < mac.length - 1) ? "-" : "");
                    }
                } else {
                    System.out.println("Address doesn't exist or is not " +
                            "accessible.");
                }
            } else {
                System.out.println("Network Interface for the specified " +
                        "address is not found.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
	}


	public MethodExpression generarActionMenu(String action){
		ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		return factory.createMethodExpression(FacesContext.getCurrentInstance().getELContext(),action, String.class, new Class[]{});
	}
	

	public String efectuarLogoff() {
		
		FacesUtils.removeUsuarioLogueado();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		session.invalidate();
		return "pretty:login";
	}
		 
	public String iniciaCambiarPassword(){
		String outcome=null;
		this.usuario.setEs_nuevo(Boolean.TRUE);
		this.usuarioServices.actualizar(this.usuario);
		outcome = "pretty:principal";
		return outcome;
	}	
	
	
	public String iniciarCambioPassword_Voluntario(){
		return "pretty:cambioPass";
	}
	
	
	public void cambiarPassword_Voluntario(){
		//String outcome = null;
		//comparando y cambiando
		if(this.nuevoPass.equals(this.nuevoPassRe)){ //ok
			if(nuevoPass.length()>=6){
				this.usuario.setPassword(this.nuevoPass);
				this.usuario.setFecha_acceso(new Date());
				this.usuario.setEs_nuevo(Boolean.FALSE);
				this.usuario.setFecha_cambio_password(new Date()); 
				this.usuarioServices.actualizarPassword(this.usuario);
				this.usuarioServices.actualizarFechaAcceso(this.usuario); 
				//Insertar en la tabla de Log
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se cambio contrasena: " + usuario.getNombre_completo());
	             logmb.insertarLog(log);
				
				nuevoPass ="";
				nuevoPassRe ="";
				//matar sesion:			
				//outcome="pretty:cambioPass";		
				//this.efectuarLogoff();
				
				FacesUtils.showFacesMessage("La contraseña se ha cambiado correctamente.", Constante.INFORMACION);
			}else{
				FacesUtils.showFacesMessage("Por seguridad, la contraseña debe tener al menos 6 caracteres.", Constante.ADVERTENCIA);				
			}
			
		}else{
			//mostrar alerta
			FacesUtils.showFacesMessage("Contraseñas no coinciden.", Constante.ADVERTENCIA);
		}		
		//return outcome;
	}
	

	/**
	 * Desc: cambia password
	 */
	public String cambiarPassword(){
		String outcome = null;
		//comparando y cambiando
		if(this.nuevoPass.equals(this.nuevoPassRe)){ //ok
			this.usuario.setPassword(this.nuevoPass);
			this.usuario.setFecha_acceso(new Date());
			this.usuario.setEs_nuevo(Boolean.FALSE);
			this.usuario.setFecha_cambio_password(new Date());
			
			this.usuarioServices.actualizarPassword(this.usuario);
			this.usuarioServices.actualizarFechaAcceso(this.usuario); 
			
			log.setAccion("UPDATE");
             log.setDescripcion("Se cambio contrasena: " + usuario.getNombre_completo());
             logmb.insertarLog(log);
			//matar sesion:
			
			outcome="pretty:login";		
			this.efectuarLogoff();		
		}else{
			//mostrar alerta
			FacesUtils.showFacesMessage("Contraseñas no coinciden.", Constante.ADVERTENCIA);
		}		
		return outcome;
	}
	
	/*###########-------setters y getters-------##################*/
	
	public Usuario getUsuario() {
		return usuario;
	}

	public String getDesTipo() {
		return desTipo;
	}

	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}

	public String getDesOficina() {
		return desOficina;
	}

	public void setDesOficina(String desOficina) {
		this.desOficina = desOficina;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Perfil> getListaPerfiles() {
		return listaPerfiles;
	}

	public void setListaPerfiles(List<Perfil> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}
	
	public List<Sistema> getListaModulos() {
		return listaModulos;
	}

	public void setListaModulos(List<Sistema> listaModulos) {
		this.listaModulos = listaModulos;
	}	
	
	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}	

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public String getFechaAcceso() {
		return fechaAcceso;
		
	}

	public void setFechaAcceso(String fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}

	public MenuModel getModelSalir() {
		return modelSalir;
	}

	public void setModelSalir(MenuModel modelSalir) {
		this.modelSalir = modelSalir;
	}
	
	public String getNuevoPass() {
		return nuevoPass;
	}

	public String getNuevoPassRe() {
		return nuevoPassRe;
	}

	public void setNuevoPass(String nuevoPass) {
		this.nuevoPass = nuevoPass;
	}

	public void setNuevoPassRe(String nuevoPassRe) {
		this.nuevoPassRe = nuevoPassRe;
	}

	public int getTiempo_cambio_password() {
		return tiempo_cambio_password;
	}

	public void setTiempo_cambio_password(int tiempo_cambio_password) {
		this.tiempo_cambio_password = tiempo_cambio_password;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getCentroAtencion() {
		return centroAtencion;
	}

	public void setCentroAtencion(String centroAtencion) {
		this.centroAtencion = centroAtencion;
	}
	
	public StreamedContent getArchivoFoto() {
		return archivoFoto;
	}

	public void setArchivoFoto(StreamedContent archivoFoto) {
		this.archivoFoto = archivoFoto;
	}

	/**
	 * @return the piloto
	 */
	public Piloto getPiloto() {
		return piloto;
	}

	/**
	 * @param piloto the piloto to set
	 */
	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public Terramoza getTerramoza() {
		return terramoza;
	}

	public void setTerramoza(Terramoza terramoza) {
		this.terramoza = terramoza;
	}

	public String getTipLiquidacion() {
		return tipLiquidacion;
	}

	public void setTipLiquidacion(String tipLiquidacion) {
		this.tipLiquidacion = tipLiquidacion;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public PuntoVenta getPv() {
		return pv;
	}

	public void setPv(PuntoVenta pv) {
		this.pv = pv;
	}

	public Postergacion getPostergacion() {
		return postergacion;
	}

	public void setPostergacion(Postergacion postergacion) {
		this.postergacion = postergacion;
	}

	public AsientoPasajero getAsientoPasajero() {
		return asientoPasajero;
	}

	public void setAsientoPasajero(AsientoPasajero asientoPasajero) {
		this.asientoPasajero = asientoPasajero;
	}

	public Boolean getImpresoraAsociada() {
		return impresoraAsociada;
	}

	public void setImpresoraAsociada(Boolean impresoraAsociada) {
		this.impresoraAsociada = impresoraAsociada;
	}



}
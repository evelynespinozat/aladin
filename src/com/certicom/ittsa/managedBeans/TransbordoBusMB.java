package com.certicom.ittsa.managedBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.primefaces.context.RequestContext;

import antlr.Utils;

import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.mapper.AsientoVentaMapper;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TipoAsientoService;
import com.pe.certicom.ittsa.commons.FacesUtils;

@ManagedBean(name="trasnbordoBusMB")
@ViewScoped
public class TransbordoBusMB {

	private Programacion programacion;
	private Programacion programacionSelec;
	
	
	private Integer nroFilas;
	
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private List<Asiento> listaAsientosPisoUno;
	private List<Asiento> listaAsientosPisoDos;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionDemanda;
	private AsientoVenta asientoVentaSelec;
	private AsientoVenta asientoVentaDestino;
	private List<AsientoVenta> listaAsientosVentaLibres;
	private Integer piso;
	
	
	private Integer nroColumnasPisoUnoTarget;
	private Integer nroColumnasPisoDosTarget;
	private List<Asiento> listaAsientosPisoUnoTarget;
	private List<Asiento> listaAsientosPisoDosTarget;
	
	private Servicio servicioTarget;
	private BigDecimal montoTotal;
	private AsientoVenta asientoVenta;
	private AsientoServices asientoService;
	private AsientoVentaServices asientoVentaService;
	private ClaseServicioServices claseServicioServices;
	private ServicioService servicioService;
	private TipoAsientoService tipoAsientoService;
	
	private Boolean trasnbordoRealizado;
	
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	public void setLogin(LoginMB login) {
		this.login = login;
	}
	
	
	
	
	public TransbordoBusMB()
	{
		;
	}
	
	@PostConstruct
	public void inicia()
	{
		asientoService = new AsientoServices();
		asientoVentaService = new AsientoVentaServices();
		claseServicioServices = new ClaseServicioServices(); 
		servicioService = new ServicioService();
		tipoAsientoService = new TipoAsientoService();
		servicioTarget = new Servicio();
		this.listaAsientosVentaLibres = new ArrayList<>();
		
		this.trasnbordoRealizado =Boolean.FALSE;
		
		this.nroFilas = 5;
		//consutando ademanda
		this.listaProgramacionDemanda = new ArrayList<>();
		
		this.asientoVentaDestino = new AsientoVenta();
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.programacion = (Programacion) flash.get("programacion");
		this.listaProgramacion = (List<Programacion>) flash.get("listaProgramacion");

		//System.out.println("el idprogramacion pasado es " +this.programacion.getIdProgramacion());
		this.pintarBus(this.programacion);
	}
	
	
	
	public void pintarBus(Programacion pr) 
	{

		
		
		if (pr.getServicio() != null) 
		{

			try{
				
				pr.setCapacidadVerdadera(this.asientoVentaService.countCapacidadVerdadera(pr.getIdProgramacion(), Boolean.TRUE));
				
				//consultar configuracion para pintar el bus
				pr.setClase(this.claseServicioServices.findById(pr.getServicio().getIdClase()));
				
				this.nroColumnasPisoUno = pr.getClase().getNroColumnasPisoUno();
			
				// generando asientos para el piso UNO ida siempre
				this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(pr.getClase().getIdclase(), new Integer(1));
				AsientoVenta asv = null;
	
				for (Asiento as : this.listaAsientosPisoUno) 
				{
					asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), pr.getIdProgramacion());
					as.setAsientoVenta(asv);
				}
	
				// generando conf inicial para el piso DOS ida
				if(pr.getClase().getNroPisos().compareTo(new Integer(2)) == 0) 
				{
					this.nroColumnasPisoDos = pr.getClase().getNroColumnasPisoDos();
					this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(pr.getClase().getIdclase(), new Integer(2));
					for (Asiento as : this.listaAsientosPisoDos) 
					{
						asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), pr.getIdProgramacion());
						as.setAsientoVenta(asv);
	
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}

		}

	}

	
	public void seleccionarAsiento(AsientoVenta asv)
	{
		this.asientoVentaSelec = asv;
		this.piso = null;
		this.listaAsientosVentaLibres.clear();
		
		if(!asv.getEstado().equals("LIBRE"))
		{
			RequestContext.getCurrentInstance().update("dlgDetManual");
			RequestContext.getCurrentInstance().execute("dlgManual.show();");
		}

	}
	
	public void seleccionar()
	{
		//listando asientos libres
		System.out.println("el idprogramacion de tabla  es " +this.programacionSelec.getIdProgramacion());
		this.pintarBusTarget(this.programacionSelec);
	}

	public void listarAsientosDisponibles()
	{
		try {
			
			//this.listaAsientosVentaLibres = this.asientoVentaService.listarTotalAsientosDisponibles(this.piso,this.programacionSelec.getIdProgramacion());

			this.listaAsientosVentaLibres.clear();
			
			if(this.piso != null)
			{
				if(this.piso.intValue() == 1)
				{
					for(Asiento as : this.listaAsientosPisoUnoTarget)
					{

						if(as.getAsientoVenta() != null && as.getAsientoVenta().getEstado().equals("LIBRE"))
						{
							this.listaAsientosVentaLibres.add(as.getAsientoVenta());
						}

					}
				}else{
					for(Asiento as : this.listaAsientosPisoDosTarget)
					{

						if(as.getAsientoVenta() != null && as.getAsientoVenta().getEstado().equals("LIBRE"))
						{
							this.listaAsientosVentaLibres.add(as.getAsientoVenta());
						}
						
					}
				}
			}
			System.out.println("haciendo :"+this.listaAsientosVentaLibres.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void hacerTransbordoIndividual()
	{
		AsientoVenta asienVent = null;
		asienVent = new AsientoVenta();
		asienVent.setMonto(this.asientoVentaSelec.getMonto());
		asienVent.setEstado(this.asientoVentaSelec.getEstado());
		asienVent.setTipoventa(this.asientoVentaSelec.getTipoventa());
		asienVent.setFechaventa(this.asientoVentaSelec.getFechaventa());
		asienVent.setDocumentoPersona(this.asientoVentaSelec.getDocumentoPersona());
		asienVent.setDocumentoEmpresa(this.asientoVentaSelec.getDocumentoEmpresa());
		asienVent.setIdPostergado(this.asientoVentaSelec.getIdPostergado());
		asienVent.setObservacion(this.asientoVentaSelec.getObservacion());
		asienVent.setExterno(this.asientoVentaSelec.getExterno());
		asienVent.setOficinaId(this.asientoVentaSelec.getOficinaId());
		asienVent.setPrepagado(this.asientoVentaSelec.getPrepagado());
		asienVent.setTipdocprepagado(this.asientoVentaSelec.getTipdocprepagado());
		asienVent.setNrodocprepagado(this.asientoVentaSelec.getNrodocprepagado());
		asienVent.setTipdocdelivery(this.asientoVentaSelec.getTipdocdelivery());
		asienVent.setDelivery(this.asientoVentaSelec.getDelivery());
		asienVent.setDireccion(this.asientoVentaSelec.getDireccion());
		asienVent.setSexo(this.asientoVentaSelec.getSexo());
		asienVent.setAbordo(this.asientoVentaSelec.getAbordo());
		asienVent.setIdusuarioventa(this.asientoVentaSelec.getIdusuarioventa());
		asienVent.setPromocional(this.asientoVentaSelec.getPromocional());
		asienVent.setIdpromocion(this.asientoVentaSelec.getIdpromocion());
		asienVent.setIdusuarioautorizante(this.asientoVentaSelec.getIdusuarioautorizante());
		asienVent.setHorareserva(this.asientoVentaSelec.getHorareserva());
		asienVent.setEstado_delivery(this.asientoVentaSelec.getEstado_delivery());
		asienVent.setReferenDelivery(this.asientoVentaSelec.getReferenDelivery());
		asienVent.setTelefDelivery(this.asientoVentaSelec.getTelefDelivery());
		asienVent.setFechaEntregaDelivery(this.asientoVentaSelec.getFechaEntregaDelivery());
		asienVent.setPersonaRecibeDeliv(this.asientoVentaSelec.getPersonaRecibeDeliv());
		asienVent.setTransferenciaBanco(this.asientoVentaSelec.getTransferenciaBanco());
		asienVent.setTransferenciaNumero(this.asientoVentaSelec.getTransferenciaNumero());
		asienVent.setVentaOrigen(this.asientoVentaSelec.getVentaOrigen());
		asienVent.setNumero(this.asientoVentaDestino.getNumero()); //Destino
		asienVent.setIdAsientoVentaOrigenTransbordo(this.asientoVentaSelec.getIdasientoventa());		
		asienVent.setIdProgramacion(this.programacionSelec.getIdProgramacion());
		
		
		
		if(this.piso.intValue() == 1)
		{//piso uno
			for(Asiento as : this.listaAsientosPisoUnoTarget)
			{
				if(as.getAsientoVenta() != null && as.getAsientoVenta().getNumero().equals(asienVent.getNumero()))
				{
					asienVent.setIdasientoventa(as.getAsientoVenta().getIdasientoventa());
					asienVent.setIdAsiento(as.getIdAsiento());
					as.setAsientoVenta(asienVent);
					break;
				}
			}
		}else{ //piso dos
			
			for(Asiento as : this.listaAsientosPisoDosTarget)
			{
				if(as.getAsientoVenta() != null && as.getAsientoVenta().getNumero().equals(asienVent.getNumero()))
				{
					asienVent.setIdasientoventa(as.getAsientoVenta().getIdasientoventa());
					asienVent.setIdAsiento(as.getIdAsiento());
					as.setAsientoVenta(asienVent);
					break;
				}
			}
		}
		
		
		
		this.asientoVentaSelec.setEstado("LIBRE");
		this.asientoVentaSelec.setSexo("");
		
		FacesUtils.showFacesMessage("Se transbordó el asiento individual correctamente.", 3);

	}
	
	public void pintarBusTarget(Programacion pr)
	{

		if (pr.getServicio() != null) 
		{

			try{
				
				//consultar configuracion para pintar el bus
				pr.setClase(this.claseServicioServices.findById(pr.getServicio().getIdClase()));
				pr.setTipoAsientoP1(this.tipoAsientoService.findById(pr.getClase().getIdtipoasientop1()));
				pr.setTipoAsientop2(this.tipoAsientoService.findById(pr.getClase().getIdtipoasientop2()));
				
				this.nroColumnasPisoUnoTarget = pr.getClase().getNroColumnasPisoUno();
			
				// generando asientos para el piso UNO ida siempre
				this.listaAsientosPisoUnoTarget = this.asientoService.findAsientoByClase(pr.getClase().getIdclase(), new Integer(1));
				AsientoVenta asv = null;
	
				for (Asiento as : this.listaAsientosPisoUnoTarget) 
				{
					asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), pr.getIdProgramacion());
					as.setAsientoVenta(asv);
				}
	
				// generando conf inicial para el piso DOS ida
				if(pr.getClase().getNroPisos().compareTo(new Integer(2)) == 0) 
				{
					this.nroColumnasPisoDosTarget = pr.getClase().getNroColumnasPisoDos();
					this.listaAsientosPisoDosTarget = this.asientoService.findAsientoByClase(pr.getClase().getIdclase(), new Integer(2));
					for (Asiento as : this.listaAsientosPisoDosTarget) 
					{
						asv = this.asientoVentaService.findAsientoByAsientoAndProgramacion(as.getIdAsiento(), as.getNumero(), pr.getIdProgramacion());
						as.setAsientoVenta(asv);
	
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		
	}
	
	public void transbordar()
	{
		System.out.println("Inicio - transbordar");
		AsientoVenta asienVent = null;
		
		//Piso 1
		for(Asiento as :this.listaAsientosPisoUno)
		{
			if(as.getAsientoVenta() !=null)
			{
				if( as.getAsientoVenta().getDocumentoPersona() != null && !as.getAsientoVenta().getDocumentoPersona().equals("-"))
				{
					for(Asiento ast :this.listaAsientosPisoUnoTarget)
					{
						
						if(ast.getAsientoVenta() != null)
						{
							//System.out.println("el asiento:"+as.getAsientoVenta().getDocumentoPersona());
							if(as.getAsientoVenta().getNumero().equals(ast.getAsientoVenta().getNumero()) &&  ast.getAsientoVenta().getEstado().equals("LIBRE") )
							{
								asienVent = new AsientoVenta();
								asienVent.setMonto(as.getAsientoVenta().getMonto());
								asienVent.setEstado(as.getAsientoVenta().getEstado());
								asienVent.setTipoventa(as.getAsientoVenta().getTipoventa());
								asienVent.setFechaventa(as.getAsientoVenta().getFechaventa());
								asienVent.setDocumentoPersona(as.getAsientoVenta().getDocumentoPersona());
								asienVent.setDocumentoEmpresa(as.getAsientoVenta().getDocumentoEmpresa());
								asienVent.setIdPostergado(as.getAsientoVenta().getIdPostergado());
								asienVent.setObservacion(as.getAsientoVenta().getObservacion());
								asienVent.setExterno(as.getAsientoVenta().getExterno());
								asienVent.setOficinaId(as.getAsientoVenta().getOficinaId());
								asienVent.setPrepagado(as.getAsientoVenta().getPrepagado());
								asienVent.setTipdocprepagado(as.getAsientoVenta().getTipdocprepagado());
								asienVent.setNrodocprepagado(as.getAsientoVenta().getNrodocprepagado());
								asienVent.setTipdocdelivery(as.getAsientoVenta().getTipdocdelivery());
								asienVent.setDelivery(as.getAsientoVenta().getDelivery());
								asienVent.setDireccion(as.getAsientoVenta().getDireccion());
								asienVent.setSexo(as.getAsientoVenta().getSexo());
								asienVent.setAbordo(as.getAsientoVenta().getAbordo());
								asienVent.setIdusuarioventa(as.getAsientoVenta().getIdusuarioventa());
								asienVent.setPromocional(as.getAsientoVenta().getPromocional());
								asienVent.setIdpromocion(as.getAsientoVenta().getIdpromocion());
								asienVent.setIdusuarioautorizante(as.getAsientoVenta().getIdusuarioautorizante());
								asienVent.setHorareserva(as.getAsientoVenta().getHorareserva());
								asienVent.setEstado_delivery(as.getAsientoVenta().getEstado_delivery());
								asienVent.setReferenDelivery(as.getAsientoVenta().getReferenDelivery());
								asienVent.setTelefDelivery(as.getAsientoVenta().getTelefDelivery());
								asienVent.setFechaEntregaDelivery(as.getAsientoVenta().getFechaEntregaDelivery());
								asienVent.setPersonaRecibeDeliv(as.getAsientoVenta().getPersonaRecibeDeliv());
								asienVent.setTransferenciaBanco(as.getAsientoVenta().getTransferenciaBanco());
								asienVent.setTransferenciaNumero(as.getAsientoVenta().getTransferenciaNumero());
								asienVent.setVentaOrigen(as.getAsientoVenta().getVentaOrigen());
								asienVent.setNumero(as.getAsientoVenta().getNumero());
								asienVent.setIdAsientoVentaOrigenTransbordo(as.getAsientoVenta().getIdasientoventa());
								asienVent.setIdProgramacion(ast.getAsientoVenta().getIdProgramacion());
								asienVent.setIdasientoventa(ast.getAsientoVenta().getIdasientoventa());
								asienVent.setIdAsiento(ast.getIdAsiento());
								ast.setAsientoVenta(asienVent); //
								as.getAsientoVenta().setEstado("LIBRE");
								as.getAsientoVenta().setSexo("");
								
								break;
							}
						}
						
					}
					
					
				}
				
			}
			
		}
		
		//pisdo dos:
		
		for(Asiento as :this.listaAsientosPisoDos)
		{
			if(as.getAsientoVenta() !=null) // AsientoVenta - Viene a ser el objeto con todos sus campos 
			{
				if(as.getAsientoVenta().getDocumentoPersona() != null && !as.getAsientoVenta().getDocumentoPersona().equals("-"))
				{
					for(Asiento ast :this.listaAsientosPisoDosTarget)
					{
						
						if(ast.getAsientoVenta() != null)
						{
							//System.out.println("el asiento:"+as.getAsientoVenta().getDocumentoPersona());
							if(as.getAsientoVenta().getNumero().equals(ast.getAsientoVenta().getNumero()) &&  ast.getAsientoVenta().getEstado().equals("LIBRE") )
							{
								asienVent = new AsientoVenta();
								asienVent.setMonto(as.getAsientoVenta().getMonto());
								asienVent.setEstado(as.getAsientoVenta().getEstado());
								asienVent.setTipoventa(as.getAsientoVenta().getTipoventa());
								asienVent.setFechaventa(as.getAsientoVenta().getFechaventa());
								asienVent.setDocumentoPersona(as.getAsientoVenta().getDocumentoPersona());
								asienVent.setDocumentoEmpresa(as.getAsientoVenta().getDocumentoEmpresa());
								asienVent.setIdPostergado(as.getAsientoVenta().getIdPostergado());
								asienVent.setObservacion(as.getAsientoVenta().getObservacion());
								asienVent.setExterno(as.getAsientoVenta().getExterno());
								asienVent.setOficinaId(as.getAsientoVenta().getOficinaId());
								asienVent.setPrepagado(as.getAsientoVenta().getPrepagado());
								asienVent.setTipdocprepagado(as.getAsientoVenta().getTipdocprepagado());
								asienVent.setNrodocprepagado(as.getAsientoVenta().getNrodocprepagado());
								asienVent.setTipdocdelivery(as.getAsientoVenta().getTipdocdelivery());
								asienVent.setDelivery(as.getAsientoVenta().getDelivery());
								asienVent.setDireccion(as.getAsientoVenta().getDireccion());
								asienVent.setSexo(as.getAsientoVenta().getSexo());
								asienVent.setAbordo(as.getAsientoVenta().getAbordo());
								asienVent.setIdusuarioventa(as.getAsientoVenta().getIdusuarioventa());
								asienVent.setPromocional(as.getAsientoVenta().getPromocional());
								asienVent.setIdpromocion(as.getAsientoVenta().getIdpromocion());
								asienVent.setIdusuarioautorizante(as.getAsientoVenta().getIdusuarioautorizante());
								asienVent.setHorareserva(as.getAsientoVenta().getHorareserva());
								asienVent.setEstado_delivery(as.getAsientoVenta().getEstado_delivery());
								asienVent.setReferenDelivery(as.getAsientoVenta().getReferenDelivery());
								asienVent.setTelefDelivery(as.getAsientoVenta().getTelefDelivery());
								asienVent.setFechaEntregaDelivery(as.getAsientoVenta().getFechaEntregaDelivery());
								asienVent.setPersonaRecibeDeliv(as.getAsientoVenta().getPersonaRecibeDeliv());
								asienVent.setTransferenciaBanco(as.getAsientoVenta().getTransferenciaBanco());
								asienVent.setTransferenciaNumero(as.getAsientoVenta().getTransferenciaNumero());
								asienVent.setVentaOrigen(as.getAsientoVenta().getVentaOrigen());
								asienVent.setNumero(as.getAsientoVenta().getNumero());
								asienVent.setIdAsientoVentaOrigenTransbordo(as.getAsientoVenta().getIdasientoventa());
								asienVent.setIdProgramacion(ast.getAsientoVenta().getIdProgramacion()); //datos para la flota destino
								asienVent.setIdasientoventa(ast.getAsientoVenta().getIdasientoventa()); //datos para la flota destino
								asienVent.setIdAsiento(ast.getIdAsiento()); //datos para la flota destino
								ast.setAsientoVenta(asienVent);
								as.getAsientoVenta().setEstado("LIBRE");
								as.getAsientoVenta().setSexo("");
								break;
							}
						}
						
					}
					
					
				}
				
			}
			
		}
		this.trasnbordoRealizado =Boolean.TRUE;
		FacesUtils.showFacesMessage("Se transbordaron los asiento correctamente.", 3);
		
		
	}
	
	
	public void confirmarTransbordo()
	{
		System.out.println("Inicio - confirmarTransbordo");
		Boolean encontrado = Boolean.FALSE;
		
		for(Asiento as : this.listaAsientosPisoUno)
		{
			if(as.getAsientoVenta() != null &&  !as.getAsientoVenta().getEstado().equals("LIBRE"))
			{
				FacesUtils.showFacesMessage("Aun hay asientos sin transbordar, seleccione en cada uno de ellos y transbórdelos manualmente.", 1);
				encontrado = Boolean.TRUE;
				break;
			}
		}
		
		for(Asiento as : this.listaAsientosPisoDos)
		{
			if(as.getAsientoVenta() != null && !as.getAsientoVenta().getEstado().equals("LIBRE"))
			{
				FacesUtils.showFacesMessage("Aun hay asientos sin transbordar, seleccione en cada uno de ellos y transbórdelos manualmente.", 1);
				encontrado = Boolean.TRUE;
				break;
			}
		}
		
		if(!encontrado)
		{
			//persisitir en la bd
			System.out.println("haciendo la peristencia");
			//liberando asientos de la flota que se hizo el transbordo
			try {
				this.asientoVentaService.actualizarAsientoxProgramacionIdLiberar(this.programacion.getIdProgramacion());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.listaAsientosPisoUnoTarget.addAll(this.listaAsientosPisoDosTarget);
			
			//actualizando asientos en la nueva flota
			for(Asiento ast : this.listaAsientosPisoUnoTarget)
			{	
					if(ast.getAsientoVenta() != null &&  !ast.getAsientoVenta().getEstado().equals("LIBRE"))
					{
						try {
							this.asientoVentaService.actualizarVentaAsiento(ast.getAsientoVenta());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
			
			//actualizando asientos en la nueva flota
			for(Asiento ast : this.listaAsientosPisoDosTarget)
			{	
					if(ast.getAsientoVenta() != null &&  !ast.getAsientoVenta().getEstado().equals("LIBRE"))
					{
						try {
							this.asientoVentaService.actualizarVentaAsiento(ast.getAsientoVenta());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
			
			//ejecutar dialogo
			RequestContext.getCurrentInstance().execute("dlgConfirm.show();");
		}
		System.out.println("Fin - confirmarTransbordo");
	}
	
	
	public String confirmarTransbordoDialogo()
	{
		String outcome = null;
		outcome = "pretty:ventaDirecta";
		return outcome;
	}
	
	
	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public Integer getNroFilas() {
		return nroFilas;
	}

	public void setNroFilas(Integer nroFilas) {
		this.nroFilas = nroFilas;
	}

	public Integer getNroColumnasPisoUno() {
		return nroColumnasPisoUno;
	}

	public void setNroColumnasPisoUno(Integer nroColumnasPisoUno) {
		this.nroColumnasPisoUno = nroColumnasPisoUno;
	}

	public Integer getNroColumnasPisoDos() {
		return nroColumnasPisoDos;
	}

	public void setNroColumnasPisoDos(Integer nroColumnasPisoDos) {
		this.nroColumnasPisoDos = nroColumnasPisoDos;
	}

	public List<Asiento> getListaAsientosPisoUno() {
		return listaAsientosPisoUno;
	}

	public void setListaAsientosPisoUno(List<Asiento> listaAsientosPisoUno) {
		this.listaAsientosPisoUno = listaAsientosPisoUno;
	}

	public List<Asiento> getListaAsientosPisoDos() {
		return listaAsientosPisoDos;
	}

	public void setListaAsientosPisoDos(List<Asiento> listaAsientosPisoDos) {
		this.listaAsientosPisoDos = listaAsientosPisoDos;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
	}


	public Servicio getServicioTarget() {
		return servicioTarget;
	}

	public void setServicioTarget(Servicio servicioTarget) {
		this.servicioTarget = servicioTarget;
	}

	public Integer getNroColumnasPisoUnoTarget() {
		return nroColumnasPisoUnoTarget;
	}

	public void setNroColumnasPisoUnoTarget(Integer nroColumnasPisoUnoTarget) {
		this.nroColumnasPisoUnoTarget = nroColumnasPisoUnoTarget;
	}

	public Integer getNroColumnasPisoDosTarget() {
		return nroColumnasPisoDosTarget;
	}

	public void setNroColumnasPisoDosTarget(Integer nroColumnasPisoDosTarget) {
		this.nroColumnasPisoDosTarget = nroColumnasPisoDosTarget;
	}

	public List<Asiento> getListaAsientosPisoUnoTarget() {
		return listaAsientosPisoUnoTarget;
	}

	public void setListaAsientosPisoUnoTarget(
			List<Asiento> listaAsientosPisoUnoTarget) {
		this.listaAsientosPisoUnoTarget = listaAsientosPisoUnoTarget;
	}

	public List<Asiento> getListaAsientosPisoDosTarget() {
		return listaAsientosPisoDosTarget;
	}

	public void setListaAsientosPisoDosTarget(
			List<Asiento> listaAsientosPisoDosTarget) {
		this.listaAsientosPisoDosTarget = listaAsientosPisoDosTarget;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Programacion> getListaProgramacionDemanda() {
		return listaProgramacionDemanda;
	}

	public void setListaProgramacionDemanda(
			List<Programacion> listaProgramacionDemanda) {
		this.listaProgramacionDemanda = listaProgramacionDemanda;
	}

	public Programacion getProgramacionSelec() {
		return programacionSelec;
	}

	public void setProgramacionSelec(Programacion programacionSelec) {
		this.programacionSelec = programacionSelec;
	}

	public AsientoVenta getAsientoVentaSelec() {
		return asientoVentaSelec;
	}

	public void setAsientoVentaSelec(AsientoVenta asientoVentaSelec) {
		this.asientoVentaSelec = asientoVentaSelec;
	}

	public List<AsientoVenta> getListaAsientosVentaLibres() {
		return listaAsientosVentaLibres;
	}

	public void setListaAsientosVentaLibres(
			List<AsientoVenta> listaAsientosVentaLibres) {
		this.listaAsientosVentaLibres = listaAsientosVentaLibres;
	}

	public AsientoVenta getAsientoVentaDestino() {
		return asientoVentaDestino;
	}

	public void setAsientoVentaDestino(AsientoVenta asientoVentaDestino) {
		this.asientoVentaDestino = asientoVentaDestino;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}




	public Boolean getTrasnbordoRealizado() {
		return trasnbordoRealizado;
	}




	public void setTrasnbordoRealizado(Boolean trasnbordoRealizado) {
		this.trasnbordoRealizado = trasnbordoRealizado;
	}



	
}

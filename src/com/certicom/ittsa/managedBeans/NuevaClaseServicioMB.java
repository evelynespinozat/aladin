package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.TipoAsientoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;



@ManagedBean(name="nuevaClaseServicioMB")
@ViewScoped
public class NuevaClaseServicioMB {

	private ClaseServicio busClase;
	private Integer nroColumnasPisoUno;
	private Integer nroColumnasPisoDos;
	private Integer nroFilasPisoUno;
	private Integer nroFilasPisoDos;
	private Integer anchoBusUno;
	private Integer anchoBusDos;
	private List<Asiento> listaAsientosPisoUno;
	private List<Asiento> listaAsientosPisoDos;
	private List<Asiento> listaAsientos;
	private Asiento asiento;
	private String tipo;
	private Boolean prueba;
	private Integer cantidad;
	private TipoAsiento tipoAsientop1;
	private TipoAsiento tipoAsientop2;
	
	private List<CategoriaServicio> listaCategoriaServicio;
	
	private ClaseServicioServices claseServicioServices;
	private CategoriaServicioService categoriaServicioService;
	private AsientoServices asientoService;
	private TipoAsientoService tipoAsientoService;
	
	
	public NuevaClaseServicioMB()
	{
		
	}
	
	@PostConstruct
	public void inicia()
	{	
		System.out.println("Constructor - NuevaClaseServicio ");
		
		//receive
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.busClase =(ClaseServicio) flash.get("busClase");
		this.cantidad = (Integer) flash.get("cantidad");
		
		this.prueba = Boolean.TRUE;
		//this.busClase = new ClaseServicio();
		this.nroFilasPisoUno = 5;
		this.nroFilasPisoDos = 5;
		this.nroColumnasPisoUno = 0;
		this.nroColumnasPisoDos = 0;
		this.claseServicioServices = new ClaseServicioServices();
		this.categoriaServicioService = new CategoriaServicioService();
		this.tipoAsientoService = new TipoAsientoService(); 
		this.asientoService =  new AsientoServices();
		this.tipo = "bus";
		this.tipoAsientop1 = new TipoAsiento();
		this.tipoAsientop2 = new TipoAsiento();
		
		try {
			this.listaCategoriaServicio = this.categoriaServicioService.findAll();
			this.tipoAsientop1 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop1());
			if(this.busClase.getNroPisos().equals(2))
			{
				this.tipoAsientop2 = this.tipoAsientoService.findById(this.busClase.getIdtipoasientop2());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(this.cantidad > 0){
			consultarConfiguracion(); // Para pintar los asientos
		}else{
			generarConfiguracionInicial(); // Para guardar en la base de datos los asientos
		}
		
	}
	
	
	public void generarConfiguracionInicial()
	{		
		
		if(this.busClase.getIdCatServicio() == 4) //bus
		{
			this.nroColumnasPisoUno = 5;
			this.anchoBusUno = 560;
			this.anchoBusDos = 860;
			this.listaAsientosPisoUno = new ArrayList<>();
			this.listaAsientosPisoDos = new ArrayList<>();
			
			//generando asientos para el piso uno siempre
			for(int i = 1; i<=this.nroColumnasPisoUno*this.nroColumnasPisoUno;i++)
			{
				this.asiento = new Asiento();
				this.asiento.setNumero(0+"");
				this.asiento.setPiso(1);
				this.asiento.setTipoBus(this.busClase);
				this.asiento.setEstado(Boolean.TRUE);
				this.asiento.setNroOrden(i);
				this.listaAsientosPisoUno.add(asiento);
				
			}
			
			
			//generando conf inicial para el piso dos
			if(this.busClase.getNroPisos()==2)
			{	this.nroColumnasPisoDos = 10;

				for(int i = 1; i<=this.nroColumnasPisoDos*this.nroFilasPisoDos;i++)
				{
					this.asiento = new Asiento();
					this.asiento.setNumero(0+"");
					this.asiento.setPiso(2);
					this.asiento.setTipoBus(this.busClase);
					this.asiento.setEstado(Boolean.TRUE);
					this.asiento.setNroOrden(i);
					this.listaAsientosPisoDos.add(asiento);
				}

			}
		}
		

	}
	
	
	
	
	
	public void agregarColumna(Integer piso)
	{
		int factor = 1;
		poblarAsientosColumna(piso,factor);

	}
	
	public void quitarColumna(Integer piso)
	{
		int factor = -1;
		poblarAsientosColumna(piso,factor);

	}
	
	public void agregarFila(Integer piso)
	{
		if(piso.equals(new Integer(1)))
		{
			this.nroFilasPisoUno++;
		}else{
			this.nroFilasPisoDos++;	
		}

		poblarAsientosFila(piso);
	}
	
	public void quitarFila(Integer piso)
	{
		if(piso.equals(new Integer(1)))
		{
			this.nroFilasPisoUno--;
		}else{
			this.nroFilasPisoDos--;	
		}
		poblarAsientosFila(piso);
	}
	
	public String guardarConfiguracion()
	{
		System.out.println("Inicio - guardarConfiguracion");
		String outcome= null;
		int nroAsientos = 0;
		Asiento asiento = null;
		//guardando los asientos asociados:
		
		
		if(this.busClase.getNroPisos() == 1) //Si es piso Nro1
		{
			System.out.println("Piso uno");
			for(Asiento as : this.listaAsientosPisoUno)
			{	
				as.setIdclase(this.busClase.getIdclase());
				as.setIdtipoasiento(this.busClase.getIdtipoasientop1());
				int  nro = Integer.parseInt(as.getNumero());
				
				if(nro == 0){//vacio
					as.setEstado(Boolean.FALSE);
				}else if(nro == 200){//escalera
					as.setEstado(Boolean.FALSE);
				}else if(nro == 300){//tv
					as.setEstado(Boolean.FALSE);
				}else if(nro == 400){//cafeteria
					as.setEstado(Boolean.FALSE);
				}else if(nro == 500){//baño
					as.setEstado(Boolean.FALSE);
				}else if(!as.getEstado()){//vacio
					as.setNumero(0+"");
				}

				//registrar asientos
				try {
					this.asientoService.registrarAsientos(as);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else{//Si es piso Nro2
			System.out.println("Piso dos");
			
			/*
			ClaseServicio claseSuperior = null;
			
			try {
				if(this.busClase.getIdclasecapacidad().intValue() > 0 || this.busClase.getIdclasecapacidad() != null )
				{
					claseSuperior = this.claseServicioServices.findById(this.busClase.getIdclasecapacidad());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			 */
			
			for(Asiento as : this.listaAsientosPisoUno)
			{	
				try {

					as.setIdclase(this.busClase.getIdclase());
					as.setIdtipoasiento(this.busClase.getIdtipoasientop1());
					as.setVisible(Boolean.TRUE);
					int  nro = Integer.parseInt(as.getNumero());
					
					if(nro == 0){//vacio
						as.setEstado(Boolean.FALSE);
					}else if(nro == 200){//escalera
						as.setEstado(Boolean.FALSE);
					}else if(nro == 300){//tv
						as.setEstado(Boolean.FALSE);
					}else if(nro == 400){//cafeteria
						as.setEstado(Boolean.FALSE);
					}else if(nro == 500){//baño
						as.setEstado(Boolean.FALSE);
					}else if(!as.getEstado()){//vacio
						as.setNumero(0+"");
					}
				
					//registrar asientos
					this.asientoService.registrarAsientos(as);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				
				/*reordenando y aumentar tres asientos mas*/
				int primeraPos = 6;
				//if(claseSuperior != null && (claseSuperior.getAsientos().intValue() == 56 || claseSuperior.getAsientos().intValue() == 42))
				//si es  39 o 53  sube a 56
				System.out.println("Ordenando y aumentado los asientos");
				if(this.busClase.getAsientos().intValue() == 39 || this.busClase.getAsientos().intValue() == 53)
				{
						//oteniendo lo persistido:solo delprimer piso
						this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(this.busClase.getIdclase(), new Integer(1));
						//reordenando
						int y = 1;
						for(Asiento as : this.listaAsientosPisoUno)
						{
							if(as.getNroOrden() == (primeraPos - 1))
							{
								as.setNroOrden(primeraPos);
								this.asientoService.actualizarOrdenAsiento(as.getNroOrden(), as.getIdAsiento());
								//regenerandolas nuevas posiciones
								int  k = 1;
								for(int i = (primeraPos - 1*y); i < this.listaAsientosPisoUno.size(); i++ )
								{
									this.listaAsientosPisoUno.get(i).setNroOrden(primeraPos+k);
									this.asientoService.actualizarOrdenAsiento(this.listaAsientosPisoUno.get(i).getNroOrden(), this.listaAsientosPisoUno.get(i).getIdAsiento());
									k++;
								}
								
								primeraPos += 5;
								
								y++;
							}
							
						}

						/*fin reordenamiento*/
						
						/*agrenado los 3 asientos*/
						
						asiento = new Asiento ();
						asiento.setIdclase(this.busClase.getIdclase());
						asiento.setPiso(new Integer(1));
						asiento.setIdtipoasiento(this.busClase.getIdtipoasientop1());
						asiento.setEstado(Boolean.FALSE);
						asiento.setVisible(Boolean.FALSE);
						asiento.setNumero("52");
						asiento.setNroOrden(new Integer(5));
						this.asientoService.registrarAsientos(asiento);
						this.listaAsientosPisoUno.add(asiento);
						
						asiento = new Asiento ();
						asiento.setIdclase(this.busClase.getIdclase());
						asiento.setPiso(new Integer(1));
						asiento.setIdtipoasiento(this.busClase.getIdtipoasientop1());
						asiento.setEstado(Boolean.FALSE);
						asiento.setVisible(Boolean.TRUE);
						asiento.setNumero("0");
						asiento.setNroOrden(new Integer(10));
						this.asientoService.registrarAsientos(asiento);
						this.listaAsientosPisoUno.add(asiento);
						
						asiento = new Asiento ();
						asiento.setIdclase(this.busClase.getIdclase());
						asiento.setPiso(new Integer(1));
						asiento.setIdtipoasiento(this.busClase.getIdtipoasientop1());
						asiento.setVisible(Boolean.FALSE);
						asiento.setEstado(Boolean.FALSE);
						asiento.setNumero("56");
						asiento.setNroOrden(new Integer(15));
						this.asientoService.registrarAsientos(asiento);
						this.listaAsientosPisoUno.add(asiento);
						
						asiento = new Asiento ();
						asiento.setIdclase(this.busClase.getIdclase());
						asiento.setPiso(new Integer(1));
						asiento.setIdtipoasiento(this.busClase.getIdtipoasientop1());
						asiento.setVisible(Boolean.FALSE);
						asiento.setEstado(Boolean.FALSE);
						asiento.setNumero("55");
						asiento.setNroOrden(new Integer(20));
						this.asientoService.registrarAsientos(asiento);
						this.listaAsientosPisoUno.add(asiento);
						//incrementando el nro de columnas del piso uno
						this.nroColumnasPisoUno++;
						
						
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			System.out.println("##############--Piso dos---");
			for(Asiento as : this.listaAsientosPisoDos)
			{
				
				try {
					
					as.setIdclase(this.busClase.getIdclase());
					as.setIdtipoasiento(this.busClase.getIdtipoasientop2());
					as.setVisible(Boolean.TRUE);
					//as.setPiso(2);
					int  nro = Integer.parseInt(as.getNumero());
					if(nro == 0){//vacio
						as.setEstado(Boolean.FALSE);
					}else if(nro == 200){//escalera
						as.setEstado(Boolean.FALSE);
					}else if(nro == 300){//tv
						as.setEstado(Boolean.FALSE);
					}else if(nro == 400){//cafeteria
						as.setEstado(Boolean.FALSE);
					}else if(nro == 500){//baño
						as.setEstado(Boolean.FALSE);
					}else if(!as.getEstado()){//vacio
						as.setNumero(0+"");
					}
					
					//registras asientos
				
					this.asientoService.registrarAsientos(as);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				/*aumentar un asiento mas (Actualizar) */
				
				//if(claseSuperior != null && claseSuperior.getAsientos().intValue() == 32)
				//si es 31 pasa 32
				if(this.busClase.getAsientos().intValue() == 31)
				{
					this.asientoService.actualizarAsiento32(new Integer(12),Boolean.FALSE, new Integer(7), new Integer(2), this.busClase.getIdclase());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		//tambien aqui hay que modificar
		
		for(Asiento as : listaAsientosPisoUno)
		{
			if(as.getEstado())
			{
				nroAsientos++;
			}
		}
		
		for(Asiento as : listaAsientosPisoDos)
		{
			if(as.getEstado())
			{
				nroAsientos++;
			}
		}
		
		//el numero de asientos generado coincide con el nro de asientos proporcionado en el formulario
		if(nroAsientos == this.busClase.getAsientos())
		{
			//actualizando
			try {
				this.claseServicioServices.actualizarColumnasClaseServicio(this.nroColumnasPisoUno, this.nroColumnasPisoDos,nroAsientos, this.busClase.getIdclase());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			
			System.out.println("Hubo un error: el numero de asientos proporcioando no coincide con el numero de asientos generado");
		}
		
	
		outcome= "pretty:claseservicio";
		System.out.println("Fin - guardarConfiguracion");
		
		return outcome;

	}
	
	private void poblarAsientosColumna(Integer piso, Integer factor)
	{
		
		
		if(piso == 1)
		{
			this.nroColumnasPisoUno =  this.nroColumnasPisoUno + factor;
			this.anchoBusUno = this.anchoBusUno + 70*factor;
			this.listaAsientosPisoUno.clear();
			for(int i = 1; i<=this.nroColumnasPisoUno*this.nroFilasPisoUno;i++)
			{
				this.asiento = new Asiento();
				this.asiento.setNumero(0+"");
				this.asiento.setPiso(1);
				this.asiento.setTipoBus(this.busClase);
				this.asiento.setEstado(Boolean.TRUE);
				this.asiento.setNroOrden(i);
				this.listaAsientosPisoUno.add(asiento);		
			}
			
		}else{
			this.nroColumnasPisoDos =  this.nroColumnasPisoDos + factor;
			this.anchoBusDos = this.anchoBusDos + 70*factor;
			this.listaAsientosPisoDos.clear();
			for(int i = 1; i<=this.nroColumnasPisoDos*this.nroFilasPisoDos;i++)
			{
				this.asiento = new Asiento();
				this.asiento.setNumero(0+"");//coloca 0 por defecto a los asientos
				this.asiento.setPiso(2);
				this.asiento.setTipoBus(this.busClase);
				this.asiento.setEstado(Boolean.TRUE);
				this.asiento.setNroOrden(i);
				this.listaAsientosPisoDos.add(asiento);		
			}
		}

	}
	
	
	private void poblarAsientosFila(Integer piso)
	{
		
		
		if(piso == 1)
		{
			this.listaAsientosPisoUno.clear();
			for(int i = 1; i<=this.nroColumnasPisoUno*this.nroFilasPisoUno;i++)
			{
				this.asiento = new Asiento();
				this.asiento.setNumero(0+"");
				this.asiento.setPiso(1);
				this.asiento.setTipoBus(this.busClase);
				this.asiento.setEstado(Boolean.TRUE);
				this.asiento.setNroOrden(i);
				this.listaAsientosPisoUno.add(asiento);		
			}
			
		}else{
			this.listaAsientosPisoDos.clear();
			for(int i = 1; i<=this.nroColumnasPisoDos*this.nroFilasPisoDos;i++)
			{
				this.asiento = new Asiento();
				this.asiento.setNumero(0+"");
				this.asiento.setPiso(2);
				this.asiento.setTipoBus(this.busClase);
				this.asiento.setEstado(Boolean.TRUE);
				this.asiento.setNroOrden(i);
				this.listaAsientosPisoDos.add(asiento);		
			}
		}

	}
	
	
	public void consultarConfiguracion()
	{
		System.out.println("Inicio - consultarConfiguracion");
		try {
		
				this.nroColumnasPisoUno = this.busClase.getNroColumnasPisoUno();
				//this.anchoBusUno = 560;
				this.anchoBusDos = 860;
				this.listaAsientosPisoUno = this.asientoService.findAsientoByClase(this.busClase.getIdclase(),new Integer(1));	//generando asientos para el piso uno siempre

				if(this.busClase.getNroPisos()==2)	//generando conf inicial para el piso dos
				{	
					System.out.println("Aqui se cae 3");
					this.nroColumnasPisoDos = this.busClase.getNroColumnasPisoDos();
					this.listaAsientosPisoDos = this.asientoService.findAsientoByClase(this.busClase.getIdclase(),new Integer(2));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("Fin - consultarConfiguracion");
	}

	
	public void editarAsiento(Asiento as)
	{
		System.out.println("editando el asiento:"+as.getNumero());
	}
	
	
	
	/*#########################------------seters and getters------##############################################33*/
	

	public ClaseServicio getBusClase() {
		return busClase;
	}

	public void setBusClase(ClaseServicio busClase) {
		this.busClase = busClase;
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

	public Integer getAnchoBusUno() {
		return anchoBusUno;
	}

	public void setAnchoBusUno(Integer anchoBusUno) {
		this.anchoBusUno = anchoBusUno;
	}

	public Integer getAnchoBusDos() {
		return anchoBusDos;
	}

	public void setAnchoBusDos(Integer anchoBusDos) {
		this.anchoBusDos = anchoBusDos;
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

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public List<CategoriaServicio> getListaCategoriaServicio() {
		return listaCategoriaServicio;
	}

	public void setListaCategoriaServicio(List<CategoriaServicio> listaCategoriaServicio) {
		this.listaCategoriaServicio = listaCategoriaServicio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getPrueba() {
		return prueba;
	}

	public void setPrueba(Boolean prueba) {
		this.prueba = prueba;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public List<Asiento> getListaAsientos() {
		return listaAsientos;
	}

	public void setListaAsientos(List<Asiento> listaAsientos) {
		this.listaAsientos = listaAsientos;
	}

	public Integer getNroFilasPisoUno() {
		return nroFilasPisoUno;
	}

	public void setNroFilasPisoUno(Integer nroFilasPisoUno) {
		this.nroFilasPisoUno = nroFilasPisoUno;
	}

	public Integer getNroFilasPisoDos() {
		return nroFilasPisoDos;
	}

	public void setNroFilasPisoDos(Integer nroFilasPisoDos) {
		this.nroFilasPisoDos = nroFilasPisoDos;
	}

	public TipoAsiento getTipoAsientop1() {
		return tipoAsientop1;
	}

	public void setTipoAsientop1(TipoAsiento tipoAsientop1) {
		this.tipoAsientop1 = tipoAsientop1;
	}

	public TipoAsiento getTipoAsientop2() {
		return tipoAsientop2;
	}

	public void setTipoAsientop2(TipoAsiento tipoAsientop2) {
		this.tipoAsientop2 = tipoAsientop2;
	}
	
	
	
	
}

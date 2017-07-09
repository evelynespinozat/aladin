package com.certicom.ittsa.managedBeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TipoAsientoService;
import com.pe.certicom.ittsa.commons.Utils;



@ManagedBean(name="reporteVentasMB")
@ViewScoped
public class ReporteVentasMB {
	
	private Integer idOrigen;
	private Integer idDestino;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private Date fechaProgramacion;
	private List<ClaseServicio> listaServicios;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionFilter;
	//private List<CategoriaServicio> listaCatServicio;
	private Integer disponibles;
	private Integer disponiblesP1;
	private Integer disponiblesP2;
	private Integer capacidad;
	private Integer vendidos;
	private Integer reservados;
	
	
	private ProgramacionService programacionService;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private ClaseServicioServices clasServicioServices;
	private CategoriaServicioService categoriaServServices;
	private ServicioService servicioService;
	private ClaseServicioServices claseServicioServices;
	private TipoAsientoService tipoAsientoService;
	private AsientoVentaServices asientoVentaService;
	
	@PostConstruct
	public void inicia()
	{
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.clasServicioServices = new ClaseServicioServices();
		this.categoriaServServices = new CategoriaServicioService();
		this.servicioService = new ServicioService();
		this.claseServicioServices = new ClaseServicioServices();
		this.tipoAsientoService = new TipoAsientoService();
		this.asientoVentaService = new AsientoVentaServices();
		this.fechaProgramacion = new Date();
		
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaServicios = this.clasServicioServices.listaCServiciosActivos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarDestinos() {
		try {
			this.listaDestino = destinoServices.obtenerDestino(getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	public void buscarProgramacion()
	{
		
		String desCategoriaServicio = "BUS";
		Servicio serv = null;
		ClaseServicio clase = null;
		try {
			
			CategoriaServicio cate = this.categoriaServServices.findByNombre(desCategoriaServicio);
			
			this.listaProgramacion = this.programacionService.findProgByFecha_orig_destVentas(getFechaProgramacion(), getIdOrigen(), getIdDestino(),cate.getIdCatServicio());
			
			for(Programacion prog : this.listaProgramacion)
			{
				serv = this.buscarServicio(new SimpleDateFormat("yyyy-MM-dd").format(getFechaProgramacion()) ,getIdOrigen(),getIdDestino(), prog.gethSalida(),prog.getIdProgramacion());
				if(serv != null){
					
				clase = this.claseServicioServices.findByServicio(serv.getIdServicio());
				TipoAsiento tipAs = null; 
				tipAs = this.tipoAsientoService.findById(clase.getIdtipoasientop1());
				prog.setTipoAsientoP1(tipAs);
				if(clase.getNroPisos().intValue()==2)
				{
					tipAs = this.tipoAsientoService.findById(clase.getIdtipoasientop2());
					prog.setTipoAsientop2(tipAs);
				}
				
				this.capacidad = clase.getAsientos();
				// obteniendo la cabtidad de asientos disponibles
				this.disponibles = this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("LIBRE"),Boolean.TRUE);
				this.disponiblesP1 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(prog.getIdProgramacion(), "LIBRE", new Integer(1),Boolean.TRUE);
				this.disponiblesP2 = this.asientoVentaService.countbyprogramacionAndEstadoXPiso(prog.getIdProgramacion(), "LIBRE", new Integer(2),Boolean.TRUE);
				
				this.vendidos =  this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("VENTA"),Boolean.TRUE);
				this.reservados = this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("RESERVA"),Boolean.TRUE);
				//this.reservados = this.asientoVentaService.countbyprogramacionAndEstado(prog.getIdProgramacion(),new String("RESERVA"));
				
				prog.setDisponibles(this.disponibles);
				prog.setDisponibleP1(this.disponiblesP1);
				prog.setDisponibleP2(this.disponiblesP2);
				prog.setVendidos(this.vendidos);
				prog.setReservados(this.reservados);
				prog.setServicio(serv);
				prog.setClase(clase);
				}
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Servicio buscarServicio(String fecha, Integer origenId,
			Integer destinoId, String hsalida, Integer idProgramacion) {
		Servicio serv = null;
		try {
			System.out.println("" +fecha+" - "+origenId+" - "+destinoId+" - "+hsalida +" - "+idProgramacion);
			serv = this.servicioService.serviciobyProgramacion(fecha, origenId,
					destinoId, hsalida, idProgramacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serv;
	}
	/*#########################-----gettres and setters---------######################*/
	
	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
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

	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	public List<ClaseServicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<ClaseServicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public Integer getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}

	public Integer getDisponiblesP1() {
		return disponiblesP1;
	}

	public void setDisponiblesP1(Integer disponiblesP1) {
		this.disponiblesP1 = disponiblesP1;
	}

	public Integer getDisponiblesP2() {
		return disponiblesP2;
	}

	public void setDisponiblesP2(Integer disponiblesP2) {
		this.disponiblesP2 = disponiblesP2;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getVendidos() {
		return vendidos;
	}

	public void setVendidos(Integer vendidos) {
		this.vendidos = vendidos;
	}

	public Integer getReservados() {
		return reservados;
	}

	public void setReservados(Integer reservados) {
		this.reservados = reservados;
	}
	

}

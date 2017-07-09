package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.CierreCaja;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.UsuarioPerfil;
import com.certicom.ittsa.services.CierreCajaService;
import com.certicom.ittsa.services.LiquidacionServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "liquidacionVentaMB")
@ViewScoped
public class LiquidacionVentaMB extends GenericBeans implements Serializable {

	private List<Liquidacion_Venta> listaProceso;
	private List<Liquidacion_Venta> listaProcesoEgreso;
	private List<Usuario> listaUsuarioTraspaso;
	private List<Liquidacion_Venta> listaSerieDocumento;

	private List<Liquidacion_Venta> listPvPendientes;
	private Liquidacion_VentaServices liquidacion_VentaServices;
	private CierreCajaService cierreCajaService;
	private UsuarioServices usuarioServices;

	private CierreCaja cierreCaja;

	@ManagedProperty(value = "#{loginMB.tipLiquidacion}")
	private String liquidacion_perfil;

	@ManagedProperty(value = "#{loginMB.tipLiquidacion}")
	private String liquidacion_perfilEgreso;

	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	@ManagedProperty(value="#{loginMB.desOficina}")
	private String oficinaUser;

	// filtro de busqueda
	private Date fecLiq;
	private Double efectivoCaja;

	// datos Log
	private Log log;
	private LogMB logmb;

	public LiquidacionVentaMB() {
	}

	@PostConstruct
	public void inicia() {
		this.liquidacion_VentaServices = new Liquidacion_VentaServices();
		this.cierreCajaService = new CierreCajaService();
		this.usuarioServices = new UsuarioServices();

		this.listaUsuarioTraspaso = new ArrayList<>();
		this.cierreCaja = new CierreCaja();
		this.fecLiq = new Date();
		this.listPvPendientes = new ArrayList<Liquidacion_Venta>();
		try {
			this.listaUsuarioTraspaso = this.usuarioServices.buscarPorPerfil(
					getUsuarioLogin().getIdoficina(), getUsuarioLogin()
							.getCod_perfil());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// metodo para consultar a la tabla t_liquidacion_venta
	public void consultarLiquidacion() {
		try {
			// INGRESOS
			this.listaProceso = this.liquidacion_VentaServices.listarProcesosVenta();//retorna VENTA
			for (Liquidacion_Venta ligVenta : this.listaProceso) {
				List<Liquidacion_Venta> liquidacion_fp = null;
				// corregir la liquidacion por punto de venta
				if (this.liquidacion_perfil.equals(Constante.LIQUIDACION_PUNTOVENTA)) {
					liquidacion_fp = this.liquidacion_VentaServices.listarLiquidacionVentasxFPagoPV(getFecLiq(), ligVenta.getProceso(), getUsuarioLogin().getIdpuntoventa());
					this.efectivoCaja = this.liquidacion_VentaServices.calcularEfectivoVentasxPuntoVenta(getFecLiq(),getUsuarioLogin().getIdpuntoventa());
				} else if (this.liquidacion_perfil.equals(Constante.LIQUIDACION_OFICINA)) {
					liquidacion_fp = this.liquidacion_VentaServices.listarLiquidacionVentasxFPagoOFI(getFecLiq(), ligVenta.getProceso(), getUsuarioLogin().getIdoficina());
					this.efectivoCaja = this.liquidacion_VentaServices.calcularEfectivoVentasxOficina(getFecLiq(),getUsuarioLogin().getIdoficina());
				}
				double sum = 0;
				for (Liquidacion_Venta liq_fp : liquidacion_fp) {
					ligVenta.getfPagos().add(liq_fp);
					sum += liq_fp.getTotalxFPago();
				}
				ligVenta.setTotalxProceso(roundDecimal(sum));
			}

			// EGRESOS
			this.listaProcesoEgreso = this.liquidacion_VentaServices.listarProcesosVenta(); //retorna VENTA
			for (Liquidacion_Venta ligVenta2 : this.listaProcesoEgreso) {
				List<Liquidacion_Venta> liquidacion_fp2 = null;
				// corregir la liquidacion por punto de venta
				if (this.liquidacion_perfil.equals(Constante.LIQUIDACION_PUNTOVENTA)) {
					liquidacion_fp2 = this.liquidacion_VentaServices.listarLiquidacionVentasxFPagoPVEgreso(getFecLiq(),ligVenta2.getProceso(), getUsuarioLogin().getIdpuntoventa());
				} else if (this.liquidacion_perfil.equals(Constante.LIQUIDACION_OFICINA)) {
					liquidacion_fp2 = this.liquidacion_VentaServices.listarLiquidacionVentasxFPagoOFIEgreso(getFecLiq(),ligVenta2.getProceso(), getUsuarioLogin().getIdoficina());
				}
				double sum = 0;
				for (Liquidacion_Venta liq_fp2 : liquidacion_fp2) {
					ligVenta2.getfPagos().add(liq_fp2);
					sum += liq_fp2.getTotalxFPago();
				}
				ligVenta2.setTotalxProceso(roundDecimal(sum));
			}
			
			
			
			//SERIES
		    this.listaSerieDocumento = 	this.liquidacion_VentaServices.listarSeriesLiquidacionVentas(getFecLiq(), getUsuarioLogin().getIdpuntoventa());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cerrarCajaxPV() {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			// validando que solo se haya cerrado una vez por dia por usuario
			List<CierreCaja> lista = this.cierreCajaService.verificarCierrexUsuarioFechaCounter(getUsuarioLogin().getIdusuario(), getFecLiq());
			if (lista.size() > 0) {
				FacesUtils.showFacesMessage("La caja se encuentra cerrada para esta fecha.",Constante.INFORMACION);
				context.execute("dlgCierreCaja.hide()");

			} else {
				this.liquidacion_VentaServices.preliquidarVentasxPuntoVentaCargo(getFecLiq(), getUsuarioLogin().getIdpuntoventa());
				// creando el cierre caja
				this.cierreCaja.setfCierre(new Date());
				this.cierreCaja.setIdPuntoVenta(getUsuarioLogin().getIdpuntoventa());
				this.cierreCaja.setIdUsuario(getUsuarioLogin().getIdusuario());
				this.cierreCaja.setSaldo(this.efectivoCaja);
				this.cierreCaja.setTipoUsuario("COUNTER");
				this.cierreCajaService.crearCierreCaja(this.cierreCaja);
				
				FacesUtils.showFacesMessage("Se preliquidó correctamente.",Constante.INFORMACION);
				context.execute("dlgCierreCaja.hide()");
				this.efectivoCaja = new Double("0");
				this.listaProceso = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrarCajaxOFI() {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			// validando
			System.out.println("FECH LIQU" + getFecLiq() +" OFICINA: "+ getUsuarioLogin().getIdoficina());
			this.listPvPendientes = this.liquidacion_VentaServices.listarPreLiquidacionesVenta(getFecLiq(), getUsuarioLogin().getIdoficina());
			if (this.listPvPendientes.size() == 0) {
				this.liquidacion_VentaServices.liquidarVentasxOficina(getFecLiq(),getUsuarioLogin().getIdoficina());
				FacesUtils.showFacesMessage("Se liquidó correctamente.",Constante.INFORMACION);
			} else if (this.listPvPendientes.size() > 0) {
				context.execute("dlgCierreCaja.show()");
				context.execute("dlgOmiso.show()");
				context.update("formOmiso");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCounter() {
		return this.liquidacion_perfil.equals(Constante.LIQUIDACION_PUNTOVENTA) ? true
				: false;
	}

	public boolean isJFCounter() {
		return this.liquidacion_perfil.equals(Constante.LIQUIDACION_OFICINA) ? true
				: false;
	}

	private double roundDecimal(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	
	public void imprimirConsolidado(){
		
		// IMPORTANTE  se esta considerando que aun las ventas no han sido liquidadas por estLiqJCounter IS NULL
		
		List<Liquidacion_Venta> rptLista = new ArrayList<Liquidacion_Venta>();
		Double caja_efectivo= 0.0;
		Liquidacion_Venta lv = new Liquidacion_Venta();

		List<Liquidacion_Venta> rpt = null;
		try {
			rpt = new ArrayList<Liquidacion_Venta>();
			rpt = this.liquidacion_VentaServices.rptLiquidacionxFPagoOFI(getFecLiq(), getUsuarioLogin().getIdoficina());
			lv.setListaIngresos(rpt);
			rpt = this.liquidacion_VentaServices.rptLiquidacionxFPagoOFIEgreso(getFecLiq(), getUsuarioLogin().getIdoficina());
			lv.setListaEgresos(rpt);
			
			rptLista.add(lv);
			
			caja_efectivo = this.liquidacion_VentaServices.calcularEfectivoxOficina(getFecLiq(), getUsuarioLogin().getIdoficina());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		String fecha = formato.format(getFecLiq());
		
		try {
			
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			Map<String, Object> input = new HashMap<String, Object>();
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			input.put("P_LOGO", p_logo);
			input.put("P_OFICINA", getOficinaUser());
			input.put("P_FECHA", fecha);
			input.put("P_EFECTIVO_CAJA", caja_efectivo);
			
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptLiquidacionCargoCons.jasper");
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, rptLista);

			ExportarArchivo.executePdf(bytes, "LiquidacionConsolidada.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
		
		
	}
	
	public void imprimirDetallado(){
		
	}

	public Date getFecLiq() {
		return fecLiq;
	}

	public void setFecLiq(Date fecLiq) {
		this.fecLiq = fecLiq;
	}

	public List<Liquidacion_Venta> getListaProceso() {
		return listaProceso;
	}

	public void setListaProceso(List<Liquidacion_Venta> listaProceso) {
		this.listaProceso = listaProceso;
	}

	public String getLiquidacion_perfil() {
		return liquidacion_perfil;
	}

	public void setLiquidacion_perfil(String liquidacion_perfil) {
		this.liquidacion_perfil = liquidacion_perfil;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<Liquidacion_Venta> getListPvPendientes() {
		return listPvPendientes;
	}

	public void setListPvPendientes(List<Liquidacion_Venta> listPvPendientes) {
		this.listPvPendientes = listPvPendientes;
	}

	public Double getEfectivoCaja() {
		return efectivoCaja;
	}

	public void setEfectivoCaja(Double efectivoCaja) {
		this.efectivoCaja = efectivoCaja;
	}

	public CierreCaja getCierreCaja() {
		return cierreCaja;
	}

	public void setCierreCaja(CierreCaja cierreCaja) {
		this.cierreCaja = cierreCaja;
	}

	public List<Usuario> getListaUsuarioTraspaso() {
		return listaUsuarioTraspaso;
	}

	public void setListaUsuarioTraspaso(List<Usuario> listaUsuarioTraspaso) {
		this.listaUsuarioTraspaso = listaUsuarioTraspaso;
	}

	public List<Liquidacion_Venta> getListaProcesoEgreso() {
		return listaProcesoEgreso;
	}

	public void setListaProcesoEgreso(List<Liquidacion_Venta> listaProcesoEgreso) {
		this.listaProcesoEgreso = listaProcesoEgreso;
	}

	public String getLiquidacion_perfilEgreso() {
		return liquidacion_perfilEgreso;
	}

	public void setLiquidacion_perfilEgreso(String liquidacion_perfilEgreso) {
		this.liquidacion_perfilEgreso = liquidacion_perfilEgreso;
	}

	public List<Liquidacion_Venta> getListaSerieDocumento() {
		return listaSerieDocumento;
	}

	public void setListaSerieDocumento(List<Liquidacion_Venta> listaSerieDocumento) {
		this.listaSerieDocumento = listaSerieDocumento;
	}

	public String getOficinaUser() {
		return oficinaUser;
	}

	public void setOficinaUser(String oficinaUser) {
		this.oficinaUser = oficinaUser;
	}
	
	
	
}

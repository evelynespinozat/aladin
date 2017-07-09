package com.pe.certicom.ittsa.commons;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utiles {
	
	
	public static String dateFormatToString(Date fecha,int opcion){
		String fechaString = "";
		String FORMATO = "";
		switch (opcion) {
		case 1:
			FORMATO = "dd-MM-yyyy";
			break;
		case 2:
			FORMATO = "HH:mm:ss";
			break;
			//agregar
		}
		DateFormat dformat  = new SimpleDateFormat(FORMATO);
		fechaString = dformat.format(fecha);
		return fechaString;
	}
	
	public  static Date stringFormatToDate(String fecha, int opcion) throws ParseException{
		Date fechaDate = null;
		String FORMATO = "";
		switch (opcion) {
		case 1:
			FORMATO = "dd-MM-yyyy HH:mm:ss";
			break;
			// agregar
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO);
		
		fechaDate = sdf.parse(fecha);
		return fechaDate;
	}
	
	public static Long getDiferenciasenHoras(Date finicio,Date ffin){
		
		Long diffEnHoras = null;
		
		Calendar calffin = Calendar.getInstance();
		Calendar calfini = Calendar.getInstance();
		
		calffin.setTime(ffin);
		calfini.setTime(finicio);
		
		Long milFfin = calffin.getTimeInMillis();
		Long milFini = calfini.getTimeInMillis();
		
		diffEnHoras= Math.abs((milFfin - milFini))/(60*60*1000);
		
		return diffEnHoras;
	}
	
	public static Date agregarQuitarHoras(Date fecha, int horas){
		 
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		  calendar.add(Calendar.HOUR, horas);  // numero de horas a a�adir, o restar en caso de horas<0

		  return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas a�adidas
		 
   }
	
	public static boolean esMismodia(Date finicio,Date ffin){
		
		boolean sameDate = false;
		
		if(ffin.compareTo(finicio) ==0){
			sameDate = true;
		} else{
			sameDate = false;
		}
		
		
		
		return sameDate;
	}
	
	public static Date deleteMinSecHour(Date fecha){
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); 
		calendar.set(Calendar.HOUR, 0);  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND, 0);  
		calendar.set(Calendar.MILLISECOND, 0);	  
	  
	  return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas a�adidas
	}
	
	public static Date agregarFechaAHora(Date fecha,Date hora){
		
		Calendar today = Calendar.getInstance();
		today.setTime(fecha);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hora);
		
		calendar.set(Calendar.DATE, today.get(Calendar.DATE));	  
		calendar.set(Calendar.MONTH, today.get(Calendar.MONTH));	  
		calendar.set(Calendar.YEAR, today.get(Calendar.YEAR));	  
	  
	  return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas a�adidas
	}
	
	public static int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) 
	{

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
		fechaInicial = df.parse(fechaInicioString);
		}
		catch (ParseException ex) {
		}

		String fechaFinalString = df.format(fechaFinal);
		try {
		fechaFinal = df.parse(fechaFinalString);
		}
		catch (ParseException ex) {
		}

		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ( (int) dias);
	}
	
	public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
		  int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
		  return d.setScale(scale, mode);
		}
	
	  public static Date obtenerPrimerDiaDelMes() {
	        Calendar cal = Calendar.getInstance();
	        cal.set(cal.get(Calendar.YEAR), 
	                cal.get(Calendar.MONTH),
	                cal.getActualMinimum(Calendar.DAY_OF_MONTH),
	                cal.getMinimum(Calendar.HOUR_OF_DAY),
	                cal.getMinimum(Calendar.MINUTE),
	                cal.getMinimum(Calendar.SECOND));
	        return cal.getTime();
	    }

	    public static Date obtenerUltimoDiaDelMes() {
	        Calendar cal = Calendar.getInstance();
	        cal.set(cal.get(Calendar.YEAR),
	                cal.get(Calendar.MONTH),
	                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
	                cal.getMaximum(Calendar.HOUR_OF_DAY),
	                cal.getMaximum(Calendar.MINUTE),
	                cal.getMaximum(Calendar.SECOND));
	        return cal.getTime();
	    }
	
}

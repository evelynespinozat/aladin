package com.pe.certicom.ittsa.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;

public class Utils {
	private ChannelSftp sftpChannel;
	private Session session;

	public ChannelSftp crearConexionSFTP(){
		JSch jsch = new JSch();
        session = null;
        Channel channel =null;
        try 
        {

            session = jsch.getSession(Constante.USER_SERVER_IMG, Constante.HOST_SERVER_IMG);
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);  
            session.setPassword(Constante.PASS_SERVER_IMG);                
            session.connect();              
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("Is connected to IP:"+ channel.isConnected());
            }
            catch (JSchException e) {
                e.printStackTrace();
            }
        
        sftpChannel = (ChannelSftp) channel;
        return sftpChannel;
		
	}
	
	public void cerrarConexionSFTP(){
		sftpChannel.exit();             
        session.disconnect();
        	
	}
	
	public void createZip(String filename, String carpeta, int inicioSubLote,int finalSubLote) {

		try {
			int BUFFER = 2048;
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(filename);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];

			for (int i = inicioSubLote; i <= finalSubLote; i++) {
				String filename1 = Constante.PREFIJO_IMG + autocompletar(i) + Constante.EXTENSION_IMG; 
				System.out.println("Agregando al ZIP: " + filename1);
				FileInputStream fi = new FileInputStream(carpeta + filename1);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(filename1);

				// Guardamos el objeto en el ZIP
				out.putNextEntry(entry);
				int count;
				// Escribimos el objeto en el ZIP
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
					
				}
				out.flush();
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void UnZip(String dirzipOriginal, String dirDest) throws Exception {
		try {
			int BUFFER = 2048;
			File dirDestino = new File(dirDest);
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(dirzipOriginal);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			FileOutputStream fos = null;
			ZipEntry entry;
			int count;
			int index = 0;
			byte data[] = new byte[BUFFER];
			String rutaarchivo = null;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				rutaarchivo = entry.getName();
				// tenemos que quitar el primer directorio
				index = rutaarchivo.indexOf("/");
				rutaarchivo = rutaarchivo.substring(index + 1);
				if (rutaarchivo.trim().length() > 0) {
					// write the files to the disk
					try {
						dest = null;
						File fileDest = new File(dirDestino.getAbsolutePath() + "/" + rutaarchivo);
						if (entry.isDirectory()) {
							fileDest.mkdirs();
						} else {
							if (fileDest.getParentFile().exists() == false)
								fileDest.getParentFile().mkdirs();

							fos = new FileOutputStream(fileDest);
							dest = new BufferedOutputStream(fos, BUFFER);
							while ((count = zis.read(data, 0, BUFFER)) != -1) {
								dest.write(data, 0, count);
							}
							dest.flush();
						}
					} finally {
						try {
							if (dest != null)
								dest.close();
						} catch (Exception e) {
							;
						}
					}
				}
			}
			zis.close();
			File zip = new File(dirzipOriginal); 
		    zip.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	static class MyJschLogger implements Logger {
		static java.util.Hashtable name = new java.util.Hashtable();
		static {
			name.put(new Integer(DEBUG), "DEBUG: ");
			name.put(new Integer(INFO), "INFO: ");
			name.put(new Integer(WARN), "WARN: ");
			name.put(new Integer(ERROR), "ERROR: ");
			name.put(new Integer(FATAL), "FATAL: ");
		}

		public boolean isEnabled(int level) {
			return true;
		}

		public void log(int level, String message) {
			System.err.print(name.get(new Integer(level)));
			System.err.println(message);
		}

	}
	
	
	
	public String autocompletar(int num){
		String num1 = String.valueOf(num);
		String cero = "0";
		
		for(int i=0;i<Constante.NRO_DIG_NUMERACION_SCANNER - num1.length()-1;i++){
			cero = 0 + cero;
		}
		return cero+num1;
	}
	
	public Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, dias);
		return cal.getTime();
	}
	
	public  Date sumaMes(Date fecha, int mes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, mes);
		return cal.getTime();
	}
	
	public Date sumaDiasVarios(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		return cal.getTime();
	}

	
	
	//fecha: dd/MM/yyyy
	//horaMin:HH:mm:ss  formaoto 24H
	public static Integer comparaFechas(String fecha, String horaMin)
	{
		//devuelve
		//0: si la fehva y hora proporcionada son iguales a lafecha y hora actual
		//1 si al fecha y hora  proporcionada esmayor a a fecha actual
		//-1 si la fecha porporcionada e smenor a a fecha actual
		Integer resultado = 0;
		String dateInString = fecha + horaMin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date fechaActual = new Date();
			
		try {
	 
			Date date = formatter.parse(dateInString);
			System.out.println(date);
			//System.out.println(formatter.format(date));
		     //comparando las horas delas fechas
			if(date.before(fechaActual))
			{
				resultado = -1;
				
			}else if(date.after(fechaActual)){
				resultado = 1;
			}else{ //si sin iguales
				resultado = 0;
			}
		     
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}


	//date to String:
	public static String dateAString(Date fecha)
	{
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(fecha);
		
	}
	
	//time to String:
	public static String timeAString(Date hora)
	{
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(hora);
			
	}
	
	
	//time to String:
	public static Date stringToDateTime(String  dateInString)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		//String dateInString = "Friday, Jun 7, 2013 12:10:56 PM";		
		Date date = null;
		try {
	 
			date = formatter.parse(dateInString);
			//System.out.println(date);
			//System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
			
	}
	
}

package com.pe.certicom.ittsa.commons;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager; // Impresión
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;


public class ExportarArchivo {

	 public static String getPath(String ruta){
	        ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
	        String path = servletContext.getRealPath(ruta);
	        return path;
	    }

	    /**
	     * Exportar archivos a pdf.
	     * @param [jasperFile] [Nombre del archivo compilado .jaspe], tipo [String].
	     * @param [parameters] [Ingreso de parametros que pueden ser utilizados en el jasper], tipo [Map<>].
	     * @return [dataList] [Arreglo que contiene los registros que deberï¿½ pintar el jasperreport], tipo [List<?>].
	     * @throws [nombre de excepciï¿½n] [explicaciï¿½n].
	     */
	    public static byte[] exportPdf(String jasperFile, Map<String, Object> parameters, List<?> dataList) throws Exception 
	    {
	        System.out.println("exportPdf ==>");
	        
	        // creating the virtualizer
	        JRFileVirtualizer virtualizer = new JRFileVirtualizer(300, "/tmp/"); 
	        // Pass virtualizer object throw parameter map
	        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer); 
	        
	      //Preenche o relatório e exibe numa GUI
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parameters, new JRBeanCollectionDataSource(dataList));	        
	        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parameters, new JRBeanCollectionDataSource(dataList));
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        JRPdfExporter jRPdfExporter = new JRPdfExporter();
	        jRPdfExporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
	        jRPdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	        jRPdfExporter.exportReport();
	        byte[] bytes = byteArrayOutputStream.toByteArray();
	        jRPdfExporter = null;
	        return bytes;
	        
	        
	     
	        
	    }
	    
	    
	    
	    /**
	     * Exportar archivos a pdf.
	     * @param [jasperFile] [Nombre del archivo compilado .jaspe], tipo [String].
	     * @param [parameters] [Ingreso de parametros que pueden ser utilizados en el jasper], tipo [Map<>].
	     * @return [dataList] [Arreglo que contiene los registros que deberï¿½ pintar el jasperreport], tipo [List<?>].
	     * @throws [nombre de excepciï¿½n] [explicaciï¿½n].
	     */
	    public static byte[] exportXls(String jasperFile, Map<String, Object> parameters, List<?> dataList, boolean isOnePagePerSheet) throws Exception {
	    	 parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parameters, new JRBeanCollectionDataSource(dataList));
		        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		        JRXlsExporter exporter = new JRXlsExporter();
		        exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, new Boolean(isOnePagePerSheet));
		        exporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		        exporter.setParameter(JExcelApiExporterParameter.CREATE_CUSTOM_PALETTE, Boolean.TRUE);
		        exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
		        exporter.exportReport();
		        byte[] bytes = byteArrayOutputStream.toByteArray();
		        byteArrayOutputStream.flush();
		        byteArrayOutputStream.close();
		        exporter = null;
		        return bytes;
	    }

	    public static void executePdf(byte[] bytes, String name){
	        try {

	            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	            response.reset();
	            response.setContentType("application/octet-stream");
	            response.setContentLength(bytes.length);
	            response.setHeader("Content-disposition","attachment; filename=\""+name+"\"");
	            response.setHeader("Pragma", "no-cache");
	            response.setDateHeader("Expires", 0);

	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(bytes, 0, bytes.length);
	            ouputStream.flush();
	            ouputStream.close();

	        } catch (Exception e) {
	            System.out.println("ERROR JASPER ==>"+e);
	        }
	    }

	    public static void executeExccel(byte[] bytes, String name){
	        try {

	            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	            response.reset();
	            response.setContentType("application/octet-stream");
	            response.setContentLength(bytes.length);
	            response.setHeader("Content-disposition","attachment; filename=\"'"+name+"\"");
	            response.setHeader("Pragma", "no-cache");
	            response.setDateHeader("Expires", 0);
	            
	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(bytes, 0, bytes.length);
	            ouputStream.flush();
	            ouputStream.close();
	            
	        } catch (Exception e) {
	            System.out.println("ERROR JASPER ==>"+e);
	        }
	    }
	    
	    
	    public static byte[] appendFiles(byte[] src1, byte[] src2) throws Exception { 
	    	try { 
	    	int f = 0; 
	    	// Create a reader for a certain document 
	    	PdfReader reader = new PdfReader(src1); 

	    	// Retrieve the total number of pages 
	    	int n = reader.getNumberOfPages(); 


	    	// step 1: creation of a document-object 
	    	Document document = new Document(reader.getPageSizeWithRotation(1)); 
	    	// step 2:Create a writer that listens to the document 
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	    	PdfWriter writer = PdfWriter.getInstance(document, baos); 

	    	// step 3: Open the document 
	    	document.open(); 
	    	PdfContentByte cb = writer.getDirectContent(); 

	    	PdfImportedPage page; 
	    	int rotation; 
	    	// step 4: Add content 
	    	{ 
	    	int i = 0; 
	    	while (i < n) { 
	    	i++; 
	    	document.setPageSize(reader.getPageSizeWithRotation(i)); 
	    	document.newPage(); 
	    	page = writer.getImportedPage(reader, i); 
	    	rotation = reader.getPageRotation(i); 
	    	if (rotation == 90 || rotation == 270) { 
	    	cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(i).getHeight()); 
	    	} else { 
	    	cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0); 
	    	} 
	    	//System.err.println("Processed page " + i); 
	    	} 
	    	reader = new PdfReader(src2); 
	    	n = reader.getNumberOfPages(); 
	    	} 
	    	{ 
	    	int i = 0; 
	    	while (i < n) { 
	    	i++; 
	    	document.setPageSize(reader.getPageSizeWithRotation(i)); 
	    	document.newPage(); 
	    	page = writer.getImportedPage(reader, i); 
	    	rotation = reader.getPageRotation(i); 
	    	if (rotation == 90 || rotation == 270) { 
	    	cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(i).getHeight()); 
	    	} else { 
	    	cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0); 
	    	} 
	    	} 
	    	} 
	    	// step 5: we close the document 
	    	document.close(); 
	    	return baos.toByteArray(); 
	    	} catch (Exception e) { 
	    	System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
	    	} 
	    	return null; 
	    	}

	    /*
	     * Autor: Zurita
	     * 02/10/2015
	     * */
	    public static void executePdfAndOpen(byte[] bytes, String name)
	    {
	    	
	        try {

	            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	            response.reset();
	            
	            //TODO: Esto debe de tener el fijo no el octec-stream, sino siempre va a dercargarlo
	            response.setContentType("application/pdf");

	            response.setContentLength(bytes.length);
	            
	            //TODO: Para mostrarlo y no descargarlo
	            response.setHeader("Content-disposition","inline; filename=\""+name+"\"");
	            
	            response.setHeader("Pragma", "public");
	            response.setDateHeader("Expires", 0);

	            ServletOutputStream ouputStream = response.getOutputStream();
	            ouputStream.write(bytes, 0, bytes.length);
	            ouputStream.flush();
	            ouputStream.close();

	        } catch (Exception e) {
	            System.out.println("ERROR JASPER ==>"+e);
	        }
	    }

	    /*
	     * Autor: Zurita
	     * 02/10/2015
	     * */
	    public static byte[] exportPdfAndSendDefaultPrint(String jasperFile, Map<String, Object> parameters, List<?> dataList) throws Exception 
	    {
	        System.out.println("exportPdf ==>");
	        
	        // creating the virtualizer
	        JRFileVirtualizer virtualizer = new JRFileVirtualizer(300, "/tmp/"); 
	        // Pass virtualizer object throw parameter map
	        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer); 
	        
	      //Preenche o relatório e exibe numa GUI
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parameters, new JRBeanCollectionDataSource(dataList));	        
	        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, parameters, new JRBeanCollectionDataSource(dataList));
	      
	        //TODO: Esto es manda a imprimir de frente, si le ponen false de frente manda a la impresora por defecto
	        //Si le ponen true les muestra la pantalla de seleccion de impresora
	        JasperPrintManager.printPage(jasperPrint, 0, false);
	        
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        JRPdfExporter jRPdfExporter = new JRPdfExporter();
	        jRPdfExporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
	        jRPdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	        jRPdfExporter.exportReport();
	        byte[] bytes = byteArrayOutputStream.toByteArray();
	        jRPdfExporter = null;
	        return bytes;
	        
	        
	     
	        
	    }
	    
	    
}

package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.joda.time.DateTime;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import datas.OfertaData;

public class PdfMaker {

	private static PdfMaker maker;
	
	private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,Font.BOLD);
	private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 9,Font.BOLD);
	
	private String userHome = System.getProperty("user.home");
	
	public static PdfMaker getMaker(){
		if (maker == null)
			maker = new PdfMaker();
		
		return  maker;
		
	}
	
	private PdfMaker(){
		
	}
	
	public void create(List<OfertaData> ofertas, DateTime date) throws FileNotFoundException, DocumentException{
		
		Document document = new Document();
		
		File dir = new File(userHome + File.separatorChar + "Registrador de ofertas - PDFs" + File.separatorChar + date.toString("yyyy_MM_dd-") + DateTime.now().toString("yyyy_MM_dd@hh_mm_ss") + ".pdf");
		
		if(!dir.getParentFile().exists())
			dir.getParentFile().mkdirs();
		
		PdfWriter.getInstance(document, new FileOutputStream(dir));
        
        document.open();
       
        addTitlePage(document);
        
        document.add(createFirstTable(ofertas));
        
        
        document.close();
	}
	
	private void addTitlePage(Document document)
	        throws DocumentException {
		
		Paragraph titulo = new Paragraph();

		String hoy= DateTime.now().toString("dd-MM-yyyy");
		
		titulo.add(new Paragraph("Comprobante asignación sala de ensayo", catFont));
		
		titulo.add(new Paragraph("Se extiende comprobante a: " + System.getProperty("user.name") + ", el dia " + hoy, smallBold));
		
		titulo.add(new Paragraph(" "));
		
		document.add(titulo);
	
	}
		    
	public PdfPTable createFirstTable(List<OfertaData> ofertas) {
	    	
		PdfPTable tabla = new PdfPTable(5);
		String [] campos = {"Nombre", "DNI", "Telefono", "Horario" , "Monto"};
		PdfPCell cell;
		
		for (String campo : campos) {
			cell = new PdfPCell(new Phrase(campo));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setColspan(1);
			tabla.addCell(cell);
		}
		
		for (OfertaData oferta : ofertas) {
			tabla.addCell(oferta.getNombre() + " " + oferta.getApellido());
			tabla.addCell(oferta.getDNI() + "");
			tabla.addCell(oferta.getTelefono() + "");
			tabla.addCell(oferta.getInicio().toString("HH:mm") + " a " + oferta.getFin().toString("HH:mm"));
			tabla.addCell(oferta.getPrecio() + "$");
		}
		
		return tabla;
	}
	
}

package proc;

import java.io.File;
import java.util.List;

public class PdfMaker {

	private static PdfMaker maker;
	
	public static PdfMaker getMaker(){
		if (maker == null)
			maker = new PdfMaker();
		
		return  maker;
		
	}
	
	private PdfMaker(){
		
	}
	
	public void create(List<OfertaData> ofertas, File file){
		
	}
	
}

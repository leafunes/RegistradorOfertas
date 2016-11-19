package currents;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import datas.OfertaData;

public class CurrentSolutionsTest {
	
	File testDir = new File("Tests" + File.separator + "solutions");
	CurrentSolutions current = CurrentSolutions.getCurrent(testDir);
	DateTime now = DateTime.now();
	
	OfertasParser parser = new OfertasParser();
	
	private List<OfertaData> getlistOfData(){
		
		return parser.parseList("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)"
				+ "y belen, devito, 40345678, belu@ungs.com, 98754689, 12/04/2016, 08:00, 09:00, 150, (guitarra, microfono)"
				+ "y javier, marenco, 56734567, jmarenco@gmail.com, 12346578, 12/04/2016, 13:00, 22:00, 9000, (guitarra, bajo, platillos)");
		
	}
	
	@Before
	public void cleanFiles(){
		if(testDir.list() == null)
			return;
		
		
		for (String s : testDir.list()) {
			File currentFile = new File(testDir,s);
			currentFile.delete();
		}
		
	}

	@Test
	public void getAllFilesOfTest() {
		
		List<OfertaData> toSave = getlistOfData();
		String start = DateTime.now().toString("yyyy/MM/dd"); 
		
		for (int i = 0; i < 5; i++) current.saveToFile(now, toSave);
		
		List<File> files = current.getAllFilesOf(now);
		
		//Es lo unico que se puede testear, ya que los nombres de los archivos son dinamicos
		//y contienen informacion de con que fecha estan relacionados
		//y del momento en el que se crearon
		assertEquals(5, files.size());
		
		for (File file : files) file.getName().startsWith(start);
		
	}
	
	@Test
	public void getOfertasOfTest(){
		
		List<OfertaData> toSave = getlistOfData();
		
		current.saveToFile(now, toSave);
		File file = current.getAllFilesOf(now).get(0);
		
		List<OfertaData> solution = current.getOfertasOf(file);
		
		assertEquals(toSave.size(), solution.size());
		
		assertTrue(toSave.containsAll(solution));
		assertTrue(solution.containsAll(toSave));
		
	}

}

package currents;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datas.EquipData;

public class CurrentEquipamentoTest {
	
	CurrentEquipamento current = CurrentEquipamento.getCurrent(new File("Tests" + 
																	File.separatorChar 
																	+ "equipamentoTest.json"));
	@Before
	public void clearData(){
		
		File file = new File("Tests" + 
					File.separatorChar 
					+ "equipamentoTest.json");
		file.delete();
		
		
	}
	
	private List<EquipData> getlistOfData(){
		List<EquipData> ret = new ArrayList<>();
		
		EquipData data1 = new EquipData("equip1");
		EquipData data2 = new EquipData("equip2");
		EquipData data3 = new EquipData("equip3");
		EquipData data4 = new EquipData("equip4");
		
		ret.add(data1);
		ret.add(data2);
		ret.add(data3);
		ret.add(data4);
		
		return ret;
	}
	
	@Test
	public void getEquipamentoTest() {
		List<EquipData> equip = current.getEquipamento();
		
		assertEquals(0, equip.size());
		
		current.putEquipamento(new EquipData("hola"));
		
		equip = current.getEquipamento();
		
		assertEquals(1, equip.size());
		
	}
	
	@Test
	public void putEquipamentoTest() {
		
		List<EquipData> list = getlistOfData();
		
		for (EquipData equipData : list) {
			current.putEquipamento(equipData);
		}
		
		List<EquipData> dataLoaded = current.getEquipamento();
		
		assertEquals(4, dataLoaded.size());
		
		assertTrue(list.containsAll(dataLoaded));
		assertTrue(dataLoaded.containsAll(list));
		
	}
	
	@Test
	public void removeEquipamentoTest(){
		
		List<EquipData> list = getlistOfData();
		
		for (EquipData equipData : list) {
			current.putEquipamento(equipData);
		}
		
		EquipData toRemove = list.get(0);
		EquipData notToRemove = list.get(1);
		
		current.removeEquipamento(toRemove);
		
		List<EquipData> dataLoaded = current.getEquipamento();
		
		assertEquals(3, dataLoaded.size());
		assertFalse(dataLoaded.contains(toRemove));
		assertTrue(dataLoaded.contains(notToRemove));
		
	}

}

package RapidoYFurioso;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AristaTest {
	private Arista arista;
	
	
	@Before
	public void crearArista(){
		arista = new Arista(1,2,4,false);
	}
	
	@Test
	public void getRecorridoTest(){
		assertEquals(false, arista.FueRecorrido());
	}
	
	@Test
	public void fueRecorridoTest(){
		arista.recorrer();
		
		assertEquals(true, arista.FueRecorrido());
	}
	
	@Test
	public void acumularPesoTest(){
		Arista aristaAuxiliar = new Arista(2,3,10,false);
		
		arista.acumularPesoCon(aristaAuxiliar);
		
		assertEquals(14, arista.getPeso());
	}
	
	@Test
	public void acumularPeajesTest(){
		Arista arista1 = new Arista(1,2,3,true);
		Arista arista2 = new Arista(1,2,3,true);
		
		arista2.acumularPesoCon(arista1);
		
		assertEquals(6,arista2.getPeso());
	}
	
	@Test
	public void asignarAnteriorDeTest(){
		Arista expected = new Arista(1,2,3,true);
		Arista actual = new Arista(3,4,5,false);
		
		expected.asignarAnteriorDe(actual);
		
		assertEquals(expected, actual.getAnterior());
	}
	
	@Test
	public void EqualsTest(){
		Arista expected = new Arista(1,2,4,false);
		
		assertEquals(expected, arista);
	}

	
}

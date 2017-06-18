package RapidoYFurioso;

/**
 * @author USER
 *
 */
public class Arista {
	private Integer inicio;
	private Integer destino;
	private int peso;
	private boolean fueRecorrido;
	private boolean peaje;
	private Arista anterior;
	private int peajesVisitados;
	
	public Arista(){
		fueRecorrido = false;
	}
	
	public Arista(Integer Inicio, Integer Destino, int Peso, boolean Peaje){
		agregar(Inicio, Destino, Peso, Peaje);
	
	}

	public void agregar(Integer Inicio, Integer Destino, int Peso, boolean Peaje) {
		inicio = Inicio;
		destino = Destino;
		peso = Peso;
		peaje = Peaje;
		
		if (tienePeaje())
			peajesVisitados++;
	}
	
	public void recorrer(){
		fueRecorrido = true;
	}
	
	void limpiar(){
		fueRecorrido = false;
	}
	
	public void acumularPesoCon(Arista arista){
		int pesoAcumulado = this.peso + arista.peso;
		peso = pesoAcumulado;
	}
	
	public void acumularPeajescon(Arista arista){
		int peajesAcumulados = this.peajesVisitados + arista.peajesVisitados;
		peajesVisitados = peajesAcumulados;
	}
	
	public void asignarAnteriorDe(Arista menor) {
		menor.anterior = this;
	}
	
	public Integer getInicio() {
		return inicio;
	}
	
	public Integer getDestino() {
		return destino;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public int getPeajesVisitados() {
		return peajesVisitados;
	}
	
	public boolean FueRecorrido() {
		return fueRecorrido;
	}
	
	public boolean tienePeaje() {
		return peaje;
	}
	
	public Arista getAnterior(){
		return anterior;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) 
			return true;
		if (obj == null) 
			return false;
		if (getClass() != obj.getClass()) 
			return false;
		
		final Arista otra = (Arista) obj;
		if (inicio != otra.getInicio()) 
			return false;
		if (destino != otra.getDestino())
			return false;
		if (peso != otra.getPeso())
			return false;
		if (fueRecorrido != otra.FueRecorrido())
			return false;
		if (peaje != otra.tienePeaje())
			return false;
		
		return true;
	}
	
	@Override	
	public String toString(){
		return "[ " +inicio +", " +destino +" ]";
	}

	
	
	
	
}

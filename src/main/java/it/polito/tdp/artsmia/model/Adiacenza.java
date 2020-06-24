package it.polito.tdp.artsmia.model;

public class Adiacenza {
	private ArtObject o1;
	private ArtObject o2;
	private Double peso;
	/**
	 * @param o1
	 * @param o2
	 * @param peso
	 */
	public Adiacenza(ArtObject o1, ArtObject o2, Double peso) {
		super();
		this.o1 = o1;
		this.o2 = o2;
		this.peso = peso;
	}
	public ArtObject getO1() {
		return o1;
	}
	public void setO1(ArtObject o1) {
		this.o1 = o1;
	}
	public ArtObject getO2() {
		return o2;
	}
	public void setO2(ArtObject o2) {
		this.o2 = o2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	
	
}

package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco>{

	private Integer id1;
	private Integer id2;
	private Integer peso;
	

	public Arco(Integer id1, Integer id2, Integer peso) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}


	public Integer getId1() {
		return id1;
	}


	public void setId1(Integer id1) {
		this.id1 = id1;
	}


	public Integer getId2() {
		return id2;
	}


	public void setId2(Integer id2) {
		this.id2 = id2;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Arco o) {
		return o.getPeso()-this.peso;
	}
	
	
}

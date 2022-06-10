package it.polito.tdp.itunes.model;

public class Adiacenze {
	private Track t1;
	private Track t2;
	private int delta;
	public Adiacenze(Track t1, Track t2, int delta) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.delta = delta;
	}
	public Track getT1() {
		return t1;
	}
	public void setT1(Track t1) {
		this.t1 = t1;
	}
	public Track getT2() {
		return t2;
	}
	public void setT2(Track t2) {
		this.t2 = t2;
	}
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}
	@Override
	public String toString() {
		return this.t1.getName()+" *** "+ this.t2.getName()+" Delta: "+ this.delta;
	}
	
	

}

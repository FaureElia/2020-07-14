package it.polito.tdp.PremierLeague.model;

public class Coppia implements Comparable<Coppia> {
	private Team s1;
	private Team s2;
	private int differenzaPunti;
	
	public Coppia(Team s1, Team s2, int differenzaPunti) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.differenzaPunti = differenzaPunti;
	}

	
	
	public Team getS1() {
		return s1;
	}



	public void setS1(Team s1) {
		this.s1 = s1;
	}



	public Team getS2() {
		return s2;
	}



	public void setS2(Team s2) {
		this.s2 = s2;
	}



	public int getDifferenzaPunti() {
		return differenzaPunti;
	}



	public void setDifferenzaPunti(int differenzaPunti) {
		this.differenzaPunti = differenzaPunti;
	}



	@Override
	public String toString() {
		return s1+" "+s2+" differenza punti: "+this.differenzaPunti;
	}

	@Override
	public int compareTo(Coppia o) {
		
		return this.differenzaPunti-o.differenzaPunti;
	}
	
	
	

}

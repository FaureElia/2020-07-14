package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Simulazione {
	
	private PremierLeagueDAO dao;
	
	//stato del sistema
	private Map<Match,Integer> mappaReporterPartite;
	private Map<Team,Integer> mappaReporterPerSquadra;
	private Map<Integer, Team> mappaSquadre;
	//input
	private int N;
	private int X;
	private Graph <Team, DefaultWeightedEdge> grafo;
	
	//coda eventi 
	private PriorityQueue<Event> queue;
	//output
	int insoddisfatti=0;
	
	
	
	public Simulazione() {
		this.dao=new PremierLeagueDAO();
		this.queue=new PriorityQueue();
		this.mappaReporterPartite=new HashMap<>();
		this.mappaReporterPerSquadra=new HashMap<>();
		this.mappaSquadre=new HashMap<Integer,Team>();
	}
	
	public void initialize(int N,int X, Set<Team> teams, Graph<Team, DefaultWeightedEdge> grafo) {
		this.N=N;
		this.X=X;
		List<Match> partite=new ArrayList<>(this.dao.listAllMatches());
		Set<Team> squadre=teams;
		this.grafo=grafo;
		for (Match m:partite) {
			this.mappaReporterPartite.put(m, 0);	
		}
		for (Team t:squadre) {
			this.mappaSquadre.put(t.getTeamID(), t);
			this.mappaReporterPerSquadra.put(t, N);	
		}
		System.out.println(this.mappaReporterPerSquadra);
		
		for (Match m: partite) {
			this.queue.add(new Event(m,m.getDate()));
		}
		this.run();
		
		
		System.out.println("numero paritte: "+this.mappaReporterPartite.size());
		
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			gestisci(e);
		}
		System.out.println(this.insoddisfatti);
		int somma=0;
		for(Integer i: this.mappaReporterPartite.values()) {
			somma+=i;
		}
		System.out.println(somma/this.mappaReporterPartite.size());
	}
	
	public void gestisci(Event e) {
		Match m=e.getPartita();
		LocalDateTime data=e.getData();
		Team vincente=null;
		Team perdente=null;
		if(m.getReaultOfTeamHome()==-1) {
			vincente=this.mappaSquadre.get(m.getTeamAwayID());
			perdente=this.mappaSquadre.get(m.getTeamHomeID());
		}else if(m.getReaultOfTeamHome()==1) {
			perdente=this.mappaSquadre.get(m.getTeamAwayID());
			vincente=this.mappaSquadre.get(m.getTeamHomeID());
		}
		
		int totaleReporter=this.mappaReporterPerSquadra.get(this.mappaSquadre.get(m.teamHomeID))+this.mappaReporterPerSquadra.get(this.mappaSquadre.get(m.teamAwayID));
		this.mappaReporterPartite.put(m,totaleReporter );
		if(totaleReporter<this.X) {
			System.out.println(m);
			System.out.println(this.mappaReporterPerSquadra.get(this.mappaSquadre.get(m.teamHomeID)));
			System.out.println(this.mappaReporterPerSquadra.get(this.mappaSquadre.get(m.teamAwayID)));
			insoddisfatti++;
			
		}
		
		if(perdente==null && vincente==null) {
			return;
		}
		
		double random=Math.random();
		//gestisco vincente
		if(random<0.5 && this.mappaReporterPerSquadra.get(vincente)>0) {
			Team nuovaSquadra=CasualiBattutaDa(vincente);
			if(nuovaSquadra!=null) {
				this.mappaReporterPerSquadra.put(nuovaSquadra,this.mappaReporterPerSquadra.get(nuovaSquadra)+1);
				this.mappaReporterPerSquadra.put(vincente,this.mappaReporterPerSquadra.get(vincente)-1);
			}	
			}
		
		//gestisco perdente
		double random2=Math.random();
		if(random<0.2 && this.mappaReporterPerSquadra.get(perdente)>0) {
			Team nuovaSquadra=CasualiBattute(perdente);
			if(nuovaSquadra!=null) {
				int numeroReporter=(int)(Math.random()*this.mappaReporterPerSquadra.get(perdente))+1;
				this.mappaReporterPerSquadra.put(perdente,this.mappaReporterPerSquadra.get(perdente)-numeroReporter);
				this.mappaReporterPerSquadra.put(nuovaSquadra,this.mappaReporterPerSquadra.get(nuovaSquadra)+numeroReporter);
			}
		}
	}
	
	public Team CasualiBattutaDa(Team team) {
		List<Coppia> piuforti=new ArrayList<Coppia>();
		for (DefaultWeightedEdge e:this.grafo.incomingEdgesOf(team)) {
			piuforti.add(new Coppia(team,Graphs.getOppositeVertex(this.grafo, e, team),(int)this.grafo.getEdgeWeight(e)));	
		}	
		Team ritorno=null;
		Collections.sort(piuforti);
		if(piuforti.size()!=0) {
			Coppia c=piuforti.get((int)Math.random()*(piuforti.size()));
			ritorno=c.getS2();
		}
		
		return ritorno;
	}
	
	public Team CasualiBattute(Team team) {
		List<Coppia> battute=new ArrayList<Coppia>();
		for (DefaultWeightedEdge e:this.grafo.outgoingEdgesOf(team)) {
			battute.add(new Coppia(team,Graphs.getOppositeVertex(this.grafo, e, team),(int)this.grafo.getEdgeWeight(e)));
		}
		Collections.sort(battute);
		
		Team ritorno=null;
		if(battute.size()!=0) {
			Coppia c=battute.get((int)Math.random()*(battute.size()));
			ritorno=c.getS2();
		}
		
		return ritorno;
	}
	
	
	
}

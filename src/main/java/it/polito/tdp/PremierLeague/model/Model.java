package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph <Team, DefaultWeightedEdge> graph;
	
	public Model(){
		this.dao=new PremierLeagueDAO();
		
	}
	
	

	public List<Team> creaGrafo() {
		this.graph=new SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Team> vertici=this.dao.listAllTeams();
		
		Graphs.addAllVertices(this.graph, vertici);
		System.out.println("vertici: "+this.graph.vertexSet().size());
		
		for (Team t1: this.graph.vertexSet()) {
			for (Team t2: this.graph.vertexSet()) {
				if(t1.teamID>t2.teamID) {
					int differenza=t1.getPunti()-t2.getPunti();
					if(differenza>0) {
						Graphs.addEdgeWithVertices(this.graph, t1, t2, differenza);
					}else if(differenza<0) {
						Graphs.addEdgeWithVertices(this.graph, t2, t1, -differenza);
					}
					
				}
			}
		}
		
		System.out.println("vertici: "+this.graph.vertexSet().size());
		System.out.println("archi: "+this.graph.edgeSet().size());
		return vertici;
		
		
		
		
		
	}



	public List<Coppia> BattutaDa(Team team) {
		List<Coppia> piuforti=new ArrayList<Coppia>();
		for (DefaultWeightedEdge e:this.graph.incomingEdgesOf(team)) {
			piuforti.add(new Coppia(team,Graphs.getOppositeVertex(this.graph, e, team),(int)this.graph.getEdgeWeight(e)));	
		}	
		Collections.sort(piuforti);
		return piuforti;
	}
	
	public List<Coppia> Battute(Team team) {
		List<Coppia> battute=new ArrayList<Coppia>();
		for (DefaultWeightedEdge e:this.graph.outgoingEdgesOf(team)) {
			battute.add(new Coppia(team,Graphs.getOppositeVertex(this.graph, e, team),(int)this.graph.getEdgeWeight(e)));
		}
		Collections.sort(battute);
		return battute;
	}



	public void doSimulazione(int n, int x) {
		Simulazione s =new Simulazione();
		s.initialize(n, x,this.graph.vertexSet(),this.graph);
		
	}
	
}

package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Track, DefaultWeightedEdge> grafo;
	private Map<Integer, Genre> idMapGenre;
	private List<Track> tracks;
	
	private List<Track> best;


	public Model() {
		this.dao= new ItunesDAO();
		this.idMapGenre= new HashMap<>();
		
	}
	
	public List<Genre> getAllGenre (){
		
		return this.dao.getAllGenres();
	}
	
	
	public void creaGrafo(Genre genere) {
		
		this.grafo= new SimpleWeightedGraph<Track, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		tracks= new ArrayList<Track>(dao.getVertici(genere));
		Graphs.addAllVertices(this.grafo, tracks);
		
		for(Track t1: tracks) {
			for(Track t2: tracks) {
				
				int durata= t1.getMilliseconds()-t2.getMilliseconds();
				if(t1.getTrackId()!=t2.getTrackId() && t1.getMediaTypeId()==t2.getMediaTypeId() && !(this.grafo.containsEdge(t1, t2))) {
					
					if(durata>0) {
						Graphs.addEdge(this.grafo, t1, t2, durata);
						Adiacenze a= new Adiacenze(t1, t2, durata);
					}else if( durata<0) {
						Graphs.addEdge(this.grafo, t1, t2, -durata);
						Adiacenze a= new Adiacenze(t1, t2, -durata);
					}
					
				}
			}
		}
	}
		

		public String nVertici() {
			return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
		}
		
		public String nArchi() {
			return "#archi: "+ this.grafo.edgeSet().size()+"\n";
		}
		
		public List<Adiacenze> deltaMax(){
			
			int max=0;
			Adiacenze a=null;
			List<Adiacenze> list= new LinkedList<Adiacenze>();
			
			for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
				if((int)this.grafo.getEdgeWeight(e)>max) {
					max=(int) this.grafo.getEdgeWeight(e);
				}
			}
			
			for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
				
				if((int) this.grafo.getEdgeWeight(e)==max) {
				Track t1= this.grafo.getEdgeSource(e);
				Track t2= this.grafo.getEdgeTarget(e);
				a= new Adiacenze(t1, t2,(int)this.grafo.getEdgeWeight(e));
				list.add(a);
				}
				
			}
			return list;
			
		}
		
		public List<Track> getTracksGrafo(){
			return this.tracks;
		}
		
		public List<Track> listaCanzoniPrefe(Track c, int maxBytes) {
			
			List<Track> parziale= new LinkedList<Track>();
			
			parziale.add(c);
			int bytesOcc=c.getBytes();
			int bestBytes=0;
			best= new LinkedList<Track>(parziale);
			cerca(parziale, maxBytes, bytesOcc, bestBytes);
			
			
			return best;
			
		}

		private void cerca(List<Track> parziale, int maxBytes, int bytesOcc, int bestBytes) {
			
			
				if(parziale.size()>this.best.size()) {
					bestBytes= bytesOcc;
				
			
			}
			for(Track t: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
				parziale.add(t);
				bytesOcc+=t.getBytes();
				if(!parziale.contains(t) && bytesOcc<= maxBytes) {
					cerca(parziale, maxBytes, bytesOcc, bestBytes);
				
				}
				parziale.remove(t);
				bytesOcc-=t.getBytes();
			}

			
			}
		
		
	
	
	
	
}

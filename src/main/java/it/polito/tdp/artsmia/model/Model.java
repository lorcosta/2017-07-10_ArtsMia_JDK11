package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private SimpleWeightedGraph<ArtObject,DefaultWeightedEdge> graph;
	private ArtsmiaDAO dao=new ArtsmiaDAO();
	private Map<Integer,ArtObject> idMapObject;
	private List<ArtObject> bestCammino;
	private Double bestPeso=0.0;
	
	public void creaGrafo() {
		this.idMapObject=new HashMap<>();
		List<ArtObject> objects=dao.listObjects(idMapObject);
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, objects);
		List<Adiacenza> adiacenze=dao.getAdiacenze(idMapObject);
		for(Adiacenza a:adiacenze) {
			if(this.graph.containsVertex(a.getO1()) && this.graph.containsVertex(a.getO2()) &&
					!this.graph.containsEdge(a.getO1(), a.getO2()) && !a.getO1().equals(a.getO2())) {
				Graphs.addEdgeWithVertices(this.graph, a.getO1(), a.getO2(), a.getPeso());
			}
		}
		
	}
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	public boolean test(Integer objectId) {
		return dao.test(objectId);
	}
	public List<ArtObject> componenteConnessa(Integer objectId) {
		List<ArtObject> visita=new ArrayList<>();
		BreadthFirstIterator<ArtObject, DefaultWeightedEdge> bft= new BreadthFirstIterator<>(this.graph,idMapObject.get(objectId));
		while(bft.hasNext()) {
			visita.add(bft.next());
		}
		return visita;
	}
	public List<ArtObject> cercaOggetti(Integer objectId,Integer lun) {
		List<ArtObject> parziale=new ArrayList<>();
		parziale.add(idMapObject.get(objectId));
		ricorsione(parziale,lun);
		Collections.sort(bestCammino);
		return bestCammino;
		
	}
	private void ricorsione(List<ArtObject> parziale,Integer lun) {
		if(parziale.size()==lun) {
			if(bestPeso<calcolaPeso(parziale)) {
				bestPeso=calcolaPeso(parziale);
				bestCammino=new ArrayList<>(parziale);
			}
		}
		for(ArtObject o:Graphs.neighborListOf(this.graph,parziale.get(parziale.size()-1))) {
			if(o.getClassification().equals(parziale.get(0).getClassification()) && !parziale.contains(o)) {
				parziale.add(o);
				ricorsione(parziale,lun);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	private Double calcolaPeso(List<ArtObject> parziale) {
		Double peso=0.0;
		for(int i=1;i<parziale.size();i++) {
			peso+=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return peso;
	}
	 public Double getPesoCammino() {
		 return this.bestPeso;
	 }
}

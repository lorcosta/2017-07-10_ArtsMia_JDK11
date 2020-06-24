package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
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
}

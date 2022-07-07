package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	private ArtsmiaDAO dao;
	
	private List<String> ruoli;
	
	private List<Integer> vertici;
	
	private List<Arco> archi;
	
	private List<Integer> best;
	
	private int max;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.ruoli = new ArrayList<String>();
		this.ruoli = dao.getAllRuoli();
		
	}
	
	public void creaGrafo(String ruolo) {
		
		this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
				
		this.vertici = dao.getAllVertici(ruolo);
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		System.out.println("#VERTICI: " + this.grafo.vertexSet().size());
		
		this.archi = new ArrayList<Arco>();
		this.archi = dao.getAllArchi(ruolo);
		for(Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getId1(), a.getId2(), a.getPeso());
		}
	}
	
	public List<Arco> getConnessi(){
		List<Arco> connessi = new ArrayList<Arco>(this.archi);
		Collections.sort(connessi);
		return connessi;
	}
	
	public boolean isCreato() {
		if(this.grafo == null)
			return false;
		else
			return true;
	}

	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public List<String> getRuoli() {
		return ruoli;
	}

	public List<Integer> getVertici() {
		return vertici;
	}
	
//	a. Permettere all’utente di inserire nella casella di testo “Artista (id)” il numero identificativo di un artista 
//	(“artist_id”).
	
//	b. Alla pressione del bottone “Calcola Percorso”, si verifichi che il numero inserito sia corretto. In caso 
//	affermativo, si determini il cammino più lungo che parte dall’artista selezionato e che connette gli artisti con 
//	un ugual numero di esposizioni condivise. Precisamente deve essere trovato il cammino tra i vari artisti che 
//	comprenda solamente archi con ugual peso e che non comprenda cicli né vertici ripetuti.

//	c. Si stampi il percorso così ottenuto, elencando gli artisti coinvolti ed il numero di esposizioni per cui il 
//	percorso risulta massimo.
	
	
	public List<Integer> cercaCammino(Integer id) {
		
		this.best = new ArrayList<Integer>();
		
		this.max = 0;
		
		List<Integer> parziale = new ArrayList<>();
		
		parziale.add(id);
		
		cerca(parziale, -1);
		
		return best;
	}

	private void cerca(List<Integer> parziale, int peso) {
		
//		mi prendo l'ultimo inserito
		Integer ultimo = parziale.get(parziale.size()-1);
//		Ottengo i vicini
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, ultimo);
//		se il peso è uguale a -1 (ovvero sono alla prima iterazione) provo tutte le diverse strade
//		poi quando il peso non sarà più uguale a meno continui vedendo i percorsi che hanno 
//		lo stesso peso
		for(Integer vicino : vicini) {
			if(!parziale.contains(vicino) && peso == -1) {
				parziale.add(vicino);
				cerca(parziale, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			}else {
//				qua cerco i percorsi che hanno lo stesso peso 
				if(!parziale.contains(vicino) && this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)) == peso) {
					parziale.add(vicino);
					cerca(parziale, peso);
					parziale.remove(vicino);
				}
			}
		}
		
		if(parziale.size() > best.size())
			this.best = new ArrayList<Integer>(parziale);
	}

}

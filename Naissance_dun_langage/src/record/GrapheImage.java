package record;

import java.util.ArrayList;
import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import architecture.Graphe;
import architecture.Individu;

public class GrapheImage {

	private final Graph graph;
	private ArrayList<Individu> individus;

	public GrapheImage() {
		graph = null;
	}

	public GrapheImage(Graphe graphe) {
		individus = graphe.lireGraphe();
		graph = new SingleGraph(graphe.lireNom());
	}

	public void imprimerGraphe() {

		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.setAutoCreate(true);
		graph.setStrict(false);
		graph.display();

		for (int numIndividu = 0; numIndividu < individus.size(); numIndividu++) {

			int numVoisins = individus.get(numIndividu).lireVoisins().size();
			String curNumIndividu = Character.toString(individus.get(numIndividu).lireLettre());

			for (int numVoisin = 0; numVoisin < numVoisins; numVoisin++) {

				String curNumVoisin = Character
						.toString(individus.get(numIndividu).lireVoisins().get(numVoisin).lireLettre());
				graph.addEdge(curNumIndividu + curNumVoisin, curNumIndividu + "  Testing", curNumVoisin + "  Testing");
			}
		}

		for (Node node : graph) {
			node.addAttribute("ui.label", node.getId());
		}

		// explore(graph.getNode("0"));
	}

	public void explore(Node source) {
		Iterator<? extends Node> k = source.getBreadthFirstIterator();

		while (k.hasNext()) {
			Node next = k.next();
			next.setAttribute("ui.class", "marked");
			sleep();
		}
	}

	protected void sleep() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}

	protected String styleSheet = "node {" + "	fill-color: black;" + "}" + "node.marked {" + "	fill-color: red;" + "}";
}

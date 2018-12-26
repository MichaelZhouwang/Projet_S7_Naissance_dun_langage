package ihm;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import systeme.Individu;
import systeme.Systeme;
import systeme.Voisin;

public class GrapheSysteme extends Pane {
	private final int rayonSommet = 20;
	
	private ArrayList<Individu> individus;
	private double width;
	private double height;		
	private double theta;
	private double rayonGraphe;

	public GrapheSysteme(ArrayList<Individu> individus, double width, double height) {
		this.individus = individus;
		this.width = width;
		this.height = height;
		
		theta = 2 * Math.PI / individus.size();
		rayonGraphe = Math.min(width / 2, height / 2);
	}
	
	public HashMap<Individu, Circle> genererSommets() {
		HashMap<Individu, Circle> sommets = new HashMap<Individu, Circle>();
		
		for (int i = 0; i < individus.size(); i++) {
			Individu individu = individus.get(i);
			double x = width / 2 + (rayonGraphe - rayonSommet) * Math.cos(theta * i);
			double y = height / 2 + (rayonGraphe - rayonSommet) * Math.sin(theta * i);
			Circle sommet = new Circle(x, y, rayonSommet);

			sommet.getStyleClass().add("sommet");
			sommet.getStyleClass().add(individu.toString().toLowerCase().replace(' ', '-'));

			sommets.put(individu, sommet);
		}
		
		return sommets;
	}
	
	public ArrayList<Arrow> genererArcs() {
		ArrayList<Arrow> arcs = new ArrayList<Arrow>();
		
		for (int i = 0; i < individus.size(); i++) {
			int individuID = individus.get(i).lireID();
			for (Voisin voisin : individus.get(i).obtenirVoisins()) {
				int voisinID = voisin.obtenirIndividu().lireID();
				
				double x1 = width / 2 + (rayonGraphe - 20) * Math.cos(theta * (individuID - 1));
				double y1 = height / 2 + (rayonGraphe - 20) * Math.sin(theta * (individuID - 1));
				double x2 = width / 2 + (rayonGraphe - 20) * Math.cos(theta * (voisinID - 1));
				double y2 = height / 2 + (rayonGraphe - 20) * Math.sin(theta * (voisinID - 1));
				
				double newX1, newY1, newX2, newY2;
				
				// Ce sont les arcs extérieurs du graphe
				if ((individuID + 1)  % Systeme.lireNombreIndividus() == voisinID % Systeme.lireNombreIndividus()
						|| individuID % Systeme.lireNombreIndividus() == (voisinID + 1)  % Systeme.lireNombreIndividus()) {
					newX1 = (1 - 0.8) * x1 + 0.8 * x2;
					newY1 = (1 - 0.8) * y1 + 0.8 * y2;
					newX2 = (1 - 0.2) * x1 + 0.2 * x2;
					newY2 = (1 - 0.2) * y1 + 0.2 * y2;
				}
				else {
					newX1 = (1 - 0.9) * x1 + 0.9 * x2;
					newY1 = (1 - 0.9) * y1 + 0.9 * y2;
					newX2 = (1 - 0.1) * x1 + 0.1 * x2;
					newY2 = (1 - 0.1) * y1 + 0.1 * y2;
				}
				
				arcs.add(new Arrow(newX1, newY1, newX2, newY2));
			}
		}
		
		return arcs;
	}
}

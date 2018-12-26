package ihm;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import beans.LemmeMemorise;
import beans.Parametre;
import evenement.enumeration.IssueEvenement;
import evenement.enumeration.TypeEvenement;
import ihm.portee.Portee;
import ihm.portee.PorteeIndividu;
import ihm.portee.PorteeSysteme;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lexique.Lemme;
import lexique.OccurrenceLemme;
import systeme.Individu;
import systeme.Systeme;
import temps.Date;

public class Controleur implements Initializable {	

	@FXML
	private ChoiceBox<Portee> choixPortee;
	private Portee portee;
	
	@FXML
	private ChoiceBox<TypeEvenement> choixTypeEvenementOccurrences;
	private TypeEvenement typeEvenementOccurrences;
	
	@FXML
	private Pane grapheSysteme;
	
	@FXML
	private PieChart diagrammeLexique;
	
	@FXML
    private StackedBarChart<String, Number> diagrammeEvolutionLexique;
	
	@FXML
    private StackedBarChart<String, Number> diagrammeEvolutionOccurrences;
    
	@FXML
	private TextFlow texteEvenements;
	
	@FXML
	private TableView<Parametre> tableParametres;

	@FXML
	private TableView<LemmeMemorise> tableLexique;

	@FXML
	private TableView<OccurrenceLemme> tableOccurrences;
	
	private ArrayList<Date> datesRepere;
	private Date dateDetailLexique;
	private Date dateDetailOccurrences;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		initialiserPortees();
		initialiserDates();
		initialiserTypesEvenement();
		
		creerGrapheSysteme();

		creerDiagrammeLexique();
		creerDiagrammeEvolutionLexique();
		remplirTableLexique();

		creerDiagrammeEvolutionOccurrences();
		remplirTableOccurrences();
		
		remplirTexteEvenements();
		remplirTableParametres();
	}
	
	public void lancerListeners() {
		choixPortee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Portee>() {
			@Override
			public void changed(ObservableValue<? extends Portee> observable, Portee anciennePortee, Portee nouvellePortee) {
				portee = choixPortee.getSelectionModel().getSelectedItem();
				creerDiagrammeLexique();
				creerDiagrammeEvolutionLexique();
				remplirTableLexique();
				remplirTexteEvenements();
				remplirTableParametres();
				creerDiagrammeEvolutionOccurrences();
				remplirTableOccurrences();
			}
		});
		
		choixTypeEvenementOccurrences.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TypeEvenement>() {
			@Override
			public void changed(ObservableValue<? extends TypeEvenement> observable, TypeEvenement ancienType, TypeEvenement nouveauType) {
				typeEvenementOccurrences = choixTypeEvenementOccurrences.getSelectionModel().getSelectedItem();
				creerDiagrammeEvolutionOccurrences();
				remplirTableOccurrences();
			}
		});
	}
	
	public void initialiserPortees() {
		ArrayList<Portee> portees = new ArrayList<Portee>();
		for (Individu individu : Systeme.obtenirIndividus()) {
			portees.add(new PorteeIndividu(individu));
		}
		portees.add(new PorteeSysteme());
		
		choixPortee.getItems().addAll(FXCollections.observableArrayList(portees));
		choixPortee.getSelectionModel().selectFirst();
		portee = choixPortee.getSelectionModel().getSelectedItem();
	}
	
	public void initialiserDates() {
		datesRepere = new ArrayList<Date>();
		int valeurDateActuelle = Systeme.lireDateHorloge().lireValeur();
		if (valeurDateActuelle <= 5) {
			for (int indice = 0; indice <= valeurDateActuelle; indice++) {
				datesRepere.add(new Date(indice));
			}
		}
		else {
			for (int indice = 0; indice < valeurDateActuelle; indice += valeurDateActuelle/5) {
				datesRepere.add(new Date(indice));
			}
			datesRepere.add(new Date(valeurDateActuelle));
		}
		
		dateDetailLexique = null;
		dateDetailOccurrences = null;
	}
	
	public void initialiserTypesEvenement() {
		choixTypeEvenementOccurrences.getItems().addAll(FXCollections.observableArrayList(TypeEvenement.values()));
		choixTypeEvenementOccurrences.getSelectionModel().selectFirst();
		typeEvenementOccurrences = choixTypeEvenementOccurrences.getSelectionModel().getSelectedItem();
	}
	
	public void creerGrapheSysteme() {
		GrapheSysteme graphe = new GrapheSysteme(Systeme.obtenirIndividus(), grapheSysteme.getPrefWidth(), grapheSysteme.getPrefHeight());
		grapheSysteme.getChildren().addAll(graphe.genererSommets().values());
		grapheSysteme.getChildren().addAll(graphe.genererArcs());
	}

	public void remplirTexteEvenements() {
		ArrayList<OccurrenceLemme> donneesTexteEvenements = portee.obtenirListeOccurrences(Systeme.lireDateHorloge(), TypeEvenement.QUELCONQUE);
		
		ObservableList<Text> textesEvenements = FXCollections.observableArrayList();
		textesEvenements.add(new Text(
				"===========================================================\n"
				+ "\t\t\t\t\t\tDebut de la simulation\n"
				+ "===========================================================\n"
		));
		
		for (OccurrenceLemme occurrence : donneesTexteEvenements) {
			String stringTypeEvenement = "";
			switch (occurrence.lireTypeEvenement()) {
				case EMISSION:
					break;
				case RECEPTION:
					stringTypeEvenement += "\t";
					break;
				case MEMORISATION:
					stringTypeEvenement += "\t\t";
					break;
				case ELIMINATION:
					stringTypeEvenement += "\t\t\t";
					break;
				case QUELCONQUE:
					break;
			}
			stringTypeEvenement += occurrence.lireTypeEvenement().toString();
			Text texteTypeEvenement = new Text(stringTypeEvenement);
			texteTypeEvenement.getStyleClass().add("label-" + occurrence.lireTypeEvenement().toString().toLowerCase());
			texteTypeEvenement.getStyleClass().add("label-type-evenement");
			
			String stringEvenement = " ("
				+ occurrence.lireIssueEvenement() + ") par "
				+ occurrence.lireIndividu().lireLettre() + " de "
				+ occurrence.lireLemme() + " a la date "
				+ occurrence.lireDate() + " [ev. "
				+ occurrence.lireID() + "]\n";
			
			Text texteEvenement = new Text(stringEvenement);

			textesEvenements.add(texteTypeEvenement);
			textesEvenements.add(texteEvenement);
		}
		
		textesEvenements.add(new Text(
			"===========================================================\n"
			+ "\t\t\t\t\t\tFin de la simulation (" + Systeme.lireTypeCritereArret() + " = " + Systeme.lireObjectifCritereArret() + ")\n"
			+ "==========================================================="
		));
		
		texteEvenements.getChildren().clear();
		ObservableList<Node> evenements = texteEvenements.getChildren(); 
		evenements.addAll(textesEvenements); 
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void remplirTableParametres() {
		ArrayList<Parametre> donneesTableParametres = portee.obtenirListeParametres();
		ObservableList<Parametre> obsListeParametres = FXCollections.observableArrayList(donneesTableParametres);

		((TableColumn)tableParametres.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Parametre, String>("nom"));
		((TableColumn)tableParametres.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Parametre, String>("valeur"));

		tableParametres.setItems(obsListeParametres);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void remplirTableLexique() {
		ArrayList<LemmeMemorise> listeLemmesMemorises = portee.obtenirListeLemmesMemorises(dateDetailLexique);
		ObservableList<LemmeMemorise> obsListeOccurrences = FXCollections.observableArrayList(listeLemmesMemorises);

		tableLexique.setPlaceholder(new Text("Cliquez sur une barre du graphe pour obtenir les details associes"));
		
		((TableColumn)tableLexique.getColumns().get(0)).setCellFactory(column -> {
		    return new TableCell<LemmeMemorise, Lemme>() {
		        @Override
		        protected void updateItem(Lemme item, boolean empty) {
		            super.updateItem(item, empty);
		            setAlignment(Pos.CENTER);
		            if (empty) {
		                setGraphic(null);
		            }
		            else {
		            	Rectangle rectangle = new Rectangle();
		                setGraphic(rectangle);
		                rectangle.getStyleClass().add(item.lireInitiateur().toString().toLowerCase().replace(' ', '-'));
		                rectangle.getStyleClass().add("origine");
		            }
		        }
		    };
		});
		((TableColumn)tableLexique.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<LemmeMemorise, Lemme>("lemme"));
		((TableColumn)tableLexique.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<LemmeMemorise, Lemme>("lemme"));
		((TableColumn)tableLexique.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<LemmeMemorise, Individu>("emetteur"));
		((TableColumn)tableLexique.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<LemmeMemorise, Individu>("recepteur"));
		((TableColumn)tableLexique.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<LemmeMemorise, Date>("dateMemorisation"));

		tableLexique.setItems(obsListeOccurrences);

		((TableColumn)tableLexique.getColumns().get(1)).setComparator(((TableColumn)tableLexique.getColumns().get(1)).getComparator().reversed());
		tableLexique.getSortOrder().add(((TableColumn)tableLexique.getColumns().get(1)));
	}

	public void creerDiagrammeLexique() {
		HashMap<Individu, Integer> donnees = portee.obtenirDonneesDiagrammeCompositionLexique(Systeme.lireDateHorloge());
		
		ObservableList<PieChart.Data> donneesDiagramme = FXCollections.observableArrayList();
		for (Individu individu : Systeme.obtenirIndividus()) {
			PieChart.Data donneeDiagramme = new PieChart.Data(individu.toString(), donnees.get(individu));
			donneesDiagramme.add(donneeDiagramme);
		}

		diagrammeLexique.getData().clear();
		diagrammeLexique.setData(donneesDiagramme);
		diagrammeLexique.setLabelsVisible(false);
		diagrammeLexique.setTitle("Composition finale du lexique (origines)");
	}
	
	public void creerDiagrammeEvolutionLexique() {
		diagrammeEvolutionLexique.getXAxis().setLabel("Date");
		diagrammeEvolutionLexique.getYAxis().setLabel("Composition");
		
		diagrammeEvolutionLexique.setTitle("Evolution des lexiques (origine)");
		diagrammeEvolutionLexique.setCategoryGap(64);

		List<String> datesRepereString = datesRepere.stream().map(Date::toString).collect(Collectors.toList());
		((CategoryAxis)diagrammeEvolutionOccurrences.getXAxis()).setCategories(FXCollections.<String>observableArrayList(datesRepereString));

		HashMap<Date, HashMap<Individu, Integer>> donnees = portee.obtenirDonneesDiagrammeEvolutionCompositionLexique(datesRepere);
		
		ObservableList<XYChart.Data<String, Number>> donneesDiagrammeEvolutionLexique = null;
		ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();

		for (Individu individu : Systeme.obtenirIndividus()) {
			donneesDiagrammeEvolutionLexique = FXCollections.observableArrayList();
			for (Date date : datesRepere) {
				donneesDiagrammeEvolutionLexique.add(new Data<String, Number>(date.toString(), donnees.get(date).get(individu)));
			}
			XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
			serie.setData(donneesDiagrammeEvolutionLexique);
			serie.setName(individu.toString());
			series.add(serie);
		}
		diagrammeEvolutionLexique.getData().clear();
		diagrammeEvolutionLexique.setData(series);
		
		for (Series<String, Number> _series : diagrammeEvolutionLexique.getData()) {
			for (XYChart.Data<String, Number> donnee : _series.getData()) {
				donnee.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent e) {
			        	dateDetailLexique = new Date(Integer.parseUnsignedInt(donnee.getXValue()));
			        	remplirTableLexique();
			        }
				});
			}
		}
	}
	
	public void creerDiagrammeEvolutionOccurrences() {
		diagrammeEvolutionOccurrences.getXAxis().setLabel("Date");
		diagrammeEvolutionOccurrences.getYAxis().setLabel("Occurrences");
		
		diagrammeEvolutionOccurrences.setTitle("Evolution des occurrences (" + typeEvenementOccurrences + ")");
		diagrammeEvolutionOccurrences.setCategoryGap(64);

		List<String> datesRepereString = datesRepere.stream().map(Date::toString).collect(Collectors.toList());
		((CategoryAxis)diagrammeEvolutionOccurrences.getXAxis()).setCategories(FXCollections.<String>observableArrayList(datesRepereString));

		HashMap<Date, HashMap<Individu, Integer>> donnees = portee.obtenirDonneesDiagrammeEvolutionOccurrences(datesRepere, typeEvenementOccurrences);
		ObservableList<XYChart.Data<String, Number>> donneesDiagrammeEvolutionOccurrences = null;
		ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
		for (Individu individu : Systeme.obtenirIndividus()) {
			donneesDiagrammeEvolutionOccurrences = FXCollections.observableArrayList();
			for (Date date : datesRepere) {
				donneesDiagrammeEvolutionOccurrences.add(new Data<String, Number>(date.toString(), donnees.get(date).get(individu)));
			}
			XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
			serie.setData(donneesDiagrammeEvolutionOccurrences);
			serie.setName(individu.toString());
			series.add(serie);
		}
		diagrammeEvolutionOccurrences.getData().clear();
		diagrammeEvolutionOccurrences.setData(series);
		
		for (Series<String, Number> _series : diagrammeEvolutionOccurrences.getData()) {
			for (XYChart.Data<String, Number> donnee : _series.getData()) {
				donnee.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent e) {
			        	dateDetailOccurrences = new Date(Integer.parseUnsignedInt(donnee.getXValue()));
			        	remplirTableOccurrences();
			        }
				});
			}
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void remplirTableOccurrences() {
		ArrayList<OccurrenceLemme> listeLemmesMemorises = portee.obtenirListeOccurrences(dateDetailOccurrences, typeEvenementOccurrences);
		ObservableList<OccurrenceLemme> obsListeOccurrences = FXCollections.observableArrayList(listeLemmesMemorises);

		tableOccurrences.setPlaceholder(new Text("Cliquez sur une barre du graphe pour obtenir les details associes"));
		
		((TableColumn)tableOccurrences.getColumns().get(0)).setCellFactory(column -> {
		    return new TableCell<OccurrenceLemme, Lemme>() {
		        @Override
		        protected void updateItem(Lemme item, boolean empty) {
		            super.updateItem(item, empty);
		            setAlignment(Pos.CENTER);
		            if (empty) {
		                setGraphic(null);
		            }
		            else {
		            	Rectangle rectangle = new Rectangle();
		                setGraphic(rectangle);
		                rectangle.getStyleClass().add(item.lireInitiateur().toString().toLowerCase().replace(' ', '-'));
		                rectangle.getStyleClass().add("origine");
		            }
		        }
		    };
		});

		((TableColumn)tableOccurrences.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, Lemme>("lemme"));
		((TableColumn)tableOccurrences.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, Lemme>("lemme"));
		((TableColumn)tableOccurrences.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, TypeEvenement>("typeEvenement"));
		((TableColumn)tableOccurrences.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, IssueEvenement>("issueEvenement"));
		((TableColumn)tableOccurrences.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, Date>("date"));
		((TableColumn)tableOccurrences.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<OccurrenceLemme, Integer>("ID"));

		tableOccurrences.setItems(obsListeOccurrences);

		((TableColumn)tableOccurrences.getColumns().get(1)).setComparator(((TableColumn)tableOccurrences.getColumns().get(1)).getComparator().reversed());
		tableOccurrences.getSortOrder().add(((TableColumn)tableOccurrences.getColumns().get(1)));
	}
}
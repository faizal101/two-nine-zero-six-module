package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

public class SelectModules extends BorderPane {

	private ListView<Module> lvUnselectedTerm1, lvUnselectedTerm2, lvSelectedYear, lvSelectedTerm1, lvSelectedTerm2;
	private TextField txtTerm1Credits, txtTerm2Credits;
	private Button btnTerm1Add, btnTerm1Remove, btnTerm2Add, btnTerm2Remove, btnReset, btnSubmit;
	private HBox term1btns, term2btns, miscbtns, creditsTerm1, creditsTerm2;
	private VBox leftArea, rightArea;
	private GridPane grid;
	private SelectModules selectmodules;
	private ObservableList<Module> unselectedModules, selectedModules;
	
	public SelectModules() {
		//TODO: styling
		this.setPadding(new Insets(10, 10, 5, 10));
				
		// Create labels
		Label lblUnselectedTerm1 = new Label("Unselected Term 1 Modules: ");
		Label lblUnselectedTerm2 = new Label("Unselected Term 2 Modules: ");
		Label lblTerm1 = new Label("Term 1: ");
		Label lblTerm2 = new Label("Term 2: ");
		Label lblSelectedYear = new Label("Selected Year Long Modules: ");
		Label lblSelectedTerm1 = new Label("Selected Term 1 Modules: ");
		Label lblSelectedTerm2 = new Label("Selected Term 2 Modules: ");
		Label lblTerm1Credits = new Label("Current Term 1 Credits: ");
		Label lblTerm2Credits = new Label("Current Term 2 Credits: ");

		
		// Setup list views
		unselectedModules = FXCollections.observableArrayList();
		selectedModules = FXCollections.observableArrayList();
		lvUnselectedTerm1 = new ListView<>(unselectedModules);
		lvUnselectedTerm2 = new ListView<>(unselectedModules);
		lvUnselectedTerm1.getSelectionModel().select(0);
		lvUnselectedTerm2.getSelectionModel().select(0);
		
		
		lvSelectedYear = new ListView<>(selectedModules);
		lvSelectedTerm1 = new ListView<>(selectedModules);
		lvSelectedTerm2 = new ListView<>(selectedModules);
		lvSelectedTerm1.getSelectionModel().select(0);
		lvSelectedTerm2.getSelectionModel().select(0);
		lvSelectedYear.getSelectionModel().select(0);
		
		lvSelectedYear.setMinHeight(20);
		lvSelectedYear.setMaxHeight(100);
		
		// Setup text fields
		txtTerm1Credits = new TextField("0");
		txtTerm2Credits = new TextField("0");
		txtTerm1Credits.setEditable(false);
		txtTerm2Credits.setEditable(false);
		
		txtTerm1Credits.setMaxSize(50, 50);
		txtTerm2Credits.setMaxSize(50, 50);
		txtTerm1Credits.setEditable(false);
		txtTerm2Credits.setEditable(false);
		
		// Setup buttons
		btnTerm1Add = new Button("Add");
		btnTerm2Add = new Button("Add");
		btnTerm1Remove = new Button("Remove");
		btnTerm2Remove = new Button("Remove");
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submt");
		
		term1btns = new HBox(10);
		term1btns.getChildren().addAll(lblTerm1, btnTerm1Add, btnTerm1Remove);
		term1btns.setAlignment(Pos.CENTER);
		term2btns = new HBox(10);
		term2btns.getChildren().addAll(lblTerm2, btnTerm2Add, btnTerm2Remove);
		term2btns.setAlignment(Pos.CENTER);
		miscbtns = new HBox(10);
		miscbtns.getChildren().addAll(btnReset, btnSubmit);
		miscbtns.setAlignment(Pos.CENTER);
		
		creditsTerm1 = new HBox(10);
		creditsTerm1.getChildren().addAll(lblTerm1Credits, txtTerm1Credits);
		creditsTerm1.setAlignment(Pos.CENTER);
		creditsTerm2 = new HBox(10);
		creditsTerm2.getChildren().addAll(lblTerm2Credits, txtTerm2Credits);
		creditsTerm2.setAlignment(Pos.CENTER);
		
		// Setup nodes to add to the left half
		leftArea = new VBox();
		leftArea.getChildren().addAll(lblUnselectedTerm1, lvUnselectedTerm1, term1btns, lblUnselectedTerm2, lvUnselectedTerm2, term2btns, creditsTerm1);
		leftArea.setPadding(new Insets(20, 20, 20, 20));
		leftArea.setSpacing(20);
		
		// Setup nodes to add to the right half
		rightArea = new VBox();
		rightArea.getChildren().addAll(lblSelectedYear, lvSelectedYear, lblSelectedTerm1, lvSelectedTerm1, lblSelectedTerm2, lvSelectedTerm2, creditsTerm2);
		rightArea.setPadding(new Insets (20, 20, 20 ,20));
		rightArea.setSpacing(20);
		
		grid = new GridPane();
		grid.add(leftArea, 0, 0);
		grid.add(rightArea, 1, 0);
		GridPane.setHgrow(leftArea, Priority.ALWAYS);
		GridPane.setVgrow(leftArea, Priority.ALWAYS);
		GridPane.setHgrow(rightArea, Priority.ALWAYS);
		GridPane.setVgrow(rightArea, Priority.ALWAYS);
		
		this.setCenter(grid);
		this.setBottom(miscbtns);
		
	}
	
	public SelectModules getSelectModules() {
		return selectmodules;
	}
	
	public void addUnselectedModules(Module module) {
		unselectedModules.add(module);
		System.out.println(unselectedModules);
	}

	public void addSelectedModules(Module module) {
		selectedModules.add(module);
		
	}	
	
	public void addCreditsTerm1(int credits) {
		String temp = txtTerm1Credits.getText();
		int newValue = Integer.parseInt(temp) + credits;
		txtTerm1Credits.setText(String.valueOf(newValue));
	}
}

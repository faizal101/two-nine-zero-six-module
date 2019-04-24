package view;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
	private SelectModules selectModules;
	private ObservableList<Module> unselectedModulesTerm1, unselectedModulesTerm2, selectedModulesYear, selectedModulesTerm1, selectedModulesTerm2;

	public SelectModules() {

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
		unselectedModulesTerm1 = FXCollections.observableArrayList();
		unselectedModulesTerm2 = FXCollections.observableArrayList();
		selectedModulesYear = FXCollections.observableArrayList();
		selectedModulesTerm1 = FXCollections.observableArrayList();
		selectedModulesTerm2 = FXCollections.observableArrayList();

		lvUnselectedTerm1 = new ListView<>(unselectedModulesTerm1);
		lvUnselectedTerm2 = new ListView<>(unselectedModulesTerm2);
		lvUnselectedTerm1.getSelectionModel().select(0);
		lvUnselectedTerm2.getSelectionModel().select(0);


		lvSelectedYear = new ListView<>(selectedModulesYear);
		lvSelectedTerm1 = new ListView<>(selectedModulesTerm1);
		lvSelectedTerm2 = new ListView<>(selectedModulesTerm2);
		lvSelectedTerm1.getSelectionModel().select(0);
		lvSelectedTerm2.getSelectionModel().select(0);
		lvSelectedYear.getSelectionModel().select(0);

		lvSelectedYear.setMinHeight(49);
		lvSelectedYear.setMaxHeight(98);

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
		btnSubmit = new Button("Submit");

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
		return selectModules;
	}

	public void addUnselectedModulesTerm1(Module module) {
		unselectedModulesTerm1.add(module);
		this.clearSelection();
	}

	public void addUnselectedModulesTerm2(Module module) {
		unselectedModulesTerm2.add(module);
		this.clearSelection();
	}

	public void addSelectedModulesYear(Module module) {
		// A year long module is worth 30 credits; 15 credits per term.
		// This is a bit of a hacky way of dealing with it but it works.
		int credits = module.getCredits()/2;
		selectedModulesYear.add(module);
		this.addCreditsTerm1(credits);
		this.addCreditsTerm2(credits);
	}

	public void addCreditsTerm1(int credits) {
		String temp = txtTerm1Credits.getText();
		int newValue = Integer.parseInt(temp) + credits;
		txtTerm1Credits.setText(String.valueOf(newValue));
	}

	public void addCreditsTerm2(int credits) {
		String temp = txtTerm2Credits.getText();
		int newValue = Integer.parseInt(temp) + credits;
		txtTerm2Credits.setText(String.valueOf(newValue));
	}

	public void removeCreditsTerm1(int credits) {
		String temp = txtTerm1Credits.getText();
		int newValue = Integer.parseInt(temp) - credits;
		txtTerm1Credits.setText(String.valueOf(newValue));
	}

	public void removeCreditsTerm2(int credits) {
		String temp = txtTerm2Credits.getText();
		int newValue = Integer.parseInt(temp) - credits;
		txtTerm2Credits.setText(String.valueOf(newValue));
	}

	public int getCreditsTerm1() {
		return Integer.parseInt(txtTerm1Credits.getText());
	}

	public int getCreditsTerm2() {
		return Integer.parseInt(txtTerm2Credits.getText());
	}

	public void clearSelection() {
		lvUnselectedTerm1.getSelectionModel().clearSelection();
	}

	public void addSelectedModulesTerm1(Module module) {
		selectedModulesTerm1.add(module);
		this.addCreditsTerm1(module.getCredits());
	}

	public void addSelectedModulesTerm2(Module module) {
		selectedModulesTerm2.add(module);
		this.addCreditsTerm2(module.getCredits());
	}

	public void removeUnselectedModulesTerm1(Module module) {
		unselectedModulesTerm1.remove(module);
	}

	public void removeUnselectedModulesTerm2(Module module) {
		unselectedModulesTerm2.remove(module);
	}

	public void removeSelectedModulesTerm1(Module module) {
		btnTerm1Remove.disableProperty().bind(Bindings.isEmpty(lvSelectedTerm1.getItems()));
		selectedModulesTerm1.remove(module);
		this.removeCreditsTerm1(module.getCredits());
	}

	public void removeSelectedModulesTerm2(Module module) {
		btnTerm2Remove.disableProperty().bind(Bindings.isEmpty(lvSelectedTerm2.getItems()));
		selectedModulesTerm2.remove(module);
		this.removeCreditsTerm2(module.getCredits());
	}

	public void addModulesTerm1AddHandler(EventHandler<ActionEvent> handler) {
		btnTerm1Add.setOnAction(handler);
	}

	public void addModulesTerm2AddHandler(EventHandler<ActionEvent> handler) {
		btnTerm2Add.setOnAction(handler);
	}

	public Module getUnselectedItemTerm1() {
		return lvUnselectedTerm1.getSelectionModel().getSelectedItem();
	}

	public Module getUnselectedItemTerm2() {
		return lvUnselectedTerm2.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedItemTerm1() {
		return lvSelectedTerm1.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedItemTerm2() {
		return lvSelectedTerm2.getSelectionModel().getSelectedItem();
	}

	public ObservableList<Module> getSelectedModulesTerm1() {
		lvSelectedTerm1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvSelectedTerm1.getSelectionModel().selectAll();
		return lvSelectedTerm1.getSelectionModel().getSelectedItems();
	}

	public ObservableList<Module> getSelectedModulesTerm2() {
		lvSelectedTerm2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvSelectedTerm2.getSelectionModel().selectAll();
		return lvSelectedTerm2.getSelectionModel().getSelectedItems();
	}

	public ObservableList<Module> getSelectedModulesYear() {
		lvSelectedYear.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvSelectedYear.getSelectionModel().selectAll();
		return lvSelectedYear.getSelectionModel().getSelectedItems();
	}

	public void addModulesTerm1RemoveHandler(EventHandler<ActionEvent> handler) {
		btnTerm1Remove.setOnAction(handler);
	}

	public void addModulesTerm2RemoveHandler(EventHandler<ActionEvent> handler) {
		btnTerm2Remove.setOnAction(handler);
	}

	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}

	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

	public void clearAll() {
		lvUnselectedTerm1.getItems().clear();
		lvUnselectedTerm2.getItems().clear();
		lvSelectedYear.getItems().clear();
		lvSelectedTerm1.getItems().clear();
		lvSelectedTerm2.getItems().clear();
		txtTerm1Credits.setText(String.valueOf(0));
		txtTerm2Credits.setText(String.valueOf(0));
	}
}

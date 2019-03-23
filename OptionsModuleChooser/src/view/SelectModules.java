package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Course;

public class SelectModules extends GridPane {

	private ListView<Course> lvUnselectedTerm1, lvUnselectedTerm2, lvSelectedYear, lvSelectedTerm1, lvSelectedTerm2;
	private TextField txtTerm1Credits, txtTerm2Credits;
	private Button btnTerm1Add, btnTerm1Remove, btnTerm2Add, brnTerm2Remove, btnReset, btnSubmit;

	public SelectModules() {
		//TODO: styling
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		// Create labels
		Label lblUnselectedTerm1 = new Label("Unselected Term 1 Modules: ");
		Label lblUnselectedTerm2 = new Label("Unselected Term 2 Modules: ");
		Label lblSelectedYear = new Label("Selected Year Long Modules: ");
		Label lblSelectedTerm1 = new Label("Selected Term 1 Modules: ");
		Label lblSelectedTerm2 = new Label("Selected Term 2 Modules: ");
		Label lblTerm1Credits = new Label("Current Term 1 Credits: ");
		Label lblTerm2Credits = new Label("Current Term 2 Credits: ");

		// Setup list views
		lvUnselectedTerm1 = new ListView<>();
		lvUnselectedTerm2 = new ListView<>();
		lvSelectedYear = new ListView<>();
		lvSelectedTerm1 = new ListView<>();
		lvSelectedTerm2 = new ListView<>();
		
		lvSelectedYear.setMaxHeight(50);
		
		
		// Setup text fields
		txtTerm1Credits = new TextField();
		txtTerm2Credits = new TextField();

		//TODO: Initialise buttons
		//btnCreateProfile = new Button("Create Profile");

		//add controls and labels to container
		this.add(lblUnselectedTerm1, 0, 0, 1, 1);
		this.add(lvUnselectedTerm1, 0, 1, 1, 1);

		this.add(lblUnselectedTerm2, 0, 2);
		this.add(lvUnselectedTerm2, 0, 3);

		this.add(lblSelectedYear, 1, 0);
		this.add(lvSelectedYear, 1, 1);

		this.add(lblSelectedTerm1, 1, 2);
		this.add(lvSelectedTerm1, 1, 3);

		this.add(lblSelectedTerm2, 1, 4);
		this.add(lvSelectedTerm2, 1, 5);

		this.add(lblTerm1Credits, 0, 4);
		this.add(txtTerm1Credits, 0, 5);

		this.add(lblTerm2Credits, 0, 6);
		this.add(txtTerm2Credits, 0, 7);

		//this.add(new HBox(), 0, 7);

	}
}

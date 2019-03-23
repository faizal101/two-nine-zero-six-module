package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class OverviewSelection extends BorderPane{

	private TextField txtOverview;
	private Button btnSave;

	public OverviewSelection() {
		
		this.setPadding(new Insets(20, 20, 20, 20));	

		// Setup text fields
		txtOverview = new TextField();
		txtOverview.prefHeightProperty().bind(this.widthProperty());
		txtOverview.setPadding(new Insets (10, 10, 10, 10));
		
		// Initialise and setup save button
		btnSave = new Button("Save Overview");
		btnSave.setPadding(new Insets(10, 10, 10, 10));

		this.setCenter(txtOverview);
		this.setBottom(btnSave);
		BorderPane.setAlignment(btnSave, Pos.CENTER);
		BorderPane.setMargin(btnSave, new Insets (10, 10, 10, 10));
	}

}

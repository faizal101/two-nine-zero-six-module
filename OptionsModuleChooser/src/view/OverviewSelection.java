package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class OverviewSelection extends BorderPane{

	private TextArea txtOverview;
	private Button btnSave;

	public OverviewSelection() {
		
		this.setPadding(new Insets(20, 20, 20, 20));	

		// Setup text area
		txtOverview = new TextArea();
		txtOverview.setWrapText(true);
		
		// Initialise and setup save button
		btnSave = new Button("Save Overview");

		this.setCenter(txtOverview);
		this.setBottom(btnSave);
		BorderPane.setAlignment(btnSave, Pos.CENTER);
		BorderPane.setMargin(btnSave, new Insets (10, 10, 10, 10));
	}
}

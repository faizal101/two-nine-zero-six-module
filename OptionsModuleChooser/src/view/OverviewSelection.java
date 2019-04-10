package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class OverviewSelection extends BorderPane{

	private TextArea txtOverview;
	private Button btnSave;
	private String text = "";

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

	public void setOverview(String text) {
		this.text += text;
		txtOverview.setText(this.text);
	}

	public String getOverview() {
		return txtOverview.getText();
	}

	public void addSaveHandler(EventHandler<ActionEvent> handler) {
		btnSave.setOnAction(handler);
	}
}

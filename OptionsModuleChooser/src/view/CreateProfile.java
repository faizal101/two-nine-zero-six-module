package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Course;

public class CreateProfile extends GridPane{

	private ComboBox<Course> cboCourses;
	private TextField txtPNumber, txtFirstName, txtSurname, txtEMail;
	private Button btnCreateProfile;

	public CreateProfile() {
		//TODO: styling
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		// Create labels
		Label lblCourses = new Label("Select course: ");
		Label lblPNumber = new Label("P Number: ");
		Label lblFirstName = new Label("First name: ");
		Label lblSurname = new Label("Surname: ");
		Label lblEmail = new Label("Email address: ");
		Label lblDate = new Label("Submission date: ");

		// Create combo box
		cboCourses = new ComboBox<Course>();

		// Setup text fields
		txtPNumber = new TextField();
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtEMail = new TextField();
		DatePicker txtSubmissionDate = new DatePicker();

		// Initialise submit button
		btnCreateProfile = new Button("Create Profile");

		//add controls and labels to container
		this.add(lblCourses, 0, 0);
		this.add(cboCourses, 1, 0);

		this.add(lblPNumber, 0, 1);
		this.add(txtPNumber, 1, 1);

		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEMail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(txtSubmissionDate, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnCreateProfile, 1, 6);
	}

}

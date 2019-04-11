package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Delivery;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.CreateProfile;
import view.ModuleMenuBar;
import view.OptionsModuleChooserRootPane;
import view.OverviewSelection;
import view.SelectModules;

public class OptionsModuleChooserController {

	//fields to be used throughout the class
	private OptionsModuleChooserRootPane view;
	private StudentProfile model;
	private CreateProfile createProfile;
	private SelectModules selectModules;
	private OverviewSelection overviewSelection;
	private ModuleMenuBar menuBar;

	public OptionsModuleChooserController(OptionsModuleChooserRootPane view, StudentProfile model) {
		// Initialise model and view fields
		this.model = model;
		this.view = view;
		createProfile = this.view.getCreateProfile();
		selectModules = this.view.getSelectModules();
		overviewSelection = this.view.getOverviewSelection();
		menuBar = this.view.getMenuBar();

		// Populate combo box in create profile pane
		createProfile.populateComboBoxWithCourses(setupAndRetrieveCourses());

		// Attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	private void attachEventHandlers() {
		createProfile.addCreateProfileHandler(new CreateProfileHandler());
		selectModules.addModulesTerm1AddHandler(new AddModulesTerm1Handler());
		selectModules.addModulesTerm2AddHandler(new AddModulesTerm2Handler());
		selectModules.addModulesTerm1RemoveHandler(new RemoveModulesTerm1Handler());
		selectModules.addModulesTerm2RemoveHandler(new RemoveModulesTerm2Handler());
		selectModules.addResetHandler(new ResetHandler());
		selectModules.addSubmitHandler(new SubmitHandler());
		overviewSelection.addSaveOverviewHandler(new SaveOverviewHandler());
		menuBar.addAboutHandler(new AboutHandler());
		menuBar.addSaveDataHandler(new SaveDataHandler());
		menuBar.addLoadDataHandler(new LoadDataHandler());
	}

	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Name n = createProfile.getNameInput();
			Validation validation = new Validation();
			if(!validation.pNumberValidation(createProfile.getPNumber())) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "You need to input a valid P Number!");
			} else if(validation.nameValidation(n)) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "You need to input both a first and surname!");
			} else if(!validation.eMailValidation(createProfile.getEMail())) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "You need to input a valid email address!");
			} else {
				model.setStudentName(new Name(n.getFirstName(), n.getFamilyName()));
				model.setStudentName(new Name(n.getFirstName(), n.getFamilyName()));
				model.setPnumber(createProfile.getPNumber());
				model.setEmail(createProfile.getEMail());
				model.setSubmissionDate(createProfile.getDate());
				model.setCourseOfStudy(createProfile.getSelectedCourse());

				populateListViews();
			}
		}
	}

	private class AddModulesTerm1Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectModules.getCreditsTerm1() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Term 1 Maximum Credits", null, "The maximum credits allowed for term 1 is 60 credits. "
						+ "Please remove a course if you would like to add a different course.");
			} else {
				selectModules.addSelectedModulesTerm1(selectModules.getUnselectedItemTerm1());
				selectModules.removeUnselectedModulesTerm1(selectModules.getUnselectedItemTerm1());
			}
		}
	}

	private class AddModulesTerm2Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectModules.getCreditsTerm2() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Term 2 Maximum Credits", null, "The maximum credits allowed for term 2 is 60 credits. "
						+ "Please remove a course if you would like to add a different course.");
			} else {
				selectModules.addSelectedModulesTerm2(selectModules.getUnselectedItemTerm2());
				selectModules.removeUnselectedModulesTerm2(selectModules.getUnselectedItemTerm2());
			}
		}

	}

	private class RemoveModulesTerm1Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectModules.getSelectedItemTerm1().isMandatory()) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, selectModules.getSelectedItemTerm1().toString() + " is a compulsory module!");
			} else {
				selectModules.addUnselectedModulesTerm1(selectModules.getSelectedItemTerm1());
				selectModules.removeSelectedModulesTerm1(selectModules.getSelectedItemTerm1());
			}

		}

	}

	private class RemoveModulesTerm2Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectModules.getSelectedItemTerm2().isMandatory()) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, selectModules.getSelectedItemTerm2().toString() + " is a compulsory module!");
			} else {
				selectModules.addUnselectedModulesTerm2(selectModules.getSelectedItemTerm2());
				selectModules.removeSelectedModulesTerm2(selectModules.getSelectedItemTerm2());
			}

		}

	}

	private class ResetHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			selectModules.clearAll();
			populateListViews();
		}

	}

	private class SubmitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			// Adds course, student name, P-Number, e-mail and date to the overview selection text area.
			overviewSelection.setOverview("Course: " + model.getCourseOfStudy() + "\n" + "Student: "
					+ model.getStudentName().getFullName() + "\n" + "P Number: " + model.getPnumber() + "\n"
					+ "Email: " + model.getEmail() + "\n" + "Date: " + model.getSubmissionDate() + "\n");

			overviewSelection.setOverview("\nChosen Modules: \n");
			// Add the chosen year long modules
			ObservableList<Module> yearLong = selectModules.getSelectedModulesYear();
			yearLong.forEach(m -> overviewSelection.setOverview(m.getModuleCode() + ": " + m.getModuleName() + "\n" + m.getCredits() + " credits\t"
					+ "Mandatory on the course: " + (m.isMandatory()?"Yes\t":"No\t")
					+ "Delivery: Year Long" + "\n\n"));

			// Adds the chosen term 1 modules
			ObservableList<Module> term1 = selectModules.getSelectedModulesTerm1();
			term1.forEach(m -> overviewSelection.setOverview(m.getModuleCode() + ": " + m.getModuleName() + "\n" + m.getCredits() + " credits\t"
					+ "Mandatory on the course: " + (m.isMandatory()?"Yes\t":"No\t")
					+ "Delivery: Term 1" + "\n\n"));

			// Add the chosen term 2 modules:
			ObservableList<Module> term2 = selectModules.getSelectedModulesTerm2();
			term2.forEach(m -> overviewSelection.setOverview(m.getModuleCode() + ": " + m.getModuleName() + "\n" + m.getCredits() + " credits\t"
					+ "Mandatory on the course: " + (m.isMandatory()?"Yes\t":"No\t")
					+ "Delivery: Term 2" + "\n\n"));

		}

	}

	private class SaveOverviewHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			try {
				PrintWriter out = new PrintWriter(model.getPnumber() + ".txt");
				out.print(overviewSelection.getOverview());
				out.close();
			} catch (FileNotFoundException e) {
				alertDialogBuilder(AlertType.ERROR, "Error when trying to save to file", null, "Failed to save to file. Please try again.");
			}

		}

	}

	private class AboutHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			alertDialogBuilder(AlertType.INFORMATION, "About: ", "Options Module Chooser", "A simple program developed by faizal101\n"
					+ "Current version: v1.0\nAll Right Reserved");
		}

	}

	private class SaveDataHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			try {
				ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream("profile.dat"));
				outputObject.writeObject(model);
				outputObject.flush();
				outputObject.close();
				alertDialogBuilder(AlertType.INFORMATION, "Saved", null, "Student profile has been saved successfully.");
			} catch (IOException e) {
				e.printStackTrace();
				alertDialogBuilder(AlertType.ERROR, "ERROR", "Failed to save student profile", "Something went wrong when trying to save the student profile.\n"
						+ "Please try again.");
			}
		}

	}

	private class LoadDataHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			try {
				ObjectInputStream readObjectFile = new ObjectInputStream(new FileInputStream("profile.dat"));
				model = (StudentProfile) readObjectFile.readObject();
				readObjectFile.close();
				alertDialogBuilder(AlertType.INFORMATION, "Loaded", null, "Student profile has been loaded successfully.");
			} catch (FileNotFoundException e) {
				alertDialogBuilder(AlertType.ERROR, "ERROR", null, "Could not find the student profile to load. Is it in the current directory?");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				alertDialogBuilder(AlertType.ERROR, "ERROR", null, "Something went wrong when trying to load the student profile. Please try again.");
			}
		}
	}

	private Course[] setupAndRetrieveCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Popular Technology Ethics", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(imat3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3426);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(imat3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3426);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

	/*
	 * This was originally in CreateProfileHandler but decided to create a method for it since ResetHandler requires it and
	 * that I want to try and avoid repeating code unnecessary.
	 * Original plan was to use a forEach to add and remove the modules in the list view when the Reset button was clicked on
	 * but that didn't exactly work (The Reset button had to be clicked on multiple times.
	 * I'm 80% confident there's a better way to do it but at the moment, I can't think of the 'correct' way of doing it orz
	 */
	private void populateListViews() {
		for(Module m : model.getCourseOfStudy().getAllModulesOnCourse()) {
			if(!m.isMandatory()) {
				if(m.getRunPlan() == Delivery.TERM_1) {
					selectModules.addUnselectedModulesTerm1(m);
				} else if(m.getRunPlan() == Delivery.TERM_2) {
					selectModules.addUnselectedModulesTerm2(m);
				}
			} else {
				if(m.getRunPlan() == Delivery.TERM_1) {
					selectModules.addSelectedModulesTerm1(m);
				} else if(m.getRunPlan() == Delivery.TERM_2) {
					selectModules.addSelectedModulesTerm2(m);
				} else {
					selectModules.addSelectedModulesYear(m);
				}
			}
		}
	}

	// Helper method to build dialogs
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private class Validation {

		public boolean pNumberValidation(String pnumber) {
			Pattern pNumber = Pattern.compile("^[P-p]+[\\d+]");
			Matcher check = pNumber.matcher(pnumber);
			return check.find();
		}

		public boolean nameValidation(Name name) {
			return name.getFirstName().equals("") || name.getFamilyName().equals("");
		}

		public boolean eMailValidation(String email) {
			// It takes like a wizard to master Regex.
			// This Regex to validate an email address is from: https://stackoverflow.com/a/8204716
			Pattern eMail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			Matcher check = eMail.matcher(email);
			return check.find();
		}

	}

}

package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

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


	public OptionsModuleChooserController(OptionsModuleChooserRootPane view, StudentProfile model) {
		// Initialise model and view fields
		this.model = model;
		this.view = view;
		createProfile = this.view.getCreateProfile();
		selectModules = this.view.getSelectModules();
		overviewSelection = this.view.getOverviewSelection();

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
		overviewSelection.addSaveHandler(new SaveHandler());
	}

	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO More validations such as email, current date etc.
			Name n = createProfile.getNameInput();

			// Validation to check if name is empty
			if (n.getFirstName().equals("") || n.getFamilyName().equals("")) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "You need to input both a first and surname!");
			} else {
				model.setStudentName(new Name(n.getFirstName(), n.getFamilyName()));
				model.setPnumber(createProfile.getPNumber());
				model.setEmail(createProfile.getEMail());
				model.setSubmissionDate(createProfile.getDate());
				model.setCourseOfStudy(createProfile.getSelectedCourse());

				for(Module m : model.getCourseOfStudy().getAllModulesOnCourse()) {
					if(!m.isMandatory() && m.getRunPlan() == Delivery.TERM_1) {
						selectModules.addUnselectedModulesTerm1(m);
					} else if(!m.isMandatory() && m.getRunPlan() == Delivery.TERM_2) {
						selectModules.addUnselectedModulesTerm2(m);
					} else if(m.isMandatory()) {
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
			// TODO Auto-generated method stub

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

	private class SaveHandler implements EventHandler<ActionEvent> {
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

	// Helper method to build dialogs
		private void alertDialogBuilder(AlertType type, String title, String header, String content) {
			Alert alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		}

}

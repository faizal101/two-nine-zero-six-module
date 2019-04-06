package controller;

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
import view.SelectModules;

public class OptionsModuleChooserController {

	//fields to be used throughout the class
	private OptionsModuleChooserRootPane view;
	private StudentProfile model;
	private CreateProfile createprofile;
	private SelectModules selectmodules;


	public OptionsModuleChooserController(OptionsModuleChooserRootPane view, StudentProfile model) {
		// Initialise model and view fields
		this.model = model;
		this.view = view;
		createprofile = this.view.getCreateProfile();
		selectmodules = this.view.getSelectModules();

		// Populate combo box in create profile pane
		createprofile.populateComboBoxWithCourses(setupAndRetrieveCourses());

		// Attach event handlers to view using private helper method
		this.attachEventHandlers();	
		

	}

	private void attachEventHandlers() {
		createprofile.addCreateProfileHandler(new CreateProfileHandler());
		selectmodules.addModulesTerm1AddHandler(new AddModulesTerm1Handler());
		selectmodules.addModulesTerm2AddHandler(new AddModulesTerm2Handler());
		selectmodules.addModulesTerm1RemoveHandler(new RemoveModulesTerm1Handler());
		selectmodules.addModulesTerm2RemoveHandler(new RemoveModulesTerm2Handler());
	}

	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			// TODO More validations such as email, current date etc.
			Name n = createprofile.getNameInput();
			
			// Validation to check if name is empty
			if (n.getFirstName().equals("") || n.getFamilyName().equals("")) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, "You need to input both a first and surname!");
			} else {
				model.setStudentName(new Name(n.getFirstName(), n.getFamilyName()));
				model.setPnumber(createprofile.getPNumber());
				model.setEmail(createprofile.getEMail());
				model.setSubmissionDate(createprofile.getDate());				
				model.setCourseOfStudy(createprofile.getSelectedCourse());
				
				for(Module m : model.getCourseOfStudy().getAllModulesOnCourse()) {
					if(!m.isMandatory() && m.getRunPlan() == Delivery.TERM_1) {
						selectmodules.addUnselectedModulesTerm1(m);
					} else if (!m.isMandatory() && m.getRunPlan() == Delivery.TERM_2) {
						selectmodules.addUnselectedModulesTerm2(m);	
					} else if (m.isMandatory()) {
						if(m.getRunPlan() == Delivery.TERM_1) {
							selectmodules.addSelectedModulesTerm1(m);
							} else if(m.getRunPlan() == Delivery.TERM_2) { 
							selectmodules.addSelectedModulesTerm2(m);
							} else {
						selectmodules.addSelectedModulesYear(m);
						}
					}
				}
			}
			
		}
		
	}
	
	private class AddModulesTerm1Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			selectmodules.addSelectedModulesTerm1(selectmodules.getUnselectedItemTerm1());
			selectmodules.removeUnselectedModulesTerm1(selectmodules.getUnselectedItemTerm1());
		}
		
	}
	
	private class AddModulesTerm2Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			selectmodules.addSelectedModulesTerm2(selectmodules.getUnselectedItemTerm2());
			selectmodules.removeUnselectedModulesTerm2(selectmodules.getUnselectedItemTerm2());			
		}
		
	}
	
	private class RemoveModulesTerm1Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectmodules.getSelectedItemTerm1().isMandatory()) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, selectmodules.getSelectedItemTerm1().toString() + " is a compulsory module!");
			} else {
				selectmodules.addUnselectedModulesTerm1(selectmodules.getSelectedItemTerm1());
				selectmodules.removeSelectedModulesTerm1(selectmodules.getSelectedItemTerm1());
			}
			
		}
		
	}
	
	private class RemoveModulesTerm2Handler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if(selectmodules.getSelectedItemTerm2().isMandatory()) {
				alertDialogBuilder(AlertType.ERROR, "Error Dialog", null, selectmodules.getSelectedItemTerm2().toString() + " is a compulsory module!");
			} else {
				selectmodules.addUnselectedModulesTerm2(selectmodules.getSelectedItemTerm2());
				selectmodules.removeSelectedModulesTerm2(selectmodules.getSelectedItemTerm2());
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

	//helper method to build dialogs
		private void alertDialogBuilder(AlertType type, String title, String header, String content) {
			Alert alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		}
		
}

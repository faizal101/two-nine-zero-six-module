package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

//You may change this class to extend another type if you wish
public class OptionsModuleChooserRootPane extends TabPane {
	private CreateProfile createprofile;
	private SelectModules selectmodules;
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();


	public OptionsModuleChooserRootPane() {
		createprofile = new CreateProfile();
		selectmodules = new SelectModules();
		Tab t1 = new Tab("Create Profile", createprofile);
		Tab t2 = new Tab("Select Modules", selectmodules);
		Tab t3 = new Tab("Overview Selection");
		this.getTabs().addAll(t1, t2, t3);
	}
}

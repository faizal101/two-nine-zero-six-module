package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

//You may change this class to extend another type if you wish
public class OptionsModuleChooserRootPane extends TabPane {
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();
	
	public OptionsModuleChooserRootPane() {
	Tab t1 = new Tab("Create Profile");
	Tab t2 = new Tab("Select Modules");
	Tab t3 = new Tab("Overview Selection");
	this.getTabs().addAll(t1, t2, t3);
	}
}

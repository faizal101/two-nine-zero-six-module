package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class OptionsModuleChooserRootPane extends BorderPane {
	
	private CreateProfile createprofile;
	private SelectModules selectmodules;
	private OverviewSelection overviewselection;
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();
	private ModuleMenuBar menubar;

	public OptionsModuleChooserRootPane() {
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		createprofile = new CreateProfile();
		selectmodules = new SelectModules();
		overviewselection = new OverviewSelection();
		menubar = new ModuleMenuBar();

		Tab t1 = new Tab("Create Profile", createprofile);
		Tab t2 = new Tab("Select Modules", selectmodules);
		Tab t3 = new Tab("Overview Selection", overviewselection);
		tabPane.getTabs().addAll(t1, t2, t3);
		
		this.setTop(menubar);
		this.setCenter(tabPane);
		
	}
	
	/* These methods provide a public interface for the root pane and allow
	 * each of the sub-containers to be accessed by the controller. */
	public CreateProfile getCreateProfile() {
		return createprofile;
	}
	
	public SelectModules getSelectModules() {
		return selectmodules;	
	}
}

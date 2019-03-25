package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class OptionsModuleChooserRootPane extends TabPane {
	
	private CreateProfile createprofile;
	private SelectModules selectmodules;
	private OverviewSelection overviewselection;
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();


	public OptionsModuleChooserRootPane() {
		
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		createprofile = new CreateProfile();
		selectmodules = new SelectModules();
		overviewselection = new OverviewSelection();

		Tab t1 = new Tab("Create Profile", createprofile);
		Tab t2 = new Tab("Select Modules", selectmodules);
		Tab t3 = new Tab("Overview Selection", overviewselection);
		this.getTabs().addAll(t1, t2, t3);
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

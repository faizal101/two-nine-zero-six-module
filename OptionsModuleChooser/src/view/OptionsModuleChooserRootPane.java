package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class OptionsModuleChooserRootPane extends BorderPane {

	private ModuleMenuBar menubar;
	private CreateProfile createProfile;
	private SelectModules selectModules;
	private OverviewSelection overviewSelection;
	TabPane tabPane = new TabPane();
	Tab tab = new Tab();

	public OptionsModuleChooserRootPane() {

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		createProfile = new CreateProfile();
		selectModules = new SelectModules();
		overviewSelection = new OverviewSelection();
		menubar = new ModuleMenuBar();

		Tab t1 = new Tab("Create Profile", createProfile);
		Tab t2 = new Tab("Select Modules", selectModules);
		Tab t3 = new Tab("Overview Selection", overviewSelection);
		tabPane.getTabs().addAll(t1, t2, t3);

		this.setTop(menubar);
		this.setCenter(tabPane);

	}

	/* These methods provide a public interface for the root pane and allow
	 * each of the sub-containers to be accessed by the controller. */
	public CreateProfile getCreateProfile() {
		return createProfile;
	}

	public SelectModules getSelectModules() {
		return selectModules;
	}

	public OverviewSelection getOverviewSelection() {
		return overviewSelection;
	}
}

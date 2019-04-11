package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class ModuleMenuBar extends MenuBar {

	private MenuItem loadDataItem, saveDataItem, aboutItem;
	private Menu menu;

	public ModuleMenuBar() {

//		Menu menu;

		// First menu on MenuBar
		menu = new Menu("_File");
		loadDataItem = new MenuItem("Load Student Profile");
		loadDataItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		menu.getItems().add(loadDataItem);

		saveDataItem = new MenuItem("Save Student Profile");
		saveDataItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		menu.getItems().add(saveDataItem);

		// Adds the menu to the MenuBar
		this.getMenus().add(menu);

		// Second menu on MenuBar
		menu = new Menu("Help");
		aboutItem = new MenuItem("About");
		aboutItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		menu.getItems().add(aboutItem);

		// Add the menu to the MenuBar
		this.getMenus().add(menu);

	}

	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		aboutItem.setOnAction(handler);
	}

	public void addSaveDataHandler(EventHandler<ActionEvent> handler) {
		saveDataItem.setOnAction(handler);;
	}

	public void addLoadDataHandler(EventHandler<ActionEvent> handler) {
		loadDataItem.setOnAction(handler);
	}

}

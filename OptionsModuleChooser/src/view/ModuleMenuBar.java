package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class ModuleMenuBar extends MenuBar {

	private MenuItem loadDataItem, saveDataItem, aboutItem;

	public ModuleMenuBar() {

		Menu menu;

		// First menu on MenuBar //
		menu = new Menu("_File");
		loadDataItem = new MenuItem("Load Student Data");
		loadDataItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		menu.getItems().add(loadDataItem);
		
		saveDataItem = new MenuItem("Save Journey Data");
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

}

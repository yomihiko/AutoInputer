package jp.ac.hsc.my.DevelopTool;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController {


    @FXML
    private Button getBtn;

    @FXML
    void onGetBtn(ActionEvent event) {
    	InfoInputer info = new InfoInputer();
    	FileChooser fc = new FileChooser();
    	fc.setInitialDirectory(new File("."));
    	fc.getExtensionFilters().add(new ExtensionFilter("FXMLファイル", "*.fxml"));
    	File file = fc.showOpenDialog(null);
    	if(file != null) {
    		String fileName = file.getPath();
    		fileName = fileName.replaceAll("\\\\", "/");
    		fileName = "file:/" + fileName;
    		info.inputModule(fileName);
        	info.InfoInput(InfoInputer.getMainAnchor(), InfoInputer.getSubAnchor());
    	}
    }

}

package jp.ac.hsc.my.DevelopTool;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;



public class SubController {

	@FXML
	private AnchorPane subAnchorMain;

	@FXML
	private Label nameLbl;

	@FXML
	private Button fileReadBtn;

	@FXML
	private TextArea infoFi;

	@FXML
    private Button nextBtn;

	 @FXML
	private Button outputBtn;



	@FXML
	void onFileReadBtn(ActionEvent event) {
		InfoInputer mainIns = InfoInputer.getIns();
		mainIns.InfoInput(InfoInputer.getMainAnchor(), InfoInputer.getSubAnchor());
		if(mainIns.hasNext()) {
			mainIns.next();
			System.out.println(mainIns.getName());
			nameLbl.setText(mainIns.getName());
			nameLbl.setVisible(true);
			mainIns.setEffect();
		}
		fileReadBtn.setDisable(true);
		nextBtn.setDisable(false);
		infoFi.setDisable(false);
	}

	@FXML
    void onNextBtn(ActionEvent event) {
		InfoInputer mainIns = InfoInputer.getIns();
		mainIns.setComment(infoFi.getText());
		mainIns.removeEffect();
		infoFi.setText("");
		if(mainIns.hasNext()) {
			mainIns.next();
			nameLbl.setText(mainIns.getName());
			mainIns.setEffect();
		}
		else {
			nextBtn.setDisable(true);
			infoFi.setDisable(true);
			outputBtn.setDisable(false);
		}
    }

    @FXML
    void onOutPutBtn(ActionEvent event) {
    	InfoInputer mainIns = InfoInputer.getIns();
    	ArrayList<MacrosJson> wkList = mainIns.getGList();
    	String wkst = "";
    	for(int i = 0;i < wkList.size();i++) {
    		wkst = wkst + wkList.get(i).getCSV();
    	}
    	FileChooser fc = new FileChooser();
    	ExtensionFilter e = new ExtensionFilter("CSVファイル", "*.csv");
		fc.getExtensionFilters().add(e );
		File csvFile = fc.showSaveDialog(null);
		if(csvFile != null) {
			try {
				Files.write(Path.of(csvFile.getPath()), wkst.getBytes(Charset.forName("MS932")));
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				Alert alt = new Alert(AlertType.ERROR);
				alt.setContentText("ファイルの書き込みに失敗しました。");
			}
		}
    }
}


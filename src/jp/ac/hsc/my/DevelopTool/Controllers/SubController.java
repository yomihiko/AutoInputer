package jp.ac.hsc.my.DevelopTool.Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jp.ac.hsc.my.DevelopTool.ILoadFxml;
import jp.ac.hsc.my.DevelopTool.InfoInputer;
import jp.ac.hsc.my.DevelopTool.MacrosJson;



public class SubController extends AnchorPane implements ILoadFxml,Initializable {

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


	public SubController() {
		// TODO 自動生成されたコンストラクター・スタブ
		loadFxml("fxml/infoinput.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		fileReadBtn.setOnAction(event -> {
			onStart();
		});
	}
	/**
	 * 開始ボタンを押したときの挙動
	 */
	private void onStart() {
		InfoInputer mainIns = InfoInputer.getIns();
		//mainIns.InfoInput(InfoInputer.getMainAnchor(), InfoInputer.getSubAnchor());
		if(mainIns.hasNext()) {
			mainIns.next();
			System.out.println(mainIns.getName());
			nameLbl.setText(mainIns.getName());
			nameLbl.setVisible(true);
			mainIns.setEffect();
		}
	}

    private void onNextBtn() {
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

    private void onOutPutBtn() {
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


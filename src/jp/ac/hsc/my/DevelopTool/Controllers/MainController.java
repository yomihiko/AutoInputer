package jp.ac.hsc.my.DevelopTool.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jp.ac.hsc.my.DevelopTool.ILoadFxml;
import jp.ac.hsc.my.DevelopTool.Main;

public class MainController extends AnchorPane implements ILoadFxml,Initializable {


    @FXML
    private Button getBtn;


    public MainController() {
		// TODO 自動生成されたコンストラクター・スタブ
    	loadFxml("fxml/Main.fxml");
	}
//    @FXML
//    void onGetBtn(ActionEvent event) {
//    	InfoInputer info = new InfoInputer();
//    	FileChooser fc = new FileChooser();
//    	fc.setInitialDirectory(new File("."));
//    	fc.getExtensionFilters().add(new ExtensionFilter("FXMLファイル", "*.fxml"));
//    	File file = fc.showOpenDialog(null);
//    	if(file != null) {
//    		String fileName = file.getPath();
//    		fileName = fileName.replaceAll("\\\\", "/");
//    		fileName = "file:/" + fileName;
//    		info.inputModule(fileName);
//        	info.InfoInput(InfoInputer.getMainAnchor(), InfoInputer.getSubAnchor());
//    	}
//    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		getBtn.setOnAction(event -> {
			openFileChooser();
		});
	}
	/**
	 * ファイルチューザを開く
	 */
	private void openFileChooser() {
    	FileChooser fc = new FileChooser();//ファイルチューザー
    	fc.setInitialDirectory(new File("."));//カレントディレクトリを基点にする
    	fc.getExtensionFilters().add(new ExtensionFilter("FXMLファイル", "*.fxml"));//fxmlのみ表示する
    	File file = fc.showOpenDialog(null);
    	if(file != null) {//ファイルが読み込めていたら
    		String fileName = file.getPath();
    		fileName = fileName.replaceAll("\\\\", "/");
    		fileName = "file:/" + fileName;
    		Main.getIns().openInputer();
    		Main.getIns().makeSubStage(fileName);
    	}
	}

}

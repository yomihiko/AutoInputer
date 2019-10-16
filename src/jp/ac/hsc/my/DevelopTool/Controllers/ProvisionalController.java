package jp.ac.hsc.my.DevelopTool.Controllers;

import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import jp.ac.hsc.my.DevelopTool.ILoadFxml;

public class ProvisionalController extends AnchorPane implements ILoadFxml {


    @FXML
    private Button getBtn;


    public ProvisionalController(String uri) {
		// TODO 自動生成されたコンストラクター・スタブ
    	try {
			loadFxmlURI(uri);
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

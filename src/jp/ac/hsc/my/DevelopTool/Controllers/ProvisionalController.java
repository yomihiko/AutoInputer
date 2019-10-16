package jp.ac.hsc.my.DevelopTool.Controllers;

import java.net.MalformedURLException;

import javafx.scene.layout.GridPane;
import jp.ac.hsc.my.DevelopTool.ILoadFxml;

public class ProvisionalController extends GridPane implements ILoadFxml {




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

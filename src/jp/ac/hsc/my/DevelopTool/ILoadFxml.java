package jp.ac.hsc.my.DevelopTool;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public interface ILoadFxml {
	default void loadFxml(String fxml) {
		// レイアウト取得
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
		loader.setRoot(this);								// ルートノードの設定
		loader.setController(this);						// コントローラの設定
		try {
			loader.load();									// レイアウトのロード(try-catch構文必須)
		} catch (IOException e) {
			throw new RuntimeException(e);		// 例外時は、実行時例外を通知する
		}
	}
	default void loadFxmlURI(String uri) throws MalformedURLException {
		// レイアウト取得
		FXMLLoader loader = new FXMLLoader(new URL(uri));
		loader.setRoot(this);								// ルートノードの設定
		loader.setController(this);						// コントローラの設定
		try {
			loader.load();									// レイアウトのロード(try-catch構文必須)
		} catch (IOException e) {
			throw new RuntimeException(e);		// 例外時は、実行時例外を通知する
		}
	}
}
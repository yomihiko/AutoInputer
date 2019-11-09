package jp.ac.hsc.my.DevelopTool;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jp.ac.hsc.my.DevelopTool.Controllers.MainController;
import jp.ac.hsc.my.DevelopTool.Controllers.ProvisionalController;
import jp.ac.hsc.my.DevelopTool.Controllers.SubController;


public class Main extends Application {
	private static Main mainIns;//インスタンス
	private static Stage stage;//メインのステージ
	private static Stage subStage;//読み込んだファイルのステージ
	private ArrayList<Node> nList;//自動入力対象画面の全ノードのリスト
	private final int ABNORMAL = -1;
	private final String errTitle = "ファイル読み込みエラー";
	private final String errText = "ファイルの読み込みに失敗しました。";

	@Override
	public void start(Stage primaryStage) {
		try {
			mainIns = this;
			stage = primaryStage;
			stage.setResizable(false);
			openMain();//メイン画面を開く
			stage.setOnCloseRequest(e -> endApp(e));						// ×ボタン処理
			stage.showingProperty().addListener((observable, oldValue, newValue) -> {
				if (oldValue == true && newValue == false) finishApp();	// ステージが非表示になったときに呼ばれる->最後に呼ばれる
			});
			stage.show();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			Alert alt = new Alert(AlertType.ERROR);
			alt.setTitle(errTitle);
			alt.setContentText(errText);
			alt.showAndWait();
			System.exit(ABNORMAL);
		}

	}
	private void endApp(WindowEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);		// 確認ダイアログ
		alert.setTitle("終了確認");
		alert.setHeaderText("確認");
		alert.setContentText("アプリケーションを終了しますか？(データは記録されません)");
		ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);
		if (buttonType == ButtonType.CANCEL) e.consume();
	}
	/**
	 * メインインスタンスゲッター
	 * @return メインインスタンス
	 */
	public static Main getIns() {
		return mainIns;
	}
	/**
	 * ステージゲッター
	 * @return
	 */
	public static Stage getStage() {
		return stage;
	}
	/**
	 * 最後の処理
	 */
	private void finishApp() {
		Platform.exit();
	}
	/**
	 * メイン画面に遷移
	 */
	public void openMain() {
		stage.setTitle("メイン画面");
		MainController c = new MainController();
		replaceScene(c);
	}
	/**
	 * コメント入力画面に遷移する
	 */
	public void openInputer() {
		stage.setTitle("入力画面");
		SubController c = new SubController();
		replaceScene(c);
	}
	/**
	 * シーンの切り替え
	 * @param controller
	 */
	private void replaceScene(Parent controller) {
		// TODO 自動生成されたメソッド・スタブ
		Scene scene = stage.getScene();		// ステージからシーンを取得
		if (scene == null) {							// まだステージにシーンが設定されていない場合：初期画面時
			scene = new Scene(controller);		// コントローラを使用してシーンの生成
			stage.setScene(scene);					// ステージにシーンを設定
		} else {												// すでにステージにシーンが設定されている場合：画面遷移時
			scene.setRoot(controller);				// ルートノードにコントローラを設定してシーンを置換する
		}
	}
	/**
	 * 内部設計書自動生成対象ファイルを開く
	 * @param uri ファイルのURI
	 */
	public void makeSubStage(String uri) {
		subStage = new Stage();
		subStage.setResizable(false);
		subStage.setTitle("仮");
		ProvisionalController c = new ProvisionalController(uri);//仮のコントローラを設定
		Scene scene = new Scene(c);
		subStage.setX(100);
		subStage.setY(40);
		subStage.setScene(scene);
		inputModule(scene);
		subStage.setOnCloseRequest(e -> endApp(e));						// ×ボタン処理
		subStage.showingProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue == true && newValue == false) finishApp();	// ステージが非表示になったときに呼ばれる->最後に呼ばれる
		});
		subStage.show();

	}
	/**
	 * 内部設計自動生成
	 * @param fileURL
	 */
	public void inputModule(Scene scene) {
		try {
			nList = new ArrayList<>();
			GridPane g = (GridPane)scene.getRoot();//Root要素をGridPaneとして読み込む
			nList.add(g);
			nList.addAll(g.getChildren());//GridPaneの子要素をリストに追加


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			Alert alt = new Alert(AlertType.ERROR);
			alt.setTitle(errTitle);
			alt.setContentText(errText);
			e.printStackTrace();
			alt.showAndWait();
		}
	}
	/**
	 * 自動入力対象の画面の全ノードのリストを返す
	 * @return
	 */
	public List<Node> getNList(){
		return nList;
	}
	public static void main(String[] args) {
		launch(args);
	}
}

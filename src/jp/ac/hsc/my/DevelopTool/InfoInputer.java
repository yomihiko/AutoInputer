package jp.ac.hsc.my.DevelopTool;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jp.ac.hsc.my.DevelopTool.Controllers.MainController;
import jp.ac.hsc.my.DevelopTool.Controllers.ProvisionalController;
import jp.ac.hsc.my.DevelopTool.Controllers.SubController;


public class InfoInputer extends Application {
	private static InfoInputer mainIns;//インスタンス
	private static Stage stage;//メインのステージ
	private static Stage subStage;//読み込んだファイルのステージ
	private ArrayList<Node> nList;
	private ArrayList<MacrosJson> gList;
	private Iterator<MacrosJson> iteGlist;
	private Iterator<Node> iteNode;
	private Node wkNode;
	private MacrosJson wkJson;
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
	/**
	 * メインインスタンスゲッター
	 * @return メインインスタンス
	 */
	public static InfoInputer getIns() {
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
	public void InfoInput() {

	}
	public boolean hasNext() {
		return iteNode.hasNext() && iteGlist.hasNext();
	}
	public void next() {
		wkNode = iteNode.next();
		wkJson = iteGlist.next();
	}
	public void setEffect() {
		wkNode.setEffect(newDropShadow());
	}
	public void setComment(String comment) {
		wkJson.comment = comment;
	}
	public String getName() {

		return wkNode.getId();
	}
	public ArrayList<MacrosJson> getGList(){
		return gList;
	}
	public void removeEffect() {
		wkNode.setEffect(null);
	}
	private static DropShadow newDropShadow() {
		DropShadow ds = new DropShadow();
		ds.setColor(Color.RED);
		ds.setBlurType(BlurType.THREE_PASS_BOX);
		return ds;
	}
	private MacrosJson inputer(MacrosNode wkNode) {
		MacrosJson wkJSON = new MacrosJson();
		wkJSON.compName = wkNode.getComp();
		wkJSON.name = wkNode.getName();
		wkJSON.x = wkNode.getX();
		wkJSON.y = wkNode.getY();
		wkJSON.width = wkNode.getWidth();
		wkJSON.height = wkNode.getHeight();
		wkJSON.font = wkNode.getFont();
		wkJSON.fontSize = wkNode.getFontSize();
		wkJSON.yori = wkNode.getAlignment();
		wkJSON.txt = wkNode.getText();
		wkJSON.txtCo = wkNode.getTextFill();
		return wkJSON;
	}
	public static void main(String[] args) {
		launch(args);
	}
}

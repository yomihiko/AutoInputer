package jp.ac.hsc.my.DevelopTool;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class InfoInputer extends Application {
	private static AnchorPane EditMain = new AnchorPane();
	private static AnchorPane EditSub = new AnchorPane();
	private static InfoInputer mainIns;
	private final String SUB = "SubAnchorPane";
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
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,400,200);
			primaryStage.setScene(scene);
			primaryStage.resizableProperty().setValue(false);
			primaryStage.show();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			Alert alt = new Alert(AlertType.ERROR);
			alt.setTitle(errTitle);
			alt.setContentText(errText);
			alt.showAndWait();
			System.exit(ABNORMAL);
		}

	}
	public void inputModule(String fileURL) {
		try {
			Stage primaryStage = new Stage();
			EditMain = (AnchorPane)FXMLLoader.load(new URL(fileURL));
			EditSub = (AnchorPane)FXMLLoader.load(getClass().getResource("InfoInput.fxml"));
			double x = EditMain.getPrefWidth() + 300;
			Scene scene = new Scene(EditMain,x,600);
			primaryStage.setScene(scene);
			ArrayList<Node> wklIte = new ArrayList<>();//EditMain.getChildren().listIterator();
			wklIte.add(EditMain);
			EditSub.setLayoutX(EditMain.getPrefWidth());
			EditSub.setLayoutY(0);
			EditSub.setId(SUB);
			EditMain.getChildren().add(EditSub);
			wklIte.addAll(EditMain.getChildren());
			iteNode = wklIte.iterator();
			mainIns = this;
			primaryStage.showAndWait();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			Alert alt = new Alert(AlertType.ERROR);
			alt.setTitle(errTitle);
			alt.setContentText(errText);
			e.printStackTrace();
			alt.showAndWait();
		}
	}
	public static AnchorPane getMainAnchor() {
		return EditMain;
	}
	public static AnchorPane getSubAnchor() {
		return EditSub;
	}
	public static InfoInputer getIns() {
		return mainIns;
	}
	public void InfoInput(AnchorPane main,AnchorPane sub) {
		Iterator<Node> iNode = main.getChildren().iterator();
		ArrayList<MacrosJson> gList = new ArrayList<>();
		gList.add(inputer(new MacrosNode(EditMain)));
		while(iNode.hasNext()) {
			MacrosNode wkNode = new MacrosNode(iNode.next());
			MacrosJson wkJSON = new MacrosJson();
			if(Objects.equals(wkNode.getName(), SUB)) {
				continue;
			}
			wkJSON = inputer(wkNode);
			gList.add(wkJSON);
		}
		this.gList = gList;
		iteGlist = gList.iterator();
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

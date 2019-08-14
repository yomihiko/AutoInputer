package application;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.Iterator;
import java.util.StringJoiner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Button bt = new Button();
			bt.setLayoutX(100);
			bt.setPrefSize(200, 10);
			bt.setLayoutY(10);
			EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Iterator<Node> iNode = root.getChildren().iterator();
					StringJoiner sj = new StringJoiner("\n,\n","[\n","\n]");
					while(iNode.hasNext()) {
						MacrosNode wkNode = new MacrosNode(iNode.next());
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

					}
					FileDialog fd = new FileDialog(new Frame(),"",FileDialog.SAVE);
					fd.setTitle("ファイルを保存");
					fd.addNotify();
					fd.setFilenameFilter((File dir, String name) -> name.endsWith(".html"));
					fd.setVisible(true);
					System.out.println(fd.getDirectory());
					System.out.println(sj.toString());

				}
			};
			bt.setOnAction(ev);
			bt.setText("JSONファイル出力");
			root.getChildren().add(bt);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

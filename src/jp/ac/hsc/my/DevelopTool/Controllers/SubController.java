package jp.ac.hsc.my.DevelopTool.Controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jp.ac.hsc.my.DevelopTool.ILoadFxml;
import jp.ac.hsc.my.DevelopTool.MacrosJson;
import jp.ac.hsc.my.DevelopTool.MacrosNode;
import jp.ac.hsc.my.DevelopTool.Main;
import jp.ac.hsc.my.DevelopTool.NodeIterator;



public class SubController extends AnchorPane implements ILoadFxml,Initializable {

	@FXML
	private AnchorPane subAnchorMain;

	@FXML
	private Label nameLbl;

	@FXML
	private Label compLbl;

	@FXML
	private Button fileReadBtn;

	@FXML
	private TextArea infoFi;

	@FXML
    private Button nextBtn;

	 @FXML
	private Button outputBtn;

	private int index;
	private String[] haveChildren = {"GridPane","HBox","VBox","TableView"};
	private NodeIterator nIte;//ノードイテレータ
	private Node wkNode;
	private MacrosNode wkMn;
	private ArrayList<MacrosJson> mainMacros;
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
		nextBtn.setOnAction(event ->{
			onNextBtn();
		});
		outputBtn.setOnAction(event -> {
			onOutPutBtn();
			subAnchorMain.getBackground().getFills().forEach(e -> System.out.println(e.getFill()));
		});
	}
	/**
	 * 開始ボタンを押したときの挙動
	 */
	private void onStart() {
		index = 0;
		nIte = new NodeIterator(Main.getIns().getNList());
		mainMacros = new ArrayList<>();
		if(nIte.hasNext()) {
			nodePointer();

		}
	}

    private void onNextBtn() {
		mainMacros.get(index).comment = infoFi.getText();//入力値をコメントにセット
		wkNode.setEffect(null);//エフェクトを消す
		infoFi.setText("");
		if(nIte.hasNext()) {
			index++;
			nodePointer();
			if(Arrays.stream(haveChildren).anyMatch(child -> Objects.equals(child, wkMn.getComp()))) {
				Parent p = (Parent) wkNode;
				nIte.setRecursive(p.getChildrenUnmodifiable());
			}
		}
    }

    private void onOutPutBtn() {
    	Main mainIns = Main.getIns();
    	String wkst = "";
    	for(int i = 0;i < mainMacros.size();i++) {
    		wkst = wkst + mainMacros.get(i).getCSV();
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
    private void nodePointer() {
    	wkNode = nIte.next();
		wkMn = new MacrosNode(wkNode);
		MacrosJson wkJ = inputer(wkMn);
		nameLbl.setText(wkMn.getName());
		compLbl.setText(wkMn.getComp());
		mainMacros.add(wkJ);//ノード情報をリストに追加
		wkNode.setEffect(newDropShadow());
    }
    /**
     * 対象ノードに適応するエフェクトの設定
     * @return
     */
    private static DropShadow newDropShadow() {
		DropShadow ds = new DropShadow();
		ds.setColor(Color.RED);
		ds.setBlurType(BlurType.THREE_PASS_BOX);
		return ds;
	}

    /**
     * ノードの情報を取得する
     * @param wkNode
     * @return 取得したノード情報
     */
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
}


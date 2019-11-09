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

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private Button fileReadBtn;

    @FXML
    private Label nameLbl;

    @FXML
    private Button nextBtn;

    @FXML
    private Button outputBtn;

    @FXML
    private Label compLbl;

    @FXML
    private TextField rowfi;

    @FXML
    private TextField colfi;

    @FXML
    private TextField weifi;

    @FXML
    private TextField heifi;

    @FXML
    private TextField fontfi;

    @FXML
    private TextField fsfi;

    @FXML
    private TextField yorifi;

    @FXML
    private TextField valuefi;

    @FXML
    private TextField textclofi;

    @FXML
    private TextField backfi;

    @FXML
    private TextField bikofi;

    @FXML
    private Button csvBtn;

    @FXML
    private TextArea classfi;

	private String[] haveChildren = {"GridPane","HBox","VBox"};
	private NodeIterator nIte;//ノードイテレータ
	private Node wkNode;
	private MacrosNode wkMn;//減税編集中のノードの設計情報を一時的に保存する
	private ArrayList<MacrosJson> mainMacros;

	private String[] sizeInputReg =
		{".root",".timeLabel",".title",".table-view",".titleBtnText",
				".titleBtnTextSystemName","-"};//文字サイズの入力規制
	private String[] frontColorReg =
		{"Black",".timeLabel",".button",".title",".titleBtnText","-"};//前景色の入力規制
	private String[] backColorReg =
		{".root",".greenBtn",".blueBtn",".redBtn",".timeBack",".combo-box",".table-view",
				".titleBack","-"};//背景色の入力規制


	public SubController() {
		// TODO 自動生成されたコンストラクター・スタブ

		nIte = new NodeIterator(Main.getIns().getNList());//ノードイテレータ設定
		mainMacros = new ArrayList<>();//全ノードの設計情報を記録するリスト
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
		});
		fileReadBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> {
			return ! compLbl.getText().isEmpty();//コンポーネントラベルがからの時は開始前
		},compLbl.textProperty()));
		nextBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> {
			return compLbl.getText().isEmpty() || ! nIte.hasNext();
		},bikofi.textProperty()));
		outputBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> {
			return compLbl.getText().isEmpty() ||  nIte.hasNext();
		},bikofi.textProperty()));
	}
	/**
	 * 開始ボタンを押したときの挙動
	 */
	private void onStart() {
		if(nIte.hasNext()) {
			wkNode = nIte.next();//ノードを進める
			wkMn = new MacrosNode(wkNode);//ノードの設計情報
    		fi(wkMn);//テキストフィールドに値を入れる
    		wkNode.setEffect(newDropShadow());//
		}
	}

	/**
	 * 次へボタンを押したときの挙動
	 */
    private void onNextBtn() {
		wkNode.setEffect(null);//エフェクトを消す
		resetEffect();//入力フォームのエフェクトを一旦リセットする
		try {//入力値の不正をチェックする
			getNodeSettings();
		}catch (InputerException e) {
			// TODO: handle exception
    		Alert alt = new Alert(AlertType.WARNING);
    		alt.setTitle("エラー");
    		alt.setContentText(e.getMessage());
    		alt.setHeaderText("不正な入力値");
    		alt.showAndWait();
    		return; //入力値に不正がある場合はノードイテレータを進めない
		}

		if(nIte.hasNext()) {//ノードが残っているか
			nodePointerNext();
			if(Arrays.stream(haveChildren).anyMatch(child -> Objects.equals(child, wkMn.getComp()))) {
				//子持ちノードの場合は子をすべてノードイテレータに格納する
				Parent p = (Parent) wkNode;

				nIte.setRecursive(p.getChildrenUnmodifiable(),1,1);
			}
		}
    }

    private void onOutPutBtn() {
    	String wkst = "";//ファイルに書き込むCSV文字列を保存する変数
    	for(int i = 0;i < mainMacros.size();i++) {
    		wkst = wkst + mainMacros.get(i).getCSV();//データをCSV形式にする
    	}
    	FileChooser fc = new FileChooser();
    	ExtensionFilter e = new ExtensionFilter("CSVファイル", "*.csv");
		fc.getExtensionFilters().add(e);
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
    /**
     * テキストフィールドからノードの設計情報を読み込み記録する
     */
    private void getNodeSettings() throws InputerException {
	    MacrosJson mj = inputer(wkMn);
		mainMacros.add(mj);
    }
    /**
     * 次のノードへシフトする
     */
    private void nodePointerNext() {
    	wkNode = nIte.next();//ノードを進める
    	wkMn = new MacrosNode(wkNode);//ノードの設計情報
    	fi(wkMn);//テキストフィールドに値を入れる
    	wkNode.setEffect(newDropShadow());//
    }
    /**
     * 対象ノードに適応するエフェクト・入力エラーのエフェクトの設定
     * @return
     */
    private static DropShadow newDropShadow() {
		DropShadow ds = new DropShadow();
		ds.setColor(Color.RED);
		ds.setBlurType(BlurType.THREE_PASS_BOX);
		return ds;
	}
    /**
     * テキストフィールドに設計情報を自動入力する
     * @param wkNode 設計情報を含んだノード
     */
    private void fi(MacrosNode wkNode) {
    	compLbl.setText(wkNode.getComp());
    	nameLbl.setText(wkNode.getName());
    	rowfi.setText(wkNode.getX());
    	colfi.setText(wkNode.getY());
    	heifi.setText(wkNode.getHeight());
    	weifi.setText(wkNode.getWidth());
    	fontfi.setText(wkNode.getFont());
    	fsfi.setText(wkNode.getFontSize());
    	yorifi.setText(wkNode.getAlignment());
    	valuefi.setText(wkNode.getText());
    	textclofi.setText(wkNode.getTextFill());
    	backfi.setText(wkNode.getBackColor());
    	classfi.setText(wkNode.getStyleClass());
    	bikofi.setText("");
    }
    /**
     * 設計情報入力フォームのエフェクトをリセットする
     */
    private void resetEffect() {
    	rowfi.setEffect(null);
    	colfi.setEffect(null);
    	heifi.setEffect(null);
    	weifi.setEffect(null);
    	fontfi.setEffect(null);
    	fsfi.setEffect(null);
    	yorifi.setEffect(null);
    	valuefi.setEffect(null);
    	textclofi.setEffect(null);
    	backfi.setEffect(null);
    	bikofi.setEffect(null);
    }

    /**
     * ノードの情報を取得する
     * @param wkNode
     * @return 取得したノード情報
     * @throws InputerException
     */
    private MacrosJson inputer(MacrosNode wkNode) throws InputerException {

    	MacrosJson wkJson = new MacrosJson();//ノードの設計情報を格納する
    	wkJson.compName = wkNode.getComp();//コンポーネント名
    	wkJson.name = wkNode.getName();//fxid

    	wkJson.x = checkEmpty(rowfi, "row");//GridRow
    	wkJson.y = checkEmpty(colfi, "col");//GridColumn
    	wkJson.width = checkEmpty(weifi, "幅");//ノードの幅
    	wkJson.height = checkEmpty(heifi,"高さ");//ノードの高さ
    	wkJson.font = checkEmpty(fontfi, "font");//ノードのフォント
    	wkJson.fontSize = checkReg(fsfi, "fontsize", sizeInputReg);//ノードのフォントサイズ
    	wkJson.yori = checkEmpty(yorifi, "寄り");//寄り
    	wkJson.txt = checkEmpty(valuefi, "値");//表示をしている値
    	wkJson.txtCo = checkReg(textclofi, "前景色", frontColorReg);//前景色
    	wkJson.backCo = checkReg(backfi, "背景色", backColorReg);
    	wkJson.comment = checkEmpty(bikofi, "備考");


    	return wkJson;
	}
    /**
     * フォームが空でないか検査する
     * @param tf 検査対象のテキストフィールド
     * @param kind フォームの内容の名
     */
    private String checkEmpty(TextField tf,String kind) throws InputerException {
    	if(tf.getText().isEmpty()) {
    		tf.setEffect(newDropShadow());//対象フィールドにエフェクトを設定
    		throw new InputerException(kind + "が未入力です。");
    	}
    	return tf.getText().strip();
    }
    /**
     * フォームの入力値が入力規制に適応しているか検査する
     * @param tf 検査対象のテキストフィールド
     * @param kind フォームの内容の名
     * @param regs 入力規制の文字列配列
     */
    private String checkReg(TextField tf,String kind,String[] regs) throws InputerException {
    	String st = checkEmpty(tf, kind);
    	if(Arrays.stream(regs).anyMatch(reg -> reg.equals(st)) == false){
    		tf.setEffect(newDropShadow());//対象フィールドにエフェクトを設定
    		throw new InputerException(kind + "の値が入力規制の条件を満たしていません。");
    	}
    	return st.strip();
    }

}
class InputerException extends Exception{
	private String msg;
	public InputerException(String msg) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}
}


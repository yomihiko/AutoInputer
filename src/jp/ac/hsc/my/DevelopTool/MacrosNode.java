package jp.ac.hsc.my.DevelopTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
/**
 *
 *内部設計書自動入力ツール用のクラス
 */
public class MacrosNode{
	private Node node;										//対象ノード
	private Map<String,String> color = Map.of(
				"0x333333ff","Black",
				"0xff0000ff","Red",
				"0xffffffff","White"
			);
	private static final int ZERO = 0;						//ゼロ
	private static final String NOT = "-";					//適切な値がない時
	private static final String POINT = "\\.";				//小数点
	private static final String PANE = "GridPane";		//rootPaneの種類
	private static final String GETWIDTH = "getWidth";		//幅を取得するメソッド名
	private static final String GETHEIGHT = "getHeight";	//高さを取得するメソッド名
	private static final String GETFONT = "getFont";		//高さを取得するメソッド名
	private static final String GETTEXT = "getText";
	private static final String GETTEXTFI = "getTextFill";
	private static final String FONT = "Meiryo";			//規定フォント
	private static final String FONTJP = "メイリオ";		//規定フォント日本語名
	private static final String FONTBOLD = "Bold";
	private static final String FONTBOLDJP = "太";
	private static final String NOTHING = "";
	public MacrosNode(Node node) {
		this.node = node;
	}
	/**
	 * コンポーネント名を返す
	 * @return コンポーネント名
	 */
	public String getComp() {
		return node.getClass().getSimpleName();
	}
	/**
	 * 変数名を返す
	 * @return 変数名
	 */
	public String getName() {
		String st = node.getId();
		if(st == null) return "fx:idが設定されていません";
		return node.getId();
	}
	/**
	 * X座標を返す
	 * @return X座標
	 */
	public String getX() {
		if(Objects.equals(getComp(), PANE)) {
			return NOT;
		}
		try {
			return Integer.toString(GridPane.getColumnIndex(node));
		}catch (Exception e) {
			// TODO: handle exception
			return NOT;
		}
	}
	/**
	 * Y座標を返す
	 * @return Y座標
	 */
	public String getY() {
		if(Objects.equals(getComp(), PANE)) {
			return NOT;
		}
		try {
			return Integer.toString(GridPane.getRowIndex(node));
		}
		catch (Exception e) {
			// TODO: handle exception
			return NOT;
		}
	}
	/**
	 * 幅を返す
	 * @return 幅
	 */
	public String getWidth() {
		if(Objects.equals(getComp(), PANE)) {
			return NOT;
		}
		return removeDecimal(methodRun(GETWIDTH));
	}
	/**
	 * 高さを返す
	 * @return 高さ
	 */
	public String getHeight() {
		if(Objects.equals(getComp(), PANE)) {
			return NOT;
		}
		return removeDecimal(methodRun(GETHEIGHT));
	}
	/**
	 * フォントを返す
	 * @return フォント名
	 */
	public String getFont() {

		if(Objects.equals(methodRun(GETFONT), NOT)) {//フォントがない時
			return NOT;
		}
		Font f = (Font) methodRun(GETFONT);
		if(Objects.equals(f.getFamily(), FONT)) {//メイリオの時は日本語で返す
			return FONTJP;
		}
		return f.getFamily();
	}
	/**
	 * フォントサイズを返す
	 * @return フォントサイズ
	 */
	public String getFontSize() {

		if(Objects.equals(methodRun(GETFONT), NOT)) {//フォントがない時
			return NOT;
		}
		Font f = (Font) methodRun(GETFONT);
		String wkst = removeDecimal(f.getSize());//フォントサイズ
		if(Objects.equals(f.getStyle(),FONTBOLD)) {//太字の時
			wkst = wkst + FONTBOLDJP;
		}
		return wkst;
	}
	/**
	 * 寄せを返す
	 * @return 寄せ
	 */
	public String getAlignment() {

		if(Objects.equals(getComp(), PANE)) {
			return NOT;
		}
		try {
			return GridPane.getHalignment(node).toString();
		}
		catch (Exception e) {
			// TODO: handle exception
			return NOT;
		}
	}
	/**
	 * テキストを返す
	 * @return テキスト
	 */
	public String getText() {
		if(Objects.equals(methodRun(GETTEXT), NOT)) {//テキストがない時
			return NOT;
		}
		if(Objects.equals(methodRun(GETTEXT).toString(), NOTHING)) {
			return NOT;
		}
		return methodRun(GETTEXT).toString();
	}
	/**
	 * 文字色を返す
	 * @return 文字色
	 */
	public String getTextFill() {
		if(Objects.equals(methodRun(GETTEXTFI), NOT)) {//テキストがない時
			return NOT;
		}
		String colorCode = methodRun(GETTEXTFI).toString();
		String c = color.get(colorCode);
		if(c != null) return c;//カラーコード対応マップに存在する色の場合は英単語で返す
		return colorCode;
	}
	/**
	 * 背景色を返す
	 * @return 背景色
	 */
	public String getBackColor() {
		if(Objects.equals(methodRun("getBackground"), NOT)) {//テキストがない時
			return NOT;
		}
		Background bk = (Background)methodRun("getBackground");
		String re = NOT;
		if(bk != null && bk.getFills().size() > 0) {
			re = bk.getFills().get(0).getFill().toString();
		}
		if(re.length() >= 11) {
			re = NOT;
		}
		String c = color.get(re);
		if(c != null) return c;//カラーコード対応マップに存在する色の場合は英単語で返す
		return re;
	}

	public String getStyleClass() {
		if(Objects.equals(methodRun("getStyleClass"), NOT)) {//テキストがない時
			return NOT;
		}
		if(methodRun("getStyleClass").toString() == null) {
			return NOT;
		}
		if(methodRun("getStyleClass").toString().equals("root")) {
			return NOT;
		}
		//ObservableList<String> style = (ObservableList<String>) methodRun(GETTEXT);
		//return String.join("\n", style);
		String st = methodRun("getStyleClass").toString();
		st = st.replaceAll(" ", "\n");
		return st;
	}
	/**
	 * メソッド実行
	 * @param methodName メソッド名
	 * @return 実行した結果　メソッドが見つからな型場合はNOT
	 */
	private Object methodRun(String methodName) {
		try {
			Method m = node.getClass().getMethod(methodName);
			return m.invoke(node);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			return NOT;
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			return NOT;
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			return NOT;
		}

	}
	/**
	 * 小数点以下を排除
	 * @param d 小数
	 * @return 小数点以下を排除した文字列
	 */
	private String removeDecimal(Object d) {
		return d.toString().split(POINT)[ZERO];
	}
}

package jp.ac.hsc.my.DevelopTool;

import java.util.Iterator;
import java.util.List;

import javafx.scene.Node;

/**
 * 読み込んだファイルのノードのイテレータクラス
 * @author yomihiko
 *
 */
public class NodeIterator implements Iterator<Node> {

	private int index;//イテレータ用のインデックス
	private List<Node> list;//ノードを格納するインデックス
	public NodeIterator(List<Node> li) {
		// TODO 自動生成されたコンストラクター・スタブ
		list = li;
		index = 0;
	}
	@Override
	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return index < list.size();
	}

	@Override
	public Node next() {
		// TODO 自動生成されたメソッド・スタブ
		Node n = list.get(index);
		index++;
		return n;
	}

}

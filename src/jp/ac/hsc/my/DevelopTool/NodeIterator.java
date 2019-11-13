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
	private NodeIterator recursive ;//子要素を持つノードを入れ子にする
	public NodeIterator(List<Node> li) {
		// TODO 自動生成されたコンストラクター・スタブ
		list = li;
		index = -1;
	}

	@Override
	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		if(hasChildren()) {
			return recursive.hasNext();
		}
		return index + 1 < list.size();
	}

	@Override
	public Node next() {
		// TODO 自動生成されたメソッド・スタブ
		if(hasChildren()) {
			return recursive.next();
		}
		index++;
		Node n = list.get(index);
		return n;
	}
	/**
	 * 子要素を持つノードを入れ子にする
	 * @param li
	 */
	public void setRecursive(List<Node> li) {
		recursive = new NodeIterator(li);
	}

	private boolean hasChildren() {
		return recursive != null && recursive.hasNext();
	}



}

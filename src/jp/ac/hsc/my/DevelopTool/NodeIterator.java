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
	private int row;
	private int column;
	public NodeIterator(List<Node> li) {
		// TODO 自動生成されたコンストラクター・スタブ
		list = li;
		index = 0;
		row = -1;
		column = -1;
	}
	public NodeIterator(List<Node> li,int row,int column) {
		// TODO 自動生成されたコンストラクター・スタブ
		list = li;
		index = 0;
		this.row = row;
		this.column = column;
	}
	@Override
	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		if(recursive != null && recursive.hasNext()) {
			return true;
		}
		return index < list.size();
	}

	@Override
	public Node next() {
		// TODO 自動生成されたメソッド・スタブ
		if(recursive != null && recursive.hasNext()) {
			return recursive.next();
		}
		Node n = list.get(index);
		index++;
		return n;
	}
	/**
	 * 子要素を持つノードを入れ子にする
	 * @param li
	 */
	public void setRecursive(List<Node> li,int row,int column) {
		recursive = new NodeIterator(li,row,column);
	}


}

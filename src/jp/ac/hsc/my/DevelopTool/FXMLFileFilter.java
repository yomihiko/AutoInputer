package jp.ac.hsc.my.DevelopTool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FXMLFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		// TODO 自動生成されたメソッド・スタブ
		if(f.isDirectory()) {
			return true;
		}
		String wkfn = f.getName();
		if(wkfn.matches(".+\\.fxml")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO 自動生成されたメソッド・スタブ
		return "FXMLファイル";
	}

}

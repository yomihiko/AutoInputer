package jp.ac.hsc.my.DevelopTool;

import java.util.StringJoiner;

public class MacrosJson {
	public String compName;
	public String name;
	public String x;
	public String y;
	public String width;
	public String height;
	public String font;
	public String fontSize;
	public String yori;
	public String txt;
	public String txtCo;
	public String backCo;
	public String comment = "-";
	public String getCSV() {
		StringJoiner sj = new StringJoiner(",","","\n");
		sj.add(compName);
		sj.add(name);
		sj.add(x);
		sj.add(y);
		sj.add(width);
		sj.add(height);
		sj.add(font);
		sj.add(fontSize);
		sj.add(yori);
		sj.add(txt);
		sj.add(txtCo);
		sj.add(backCo);
		sj.add(comment);
		return sj.toString();
	}
}

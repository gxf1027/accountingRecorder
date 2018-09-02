package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;

public class StatByCategory implements Serializable {

	private static final long serialVersionUID = 7135372750943988767L;
	private String category_dm;
	private String category_mc;
	private String je;
	private String nd;

	public String getCategory_dm() {
		return category_dm;
	}

	public void setCategory_dm(String category_dm) {
		this.category_dm = category_dm;
	}

	public String getCategory_mc() {
		return category_mc;
	}

	public void setCategory_mc(String category_mc) {
		this.category_mc = category_mc;
	}

	public String getJe() {
		return je;
	}

	public void setJe(String je) {
		this.je = je;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	@Override
	public String toString() {
		return "StatByCategory [category_dm=" + category_dm + ", category_mc=" + category_mc + ", je=" + je + ", nd="
				+ nd + "]";
	}

}

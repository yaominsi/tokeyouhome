package com.fujisan.model;

import java.io.Serializable;
/**
 * 结点信息
 * @author siyaomin
 *
 */
public class PointModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double pointx;
	private Double pointy;
	public PointModel(){}
	public PointModel(Double x,Double y){
		this.pointx=x;
		this.pointy=y;
	}
	public Double getPointx() {
		return pointx;
	}
	public void setPointx(Double pointx) {
		this.pointx = pointx;
	}
	public Double getPointy() {
		return pointy;
	}
	public void setPointy(Double pointy) {
		this.pointy = pointy;
	}
}

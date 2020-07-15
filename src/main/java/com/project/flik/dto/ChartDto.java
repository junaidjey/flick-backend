package com.project.flik.dto;

import java.util.Arrays;

public class ChartDto {
	private int[] data;
    private String label;
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "ChartDto [data=" + Arrays.toString(data) + ", label=" + label + "]";
	}
	
}

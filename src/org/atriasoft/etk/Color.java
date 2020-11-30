package org.atriasoft.etk;

public class Color {
	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}
	public float r;
	public float g;
	public float b;
	public float a;
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	public Color(float r, float g, float b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0f;
	}
}

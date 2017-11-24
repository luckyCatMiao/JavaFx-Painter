package Bean;

import javafx.scene.canvas.Canvas;

public class CanvasLayer {

	private Canvas canvas;
	private String name;

	public CanvasLayer(Canvas canvas, String name) {
		this.canvas = canvas;
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "CanvasLayer [" + (canvas != null ? "canvas=" + canvas + ", " : "")
				+ (name != null ? "name=" + name : "") + "]";
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public CanvasLayer clone()
	{


		return new CanvasLayer(canvas, name);
	}
	
	
}

package Manager;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

import Bean.CanvasLayer;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by Administrator on 10/15/2017.
 */
public class Painter {


    static private Painter painter;
    static public Painter GetInstance()
    {
        if(painter==null)
        {
            painter=new Painter();
        }
        return painter;
    }



    /**
     * drawType
     */
    private PaintType type=PaintType.PEN;
    /**
     *
     */
    private boolean mousePress=false;
    /**
     * canvas
     */
    private Canvas currentCanvas;

    private ObservableList<CanvasLayer> canvaList=FXCollections.observableArrayList();

    public double lastX = 0.0;
    public double lastY = 0.0;
	private Canvas backCanvas;
	private Paint strokeColor=Color.BLACK;
	private double lineWidth=1;
	private double alpha=1;
	private double eraserSize=1;
	private Paint fillColor=Color.WHITE;
	private int autoGrowID=1;



    public void mousePress(double x, double y) {
    	
        lastY=y;
        lastX=x;
        mousePress=true;
        
        if(type==PaintType.BUCKET)
        {
        	fill();
        }
    }

    private void fill() {
		currentCanvas.getGraphicsContext2D().fillRect(0, 0, currentCanvas.getWidth(), currentCanvas.getHeight());
		
	}

	public void mouseMove(double x, double y) {

	
       if(type==PaintType.PEN&&mousePress)
       {
    	 
            drawLine(lastX,lastY,x,y);
           
            
       }
       else if(type==PaintType.ERASER&&mousePress)
       {
    	   clearRect(x,y);
       }

       lastX=x;
       lastY=y;
       
    }

    
    private void clearRect(double x, double y) {
    	 currentCanvas.getGraphicsContext2D().clearRect(x, y, eraserSize, eraserSize);
		
	}

	private void drawLine(double lastX, double lastY, double x, double y) {

	
        currentCanvas.getGraphicsContext2D().strokeLine(lastX,lastY,x,y);
      
    }

    public void mouseUp(double x, double y) {
        mousePress=false;
    }

    public void setCanvas(Canvas canvas) {
        this.currentCanvas =canvas;
    }

	public void setType(PaintType type) {
	
		this.type=type;
		
	}

	public void redrawBackCanvas() {

		backCanvas.getGraphicsContext2D().fillRect(0, 0, backCanvas.getWidth(), backCanvas.getHeight());
		
	}

	private void applyPaint() {
		//apply Paint setting to current canvas
		if(this.currentCanvas!=null)
		{
			GraphicsContext graphicsContext2D = currentCanvas.getGraphicsContext2D();
			graphicsContext2D.setStroke(this.strokeColor);
			graphicsContext2D.setFill(this.fillColor);
			graphicsContext2D.setLineWidth(lineWidth);
			graphicsContext2D.setGlobalAlpha(alpha);
		}
		
	}

	public void addBackCanvas(Canvas back) {
		this.backCanvas=back;
		backCanvas.getGraphicsContext2D().setFill(fillColor);
		redrawBackCanvas();
	}

	

	public ObservableList<CanvasLayer> getCanvasLayerList() {
		// TODO Auto-generated method stub
		return canvaList;
	}

	public void addCanvas(Canvas canvas) {
		
	
		canvaList.add(new CanvasLayer(canvas,generageCanvasName()));
		
	}

	private String generageCanvasName() {
		
		
		return "图层"+(autoGrowID++);
	}

	public void setStroke(Color value) {
		this.strokeColor=value;
		applyPaint();
	}

	public void setLineWidth(double value) {
		this.lineWidth=value;
		applyPaint();
		
	}

	public void setAlpha(double alpha) {
		this.alpha=alpha/100.0;
		applyPaint();
	}

	public void setEraserWidth(double eraserWidth) {
		this.eraserSize = eraserWidth;
		applyPaint();
		
	}

	public void setFill(Color fillColor) {
		this.fillColor = fillColor;
		applyPaint();
		
	}

	public void deleteCanvas(CanvasLayer item) {
		
		this.canvaList.remove(item);
		
	}

	/**
	 * set the current canvas to be draw
	 * @param canvasLayer
	 */
	public void setCurrentCanvas(CanvasLayer canvasLayer) {
	

			if(canvaList.contains(canvasLayer))
			{
				
				this.currentCanvas=canvasLayer.getCanvas();
				applyPaint();
			}
			else if(canvasLayer==null)
			{
				this.currentCanvas=null;
			}
			else {
				
				throw new InvalidParameterException();
			}

	}

	public Canvas getcurrentCanvas() {
		// TODO Auto-generated method stub
		return currentCanvas;
	}

	public void clearCanvas() {
		
		if(currentCanvas!=null)
		{
			currentCanvas.getGraphicsContext2D().clearRect(0, 0, currentCanvas.getWidth(), currentCanvas.getHeight());
		}
		
	}

	public void drawImage(Image image) {
		
		if(currentCanvas!=null)
		{
			currentCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
		}
	}
}

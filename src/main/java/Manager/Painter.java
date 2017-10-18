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
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;

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

    private double lastX = 0.0;
    private double lastY = 0.0;
    
    
    /**
     * current canvas
     */
    private Canvas currentCanvas;
    /**
     * the back canvas
     */
	private Canvas backCanvas;
	/**
	 * tempCanvas,used for temp draw such as rect tool,line tool which need to indicate what will
	 * be draw
	 */
	private Canvas tempCanvas;
	/**
	 * 
	 */
	private int autoGrowID=1;
	
	/**
	 * save all canvas beside temp and back canvas
	 */
    private ObservableList<CanvasLayer> canvaList=FXCollections.observableArrayList();

    
    
    /**
     * stroke color,used for pen
     */
	private Paint strokeColor=Color.BLACK;
	/**
	 * line width
	 */
	private double lineWidth=1;
	/**
	 * draw alpha
	 */
	private double alpha=1;
	/**
	 * the size of the eraser
	 */
	private double eraserSize=1;
	/**
	 * fill color
	 */
	private Paint fillColor=Color.BLACK;


	private double shapeDrawStartX;


	private double shapeDrawStartY;


	private StrokeLineCap lineCap;


	private double lineSpace;


	private boolean needFill;


	private double arc;
	



    public void mousePress(double x, double y) {
    	
        lastY=y;
        lastX=x;
        mousePress=true;
        
        if(type==PaintType.BUCKET)
        {
        	fill();
        }
        else if(type==PaintType.LINE)
        {
        	shapeDrawStartX=x;
        	shapeDrawStartY=y;
        }
        else if(type==PaintType.CIRCLE)
        {
        	shapeDrawStartX=x;
        	shapeDrawStartY=y;
        }
        else if(type==PaintType.RECT)
        {
        	shapeDrawStartX=x;
        	shapeDrawStartY=y;
        }
    }

    private void fill() {
		currentCanvas.getGraphicsContext2D().fillRect(0, 0, currentCanvas.getWidth(), currentCanvas.getHeight());
		
	}

	public void mouseDrag(double x, double y) {

	
       if(type==PaintType.PEN&&mousePress)
       {
            drawLine(lastX,lastY,x,y);  
       }
       else if(type==PaintType.ERASER&&mousePress)
       {
    	   clearRectBySize(x,y);
       }
       else if(type==PaintType.LINE)
       {
    	   clearCanvas(tempCanvas);
    	   drawLine(shapeDrawStartX, shapeDrawStartY, x, y,tempCanvas);
       }
       else if(type==PaintType.CIRCLE)
       {
//    	   clearCanvas(tempCanvas);
//    	   d(shapeDrawStartX, shapeDrawStartY, x, y,tempCanvas);
       }
       else if(type==PaintType.RECT)
       {
    	   clearCanvas(tempCanvas);
    	   drawRect(shapeDrawStartX, shapeDrawStartY, x-shapeDrawStartX, y-shapeDrawStartY,tempCanvas,!needFill);
       }
       

       lastX=x;
       lastY=y;
       
    }

    
    private void drawRect(double x, double y, double width, double height, Canvas canvas,boolean isStroke) {
    	
    	if(isStroke)
    	{
    		if(arc!=0)
    		{
    			canvas.getGraphicsContext2D().strokeRoundRect(x, y, width, height, arc, arc);
    		}
    		else
    		{
    			canvas.getGraphicsContext2D().strokeRect(x, y, width, height);
    		}
    		
    	}
    	else
    	{
    		if(arc!=0)
    		{
    			canvas.getGraphicsContext2D().fillRoundRect(x, y, width, height, arc,arc);
    		}
    		else
    		{
    			canvas.getGraphicsContext2D().fillRect(x, y, width, height);
    		}
    		
    	}
		
	}

	private void clearRectBySize(double width, double height) {
	
    	 currentCanvas.getGraphicsContext2D().clearRect(width, height, eraserSize, eraserSize);
	}

	private void drawLine(double lastX, double lastY, double x, double y) {
       drawLine(lastX, lastY, x, y,currentCanvas);
    }

    private void drawLine(double lastX, double lastY, double x, double y, Canvas canvas) {
    	canvas.getGraphicsContext2D().strokeLine(lastX,lastY,x,y);
		
	}

	public void mouseUp(double x, double y) {
        mousePress=false;
        //clear temp canvas
        clearCanvas(tempCanvas);
        if(type==PaintType.LINE)
        {
     	   drawLine(shapeDrawStartX, shapeDrawStartY, x, y,currentCanvas);
        }
        else if(type==PaintType.RECT)
        {
        	drawRect(shapeDrawStartX, shapeDrawStartY, x-shapeDrawStartX, y-shapeDrawStartY, currentCanvas,!needFill);
        }
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
			graphicsContext2D.setLineCap(lineCap);
			graphicsContext2D.setLineDashes(lineSpace);
			
		}
		
	}

	public void setBackCanvas(Canvas back) {
		this.backCanvas=back;
		backCanvas.getGraphicsContext2D().setFill(Color.WHITE);
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

	public void clearCanvas(Canvas canvas) {
		
		if(canvas!=null)
		{
			canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
		
	}

	public void drawImage(Image image) {
		
		if(currentCanvas!=null)
		{
			currentCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
		}
	}

	public void setTempCanvas(Canvas canvas) {

		this.tempCanvas=canvas;
		tempCanvas.getGraphicsContext2D().setFill(Color.BLACK);
		tempCanvas.getGraphicsContext2D().setStroke(Color.BLACK);

	}

	public void clearCurrentCanvas() {
		clearCanvas(currentCanvas);
		
	}

	public void setStrokeLineCap(StrokeLineCap value) {
		
		this.lineCap=value;
		applyPaint();
	}

	public void setLineSpace(double newValue) {
		this.lineSpace=newValue;
		applyPaint();
		
	}

	public void mouseMove(double x, double y) {
		if(type==PaintType.ERASER)
		{
			 clearCanvas(tempCanvas);
	    	 drawRect(x-eraserSize/2, y-eraserSize/2, eraserSize, eraserSize, tempCanvas,true); 
	    	 drawRect(x-eraserSize/2, y-eraserSize/2, eraserSize, eraserSize, tempCanvas,false); 
		}
		
	}

	public void setNeedFill(boolean selected) {
		this.needFill=selected;
		
	}

	public void setArc(double doubleValue) {
		this.arc=doubleValue;
	}
}

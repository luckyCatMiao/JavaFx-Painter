package Manager;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

import Bean.ToolIcon;
import Controller.ToolController.BaseToolController;
import Controller.ToolController.Tool_Bucket_Controller;
import Controller.ToolController.Tool_Eraser_Controller;
import Controller.ToolController.Tool_Pen_Controller;
import Paint.Config;
import Tool.Tool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by Administrator on 10/15/2017.
 */
public class ToolManager {

    private static HashMap<PaintType, Parent> map=new HashMap<>();
	private static List<ToolIcon> list;

	/**
	 * init the tool list
	 */
    static
    {
    	  list= FXCollections.observableArrayList();
          list.add(new ToolIcon("铅笔",Tool.loadImage("pen")));
          list.add(new ToolIcon("橡皮擦",Tool.loadImage("eraser")));
          list.add(new ToolIcon("油漆桶",Tool.loadImage("bucket")));
          list.add(new ToolIcon("线段",Tool.loadImage("line")));
          list.add(new ToolIcon("椭圆",Tool.loadImage("circle")));
          list.add(new ToolIcon("矩形",Tool.loadImage("rect")));
          
          
    }
    
    /**
     * get all tools
     * @return
     */
	public static List<ToolIcon> getTools() {
        return list;
    }

	/**
	 * return the enum type of tool by it's corresponding name
	 * @param text
	 * @return
	 */
	public static PaintType getTypeByName(String text) {
		PaintType result;
		
		switch (text) {
		case "铅笔":
			result=PaintType.PEN;
			break;
		case "橡皮擦":
			result=PaintType.ERASER;
			break;
		case "油漆桶":
			result=PaintType.BUCKET;
			break;
		case "线段":
			result=PaintType.LINE;
			break;
		case "椭圆":
			result=PaintType.CIRCLE;
			break;
		case "矩形":
			result=PaintType.RECT;
			break;
			
		default:
			throw new InvalidParameterException();
		}
		
		return result;
	}

	
	/**
	 * return setting pane base on tool,the first load will initial the corresponding pane
	 * @param type
	 * @return
	 */
	public static Parent getToolPaneRootByType(PaintType type) {
		if(map.get(type)==null)
		{
			switch (type) {
			case PEN:
				map.put(type, Tool.loadFxml("Tool_Pen",null));
				break;
			case ERASER:
				map.put(type, Tool.loadFxml("Tool_Eraser",null));
				break;
			case BUCKET:
				map.put(type, Tool.loadFxml("Tool_Bucket",null));
				break;
			case LINE:
				map.put(type, Tool.loadFxml("Tool_Line",null));
				break;
			case CIRCLE:
				map.put(type, Tool.loadFxml("Tool_Circle",null));
				break;
			case RECT:
				map.put(type, Tool.loadFxml("Tool_Rect",null));
				break;
			default:
				throw new InvalidParameterException();
			}
		}
		
			return map.get(type);
		
	}
}

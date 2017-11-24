package Manager;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

public class EffectFactory {

	private static final Map<Color, DropShadow> drapShawowMap=new HashMap<>();

	public static Effect getDrapShadow(Color color) {
		
		if(drapShawowMap.get(color)==null)
		{
			drapShawowMap.put(color, new DropShadow(10,color));
		}
		
		return drapShawowMap.get(color);
	}

}

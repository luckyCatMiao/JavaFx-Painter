package Bean;

import javafx.scene.image.Image;

/**
 * Created by Administrator on 10/15/2017.
 */
public class ToolIcon {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
	private Image image;

    public ToolIcon(String name, Image image) {
        this.name = name;
		this.image = image;
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	
    

}

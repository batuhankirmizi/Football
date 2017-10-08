package Engine;

import java.awt.*;

public class Text{
	String text;
	int xpos,ypos;
	int font=Font.PLAIN;
	int size=20;
	Color color=Color.WHITE;
	final String identity;
	public Text(String identity,int xpos,int ypos){
		this.identity=identity;
		this.xpos=xpos;
		this.ypos=ypos;
	}
	public Text(String identity,int xpos,int ypos,int size,int font){
		this(identity,xpos,ypos);
		this.size=size;
		this.font=font;
	}
	public void render(Graphics g){
		g.setColor(color);
		g.setFont(new Font("Sathu", font, size));
		g.drawString(text, xpos, ypos);
	}
}

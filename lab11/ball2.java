package lab11;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.ImageIcon;

public class ball2 extends ball1{
    Image img;
    ball2(){
        String imageLocation = "ball2.png";
        URL imageURL1 = this.getClass().getResource(imageLocation);
        img = Toolkit.getDefaultToolkit().getImage(imageURL1);
        runner.start();
    }
    Thread runner = new Thread(new Runnable() {
	public void run() {
		while(true){
                    x -= 2;
                    if(x <= -50){
			img = null;
			runner = null;
			x = +1100;
			y = +500;
                    }
                    try{
			runner.sleep(10);
                    }catch(InterruptedException e){}
                }
            }
    });

    public Image getImage(){
	return img;
    }
}

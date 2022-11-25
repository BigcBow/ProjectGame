package lab11;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class playstate1 extends JPanel implements ActionListener{
    
	private final ImageIcon imgstate1 = new ImageIcon(this.getClass().getResource("bg2.png"));
	meleon m = new meleon();
        
	homegames hg = new homegames();
	ImageIcon feildover = new ImageIcon(this.getClass().getResource("bgover2.jpg"));
	ImageIcon img_paralyze = new ImageIcon(this.getClass().getResource("7.png"));
	ImageIcon exitover = new ImageIcon(this.getClass().getResource("exit.png"));
	ImageIcon restart = new ImageIcon(this.getClass().getResource("start.png"));
        JButton BStartover = new JButton(restart);
	JButton BExitover  = new JButton(exitover);
	
	private JLabel score = new JLabel(); 
	public ArrayList<Fireball> fireball = new ArrayList<Fireball>();
	public ArrayList<ball1> ba1 = new ArrayList<ball1>();
	public ArrayList<ball2> ba2 = new ArrayList<ball2>();
	public ArrayList<ball5> ba5 = new ArrayList<ball5>();
        
	public int times ;
	public int HP = 3;
	public int rs1 = 1;
	public int rs2 = 2;
	boolean timestart = true;
	boolean startball = false;
	
	private gameover gover = new gameover();
	public int scor = 0;
	boolean paralyze1 = false;
	int time_paralyze=5;
        int height = 100;
	
	Thread time = new Thread(new Runnable(){
            public void run(){
		while(true){
                    try{
			Thread.sleep(10);
                    }catch(Exception e){ }
                    
                    if(timestart == false){
			repaint();
                    }
		}
            }
	});
	
	Thread actor = new Thread(new Runnable(){
            public void run(){
		while(true){
                	try{
                            Thread.sleep(1);
			}catch(Exception e){}
                            repaint();
		}
            }
	});
	Thread tballs1 = new Thread(new Runnable(){
            public void run() {
                while(true){
                	try{
                            if(startball == false){
				Thread.sleep((long)(Math.random()*10000)+2000);
                            }
			}catch(InterruptedException e){
                            e.printStackTrace();
			}
			if(startball == false){
                            ba1.add(new ball1());
			}
		}
            }
	});
	Thread tballs2 = new Thread(new Runnable(){
            public void run() {
		while(true){
			try{
                            if(startball==false){
				Thread.sleep((long)(Math.random()*10000)+2000);
                            }
			}catch(InterruptedException e){
                            e.printStackTrace();
			}
			if(startball == false){
                            ba2.add(new ball2());
			}
		}
            }
	});
	
	Thread tballs5 = new Thread(new Runnable(){
            public void run() {
            	while(true){
			try{
                            if(startball==false){
                                Thread.sleep((long)(Math.random()*10000)+2000);
                            }
			}catch(InterruptedException e){
                            e.printStackTrace();
			}
			if(startball == false){
                            ba5.add(new ball5());
			}
		}
            }
	});
	Thread paralyze = new Thread(new Runnable(){
            public void run(){
		while (true){
			if(time_paralyze < 1){
                            paralyze1 = false;
                            time_paralyze = 5;
			}
			try{
                            Thread.sleep(5000);
			}catch(InterruptedException e){e.printStackTrace();}
		}
            }
	});
        Thread t = new Thread(new Runnable(){
            public void run() {
		while(true){
                	if(timestart == false){
                            times = (times-1) ;
                            if(paralyze1){
				time_paralyze--;
                            }
			}
			try{
                            Thread.sleep(1000);
			}catch(InterruptedException e)
			{
                            e.printStackTrace();
			}
		}
            }
	});
	
	playstate1(){
		this.setFocusable(true);
		this.setLayout(null);
		this.add(score);
		
		this.addKeyListener(new KeyAdapter(){
                    public void keyPressed(KeyEvent e){
                        int a = e.getKeyCode();
			if(!paralyze1){
			    if(a==KeyEvent.VK_A){
			     	m.x-=10;
                                m.count++;
                            }
                            else if(a == KeyEvent.VK_D){
                               m.x+=10;
                               m.count++;
			   }
                            if(m.count>3){
                                m.count=0;
                            }
                            else if(a == KeyEvent.VK_UP){
                               m.count=5;
			       fireball.add(new Fireball(m.x,550));
			    }
			}
                    }
                    public void keyReleased(KeyEvent e){
			m.count=0;
		    }
		});
		m.x = 400;
		time.start();
		actor.start();
		t.start();
		tballs1.start();
		tballs2.start();
		tballs5.start();
		paralyze.start();
	}
	
	
	
	public void paintComponent(Graphics g){
            super.paintComponent(g);
            if(times <= 0 || HP<=0){
                this.setLayout(null);
                g.drawImage(feildover.getImage(),0,0,1000,800,this);
		g.setColor(Color.BLACK);
                g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,40));		
                g.drawString("SCORE   "+scor,380,200);	     
                g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,70));
                g.drawString("GAME OVER",290,150);
				    
            }else{
                g.drawImage(imgstate1.getImage(),0,0,1000,800,this);
                if(paralyze1){
                    g.setColor(Color.RED);
                    g.setFont(new Font("Hobo Std",Font.BOLD,50));
                    g.drawImage(img_paralyze.getImage(), m.x, 550,100,150, this);
                    g.drawString("-10",m.x+100,650);
                    g.drawString("AHHHH !!!", m.x+100, 560);
                }else{
                    g.drawImage(m.im[m.count].getImage(), m.x, 550,110,160, this);
                }
		if(m.x<0){
                    m.x=this.getWidth()-10;
		}
		if(m.x>this.getWidth()){
                    m.x=20;
		}
		for(int i=0;i<fireball.size();i++){
		    Fireball ba = fireball.get(i);
                    g.drawImage(ba.imfire[ba.count%5].getImage(), ba.x, ba.y,50,50, null);
		    ba.move();
                    ba.count++;
		    if(ba.y<0){
		    	fireball.remove(i);
		    }
		}
		  
		//========================================ball1================= 
                for(int i=0 ; i<ba1.size();i++){
                    g.drawImage(ba1.get(i).getImage(),ba1.get(i).getX(),ba1.get(i).getY(),100,100,this);
 		}
		for(int i=0 ; i<fireball.size();i++){
		    for(int j=0 ; j<ba1.size();j++){
		    	if(Intersect(fireball.get(i).getbound(),ba1.get(j).getbound())){
			    ba1.remove(j);
			    fireball.remove(i);
			    scor += 10;
			    g.drawString("+10",m.x+100,650);
			} 
		    }
		}
		//========================ball2=========================
		for(int i=0 ; i<ba2.size();i++){
		    g.drawImage(ba2.get(i).getImage(),ba2.get(i).getX(),ba2.get(i).getY(),100,100,this);
		 }
		for(int i=0 ; i<fireball.size();i++){
		    for(int j=0 ; j<ba2.size();j++){
		    	if(Intersect(fireball.get(i).getbound(),ba2.get(j).getbound())){
			    ba2.remove(j);
			    fireball.remove(i);
			    scor += 20;
			    g.drawString("+10",m.x+100,650);
			 } 
		    }
		}
		//=================================ball5=============
		for(int i=0 ; i<ba5.size();i++){
		    g.drawImage(ba5.get(i).getImage(),ba5.get(i).getX(),ba5.get(i).getY(),100,100,this);
		}
		for(int i=0 ; i<fireball.size();i++){
		    for(int j=0 ; j<ba5.size();j++){
		    	if(Intersect(fireball.get(i).getbound(),ba5.get(j).getbound())){
			    ba5.remove(j);
			    fireball.remove(i);
			    scor -=20;
			    HP=HP-1;
			    g.drawString("-1HP",m.x+100,650);
			    g.drawString("-20",m.x+100,580);
			} 
		    }
		}
		g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,30));
		g.setColor(Color.BLACK);
		g.drawString("SCORE =  "+scor,80, 100);	     
		g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,50));
		g.drawString("STATE "+rs1,350,80);
		g.drawString("Time "+times,335,150);
		g.setColor(Color.RED);
		g.drawString("HP  "+HP,100,200);
		      
	    }

	}
	public boolean Intersect(Rectangle2D a, Rectangle2D b){
		return (a.intersects(b));
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== BStartover){		
                    this.setSize(1000,800);
                    this.add(hg);
                    this.setLocation(null);
                    timestart = true;
                    startball = true;
		}else if(e.getSource() == BExitover){
                    System.exit(0);
		}		
	}
}

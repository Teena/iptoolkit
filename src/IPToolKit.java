import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.imageio.stream.*;

public class IPToolKit extends JFrame implements ActionListener,MouseListener
{
	/******************** Components and Variables used ********************/
	
	Container cp,cp1;
	JPanel MainPanel,panel_image,panel_gray,panel_result;
	JScrollPane scrollPane; 
	JMenuBar jmb;
	JMenu m_File,m_Arith,m_Filter,m_Enhance;
	JMenuItem m_Open,m_Save,m_Exit;
	JMenu m_Add , m_Sub,m_Mul,m_Div;
	JMenu mAddConst,mSubConst,mMulConst,mDivConst;
	JMenuItem mAddImg,mSubImg,mMulImg,mDivImg;
	JMenuItem mAddSat,mAddWrap,mSubSat,mSubWrap,mMulSat,mMulWrap,mDivSat,mDivWrap;
	JMenuItem mlowPass,mhighPass,mhighBoost,median,mean;
	JMenuItem mBright,mContrast,mThreshold,mInvert,mBlend;
	JMenu edge,mpre,msob,other;
	JMenuItem mpre_hor,mpre_ver,mpre_both,msob_hor,msob_ver,msob_both,mrob,mlap,hist,conect;
	 	
	JFileChooser jfc;
	JLabel lbl_img,lbl_gray,lbl_res;
	Image img_temp,img_gray;
	File file,file1,fileR;
	int len,w,h,len1,w1,h1,x_cor,y_cor;
	int pixel_result[],pix_temp[],pix_temp1[],pix_res[],pixel_cont[],pixel_div[],pixel_add[],pixel_sub[],pixel_mul[];
	int pixel1[][],pixelR[][],conect_input[][],conect_output[][];
	byte byteArray[],byteArray1[];
	Dimension dimImg1,dimImg2,dim;
	FileImageInputStream inImage,inImage1;
	FileImageOutputStream outImage;
	JScrollBar vsb,hsb;
	JPanel Main_pnl;
	JScrollPane jsp;
	Image image,image1;
	BufferedImage buf_img;
	int s,t,intensity,obj_size;
	
	/************* Initialization of every component and variable being used **************/
	
	IPToolKit()
	{
			
			cp = new Container();
			cp.setLayout(new BorderLayout());
			Main_pnl = new JPanel();
			jmb = new JMenuBar();
			m_File = new JMenu("File");
			m_Arith = new JMenu("Arithmetic");
			m_Filter = new JMenu("Filters");  
			m_Open = new JMenuItem("Open");
			m_Save = new JMenuItem("Save");
			m_Exit = new JMenuItem("Exit");
			m_Add = new JMenu("Add");
			m_Sub = new JMenu("Subtract");
			m_Mul = new JMenu("Multiply");
			m_Div = new JMenu("Divide");
			edge = new JMenu("Edge Detection");
			mpre = new JMenu("Prewits");
			msob = new JMenu("Sobel");
			lbl_img = new JLabel("");
			lbl_gray = new JLabel("");
			lbl_res = new JLabel("");
			jfc = new JFileChooser();
			panel_image = new JPanel();
			panel_gray = new JPanel();
			panel_result = new JPanel();
			scrollPane = new JScrollPane();
			mAddConst = new JMenu("Constant");
			mSubConst = new JMenu("Constant");
			mMulConst = new JMenu("Constant");
			mDivConst= new JMenu("Constant");
			mAddSat = new JMenuItem("Saturation");
			mAddWrap = new JMenuItem("Wrap Around");
			mSubSat = new JMenuItem("Saturation");
			mSubWrap = new JMenuItem("Wrap Around");
			mMulSat =new JMenuItem("Saturation");
			mMulWrap =new JMenuItem("Wrap Around");
			mDivSat = new JMenuItem("Saturation");
			mDivWrap = new JMenuItem("Wrap Around");
			mAddImg = new JMenuItem("Image");
			mSubImg = new JMenuItem("Image");
			mMulImg = new JMenuItem("Image");
			mDivImg = new JMenuItem("Image");
			mlowPass = new JMenuItem("Low Pass");
			mhighPass = new JMenuItem("High Pass");
			mhighBoost = new JMenuItem("High Boost");
			median = new JMenuItem("Median");
			mBright = new JMenuItem("Brightness");
			mContrast= new JMenuItem("Contrast Stretch");
			mThreshold = new JMenuItem(" Threshold ");
			m_Enhance = new JMenu("Enhancement");
			mInvert = new JMenuItem("Negative");
			mpre_hor = new JMenuItem("Prewits Horizontal");
			mpre_ver = new JMenuItem("Prewits Vertical");
			mpre_both= new JMenuItem("Prewits Both");
			msob_hor = new JMenuItem("Sobel Horizontal");
			msob_ver = new JMenuItem("Sobel Vertical");
			msob_both = new JMenuItem("Sobel Both");;
			mrob = new JMenuItem("Roberts");
			mean = new JMenuItem("Mean");
			mlap = new JMenuItem("Laplacian");
			other = new JMenu("Other");
			hist = new JMenuItem("Histogram Equalization");
			conect = new JMenuItem("Connected Component");
			mBlend = new JMenuItem("Blending");
			vsb = new JScrollBar(JScrollBar.VERTICAL);
			hsb = new JScrollBar(JScrollBar.HORIZONTAL);
		//	jsp = new JScrollPane();
			
			setJMenuBar(jmb);
			add(cp);
			
		//	setBackground(new Color(255,255,255));
			
			panel_image.setBackground(new Color(255, 0, 0));
			panel_gray.setBackground(new Color(0, 255,0));
			panel_result.setBackground(new Color(0,0,255));
			

			jmb.add(m_File);
			jmb.add(m_Arith);
			jmb.add(m_Enhance);
			jmb.add(m_Filter);
			jmb.add(edge);
			jmb.add(other);
			
			cp.add(Main_pnl);
		
			
			Main_pnl.setLayout(new GridLayout(1,3,10,10));
			
			Main_pnl.add(panel_image);
			Main_pnl.add(panel_gray);
			Main_pnl.add(panel_result);						
			
			m_File.add(m_Open);
			m_File.add(m_Save);
			m_File.add(m_Exit);
			
			m_Arith.add(m_Add);
			m_Add.add(mAddImg);
			m_Add.add(mAddConst);
			mAddConst.add(mAddSat);
			mAddConst.add(mAddWrap); 
						
			m_Arith.add(m_Sub);
			m_Sub.add(mSubImg);
			m_Sub.add(mSubConst);
			mSubConst.add(mSubSat);
			mSubConst.add(mSubWrap);
			
			m_Arith.add(m_Mul);
			m_Mul.add(mMulImg);
			m_Mul.add(mMulConst);
			mMulConst.add(mMulSat);
			mMulConst.add(mMulWrap); 
			
			m_Arith.add(m_Div);
			m_Div.add(mDivImg);
			m_Div.add(mDivConst);
			mDivConst.add(mDivSat);
			mDivConst.add(mDivWrap);
			
		
			m_Filter.add(mlowPass);
			m_Filter.add(mhighPass);
			m_Filter.add(mhighBoost);
			m_Filter.add(median);
			m_Filter.add(mean);
			
			edge.add(mpre);
			edge.add(msob);
			mpre.add(mpre_hor);
			mpre.add(mpre_ver);
			mpre.add(mpre_both);
			msob.add(msob_hor);
			msob.add(msob_ver);
			msob.add(msob_both);
			edge.add(mrob);
			edge.add(mlap);
			
			m_Enhance.add(mBright);
			m_Enhance.add(mContrast);
			m_Enhance.add(mThreshold);
			m_Enhance.add(mInvert);
			m_Enhance.add(mBlend);
			
			other.add(hist);
			other.add(conect);
						
			m_Open.addActionListener(this);
			m_Save.addActionListener(this);
			m_Exit.addActionListener(this);
			
			mAddImg.addActionListener(this);
			mSubImg.addActionListener(this);
			mMulImg.addActionListener(this);
			mDivImg.addActionListener(this);
			
			mAddSat.addActionListener(this);
			mAddWrap.addActionListener(this);
			mSubSat.addActionListener(this);
			mSubWrap.addActionListener(this);
			mMulSat.addActionListener(this);
			mMulWrap.addActionListener(this);
			mDivSat.addActionListener(this);
			mDivWrap.addActionListener(this);
			
			mlowPass.addActionListener(this);
			mhighPass.addActionListener(this); 
			mhighBoost.addActionListener(this); 
			median.addActionListener(this); 
			
			mBright.addActionListener(this);
			mContrast.addActionListener(this);
			mThreshold.addActionListener(this);
			mInvert.addActionListener(this);
			mBlend.addActionListener(this);
			
			mpre_hor.addActionListener(this);
			mpre_ver.addActionListener(this);
			mpre_both.addActionListener(this);
			msob_hor.addActionListener(this);
			msob_ver.addActionListener(this);
			msob_both.addActionListener(this);
			mrob.addActionListener(this);	
			mean.addActionListener(this);
			mlap.addActionListener(this);
			
			
			hist.addActionListener(this);
			conect.addActionListener(this);
			
			
			lbl_gray.addMouseListener(this);
			
			panel_image.add(lbl_img);
			panel_gray.add(lbl_gray);
			panel_result.add(lbl_res);
	
			cp.add(vsb,BorderLayout.EAST);
			cp.add(hsb,BorderLayout.SOUTH);
		//	cp1 = getContentPane();
			
			cp = getContentPane();		
			setSize(600,400);
			setVisible(true);
		
			
			
	}
	
	/******************** Open & Read an Image ***********************/
	
	void OpenImage2() 
	{
		int returnVal = jfc.showOpenDialog(IPToolKit.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
       			{
        			file1 = jfc.getSelectedFile();
        			String path = file.getAbsolutePath();
        		//	image = Toolkit.getDefaultToolkit().getImage(path);
        			
        				try{
							try{
								 inImage1 = new FileImageInputStream(file1);
								 len1 = (int)inImage1.length();
								 byteArray1 = new byte[len1];
								// System.out.println(len);
								 inImage1.read(byteArray1,0,len1);
								 image1 = Toolkit.getDefaultToolkit().createImage(byteArray1);
								 MediaTracker t = new MediaTracker(this);
								 t.addImage(image1,0);
								 	
								 try
								 {
								 	t.waitForID(1);
								 }catch(Exception eeee)
								 {
								 	System.out.println(eeee);
								 }	
								 	
							 	 w1 = image1.getWidth(null);
        						 h1 = image1.getHeight(null);
								    
								  
								 // System.out.println(w+"\t"+h);
								  
							}catch(Exception fnfe)
							{
								JOptionPane.showMessageDialog(this,"File: Not Found");
							}
							}catch(Exception ice)
							{
						
								JOptionPane.showMessageDialog(this,"File I/O Error");
							}
							
							
							
			}
		
		   
			
			
	}
	 
	/***************** Grab Pixels and Convert to gray scale ***************/
	
	int[] pixel_grab(Image img,int width,int height)
	{
		int pixSrc[] = new int[w*h];
		try 
		{
			PixelGrabber pg = new PixelGrabber(img,0,0,w,h,pixSrc,0,w);
			pg.grabPixels();
		} 
		catch (Exception e){System.out.println("Exception at Pixel Grabbing");} 
				
		for(int i=0; i<w*h; i++)  //  Convertion of pixle form to normal single Value...
		{
			int a = pixSrc[i];
			int r = (0xff & (a>>16));
			int g = (0xff & (a>>8));
			int b = (0xff & a);
			int avg = (int)((.33*r+.56*g+.11*b));  // Add a constant to each pixel
			pixSrc[i]=avg;
		} 
		
		
		
	
		return(pixSrc);
		 
	}
	/******************* Convert pixels to 32 it format for Display *************/
	
	int [] pix_pack(int pixSrc[],int wid,int hgt)
	{
		for(int i=0;i<wid*hgt;i++)
    	{
    		pixSrc[i] = (0xff000000|pixSrc[i]<<16 |pixSrc[i]<<8 |pixSrc[i]);
    	}
        	
    	return(pixSrc);
	}
	
	/******************** Display Gray Scale version of image opened ****************/
	
	 void grayImageDisplay(int width,int height,int pix[])
    {	
  
    	img_temp = createImage(new MemoryImageSource(width,height,pix,0,width));
    	ImageIcon imgI = new ImageIcon();
    	imgI.setImage(img_temp);
    	lbl_gray.setIcon(imgI);
    }
	
	/**************** Display result of processed Image ************/
	
	void imageResultDisplay(int width,int height,int pix[])
    {	
    	
	   	img_temp = createImage(new MemoryImageSource(width,height,pix,0,width));
	   	ImageIcon imgIcon = new ImageIcon();
    	imgIcon.setImage(img_temp);
    	lbl_res.setIcon(imgIcon);
    }

/************* Pixel handling by Saturation **************/
	
 int [] saturate(int pixel_handle[],int w,int h)
 {
 	for(int k = 0;k<w*h;k++)
 	{
 	
 		if(pixel_handle[k] > 255)
 		{
 				pixel_handle[k] = 255;
 		}
 		else if(pixel_handle[k]<0)
 		{
 			pixel_handle[k] = 0;
 		}
 		
 	}
 	
 	return(pixel_handle);
 	
 	
 }

/************** Pixel handling by wrap Around **************/
 
 int [] WrapAround(int pixel_handle[],int w,int h)
 {
 	for(int i =0;i<w*h;i++)
 	{
 	
 		if(pixel_handle[i]>255)
 		{
 			int rem = pixel_handle[i] - 255;
 			pixel_handle[i] = rem;
 		}
 		else if(pixel_handle[i] < 0)
 		{
 			int rem = 255 + pixel_handle[i];
 			pixel_handle[i] = rem;
 		}
 		
 	}
 	return(pixel_handle);
 }

/********************** Mask Operation for Filters and Edge Detectors (Convolution) ********************/
 
 int[][] MaskOperation(int width,int height,int pix_tempImg[][], int temp_mask[][])
 {
 	
	int pix_tempImg1[][] = new int[height][width];
	
	for(int i =1;i<height-1;i++)
	{
		for(int j=1;j<width-1;j++)
		{
			pix_tempImg1[i][j] = ((((temp_mask[0][0]*pix_tempImg[i-1][j-1]) + (temp_mask[1][0]*pix_tempImg[i][j-1]) + (temp_mask[2][0]*pix_tempImg[i+1][j-1]))+
								((temp_mask[0][1]*pix_tempImg[i-1][j]) + (temp_mask[1][1]*pix_tempImg[i][j]) + (temp_mask[2][1]*pix_tempImg[i+1][j]))+
								((temp_mask[0][2]*pix_tempImg[i-1][j+1]) + (temp_mask[1][2]*pix_tempImg[i][j+1]) + (temp_mask[2][2]*pix_tempImg[i+1][j+1]))));
		}
		
		
	}  	
	
	return(pix_tempImg1);
	
	
 	
 }
 
/************** Conversion of One-Dimensional Array to Two-Dimensional **************/ 
 int[][] OneD_ArrayToTwoD_Array(int width,int height,int pix_temp[])
 {
 	
 	int pix2D[][] = new int[height][width];
 	int c = 0;
 	
 	for(int i=0;i<height;i++)
 	{
 		for(int j =0;j<width;j++)
 		{
 			
 			pix2D[i][j] = pix_temp[c++];	
 
 		}
 	}
 	
 	return(pix2D);
 	 
 }
 
 
 /************** Conversion of Two-Dimensional Array to One-Dimensional **************/
 
 int[] TwoD_ArrayToOneD_Array(int width,int height,int pix_tempLow[][])
 {
 	int pix1D[] = new int[(height-2)*(width-2)];
 	int c=0;

	for(int i = 1;i<height-1;i++)
	{
		for(int j =1;j<width-1;j++)
		{
			pix1D[c] = pix_tempLow[i][j];
			c++;
		}
	} 
	
	return(pix1D);	
 }
 
 
/************** Method for obtaining the median ***************/
 
int[][] median_filter(int pixell_2d[][])
{
	int median_pix[][] = new int [h][w];
	int neighbour_array[] = new int[9];
	int median_val;
		
	for(int i = 1;i<h-1;i++)
	{
		for(int j =1;j<w-1;j++)
		{
			
			neighbour_array[0] = pixell_2d[i-1][j-1];
			neighbour_array[1] = pixell_2d[i-1][j];
			neighbour_array[2] = pixell_2d[i-1][j+1];
			neighbour_array[3] = pixell_2d[i][j-1];
			neighbour_array[4] = pixell_2d[i][j];
			neighbour_array[5] = pixell_2d[i][j+1];
			neighbour_array[6] = pixell_2d[i+1][j-1];
			neighbour_array[7] = pixell_2d[i+1][j];
			neighbour_array[8] = pixell_2d[i+1][j+1];
			
				
			median_val =  sort(neighbour_array);
				
			median_pix[i][j] = median_val;		
				
				
				
		}
	}
		
	return median_pix;
		
}

public int[] restrict(int pin[])
{
	for(int i=0;i<(h-2)*(w-2);i++)
	{
		pin[i] = pin[i]/9;
	}
	return pin;
}

/****************** Method to sort the neighbouring pixels **************/

int sort(int array[])
{
	
		
	for(int i = 0;i<9;i++)
	{
		for(int j=1;j<9;j++)
		{
				
			if(array[j]>array[j-1])
			{
				int temp = array[j];
				array[j] = array[j-1];
				array[j-1] = temp;
					
			}
		}
	}
		
	return array[4];
}


/*********************** Mean Filtering **************/

int [][] mean_filt(int pixell_2d[][])
{
	int[][] ppp = new int[h][w];
	int []neighbour_array = new int[9];
	
	for(int i = 1;i<h-1;i++)
	{
		for(int j =1;j<w-1;j++)
		{
			
			neighbour_array[0] = pixell_2d[i-1][j-1];
			neighbour_array[1] = pixell_2d[i-1][j];
			neighbour_array[2] = pixell_2d[i-1][j+1];
			neighbour_array[3] = pixell_2d[i][j-1];
			neighbour_array[4] = pixell_2d[i][j];
			neighbour_array[5] = pixell_2d[i][j+1];
			neighbour_array[6] = pixell_2d[i+1][j-1];
			neighbour_array[7] = pixell_2d[i+1][j];
			neighbour_array[8] = pixell_2d[i+1][j+1];
			
			ppp[i][j] = (neighbour_array[0]+neighbour_array[1]+neighbour_array[2]+neighbour_array[3]+neighbour_array[4]+
						 neighbour_array[5]+neighbour_array[6]+neighbour_array[7]+neighbour_array[8]) / 9;
				
		}
	}
	return ppp;
}

/*******************************Connected Component Recursive ******************************/

void connect(int y,int x)
	{
	
	
	
	   if(conect_output[y][x]==0)
	   {
	   		conect_output[y][x] = 175;
			obj_size++;
		
		//System.out.println("Size :" +obj_size);
		s = x+1;
		t = y;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		
		s = x;
		t = y-1;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x+1;
		t = y-1;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x+1;
		t = y+1;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x-1;
		t = y-1;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x-1;
		t = y+1;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x+1;
		t = y;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
		s = x-1;
		t = y;
		if(boundary_check(t,s) && (Math.abs(conect_input[t][s]-intensity) <20))
		{
		//	conect_output[s][t] = 175;
			connect(t,s);
			
		}
	}
		
		
	}
	
	private final boolean boundary_check(int yy,int xx)
	{
		
		return yy>=0 && yy<h && xx >=0 && xx<w;
	}
	


	public void mouseClicked(MouseEvent me)
	{
		x_cor = me.getX();
		y_cor = me.getY();
	//	jtx.setText("X_Cor ="+x_cor+"\t"+"Y_Cor"+y_cor);
		
		
	}
		public void mousePressed(MouseEvent me)
	{
		
		
	}
		public void mouseEntered(MouseEvent me)
	{
		
	}
		public void mouseReleased(MouseEvent me)
	{
		
	}
		public void mouseExited(MouseEvent me)
	{
		
	}
 
 
 
	
	public void actionPerformed(ActionEvent e)
	{
		String arg = (String)e.getActionCommand();
		
		/********************** Open an Image *****************/
		
		if(e.getSource() == m_Open)
		{
				int returnVal = jfc.showOpenDialog(IPToolKit.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
       			{
        			file = jfc.getSelectedFile();
        			String path = file.getAbsolutePath();
        		//	image = Toolkit.getDefaultToolkit().getImage(path);
        			
        				try{
							try{
								 inImage = new FileImageInputStream(file);
								 len = (int)inImage.length();
								 byteArray = new byte[len];
								// System.out.println(len);
								 inImage.read(byteArray,0,len);
								 image = Toolkit.getDefaultToolkit().createImage(byteArray);
								 MediaTracker t = new MediaTracker(this);
								 t.addImage(image,0);
								 	
								 try
								 {
								 	t.waitForID(0);
								 }catch(Exception eeee)
								 {
								 	System.out.println(eeee);
								 }	
								 	
							 	 w = image.getWidth(null);
        						 h = image.getHeight(null);
								    
								  
								 // System.out.println(w+"\t"+h);
								  
							}catch(Exception fnfe)
							{
								JOptionPane.showMessageDialog(this,"File: Not Found");
							}
							}catch(Exception ice)
							{
						
								JOptionPane.showMessageDialog(this,"File I/O Error");
							}						
				}
					if(image != null)
					{	
								
								pix_temp = new int[h*w];
								ImageIcon icon = new ImageIcon(image);
								lbl_img.setIcon(icon);
								setVisible(true);
								
					}
					
						
			pix_temp = pixel_grab(image,w,h);
			pix_temp = pix_pack(pix_temp,w,h); 
			grayImageDisplay(w,h,pix_temp);
					
				
					
					
						
		}
		
		/******************** Add one image to another ***************/
		
		if(e.getSource() == mAddImg)
		{
			OpenImage2();
			pix_temp1 = new int[w*h];
			pix_res = new int[w*h];
							
			pix_temp1 = pixel_grab(image1,w1,h1);
			pix_temp1 = pix_pack(pix_temp1,w1,h1);
					
			for(int s= 0;s<w*h;s++)
			{
					
				pix_res[s] = pix_temp[s] + pix_temp1[s];
						
			}
		;
					
			pix_temp1 = saturate(pix_temp1,w,h);
			imageResultDisplay(w,h,pix_res);
					
											
				
		}	
		
	
	
       
        
        /************************* Add Constant value and then use Satuartion technique ****************/
        
     	if(e.getSource()==mAddSat)
        {
        	String add_value;	
      		Image img_add = image;
     	 	pixel_add = new int[w*h]; 
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        	
        	add_value = JOptionPane.showInputDialog("Enter Value you wish to add to the image");
        	int number = Integer.parseInt(add_value);
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_add[r] = number + pixel_result[r];
        	}
        	pixel_add = saturate(pixel_add,w,h);
        	pixel_add = pix_pack(pixel_add,w,h);
        	imageResultDisplay(w,h,pixel_add);							
							
    	}
    	
    	/************** Add constant by Wrap Around technique ***********/		
    	if(e.getSource()==mAddWrap)
    	{
    		String add_value;		
    		Image img_add = image;
     	 	pixel_add = new int[w*h]; 
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        	
        	add_value = JOptionPane.showInputDialog("Enter Value you wish to add to the image");
        	int number = Integer.parseInt(add_value);
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_add[r] = number + pixel_result[r];
        	}
        	pixel_add = WrapAround(pixel_add,w,h);
           	pixel_add = pix_pack(pixel_add,w,h);
        	imageResultDisplay(w,h,pixel_add);							
							
    				
    	}
    	
    	/******************** Subtract one image from another ***************/
    				
    	if(e.getSource() == mSubImg)
		{
			OpenImage2();							
			pix_temp1 = new int[w*h];
			pix_res = new int[w*h];
							
			pix_temp1 = pixel_grab(image1,w1,h1);
			pix_temp1 = pix_pack(pix_temp1,w1,h1);
					
			for(int s= 0;s<w*h;s++)
			{
				pix_res[s] = pix_temp[s] - pix_temp1[s];
						
			}
					
			pix_temp1 = saturate(pix_temp1,w1,h1);
			imageResultDisplay(w1,h1,pix_res);
																		
		}
		
		/************************* Subtract Constant value and then use Satuartion technique ****************/
        			
       if(e.getSource() == mSubSat)
       {
        	String sub_value;			
       		Image img_add = image;
        	pixel_sub = new int[w*h];
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        	
        	sub_value = JOptionPane.showInputDialog("Enter Value you wish to subtract from the image");
        	int number = Integer.parseInt(sub_value);
        					
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_sub[r] = pixel_result[r]-number;
      	  
        	}
        					
        	pixel_sub = saturate(pixel_sub,w,h);
   			pixel_sub = pix_pack(pixel_sub,w,h);
        	imageResultDisplay(w,h,pixel_sub);
							
        				
        }
        
        /************** Subtract constant by Wrap Around technique ***********/
        
        if(e.getSource()==mSubWrap)
    	{
    		String sub_value;		
    		Image img_add = image;
     	 	pixel_sub = new int[w*h]; 
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        	
        	sub_value = JOptionPane.showInputDialog("Enter Value you wish to add to the image");
        	int number = Integer.parseInt(sub_value);
        	
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_sub[r] = pixel_result[r]- number;
        	}
        	pixel_sub = WrapAround(pixel_sub,w,h);
           	pixel_sub = pix_pack(pixel_sub,w,h);
        	imageResultDisplay(w,h,pixel_sub);							
							
    				
    	}
    			
    	/******************** Multiply one image with another ***************/
    			
    	if(e.getSource() == mMulImg)
		{
			OpenImage2();
			pix_temp1 = new int[w*h];
			pix_res = new int[w*h];
							
			pix_temp1 = pixel_grab(image1,w1,h1);
			pix_temp1 = pix_pack(pix_temp1,w1,h1);
					
			for(int s= 0;s<w*h;s++)
			{
				pix_res[s] = pix_temp[s] * pix_temp1[s];
						
			}
					
			pix_temp1 = saturate(pix_temp1,w1,h1);
			imageResultDisplay(w1,h1,pix_res);
							
		}
       
       
       /************************* Multiply Constant value and then use Satuartion technique ****************/ 		
        			
        if(e.getSource() == mMulSat)
        {
        	String mul_value;			
        	Image img_mul = image;
        	pixel_mul = new int[w*h];
        			 		
        	pixel_result = pixel_grab(img_mul,w,h);
        	
        	mul_value = JOptionPane.showInputDialog("Enter Value you wish to multiply to the image");
        	int number = Integer.parseInt(mul_value);				
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_mul[r] = pixel_result[r]*number;
      	  
        	}
        					
        					
        	pixel_mul = saturate(pixel_mul,w,h);
   			pixel_mul = pix_pack(pixel_mul,w,h);
        	imageResultDisplay(w,h,pixel_mul);
							
        				
     	}
     	
     	/************** Multiply constant by Wrap Around technique ***********/
     	if(e.getSource()==mMulWrap)
    	{
    		String mul_value;		
    		Image img_mul = image;
     	 	pixel_mul = new int[w*h]; 
        			 		
        	pixel_result = pixel_grab(img_mul,w,h);
        	
        	mul_value = JOptionPane.showInputDialog("Enter Value you wish to multiply to the image");
        	int number = Integer.parseInt(mul_value);
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_mul[r] = pixel_result[r] * number;
      		}
        	pixel_mul = WrapAround(pixel_mul,w,h);
           	pixel_mul = pix_pack(pixel_mul,w,h);
        	imageResultDisplay(w,h,pixel_mul);							
							
    				
    	}
    	
    	/******************** Divide one image by another ***************/
    		
    	if(e.getSource() == mDivImg)
		{
					
			OpenImage2();		
			pix_temp1 = new int[w*h];
			pix_res = new int[w*h];
							
			pix_temp1 = pixel_grab(image1,w1,h1);
			pix_temp1 = pix_pack(pix_temp1,w1,h1);
					
			for(int s= 0;s<w*h;s++)
			{
				pix_res[s] = pix_temp1[s]/pix_temp[s] ;
						
			}
					
					//pix_temp1 = saturate(pix_temp1,w1,h1);
			pix_temp1 = WrapAround(pix_temp1,w,h);
			imageResultDisplay(w1,h1,pix_res);											
				
		}
        		
     	
     	
     	/************************* Divide by Constant value and then use Saturation technique ****************/
        if(e.getSource() == mDivSat)
        {
        	String div_value;			
        	Image img_add = image;
        	pixel_div = new int[w*h];
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        					
        	
        	div_value = JOptionPane.showInputDialog("Enter value to divide the image by");
        	int number = Integer.parseInt(div_value);
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_div[r] = pixel_result[r]/number;
      	  
        	}
        					
        	pixel_div = saturate(pixel_div,w,h);
   			pixel_div = pix_pack(pixel_div,w,h);
        	imageResultDisplay(w,h,pixel_div);
							
        				
       }
       
       
       /************** Divide constant by Wrap Around technique ***********/
       
       	if(e.getSource()==mDivWrap)
    	{
    		String div_value;		
    		Image img_add = image;
     	 	pixel_div = new int[w*h]; 
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        	
        	div_value = JOptionPane.showInputDialog("Enter value to divide image by");
        	int number = Integer.parseInt(div_value);
        	for(int r = 0; r < w*h ; r++)
        
        	{
        		pixel_div[r] = pixel_result[r]/number;
        	}
        	pixel_div = WrapAround(pixel_div,w,h);
          	pixel_div = pix_pack(pixel_div,w,h);
        	imageResultDisplay(w,h,pixel_div);							
							
    				
   		}
   		
   		/********************** Thresholding **************/
   		
   		if(e.getSource() == mThreshold)
        {
        	String thresh_value;			
        	Image img_add = image;
        
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        					
        	
        	thresh_value = JOptionPane.showInputDialog("Enter value to threshold the image");
        	int number = Integer.parseInt(thresh_value);
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		if(pixel_result[r] >= number)
        			pixel_result[r] = 255;
        		else if(pixel_result[r] <= number)
        			pixel_result[r] =0;
      	  
        	}
        					
        
   			pixel_result = pix_pack(pixel_result,w,h);
        	imageResultDisplay(w,h,pixel_result);
        	
        }
        	
       	if(e.getSource() ==m_Save)
   		{
   				FileImageOutputStream src_img;
   			
   				int returnVal = jfc.showSaveDialog(IPToolKit.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
						 File fileR = jfc.getSelectedFile();
     					 String fileToSave = file.getAbsolutePath();

     					 if (!fileToSave.toLowerCase().endsWith(".jpg")) 
     					 {
     					   fileR = new File(fileToSave + ".jpg");
      					 }

      				try 
      				{
      				  	src_img = new FileImageOutputStream(fileR);
      				  	buf_img = getImageFromArray(pixel_result,w,h);
      				  	ImageIO.write(buf_img,"jpg",src_img);
      				  	
      				  	
      				}catch (IOException ex8) 
      				{
      					System.out.println(ex8);  
      				
					}
				} 
				
				 
				       					
   			
   	//	}
							
        				
       }
       
       /******************************* Increase brightness *****************/
       
       if(e.getSource() == mBright)
       {
        	String bright_value;			
        	Image img_add = image;
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        					
        	bright_value = JOptionPane.showInputDialog("Enter value to increase brightness");
        	int number = Integer.parseInt(bright_value);
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_result[r] = pixel_result[r] + number;
      	  
        	}
        					
        	pixel_result = saturate(pixel_result,w,h);
   			pixel_result = pix_pack(pixel_result,w,h);
        	imageResultDisplay(w,h,pixel_result);
							
        				
       }
       
       /******************** Contrast Strtching ****************/
        
         if(e.getSource() == mContrast)
        {
        	String cont_value1,cont_value2;			
        	Image img_add = image;
        	pixel_cont = new int[w*h];
        			 		
        	pixel_result = pixel_grab(img_add,w,h);
        					
        	
        	cont_value1 = JOptionPane.showInputDialog("Enter lower limit for contrast stretch");
        	int number = Integer.parseInt(cont_value1);
        	cont_value2 = JOptionPane.showInputDialog("Enter higher limit for contrast stretch");
        	int number1 = Integer.parseInt(cont_value2);
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		if(pixel_result[r] <= number1)
        		{
        		
        			pixel_cont[r] = pixel_result[r]-50;
        			if(pixel_cont[r] <0)
        			  pixel_cont[r] =0;
        		}
        		
        		else if(pixel_result[r] >= number)
        		{
        			pixel_cont[r] = pixel_result[r]+50;
        			
        			if(pixel_cont[r] >255)
        			  pixel_cont[r] =255;
        				
        		}
        		else
        		
        			pixel_cont[r] = pixel_result[r];
      	  	
        	}
        					
        	pixel_cont = saturate(pixel_cont,w,h);
   			pixel_cont = pix_pack(pixel_cont,w,h);
        	imageResultDisplay(w,h,pixel_cont);
        	
        	
							
        				
       }
       
       
   	 /********************* Low Pass Filter *******************/	
   		
   		if(e.getSource() == mlowPass)
   		{
   			int mask[][] = {{1,1,1},{1,1,1},{1,1,1}};
   			
   			pixel_result = pixel_grab(image,w,h);
   			
   			int pix_tempLow[][] = new int[h][w];
   			
   			 
   			pix_tempLow =  OneD_ArrayToTwoD_Array(w,h,pixel_result);
   			
   			pix_tempLow= MaskOperation(w,h,pix_tempLow,mask);
   			int pix_temp1D[] = new int[(w)*(h)];
   			
   			
   			
   			pix_temp1D = TwoD_ArrayToOneD_Array(w,h,pix_tempLow);
   			
   			pix_temp1D = restrict(pix_temp1D);
   			pix_temp1D = pix_pack(pix_temp1D,w-2,h-2);
   			imageResultDisplay(w-2,h-2,pix_temp1D);
   						
   		}
   		
   		/*********************** High Pass Filter ****************/
   		
   		if(e.getSource() == mhighPass)
   		{
   			int mask[][] = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
   			
   			pixel_result = pixel_grab(image,w,h);
   			
   			int pix_tempLow[][] = new int[h][w];
   			
   			 
   			pix_tempLow =  OneD_ArrayToTwoD_Array(w,h,pixel_result);
   			
   			pix_tempLow= MaskOperation(w,h,pix_tempLow,mask);
   			int pix_temp1D[] = new int[(w)*(h)];
   			
   			pix_temp1D = TwoD_ArrayToOneD_Array(w,h,pix_tempLow);
   		
   			pix_temp1D = saturate(pix_temp1D,w-2,h-2);
   			pix_temp1D = restrict(pix_temp1D);
   			pix_temp1D = pix_pack(pix_temp1D,w-2,h-2);
   			imageResultDisplay(w-2,h-2,pix_temp1D);
   						
   		}
   		
   		
   		/***************************** High Boost Filter *****************/
   		
   		if(e.getSource() == mhighBoost)
   		{
   			int mask[][] = {{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
   			
   			pixel_result = pixel_grab(image,w,h);
   			
   			int pix_tempLow[][] = new int[h][w];
   			
   			 
   			pix_tempLow =  OneD_ArrayToTwoD_Array(w,h,pixel_result);
   			
   			pix_tempLow= MaskOperation(w,h,pix_tempLow,mask);
   			int pix_temp1D[] = new int[(w)*(h)];
   			
   			pix_temp1D = TwoD_ArrayToOneD_Array(w,h,pix_tempLow);
   			pix_temp1D = saturate(pix_temp1D,w-2,h-2);
   			pix_temp1D = restrict(pix_temp1D);
   			pix_temp1D = pix_pack(pix_temp1D,w-2,h-2);
   			imageResultDisplay(w-2,h-2,pix_temp1D);
   						
   		}
   		
	  	if(e.getSource() == m_Save)
   		{
   			
   				for(int i = 0;i<w*h;i++)
   				{
   					System.out.println(pixel_result[i]);
   				}
   				
   				FileImageOutputStream src_img;
   			
   				int returnVal = jfc.showSaveDialog(IPToolKit.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
						 File fileR = jfc.getSelectedFile();
     					 String fileToSave = file.getAbsolutePath();

     					 if (!fileToSave.toLowerCase().endsWith(".jpg")) 
     					 {
     					   fileR = new File(fileToSave + ".jpg");
      					 } 

      				try 
      				{
      				  	src_img = new FileImageOutputStream(fileR);
      				  	buf_img = getImageFromArray(pixel_result,w,h);
      				  	ImageIO.write(buf_img,"jpg",src_img);
      				  	
      				  	
      				}catch (IOException ex8) 
      				{
      					System.out.println(ex8);  
      				
					}
				}
				       					
   			
   		} 
   		
 		/*********************** Invert and image *************/
 			
		if(e.getSource() == mInvert)
		{
			
						
        	Image img_Invert = image;
        	pixel_result = new int[w*h];
        			 		
        	pixel_result = pixel_grab(img_Invert,w,h);
        					
        	for(int r = 0; r < w*h ; r++)
        	{
        		pixel_result[r] = 255 - pixel_result[r] ;
      	  
        	}
        					
        //	pixel_div = saturate(pixel_div,w,h);
   			pixel_result = pix_pack(pixel_result,w,h);
        	imageResultDisplay(w,h,pixel_result);
			
				
			
	
		}
		/***************************** Median Filter *****************/
		
		
		if(arg.equals("Median"))
		{
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d = median_filter(p2d);
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
			
		}
		
		/************************ Prewits Horizontal Edge Detection ********************/
		
		if(arg.equals("Prewits Horizontal"))
		{
		
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{-1,-1,-1},{0,0,0},{1,1,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask);
			
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pres = restrict(pres); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}			
   		 
   		 /************************ Prewits Vertical Edge Detection ********************/
   		 
   		 if(arg.equals("Prewits Vertical"))
		{
			
			
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{-1,0,1},{-1,0,1},{-1,0,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask);
			
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pres = restrict(pres); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		/************************ Prewits Edge Detection ********************/
		if(arg.equals("Prewits Both"))
		{
			
			
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres1[] = new int[h*w];
			int pres2[] = new int[h*w];
			int pres[] = new int[h*w];
			int mask1[][] = {{-1,-1,-1},{0,0,0},{1,1,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask1);
			pres1 = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres1 = saturate(pres1,w-2,h-2); 
			pres1 = restrict(pres1);
			int mask2[][] = {{-1,0,1},{-1,0,1},{-1,0,1}};
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask2);
			pres2 = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres2 = saturate(pres2,w-2,h-2);
			pres2 = restrict(pres2);
			for(int i =0;i<((h-2)*(w-2));i++)
			{
				pres[i] = pres1[i]+pres2[i];
			}
			 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		
		/************************ Sobel Horizontal Edge Detection ********************/
		
		if(arg.equals("Sobel Horizontal"))
		{
		
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask);
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pres = restrict(pres); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		/************************ Sobel Vertical Edge Detection ********************/			
		
		if(arg.equals("Sobel Vertical"))
		{
			
			
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask);
			
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pres = restrict(pres); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		/************************ Sobel Edge Detection ********************/
		
		if(arg.equals("Sobel Both"))
		{
			
			
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres1[] = new int[h*w];
			int pres2[] = new int[h*w];
			int pres[] = new int[h*w];
			
			int mask1[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};
			
			pix_grabed = pixel_grab(image,w,h);
			
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			 pp2d= MaskOperation(w,h,p2d,mask1);
			pres1 = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres1 = saturate(pres1,w-2,h-2); 
			pres1 = restrict(pres1);
			int mask2[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask2);
			pres2 = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres2 = saturate(pres2,w-2,h-2);
			pres2 = restrict(pres2);
			for(int i =0;i<((h-2)*(w-2));i++)
			{
				pres[i] = pres1[i]+pres2[i];
			}
			 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		/************************ Roberts Edge Detection ********************/
		
		if(arg.equals("Roberts"))
		{
			
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{1,0},{0,-1}};
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			
			for(int i=1;i<h-1;i++)
			{
				for(int j =1;j<w-1;j++)
				{
					pp2d[i][j] = ((mask[0][0]*p2d[i-1][j-1] + mask[1][0]*p2d[i+1][j-1])+
								  (mask[0][1]*p2d[i-1][j+1] + mask[1][1]*p2d[i+1][j+1]))/2;
				}
				
				
			}
			
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
		}
		
		/************************ Mean Filter ********************/
		
		if(arg.equals("Mean"))
		{
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d = mean_filt(p2d);
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);  
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
		}
		
		
	/************************ Laplacian Edge Detector ********************/
		
		if(arg.equals("Laplacian"))
		{
			int[] pix_grabed = new int[w*h];
			int [][] p2d = new int[h][w];
			int [][] pp2d = new int[h][w];
			int pix_pcked[] = new int[w*h];
			int pres[] = new int[h*w];
			
			int mask[][] = {{0,-2,0},{-2,8,-2},{0,-2,0}};
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			pp2d= MaskOperation(w,h,p2d,mask);
			
			pres = TwoD_ArrayToOneD_Array(w,h,pp2d);
			pres = saturate(pres,w-2,h-2);
			pres = restrict(pres); 
			pix_pcked = pix_pack(pres,w-2,h-2);
			imageResultDisplay(w-2,h-2,pix_pcked);
			
		}
		
		/************************ Histogram Equalization ********************/
		
		if(arg.equals("Histogram Equalization"))
		{
			int[] pix_grabed = new int[h*w];
			int pres[] = new int[h*w];
			int cnt_pix[] = new int[256];
			int pixx[] = new int[h*w];
			int pix_pcked[] = new int[h*w];
			int pix_res[] = new int[h*w];
			int len = h*w;
			int res = 0;
			
			pix_grabed = pixel_grab(image,w,h);
			
		
			for(int i = 0;i<h*w;i++)
		    {
		    	pixx[i] = pix_grabed[i];
		    	
		    }
		  
			for(int i = 0; i < 256; i++)
			{
				
     				cnt_pix[i] = 0;
     		}				
  				
  			for(int i = 0; i < len; i++)
  			{	
  					
  					int ind = pixx[i];
   			 		cnt_pix[ind]++; 
   			} 
   			 		  
		    for(int i=0;i<w*h;i++)
		    {
		    	float a = 0;
		    	
		    	for(int j =0;j<(pixx[i]+1);j++)
		    	{
		    		float b  = (float)cnt_pix[j];
							
					float c = (float)(h*w);
							
				    a = a + (b/c);
		    	}
		    	
		    	res =(int)(a*255);
		    	if(res > 255)
		    	 res = 255;
		    	 
		    	pix_res[i] = ( 0xff000000 | (res << 16) | (res << 8) | res); 	 
		    	
		    }
		    
		    pix_pcked = pix_pack(pix_res,w,h);
			imageResultDisplay(w,h,pix_pcked);
		    
		    	
			
		}
		
		if(arg.equals("Connected Component"))
		{
			int pix_grabed[] = new int[h*w];
			int p2d[][] = new int[h][w];
			conect_input = new int[h][w];
			conect_output = new int[h][w];
			
			int x1 = x_cor;
			int y1 = y_cor;
			
			pix_grabed = pixel_grab(image,w,h);
			p2d = OneD_ArrayToTwoD_Array(w,h,pix_grabed);
			s=0;
			t=0;
			obj_size=0;
			intensity = p2d[y1][x1];
			
			for(int i=0;i<h;i++)
			{
				for(int j=0;j<w;j++)
				{
					conect_input[i][j] = p2d[i][j];
					conect_output[i][j]=0;	
					
				}
			}
			
			connect(y1,x1);
			int pixx[] = new int[h*w];
			int pp[] = new int[h*w];
			
			pixx = TwoD_ArrayToOneD_Array(w,h,conect_output);
			pixx = saturate(pixx,w-2,h-2); 
			pp = pix_pack(pixx,w-2,h-2);
			imageResultDisplay(w-2,h-2,pp);
			
			
		}
		
		
		/************************ Blending ********************/
		
		if(arg.equals("Blending"))
		{
			
			
			int size;
			int hei,wid;
			if(h1 > h)
			
				hei = h1;
			else
				hei = h;
				
			if(w1 > w)
				wid = w1;
			else
				wid = w;
				
			size = hei*wid;	
			int pix_img1[] = new int[size];
			int pix_img2[] = new int[size];
			int pix_res[] = new int[size];
			int pixx[] = new int[h1*w1];
			double x = 0.5;
			pix_img1 = pixel_grab(image,w,h);
			OpenImage2();
			pix_img2 = pixel_grab(image1,w1,h1);
			
			
//	 grayImageDisplay(w1,h1,pixx)
			
			for(int i = 0;i<size;i++)
			{
				pix_res[i] =(int) ((x*pix_img1[i]) +((1-x)*(pix_img2[i]))); 
			}
			int pix_pcked[] = new int[size];
			
			 
			
			pix_pcked = pix_pack(pix_res,wid,hei);
			imageResultDisplay(wid,hei,pix_pcked);
		
			
			
		}
		
				
		/******************** Quit OR Exit *****************/	
	
	   if(arg.equals("Exit"))
	   {
			System.exit(0);
	   }
	
	
	
	
} 				
         
	public static void main(String args[])
	{
		new IPToolKit();
	}
	
	public static BufferedImage getImageFromArray(int[] pixels, int width, int height) 
	{            
		BufferedImage imageBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = (WritableRaster) imageBI.getData();
		raster.setPixels(0,0,width,height,pixels);            
		return imageBI;
	}
}
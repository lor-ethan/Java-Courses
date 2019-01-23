/**
 * This class draws the Piggly Wiggly Pig.
 * 
 * @author Ethan Lor
 */

public class Pig {
	/**
	 * This class draws the Piggly Wiggly Pig.
	 * 
	 * @param args
	 *            Not used.
	 * 
	 */

	public static void main(String[] args) {
		Window win;
		win = new Window();
		win.setSize(700, 700);
		win.setTitle("Piggly Wiggly");
		win.setBackground(java.awt.Color.blue);

		// The next few methods creates the hat that will be layered
		// over.
		Rectangle hatMainoutline;
		hatMainoutline = new Rectangle(195, 80, 200, 140);
		win.add(hatMainoutline);

		Rectangle hatMain;
		hatMain = new Rectangle(200, 85, 190, 130);
		hatMain.setBackground(java.awt.Color.white);
		win.add(hatMain);

		Triangle hatTip;
		hatTip = new Triangle(215, 35, 150, 50, 1);
		win.add(hatTip);

		Triangle hatTipFill;
		hatTipFill = new Triangle(228, 45, 125, 40, 1);
		hatTipFill.setBackground(java.awt.Color.white);
		win.add(hatTipFill);

		Triangle hatMiddle;
		hatMiddle = new Triangle(215, 85, 150, 70, 0);
		win.add(hatMiddle);

		Triangle hatMiddleFill;
		hatMiddleFill = new Triangle(223, 85, 135, 60, 0);
		hatMiddleFill.setBackground(java.awt.Color.white);
		win.add(hatMiddleFill);

		Rectangle hatCenterLine;
		hatCenterLine = new Rectangle(288, 45, 5, 100);
		win.add(hatCenterLine);

		// The next few methods will create the basic face outline and
		// face layers to be layered over later.
		Oval mainFaceOutlineR;
		mainFaceOutlineR = new Oval(195, 145, 350, 410);
		win.add(mainFaceOutlineR);

		Oval mainFaceOutlineL;
		mainFaceOutlineL = new Oval(165, 220, 185, 235);
		win.add(mainFaceOutlineL);

		Triangle earRoutline;
		earRoutline = new Triangle(440, 175, 134, 130, 0);
		win.add(earRoutline);

		Triangle earLoutline;
		earLoutline = new Triangle(106, 220, 170, 160, 0);
		win.add(earLoutline);

		Oval mainFaceR;
		mainFaceR = new Oval(200, 150, 340, 400);
		mainFaceR.setBackground(java.awt.Color.pink);
		win.add(mainFaceR);

		Oval mainFaceL;
		mainFaceL = new Oval(170, 225, 175, 225);
		mainFaceL.setBackground(java.awt.Color.pink);
		win.add(mainFaceL);

		// The next few methods will create the eyes.
		Oval eyeBrowR;
		eyeBrowR = new Oval(375, 210, 100, 135);
		win.add(eyeBrowR);

		Oval eyeBrowfillR;
		eyeBrowfillR = new Oval(370, 220, 110, 135);
		eyeBrowfillR.setBackground(java.awt.Color.pink);
		win.add(eyeBrowfillR);

		Oval eyeBrowL;
		eyeBrowL = new Oval(250, 230, 100, 135);
		win.add(eyeBrowL);

		Oval eyeBrowfillL;
		eyeBrowfillL = new Oval(245, 240, 110, 135);
		eyeBrowfillL.setBackground(java.awt.Color.pink);
		win.add(eyeBrowfillL);

		Oval eyesOutlineR;
		eyesOutlineR = new Oval(375, 270, 100, 135);
		win.add(eyesOutlineR);

		Oval eyesOutlineL;
		eyesOutlineL = new Oval(250, 290, 100, 135);
		win.add(eyesOutlineL);

		Oval eyesR;
		eyesR = new Oval(380, 275, 90, 125);
		eyesR.setBackground(java.awt.Color.white);
		win.add(eyesR);

		Oval eyesL;
		eyesL = new Oval(255, 295, 90, 125);
		eyesL.setBackground(java.awt.Color.white);
		win.add(eyesL);

		Oval pupilR;
		pupilR = new Oval(395, 320, 60, 80);
		win.add(pupilR);

		Oval pupilL;
		pupilL = new Oval(270, 340, 60, 80);
		win.add(pupilL);

		Triangle eyeHilightR;
		eyeHilightR = new Triangle(425, 320, 13, 18, 0);
		eyeHilightR.setBackground(java.awt.Color.white);
		win.add(eyeHilightR);

		Triangle eyeHilightL;
		eyeHilightL = new Triangle(300, 340, 13, 18, 0);
		eyeHilightL.setBackground(java.awt.Color.white);
		win.add(eyeHilightL);

		// The next few methods will create the cheeks.
		Oval cheekOutlineR;
		cheekOutlineR = new Oval(410, 375, 135, 135);
		win.add(cheekOutlineR);

		Oval cheekR;
		cheekR = new Oval(415, 380, 125, 125);
		cheekR.setBackground(java.awt.Color.pink);
		win.add(cheekR);

		Oval cheekOutlineL;
		cheekOutlineL = new Oval(205, 385, 135, 135);
		win.add(cheekOutlineL);

		Oval cheekL;
		cheekL = new Oval(210, 390, 125, 125);
		cheekL.setBackground(java.awt.Color.pink);
		win.add(cheekL);

		Rectangle cheekLfill;
		cheekLfill = new Rectangle(209, 401, 20, 30);
		cheekLfill.setBackground(java.awt.Color.pink);
		win.add(cheekLfill);

		Rectangle cheekLfill2;
		cheekLfill2 = new Rectangle(279, 476, 60, 43);
		cheekLfill2.setBackground(java.awt.Color.pink);
		win.add(cheekLfill2);

		Oval noseOutline1;
		noseOutline1 = new Oval(360, 375, 90, 150);
		win.add(noseOutline1);

		// The next few methods will create the nose.
		Oval noseFill1;
		noseFill1 = new Oval(365, 380, 90, 150);
		noseFill1.setBackground(java.awt.Color.pink);
		win.add(noseFill1);

		Oval noseOutline2;
		noseOutline2 = new Oval(380, 380, 90, 150);
		win.add(noseOutline2);

		Oval noseFill2;
		noseFill2 = new Oval(385, 385, 80, 140);
		noseFill2.setBackground(java.awt.Color.pink);
		win.add(noseFill2);

		Oval nostrilsR;
		nostrilsR = new Oval(435, 415, 15, 30);
		win.add(nostrilsR);

		Oval nostrilsL;
		nostrilsL = new Oval(405, 415, 15, 30);
		win.add(nostrilsL);

		Rectangle noseBridge;
		noseBridge = new Rectangle(360, 375, 40, 5);
		noseBridge.setBackground(java.awt.Color.black);
		win.add(noseBridge);

		Rectangle noseBottomLine;
		noseBottomLine = new Rectangle(334, 460, 135, 5);
		win.add(noseBottomLine);

		// The next couple of methods covers over the unwanted lines and areas
		// in the lower face area.
		Rectangle chinFill;
		chinFill = new Rectangle(338, 465, 135, 45);
		chinFill.setBackground(java.awt.Color.pink);
		win.add(chinFill);

		Rectangle chinFill2;
		chinFill2 = new Rectangle(444, 500, 11, 23);
		chinFill2.setBackground(java.awt.Color.pink);
		win.add(chinFill2);

		// The next methods creates the mouth, tongue, and covers any
		// remaining unwanted areas and lines.
		Triangle mouthOutline;
		mouthOutline = new Triangle(338, 465, 110, 65, 0);
		win.add(mouthOutline);

		Triangle mouth;
		mouth = new Triangle(346, 465, 95, 55, 0);
		mouth.setBackground(java.awt.Color.red);
		win.add(mouth);

		Rectangle lowerMouthline;
		lowerMouthline = new Rectangle(370, 495, 45, 5);
		lowerMouthline.setBackground(java.awt.Color.black);
		win.add(lowerMouthline);

		Triangle tongue;
		tongue = new Triangle(368, 465, 40, 15, 0);
		win.add(tongue);

		Rectangle chinfill2;
		chinfill2 = new Rectangle(338, 500, 106, 30);
		chinfill2.setBackground(java.awt.Color.pink);
		win.add(chinfill2);

		// The next methods will create the ears.
		Triangle earR;
		earR = new Triangle(445, 180, 120, 120, 0);
		earR.setBackground(java.awt.Color.pink);
		win.add(earR);

		Triangle earL;
		earL = new Triangle(115, 225, 155, 150, 0);
		earL.setBackground(java.awt.Color.pink);
		win.add(earL);

		Rectangle earMarkR;
		earMarkR = new Rectangle(480, 190, 60, 5);
		win.add(earMarkR);

		Rectangle earMarkL;
		earMarkL = new Rectangle(150, 235, 70, 5);
		win.add(earMarkL);

	}
}

package sample;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;

import MyGraphLib.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.*;

import static MyGraphLib.Warnok.*;


public class Controller {


    @FXML
    private Canvas myHolst;


    private  ArrayList<JColorFigure> m;

    @FXML
    void initialize() {
        assert myHolst != null : "fx:id=\"myHolst\" was not injected: check your FXML file 'sample.fxml'.";
        m = new ArrayList<>();
        Random rnd = new Random();



       JColorFigure cf = new JColorFigure();

        cf.setColor(Color.RED);
        cf.addLine(new Point3D(1,3,0), new Point3D(1,7,0));
        cf.addLine(new Point3D(1,7,0), new Point3D(5,10,0));
        cf.addLine(new Point3D(5,10,0), new Point3D(10,7,0));
        cf.addLine(new Point3D(10,7,0), new Point3D(1,3,0));

        cf.turnFigureAroundX(rnd.nextInt(60));
        cf.turnFigureAroundY(rnd.nextInt(180));
        cf.turnFigureAroundZ(rnd.nextInt(360));
        m.add(cf.proecColorFigureAksom(0,90));


        JColorFigure pf = new JColorFigure();
        pf.addLine(new Point3D(-7,-7,0), new Point3D(-10,3,0));
        pf.addLine(new Point3D(-10,3,0), new Point3D(-3,10,0));
        pf.addLine(new Point3D(-3,10,0), new Point3D(5,5,0));
        pf.addLine(new Point3D(5,5,0), new Point3D(7,-7,0));
        pf.addLine(new Point3D(7,-7,0), new Point3D(-7,-7,0));
        pf.setColor(Color.BROWN);
        pf.turnFigureAroundX(rnd.nextInt(60));
        pf.turnFigureAroundY(rnd.nextInt(180));
        pf.turnFigureAroundZ(rnd.nextInt(360));
        m.add(pf.proecColorFigureAksom(0,90));


        cf = new JColorFigure();
        cf.addLine(new Point3D(1,-6,0), new Point3D(-2,2,0));
        cf.addLine(new Point3D(-2,2,0), new Point3D(4,2,0));
        cf.addLine(new Point3D(4,2,0), new Point3D(1,-6,0));
        cf.setColor(Color.AQUA);
        cf.turnFigureAroundX(rnd.nextInt(60));
        cf.turnFigureAroundY(rnd.nextInt(180));
        cf.turnFigureAroundZ(rnd.nextInt(360));
        m.add(cf.proecColorFigureAksom(0,90));//*/

        cf = new JColorFigure();
        cf.setColor(Color.GREEN);
        cf.addLine(new Point3D(1,3,0), new Point3D(1,7,0));
        cf.addLine(new Point3D(1,7,0), new Point3D(5,10,0));
        cf.addLine(new Point3D(5,10,0), new Point3D(10,7,0));
        cf.addLine(new Point3D(10,7,0), new Point3D(1,3,0));

        cf.turnFigureAroundX(rnd.nextInt(60));
        cf.turnFigureAroundY(rnd.nextInt(180));
        cf.turnFigureAroundZ(rnd.nextInt(360));
        m.add(cf.proecColorFigureAksom(0,90));

        cf = new JColorFigure();
        cf.addLine(new Point3D(1,-6,0), new Point3D(-2,2,0));
        cf.addLine(new Point3D(-2,2,0), new Point3D(4,2,0));
        cf.addLine(new Point3D(4,2,0), new Point3D(1,-6,0));
        cf.setColor(Color.GRAY);
        cf.turnFigureAroundX(rnd.nextInt(60));
        cf.turnFigureAroundY(rnd.nextInt(180));
        cf.turnFigureAroundZ(rnd.nextInt(180));
        m.add(cf.proecColorFigureAksom(0,90));
        //this.drawFigure();

        m.sort((c1,c2) -> (int)(Warnok.maxZ(c2)-Warnok.maxZ(c1)));
        wins.add(new Win(600,0,0,600));
    }

    public void drawFigure(){
        animationTimer.stop();
        myHolst.getGraphicsContext2D().clearRect(0, 0, myHolst.getWidth(), myHolst.getHeight());
        for(JColorFigure cf: m){
            cf.drawFllFigure(myHolst);
        }
    }



    public void buttonRunClick(){
        /*myHolst.getGraphicsContext2D().clearRect(0, 0, myHolst.getWidth(), myHolst.getHeight());
        Warnok.algorithm(new Win(600,0,0,600),m,myHolst);*/
        //
        myHolst.getGraphicsContext2D().clearRect(0, 0, myHolst.getWidth(), myHolst.getHeight());
        animationTimer.start();
    }

    public void buttonRunClick1(){
        animationTimer.stop();
        myHolst.getGraphicsContext2D().clearRect(0, 0, myHolst.getWidth(), myHolst.getHeight());
        Warnok.algorithm(new Win(600,0,0,600),m,myHolst);//*/
        Warnok.algorithm(new Win(600,0,0,600),m,myHolst);
        Warnok.algorithm(new Win(600,0,0,600),m,myHolst);
        Warnok.algorithm(new Win(600,0,0,600),m,myHolst);//*/
        //animationTimer.start();
    }

    private Stack<Win> wins = new Stack<>();
    private AnimationTimer animationTimer =new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (wins.empty()) animationTimer.stop();
            Warnok.algorithm(wins.pop(),wins,m,myHolst);
        }
    };

}

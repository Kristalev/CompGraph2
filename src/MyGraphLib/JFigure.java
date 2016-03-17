package MyGraphLib;

import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Данил on 03.02.2016.
 */
public class JFigure {
    public class JLine{
        Point3D fist;
        Point3D second;

        public Point3D getSecond() {
            return second;
        }

        public void setSecond(Point3D second) {
            this.second = second;
        }

        public Point3D getFist() {
            return fist;
        }

        public void setFist(Point3D fist) {
            this.fist = fist;
        }

        public JLine(Point3D f,Point3D s){
            this.fist = f;
            this.second = s;
        }
        public JLine(){
            this.fist = new Point3D(0,0,0);
            this.second = new Point3D(0,0,0);
        }

        private Point3D transferPoint(double x, double y, double z,double dx, double dy, double dz) {
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;

            double tM[][] = new double[4][4];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    if (i == j)
                        tM[i][j] = 1;
                    if (j == 3) {
                        switch (i) {
                            case 0:
                                tM[i][j] = -dx;
                                break;
                            case 1:
                                tM[i][j] = -dy;
                                break;
                            case 2:
                                tM[i][j] = -dz;
                                break;
                        }

                    }

                }

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            return new Point3D(corNewP[0][0],corNewP[1][0],corNewP[2][0]);
        }

        public void transferLine(double dx, double dy, double dz){
            this.fist = this.transferPoint(this.fist.getX(),this.fist.getY(),this.fist.getZ(),dx,dy,dz);
            this.second =  this.transferPoint(this.second.getX(),this.second.getY(),this.second.getZ(),dx,dy,dz);
        }

        private Point3D scalingPoint(double x, double y, double z,double kx, double ky, double kz){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            for (int i = 0; i < 4; i++)
                switch (i){
                    case 0:  tM[i][i] = kx; break;
                    case 1:  tM[i][i] = ky; break;
                    case 2:  tM[i][i] = kz; break;
                    case 3:  tM[i][i] = 1; break;
                }

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            return new Point3D(corNewP[0][0],corNewP[1][0],corNewP[2][0]);
        }

        public void scalingLine(double kx,double ky,double kz){
            this.fist = this.scalingPoint(this.fist.getX(),this.fist.getY(),this.fist.getZ(),kx,ky,kz);
            this.second =  this.scalingPoint(this.second.getX(),this.second.getY(),this.second.getZ(),kx,ky,kz);
        }

        private Point3D turnPointAroundX(double x, double y, double z,double rad){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            tM[0][0] = 1;
            tM[1][1] = Math.cos(rad);
            tM[1][2] = -Math.sin(rad);
            tM[2][1] = Math.sin(rad);
            tM[2][2] = Math.cos(rad);
            tM[3][3] = 1;

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            return new Point3D(corNewP[0][0],corNewP[1][0],corNewP[2][0]);

        }

        public void turnLineAroundX(double angle){
            double rad = Math.toRadians(angle);
            this.fist = this.turnPointAroundX(this.fist.getX(),this.fist.getY(),this.fist.getZ(),rad);
            this.second = this.turnPointAroundX(this.second.getX(),this.second.getY(),this.second.getZ(),rad);
        }

        private Point3D turnPointAroundY(double x, double y, double z,double rad){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            tM[0][0] = Math.cos(rad);
            tM[0][2] = -Math.sin(rad);
            tM[1][1] = 1;
            tM[2][0] = Math.sin(rad);
            tM[2][2] = Math.cos(rad);
            tM[3][3] = 1;

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            return new Point3D(corNewP[0][0],corNewP[1][0],corNewP[2][0]);

        }

        public void turnLineAroundY(double angle){
            double rad = Math.toRadians(angle);
            this.fist = this.turnPointAroundY(this.fist.getX(),this.fist.getY(),this.fist.getZ(),rad);
            this.second = this.turnPointAroundY(this.second.getX(),this.second.getY(),this.second.getZ(),rad);
        }

        private Point3D turnPointAroundZ(double x, double y, double z,double rad){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            tM[0][0] = Math.cos(rad);
            tM[0][1] = -Math.sin(rad);
            tM[1][0] = Math.sin(rad);
            tM[1][1] = Math.cos(rad);
            tM[2][2] = 1;
            tM[3][3] = 1;

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            return new Point3D(corNewP[0][0],corNewP[1][0],corNewP[2][0]);

        }
        public void turnLineAroundZ(double angle){
            double rad = Math.toRadians(angle);
            this.fist = this.turnPointAroundZ(this.fist.getX(),this.fist.getY(),this.fist.getZ(),rad);
            this.second = this.turnPointAroundZ(this.second.getX(),this.second.getY(),this.second.getZ(),rad);
        }

        private Point3D proecPoint(double x, double y, double z,double rad, double h){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            tM[0][0] = Math.cos(rad);
            tM[0][1] = -Math.sin(rad);
            tM[1][2] = -1;
            tM[1][3] = h;
            tM[2][0] = Math.sin(rad);
            tM[2][1] = Math.cos(rad);
            tM[3][3] = 1;

            /*double pM[][] = new double[4][4];
            tM[0][0] = 1;
            tM[1][1] = 1;
            tM[2][2] = 0;
            tM[2][0] = 0.5*Math.cos(Math.toRadians(45));
            tM[2][1] = 0.5*Math.sin(Math.toRadians(45));
            tM[2][3] = 0;
            tM[3][3] = 1;

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += pM[i][j] * corP[j][0];
                }

            double corProecLine[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    corProecLine[i][0] +=  pM[i][j] * corNewP[j][0];
                }*/

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            double X = corNewP[0][0]*(2000 - 700) / (2000 - corNewP[2][0]);
            double Y = corNewP[1][0]*(2000 - 700) / (2000 - corNewP[2][0]);

            Y -= 325; //это мистика!!! это магия!!! трогать нельзя!!!


            return new Point3D(20*X+300,20*Y+300,0);
        }

        public JLine proecLine(double alph, double h){
            JLine newLine = new JLine();
            double rad = Math.toRadians(alph);
            newLine.setFist(this.proecPoint(this.fist.getX(),this.fist.getZ(),this.fist.getY(),rad, h));
            newLine.setSecond(this.proecPoint(this.second.getX(), this.second.getZ(), this.second.getY(), rad, h));
            return newLine;
        }

        private Point3D proecPointAksom(double x, double y, double z,double radA, double radB){
            double corP[][] = new double[4][1];
            corP[0][0] = x;
            corP[1][0] = y;
            corP[2][0] = z;
            corP[3][0] = 1;
            double tM[][] = new double[4][4];
            tM[0][0] = Math.cos(radA);
            tM[0][1] = -Math.sin(radA);
            tM[1][0] = Math.sin(radA)*Math.cos(radB);
            tM[1][1] = Math.cos(radA)*Math.cos(radB);
            tM[1][2] = -Math.sin(radB);
            tM[2][0] = Math.sin(radA)*Math.sin(radB);
            tM[2][1] = Math.cos(radA)*Math.sin(radB);
            tM[2][2] = Math.cos(radB);
            tM[3][3] = 1;

            double corNewP[][] = new double[4][1];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++){
                    corNewP[i][0] += tM[i][j] * corP[j][0];
                }
            double X = corNewP[0][0];
            double Y = corNewP[1][0];
            double Z = corNewP[2][0];
            //return new Point3D(20*X+300,20*Y+300,0);
            return new Point3D(15*X+300,15*Y+300,15*Z);
        }

        public JLine proecLineAksom(double alph, double bet){
            JLine newLine = new JLine();
            double radA = Math.toRadians(alph);
            double radB = Math.toRadians(bet);
            newLine.setFist(this.proecPointAksom(this.fist.getX(),this.fist.getZ(),this.fist.getY(),radA, radB));
            newLine.setSecond(this.proecPointAksom(this.second.getX(), this.second.getZ(), this.second.getY(), radA, radB));
            return newLine;
        }

        public void drawLine(Canvas can){
            GraphicsContext g = can.getGraphicsContext2D();
            g.strokeLine(this.fist.getX(),this.fist.getY(),this.second.getX(),this.second.getY());
        }

    }



    protected ArrayList<JLine> lines;

    public JFigure(){
        this.lines = new ArrayList<>();
    }


    public void transferFigure(double dx, double dy, double dz){
        for(JLine line: lines){
            line.transferLine(dx,dy,dz);
        }
    }

    public void scalingFigure(double kx,double ky,double kz){
        for(JLine line: lines){
            line.scalingLine(kx, ky, kz);
        }
    }

    public void turnFigureAroundX(double angle){
        for(JLine line: lines){
            line.turnLineAroundX(angle);
        }
    }

    public void turnFigureAroundY(double angle){
        for(JLine line: lines){
            line.turnLineAroundY(angle);
        }
    }

    public void turnFigureAroundZ(double angle){
        for(JLine line: lines){
            line.turnLineAroundZ(angle);
        }
    }

    public ArrayList<JLine> proecFigure(double alph, double h){
        ArrayList<JLine> pr = lines.stream().map(line -> line.proecLine(alph, h)).collect(Collectors.toCollection(ArrayList::new));
        return pr;
    }

    public ArrayList<JLine> proecFigureAksom(double alph, double bet){
        ArrayList<JLine> pr = new ArrayList<>();
        for(JLine line: lines){
            JLine l = line.proecLineAksom(alph,bet);
            pr.add(l);
        }
        return pr;
    }

    public void addLine(Point3D fist,Point3D sec){
        JLine jl = new JLine(fist,sec);
        lines.add(jl);
    }



    public void zadan(int step){
        if(step != 0){
            double xSr = 0;
            double ySr = 0;
            double zSr = 0;
            int count = 0;
            for(JLine line: lines){
                xSr += line.fist.getX();
                xSr += line.second.getX();
                ySr += line.fist.getY();
                ySr += line.second.getY();
                zSr += line.fist.getZ();
                zSr += line.second.getZ();
                count++;
            }
            this.transferFigure((xSr/(count*2)/step),(ySr/(count*2)/step),(zSr/(count*2)/step));
        }
       this.turnFigureAroundY(2);
    }

    public ArrayList<JLine> getLines(){
        return lines;
    }

}

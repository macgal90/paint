import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    int posX, posX2, topY, topY2, width, height;
    boolean first = true;
    static int xWidth = 50 + 7, yHeight = 50 + 30;
    public ArrayList<Rectangle2D> rectang = new ArrayList<Rectangle2D>();
    public Iterator<Rectangle2D> re = rectang.iterator();
    public ArrayList<Rectangle2D> point = new ArrayList<Rectangle2D>();
    public ArrayList<Ellipse2D> ellipse = new ArrayList<Ellipse2D>();
    public ArrayList<String> clearList = new ArrayList<>();
    public ArrayList<Color> color = new ArrayList<>();
    private String choise;
    private Color newColor;

    void setChoise(String choise) {
        this.choise = choise;
    }

    void setNewColor(Color newColor) {
        this.newColor = newColor;
    }

    void back() {
        if (!rectang.isEmpty() && !clearList.isEmpty() && clearList.get(clearList.size() - 1) == "r") {
            rectang.remove(rectang.size() - 1);
            repaint();
            clearList.remove(clearList.size() - 1);
            color.remove(color.size() - 1);
        }
        if (!point.isEmpty() && !clearList.isEmpty() && clearList.get(clearList.size() - 1) == "p") {
            point.remove(point.size() - 1);
            repaint();
            clearList.remove(clearList.size() - 1);
        }
        if (!ellipse.isEmpty() && !clearList.isEmpty() && clearList.get(clearList.size() - 1) == "e") {
            ellipse.remove(ellipse.size() - 1);
            repaint();
            clearList.remove(clearList.size() - 1);
        }
    }

    void clear() {
        rectang.clear();
        ellipse.clear();
        clearList.clear();
        point.clear();
        color.clear();
        newColor = Color.BLACK;
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (choise == "Kwadrat") {
            Rectangle2D rectangle = new Rectangle2D.Double(posX - xWidth, topY - yHeight, width, height);
            g2d.setColor(newColor);
            g2d.fill(rectangle);
        }
        if (choise == "Elipsa") {
            Ellipse2D ellipse = new Ellipse2D.Double(posX - xWidth, topY - yHeight, width, height);
            g2d.setColor(newColor);
            g2d.fill(ellipse);
        }
        if (!clearList.isEmpty()) {
            int j = 0, k = 0, l = 0;
            for (int i = 0; i < clearList.size(); i++) {
                g2d.setColor(color.get(i));
                if (clearList.get(i) == "p") {
                    g2d.fill(point.get(j));
                    j++;
                }
                if (clearList.get(i) == "r") {
                    g2d.fill(rectang.get(k));
                    k++;
                }
                if (clearList.get(i) == "e") {
                    g2d.fill(ellipse.get(l));
                    l++;
                }
            }
        }
//        for (Rectangle2D rect : rectang
//        ) {
//            g2d.draw(rect);
//        }
//        for (Rectangle2D point : point
//        ) {
//            if (newColor != null)
//                g2d.setColor(newColor);
//            g2d.fill(point);
//            //g2d.draw(point);
//        }
//        for (Ellipse2D elip : ellipse
//        ) {
//            g2d.draw(elip);
//        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        first = true;
        if (choise == "Kwadrat") {
            clearList.add("r");
            rectang.add(new Rectangle2D.Double(posX - xWidth, topY - yHeight, width, height));
            if (newColor != null)
                color.add(newColor);
            else
                color.add(Color.BLACK);

        }
        if (choise == "Elipsa") {
            clearList.add("e");
            ellipse.add(new Ellipse2D.Double(posX - xWidth, topY - yHeight, width, height));
            if (newColor != null)
                color.add(newColor);
            else
                color.add(Color.BLACK);
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (choise == "Kwadrat" || choise == "Elipsa")
            drawRectangle(e);
        if (choise == "Pędzel") {
            clearList.add("p");
            point.add(new Rectangle2D.Double(e.getX() - xWidth, e.getY() - yHeight, 3, 3));
            if (newColor != null)
                color.add(newColor);
            else
                color.add(Color.BLACK);
            repaint();
        }

    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void drawRectangle(MouseEvent e) {
        if (first) {
            posX2 = e.getX();
            posX = e.getX();
            topY2 = e.getY();
            topY = e.getY();
            first = false;
        } else {
            if (posX <= e.getX()) {
                width = e.getX() - posX;
            } else {
                width = -(e.getX() - posX2);
                posX = e.getX();
                if (posX == posX2)
                    posX2 = posX;
            }
            if (topY <= e.getY()) {
                height = e.getY() - topY;
            } else {
                topY = e.getY();
                height = -(e.getY() - topY2);
                if (topY == topY2)
                    topY2 = e.getY();
            }
            repaint();
        }
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
    int posX, posX2, topY, topY2, width, height;
    boolean first = true;
    static int xWidth = 50 + 7, yHeight = 50 + 30;
    public ArrayList<Rectangle2D> rectang = new ArrayList<Rectangle2D>();
    public ArrayList<Ellipse2D> point = new ArrayList<Ellipse2D>();
    public ArrayList<Ellipse2D> ellipse = new ArrayList<Ellipse2D>();
    public ArrayList<Line2D> line = new ArrayList<Line2D>();
    public ArrayList<String> clearList = new ArrayList<>();
    public ArrayList<Color> color = new ArrayList<>();
    private String choise;
    private Integer size = 10;
    private Color newColor;
    private boolean fill = false;

    void setChoise(String choise) {
        this.choise = choise;
    }

    void setSize(int size) {
        this.size = size;
    }

    void setNewColor(Color newColor) {
        this.newColor = newColor;
    }

    void setFill(boolean fill) {
        this.fill = fill;
    }

    ;

    void back() {
        if (!clearList.isEmpty()) {
            if (!rectang.isEmpty() && clearList.get(clearList.size() - 1) == "r") {
                rectang.remove(rectang.size() - 1);
            }
            if (!point.isEmpty() && clearList.get(clearList.size() - 1) == "p") {
                point.remove(point.size() - 1);
            }
            if (!ellipse.isEmpty() && clearList.get(clearList.size() - 1) == "e") {
                ellipse.remove(ellipse.size() - 1);
            }
            if (!line.isEmpty() && clearList.get(clearList.size() - 1) == "l") {
                line.remove(line.size() - 1);
            }
            repaint();
            clearList.remove(clearList.size() - 1);
            color.remove(color.size() - 1);
        }
    }

    void clear() {
        rectang.clear();
        ellipse.clear();
        clearList.clear();
        line.clear();
        point.clear();
        color.clear();
        newColor = Color.BLACK;
    }

    void save() {
        BufferedImage bImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        this.paintAll(cg);
        JFileChooser jfc = new JFileChooser();
        try {
            jfc.showSaveDialog(this);
            if (ImageIO.write(bImg, "png", jfc.getSelectedFile())) {
                System.out.println("The file was saved!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (!clearList.isEmpty()) {
            int j = 0, k = 0, l = 0, m = 0;
            for (int i = 0; i < clearList.size(); i++) {
                g2d.setColor(color.get(i));
                if (clearList.get(i) == "p") {
                    g2d.fill(point.get(j));
                    j++;
                }
                if (clearList.get(i) == "r") {
                    g2d.draw(rectang.get(k));
                    k++;
                }
                if (clearList.get(i) == "rf") {
                    g2d.fill(rectang.get(k));
                    k++;
                }
                if (clearList.get(i) == "e") {
                    g2d.draw(ellipse.get(l));
                    l++;
                }
                if (clearList.get(i) == "ef") {
                    g2d.fill(ellipse.get(l));
                    l++;
                }
                if (clearList.get(i) == "l") {
                    g2d.draw(line.get(m));
                    m++;
                }
            }
        }
        g2d.setColor(newColor);
        if (choise == "Kwadrat") {
            Rectangle2D rectangle = new Rectangle2D.Double(posX - xWidth, topY - yHeight, width, height);
            if (fill)
                g2d.fill(rectangle);
            else
                g2d.draw((rectangle));
        }
        if (choise == "Elipsa") {
            Ellipse2D ellipse = new Ellipse2D.Double(posX - xWidth, topY - yHeight, width, height);
            if (fill)
                g2d.fill(ellipse);
            else
                g2d.draw(ellipse);
        }
        if (choise == "Linia") {
            Line2D line = new Line2D.Double(posX - xWidth, topY - yHeight, posX2 - xWidth, topY2 - yHeight);
            g2d.draw(line);
        }
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
            rectang.add(new Rectangle2D.Double(posX - xWidth, topY - yHeight, width, height));
            addColor();
            if (fill)
                clearList.add("rf");
            else
                clearList.add("r");
        }
        if (choise == "Elipsa") {
            ellipse.add(new Ellipse2D.Double(posX - xWidth, topY - yHeight, width, height));
            addColor();
            if (fill)
                clearList.add("ef");
            else
                clearList.add("e");
        }
        if (choise == "Linia") {
            clearList.add("l");
            line.add(new Line2D.Double(posX - xWidth, topY - yHeight, posX2 - xWidth, topY2 - yHeight));
            addColor();
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
        if (choise == "Linia")
            drawLine(e);
        if (choise == "Pędzel") {
            clearList.add("p");
            point.add(new Ellipse2D.Double(e.getX() - xWidth, e.getY() - yHeight, size, size));
            addColor();
            repaint();
        }

    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void addColor() {
        if (newColor != null)
            color.add(newColor);
        else
            color.add(Color.BLACK);
    }

    public void drawLine(MouseEvent e) {
        if (first) {
            posX = e.getX();
            topY = e.getY();
            first = false;
        } else {
            posX2 = e.getX();
            topY2 = e.getY();
        }
        repaint();
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

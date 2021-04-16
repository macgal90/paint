import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main extends JFrame {
    DrawPanel draw = new DrawPanel();

    public Main() {
        setTitle("Paint");
        setVisible(true);
        setLayout(null);
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(getToolkit().getScreenSize().width / 2 - this.getWidth() / 2,
                getToolkit().getScreenSize().height / 2 - this.getHeight() / 2);


        draw.setBorder(new LineBorder(Color.BLACK, 1, true));
        draw.setBounds(50, 50, 500, 500);
        getContentPane().add(draw);
        addMouseListener(draw);
        addMouseMotionListener(draw);

        JSlider scroll = new JSlider(JSlider.HORIZONTAL, 2, 20, 10);
        add(scroll);
        scroll.setBounds(700, 125, 90, 100);
        scroll.addChangeListener(this::stateChanged);

        JCheckBox fill = new JCheckBox("Fill", false);
        fill.setBounds(700, 265, 50, 50);
        add(fill);
        fill.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1)
                    draw.setFill(true);
                else
                    draw.setFill(false);
            }
        });

        int y = 30;
        String[] tab = {"Wyczyść", "Cofnij", "Pędzel", "Linia", "Kwadrat", "Elipsa", "Kolor", "Kolor tła", "Zapisz"};
        for (int i = 0; i <= 8; i++) {
            JButton button = new JButton("");
            add(button);
            button.setName(tab[i]);
            button.setText(tab[i]);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setBounds(590, y, 100, 40);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getText() == tab[0]) {
                        draw.setChoise(tab[0]);
                        draw.clear();
                        draw.setBackground(null);
                        draw.removeAll();
                        revalidate();
                        repaint();
                    }
                    if (button.getText() == tab[1]) {
                        draw.setChoise(tab[1]);
                        draw.back();
                    }
                    if (button.getText() == tab[2]) {
                        draw.setChoise(tab[2]);
                    }
                    if (button.getText() == tab[3]) {
                        draw.setChoise(tab[3]);
                    }
                    if (button.getText() == tab[4]) {
                        draw.setChoise(tab[4]);
                    }
                    if (button.getText() == tab[5]) {
                        draw.setChoise(tab[5]);
                    }
                    if (button.getText() == tab[6]) {
                        Color newColor = JColorChooser.showDialog(null,
                                "Wybierz kolor", draw.getBackground());
                        draw.setNewColor(newColor);
                    }
                    if (button.getText() == tab[7]) {
                        Color newColor = JColorChooser.showDialog(null,
                                "Wybierz kolor", draw.getBackground());
                        draw.setBackground(newColor);
                    }
                    if (button.getText() == tab[8])
                        draw.save();
                }
            });
            y += 60;
        }


    }


    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int size = (int) source.getValue();
        draw.setSize(size);
    }


    public static void main(String[] args) {
        new Main();
    }
}

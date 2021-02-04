import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Slider implements ChangeListener {

    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 90;
    private static final int START_VALUE = 50;
    private static final int MINOR_SPACING = 1;
    private static final int MAJOR_SPACING = 10;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final int SLIDER_WIDTH = 740;
    private static final int SLIDER_HEIGHT = 200;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JSlider slider;
    private JButton button;
    private int sliderValue;

    public Slider() {
        frame = new JFrame("Parallel slider");
        panel = new JPanel();
        label = new JLabel();
        button = new JButton("Click");
        slider = new JSlider(MIN_VALUE, MAX_VALUE, START_VALUE);
        sliderValue = START_VALUE;

        slider.setPreferredSize(new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT));
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(MINOR_SPACING);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(MAJOR_SPACING);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);

        label.setText("Current slider value is:" + slider.getValue());

        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.addActionListener(e -> {

            Thread sliderValueChangerMax = new Thread(() -> {

                System.out.println("123");

                try {
                    changeSliderValueMax();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });

            Thread sliderValueChangerMin = new Thread(() -> {

                System.out.println("321");

                try {
                    changeSliderValueMin();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });

            sliderValueChangerMax.start();
            sliderValueChangerMin.start();
        });

        panel.add(slider);
        panel.add(label);
        panel.add(button);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

    private synchronized void changeSliderValueMax() throws InterruptedException {

        while(sliderValue < MAX_VALUE) {
            sliderValue++;
            slider.setValue(sliderValue);
            wait(400);
        }
    }

    private synchronized void changeSliderValueMin() throws InterruptedException {

        while(sliderValue < MAX_VALUE) {
            sliderValue--;
            slider.setValue(sliderValue);
            wait(300);
        }
    }

    public void setSliderValue(int value) {
        slider.setValue(value);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText("Current slider value is:" + slider.getValue());
    }

}

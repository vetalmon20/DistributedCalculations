import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class Slider implements ChangeListener {

    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 90;
    private static final int START_VALUE = 20;
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
    private JButton button, bStartIncr, bStartDecr, bStopIncr, bStopDecr;
    private int sliderValue;
    private AtomicBoolean threadIncrFlag, threadDecrFlag;

    public Slider() {
        frame = new JFrame("Parallel slider");
        panel = new JPanel();
        label = new JLabel();
        button = new JButton("Click");
        bStartIncr = new JButton("Start incr");
        bStartDecr = new JButton("Start decr");
        bStopIncr = new JButton("Stop incr");
        bStopDecr = new JButton("Stop decr");
        slider = new JSlider(MIN_VALUE, MAX_VALUE, START_VALUE);
        sliderValue = START_VALUE;
        threadIncrFlag = new AtomicBoolean(false);
        threadDecrFlag = new AtomicBoolean(false);

        slider.setPreferredSize(new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT));
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(MINOR_SPACING);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(MAJOR_SPACING);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);

        label.setText("Current slider value is:" + slider.getValue());

        bStartIncr.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bStartDecr.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bStopIncr.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bStopDecr.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));


        bStartIncr.addActionListener(e -> {
            Thread thread = createThreadIncr();
            thread.start();
        });

        bStartDecr.addActionListener(e -> {
            Thread thread = createThreadDecr();
            thread.start();
        });

        bStopIncr.addActionListener(e -> {
            threadIncrFlag.set(false);
        });

        bStopDecr.addActionListener(e -> {
            threadDecrFlag.set(false);
        });

        panel.add(slider);
        panel.add(label);
        panel.add(button);
        panel.add(bStartIncr);
        panel.add(bStartDecr);
        panel.add(bStopIncr);
        panel.add(bStopDecr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
    }

    private Thread createThreadIncr() {

        Thread thread = new Thread(() -> {
            System.out.println("123");

            try {
                changeSliderValueIncr();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        thread.setPriority(1);
        return thread;
    }

    private Thread createThreadDecr() {

        Thread thread = new Thread(() -> {
            System.out.println("321");

            try {
                changeSliderValueDecr();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        thread.setPriority(10);
        return thread;
    }

    private synchronized void changeSliderValueIncr() throws InterruptedException {

        if(threadIncrFlag.get()) {
            return;
        }

        threadIncrFlag.set(true);

        while(sliderValue < MAX_VALUE && threadIncrFlag.get()) {
            sliderValue++;
            slider.setValue(sliderValue);
            wait(400);
        }
    }

    private synchronized void changeSliderValueDecr() throws InterruptedException {

        if(threadDecrFlag.get()) {
            return;
        }

        threadDecrFlag.set(true);

        while(sliderValue > MIN_VALUE && threadDecrFlag.get()) {
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

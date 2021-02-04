import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.concurrent.Semaphore;
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
    private static final int INCR_DELAY = 150;
    private static final int DECR_DELAY = 100;

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel label;
    private final JSlider slider;
    private final JButton bStartIncr, bStartDecr, bStopIncr, bStopDecr;
    private int sliderValue;
    private final AtomicBoolean threadIncrFlag, threadDecrFlag;
    private final Semaphore semaphore;

    public Slider() {
        frame = new JFrame("Parallel slider");
        panel = new JPanel();
        label = new JLabel();
        bStartIncr = new JButton("Start incr");
        bStartDecr = new JButton("Start decr");
        bStopIncr = new JButton("Stop incr");
        bStopDecr = new JButton("Stop decr");
        slider = new JSlider(MIN_VALUE, MAX_VALUE, START_VALUE);
        sliderValue = START_VALUE;
        threadIncrFlag = new AtomicBoolean(false);
        threadDecrFlag = new AtomicBoolean(false);
        semaphore = new Semaphore(1);

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

        bStartIncr.addActionListener(e -> {
            Thread thread = createThreadIncr();
            thread.start();
        });

        bStartDecr.addActionListener(e -> {
            Thread thread = createThreadDecr();
            thread.start();
        });

        bStopIncr.addActionListener(e -> {
            if(semaphore.availablePermits() == 0) {
                semaphore.release();
            }
            threadIncrFlag.set(false);
            bStopDecr.setEnabled(true);
        });

        bStopDecr.addActionListener(e -> {
            if(semaphore.availablePermits() == 0) {
                semaphore.release();
            }
            threadDecrFlag.set(false);
            bStopIncr.setEnabled(true);
        });

        panel.add(slider);
        panel.add(label);
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

        if(semaphore.availablePermits() <= 0) {
            new Thread(() -> JOptionPane.showMessageDialog(frame, "The slider is currently busy by another thread")).start();
            return;
        }

        bStopDecr.setEnabled(false);
        semaphore.acquire();
        threadIncrFlag.set(true);

        while(sliderValue < MAX_VALUE && threadIncrFlag.get()) {
            sliderValue++;
            slider.setValue(sliderValue);
            wait(INCR_DELAY);
        }

        bStopDecr.setEnabled(true);
        threadIncrFlag.set(false);

        if(semaphore.availablePermits() == 0) {
            semaphore.release();
        }
    }

    private synchronized void changeSliderValueDecr() throws InterruptedException {

        if(threadDecrFlag.get()) {
            return;
        }

        if(semaphore.availablePermits() <= 0) {
            new Thread(() -> JOptionPane.showMessageDialog(frame, "The slider is currently busy by another thread")).start();
            return;
        }

        bStopIncr.setEnabled(false);
        semaphore.acquire();
        threadDecrFlag.set(true);

        while(sliderValue > MIN_VALUE && threadDecrFlag.get()) {
            sliderValue--;
            slider.setValue(sliderValue);
            wait(DECR_DELAY);
        }

        bStopIncr.setEnabled(true);
        threadDecrFlag.set(false);

        if(semaphore.availablePermits() == 0) {
            semaphore.release();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText("Current slider value is:" + slider.getValue());
    }

}

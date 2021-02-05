import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slider implements ChangeListener {

    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 90;
    private static final int START_VALUE = 12;
    private static final int MINOR_SPACING = 1;
    private static final int MAJOR_SPACING = 10;
    private static final int FRAME_WIDTH = 840;
    private static final int FRAME_HEIGHT = 400;
    private static final int SLIDER_WIDTH = 740;
    private static final int SLIDER_HEIGHT = 200;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;
    private static final int INCR_DELAY = 150;
    private static final int DECR_DELAY = 100;

    private static final Logger log = Logger.getLogger(Slider.class.getName());

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel label;
    private final JSlider slider;
    private final JButton bStartIncr, bStartDecr, bStopIncr, bStopDecr;
    private final JButton bStartBoth, bStopBoth, bPriorUpIncr, bPriorDownIncr, bPriorUpDecr, bPriorDownDecr;
    private int sliderValue;
    private final AtomicBoolean threadIncrFlag, threadDecrFlag;
    private final Semaphore semaphore;
    private Thread threadIncr, threadDecr;

    public Slider() {
        frame = new JFrame("Parallel slider");
        panel = new JPanel();
        label = new JLabel();
        bStartIncr = new JButton("Start incr");
        bStartDecr = new JButton("Start decr");
        bStopIncr = new JButton("Stop incr");
        bStopDecr = new JButton("Stop decr");
        bStartBoth = new JButton("Start both");
        bStopBoth = new JButton("Stop both");
        bPriorUpIncr = new JButton("Add incr priority");
        bPriorDownIncr = new JButton("Take away incr priority");
        bPriorUpDecr = new JButton("Add decr priority");
        bPriorDownDecr = new JButton("Take away decr priority");
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
            setEnabledTopButtons(true);
        });

        bStopDecr.addActionListener(e -> {
            if(semaphore.availablePermits() == 0) {
                semaphore.release();
            }
            threadDecrFlag.set(false);
            bStopIncr.setEnabled(true);
            setEnabledTopButtons(true);
        });

        bStartBoth.addActionListener(e -> startBothThreads());
        bStopBoth.addActionListener(e -> stopBothThreads());
        bPriorUpIncr.addActionListener(e -> changePriority(threadIncr, true));
        bPriorDownIncr.addActionListener(e -> changePriority(threadIncr, false));
        bPriorUpDecr.addActionListener(e -> changePriority(threadDecr, true));
        bPriorDownDecr.addActionListener(e -> changePriority(threadDecr, false));


        panel.add(bStartBoth);
        panel.add(bStopBoth);
        panel.add(bPriorUpIncr);
        panel.add(bPriorDownIncr);
        panel.add(bPriorUpDecr);
        panel.add(bPriorDownDecr);
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

    private void startBothThreads() {

        setEnabledBotButtons(false);
        Thread threadIncr = new Thread(() -> {
            threadIncrFlag.set(true);
            while(sliderValue < MAX_VALUE && threadIncrFlag.get()) {
                sliderValue++;
                slider.setValue(sliderValue);
                synchronized (this) {
                    try {
                        wait(INCR_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            threadIncrFlag.set(false);
            if(!threadDecrFlag.get()) {
                setEnabledBotButtons(true);
            }
            this.threadIncr = null;
        });
        if(this.threadIncr == null) {
            this.threadIncr = threadIncr;
            threadIncr.start();
        }


        Thread threadDecr = new Thread(() -> {
            threadDecrFlag.set(true);
            while(sliderValue > MIN_VALUE && threadDecrFlag.get()) {
                sliderValue--;
                slider.setValue(sliderValue);
                synchronized (this) {
                    try {
                        wait(DECR_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            threadDecrFlag.set(false);
            if(!threadIncrFlag.get()) {
                setEnabledBotButtons(true);
            }
            this.threadDecr = null;
        });

        if(this.threadDecr == null) {
            this.threadDecr = threadDecr;
            threadDecr.start();
        }
    }

    private void stopBothThreads() {
        threadIncrFlag.set(false);
        threadDecrFlag.set(false);
        this.threadIncr = null;
        this.threadDecr = null;
        setEnabledBotButtons(true);
    }

    private void changePriority(Thread thread, boolean isIncr) {
        if(thread == null) {
            return;
        }

        if(isIncr) {
            if(thread.getPriority() == 10) {
                return;
            }
            int temp = thread.getPriority() + 1;
            thread.setPriority(thread.getPriority() + 1);
            log.log(Level.INFO, "thread's #" + thread.toString() + " increased priority to " + temp);
        } else {
            if(thread.getPriority() == 1) {
                return;
            }
            int temp = thread.getPriority() - 1;
            thread.setPriority(thread.getPriority() - 1);
            log.log(Level.INFO, "thread's #" + thread.toString() + " decreased priority to " + temp);
        }
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
        setEnabledTopButtons(false);

        while(sliderValue < MAX_VALUE && threadIncrFlag.get()) {
            sliderValue++;
            slider.setValue(sliderValue);
            wait(INCR_DELAY);
        }

        setEnabledTopButtons(true);
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

        setEnabledTopButtons(false);
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
        setEnabledTopButtons(true);

        if(semaphore.availablePermits() == 0) {
            semaphore.release();
        }
    }

    private void setEnabledTopButtons(boolean isEnabled) {
        bStartBoth.setEnabled(isEnabled);
        bStopBoth.setEnabled(isEnabled);
        bPriorDownDecr.setEnabled(isEnabled);
        bPriorUpDecr.setEnabled(isEnabled);
        bPriorDownIncr.setEnabled(isEnabled);
        bPriorUpIncr.setEnabled(isEnabled);
    }

    private void setEnabledBotButtons(boolean isEnabled) {
        bStopIncr.setEnabled(isEnabled);
        bStartIncr.setEnabled(isEnabled);
        bStopDecr.setEnabled(isEnabled);
        bStartDecr.setEnabled(isEnabled);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        label.setText("Current slider value is:" + slider.getValue());
    }

}

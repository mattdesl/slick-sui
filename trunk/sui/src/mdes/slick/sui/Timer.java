/*
 * Timer.java
 *
 * Created on November 22, 2007, 2:32 PM
 */

package mdes.slick.sui;

import javax.swing.event.EventListenerList;

import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;

import org.lwjgl.Sys;
import org.newdawn.slick.gui.GUIContext;

/**
 * <p>A simple Sui-based Timer for using delays and "waits"
 * within a GUI environment.</p>
 * 
 * @author davedes
 */
public class Timer {
    
    private String command = null;
    
    /** Whether to repeat this timer. */
    private boolean repeating = true;
    
    /** The last time since update. */
    protected long lastTime = getTime();
    
    protected EventListenerList listenerList = new EventListenerList();
    
    protected boolean done = false;
    protected int delay;
    private int initialDelay;
    private boolean isAction;
    protected boolean active;
    private GUIContext container;
    protected float percent = 0f;
    private boolean firstRepeat = false;
    private long now;

    
    //public static final int ADVANCE = 1;
    //public static final int REVERSE = 0;
    //private int direction = ADVANCE;
    
    /** Creates a new instance of Timer */
    public Timer(int delay, ActionListener listener) {
        if (delay<0)
            throw new IllegalArgumentException("delay must be >= 0");
        this.delay = delay;
        this.setInitialDelay(delay);
        this.addActionListener(listener);
    }
    
    public Timer(int delay) {
        this(delay, null);
    }
    
    /**
     * Fires the specified action event to all action listeners
     * in this timer.
     * 
     * 
     * @param command the action command for the event
     * @see mdes.slick.sui.event.ActionEvent
     */
    protected void fireActionPerformed(String command) {
        ActionEvent evt = null;
        
        final ActionListener[] listeners =
                (ActionListener[])listenerList.getListeners(ActionListener.class);
        for (int i=0; i<listeners.length; i++) {
            //lazily create it
            if (evt==null) {
                evt = new ActionEvent(this, command);
            }
            listeners[i].actionPerformed(evt);
        }
    }
    
    /**
     * Adds the specified listener to the list.
     *
     * @param s the listener to receive events
     */
    public synchronized void addActionListener(ActionListener s) {
        listenerList.add(ActionListener.class, s);
    }
    
    /**
     * Removes the specified listener from the list.
     *
     * @param s the listener to remove
     */
    public synchronized void removeActionListener(ActionListener s) {
        listenerList.remove(ActionListener.class, s);
    }
    
    public float getPercent() {
        return percent;
    }
        
    public void update(GUIContext container, int delta) {
        this.container = container;
        isAction = false;
        
        //percent = Math.max(0f, Math.min(1f, percent));
        
        //if we are stopped
        if (!active)
            return;
        //or if we have finished repeating (once)
        if (done && !repeating) {
            active = false;
            return;
        }
        
        now = (getTime()-delta)-lastTime;
        
        int delayCheck = delay;
        
        if (firstRepeat)
            delayCheck = getInitialDelay();
        
        if (now >= delayCheck) { //if the timer is done
            percent = 1f;
            //reset last time
            lastTime = getTime();
            
            //if we aren't repeating, we are finished
            if (!repeating) {
                done = true;
                active = false;
            }
            firstRepeat = false;
            isAction = true;
            fireActionPerformed(command);
        } else { //if we aren't done yet
            percent = now / (float)delay;
        }
    }
    
    public long getNow() {
        return now;
    }
    
    /** Restarts this timer. */
    public void restart() {
        lastTime = getTime();
        done = false;
        percent = 1f;
        active = true;
        firstRepeat = true;
    }

    public boolean isRepeats() {
        return repeating;
    }

    public void setRepeats(boolean repeating) {
        boolean old = this.repeating;
        this.repeating = repeating;
        if (this.repeating != old)
            done = false;
    }
    
    /**
     * Starts the Timer. 
     *
     */
    public void start() {
        boolean old = active;
        active = true;
        if (active != old) { //changed
            lastTime = getTime();
            done = false;
            percent = 1f;
            firstRepeat = true;
        }
    }
    
    /*public void setDirection(int direction) {
        if (direction!=ADVANCE && direction!=REVERSE)
            throw new IllegalArgumentException("invalid direction argument");
        this.direction = direction;
    }
    
    public int getDirection() {
        return direction;
    }*/
    
    /** 
     * Whether the timer is active. 
     *
     * @return whether the timer is active
     */
    public boolean isRunning() {
        return active;
    }
    
    /** 
     * Sets the delay of this timer. 
     *
     * @param delay the time in milliseconds
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    /**
     * Gets the delay of this timer.
     *
     * @return the delay of this timer
     */
    public int getDelay() {
        return delay;
    }
    
    /**
     * 
     */
    public void stop() {
        active = false;
        percent = 0f;
    }
        
    /** 
     * Whether the action has occurred.
     *
     * @return <tt>true</tt> if the action has occurred */
    public boolean isAction() {
        return isAction;
    }

    public String getCommand() {
        return command;
    }
    
    private long getTime() {
        return container!=null 
                    ? container.getTime()
                    : (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }
}
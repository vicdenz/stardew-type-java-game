/**
 * The DelayEvent is a class that synchronously times events and ensuring delay between events.
 * @see Main
 * @author David Daniliuc
 * @version "1.8.0_322"
 */

public class DelayEvent {
    // the entire class works off of milliseconds
    private int currentDelay;
    private int maxDelay;
    //------------------------------------------------------------------------------       
    public DelayEvent(int maxDelay) {
        this.currentDelay = 0;
        this.maxDelay = maxDelay;
    }
    //------------------------------------------------------------------------------       
    public int getCurrentDelay() {
        return this.currentDelay;
    }

    public void setCurrentDelay(int currentDelay) {
        this.currentDelay = currentDelay;
    }

    public int getMaxDelay() {
        return this.maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }
    //------------------------------------------------------------------------------       	    
    public boolean isEvent() {
        return (this.currentDelay == 0);
    }
    //------------------------------------------------------------------------------       	    
    public void resetDelay() {
        this.currentDelay = 0;
    }
    //------------------------------------------------------------------------------       	    
    public void updateDelay() {
        this.currentDelay = this.currentDelay + Const.FRAME_DURATION;

        if (this.currentDelay >= this.maxDelay) {
            this.currentDelay = 0;
        }
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public String toString() {
        return "{" +
            " currentDelay='" + getCurrentDelay() + "'" +
            ", maxDelay='" + getMaxDelay() + "'" +
            "}";
    }
}
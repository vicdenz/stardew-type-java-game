import java.awt.Graphics2D;
import java.util.ArrayList;

/**
* The InteractiveObject is a parent class to other classes that interact with the player when keys are pressed.
* @see NPC
* @see Door
* @see Map
* @author David Daniliuc
* @version "1.8.0_322"
*/
public abstract class InteractiveObject {
    private String activationKey;
    //------------------------------------------------------------------------------       
    public InteractiveObject(String activationKey) {
        this.activationKey = activationKey;
    }
    //------------------------------------------------------------------------------       
    public String getActivationKey() {
        return this.activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }
    //------------------------------------------------------------------------------       
    public boolean checkActivation(ArrayList < String > keysHeld) {
        // check if player in talking radius of NPCs.(also updates a 1sec delay on checking interactions)
        if (keysHeld.contains(this.activationKey)) {
            return true;
        }
        return false;
    }
    //------------------------------------------------------------------------------       
    public abstract int[] getCenter();
    public abstract void updateFrame();
    public abstract void draw(Graphics2D g2d, int[] scroll);
    //------------------------------------------------------------------------------       
    @Override
    public String toString() {
        return "{" +
            " activationKey='" + getActivationKey() + "'" +
            "}";
    }
}
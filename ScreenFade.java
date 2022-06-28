import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The ScreenFace is a class that create a fade in and out transition between two scenes.
 * The Rect class inherits from MenuItem.
 * @see Main
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class ScreenFade extends MenuItem {
    public static final int MAX_ALPHA = 255;

    private int width;
    private int height;
    private double alpha;
    private double duration; // seconds
    private double alphaStep;
    private boolean fadingIn;
    //------------------------------------------------------------------------------    
    ScreenFade(int x, int y, double duration) {
        super(x, y, new Color(0, 0, 0, 0));
        this.width = Const.WIDTH;
        this.height = Const.HEIGHT;
        this.alpha = 0;
        this.duration = duration;
        this.alphaStep = MAX_ALPHA / (duration * Const.FPS) * 2;
        this.fadingIn = true;
    }
    //------------------------------------------------------------------------------ 
    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getAlphaStep() {
        return this.alphaStep;
    }

    public void setAlphaStep(double alphaStep) {
        this.alphaStep = alphaStep;
    }

    public boolean isFadingIn() {
        return this.fadingIn;
    }

    public boolean getFadingIn() {
        return this.fadingIn;
    }

    public void setFadingIn(boolean fadingIn) {
        this.fadingIn = fadingIn;
    }
    //------------------------------------------------------------------------------  
    /**
     * Prevent this.alpha from becoming a non valid alpha channel value.
     * @return int
     */
    public int clampAlpha() {
        if (this.alpha > ScreenFade.MAX_ALPHA) {
            return ScreenFade.MAX_ALPHA;
        } else if (this.alpha < 0) {
            return 0;
        }
        return (int) this.alpha;
    }
    //------------------------------------------------------------------------------  
    public void resetFade() {
        this.fadingIn = true;
        this.alpha = 0;
        this.alphaStep = Math.abs(this.alphaStep);
        this.setColor(new Color(0, 0, 0, this.clampAlpha()));
    }
    //------------------------------------------------------------------------------  
    public boolean isDone() {
        return (this.fadingIn == false && this.alpha <= 0);
    }
    //------------------------------------------------------------------------------  
    public boolean updateFade() { // returns if the fade is at the half way point
        if (this.alpha > ScreenFade.MAX_ALPHA) {
            this.fadingIn = false;
            this.alphaStep = -this.alphaStep;
            this.alpha = ScreenFade.MAX_ALPHA;
            return true;
        }

        this.alpha = this.alpha + this.alphaStep;
        this.setColor(new Color(0, 0, 0, this.clampAlpha()));

        return false;
    }
    //------------------------------------------------------------------------------  
    /** 
     * Draws the rect onto a Graphics2D panel.
     * @param g2d Graphics2D object
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.getColor());
        g2d.fillRect(this.getX(), this.getY(), this.width, this.height);
    }

    //------------------------------------------------------------------------------  

    @Override
    public String toString() {
        return super.toString() + "\b," +
            " width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", alpha='" + getAlpha() + "'" +
            ", duration='" + getDuration() + "'" +
            ", alphaStep='" + getAlphaStep() + "'" +
            ", fadingIn='" + isFadingIn() + "'" +
            "}";
    }
}
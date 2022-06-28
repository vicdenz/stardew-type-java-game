import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

/**
 * The Player class is a child class of Sprite.
 * This class has extra functionality to move the sprite and collide/interact with other objects.
 * @see Sprite
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Player extends Sprite {
    private int step;
    private int velX;
    private int velY;

    private boolean canMove;
    private boolean talking;

    private boolean talkedToBetty;
    private boolean talkedToRupert;
    private boolean talkedToChad;
    private boolean talkedToBrad;
    private boolean talkedToGus;
    private boolean talkedToOutsideGus;
    private boolean talkedToSusan;
    private boolean talkedToMaskedGus;
    //------------------------------------------------------------------------------       
    Player(String name, int x, int y, String spriteSheetName, int step) {
        super(name, x, y, spriteSheetName);
        this.step = step;
        this.velX = 0;
        this.velY = 0;

        this.talking = false;
        this.canMove = true;

        this.setHitbox(new Hitbox(x, y + this.getHeight() - Const.TILE_SIZE, this.step));
        this.getHitbox().setColor(new Color(0, 255, 0));
    }
    //------------------------------------------------------------------------------       
    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getVelX() {
        return this.velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return this.velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public boolean isCanMove() {
        return this.canMove;
    }

    public boolean getCanMove() {
        return this.canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isTalking() {
        return this.talking;
    }

    public boolean getTalking() {
        return this.talking;
    }

    public void setTalking(boolean talking) {
        this.talking = talking;
    }

    public boolean isTalkedToBetty() {
        return this.talkedToBetty;
    }

    public boolean getTalkedToBetty() {
        return this.talkedToBetty;
    }

    public void setTalkedToBetty(boolean talkedToBetty) {
        this.talkedToBetty = talkedToBetty;
    }

    public boolean isTalkedToRupert() {
        return this.talkedToRupert;
    }

    public boolean getTalkedToRupert() {
        return this.talkedToRupert;
    }

    public void setTalkedToRupert(boolean talkedToRupert) {
        this.talkedToRupert = talkedToRupert;
    }

    public boolean isTalkedToChad() {
        return this.talkedToChad;
    }

    public boolean getTalkedToChad() {
        return this.talkedToChad;
    }

    public void setTalkedToChad(boolean talkedToChad) {
        this.talkedToChad = talkedToChad;
    }

    public boolean isTalkedToBrad() {
        return this.talkedToBrad;
    }

    public boolean getTalkedToBrad() {
        return this.talkedToBrad;
    }

    public void setTalkedToBrad(boolean talkedToBrad) {
        this.talkedToBrad = talkedToBrad;
    }

    public boolean isTalkedToGus() {
        return this.talkedToGus;
    }

    public boolean getTalkedToGus() {
        return this.talkedToGus;
    }

    public void setTalkedToGus(boolean talkedToGus) {
        this.talkedToGus = talkedToGus;
    }

    public boolean isTalkedToOutsideGus() {
        return this.talkedToOutsideGus;
    }

    public boolean getTalkedToOutsideGus() {
        return this.talkedToOutsideGus;
    }

    public void setTalkedToOutsideGus(boolean talkedToOutsideGus) {
        this.talkedToOutsideGus = talkedToOutsideGus;
    }

    public boolean isTalkedToSusan() {
        return this.talkedToSusan;
    }

    public boolean getTalkedToSusan() {
        return this.talkedToSusan;
    }

    public void setTalkedToSusan(boolean talkedToSusan) {
        this.talkedToSusan = talkedToSusan;
    }

    public boolean isTalkedToMaskedGus() {
        return this.talkedToMaskedGus;
    }

    public boolean getTalkedToMaskedGus() {
        return this.talkedToMaskedGus;
    }

    public void setTalkedToMaskedGus(boolean talkedToMaskedGus) {
        this.talkedToMaskedGus = talkedToMaskedGus;
    }
    //------------------------------------------------------------------------------       	    
    // update the animations
    public void updateFrame() {
        if (this.velX != 0 || this.velY != 0) {
            super.updateFrame(1);
        } else {
            super.updateFrame(0);
        }
    }
    //------------------------------------------------------------------------------       	    
    public void move(ArrayList < String > keysHeld, Hitbox[][] hitboxes) {
        int diagonalStep = (int) Math.sqrt(Math.pow(this.step, 2) / 2); // calculates the step (a, b) if the player moves diagonally (c)

        // reset velocity
        this.velX = 0;
        this.velY = 0;

        // get moved in directions
        boolean[] directions = new boolean[4]; // [right, up, left, down]
        if (keysHeld.contains("w")) {
            directions[1] = true;
        }
        if (keysHeld.contains("a")) {
            directions[2] = true;
        }
        if (keysHeld.contains("s")) {
            directions[3] = true;
        }
        if (keysHeld.contains("d")) {
            directions[0] = true;
        }

        // check diagonal movement first
        boolean isDiagonal = false;
        if (directions[1] && directions[2]) { // up left
            this.velX = this.velX - diagonalStep;
            this.velY = this.velY - diagonalStep;
            this.setDirection("NW");
            isDiagonal = true;
        }
        if (directions[1] && directions[0]) { // up right
            this.velX = this.velX + diagonalStep;
            this.velY = this.velY - diagonalStep;
            this.setDirection("NE");
            isDiagonal = true;
        }
        if (directions[3] && directions[2]) { // down left
            this.velX = this.velX - diagonalStep;
            this.velY = this.velY + diagonalStep;
            this.setDirection("SW");
            isDiagonal = true;
        }
        if (directions[3] && directions[0]) { // down right
            this.velX = this.velX + diagonalStep;
            this.velY = this.velY + diagonalStep;
            this.setDirection("SE");
            isDiagonal = true;
        }

        // then check horizontal/vertical movement after
        if (isDiagonal == false) {
            if (directions[1]) { // up
                this.velY = this.velY - this.step;
                this.setDirection("N");
            }
            if (directions[2]) { // left
                this.velX = this.velX - this.step;
                this.setDirection("W");
            }
            if (directions[3]) { // down
                this.velY = this.velY + this.step;
                this.setDirection("S");
            }
            if (directions[0]) { // right
                this.velX = this.velX + this.step;
                this.setDirection("E");
            }
        }

        // move the player
        moveSingleAxis(this.velX, 0, hitboxes);
        moveSingleAxis(0, this.velY, hitboxes);
    }
    //------------------------------------------------------------------------------       	    
    public void moveSingleAxis(int velX, int velY, Hitbox[][] hitboxes) {
        this.setX(this.getX() + velX);
        this.setY(this.getY() + velY);
        this.syncHitboxToCoord();

        for (Hitbox[] hitboxRow: hitboxes) {
            for (Hitbox hitbox: hitboxRow) {
                if (hitbox != null && hitbox.collide(this.getHitbox())) {
                    if (velX > 0) { // Moving right; Hit the left side of the wall
                        this.getHitbox().setRight(hitbox.getLeft());
                    }
                    if (velX < 0) { // Moving left; Hit the right side of the wall
                        this.getHitbox().setLeft(hitbox.getRight());
                    }
                    if (velY > 0) { // Moving down; Hit the top side of the wall
                        this.getHitbox().setBottom(hitbox.getTop());
                    }
                    if (velY < 0) { // Moving down; Hit the top side of the wall
                        this.getHitbox().setTop(hitbox.getBottom());
                    }

                    this.syncCoordToHitbox();
                }
            }
        }
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public void draw(Graphics2D g2d, int[] scroll) {
        g2d.drawImage(this.getCurrentSpriteImage(), this.getX() + scroll[0] - this.getHitbox().getMargin(), this.getY() + scroll[1] - this.getHitbox().getMargin(), null);
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " step='" + getStep() + "'" +
            ", velX='" + getVelX() + "'" +
            ", velY='" + getVelY() + "'" +
            ", canMove='" + isCanMove() + "'" +
            ", talking='" + isTalking() + "'" +
            ", talkedToBetty='" + isTalkedToBetty() + "'" +
            ", talkedToRupert='" + isTalkedToRupert() + "'" +
            ", talkedToChad='" + isTalkedToChad() + "'" +
            ", talkedToBrad='" + isTalkedToBrad() + "'" +
            ", talkedToGus='" + isTalkedToGus() + "'" +
            ", talkedToOutsideGus='" + isTalkedToOutsideGus() + "'" +
            ", talkedToSusan='" + isTalkedToSusan() + "'" +
            ", talkedToMaskedGus='" + isTalkedToMaskedGus() + "'" +
            "}";
    }

}
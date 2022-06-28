import java.awt.Graphics2D;

/**
 * The NPC class is a child of the InteractiveObject class.
 * It is another sprite that the player can interact with.
 * @see Map
 * @see Sprite
 * @see Hitbox
 * @see InteractiveObject
 * @see Dialogue
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class NPC extends InteractiveObject {
    private Sprite sprite;

    private String defaultDirection;

    private boolean talking;
    private Dialogue dialogue;
    private String requiredDialogueEvent;
    //------------------------------------------------------------------------------       
    public NPC(String activationKey, Sprite sprite, String defaultDirection, Dialogue dialogue, String requiredDialogueEvent) {
        super(activationKey);

        this.defaultDirection = defaultDirection;

        this.sprite = sprite;
        this.talking = false;
        this.dialogue = dialogue;
        this.requiredDialogueEvent = requiredDialogueEvent;

        this.sprite.setDirection(this.defaultDirection);
    }
    //------------------------------------------------------------------------------       
    public Sprite getSprite() {
        return this.sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public String getRequiredDialogueEvent() {
        return this.requiredDialogueEvent;
    }

    public void setRequiredDialogueEvent(String requiredDialogueEvent) {
        this.requiredDialogueEvent = requiredDialogueEvent;
    }
    //------------------------------------------------------------------------------
    public int[] getCenter() {
        return this.sprite.getHitbox().getCenter();
    }
    //------------------------------------------------------------------------------
    public boolean isPlayerAbove(Player player) {
        return (this.sprite.getHitbox().getBottom() > player.getHitbox().getBottom());
    }
    //------------------------------------------------------------------------------       
    /**
     * Updates the player's talkedTo variables depending on who they are talking to.
     * @param player Player object
     */
    public void startTalking(Player player) {
        this.talking = true;

        if (this.requiredDialogueEvent.equalsIgnoreCase("Betty")) {
            this.dialogue.setDialogueExtended(player.getTalkedToBetty());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("Rupert")) {
            this.dialogue.setDialogueExtended(player.getTalkedToRupert());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("Chad")) {
            this.dialogue.setDialogueExtended(player.getTalkedToChad());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("Brad")) {
            this.dialogue.setDialogueExtended(player.getTalkedToBrad());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("Gus")) {
            this.dialogue.setDialogueExtended(player.getTalkedToGus());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("OutsideGus")) {
            this.dialogue.setDialogueExtended(player.getTalkedToOutsideGus());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("Susan")) {
            this.dialogue.setDialogueExtended(player.getTalkedToSusan());
        } else if (this.requiredDialogueEvent.equalsIgnoreCase("MaskedGus")) {
            this.dialogue.setDialogueExtended(player.getTalkedToMaskedGus());
        }

        this.updateDirection(player);

        this.dialogue.startDialogue();
    }
    //------------------------------------------------------------------------------       
    public void updateFrame() {
        this.sprite.updateFrame(this.sprite.getState());
    }
    //------------------------------------------------------------------------------       
    /**
     * Check where the player is closest then point the NPC's sprite in that direction.
     * @param player Player object
     */
    public void updateDirection(Player player) {
        int closestDirection = Const.TILE_SIZE * 2;

        int directionDistance = this.getSprite().getHitbox().getRight() - player.getHitbox().getLeft();
        if (-1 < directionDistance && directionDistance < closestDirection) {
            closestDirection = directionDistance;
            this.getSprite().setDirection("E");
        }

        directionDistance = this.getSprite().getHitbox().getLeft() - player.getHitbox().getRight();
        if (-1 < directionDistance && directionDistance < closestDirection) {
            closestDirection = directionDistance;
            this.getSprite().setDirection("W");
        }

        directionDistance = this.getSprite().getHitbox().getTop() - player.getHitbox().getBottom();
        if (-1 < directionDistance && directionDistance < closestDirection) {
            closestDirection = directionDistance;
            this.getSprite().setDirection("N");
        }

        directionDistance = this.getSprite().getHitbox().getBottom() - player.getHitbox().getTop();
        if (-1 < directionDistance && directionDistance < closestDirection) {
            closestDirection = directionDistance;
            this.getSprite().setDirection("S");
        }
    }
    //------------------------------------------------------------------------------       
    public void resetDirection() {
        this.getSprite().setDirection(this.defaultDirection);
    }
    //------------------------------------------------------------------------------       
    public void draw(Graphics2D g2d, int[] scroll) {
        this.sprite.draw(g2d, scroll);
    }
    //------------------------------------------------------------------------------       
    public void drawDialogue(Graphics2D g2d) {
        this.dialogue.draw(g2d);
    }
    //------------------------------------------------------------------------------       
    @Override
    public String toString() {
        return super.toString() + "\b," +
            " sprite='" + getSprite() + "'" +
            ", talking='" + isTalking() + "'" +
            ", dialogue='" + getDialogue() + "'" +
            ", requiredDialogueEvent='" + getRequiredDialogueEvent() + "'" +
            "}";
    }
}
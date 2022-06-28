import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

import java.util.Scanner;
import java.io.File;

// possible exceptions
import java.io.IOException;

/**
 * The Map class is a entire map of the game.
 * It contains hitboxes for player collision, drawing methods to display the player correctly 
 * and interaction methods to the player to interact with InteractiveObject.
 * @see Main
 * @see Hitbox
 * @see InteractiveObject
 * @see Door
 * @see NPC
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Map {
    private int rows;
    private int columns;
    private TileLayer[] layers;
    private Color bgColor;
    private int playerLayer;
    private boolean movementRelative;

    private Hitbox[][] hitboxes;

    private boolean hasInteraction;
    private int numOfInteractiveObjects;
    private InteractiveObject[] interactiveObjects;
    private int interactionRadius;

    private NPC playerTalkingTo;
    //------------------------------------------------------------------------------       
    Map(TileLayer[] layers, int playerLayer, String InteractiveLayerName, boolean movementRelative, Color bgColor) {
        this.playerLayer = playerLayer;
        this.layers = layers;
        this.movementRelative = movementRelative;
        this.bgColor = bgColor;
        this.interactionRadius = (int)(Const.TILE_SIZE * 1.5);

        for (TileLayer layer: this.layers) {
            if (layer != null) {
                this.rows = layer.getRows();
                this.columns = layer.getColumns();
            }
        }

        // read through every layer and create an 2d array of hitboxes
        this.hitboxes = new Hitbox[this.rows][this.columns];
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                for (TileLayer layer: this.layers) {
                    if (layer.getCollision()) {
                        Tile tile = layer.getTile(row, column);
                        if (tile != null && tile.getCollision()) {
                            this.hitboxes[row][column] = new Hitbox(column * Const.TILE_SIZE, row * Const.TILE_SIZE);
                        }
                    }
                }
            }
        }

        if (InteractiveLayerName != null) {
            // read and load all the NPCs on this map
            try {
                Scanner input = new Scanner(new File(InteractiveLayerName));
                while (input.hasNext()) {
                    input.nextLine();
                    this.numOfInteractiveObjects++;
                }
                input.close();
            } catch (IOException ex) {
                System.out.println("Unable to read Interactive file. " + InteractiveLayerName);
            }

            this.interactiveObjects = new InteractiveObject[this.numOfInteractiveObjects];
            try {
                Scanner input = new Scanner(new File(InteractiveLayerName));

                for (int objectIndex = 0; objectIndex < this.numOfInteractiveObjects; objectIndex++) {
                    String[] objectLine = input.nextLine().split(" ");
                    String objectType = objectLine[0];
                    String[] objectCoords = objectLine[1].split(",");
                    int col = Integer.parseInt(objectCoords[0]);
                    int row = Integer.parseInt(objectCoords[1]);

                    if (objectType.equalsIgnoreCase("Door")) {
                        String doorImageName;
                        if (objectLine[2].equalsIgnoreCase("-1")) {
                            doorImageName = null;
                        } else {
                            doorImageName = "images/door/door" + Integer.parseInt(objectLine[2]) + ".png";
                        }

                        int mapEntered = Integer.parseInt(objectLine[3]);
                        String[] roomCoords = objectLine[4].split(",");
                        int roomX = Integer.parseInt(roomCoords[0]) * Const.TILE_SIZE;
                        int roomY = Integer.parseInt(roomCoords[1]) * Const.TILE_SIZE;
                        String entryRequirement = "";
                        boolean extendedRequirement = false;

                        if (objectLine.length >= 6) {
                            entryRequirement = objectLine[5];
                        }

                        if (objectLine.length >= 7) {
                            if (objectLine[6].equalsIgnoreCase("E")) {
                                extendedRequirement = true;
                            } else {
                                extendedRequirement = false;
                            }
                        }

                        this.interactiveObjects[objectIndex] = new Door(
                            doorImageName,
                            "e",
                            col * Const.TILE_SIZE,
                            row * Const.TILE_SIZE, //door coords starts from bottom left corner
                            roomX,
                            roomY,
                            mapEntered,
                            entryRequirement,
                            extendedRequirement
                        );
                        this.hasInteraction = true;
                    } else {
                        String spriteName = "images/" + objectType.toLowerCase() + ".png";
                        if (objectType.equalsIgnoreCase("OutsideGus")) {
                            spriteName = "images/gus.png";
                        }

                        String defaultDirection = objectLine[2];

                        String requiredDialogueEvent = "";
                        if (objectLine.length == 4) {
                            requiredDialogueEvent = objectLine[3];
                        }

                        this.interactiveObjects[objectIndex] = new NPC(
                            "e",
                            new Sprite(objectType, col * Const.TILE_SIZE, row * Const.TILE_SIZE - Const.SPRITE_HEIGHT + Const.TILE_SIZE, spriteName),
                            defaultDirection,
                            null,
                            requiredDialogueEvent
                        );
                        this.hasInteraction = true;
                    }

                    if (this.hasInteraction) {
                        this.hitboxes[row][col] = new Hitbox(col * Const.TILE_SIZE, row * Const.TILE_SIZE);
                    }
                }

                input.close();
            } catch (IOException ex) {
                System.out.println("Unable to parse NPC file. " + InteractiveLayerName);
            }
        } else {
            this.hasInteraction = false;
        }
    }
    //------------------------------------------------------------------------------       
    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public TileLayer[] getLayers() {
        return this.layers;
    }

    public void setLayers(TileLayer[] layers) {
        this.layers = layers;
    }

    public Color getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public int getPlayerLayer() {
        return this.playerLayer;
    }

    public void setPlayerLayer(int playerLayer) {
        this.playerLayer = playerLayer;
    }

    public boolean isMovementRelative() {
        return this.movementRelative;
    }

    public boolean getMovementRelative() {
        return this.movementRelative;
    }

    public void setMovementRelative(boolean movementRelative) {
        this.movementRelative = movementRelative;
    }

    public Hitbox[][] getHitboxes() {
        return this.hitboxes;
    }

    public void setHitboxes(Hitbox[][] hitboxes) {
        this.hitboxes = hitboxes;
    }

    public boolean isHasInteraction() {
        return this.hasInteraction;
    }

    public boolean getHasInteraction() {
        return this.hasInteraction;
    }

    public void setHasInteraction(boolean hasInteraction) {
        this.hasInteraction = hasInteraction;
    }

    public int getNumOfInteractiveObjects() {
        return this.numOfInteractiveObjects;
    }

    public void setNumOfInteractiveObjects(int numOfInteractiveObjects) {
        this.numOfInteractiveObjects = numOfInteractiveObjects;
    }

    public InteractiveObject[] getInteractiveObjects() {
        return this.interactiveObjects;
    }

    public void setInteractiveObjects(InteractiveObject[] interactiveObjects) {
        this.interactiveObjects = interactiveObjects;
    }

    public int getInteractionRadius() {
        return this.interactionRadius;
    }

    public void setInteractionRadius(int interactionRadius) {
        this.interactionRadius = interactionRadius;
    }

    public NPC getPlayerTalkingTo() {
        return this.playerTalkingTo;
    }

    public void setPlayerTalkingTo(NPC playerTalkingTo) {
        this.playerTalkingTo = playerTalkingTo;
    }
    //------------------------------------------------------------------------------        
    public void updateDialogue(Dialogue[] dialogues) {
        int dialogueIndex = 0;

        for (int objectIndex = 0; objectIndex < this.interactiveObjects.length; objectIndex++) {
            InteractiveObject interactiveObject = this.interactiveObjects[objectIndex];

            if (interactiveObject instanceof NPC) {
                ((NPC) interactiveObject).setDialogue(dialogues[dialogueIndex]);
                dialogueIndex++;
            }
        }
    }
    //------------------------------------------------------------------------------        
    public void updateFrames() {
        if (this.hasInteraction) {
            for (int objectIndex = 0; objectIndex < this.interactiveObjects.length; objectIndex++) {
                InteractiveObject interactiveObject = this.interactiveObjects[objectIndex];

                if (interactiveObject != null) {
                    interactiveObject.updateFrame();
                }
            }
        }
    }
    public NPC getNPCByName(String name) {
        for (int objectIndex = 0; objectIndex < this.interactiveObjects.length; objectIndex++) {
            InteractiveObject interactiveObject = this.interactiveObjects[objectIndex];

            if (interactiveObject instanceof NPC) {
                if (((NPC) interactiveObject).getSprite().getName().equalsIgnoreCase(name)) {
                    return ((NPC) interactiveObject);
                }
            }
        }
        return null;
    }
    //------------------------------------------------------------------------------        
    public int[] checkInteraction(ArrayList < String > keysHeld, Player player) {
        if (this.hasInteraction) {
            int closestInteractiveObjectDistance = this.interactionRadius;
            int closestInteractiveObjectIndex = -1;

            for (int objectIndex = 0; objectIndex < this.interactiveObjects.length; objectIndex++) {
                InteractiveObject interactiveObject = this.interactiveObjects[objectIndex];
                int[] playerCenter = player.getHitbox().getCenter();
                int[] objectCenter = interactiveObject.getCenter();
                int playerDistance = (int) Math.sqrt(Math.pow(objectCenter[0] - playerCenter[0], 2) + Math.pow(objectCenter[1] - playerCenter[1], 2));

                if (playerDistance <= this.interactionRadius && playerDistance < closestInteractiveObjectDistance) {
                    closestInteractiveObjectIndex = objectIndex;
                }
            }

            if (closestInteractiveObjectIndex != -1) {
                InteractiveObject closestInteractiveObject = this.interactiveObjects[closestInteractiveObjectIndex];

                if (this.interactiveObjects[closestInteractiveObjectIndex].checkActivation(keysHeld)) {
                    if (closestInteractiveObject instanceof Door) {
                        Door doorObject = (Door) closestInteractiveObject;

                        String NPCName = doorObject.getEntryRequirement();
                        if (NPCName.equalsIgnoreCase("OutsideGus") || NPCName.equalsIgnoreCase("MaskedGus")) {
                            NPCName = "Gus";
                        }

                        if (doorObject.enterRoom(player)) {
                            if (doorObject.getExtendedRequirement()) {
                                NPC talkedToNPC = this.getNPCByName(doorObject.getEntryRequirement());

                                if (talkedToNPC.getDialogue().getDialogueExtended()) {
                                    int[] newMapPostions = {
                                        doorObject.getMapEntered(),
                                        doorObject.getRoomX(),
                                        doorObject.getRoomY()
                                    };
                                    return newMapPostions;
                                } else {
                                    Main.warnPlayer("You must talk to " + NPCName + " further.");
                                }
                            } else {
                                int[] newMapPostions = {
                                    doorObject.getMapEntered(),
                                    doorObject.getRoomX(),
                                    doorObject.getRoomY()
                                };
                                return newMapPostions;
                            }
                        } else {
                            Main.warnPlayer("You still need to talk to " + NPCName + ".");
                        }
                    } else if (closestInteractiveObject instanceof NPC) {
                        NPC NPCObject = (NPC) closestInteractiveObject;

                        this.playerTalkingTo = NPCObject;

                        NPCObject.startTalking(player);

                        player.setTalking(true);
                        player.setCanMove(false);
                    }
                }
            }
        }
        return null;
    }
    //------------------------------------------------------------------------------        
    public int findNPCAboveIndex(Player player) {
        for (int interactiveObjectIndex = 0; interactiveObjectIndex < this.interactiveObjects.length; interactiveObjectIndex++) {
            InteractiveObject interactiveObject = this.interactiveObjects[interactiveObjectIndex];

            if (interactiveObject instanceof NPC) {
                NPC NPC = (NPC) interactiveObject;

                if (NPC.isPlayerAbove(player)) {
                    return interactiveObjectIndex;
                }
            }
        }
        return this.interactiveObjects.length;
    }
    //------------------------------------------------------------------------------        
    public void draw(Graphics2D g2d, Player player, int[] scroll) {
        if (this.bgColor != null) {
            g2d.setColor(this.bgColor);
        } else {
            g2d.setColor(Const.WHITE_COLOR);
        }
        g2d.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);

        for (int layerIndex = 0; layerIndex < this.playerLayer + 1; layerIndex++) {
            this.drawLayer(g2d, layerIndex, scroll);
        }

        if (this.hasInteraction) {
            int NPCAboveIndex = findNPCAboveIndex(player);

            for (int interactiveObjectIndex = 0; interactiveObjectIndex < NPCAboveIndex; interactiveObjectIndex++) {
                this.interactiveObjects[interactiveObjectIndex].draw(g2d, scroll);
            }

            player.draw(g2d, scroll);

            for (int interactiveObjectIndex = NPCAboveIndex; interactiveObjectIndex < this.interactiveObjects.length; interactiveObjectIndex++) {
                this.interactiveObjects[interactiveObjectIndex].draw(g2d, scroll);
            }
        } else if (this.hasInteraction == false) {
            player.draw(g2d, scroll);
        }

        for (int layerIndex = this.playerLayer + 1; layerIndex < this.layers.length; layerIndex++) {
            this.drawLayer(g2d, layerIndex, scroll);
        }
    }
    //------------------------------------------------------------------------------        
    public void drawLayer(Graphics2D g2d, int index) {
        if (this.layers[index] != null) {
            this.layers[index].draw(g2d);
        }
    }

    public void drawLayer(Graphics2D g2d, int index, int[] scroll) {
        if (this.layers[index] != null) {
            this.layers[index].draw(g2d, scroll);
        }
    }
    //------------------------------------------------------------------------------        
    public void drawHixboxes(Graphics2D g2d) {
        for (int row = 0; row < this.hitboxes.length; row++) {
            for (int column = 0; column < this.hitboxes[row].length; column++) {
                if (this.hitboxes[row][column] != null) {
                    this.hitboxes[row][column].draw(g2d);
                }
            }
        }
    }

    //------------------------------------------------------------------------------        
    @Override
    public String toString() {
        return "{" +
            " rows='" + getRows() + "'" +
            ", columns='" + getColumns() + "'" +
            ", layers='" + getLayers() + "'" +
            ", bgColor='" + getBgColor() + "'" +
            ", playerLayer='" + getPlayerLayer() + "'" +
            ", movementRelative='" + isMovementRelative() + "'" +
            ", hitboxes='" + getHitboxes() + "'" +
            ", hasInteraction='" + isHasInteraction() + "'" +
            ", numOfInteractiveObjects='" + getNumOfInteractiveObjects() + "'" +
            ", interactiveObjects='" + getInteractiveObjects() + "'" +
            ", interactionRadius='" + getInteractionRadius() + "'" +
            ", playerTalkingTo='" + getPlayerTalkingTo() + "'" +
            "}";
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.AffineTransform;

import java.util.ArrayList;

/**
* The InteractiveObject is a parent class to other classes that interact with the player when keys are pressed.
* @see NPC
* @see Door
* @see Map
* @author David Daniliuc
* @version "1.8.0_322"
*/
public class Main {
    private JFrame gameWindow;
    private GamePanel gamePanel;
    private KeyboardListener keyboardListener;
    private MouseButtonListener mouseListener;
    private MenuMotionListener menuMotionListener;

    private ArrayList < String > keysHeld = new ArrayList < String > ();

    //map tilesets
    private TileSet houseTileset = new TileSet("images/tilesets/house_tileset.png");
    private TileSet villageTileset = new TileSet("images/tilesets/village_tileset.png");
    private TileSet interiorTileset = new TileSet("images/tilesets/interior_tileset.png");

    // village layers + map
    private TileLayer[] villageLayers = {
        new TileLayer(villageTileset, "maps/village/floor.txt", false),
        new TileLayer(villageTileset, "maps/village/border.txt", true),
        new TileLayer(villageTileset, "maps/village/earth.txt", true),
        new TileLayer(villageTileset, "maps/village/trees.txt", true),
        new TileLayer(villageTileset, "maps/village/floor_decor.txt", false),
        new TileLayer(villageTileset, "maps/village/fences.txt", true),
        new TileLayer(villageTileset, "maps/village/houses.txt", true),
        new TileLayer(villageTileset, "maps/village/house_decor.txt", false),
        new TileLayer(villageTileset, "maps/village/terrain.txt", true),
        new TileLayer(villageTileset, "maps/village/above_player_decor.txt", false),
        new TileLayer(villageTileset, "maps/village/above_trees_decor.txt", false),
        new TileLayer(villageTileset, "maps/village/above_fences_decor.txt", false),
        new TileLayer(villageTileset, "maps/village/above_house.txt", false),
        new TileLayer(villageTileset, "maps/village/above_house_decor.txt", false),
    };
    private Map village = new Map(villageLayers, 8, "maps/village/objects.txt", true, Const.OCEAN_COLOR);

    // susan_house layers + map
    private TileLayer[] bettyHouseLayers = {
        new TileLayer(houseTileset, "maps/susan_house/floor.txt", false),
        new TileLayer(houseTileset, "maps/susan_house/walls.txt", true),
        new TileLayer(interiorTileset, "maps/susan_house/below_physical_decor.txt", false),
        new TileLayer(interiorTileset, "maps/susan_house/physical_decor.txt", true),
        new TileLayer(interiorTileset, "maps/susan_house/below_player_decor.txt", false),
        new TileLayer(interiorTileset, "maps/susan_house/above_player_decor.txt", false)
    };
    private Map bettyHouse = new Map(bettyHouseLayers, 4, "maps/susan_house/objects.txt", true, Const.ROOM_COLOR);

    // bar room 1 layers + map
    private TileLayer[] barRoom1Layers = {
        new TileLayer(interiorTileset, "maps/bar_room1/floor.txt", false),
        new TileLayer(houseTileset, "maps/bar_room1/walls.txt", true),
        new TileLayer(interiorTileset, "maps/bar_room1/physical_decor.txt", true),
        new TileLayer(interiorTileset, "maps/bar_room1/below_player_decor.txt", false),
        new TileLayer(interiorTileset, "maps/bar_room1/above_player_decor.txt", false)
    };
    private Map barRoom1 = new Map(barRoom1Layers, 3, "maps/bar_room1/objects.txt", true, Const.BLACK_COLOR);

    // bar room 1_2 layers + map
    private TileLayer[] barRoom1_2Layers = {
        new TileLayer(interiorTileset, "maps/bar_room1_2/floor.txt", false),
        new TileLayer(houseTileset, "maps/bar_room1_2/walls.txt", true),
        new TileLayer(interiorTileset, "maps/bar_room1_2/physical_decor.txt", true),
        new TileLayer(interiorTileset, "maps/bar_room1_2/below_player_decor.txt", false),
        new TileLayer(interiorTileset, "maps/bar_room1_2/above_player_decor.txt", false)
    };
    private Map barRoom1_2 = new Map(barRoom1_2Layers, 3, "maps/bar_room1_2/objects.txt", true, Const.BLACK_COLOR);

    // cave layers + map
    private TileLayer[] caveLayers = {
        new TileLayer(interiorTileset, "maps/cave/floor.txt", false),
        new TileLayer(interiorTileset, "maps/cave/walls.txt", true),
        new TileLayer(interiorTileset, "maps/cave/floor_decor.txt", false),
        new TileLayer(interiorTileset, "maps/cave/physical_decor.txt", true)
    };
    private Map cave = new Map(caveLayers, 3, "maps/cave/objects.txt", true, Const.CAVE_COLOR);

    private Map[] maps = {
        village,
        bettyHouse,
        barRoom1,
        barRoom1_2,
        cave
    };
    private int currentMap = -1;
    private boolean mapChanged = false;

    private int[] scrollOrigin = {
        Const.WIDTH / 2 + Const.TILE_SIZE,
        Const.HEIGHT / 2
    };
    private int[] scroll = {
        0,
        0
    };

    private Player player = new Player("Detective Henry Foreman", 3 * Const.TILE_SIZE, 13 * Const.TILE_SIZE, "images/detective.png", Const.INSIDE_STEP);

    private DelayEvent interactionDelayEvent = new DelayEvent(1000);
    private DelayEvent talkingDelayEvent = new DelayEvent(500);

    private String[] bettyCustomWords = {
        player.getName()
    };
    private Dialogue bettyDialogue = new Dialogue("betty", Const.FEMALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, bettyCustomWords, 15);

    private Dialogue rupertDialogue = new Dialogue("rupert", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 15);

    private Dialogue chadDialogue = new Dialogue("chad", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 15);

    private Dialogue bradDialogue = new Dialogue("brad", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 15);

    private Dialogue gusDialogue = new Dialogue("gus", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 15);

    private Dialogue gus2Dialogue = new Dialogue("gus2", "gus", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 15);

    private String[] susanCustomWords = {
        player.getName()
    };
    private Dialogue susanDialogue = new Dialogue("susan", Const.FEMALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, susanCustomWords, 20);

    private Dialogue maskedGusDialogue = new Dialogue("maskedgus", Const.MALE_TALKING_SOUNDS, "detective", Const.MALE_TALKING_SOUNDS, null, 20);

    private Dialogue[] villageDialogues = {
        gus2Dialogue
    };

    private Dialogue[] barRoom1Dialogues = {
        rupertDialogue,
        gusDialogue,
        bradDialogue,
        chadDialogue
    };

    private Dialogue[] barRoom1_2Dialogues = {
        bettyDialogue
    };

    private Dialogue[] susanHouseDialogues = {
        susanDialogue
    };

    private Dialogue[] caveDialogues = {
        maskedGusDialogue
    };

    private Dialogue[][] dialogues = {
        villageDialogues,
        susanHouseDialogues,
        barRoom1Dialogues,
        barRoom1_2Dialogues,
        caveDialogues
    };

    // player warning variables
    private static MenuItem[] warningMenuItems = {
        new Button(new Rect(0, 0, Const.LIGHT_BROWN_COLOR, 100, 50, 20, Const.BROWN_COLOR), new Text(0, 0, "", Const.MEDIUM_FONT, Const.BLACK_COLOR), MouseEvent.MOUSE_CLICKED, 0, Const.WHITE_COLOR, true, true, 20)
    };
    private static Menu warningMenu = new Menu(0, 0, Const.WIDTH, Const.HEIGHT, Const.CLEAR_COLOR, warningMenuItems);
    private static boolean warningPlayer = false;
    private static DelayEvent warningDelayEvent = new DelayEvent(1000);

    private double screenZoom = Const.INSIDE_ZOOM;

    private ScreenFade screenFade = new ScreenFade(0, 0, 3);
    private int newMap = 2;
    private int[] newPlayerCoords = {
        player.getX(),
        player.getY()
    };

    private boolean endGameReady = false;
    private String killerName = "MaskedGus";

    // start menu items
    private MenuItem[] startMenuItems = {
        new Text(0, 0, "Just A Little", Const.TITLE_FONT, Const.WHITE_COLOR),
        new Text(0, 0, "Murder", Const.TITLE_FONT, Const.RED_COLOR),
        new Button(new Rect(0, 0, Const.LIGHT_BROWN_COLOR, 80, 30, 15, Const.BROWN_COLOR), new Text(0, 0, "Start Game", Const.VERY_LARGE_FONT, Const.BLACK_COLOR), MouseEvent.MOUSE_CLICKED, -1, Const.WHITE_COLOR, true, true, 15),
        new Button(new Rect(0, 0, Const.LIGHT_BROWN_COLOR, 80, 30, 15, Const.BROWN_COLOR), new Text(0, 0, "Credits", Const.VERY_LARGE_FONT, Const.BLACK_COLOR), MouseEvent.MOUSE_CLICKED, 1, Const.WHITE_COLOR, true, true, 15)
    };
    private Menu startMenu = new Menu(0, 0, Const.WIDTH, Const.HEIGHT, Const.BLACK_COLOR, startMenuItems);

    // credits menu items
    private MenuItem[] creditsMenuItems = {
        new Text(0, 0, "Credits:", Const.VERY_LARGE_FONT, Const.RED_COLOR),
        new Text(0, 0, "Creator, Lead Programmer, Map", Const.LARGE_FONT, Const.WHITE_COLOR),
        new Text(0, 0, "Designer, and Scriptwriter:", Const.LARGE_FONT, Const.WHITE_COLOR),
        new Text(0, 0, "David Daniliuc", Const.LARGE_FONT, Const.RED_COLOR),
        new Text(0, 0, "Asset Artist:", Const.LARGE_FONT, Const.WHITE_COLOR),
        new Text(0, 0, "LimeZu on itch.io", Const.LARGE_FONT, Const.RED_COLOR),
        new Button(new Rect(0, 0, Const.LIGHT_BROWN_COLOR, 100, 50, 15, Const.BROWN_COLOR), new Text(0, 0, "Go Back", Const.LARGE_FONT, Const.BLACK_COLOR), MouseEvent.MOUSE_CLICKED, 0, Const.WHITE_COLOR, true, true, 15)
    };
    private Menu creditsMenu = new Menu(0, 0, Const.WIDTH, Const.HEIGHT, Const.BLACK_COLOR, creditsMenuItems);

    // end game menu items
    private MenuItem[] endMenuItems = {
        new Text(0, 0, "Congratulations!", Const.TITLE_FONT, Const.WHITE_COLOR),
        new Text(0, 0, "You caught the killer!", Const.VERY_LARGE_FONT, Const.RED_COLOR),
        new Text(0, 0, "Now the town is safer than ever.", Const.VERY_LARGE_FONT, Const.RED_COLOR),
        new Button(new Rect(0, 0, Const.LIGHT_BROWN_COLOR, 100, 50, 20, Const.BROWN_COLOR), new Text(0, 0, "Close", Const.VERY_LARGE_FONT, Const.WHITE_COLOR), MouseEvent.MOUSE_CLICKED, -1, Const.WHITE_COLOR, true, true, 20)
    };
    private Menu endMenu = new Menu(0, 0, Const.WIDTH, Const.HEIGHT, Const.BLACK_COLOR, endMenuItems);

    private Menu[] menus = {
        startMenu,
        creditsMenu,
        endMenu
    };

    private int currentMenu = 0;
    private boolean gameRunning = false;
    private boolean gameOver = false;
    //------------------------------------------------------------------------------
    Main() {
        gameWindow = new JFrame("Game Window");
        gameWindow.setSize(Const.WIDTH, Const.HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        gamePanel = new GamePanel();
        gameWindow.add(gamePanel);

        keyboardListener = new KeyboardListener();
        gamePanel.addKeyListener(keyboardListener);

        mouseListener = new MouseButtonListener();
        gamePanel.addMouseListener(mouseListener);

        menuMotionListener = new MenuMotionListener();
        gamePanel.addMouseMotionListener(menuMotionListener);

        gameWindow.setVisible(true);

        // updating dialogues to match NPCs.
        for (int mapIndex = 0; mapIndex < maps.length; mapIndex++) {
            maps[mapIndex].updateDialogue(dialogues[mapIndex]);
        }

        // Positioning menus
        ((Text) menus[0].getMenuItem(0)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4);
        ((Text) menus[0].getMenuItem(1)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 120);
        ((Button) menus[0].getMenuItem(2)).centerButton(Const.WIDTH / 4, Const.HEIGHT / 3 * 2 + 40);
        ((Button) menus[0].getMenuItem(3)).centerButton(Const.WIDTH / 4 * 3, Const.HEIGHT / 3 * 2 + 40);

        ((Text) menus[1].getMenuItem(0)).centerText(Const.WIDTH / 2, 50);
        ((Text) menus[1].getMenuItem(1)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4);
        ((Text) menus[1].getMenuItem(2)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 60);
        ((Text) menus[1].getMenuItem(3)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 120);
        ((Text) menus[1].getMenuItem(4)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 220);
        ((Text) menus[1].getMenuItem(5)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 280);
        ((Button) menus[1].getMenuItem(6)).centerButton(Const.WIDTH - 100, Const.HEIGHT - 100);

        ((Text) menus[2].getMenuItem(0)).centerText(Const.WIDTH / 2, 100);
        ((Text) menus[2].getMenuItem(1)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 40);
        ((Text) menus[2].getMenuItem(2)).centerText(Const.WIDTH / 2, Const.HEIGHT / 4 + 120);
        ((Button) menus[2].getMenuItem(3)).centerButton(Const.WIDTH / 2, Const.HEIGHT - 200);

        // ConvertTileLayer.convertTileLayer("maps/", );
    }
    //------------------------------------------------------------------------------  
    public void run() {
        while (true) {
            gameWindow.repaint();
            try {
                Thread.sleep(Const.FRAME_DURATION);
            } catch (Exception e) {}
            //------------------------------------------------------------------
            // implement the gameplay here

            if (warningPlayer) {
                warningDelayEvent.updateDelay();

                if (warningDelayEvent.isEvent()) {
                    warningPlayer = false;
                }
            }

            if (mapChanged) {
                scroll[0] = 0;
                scroll[1] = 0;

                if (currentMap == 0) {
                    player.setStep(Const.OUTSIDE_STEP);
                    screenZoom = Const.OUTSIDE_ZOOM;
                } else {
                    player.setStep(Const.INSIDE_STEP);
                    screenZoom = Const.INSIDE_ZOOM;
                }

                if (screenFade.updateFade()) {
                    player.setX(newPlayerCoords[0]);
                    player.setY(newPlayerCoords[1]);
                    if (currentMap == -1) {
                        gameRunning = true;

                        Const.MUSIC_SOUND.loop();
                    }

                    if (newMap == maps.length - 1) {
                        Const.resetSound(Const.MUSIC_SOUND);
                    }

                    if (gameOver) {
                        gameRunning = false;
                        currentMenu = 2;

                        Const.resetSound(Const.END_SOUND);

                        Const.END_SOUND.start();
                    }
                    currentMap = newMap;
                }
                if (screenFade.isDone()) {
                    mapChanged = false;
                    player.setCanMove(true);
                    screenFade.resetFade();

                    if (currentMap == maps.length - 1) {
                        NPC killerNPC = maps[currentMap].getNPCByName(killerName);
                        maps[currentMap].setPlayerTalkingTo(killerNPC);

                        killerNPC.startTalking(player);

                        player.setDirection("W");
                        player.setTalking(true);
                        player.setCanMove(false);
                    }
                }
            }

            if (gameRunning) {
                player.updateFrame();
                maps[currentMap].updateFrames();

                if (player.getCanMove()) {
                    player.move(keysHeld, maps[currentMap].getHitboxes());
                } else if (!(talkingDelayEvent.isEvent())) {
                    talkingDelayEvent.updateDelay();
                }

                if (maps[currentMap].getMovementRelative()) {
                    scroll[0] = scrollOrigin[0] - player.getX();
                    scroll[1] = scrollOrigin[1] - player.getY();
                }

                // check if player in radius of InteractiveObjects, if so, do their function.(also updates a 1sec delay on checking interactions)
                if (!(keysHeld.isEmpty()) && !(player.isTalking())) {
                    if (interactionDelayEvent.isEvent()) {
                        int[] newMapPostions = maps[currentMap].checkInteraction(keysHeld, player);

                        if (newMapPostions != null) {
                            if (newMapPostions[0] == maps.length - 1) {
                                if (endGameReady) {
                                    changeMap(newMapPostions);
                                } else {
                                    Main.warnPlayer("You are ready yet.");
                                }
                            } else {
                                changeMap(newMapPostions);
                            }
                        }
                    }

                    interactionDelayEvent.updateDelay();
                } else {
                    interactionDelayEvent.resetDelay();
                }
            }
            //------------------------------------------------------------------
        }
    }
    //------------------------------------------------------------------------------  
    public class KeyboardListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_W) {
                addKeyHeld("w");
            }
            if (key == KeyEvent.VK_S) {
                addKeyHeld("s");
            }
            if (key == KeyEvent.VK_A) {
                addKeyHeld("a");
            }
            if (key == KeyEvent.VK_D) {
                addKeyHeld("d");
            }
            if (key == KeyEvent.VK_E) {
                addKeyHeld("e");
            }
            if (key == KeyEvent.VK_SPACE) {
                addKeyHeld("SPACE");
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W) {
                keysHeld.remove("w");
            }
            if (key == KeyEvent.VK_S) {
                keysHeld.remove("s");
            }
            if (key == KeyEvent.VK_A) {
                keysHeld.remove("a");
            }
            if (key == KeyEvent.VK_D) {
                keysHeld.remove("d");
            }
            if (key == KeyEvent.VK_E) {
                keysHeld.remove("e");
            }
            if (key == KeyEvent.VK_SPACE) {
                keysHeld.remove("SPACE");
            }
        }
        public void keyTyped(KeyEvent e) {
            // char keyChar = e.getKeyChar();
        }
    }
    //------------------------------------------------------------------------------  
    public class MouseButtonListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int mouse = e.getButton();
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_CLICKED);

            if (mouse == MouseEvent.BUTTON1) {
                if (gameRunning && player.isTalking()) {
                    if (talkingDelayEvent.isEvent()) {
                        Const.resetSound(Const.CLICK_SOUND);

                        Const.CLICK_SOUND.setFramePosition(0);
                        Const.CLICK_SOUND.start();
                        if (maps[currentMap].getPlayerTalkingTo().getDialogue().isEndOfScript()) {

                            player.setTalking(false);
                            player.setCanMove(true);
                            maps[currentMap].getPlayerTalkingTo().resetDirection();

                            String playerTalkingToByName = maps[currentMap].getPlayerTalkingTo().getSprite().getName();
                            if (playerTalkingToByName.equalsIgnoreCase("Betty")) {
                                player.setTalkedToBetty(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("Rupert")) {
                                player.setTalkedToRupert(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("Chad")) {
                                player.setTalkedToChad(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("Brad")) {
                                player.setTalkedToBrad(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("Gus")) {
                                player.setTalkedToGus(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("OutsideGus")) {
                                player.setTalkedToOutsideGus(true);
                            } else if (playerTalkingToByName.equalsIgnoreCase("Susan")) {
                                player.setTalkedToSusan(true);
                                endGameReady = true;
                            } else if (playerTalkingToByName.equalsIgnoreCase("MaskedGus")) {
                                player.setTalkedToMaskedGus(true);
                                gameOver = true;
                                mapChanged = true;
                            }

                            maps[currentMap].setPlayerTalkingTo(null);

                            talkingDelayEvent.resetDelay();
                        } else {
                            maps[currentMap].getPlayerTalkingTo().getDialogue().nextScript();
                        }
                    }
                }
            }
        }
        public void mousePressed(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_PRESSED);
        }
        public void mouseReleased(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_RELEASED);
        }
        public void mouseEntered(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_ENTERED);
        }
        public void mouseExited(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_EXITED);
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------
    public class MenuMotionListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) { // MUST be implemented even if not used!
        }
        public void mouseMoved(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (!(gameRunning)) {
                hoverButtons(mouseX, mouseY);
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------
    /** 
     * Check if any button in the current game state menu is colliding with the mouse.
     * If so, change the button to its hover color, otherwise, reset the button to the original color.
     * @param mouseX
     * @param mouseY
     */
    public void hoverButtons(int mouseX, int mouseY) {
        menus[currentMenu].resetButtons();

        ArrayList < Integer > hoveredButtons = menus[currentMenu].findCollidedButtons(mouseX, mouseY);

        for (int hoveredButtonIndex: hoveredButtons) {
            Button hoveredButton = ((Button) menus[currentMenu].getMenuItem(hoveredButtonIndex));

            hoveredButton.hoveredColor();
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------
    /** 
     * Check if any of the button's on the current map has been collided with and have been activated by the mouse.
     * If so, change the menu shown. 
     * @param mouseX
     * @param mouseY
     * @param mouseEvent
     */
    public void callRanButtons(int mouseX, int mouseY, int mouseEvent) {
        if (!(gameRunning)) {
            ArrayList < Integer > ranButtons = menus[currentMenu].findCollidedButtons(mouseX, mouseY);

            if (!(ranButtons.isEmpty())) {
                Const.resetSound(Const.CLICK_SOUND);

                // Click sound implementation
                Const.CLICK_SOUND.setFramePosition(0);
                Const.CLICK_SOUND.start();
            }

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) menus[currentMenu].getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    if (ranButton.getButtonFunction() == -1) {
                        if (gameOver) {
                            System.out.println("Game Over!");
                            System.exit(0);
                        } else {
                            mapChanged = true;
                        }
                    } else {
                        currentMenu = ranButton.getButtonFunction();
                    }
                }
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------
    //draw everything
    public class GamePanel extends JPanel {
        GamePanel() {
            setFocusable(true);
            requestFocusInWindow();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required

            // required to zoom screen
            Graphics2D g2d = (Graphics2D) g;

            if (gameRunning) {
                AffineTransform orginalTransformation = g2d.getTransform();

                double width = getWidth();
                double height = getHeight();

                double zoomWidth = width * screenZoom;
                double zoomHeight = height * screenZoom;

                double anchorx = (width - zoomWidth) / 2;
                double anchory = (height - zoomHeight) / 2;

                g2d.translate(anchorx, anchory);
                g2d.scale(screenZoom, screenZoom);
                g2d.translate(-100, -100);

                if (maps[currentMap].getMovementRelative()) {
                    maps[currentMap].draw(g2d, player, scroll);
                } else {
                    maps[currentMap].draw(g2d, player, scroll);
                }

                g2d.setTransform(orginalTransformation);

                if (player.isTalking()) {
                    maps[currentMap].getPlayerTalkingTo().drawDialogue(g2d);
                }

                if (warningPlayer) {
                    warningMenu.draw(g2d);
                }
            } else {
                menus[currentMenu].draw(g2d);
            }

            if (mapChanged) {
                screenFade.draw(g2d);
            }
        }
    }
    //------------------------------------------------------------------------------
    public void addKeyHeld(String key) {
        if (!(keysHeld.contains(key))) {
            keysHeld.add(key);
        }
    }
    //------------------------------------------------------------------------------
    public static void warnPlayer(String warning) {
        warningPlayer = true;

        warningDelayEvent.resetDelay();

        ((Button) warningMenu.getMenuItem(0)).getText().setText(warning);
        ((Button) warningMenu.getMenuItem(0)).centerButton(Const.WIDTH / 2, Const.HEIGHT - 180);
    }
    //------------------------------------------------------------------------------
    public void changeMap(int[] newMapPostions) {
        mapChanged = true;
        player.setCanMove(false);

        newMap = newMapPostions[0];
        newPlayerCoords[0] = newMapPostions[1];
        newPlayerCoords[1] = newMapPostions[2];
    }
    //------------------------------------------------------------------------------
    public static void main(String[] args) {
        Main demo = new Main();
        demo.run();
    }
}
import java.awt.Graphics2D;

import java.util.ArrayList;

import java.util.Scanner;
import java.io.File;

// possible exceptions
import java.io.IOException;

/**
 * The Button is a class that parses, contains and displays dialogue between two speakers. 
 * Dialogue is stored in script.txt files, that encode data using special characters.
 * @see NPC
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Dialogue {
    public static final int SENTENCE_LIMIT = 12;

    private int numOfLines;
    private String[][] lines;
    private int currentLine;

    private boolean[] speaker2TalkingIndexes;

    private boolean dialogueExtended;
    private int dialogueExtendedIndex;

    private int margin;
    private Rect textBox;
    private Paragraph text;
    private Image speaker1Image;
    private Image speaker2Image;
    private Image currentTitleImage;

    private Sound[] speaker1Sounds;
    private Sound[] speaker2Sounds;
    //------------------------------------------------------------------------------       
    public Dialogue(String speaker1, Sound[] speaker1Sounds, String speaker2, Sound[] speaker2Sounds, String[] customWords, int margin) {
        this.loadDialogue("scripts/" + speaker1 + ".txt", speaker1, speaker1Sounds, speaker2, speaker2Sounds, customWords, margin);
    }

    public Dialogue(String scriptName, String speaker1, Sound[] speaker1Sounds, String speaker2, Sound[] speaker2Sounds, String[] customWords, int margin) {
        this.loadDialogue("scripts/" + scriptName + ".txt", speaker1, speaker1Sounds, speaker2, speaker2Sounds, customWords, margin);
    }

    public void loadDialogue(String scriptName, String speaker1, Sound[] speaker1Sounds, String speaker2, Sound[] speaker2Sounds, String[] customWords, int margin) {
        this.speaker1Sounds = speaker1Sounds;
        this.speaker2Sounds = speaker2Sounds;
        this.margin = margin;

        // tally every line in the Dialogue file
        try {
            Scanner input = new Scanner(new File(scriptName));
            while (input.hasNext()) {
                input.nextLine();
                this.numOfLines++;
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("Unable to read dialogue file. " + scriptName);
        }

        // parse every line in script, detect special charecters then divide the line on every slash
        this.lines = new String[this.numOfLines][];
        this.speaker2TalkingIndexes = new boolean[this.numOfLines];
        dialogueExtended = true;
        try {
            Scanner input = new Scanner(new File(scriptName));

            for (int scriptIndex = 0; scriptIndex < this.numOfLines; scriptIndex++) {
                String scriptLine = input.nextLine();

                if (scriptLine.equalsIgnoreCase("-")) {
                    this.dialogueExtendedIndex = scriptIndex;
                    this.dialogueExtended = false;
                    continue;
                }

                if (scriptLine.charAt(0) == '+') {
                    this.speaker2TalkingIndexes[scriptIndex] = true;
                    scriptLine = scriptLine.replace("+", "");
                }

                if (customWords != null) {
                    int customWordIndex = scriptLine.indexOf("~");
                    if (customWordIndex != -1) {
                        int customWordKey = Character.getNumericValue(scriptLine.charAt(customWordIndex + 1));
                        scriptLine = scriptLine.replace(scriptLine.substring(customWordIndex, customWordIndex + 2), customWords[customWordKey]);
                    }
                }

                ArrayList < Integer > wordBreaks = new ArrayList < Integer > ();
                wordBreaks.add(-1);
                int wordBreakIndex = 0;
                while (wordBreakIndex != -1) {
                    wordBreakIndex = scriptLine.indexOf("/", wordBreakIndex + 1);
                    if (wordBreakIndex != -1) {
                        wordBreaks.add(wordBreakIndex);
                    }
                }
                wordBreaks.add(scriptLine.length());
                String[] words = new String[wordBreaks.size() - 1];

                for (int wordBreak = 0; wordBreak < words.length; wordBreak++) {
                    words[wordBreak] = scriptLine.substring(wordBreaks.get(wordBreak) + 1, wordBreaks.get(wordBreak + 1));
                }

                this.lines[scriptIndex] = words;
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("Unable to parse Dialogue file. " + scriptName);
        }

        this.textBox = new Rect(0, 0, Const.LIGHT_BROWN_COLOR, Const.WIDTH / 3 * 2, Const.HEIGHT / 5, this.margin, Const.BROWN_COLOR);
        this.textBox.centerRect(Const.WIDTH / 2, Const.HEIGHT / 5 * 4);

        this.speaker1Image = new Image(this.textBox.getX(), this.textBox.getY() + this.textBox.getHeight(), "images/" + speaker1 + "_title.png");
        this.speaker1Image.setY(this.speaker1Image.getY() - this.speaker1Image.getHeight());

        this.speaker2Image = new Image(this.textBox.getX(), this.textBox.getY() + this.textBox.getHeight(), "images/" + speaker2 + "_title.png");
        this.speaker2Image.setY(this.speaker2Image.getY() - this.speaker2Image.getHeight());

        this.text = new Paragraph(0, 0, this.lines[this.currentLine], Const.MEDIUM_FONT, Const.BLACK_COLOR);

        this.updateText();
    }
    //------------------------------------------------------------------------------       
    public int getNumOfLines() {
        return this.numOfLines;
    }

    public void setNumOfLines(int numOfLines) {
        this.numOfLines = numOfLines;
    }

    public String[][] getLines() {
        return this.lines;
    }

    public void setLines(String[][] lines) {
        this.lines = lines;
    }

    public int getCurrentLine() {
        return this.currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public boolean[] getSpeaker2TalkingIndexes() {
        return this.speaker2TalkingIndexes;
    }

    public void setSpeaker2TalkingIndexes(boolean[] speaker2TalkingIndexes) {
        this.speaker2TalkingIndexes = speaker2TalkingIndexes;
    }

    public boolean isDialogueExtended() {
        return this.dialogueExtended;
    }

    public boolean getDialogueExtended() {
        return this.dialogueExtended;
    }

    public void setDialogueExtended(boolean dialogueExtended) {
        this.dialogueExtended = dialogueExtended;
    }

    public int getDialogueExtendedIndex() {
        return this.dialogueExtendedIndex;
    }

    public void setDialogueExtendedIndex(int dialogueExtendedIndex) {
        this.dialogueExtendedIndex = dialogueExtendedIndex;
    }

    public int getMargin() {
        return this.margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public Rect getTextBox() {
        return this.textBox;
    }

    public void setTextBox(Rect textBox) {
        this.textBox = textBox;
    }

    public Paragraph getText() {
        return this.text;
    }

    public void setText(Paragraph text) {
        this.text = text;
    }

    public Image getSpeaker1Image() {
        return this.speaker1Image;
    }

    public void setSpeaker1Image(Image speaker1Image) {
        this.speaker1Image = speaker1Image;
    }

    public Image getSpeaker2Image() {
        return this.speaker2Image;
    }

    public void setSpeaker2Image(Image speaker2Image) {
        this.speaker2Image = speaker2Image;
    }

    public Image getCurrentTitleImage() {
        return this.currentTitleImage;
    }

    public void setCurrentTitleImage(Image currentTitleImage) {
        this.currentTitleImage = currentTitleImage;
    }

    public Sound[] getSpeaker1Sounds() {
        return this.speaker1Sounds;
    }

    public void setSpeaker1Sounds(Sound[] speaker1Sounds) {
        this.speaker1Sounds = speaker1Sounds;
    }

    public Sound[] getSpeaker2Sounds() {
        return this.speaker2Sounds;
    }

    public void setSpeaker2Sounds(Sound[] speaker2Sounds) {
        this.speaker2Sounds = speaker2Sounds;
    }
    //------------------------------------------------------------------------------       	    
    public void extendDialogue() {
        this.dialogueExtended = true;
    }
    //------------------------------------------------------------------------------       	    
    public void nextScript() { // true = end of dialogue, false = when to next script
        this.currentLine++;

        this.playSpeakerSound();

        this.updateText();
    }
    //------------------------------------------------------------------------------       	    
    /**
     * Checks if the current line is the last spoken line.
     * @return boolean
     */
    public boolean isEndOfScript() { // true = end of dialogue, false = more dialogue avaliable
        if (this.dialogueExtended) {
            return (this.currentLine + 1 >= this.lines.length);
        } else {
            return (this.currentLine + 1 >= this.dialogueExtendedIndex);
        }
    }
    //------------------------------------------------------------------------------       	   
    public void resetScript() {
        // reset the current line to 0 or the start of the extended dialogue
        if (this.dialogueExtended) {
            if (this.dialogueExtendedIndex == 0) {
                this.currentLine = this.dialogueExtendedIndex;
            } else {
                this.currentLine = this.dialogueExtendedIndex + 1;
            }
        } else {
            this.currentLine = 0;
        }

        this.updateText();
    }
    //------------------------------------------------------------------------------       	    
    public void playSpeakerSound() {
        // resets all sounds
        Const.resetSound(this.speaker1Sounds);
        Const.resetSound(this.speaker2Sounds);

        // checks if it should play the shorter or longer talking sound
        int wordCount = 0;
        for (String script: this.lines[this.currentLine]) {
            wordCount = wordCount + script.split(" ").length;
        }

        if (wordCount < Dialogue.SENTENCE_LIMIT) {
            if (this.speaker2TalkingIndexes[this.currentLine]) {
                speaker2Sounds[0].setFramePosition(0);
                speaker2Sounds[0].start();
            } else {
                speaker1Sounds[0].setFramePosition(0);
                speaker1Sounds[0].start();
            }
        } else {
            if (this.speaker2TalkingIndexes[this.currentLine]) {
                speaker2Sounds[1].setFramePosition(0);
                speaker2Sounds[1].start();
            } else {
                speaker1Sounds[1].setFramePosition(0);
                speaker1Sounds[1].start();
            }
        }
    }
    //------------------------------------------------------------------------------       	    
    public void startDialogue() {
        this.resetScript();
        this.playSpeakerSound();
    }
    //------------------------------------------------------------------------------       	    
    // update the rect and text when the TEXT STRING changes
    public void updateText() {
        this.text.setText(this.lines[this.currentLine]);

        this.text.setX(this.textBox.getX() + this.margin + this.speaker1Image.getWidth());
        this.text.setY(this.textBox.getY());

        if (this.speaker2TalkingIndexes[this.currentLine]) {
            this.currentTitleImage = this.speaker2Image;
        } else {
            this.currentTitleImage = this.speaker1Image;
        }
    }
    //------------------------------------------------------------------------------       	    
    public String[] getScript() {
        return this.lines[this.currentLine];
    }
    //------------------------------------------------------------------------------       	    
    public void draw(Graphics2D g2d) {
        this.textBox.draw(g2d);

        this.currentTitleImage.draw(g2d);

        this.text.draw(g2d);
    }
    //------------------------------------------------------------------------------       	    
    @Override
    public String toString() {
        return "{" +
            " numOfLines='" + getNumOfLines() + "'" +
            ", lines='" + getLines() + "'" +
            ", currentLine='" + getCurrentLine() + "'" +
            ", speaker2TalkingIndexes='" + getSpeaker2TalkingIndexes() + "'" +
            ", dialogueExtended='" + isDialogueExtended() + "'" +
            ", dialogueExtendedIndex='" + getDialogueExtendedIndex() + "'" +
            ", margin='" + getMargin() + "'" +
            ", textBox='" + getTextBox() + "'" +
            ", text='" + getText() + "'" +
            ", speaker1Image='" + getSpeaker1Image() + "'" +
            ", speaker2Image='" + getSpeaker2Image() + "'" +
            ", currentTitleImage='" + getCurrentTitleImage() + "'" +
            ", speaker1Sounds='" + getSpeaker1Sounds() + "'" +
            ", speaker2Sounds='" + getSpeaker2Sounds() + "'" +
            "}";
    }
}
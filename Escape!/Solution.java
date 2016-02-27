import java.io.*;
import java.util.*;

public class Solution {
    
    public static final char kCellEmpty     = '.';
    public static final char kCellObstacle  = '#';
    public static final char kCellYou       = 'Y';
    public static final char kCellBear      = 'B';
    public static final char kCellEscape    = 'E';
    public static final char kCellVisited   = 'o';
    
    public static char[][] grid = null;
    public static int gridRows = 0;
    public static int gridCols = 0;
    
    public static int numTimesEscaped = 0;
    public static int numTimesEaten = 0;
    
    /**
     * helper to determine if a given coordinate is in the grid. If it is not,
     * then it fences the grid with "obstacle" pieces.
     */
    public static char getGridValSafe(int r, int c) {
        if (r < 0 || r >= gridRows) {
            return kCellObstacle;
        }
                
        if (c < 0 || c >= gridCols) {
            return kCellObstacle;
        }
        
        return grid[r][c];
    }
    
    public static void recurse(int youCurR, int youCurC, int youNextR, int youNextC, int bearCurR, int bearCurC, char lastBearCell) {

        char chDst = getGridValSafe(youNextR, youNextC);
        if (kCellEscape == chDst) {
            ++numTimesEscaped;
            return;
        } else if (kCellEmpty != chDst) {
            return;
        }
        
        // Perform the move
        grid[youCurR][youCurC] = kCellVisited;
        grid[youNextR][youNextC] = kCellYou;
        
        // Bear move time
        int bearNextR = bearCurR;
        int bearNextC = bearCurC;
        
        char ul = getGridValSafe(bearCurR - 1, bearCurC - 1);
        char cl = getGridValSafe(bearCurR + 0, bearCurC - 1);
        char dl = getGridValSafe(bearCurR + 1, bearCurC - 1);
        char uc = getGridValSafe(bearCurR - 1, bearCurC + 0);
        char dc = getGridValSafe(bearCurR + 1, bearCurC + 0);
        char ur = getGridValSafe(bearCurR - 1, bearCurC + 1);
        char cr = getGridValSafe(bearCurR + 0, bearCurC + 1);
        char dr = getGridValSafe(bearCurR + 1, bearCurC + 1);
        
        // Adjacent You means dinner!
        if (kCellYou == ul || kCellYou == cl || kCellYou == dl || kCellYou == uc
             || kCellYou == dc || kCellYou == ur || kCellYou == cr || kCellYou == dr) {
            ++numTimesEaten;
            
            // Set back values
            grid[youCurR][youCurC] = kCellYou;
            grid[youNextR][youNextC] = chDst;
            
            return;
        }
        
        // row difference to determine which x direction the bear will move in
        int diffR = youNextR - bearCurR;
        if (diffR < 0) {
            --bearNextR;
        } else if (diffR > 0) {
            ++bearNextR;
        }
        
        // column difference to determine which y direction the bear will move in
        int diffC = youNextC - bearCurC;
        if (diffC < 0) {
            --bearNextC;
        } else if (diffC > 0) {
            ++bearNextC;
        }
        
        char chBearDst = getGridValSafe(bearNextR, bearNextC);
        boolean bBearMoved = false;
        if (kCellObstacle != chBearDst) {
            grid[bearCurR][bearCurC] = lastBearCell;
            grid[bearNextR][bearNextC] = kCellBear;
            bBearMoved = true;
        } else {
            bearNextR = bearCurR;
            bearNextC = bearCurC;
            chBearDst = lastBearCell;
        }
        
        // Recurse! - consider all permutations:
        // if you move down-left, down, or up-left
        recurse(youNextR, youNextC, youNextR - 1, youNextC - 1, bearNextR, bearNextC, chBearDst);
        recurse(youNextR, youNextC, youNextR + 0, youNextC - 1, bearNextR, bearNextC, chBearDst);
        recurse(youNextR, youNextC, youNextR + 1, youNextC - 1, bearNextR, bearNextC, chBearDst);
        
        // if you move left or right
        recurse(youNextR, youNextC, youNextR - 1, youNextC + 0, bearNextR, bearNextC, chBearDst);
        recurse(youNextR, youNextC, youNextR + 1, youNextC + 0, bearNextR, bearNextC, chBearDst);
        
        // if you move up-left, up, or up-right
        recurse(youNextR, youNextC, youNextR - 1, youNextC + 1, bearNextR, bearNextC, chBearDst);
        recurse(youNextR, youNextC, youNextR + 0, youNextC + 1, bearNextR, bearNextC, chBearDst);
        recurse(youNextR, youNextC, youNextR + 1, youNextC + 1, bearNextR, bearNextC, chBearDst);
        
        // Set back the values
        if (bBearMoved) {
            grid[bearCurR][bearCurC] = kCellBear;
            grid[bearNextR][bearNextC] = chBearDst;
        }
        
        grid[youCurR][youCurC] = kCellYou;
        grid[youNextR][youNextC] = chDst;
    }
    
    public static void main(String args[]) {
        // Parse each line in the file
        Scanner kb = new Scanner(System.in);
        
        int numGrids = Integer.parseInt(kb.nextLine().trim());
        for (int g = 0; g < numGrids; ++g) {
            String strLine = kb.nextLine().trim();
            String[] strArr = strLine.split(" ");
            
            gridRows = Integer.parseInt(strArr[0]);
            gridCols = Integer.parseInt(strArr[1]);
            numTimesEscaped = 0;
            numTimesEaten = 0;
            
            grid = new char[gridRows][gridCols];
            
            int bearR = 0;
            int bearC = 0;
            int youR = 0;
            int youC = 0;
            
            for (int r = 0; r < gridRows; ++r) {
                strLine = kb.nextLine().trim();
                
                for (int c = 0; c < gridCols; ++c) {
                    char ch = strLine.charAt(c);
                    if (kCellYou == ch) {
                        youR = r;
                        youC = c;
                    } else if (kCellBear == ch) {
                        bearR = r;
                        bearC = c;
                    }
                    
                    grid[r][c] = ch;
                }
            }
            
            recurse(youR, youC, youR - 1, youC - 1, bearR, bearC, kCellEmpty);
            recurse(youR, youC, youR + 0, youC - 1, bearR, bearC, kCellEmpty);
            recurse(youR, youC, youR + 1, youC - 1, bearR, bearC, kCellEmpty);
            
            recurse(youR, youC, youR - 1, youC + 0, bearR, bearC, kCellEmpty);
            recurse(youR, youC, youR + 1, youC + 0, bearR, bearC, kCellEmpty);
            
            recurse(youR, youC, youR - 1, youC + 1, bearR, bearC, kCellEmpty);
            recurse(youR, youC, youR + 0, youC + 1, bearR, bearC, kCellEmpty);
            recurse(youR, youC, youR + 1, youC + 1, bearR, bearC, kCellEmpty);
            
            System.out.printf("%d%%\n", numTimesEscaped * 100 / (numTimesEscaped + numTimesEaten));
        }
    }    
}

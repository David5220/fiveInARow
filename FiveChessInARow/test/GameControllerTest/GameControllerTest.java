/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameControllerTest;

import com.test.fiveinarow.GameController;
import com.test.fiveinarow.db.DBHelper;
import com.test.fiveinarow.gui.ChessBoard;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 23577
 */
public class GameControllerTest {
    private GameController gameGui = new GameController(new ChessBoard());  
    DBHelper playerDB;
    
    public GameControllerTest() {
        gameGui=new  GameController();
        playerDB = new DBHelper();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
//         gameGui=new  GameGUI();
//		for (int i = 0; i < ChessBoard.BOARDSIZE; i++) {
//			for (int j = 0; j < ChessBoard.BOARDSIZE; j++) {
//				gameGui.chessBoard.board[i][j] = new Random().nextInt(1)+1;
//			}
//		}
    }
    
    @After
    public void tearDown() {
    }

 
    @Test
    public void testCheckCountForBlackChess(){
        System.out.println("BlackChess is able to move or increase count to any directions");
        
        int xChange = gameGui.checkCount(1, 0, 1);
        int yChange= gameGui.checkCount(0, 1, 1);
        int noChange= gameGui.checkCount(0, 0, 1);
        int allChange =gameGui.checkCount(1, 1, 1);
        
        int expResult = 1;
        assertEquals(expResult,xChange,0);
        assertEquals(expResult,yChange,0);
        assertEquals(expResult,noChange,0);
        assertEquals(expResult,allChange,0);
    }
    
     @Test
    public void testCheckCountForWhiteChess(){
        System.out.println("WhiteChess is able to move or increase count to any directions");
        
        int xChange = gameGui.checkCount(1, 0, 2);
        int yChange= gameGui.checkCount(0, 1, 2);
        int noChange= gameGui.checkCount(0, 0, 2);
        int allChange =gameGui.checkCount(1, 1, 2);
        
        int expResult = 1;
        assertEquals(expResult,xChange,0);     
        assertEquals(expResult,yChange,0);
        assertEquals(expResult,noChange,0);
        assertEquals(expResult,allChange,0);
    }
    
    @Test
    public void testCheckifAnyoneWins(){
        System.out.println("black or white chess win");
        
        boolean expWin = true;
        boolean win = gameGui.checkWin();
        assertTrue(expWin == win);
        
    }

    @Test
	public void testClearChess() {
            System.out.println("clear for chess works");
		int temp [][]=new int[ChessBoard.BOARDSIZE][ChessBoard.BOARDSIZE];
		
		for (int i = 0; i < gameGui.chessBoard.board.length; i++) {
			for (int j = 0; j < gameGui.chessBoard.board.length; j++) {
				temp[i][j]=0;
			}
		}
		gameGui.clearChess();
		assertArrayEquals(temp,  gameGui.chessBoard.board);
	}
	
//	@Test
//	public void testAutoConnectPlayerScoreDB(){
//            System.out.println("Connect");
//		playerDB.autoConnectPlayerScoreDB();
//		assertNotNull(playerDB.conn);
//	}
//	@Test
//	public void testCreatePlayerScoreTable(){
//		playerDB.createPlayerScoreTable();
//		assertNotNull(playerDB.statement);
//	}
}

package edu.clarkson.batest.ee242;

import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;  // Contains EventHandler & ActionEvent
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.input.*; // Contains MouseEvent & KeyEvent
import javafx.stage.*; // Contains Stage and StageStyle
import javafx.util.Pair;
import javafx.scene.*; // Contains Scene, Group, and Node
import javafx.scene.control.*; // Contains Button
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.shape.*; // Contains Circle
import javafx.scene.text.Text;
import javafx.scene.image.*; // Contains ImageView & Image
import javafx.scene.layout.*; // Contains subclasses of anchorPane
import javafx.scene.paint.Color;
/**
 * This is a GUI that impliments the die and loaded die classes in order to play a dice game
 * This game keeps track of points. If you roll a 1 on the first roll you lose.
 * Every time you roll a new die it is added to a list and you get as many points as the number on the die.
 * If you roll the same number twice without rolling a 1 in between you lose.
 * @author troy
 *
 */
public class DieGame extends Application {
		
	double width = 500.0; //width of the anchor pane
	double height = 300.0; //height of the anchor pane
	static Die gameDie = new Die(); //die used in game can be a standard die or loaded die
	int scoreValue = 0; 	//keeps track of points
	int numberOfRolls=0;	//keeps track of how many times the die has been rolled
	int pastRollValue = 1;	//gives past roll value for reference
	int currentRollValue = 1;	//represents the current roll
	Text pastRoll = new Text("Past Roll:"); 			//------------------------------------------
	Text currentRoll  = new Text("Current Roll");
	Text rollCount = new Text("Number Of Rolls: 0");
	Text score = new Text("Score: 0" );				//All of these lay out the text for the GUI
	Text currentDieNumber = new Text("X");
	Text pastDieValues = new Text("");
	Text title	=new Text("Hot Dice");
	Rectangle currentDie = new Rectangle(100,100);	//------------------------------------------
	Boolean gameOver = false;		//represents if the user has lost
	Vector<Integer> previousValues = new Vector<>();	//a vector of previously rolled values, serves to check if the user has lost
	ImageView diePNG = new ImageView();
	/**
	 * Main function begins the GUI
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args); //launches gui with args as an input
		
	}
	/**
	 * Start is used to create a multi threaded application it adds all of the buttons text and images to properly lay out the GUI.
	 * Start will also create event listeners, so that when the buttons are pressed they call a sub routine.
	 */
	public void start(final Stage primaryStage) {
		try {						
			GridPane root = new GridPane();			//creates gid pane
			root.getStyleClass().add("dietheme"); 	//sets theme to what is defined in application.css
			
			AnchorPane anchorPane = new AnchorPane(); //creates anchorPane this will display our data to the user
			anchorPane.setMinSize(width, height);
			anchorPane.setMaxSize(width, height);
			anchorPane.setPrefSize(width, height);// sets anchor pane to specific size
			
			HBox buttonsBox = new HBox();	//HBox sets inputs side by side
			buttonsBox.getStyleClass().add("dietheme"); //sets color scheme, font and centered
			
			
			/*
			 * Adds all buttons and sets their styles to be the same
			 */
			Button StandardNewDieButton = new Button("New Standard Die");
			StandardNewDieButton.getStyleClass().add("buttontheme"); 
			
			Button LoadedDieButton = new Button("Make a loaded Die");
			LoadedDieButton.getStyleClass().add("buttontheme");

			Button rollDieButton = new Button("Roll Die");
			rollDieButton.getStyleClass().add("buttontheme");			
		
			Button newGame = new Button("New Game");
			newGame.getStyleClass().add("buttontheme");
		
			/*
			 * quitButton lets you quit the program
			 */
			Button quitButton = new Button("Quit");
			quitButton.getStyleClass().add("buttontheme");
			
			/*
			 * Vbox takes objects and displays them vertically
			 */
			VBox gameBox = new VBox();
			gameBox.getStyleClass().add("dietheme");
			/*
			 * Sets all texts to the same themes
			 */
			score.getStyleClass().add("texttheme");
			rollCount.getStyleClass().add("texttheme");
			currentRoll.getStyleClass().add("texttheme");
			pastRoll.getStyleClass().add("texttheme");
			currentDieNumber.getStyleClass().add("dietheme");
			pastDieValues.getStyleClass().add("texttheme");
			title.getStyleClass().add("titletheme");
			
			
			/*
			 * Creates a rectangle that represents a side of a die
			 */
			currentDie.setFill(Color.WHITE);


			/*
			 * The scene is created
			 */
			Scene scene = new Scene( root, width, height+50 );
			
			/*
			 * Add the file application.css as a resource to the
			 * scene's style sheets
			 */
			scene.getStylesheets().add( 
					getClass().getResource("application.css").toExternalForm() );
			
			/*
			 * All Buttons get added to the buttonsBox
			 */
			buttonsBox.getChildren().add( rollDieButton );
			buttonsBox.getChildren().add( newGame );
			buttonsBox.getChildren().add( StandardNewDieButton );
			buttonsBox.getChildren().add(LoadedDieButton);
			buttonsBox.getChildren().add( quitButton );	
			
			/*
			 * creates a stack pane to symbolize a die, the die backround and the number are placed in it
			 */
			StackPane dieImage = new StackPane();
			dieImage.getStyleClass().add("dietheme");
			dieImage.getChildren().addAll(currentDie,currentDieNumber);
			
			/*
			 * Handles the images
			 */
			//Image diePicture = new Image("Die1.png");
			
			diePNG.setVisible(false);
			/*
			 * Adds all of the text to the vBox gameBox and centers it
			 */
			dieImage.getChildren().add(diePNG);
			
			
			
			gameBox.getChildren().add(title);
			gameBox.getChildren().add(currentRoll);
			gameBox.getChildren().add(dieImage);
			gameBox.getChildren().add(score);
			gameBox.getChildren().add(rollCount);
			gameBox.getChildren().add(pastRoll);
			gameBox.getChildren().add(pastDieValues);
			gameBox.translateXProperty().bind(scene.widthProperty().subtract(gameBox.widthProperty().add(20))
                    .divide(2));
			anchorPane.getChildren().add(gameBox); //adds game box to anchor Pane 
		
			
			/*
			 * adds in the anchor pane and buttonsbox to the root
			 */
			root.add( anchorPane, 0, 0 );
			root.add( buttonsBox, 0, 1 );
			
				
			
			/*
			 * addNewDieButton opens a dialog box, the user than provides how many sided die they want, it will then change the game die to be that many sides
			 */
			StandardNewDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					
					TextInputDialog newDieDialog = new TextInputDialog("N");
					newDieDialog.setTitle("New Die");
					newDieDialog.setHeaderText("New Standard Die");
					newDieDialog.setContentText("Input the number of sides:");

					Optional<String> result = newDieDialog.showAndWait(); //waits for the user to input value
					if (result.isPresent()&&result.get().matches("\\d*")){ //makes sure the user result is present and it is a number
							if (Integer.parseInt(result.get()) > 2) //makes sure the value is greater than 2
								{
								gameDie = new Die(Integer.parseInt(result.get())); //if it is a number it creates a die that many sides
								newGame();
								}							
							
					}
				}
					
			});
			/**
			 * Opens a dialog box, that will load the current die. 
			 * It asks the user to input the desired side to load and its factor
			 * it will only accept numbers between 1-100 for the load factor
			 * and then 1 through the number of sides. It then changes the die to a loaded die with those parameters
			 */
			LoadedDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					/*
					 * Dialog takes two string inputs
					 */
					Dialog<Pair<String, String>> loadedDieDialog = new Dialog<>();
					ButtonType loginButtonType = new ButtonType("Load Die", ButtonData.OK_DONE);
					loadedDieDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
					
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					
					TextField side = new TextField();
					side.setPromptText("1 - "+ gameDie.numberOfSides);
					
					TextField weight = new TextField();
					weight.setPromptText("1 - 100");
					/*
					 * adds both string inputs to a grid
					 */
					grid.add(new Label("Load Side:"), 0, 0);
					grid.add(side, 1, 0);
					grid.add(new Label("Load Factor:"), 0, 1);
					grid.add(weight, 1, 1);
					
					loadedDieDialog.setTitle("Load Die");
					loadedDieDialog.setHeaderText("New Standard Die");
					loadedDieDialog.setContentText("Input the number of sides:"); 
					loadedDieDialog.getDialogPane().setContent(grid);
					
					Optional<Pair<String, String>> result = loadedDieDialog.showAndWait(); //waits for input
					/*
					 * makes sure both inputs are numbers and the are in the proper range, 
					 * then creates a loaded die and starts a new game
					 */
					if (!side.getText().isEmpty()&&!weight.getText().isEmpty()&&side.getText().matches("\\d*") && weight.getText().matches("\\d*")){
						if(Integer.parseInt(side.getText())>0&&Integer.parseInt(side.getText())<gameDie.numberOfSides&&Integer.parseInt(weight.getText())>0&&Integer.parseInt(weight.getText())<100){
						gameDie= new LoadedDie(gameDie.numberOfSides,Integer.parseInt(side.getText()),Integer.parseInt(weight.getText()) );
						newGame();
						}
					}
					

				}
					
			});
			
			/*
			 * rolls the die when pressed
			 */
			rollDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					roll();
				}
			});
			
			/*
			 * The quit button closes the window and exits the application
			 */
			quitButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					try {
						primaryStage.close(); // closes the window
												// ( can be reopened by calling primaryStage.show() )
						Platform.exit(); // exits the current application thread		
					} catch ( Exception e ) {
						e.printStackTrace(System.err);
					}
				}
			});			
			/**
			 * starts a new game
			 */
			newGame.setOnAction( new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					newGame();
				}
				
			});
			/*
			 * disables the boarder around the window
			 * sets the scene to be the designed game
			 * disables resizeing and shows it to the user
			 */
			primaryStage.initStyle( StageStyle.TRANSPARENT );			
			primaryStage.setScene( scene );
			primaryStage.setResizable( false );
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Called to roll the die
 * Checks to see if the game is over, then follows the game logic
 * making sure the user didnt loose already, then checks to see if the first roll is a 1.
 * Also checks to see the number rolled has been rolled since the last time a 1 has been rolled before
 */
	protected void roll() {
		if (!gameOver){ //checks if the game is over
			currentRollValue=gameDie.roll(); //rolls the die
			numberOfRolls++; //increments roll
			if(currentRollValue < 10){
				diePNG.setVisible(true); //shows image on top of other box
				diePNG.setImage(new Image(getClass().getResource("Die"+currentRollValue+".png").toExternalForm())); //loads the new image
			}
			else{
				diePNG.setVisible(false); //disables the image
				currentDieNumber.setText(""+currentRollValue); //updates users
			} 
			if(numberOfRolls>1){ //checks to see if it is the first roll
				if (currentRollValue == 1) //if not the first roll and a 1 is rolled it resets the previous values
					{
					previousValues.clear();
					pastDieValues.setText("");
					}
				
				else if (previousValues.contains(currentRollValue)) //if the value is present in the current roll vector the player looses
					gameOver();
				else{
					previousValues.addElement(currentRollValue); //if none of the prior happen the user gets the points and the current roll is added to the vector 
					if(previousValues.size()>20) //checks how many values are in the vector
						pastDieValues.setWrappingWidth(300); // wrapps the past die values so that if the user selects a large die it can handle multiple lines
					pastDieValues.setText("" +previousValues.toString()); //displays all the variables
					}
			}
			else{
				if (currentRollValue ==1)
					gameOver(); //if the user rolls 1 on their first roll they lose
				else
				{
					previousValues.addElement(currentRollValue); //adds the element to the vector
					pastDieValues.setText("" +previousValues.toString()); //displays last roll as past rolls
					}
			}
			scoreValue = scoreValue + currentRollValue; //adds score
			score.setText("Score: "+scoreValue); //sets the text to be the score
			rollCount.setText("Number Of Rolls: "+ numberOfRolls); //shows roll count
		}
	}
	/**
	 * Game over creates an alert box that alerts the user that the game is 
	 * over and asks if they want to play again
	 * if they say yes it starts a new game
	 */
	protected void gameOver() {
		gameOver=true; //sets variable represetning the end of game to true
		/*
		 *sets up the alert with desired outputs
		 */
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("Game Over! Play again?");
		alert.setContentText("");
		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("No"); 
		alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeYes){
			newGame(); //starts a new game
		} else {
			 	//do nothing and wait for the user to close out or start a new game
			}
		}
	
	/**
	 * New game resets all of the text fields and resets the score, past values, number of rolls, 
	 * then resets gameOver to be false 
	 */

	protected void newGame() {
		previousValues.clear(); //clears all past values
		scoreValue = 0;
		numberOfRolls=0;
		pastRollValue = 1;
		currentRollValue = 0;
		score.setText("Score: "+scoreValue);
		rollCount.setText("Number Of Rolls: "+ numberOfRolls);
		currentDieNumber.setText("X");
		pastDieValues.setText("");
		gameOver=false;
		pastDieValues.setWrappingWidth(0); //unwrapps text
	}
	}
	
	


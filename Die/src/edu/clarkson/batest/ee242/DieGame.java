package edu.clarkson.batest.ee242;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;  // Contains EventHandler & ActionEvent
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

public class DieGame extends Application {
		
	double width = 300.0;
	double height = 200.0;
	static Die gameDie = new Die();
	int scoreValue = 0;
	int numberOfRolls=0;
	int pastRollValue = 1;
	int currentRollValue = 1;
	Text pastRoll = new Text(105,10 ,"Past Roll");
	Text currentRoll  = new Text(315, 10, "Current Roll");
	Text pastDieNumber = new Text(310, 100 ," X");
	Text rollCount = new Text(90,180,"Number Of Rolls: 0");
	Text score = new Text(90,155,"Score: 0" );
	Text currentDieNumber = new Text(90, 100, " X");
	Rectangle pastDie = new Rectangle(300, 25, 100, 100);
	Rectangle currentDie = new Rectangle(80, 25, 100,100);
	Boolean gameOver = false;
	Vector<Integer> previousValues = new Vector<>();
	
	static List<String> choices = new ArrayList<>();
	public static void main(String[] args) {
		choices.add("Standard");
		choices.add("Loaded");
		launch(args);
		
	}
	
	public void start(final Stage primaryStage) {
		try {			
			/* 
			 * Step 0: Initialize the global variables
			 */	
			
			GridPane root = new GridPane();
			root.getStyleClass().add("graytheme");
			
			AnchorPane anchorPane = new AnchorPane();
			anchorPane.setMinSize(width, height);
			anchorPane.setMaxSize(width, height);
			anchorPane.setPrefSize(width, height);
			
			/*
			 * The HBox is a layout manager object 
			 * that allows placing objects side-by-side
			 * (similar to this is the VBox). All buttons
			 * are placed in the HBox, and its style is
			 * also graytheme.
			 */
			HBox buttonsBox = new HBox();
			buttonsBox.getStyleClass().add("graytheme");
			
			Button StandardNewDieButton = new Button("New Standard Die");
			StandardNewDieButton.getStyleClass().add("buttontheme");
			
			Button LoadedDieButton = new Button("Laod Curent Die");
			LoadedDieButton.getStyleClass().add("buttontheme");

			Button rollDieButton = new Button("Roll Die");
			rollDieButton.getStyleClass().add("buttontheme");			
			
			/*
			 * quitButton lets you quit the program
			 */
			Button quitButton = new Button("Quit");
			quitButton.getStyleClass().add("buttontheme");
			
			Button newGame = new Button("New Game");
			newGame.getStyleClass().add("buttontheme");
		
			score.getStyleClass().add("texttheme");
			rollCount.getStyleClass().add("texttheme");
			currentRoll.getStyleClass().add("texttheme");
			pastRoll.getStyleClass().add("texttheme");
			currentDieNumber.getStyleClass().add("dietheme");
			pastDieNumber.getStyleClass().add("dietheme");
			
			
			
			currentDie.setFill(Color.WHITE);
			currentDie.setArcHeight(25);
			currentDie.setArcWidth(25);

			pastDie.setFill(Color.WHITE);
			pastDie.setArcHeight(25);
			pastDie.setArcWidth(25);

/*			Image currentDiePNG = new Image("/DieImages/Die1.png");
			ImageView currentDieImage = new ImageView(currentDiePNG);
			currentDieImage.setX(100.00);
			currentDieImage.setY(100.00);*/
			/*
			 * The scene is created from the GridanchorPane root
			 */
			Scene scene = new Scene( root, 500, 300 );
			
			/*
			 * Add the file application.css as a resource to the
			 * scene's style sheets
			 */
			scene.getStylesheets().add( 
					getClass().getResource("application.css").toExternalForm() );
			
			/*
			 * Step 1(b): Create the scene graph
			 */
			
			/*
			 * Buttons go into the buttonsBox
			 */
			buttonsBox.getChildren().add( rollDieButton );
			buttonsBox.getChildren().add( newGame );
			buttonsBox.getChildren().add( StandardNewDieButton );
			buttonsBox.getChildren().add(LoadedDieButton);
				
			buttonsBox.getChildren().add( quitButton );	
			
			
			
			anchorPane.getChildren().add(score);
			anchorPane.getChildren().add(rollCount);
			anchorPane.getChildren().add(currentRoll);
			anchorPane.getChildren().add(pastRoll);
			anchorPane.getChildren().add(currentDie);
			anchorPane.getChildren().add(pastDie);
			anchorPane.getChildren().add(currentDieNumber);
			anchorPane.getChildren().add(pastDieNumber);
			//anchorPane.getChildren().add(currentDieImage);
			
			/*
			 * The anchorPane and buttonsBox go into the
			 * GridPane root. The add() method of GridPane lets you 
			 * directly add elements at different indices
			 * in a grid, instead of using getChildren().add()
			 */
			root.add( anchorPane, 0, 0 );
			root.add( buttonsBox, 0, 1 );
			
			
			/*
			 * Step 2: Set up all your event handling
			 */
			
			/*
			 * To handle mouse clicks and mouse drags, we create an EventHandler object that handles
			 * a MouseEvent. The anonymous class can also be used to create a named object,
			 * called mouseHandler, which will be attached to mouse clicks and mouse 
			 * drags in the image view. mouseHandler places a circle at the click point
			 */
			EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
				public void handle( MouseEvent event ) {
					
					}					
			};
			
			/*
			 * Since several nodes in the scene graph can receive the 
			 * key pressed event, specifically register an event filter
			 * for key pressing to the scene. The key pressed event
			 * detects if the 'Z' key has has been pressed, and 
			 * clears the circles if it has. 
			 */
			scene.addEventFilter( KeyEvent.KEY_PRESSED, 
					new EventHandler<KeyEvent>() {				
				public void handle( KeyEvent event ) {
					// A KeyEvent object contains information
					// about the pressed keycode. This code
					// can be compared to the constants in the 
					// KeyCode enumeration
					if (event.getCode().equals( KeyCode.R ) ) {
						//roll die						
					}
					event.consume(); // do not let the event go down to the target
				}
			});
						
			
			/*
			 * addNewDieButton should open a dialog box (specifically
			 * a TextInputDialog). The dialog box allows the user to
			 * provide an image name.
			 */
			StandardNewDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					
					TextInputDialog newDieDialog = new TextInputDialog("N");
					newDieDialog.setTitle("New Die");
					newDieDialog.setHeaderText("New Standard Die");
					newDieDialog.setContentText("Input the number of sides:");

					// Traditional way to get the response value.
					Optional<String> result = newDieDialog.showAndWait();
					if (result.isPresent()){
						if (result.get().matches("\\d*")) 
							gameDie = new Die(Integer.parseInt(result.get()));
					}
				}
					
			});
			
			LoadedDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
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
					
					grid.add(new Label("Load Side:"), 0, 0);
					grid.add(side, 1, 0);
					grid.add(new Label("Load weight:"), 0, 1);
					grid.add(weight, 1, 1);
					
					loadedDieDialog.setTitle("Load Die");
					loadedDieDialog.setHeaderText("New Standard Die");
					loadedDieDialog.setContentText("Input the number of sides:"); 
					
					loadedDieDialog.getDialogPane().setContent(grid);
					
					Optional<Pair<String, String>> result = loadedDieDialog.showAndWait();
				
					if (!side.getText().isEmpty()&&!weight.getText().isEmpty()&&side.getText().matches("\\d*") && weight.getText().matches("\\d*")){
						gameDie= new LoadedDie(gameDie.numberOfSides,Integer.parseInt(side.getText()),Integer.parseInt(weight.getText()) );
					}

					

				}
					
			});
			
			/*
			 * The switch circle button should cycle through the circle themes,
			 * and set the current circles' style to the new theme, as well as
			 * set the group to the new theme for any new circles.
			 */
			rollDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					if (!gameOver){
						pastRollValue=currentRollValue;
						currentRollValue=gameDie.roll();
						numberOfRolls++;
						currentDieNumber.setText(""+currentRollValue);
						if(numberOfRolls>1){
							pastDieNumber.setText(""+pastRollValue);
							if (currentRollValue == 1)
								previousValues.clear();
							else if (previousValues.contains(currentRollValue))
								gameOver();
							else
								previousValues.addElement(currentRollValue);
							}
						
						else{
							if (currentRollValue ==1)
								gameOver();
							pastDieNumber.setText(" X");
						}
						scoreValue = scoreValue + currentRollValue;
						score.setText("Score: "+scoreValue);
						rollCount.setText("Number Of Rolls: "+ numberOfRolls);
						
					}
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
			newGame.setOnAction( new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					newGame();
				}
				
			});
			
			primaryStage.initStyle( StageStyle.TRANSPARENT );			
			primaryStage.setScene( scene );
			primaryStage.setResizable( false );
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void gameOver() {
		gameOver=true;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("Game Over! Play again?");
		alert.setContentText("");
		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("No"); 
		alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeYes){
			newGame();
		} else {
			 	//do nothing and wait for the user to close out or start a new game
			}
		}

	protected void newGame() {
		scoreValue = 0;
		numberOfRolls=0;
		pastRollValue = 1;
		currentRollValue = 0;
		score.setText("Score: "+scoreValue);
		rollCount.setText("Number Of Rolls: "+ numberOfRolls);
		currentDieNumber.setText(" X");
		pastDieNumber.setText(" X");
		gameOver=false;
	}
		
		
	}
	
	


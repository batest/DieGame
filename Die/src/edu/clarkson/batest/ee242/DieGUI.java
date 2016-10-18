package edu.clarkson.batest.ee242;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;  // Contains EventHandler & ActionEvent
import javafx.scene.input.*; // Contains MouseEvent & KeyEvent
import javafx.stage.*; // Contains Stage and StageStyle
import javafx.scene.*; // Contains Scene, Group, and Node
import javafx.scene.control.*; // Contains Button
import javafx.scene.shape.*; // Contains Circle
import javafx.scene.image.*; // Contains ImageView & Image
import javafx.scene.layout.*; // Contains subclasses of anchorPane

public class DieGUI extends Application {
	
	@Override	
	public void start(final Stage primaryStage) {
		try {			
			/* 
			 * Step 0: Initialize the global variables
			 */	
			
			GridPane root = new GridPane();
			root.getStyleClass().add("graytheme");

			AnchorPane anchorPane = new AnchorPane();
			anchorPane.setMinSize(300.0, 400.0);
			anchorPane.setMaxSize(300.0, 400.0);
			anchorPane.setPrefSize(300.0, 400.0);
			
			/*
			 * The HBox is a layout manager object 
			 * that allows placing objects side-by-side
			 * (similar to this is the VBox). All buttons
			 * are placed in the HBox, and its style is
			 * also graytheme.
			 */
			HBox buttonsBox = new HBox();
			buttonsBox.getStyleClass().add("graytheme");
			
			/*
			 * openImageButton lets you open the image,
			 * and has the style buttontheme that sets the
			 * color and font of the button.
			 */
			Button addNewDieButton = new Button();
			addNewDieButton.setText( "New Die" );
			addNewDieButton.getStyleClass().add("buttontheme");
			
			/* 
			 * switchCircleColorButton cycles through 
			 * the colors of the circle, between red,
			 * green, and blue.
			 */
			Button rollDieButton = new Button();
			rollDieButton.setText( "Roll Die" );
			rollDieButton.getStyleClass().add("buttontheme");			
			
			/*
			 * quitButton lets you quit the program
			 */
			Button quitButton = new Button();
			quitButton.setText( "Quit" );
			quitButton.getStyleClass().add("buttontheme");
			
			
			/*
			 * The following group object is created to 
			 * group all circles together, so that we can delete
			 * them all or change all their styles
			 */
			final Group group = new Group();
			
			
			/*
			 * The scene is created from the GridanchorPane root
			 */
			Scene scene = new Scene( root, 500, 500 );
			
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
			buttonsBox.getChildren().add( addNewDieButton );
			buttonsBox.getChildren().add( rollDieButton );	
			buttonsBox.getChildren().add( quitButton );	
			
			/*
			 * The imageView and circles group go
			 * into anchorPane
			 */
			anchorPane.getChildren().add( group );
			
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
			addNewDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					// Create a new TextInputDialog, with a title,
					// a header text, and a content menu
					/*List<String> choices = new ArrayList<>();
					choices.add("Standard");
					choices.add("Loaded");
					Label label1 = new Label("Name: ");
					Label label2 = new Label("Phone: ");
					TextField text1 = new TextField();
					TextField text2 = new TextField();
					GridPane grid = new GridPane();
					grid.add(label1, 1, 1);
					grid.add(text1, 2, 1);
					grid.add(label2, 1, 2);
					grid.add(text2, 2, 2);
					*/
    

					Dialog<String> dieTypeDialog = new Dialog<>();		
					//dieTypeDialog.getDialogPane().setContent(grid);
					//dieTypeDialog.setTitle("New Die");
					//dieTypeDialog.setHeaderText("Please Input the desired type of Die");
					//dieTypeDialog.setContentText("Die Type:");
					
					// The Optional class keeps track of whether a desired
					// value is present or absent. If it is present, 
					// (i.e., if a non-null value is present), the
					// get() method in Optional returns the value. The value is of 
					// the type <T> fed in angular brackets. In the case
					// of the dialog box, it is a String corresponding to the
					// text the user types.
					Optional<String> result = dieTypeDialog.showAndWait(); // wait for the user's input
					}
					
			});
			
			/*
			 * The switch circle button should cycle through the circle themes,
			 * and set the current circles' style to the new theme, as well as
			 * set the group to the new theme for any new circles.
			 */
			rollDieButton.setOnAction( new EventHandler<ActionEvent>() {
				public void handle( ActionEvent event ) {
					System.out.println("Rolling!");
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
			primaryStage.initStyle( StageStyle.TRANSPARENT );			
			primaryStage.setScene( scene );
			primaryStage.setResizable( false );
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
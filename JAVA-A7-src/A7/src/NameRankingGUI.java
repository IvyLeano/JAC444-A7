import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NameRankingGUI extends Application {
	GridPane search_again = new GridPane();
	GridPane year_gender_name = new GridPane();
	Scene gridpane_search_again = new Scene(search_again);
	Scene gridpane_year_gender_name = new Scene(year_gender_name);
	Text name_ranked = new Text();
	TextField re_search_input = new TextField();
	TextField year_input = new TextField();
	TextField gender_input = new TextField();
	TextField name_input = new TextField();

	@Override
	public void start(Stage primaryStage) {
		NameRanker nameRanker = new NameRanker();
		// GridPane settings
		year_gender_name.setHgap(6);
		year_gender_name.setVgap(6);
		search_again.setHgap(6);
		search_again.setVgap(6);
		year_gender_name.setPrefSize(500, 200);
		search_again.setPrefSize(500, 200);
		year_gender_name.setAlignment(Pos.CENTER);
		search_again.setAlignment(Pos.CENTER);

		// Below are the elements for GridPane "year_gender_name"
		Text year = new Text("Enter the Year: ");
		year_input = new TextField();
		Text gender = new Text("Enter the Gender: ");
		gender_input = new TextField();
		Text name = new Text("Enter the Name: ");
		name_input = new TextField();
		Button btnSubmitQuery = new Button("Submit Query");
		Button btnExit_year_gender_name = new Button("Exit");

		// Below are the elements for the GridPane "search_again"
		Text re_search = new Text("Do you want to try again: ");
		Button btnSubmit = new Button("Submit");
		Button btnExit_search_again = new Button("Exit");

		// Adding the elements to GridPane "year_gender_name"
		year_gender_name.add(year, 0, 0);
		year_gender_name.add(year_input, 1, 0);
		year_gender_name.add(gender, 0, 1);
		year_gender_name.add(gender_input, 1, 1);
		year_gender_name.add(name, 0, 2);
		year_gender_name.add(name_input, 1, 2);
		year_gender_name.add(btnSubmitQuery, 1, 3, 1, 1);
		btnSubmitQuery.setMaxWidth(Double.MAX_VALUE);
		year_gender_name.add(btnExit_year_gender_name, 1, 4, 1, 1);
		btnExit_year_gender_name.setMaxWidth(Double.MAX_VALUE);

		// Adding the elements to GridPane "search_again"
		search_again.add(name_ranked, 0, 0);
		search_again.add(re_search, 0, 1);
		search_again.add(re_search_input, 1, 1);
		search_again.add(btnSubmit, 1, 2, 1, 1);
		btnSubmit.setMaxWidth(Double.MAX_VALUE);

		// Button on click event handlers
		btnSubmitQuery.setOnAction(event -> {
			try {
				if (nameRanker.isValid(Integer.valueOf(year_input.getText()), gender_input.getText(),
						name_input.getText())) {
					nameRanker.setState(Integer.valueOf(year_input.getText()), gender_input.getText(),
							name_input.getText());
					resetNameRanked(nameRanker.getRanking());
					primaryStage.setScene(gridpane_search_again);
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				resetNameRanked("Invalid input.");
				primaryStage.setScene(gridpane_search_again);
			}
		});
		btnSubmit.setOnAction(event -> {
			// try catch for line:
			// Character.toUpperCase(re_search_input.getText().charAt(0)) == 'Y'.
			// throws exception if there is no input
			try {
				if (searchAgain()) {
					if (Character.toUpperCase(re_search_input.getText().charAt(0)) == 'Y') {
						resetYearGenderAge();
						primaryStage.setScene(gridpane_year_gender_name);
						search_again.getChildren().remove(re_search_input);
						re_search_input = new TextField();
						search_again.add(re_search_input, 1, 1);
					} else {
						System.exit(0);
					}
				} else {
					resetNameRanked("Invalid Input, please enter Y/N.");
				}
			} catch (Exception e) {
				resetNameRanked("Invalid Input, please enter Y/N.");
			}
		});
		btnExit_year_gender_name.setOnAction(event -> {
			System.exit(0);
		});
		primaryStage.setTitle("Search Name Ranking Application");
		primaryStage.setScene(gridpane_year_gender_name);
		primaryStage.show();
	}

	// resets the notification text
	void resetNameRanked(String name) {
		search_again.getChildren().remove(name_ranked);
		name_ranked = new Text(name);
		search_again.add(name_ranked, 0, 0);
	}

	// resets the gridPane for year, gender and age input
	void resetYearGenderAge() {
		year_gender_name.getChildren().remove(year_input);
		year_gender_name.getChildren().remove(gender_input);
		year_gender_name.getChildren().remove(name_input);
		year_input = new TextField();
		gender_input = new TextField();
		name_input = new TextField();
		year_gender_name.add(year_input, 1, 0);
		year_gender_name.add(gender_input, 1, 1);
		year_gender_name.add(name_input, 1, 2);
	}

	// validates user input for "Do you want to search for another name"
	boolean searchAgain() {
		boolean correctLength = re_search_input.getText().length() == 1;
		boolean correctLetter = Character.toUpperCase(re_search_input.getText().charAt(0)) == 'Y'
				|| Character.toUpperCase(re_search_input.getText().charAt(0)) == 'N';

		return correctLength && correctLetter;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
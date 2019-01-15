package Project;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ReminderCalendar extends Application{
    Stage window;
    Scene scene1, scene2;
    String year = "2019";
    String defaultMessage = "No messages.";
    String dateSelected="";
    ArrayList<String> messages = new ArrayList<>();
    String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    String[] months = { "", "January", "February", "March"
            , "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Set the scene and create a GridPane to organize how it is display
        window = primaryStage;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        Label monthLabel = new Label(months[3]);
        GridPane.setConstraints(monthLabel,0,0);
        Label yearLabel = new Label(year);
        GridPane.setConstraints(yearLabel,1,0);

        Label sunLabel = new Label(days[0]);
        Label monLabel = new Label(days[1]);
        Label tueLabel = new Label(days[2]);
        Label wedLabel = new Label(days[3]);
        Label thuLabel = new Label(days[4]);
        Label friLabel = new Label(days[5]);
        Label satLabel = new Label(days[6]);
        Label[] labels = {sunLabel, monLabel,tueLabel,wedLabel,thuLabel,friLabel,satLabel};

        //Set up the display of the Calendar
        for(int i=0;i<labels.length;i++){
            GridPane.setConstraints(labels[i],i,1);
        }
        Label[] num = new Label[31];
        for(int j =1;j<=31;j++){
            Label temp = new Label(j+"");
            num[j-1] = temp;
        }
        int counter1=5;
        int counter2=2;
        for(Label day : num){
            GridPane.setConstraints(day,counter1,counter2);
            counter1++;
            if(counter1 > 6){
                counter2++;
                counter1=0;
            }
        }

        grid.getChildren().addAll(monthLabel,yearLabel,sunLabel,monLabel,tueLabel,wedLabel,thuLabel,friLabel,satLabel);

        for(Label day: num){
            grid.getChildren().add(day);
        }

        //Add a drop down box with option to choose what date to set the reminder to
        ChoiceBox<String> dateChoice = new ChoiceBox<>();
        for(int i =1;i<32;i++){
            dateChoice.getItems().add(i + "/"+ months[3] + "/"+ year);
        }
        dateChoice.setValue("1/"+months[3]+"/"+year);
        GridPane.setConstraints(dateChoice,9,2);
        dateSelected = dateChoice.getValue();

        //Create buttons and set their function
        Button button1 = new Button("Check reminders");
        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10,10,10,10));
        grid2.setVgap(8);
        grid2.setHgap(10);
        scene2 = new Scene(grid2,400,300);
        button1.setOnAction(e -> AlertBox.display("Reminders", getMessages()));

        GridPane.setConstraints(button1,9, 0);
        grid.getChildren().addAll(button1);

        TextField input = new TextField();
        input.setPromptText("Reminder message");
        GridPane.setConstraints(input,9,3);
        Button addButton = new Button("Add reminder");
        GridPane.setConstraints(addButton,9,4);
        grid.getChildren().addAll(input,addButton,dateChoice);

        addButton.setOnAction(e -> addMessage(input, dateChoice));


        Label s = new Label(getMessages());
        GridPane.setConstraints(s,1,1);
        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> window.setScene(scene2));
        GridPane.setConstraints(refresh, 8, 1);
        grid2.getChildren().addAll(s);

        Scene scene = new Scene(grid,400,300);

        window.setScene(scene);
        window.setTitle("Calendar");
        window.show();
    }
    //Method to get all the messages that have been added as a String
    public String getMessages(){
        String body ="";
        
        if(messages.size() == 0){
          return "No Messages.";
        }
        for(String s :messages){
            body += s + "\n";
        }
        return body;
    }
    //Method to add the message in the TextField into the messages ArrayList
    public void addMessage(TextField input, ChoiceBox<String> dateChoice){
       String message = dateChoice.getValue();
       message += ": " + input.getText();
        messages.add(message);
    }
}

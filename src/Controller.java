import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.fxml.Initializable;

public class Controller implements Initializable {

    @FXML
    private Button btn_generate;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label label_end_word;

    @FXML
    private Label label_start_word;

    @FXML
    private TextField tf_end_word;

    @FXML
    private TextField tf_start_word;

    @FXML
    private ChoiceBox<String> cb_mode;

    @FXML
    private Label label_time;

    @FXML
    private Label label_visited;

    @FXML
    private Label label_size;

    @FXML
    private Label label_memory;

    private static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        return totalMemory - freeMemory;
    }

    @FXML
    void generateAnswers(ActionEvent event) {
        String start = (tf_start_word.getText()).toLowerCase();
        String end = (tf_end_word.getText()).toLowerCase();
        label_time.setText("");
        label_visited.setText("");
        listView.getItems().clear();

        label_end_word.setText("End Word");
        label_end_word.setTextFill(Color.rgb(79, 113, 138));
        label_start_word.setText("Start Word");
        label_start_word.setTextFill(Color.rgb(79, 113, 138));
        label_memory.setText("");
        label_size.setText("");

        // Kondisi String Kosong
        if (start.length() == 0 || end.length() == 0) {
            if (start.length() == 0) {
                label_start_word.setText("Fill This");
                label_start_word.setTextFill(Color.rgb(255, 169, 169));
            }
            if (end.length() == 0) {
                label_end_word.setText("Fill This");
                label_end_word.setTextFill(Color.rgb(255, 169, 169));
            }

            return;
        }

        // Kondisi Panjang Karakter Tidak Sama
        if (start.length() != end.length()) {
            label_start_word.setText("Words Length Not Same");
            label_start_word.setTextFill(Color.rgb(255, 169, 169));
            label_end_word.setText("");
            label_end_word.setTextFill(Color.rgb(255, 169, 169));

            return;
        }

        // Kondisi Kata Tidak Ditemukan
        if (!WordLadder.isStringValid(start) || !WordLadder.isStringValid(end)) {
            if (!WordLadder.isStringValid(start)) {
                label_start_word.setText("Not Found");
                label_start_word.setTextFill(Color.rgb(255, 169, 169));
            }
            if (!WordLadder.isStringValid(end)) {
                label_end_word.setText("Not Found");
                label_end_word.setTextFill(Color.rgb(255, 169, 169));
            }

            return;
        }

        if (currentMode == null) {
            label_start_word.setText("Pilih Mode Terlebih Dahulu");
            label_start_word.setTextFill(Color.rgb(255, 169, 169));
            label_end_word.setText("");
            label_end_word.setTextFill(Color.rgb(255, 169, 169));

            return;
        }
        label_end_word.setText("End Word");
        label_end_word.setTextFill(Color.rgb(79, 113, 138));
        label_start_word.setText("Start Word");
        label_start_word.setTextFill(Color.rgb(79, 113, 138));

        // Kondisi Valid Semua
        List<String> answers = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        long initialMemory = getMemoryUsage();

        if (currentMode.equals("A-Star")) {
            answers = AStar.astar(start, end);
        } else if (currentMode.equals("GBFS")) {
            answers = BestFirstSearch.bfs(start, end);
        } else if (currentMode.equals("UCS")) {
            answers = UniformCostSearch.ucs(start, end);
        }

        long finalMemory = getMemoryUsage();
        long endTime = System.currentTimeMillis();

        long memoryUsage = finalMemory - initialMemory;
        long timeElapsed = endTime - startTime;

        String visitednodes = answers.get(0);
        answers.removeFirst();

        label_memory.setText("Memory Usage : " + memoryUsage + " bytes");
        label_size.setText("Path Size    : " + answers.size());

        label_time.setText("Time Elapsed  : " + timeElapsed + " ms");
        label_visited.setText("Visited Nodes : " + visitednodes);

        ObservableList<String> items = FXCollections.observableArrayList(answers);
        listView.setItems(items);

        // Render List
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item);
                            if (getIndex() == 0) {
                                setBackground(new Background(
                                        new BackgroundFill(Color.rgb(168, 236, 183), CornerRadii.EMPTY, Insets.EMPTY))); // Green
                            } else if (getIndex() == items.size() - 1) {
                                setBackground(new Background(
                                        new BackgroundFill(Color.rgb(245, 188, 188), CornerRadii.EMPTY, Insets.EMPTY))); // Red
                            } else {
                                setBackground(new Background(
                                        new BackgroundFill(Color.rgb(182, 207, 229), CornerRadii.EMPTY, Insets.EMPTY))); // Blue
                            }
                        } else {
                            setText(null);
                            setBackground(new Background(
                                    new BackgroundFill(Color.rgb(14, 21, 29), CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                };
            }
        });
    }

    private String[] mode = { "A-Star", "GBFS", "UCS" };

    private String currentMode = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Set up the ChoiceBox
        cb_mode.getItems().addAll(mode);
        cb_mode.setOnAction(this::getMode);
    }

    public void getMode(ActionEvent event) {
        currentMode = cb_mode.getValue();
        System.out.println(currentMode);
    }
}

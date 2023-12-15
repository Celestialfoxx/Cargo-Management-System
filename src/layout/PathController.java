package layout;

import java.util.List;

import org.slf4j.helpers.Util;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PathController {

	private List<String> cities;
	
	private String curCity;
	
	private double dis;
	
	@FXML
	private HBox hBox;
	
	
	@FXML
	private Label distanceNum;
	
	public PathController() {
	}
	
	public PathController(List<String> cities, String curCity, double dis) {
		this.cities = cities;
		this.curCity = curCity;
		this.dis = dis;
	}
	
	public void setCities(List<String> cities) {
        this.cities = cities;
    }
	
	public void setCurCity(String cur) {
        this.curCity = cur;
    }
	
	public void setDis(double dis) {
        this.dis = dis;
    }
	
	@FXML
    public void initialize() {
    }
	
	public void updateLabels() {
		hBox.getChildren().clear();
	    hBox.setSpacing(10);
	    int n = cities.size();
        for (String s : cities) {
            Label label = new Label(s);
            String rgb = utils.Utils.generateRandomColor();
            label.setStyle("-fx-text-fill: " + rgb + ";");
            if (s.equals(this.curCity)) {
            	label.setFont(Font.font("System", FontWeight.BOLD, 14));
            	label.setUnderline(true);
            }
            n--;
            HBox.setMargin(label, new Insets(0, 10, 0, 10)); // 上, 右, 下, 左
            hBox.getChildren().add(label);
            if (n>0) {
            	Label arow = new Label("-->");
            	hBox.getChildren().add(arow);
            }
        }
        distanceNum.setText(Double.toString(dis));
    }
	
}

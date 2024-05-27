package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Publisherpage {

	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<Publisher> pub;
	private ObservableList<Publisher> publist;
	TableView<Publisher> table = new TableView<Publisher>();
	
	public BorderPane publisher(Stage primaryStage) {
		pub = new ArrayList<>();
		
		try {
			getData();
			publist = FXCollections.observableArrayList(pub);
			table(primaryStage);
			primaryStage.show();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bp;
	}
	
	
	//method to get data from table in mySQL
	private void getData() throws ClassNotFoundException, SQLException {
		String sql;
		con.connectDB();
		System.out.println("connected");
		
		sql = "select publisherID, place, country, yearofpublication from Publisher";
		Statement s = ConnectionDataBase.connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
			pub.add(new Publisher(Integer.parseInt(rs.getString(1)),
					rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(1))));
			
		}
	}
	
	
	
	//method to set data in table and edit user interface
	private void table(Stage primaryStage) {
		
		HBox hbox1 = new HBox(15);
		HBox hbox2 = new HBox(15);
		
		
		Scene scene = new Scene(bp,750,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
		Label title = new Label("Publisher Table");
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
		
		
        
        table.setEditable(true);
        bp.setCenter(table);
    //  table.setMaxSize(750, 600);
        
        //Initialize id column 
		TableColumn<Publisher, Integer> idcolumn = new TableColumn<Publisher, Integer>("publisherID");
		idcolumn.setCellValueFactory(new PropertyValueFactory<Publisher, Integer>("publisherID"));
		
		//Initialize place column 
		TableColumn<Publisher, String> placecolumn = new TableColumn<Publisher, String>("place");
		placecolumn.setCellValueFactory(new PropertyValueFactory<Publisher, String>("place"));
		
		//select place to update it then call method to update place using id in mySQL
		placecolumn.setCellFactory(TextFieldTableCell.<Publisher>forTableColumn());
		placecolumn.setOnEditCommit((CellEditEvent<Publisher, String> t) -> {
			((Publisher) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlace(t.getNewValue());
			updatePlace(t.getRowValue().getPublisherID(), t.getNewValue());
		});
		
		//Initialize country column 
		TableColumn<Publisher, String> countrycolumn = new TableColumn<Publisher, String>("country");
		countrycolumn.setCellValueFactory(new PropertyValueFactory<Publisher, String>("country"));
		
		//select country to update it then call method to update country using id in mySQL
		countrycolumn.setCellFactory(TextFieldTableCell.<Publisher>forTableColumn());
		countrycolumn.setOnEditCommit((CellEditEvent<Publisher, String> t) -> {
			((Publisher) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountry(t.getNewValue());
			updateCountry(t.getRowValue().getPublisherID(), t.getNewValue());
		});
		
		//Initialize year of publication column 
		TableColumn<Publisher, Integer> yearcolumn = new TableColumn<Publisher, Integer>("yearofpublication");
		yearcolumn.setCellValueFactory(new PropertyValueFactory<Publisher, Integer>("yearofpublication"));
				
		//select year of publication to update it then call method to update year of publication using id in mySQL
		yearcolumn.setCellFactory(TextFieldTableCell.<Publisher, Integer>forTableColumn(new IntegerStringConverter()));
		yearcolumn.setOnEditCommit((CellEditEvent<Publisher, Integer> t) -> {
			((Publisher) t.getTableView().getItems().get(t.getTablePosition().getRow())).setYearofpublication(t.getNewValue());
			updateYear(t.getRowValue().getPublisherID(), t.getNewValue());
		});
		
		//add columns in the table
		table.setItems(publist);
		table.getColumns().add(idcolumn);
		table.getColumns().add(placecolumn);
		table.getColumns().add(countrycolumn);
		table.getColumns().add(yearcolumn);
		
		TextField addid = new TextField();
		addid.setPromptText("publisherid");
		addid.setMaxWidth(idcolumn.getPrefWidth());
		
		TextField addplace = new TextField();
		addplace.setPromptText("place");
		addplace.setMaxWidth(placecolumn.getPrefWidth());
		
		TextField addcountry = new TextField();
		addcountry.setPromptText("country");
		addcountry.setMaxWidth(countrycolumn.getPrefWidth());
		
		TextField addyear = new TextField();
		addyear.setPromptText("year of publication");
		addyear.setMaxWidth(yearcolumn.getPrefWidth());
		
		Button add = new Button("Add");
		add.setOnAction(e-> {
			Publisher p = new Publisher((Integer.valueOf(addid.getText())), addplace.getText(), addcountry.getText(), (Integer.valueOf(addyear.getText())));
			publist.add(p);
			insertData(p);
			addid.clear();
			addplace.clear();
			addcountry.clear();
			addyear.clear();
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			ObservableList<Publisher> selectrow = table.getSelectionModel().getSelectedItems();
			ArrayList<Publisher> rows = new ArrayList<>(selectrow);
			rows.forEach(row ->{
				table.getItems().remove(row);
				delete(row);
				table.refresh();
			});
		});
		
		Button back = new Button("Back");
		back.setOnAction(e-> {
			Main m = new Main();
			m.start(primaryStage);
		});
		
		hbox1.getChildren().addAll(addid, addplace, addcountry, addyear);
		hbox2.getChildren().addAll(add, delete, back);
		vbox.getChildren().addAll(title, table, hbox1, hbox2);
		bp.setCenter(vbox);;
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		selection();
	}


	//method to update place using publisher id in mySQL project
	private void updatePlace(int publisherId, String place) {
		try {
			System.out.println("update Publisher set place = '" +place+ "' where publisherID = "+publisherId);
			con.connectDB();
			con.ExecuteStatement("update Publisher set place = " +place+ " where publisherID = "+publisherId+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//method to update country using publisher id in mySQL project
	private void updateCountry(int publisherID, String country) {
		try {
			System.out.println("update publisher set country = '" +country+ "' where publisherID = "+publisherID);
			con.connectDB();
			con.ExecuteStatement("update publisher set country = '" +country+ "' where publisherID = "+publisherID+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//method to update year of publication using publisher id in mySQL project
		private void updateYear(int publisherId, int year) {
			try {
				System.out.println("update Publisher set yearofpublication = '" +year+ "' where publisherID = "+publisherId);
				con.connectDB();
				con.ExecuteStatement("update Publisher set yearofpublication = " +year+ " where publisherID = "+publisherId+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}


	//method to add data in mySQL project
	private void insertData(Publisher p) {
		try {
			System.out.println("Insert into Publisher(publisherID, place, country, yearofpublication) values(" +p.getPublisherID()+", "+p.getPlace()+", "+p.getCountry()+", "+p.getYearofpublication());
			con.connectDB();
			con.ExecuteStatement("Insert into Publisher(publisherID, place, country, yearofpublication) values(" +p.getPublisherID()+", "+p.getPlace()+", '"+p.getCountry()+"', "+p.getYearofpublication()+ ");");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//method to delete data in mySQL project
	private void delete(Publisher row) {	
		try {
			System.out.println("delete from publisher where publisherID=" +row.getPublisherID());
			con.connectDB();
			con.ExecuteStatement("delete from publisher where publisherID=" +row.getPublisherID()+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			System.out.println("This row cannot be deleted because it points to another row");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void selection() {
		bp.setPadding(new Insets(50, 50, 50, 50));
		
		Button id = new Button("Search Publisher ID");
		TextField txt1 = new TextField();
		txt1.setMaxSize(100, 20);
		
		id.setOnAction(e->{
			ObservableList<Publisher> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select * from Publisher where publisherID = "+txt1.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					l1.add(new Publisher(Integer.parseInt(rs.getString(1)), rs.getString(2),
							rs.getString(3), Integer.parseInt(rs.getString(1))));
				}
				table.setItems(l1);
			} catch (Exception e1) {
				e1.getStackTrace();
			}
		});
	
		
		HBox hbox1 = new HBox(15);
		hbox1.getChildren().addAll(id, txt1);
		hbox1.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox1);
 	}
}

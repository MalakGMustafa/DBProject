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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class libpage {

	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<Library> lib;
	private ObservableList<Library> liblist;
	TableView<Library> table = new TableView<Library>();
	
	public BorderPane library(Stage primaryStage) {
		lib = new ArrayList<>();
		
		try {
			getData();
			liblist = FXCollections.observableArrayList(lib);
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
		try {
			String sql;
			con.connectDB();
			System.out.println("connected");
			
			sql = "select libraryID, name, address from library";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getInt(1)+ " "+ rs.getString(2));
				lib.add(new Library(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3)));
			}
		} catch (Exception e) {
			e.getStackTrace();
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
		Label title = new Label("Library Table");
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
		
		//bp.getChildren().add(title);
		
		
        
        table.setEditable(true);
        bp.setCenter(table);
        table.setMaxSize(7000, 300);
        
        //Initialize id column 
		TableColumn<Library, Integer> idcolumn = new TableColumn<Library, Integer>("libraryID");
		idcolumn.setCellValueFactory(new PropertyValueFactory<Library, Integer>("libraryID"));
		
		//Initialize name column 
		TableColumn<Library, String> namecolumn = new TableColumn<Library, String>("name");
		namecolumn.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));
		
		//select name to update it then call method to update name using id in mySQL
		namecolumn.setCellFactory(TextFieldTableCell.<Library>forTableColumn());
		namecolumn.setOnEditCommit((CellEditEvent<Library, String> t) -> {
			((Library) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			updateName(t.getRowValue().getLibraryID(), t.getNewValue());
		});
		
		//Initialize address column 
		TableColumn<Library, String> addresscolumn = new TableColumn<Library, String>("address");
		addresscolumn.setCellValueFactory(new PropertyValueFactory<Library, String>("address"));
		
		//select address to update it then call method to update address using id in mySQL
		addresscolumn.setCellFactory(TextFieldTableCell.<Library>forTableColumn());
		addresscolumn.setOnEditCommit((CellEditEvent<Library, String> t) -> {
			((Library) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
			updateAddress(t.getRowValue().getLibraryID(), t.getNewValue());
		});
		
		//add columns in the table
		table.setItems(liblist);
		table.getColumns().add(idcolumn);
		table.getColumns().add(namecolumn);
		table.getColumns().add(addresscolumn);
	//	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TextField addid = new TextField();
		addid.setPromptText("libraryid");
		addid.setMaxWidth(idcolumn.getPrefWidth());
		
		TextField addname = new TextField();
		addname.setPromptText("name");
		addname.setMaxWidth(namecolumn.getPrefWidth());
		
		TextField addaddress = new TextField();
		addaddress.setPromptText("address");
		addaddress.setMaxWidth(addresscolumn.getPrefWidth());
		
		
		Button add = new Button("Add");
		add.setOnAction(e-> {
			Library l = new Library((Integer.valueOf(addid.getText())), addname.getText(), addaddress.getText());
			liblist.add(l);
			insertData(l);
			addid.clear();
			addname.clear();
			addaddress.clear();
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			ObservableList<Library> selectrow = table.getSelectionModel().getSelectedItems();
			ArrayList<Library> rows = new ArrayList<>(selectrow);
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
		
		bp.setCenter(table);
		bp.setTop(title);
		title.setAlignment(Pos.CENTER);
		hbox1.getChildren().addAll(addid, addname, addaddress);
		hbox2.getChildren().addAll(add, delete, back);
		vbox.getChildren().addAll(title, table, hbox1, hbox2);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		bp.setCenter(vbox);
		selection();
	}


	//method to update name using library id in mySQL project
	private void updateName(int libraryId, String name) {
		try {
			System.out.println("update library set name = '" +name+ "' where libraryID = "+libraryId);
			con.connectDB();
			con.ExecuteStatement("update library set name = '" +name+ "' where libraryID = "+libraryId+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//method to update address using library id in mySQL project
	private void updateAddress(int libraryId, String address) {
		try {
			System.out.println("update library set address = '" +address+ "' where libraryID = "+libraryId);
			con.connectDB();
			con.ExecuteStatement("update library set address = '" +address+ "' where libraryID = "+libraryId+ ";");
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
	private void insertData(Library l) {
		try {
			System.out.println("Insert into Library(libraryID, name, address) values(" +l.getLibraryID()+", "+l.getName()+", "+l.getAddress());
			con.connectDB();
			con.ExecuteStatement("Insert into Library(libraryID, name, address) values(" +l.getLibraryID()+", '"+l.getName()+"', '"+l.getAddress()+ "');");
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
	private void delete(Library row) {	
		try {
			System.out.println("delete from library where libraryID=" +row.getLibraryID()+ ";");
			con.connectDB();
			con.ExecuteStatement("delete from library where libraryID=" +row.getLibraryID()+ ";");
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
		
		Button id = new Button("Search Library ID");
		TextField txt1 = new TextField();
		txt1.setMaxSize(100, 20);
		
		id.setOnAction(e->{
			ObservableList<Library> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select libraryID, name, address from library where libraryID = "+txt1.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					System.out.println(rs.getInt(1)+ " "+ rs.getString(2));
					l1.add(new Library(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3)));
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

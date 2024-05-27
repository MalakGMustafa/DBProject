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

public class booksPage {
	
	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<Books> book;
	private ObservableList<Books> booklist;
	TableView<Books> table = new TableView<Books>();
	
	public BorderPane books(Stage primaryStage) {
		book = new ArrayList<>();
		
		try {
			getData();
			booklist = FXCollections.observableArrayList(book);
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
		
		sql = "select bookID, title, auther, bookstatus, libraryid, publisherid from Books";
		Statement s = ConnectionDataBase.connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
			book.add(new Books(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
					 rs.getString(4), Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
			
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
		Label title = new Label("Book Table");
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
		
		
        
        table.setEditable(true);
        bp.setCenter(table);
    //  table.setMaxSize(750, 600);
        
        //Initialize id column 
		TableColumn<Books, Integer> idcolumn = new TableColumn<Books, Integer>("bookID");
		idcolumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookID"));
		
		//Initialize title column 
		TableColumn<Books, String> titlecolumn = new TableColumn<Books, String>("title");
		titlecolumn.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
		
		//select title to update it then call method to update it using id in mySQL
		titlecolumn.setCellFactory(TextFieldTableCell.<Books>forTableColumn());
		titlecolumn.setOnEditCommit((CellEditEvent<Books, String> t) -> {
			((Books) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
			updateTitle(t.getRowValue().getBookID(), t.getNewValue());
		});
		
		//Initialize auther column 
		TableColumn<Books, String> authercolumn = new TableColumn<Books, String>("auther");
		authercolumn.setCellValueFactory(new PropertyValueFactory<Books, String>("auther"));
				
				
		//select auther to update it then call method to update it using id in mySQL
		authercolumn.setCellFactory(TextFieldTableCell.<Books>forTableColumn());
		authercolumn.setOnEditCommit((CellEditEvent<Books, String> t) -> {
			((Books) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAuther(t.getNewValue());
			updateAuther(t.getRowValue().getBookID(), t.getNewValue());
		});
		
		
		//Initialize bookstatus column 
		TableColumn<Books, String> bookstatuscolumn = new TableColumn<Books, String>("bookstatus");
		bookstatuscolumn.setCellValueFactory(new PropertyValueFactory<Books, String>("bookstatus"));
						
						
		//select bookstatus to update it then call method to update it using id in mySQL
		bookstatuscolumn.setCellFactory(TextFieldTableCell.<Books>forTableColumn());
		bookstatuscolumn.setOnEditCommit((CellEditEvent<Books, String> t) -> {				
			((Books) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBookstatus(t.getNewValue());
			updateBookstatus(t.getRowValue().getBookID(), t.getNewValue());
		});
				
		//Initialize libraryid column 
		TableColumn<Books, Integer> libraryidcolumn = new TableColumn<Books, Integer>("libraryid");
		libraryidcolumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("libraryid"));
				
		//select libraryid to update it then call method to update it using id in mySQL
		libraryidcolumn.setCellFactory(TextFieldTableCell.<Books, Integer>forTableColumn(new IntegerStringConverter()));
		libraryidcolumn.setOnEditCommit((CellEditEvent<Books, Integer> t) -> {
			((Books) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLibraryid(t.getNewValue());
			updateLibraryid(t.getRowValue().getBookID(), t.getNewValue());
		});
		
		
		//Initialize publisherid column 
		TableColumn<Books, Integer> publisheridcolumn = new TableColumn<Books, Integer>("publisherid");
		publisheridcolumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("publisherid"));
						
		//select publisherid to update it then call method to update it using id in mySQL
		publisheridcolumn.setCellFactory(TextFieldTableCell.<Books, Integer>forTableColumn(new IntegerStringConverter()));
		publisheridcolumn.setOnEditCommit((CellEditEvent<Books, Integer> t) -> {
			((Books) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPublisherid(t.getNewValue());
			updatePublisherid(t.getRowValue().getBookID(), t.getNewValue());
		});
		
	
		//add columns in the table
		table.setItems(booklist);
		table.getColumns().add(idcolumn);
		table.getColumns().add(titlecolumn);
		table.getColumns().add(authercolumn);
		table.getColumns().add(bookstatuscolumn);
		table.getColumns().add(libraryidcolumn);
		table.getColumns().add(publisheridcolumn);
		
		TextField addid = new TextField();
		addid.setPromptText("bookID");
		addid.setMaxWidth(idcolumn.getPrefWidth());
		
		TextField addtitle = new TextField();
		addtitle.setPromptText("title");
		addtitle.setMaxWidth(addtitle.getPrefWidth());
		
		TextField addauther = new TextField();
		addauther.setPromptText("auther");
		addauther.setMaxWidth(addauther.getPrefWidth());
		
		TextField addbookstatus = new TextField();
		addbookstatus.setPromptText("bookstatus");
		addbookstatus.setMaxWidth(addbookstatus.getPrefWidth());
		
		TextField addlibraryid = new TextField();
		addlibraryid.setPromptText("libraryid");
		addlibraryid.setMaxWidth(addlibraryid.getPrefWidth());
		
		TextField addpublisherid = new TextField();
		addpublisherid.setPromptText("publisherid");
		addpublisherid.setMaxWidth(addpublisherid.getPrefWidth());
		
		
		Button add = new Button("Add");
		add.setOnAction(e-> {
			Books b = new Books((Integer.valueOf(addid.getText())), addtitle.getText(), addauther.getText(), 
					addbookstatus.getText(), (Integer.valueOf(addlibraryid.getText())), (Integer.valueOf(addpublisherid.getText())));
			booklist.add(b);
			insertData(b);
			addid.clear();
			addtitle.clear();
			addauther.clear();
			addbookstatus.clear();
			addlibraryid.clear();
			addpublisherid.clear();
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			ObservableList<Books> selectrow = table.getSelectionModel().getSelectedItems();
			ArrayList<Books> rows = new ArrayList<>(selectrow);
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
		
		hbox1.getChildren().addAll(addid, addtitle, addauther, addbookstatus, addlibraryid, addpublisherid);
		hbox2.getChildren().addAll(add, delete, back);
		vbox.getChildren().addAll(title, table, hbox1, hbox2);
		bp.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		selection();
	}


	private void updateLibraryid(int bookid, Integer libraryid) {
		try {
			System.out.println("update Books set libraryid = '" +libraryid+ "' where bookID = "+bookid);
			con.connectDB();
			con.ExecuteStatement("update Books set libraryid = '" +libraryid+ "' where bookID = "+bookid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updatePublisherid(int bookid, Integer publisherid) {
		try {
			System.out.println("update Books set publisherid = '" +publisherid+ "' where bookID = "+bookid);
			con.connectDB();
			con.ExecuteStatement("update Books set publisherid = '" +publisherid+ "' where bookID = "+bookid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateBookstatus(int bookid, String bookstatus) {
		try {
			System.out.println("update Books set bookstatus = '" +bookstatus+ "' where bookID = "+bookid);
			con.connectDB();
			con.ExecuteStatement("update Books set bookstatus = '" +bookstatus+ "' where bookID = "+bookid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	//method to update title using member id in mySQL project
	private void updateTitle(int bookid, String title) {
		try {
			System.out.println("update Books set title = '" +title+ "' where bookID = "+bookid);
			con.connectDB();
			con.ExecuteStatement("update Books set title = '" +title+ "' where bookID = "+bookid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//method to update auther using member id in mySQL project
	private void updateAuther(int bookid, String auther) {
		try {
			System.out.println("update Books set auther = '" +auther+ "' where bookID = "+bookid);
			con.connectDB();
			con.ExecuteStatement("update Books set auther = '" +auther+ "' where bookID = "+bookid+ ";");
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
	private void insertData(Books b) {
		try {
			System.out.println("Insert into Books(bookID, auther, bookstatus, libraryid, publisherid) values(" 
		            +b.getBookID()+", "+b.getTitle()+", "+b.getAuther()+", "+b.getLibraryid()+", "+b.getPublisherid());
			con.connectDB();
			con.ExecuteStatement("Insert into Books(bookID, auther, bookstatus, libraryid, publisherid) values(" 
		            +b.getBookID()+", "+b.getTitle()+", "+b.getAuther()+", "+b.getLibraryid()+", "+b.getPublisherid()+ ";");
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
	private void delete(Books row) {	
		try {
			System.out.println("delete from Books where bookID=" +row.getBookID());
			con.connectDB();
			con.ExecuteStatement("delete from Books where bookID=" +row.getBookID()+ ";");
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
		
		Button id = new Button("Search Book ID");
		TextField txt1 = new TextField();
		txt1.setMaxSize(100, 20);
		
		id.setOnAction(e->{
			ObservableList<Books> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select * from Books where bookID = "+txt1.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					System.out.println(rs.getInt(1)+ " "+ rs.getString(2));
					l1.add((new Books(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
							 rs.getString(4), Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6)))));
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

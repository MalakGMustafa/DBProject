package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.util.converter.DateStringConverter;

public class Books2memPage {

	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<books2member> array;
	private ObservableList<books2member> list;
	TableView<books2member> table = new TableView<books2member>();
	
	public BorderPane books2member(Stage primaryStage) throws ParseException {
		array = new ArrayList<>();
		
		try {
			getData();
			list = FXCollections.observableArrayList(array);
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
	private void getData() throws ClassNotFoundException, SQLException, ParseException {
		String sql;
		con.connectDB();
		System.out.println("connected");
		
		sql = "select bookid, memberid, reservedate, returndate, duedate from books2member";
		Statement s = ConnectionDataBase.connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
			Date reservedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3));
			Date returndate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4));
			Date dueedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5));
			
			array.add(new books2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
					reservedate, returndate, dueedate));
			
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
		Label title = new Label("Member Table");
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
		
		
        
        table.setEditable(true);
        bp.setCenter(table);
    //  table.setMaxSize(750, 600);
        
        //Initialize bookid column 
		TableColumn<books2member, Integer> idcolumn = new TableColumn<books2member, Integer>("bookid");
		idcolumn.setCellValueFactory(new PropertyValueFactory<books2member, Integer>("bookid"));
		
		//Initialize memberid column 
		TableColumn<books2member, Integer> memberidcolumn = new TableColumn<books2member, Integer>("memberid");
		memberidcolumn.setCellValueFactory(new PropertyValueFactory<books2member, Integer>("memberid"));
		
		//Initialize reserve date column 
		TableColumn<books2member, Date> reservedatecolumn = new TableColumn<books2member, Date>("reservedate");
		reservedatecolumn.setCellValueFactory(new PropertyValueFactory<books2member, Date>("reservedate"));
		
		//select reserve date to update it then call method to update it using id in mySQL
		reservedatecolumn.setCellFactory(TextFieldTableCell.<books2member, Date>forTableColumn(new DateStringConverter()));
		reservedatecolumn.setOnEditCommit((CellEditEvent<books2member, Date> t) -> {
			((books2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReservedate(t.getNewValue());
			updateReservedate(t.getRowValue().getBookid(), t.getRowValue().getMemberid(), t.getNewValue());
			});
		
		//Initialize return date column 
		TableColumn<books2member, Date> returndatecolumn = new TableColumn<books2member, Date>("returndate");
		returndatecolumn.setCellValueFactory(new PropertyValueFactory<books2member, Date>("returndate"));
				
		//select return date to update it then call method to update it using id in mySQL
		returndatecolumn.setCellFactory(TextFieldTableCell.<books2member, Date>forTableColumn(new DateStringConverter()));
		returndatecolumn.setOnEditCommit((CellEditEvent<books2member, Date> t) -> {
			((books2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReturndate(t.getNewValue());
			updateReturndate(t.getRowValue().getBookid(), t.getRowValue().getMemberid(), t.getNewValue());
		});
				
		//Initialize due date column 
		TableColumn<books2member, Date> duedatecolumn = new TableColumn<books2member, Date>("duedate");
		duedatecolumn.setCellValueFactory(new PropertyValueFactory<books2member, Date>("duedate"));
				
		//select reserve date to update it then call method to update it using id in mySQL
		duedatecolumn.setCellFactory(TextFieldTableCell.<books2member, Date>forTableColumn(new DateStringConverter()));
		duedatecolumn.setOnEditCommit((CellEditEvent<books2member, Date> t) -> {
			((books2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDuedate(t.getNewValue());
			updateDuedate(t.getRowValue().getBookid(), t.getRowValue().getMemberid(), t.getNewValue());
		});
		
	
		//add columns in the table
		table.setItems(list);
		table.getColumns().add(idcolumn);
		table.getColumns().add(memberidcolumn);
		table.getColumns().add(reservedatecolumn);
		table.getColumns().add(returndatecolumn);
		table.getColumns().add(duedatecolumn);
		
		TextField addid = new TextField();
		addid.setPromptText("bookid");
		addid.setMaxWidth(idcolumn.getPrefWidth());
		
		TextField addmemberid = new TextField();
		addmemberid.setPromptText("memberid");
		addmemberid.setMaxWidth(addmemberid.getPrefWidth());
		
		TextField addreservedate = new TextField();
		addreservedate.setPromptText("reserve date");
		addreservedate.setMaxWidth(addreservedate.getPrefWidth());
		
		TextField addreturndate = new TextField();
		addreturndate.setPromptText("return date");
		addreturndate.setMaxWidth(addreturndate.getPrefWidth());
		
		TextField addduedate = new TextField();
		addduedate.setPromptText("due date");
		addduedate.setMaxWidth(addduedate.getPrefWidth());
		
		
		Button add = new Button("Add");
		add.setOnAction(e-> {
			try {
				Date reservedate = new SimpleDateFormat("yyyy-MM-dd").parse(addreservedate.getText());
				Date returndate = new SimpleDateFormat("yyyy-MM-dd").parse(addreturndate.getText());
				Date dueedate = new SimpleDateFormat("yyyy-MM-dd").parse(addduedate.getText());
				
				books2member b2m = new books2member(Integer.valueOf(addid.getText()), Integer.valueOf(addid.getText()), reservedate, returndate, dueedate);
				list.add(b2m);
				insertData(b2m);
				addid.clear();
				addmemberid.clear();
				addreservedate.clear();
				addreturndate.clear();
				addduedate.clear();
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			ObservableList<books2member> selectrow = table.getSelectionModel().getSelectedItems();
			ArrayList<books2member> rows = new ArrayList<>(selectrow);
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
		
		hbox1.getChildren().addAll(addid, addmemberid, addreservedate, addreturndate, addduedate);
		hbox2.getChildren().addAll(add, delete, back);
		vbox.getChildren().addAll(title, table, hbox1, hbox2);
		bp.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		selection();
	}


	private void updateReturndate(int bookid, int memberID, Date returndate) {
		try {
			System.out.println("update books2member set returndate = '" +returndate+ "' where bookid = "+bookid+ "and memberID "+memberID);
			con.connectDB();
			con.ExecuteStatement("update requests2member set returndate = '" +returndate+ "' where bookid = "+bookid+ "and memberID "+memberID+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateDuedate(int bookid, int memberID, Date duedate) {
		try {
			System.out.println("update books2member set duedate = '" +duedate+ "' where bookid = "+bookid+ "and memberID "+memberID);
			con.connectDB();
			con.ExecuteStatement("update requests2member set duedate = '" +duedate+ "' where bookid = "+bookid+ "and memberID "+memberID+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateReservedate(int bookid, int memberID, Date reservedate) {
		try {
			System.out.println("update books2member set reservedate = '" +reservedate+ "' where bookid = "+bookid+ "and memberID "+memberID);
			con.connectDB();
			con.ExecuteStatement("update requests2member set reservedate = '" +reservedate+ "' where bookid = "+bookid+ "and memberID "+memberID+ ";");
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
	private void insertData(books2member b2m) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String reservedate = sdf.format(b2m.getReservedate());
			String returndate = sdf.format(b2m.getReturndate());
			String duedate = sdf.format(b2m.getDuedate());
		
			System.out.println("Insert into books2member(bookid, memberid, reservedate, returndate, duedate) values(" 
		            +b2m.getBookid()+", "+b2m.getMemberid()+", '"+reservedate+"', '"+returndate+ "', '"
					+duedate+"') ");
			con.connectDB();
			con.ExecuteStatement("Insert into books2member(bookid, memberid, reservedate, returndate, duedate) values(" 
		            +b2m.getBookid()+", "+b2m.getMemberid()+", '"+reservedate+"', '"+returndate+ "', '"
					+duedate+"'); ");
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
	private void delete(books2member row) {	
		try {
			System.out.println("delete from books2member where bookid= " +row.getBookid()+ "and memberid= "+row.getMemberid());
			con.connectDB();
			con.ExecuteStatement("delete from books2member where bookid= " +row.getBookid()+ "and memberid= "+row.getMemberid()+ ";");
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
		
		Button bid = new Button("Search Book ID");
		TextField txt1 = new TextField();
		txt1.setMaxSize(100, 20);
		
		bid.setOnAction(e->{
			ObservableList<books2member> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select * from books2member where bookid = "+txt1.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					Date reservedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3));
					Date returndate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4));
					Date dueedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5));
					
					l1.add(new books2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
							reservedate, returndate, dueedate));
				}
				table.setItems(l1);
			} catch (Exception e1) {
				e1.getStackTrace();
			}
		});
		
		Button mid = new Button("Search Member ID");
		TextField txt2 = new TextField();
		txt1.setMaxSize(100, 20);
		
		mid.setOnAction(e->{
			ObservableList<books2member> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select * from books2member where member = "+txt2.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					Date reservedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3));
					Date returndate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4));
					Date dueedate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5));
					
					l1.add(new books2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
							reservedate, returndate, dueedate));
				}
				table.setItems(l1);
			} catch (Exception e1) {
				e1.getStackTrace();
			}
		});
	
		
		HBox hbox1 = new HBox(15);
		HBox hbox2 = new HBox(15);
		hbox1.getChildren().addAll(bid, txt1);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(mid, txt2);
		hbox2.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox1, hbox2);
 	}
}

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

public class memberpage {
	
	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<Member> mem;
	private ObservableList<Member> memlist;
	TableView<Member> table = new TableView<Member>();
	
	public BorderPane member(Stage primaryStage) {
		mem = new ArrayList<>();
		
		try {
			getData();
			memlist = FXCollections.observableArrayList(mem);
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
		
		sql = "select memberID, name, phonenumber, maritalstatus, gender, SNN, branch, email, sponsorid from Member";
		Statement s = ConnectionDataBase.connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		
		while(rs.next()) {
			mem.add(new Member(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
					rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)), rs.getString(7), rs.getString(8) ,Integer.parseInt(rs.getString(9))));
		}
	}
	
	//method to set data in table and edit user interface
	private void table(Stage primaryStage) {
		
		HBox hbox1 = new HBox(15);
		HBox hbox2 = new HBox(15);
		HBox hbox3 = new HBox(15);
		
		
		Scene scene = new Scene(bp,750,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
		Label title = new Label("Member Table");
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
		
        
        table.setEditable(true);
        bp.setCenter(table);
        table.setMaxSize(400, 400);
        
        //Initialize id column 
		TableColumn<Member, Integer> idcolumn = new TableColumn<Member, Integer>("memberID");
		idcolumn.setCellValueFactory(new PropertyValueFactory<Member, Integer>("memberID"));
		
		//Initialize name column 
		TableColumn<Member, String> namecolumn = new TableColumn<Member, String>("name");
		namecolumn.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
		
		//select name to update it then call method to update it using id in mySQL
		namecolumn.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
		namecolumn.setOnEditCommit((CellEditEvent<Member, String> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			updateName(t.getRowValue().getMemberID(), t.getNewValue());
		});
		
		//Initialize phone number column 
		TableColumn<Member, Integer> numcolumn = new TableColumn<Member, Integer>("phonenumber");
		numcolumn.setCellValueFactory(new PropertyValueFactory<Member, Integer>("phonenumber"));
		
		//select phone number to update it then call method to update it using id in mySQL
		numcolumn.setCellFactory(TextFieldTableCell.<Member, Integer>forTableColumn(new IntegerStringConverter()));
		numcolumn.setOnEditCommit((CellEditEvent<Member, Integer> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhonenumber(t.getNewValue());
			updateNum(t.getRowValue().getMemberID(), t.getNewValue());
		});
		
		//Initialize marital status column 
		TableColumn<Member, String> maritalstatuscolumn = new TableColumn<Member, String>("maritalstatus");
		maritalstatuscolumn.setCellValueFactory(new PropertyValueFactory<Member, String>("maritalstatus"));
				
				
		//select marital status to update it then call method to update it using id in mySQL
		maritalstatuscolumn.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
		maritalstatuscolumn.setOnEditCommit((CellEditEvent<Member, String> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMaritalstatus(t.getNewValue());
			updateMaritalstatus(t.getRowValue().getMemberID(), t.getNewValue());
		});
		
		
		//Initialize gender column 
		TableColumn<Member, String> gendercolumn = new TableColumn<Member, String>("gender");
		gendercolumn.setCellValueFactory(new PropertyValueFactory<Member, String>("gender"));
						
						
		//select gender to update it then call method to update it using id in mySQL
		gendercolumn.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
		gendercolumn.setOnEditCommit((CellEditEvent<Member, String> t) -> {				
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGender(t.getNewValue());
			updateGender(t.getRowValue().getMemberID(), t.getNewValue());
		});
				
				
		//Initialize SNN column 
		TableColumn<Member, Integer> SNNcolumn = new TableColumn<Member, Integer>("SNN");
		SNNcolumn.setCellValueFactory(new PropertyValueFactory<Member, Integer>("SNN"));
				
		//select SNN to update it then call method to update it using id in mySQL
		SNNcolumn.setCellFactory(TextFieldTableCell.<Member, Integer>forTableColumn(new IntegerStringConverter()));
		SNNcolumn.setOnEditCommit((CellEditEvent<Member, Integer> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSNN(t.getNewValue());
			updateSNN(t.getRowValue().getMemberID(), t.getNewValue());
		});
		
		
		//Initialize branch column 
		TableColumn<Member, String> branchcolumn = new TableColumn<Member, String>("branch");
		branchcolumn.setCellValueFactory(new PropertyValueFactory<Member, String>("branch"));
						
						
		//select branch to update it then call method to update it using id in mySQL
		branchcolumn.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
		branchcolumn.setOnEditCommit((CellEditEvent<Member, String> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBranch(t.getNewValue());
			updateBranch(t.getRowValue().getMemberID(), t.getNewValue());
		});
				
				
		//Initialize email column 
		TableColumn<Member, String> emailcolumn = new TableColumn<Member, String>("email");
		emailcolumn.setCellValueFactory(new PropertyValueFactory<Member, String>("email"));
								
								
		//select email to update it then call method to update it using id in mySQL
		emailcolumn.setCellFactory(TextFieldTableCell.<Member>forTableColumn());
		emailcolumn.setOnEditCommit((CellEditEvent<Member, String> t) -> {				
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
			updateEmail(t.getRowValue().getMemberID(), t.getNewValue());
		});
						
						
		//Initialize sponsorid column 
		TableColumn<Member, Integer> sponsoridcolumn = new TableColumn<Member, Integer>("sponsorid");
		sponsoridcolumn.setCellValueFactory(new PropertyValueFactory<Member, Integer>("sponsorid"));
						
		//select sponsorid to update it then call method to update sponsorid using id in mySQL
		sponsoridcolumn.setCellFactory(TextFieldTableCell.<Member, Integer>forTableColumn(new IntegerStringConverter()));
		sponsoridcolumn.setOnEditCommit((CellEditEvent<Member, Integer> t) -> {
			((Member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSponsorid(t.getNewValue());
			updateSponsorid(t.getRowValue().getMemberID(), t.getNewValue());
		});
		
	
		//add columns in the table
		table.setItems(memlist);
		table.getColumns().add(idcolumn);
		table.getColumns().add(namecolumn);
		table.getColumns().add(numcolumn);
		table.getColumns().add(maritalstatuscolumn);
		table.getColumns().add(gendercolumn);
		table.getColumns().add(SNNcolumn);
		table.getColumns().add(branchcolumn);
		table.getColumns().add(emailcolumn);
		table.getColumns().add(sponsoridcolumn);
		
		TextField addid = new TextField();
		addid.setPromptText("publisherid");
		addid.setMaxWidth(idcolumn.getPrefWidth());
		
		TextField addname = new TextField();
		addname.setPromptText("name");
		addname.setMaxWidth(addname.getPrefWidth());
		
		TextField addnum = new TextField();
		addnum.setPromptText("phonenumber");
		addnum.setMaxWidth(addnum.getPrefWidth());
		
		TextField addmaritalstatus = new TextField();
		addmaritalstatus.setPromptText("maritalstatus");
		addmaritalstatus.setMaxWidth(addmaritalstatus.getPrefWidth());
		
		TextField addgender = new TextField();
		addgender.setPromptText("gender");
		addgender.setMaxWidth(addgender.getPrefWidth());
		
		TextField addSNN = new TextField();
		addSNN.setPromptText("SNN");
		addSNN.setMaxWidth(addSNN.getPrefWidth());
		
		TextField addbranch = new TextField();
		addbranch.setPromptText("branch");
		addbranch.setMaxWidth(addbranch.getPrefWidth());
		
		TextField addemail = new TextField();
		addemail.setPromptText("email");
		addemail.setMaxWidth(addemail.getPrefWidth());
		
		TextField addsid = new TextField();
		addsid.setPromptText("sponsorid");
		addsid.setMaxWidth(addsid.getPrefWidth());
		
		
		Button add = new Button("Add");
		add.setOnAction(e-> {
			Member m = new Member((Integer.valueOf(addid.getText())), addname.getText(), (Integer.valueOf(addnum.getText())), 
					addmaritalstatus.getText(), addgender.getText(), (Integer.valueOf(addSNN.getText())),
					addbranch.getText(), addemail.getText(), (Integer.valueOf(addsid.getText())));
			memlist.add(m);
			insertData(m);
			addid.clear();
			addname.clear();
			addnum.clear();
			addmaritalstatus.clear();
			addgender.clear();
			addSNN.clear();
			addbranch.clear();
			addemail.clear();
			addsid.clear();
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			ObservableList<Member> selectrow = table.getSelectionModel().getSelectedItems();
			ArrayList<Member> rows = new ArrayList<>(selectrow);
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
		title.setAlignment(Pos.TOP_CENTER);
		hbox1.getChildren().addAll(addid, addname, addnum, addmaritalstatus, addgender);
		hbox2.getChildren().addAll(addbranch, addSNN, addemail, addsid);
		hbox3.getChildren().addAll(add, delete, back);
		vbox.getChildren().addAll(title, table, hbox1, hbox2, hbox3);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		bp.setCenter(vbox);
		selection();
	}


	private void updateSponsorid(int memberid, Integer sid) {
		try {
			System.out.println("update Member set sponsorid = " +sid+ " where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set sponsorid = " +sid+ " where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateEmail(int memberid, String email) {
		try {
			System.out.println("update Member set email = '" +email+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set email = '" +email+ "' where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateBranch(int memberid, String branch) {
		try {
			System.out.println("update Member set branch = '" +branch+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set branch = '" +branch+ "' where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateSNN(int memberid, Integer SNN) {
		try {
			System.out.println("update Member set SNN = '" +SNN+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set SNN = " +SNN+ " where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void updateMaritalstatus(int memberid, String maritalstatus) {
		try {
			System.out.println("update Member set maritalstatus = '" +maritalstatus+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set maritalstatus = '" +maritalstatus+ "' where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	//method to update name using member id in mySQL project
	private void updateName(int memberid, String name) {
		try {
			System.out.println("update Member set name = '" +name+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set name = '" +name+ "' where memberID = "+memberid+ ";");
			ConnectionDataBase.connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	//method to update gender using member id in mySQL project
	private void updateGender(int memberid, String gender) {
		try {
			System.out.println("update Member set gender = '" +gender+ "' where memberID = "+memberid);
			con.connectDB();
			con.ExecuteStatement("update Member set gender = '" +gender+ "' where memberID = "+memberid+ ";");
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
		private void updateNum(int memberid, int num) {
			try {
				System.out.println("update Member set phonenumber = '" +num+ "' where memberID = "+memberid);
				con.connectDB();
				con.ExecuteStatement("update Member set phonenumber = " +num+ " where memberID = "+memberid+ ";");
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
	private void insertData(Member m) {
		try {
			System.out.println("Insert into Member(memberID, name, phonenumber, maritalstatus, gender, SNN, branch, email, sponsorid) values(" 
		            +m.getMemberID()+", "+m.getName()+", "+m.getPhonenumber()+", "+m.getMaritalstatus()+", "
					+m.getGender()+", "+m.getSNN()+", "+m.getBranch()+", "+m.getEmail()+", "+m.getSponsorid());
			con.connectDB();
			con.ExecuteStatement("Insert into Member(memberID, name, phonenumber, maritalstatus, gender, SNN, branch, email, sponsorid) values(" 
		            +m.getMemberID()+", '"+m.getName()+"', "+m.getPhonenumber()+", '"+m.getMaritalstatus()+"', '"
					+m.getGender()+"', "+m.getSNN()+", '"+m.getBranch()+"', '"+m.getEmail()+"', "+m.getSponsorid()+ ");");
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
	private void delete(Member row) {	
		try {
			System.out.println("delete from Member where memberID=" +row.getMemberID());
			con.connectDB();
			con.ExecuteStatement("delete from Member where memberID=" +row.getMemberID()+ ";");
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
		
		Button id = new Button("Search Member ID");
		TextField txt1 = new TextField();
		txt1.setMaxSize(100, 20);
		
		id.setOnAction(e->{
			ObservableList<Member> l1 = FXCollections.observableArrayList();
			try {
				String sql;
				con.connectDB();
				System.out.println("connected");
				
				sql = "select * from Member where memberID = "+txt1.getText();
				Statement s = ConnectionDataBase.connection.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					l1.add(new Member(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
							rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)), 
							rs.getString(7), rs.getString(8) ,Integer.parseInt(rs.getString(9))));
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

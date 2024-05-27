package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class req2memPage {
	
	    BorderPane bp = new BorderPane();
	    VBox vbox = new VBox(15);
		private ConnectionDataBase con = new ConnectionDataBase();
		private ArrayList<requests2member> requests2Members;
		private ObservableList<requests2member> requests2MembersList;
		TableView<requests2member> table = new TableView<>();

		public BorderPane requests2Members(Stage primaryStage) {
			requests2Members = new ArrayList<>();

			try {
				getData();
				requests2MembersList = FXCollections.observableArrayList(requests2Members);
				table(primaryStage);
				primaryStage.show();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return bp;
		}

		private void getData() throws ClassNotFoundException, SQLException {
			String sql;
			con.connectDB();
			System.out.println("connected");

			sql = "SELECT requestsid, memberid, dateofregistration, registrationexpirydate, note FROM requests2member";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				try {
					Date dateOfRegistration = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("dateofregistration"));
					Date registrationExpiryDate = new SimpleDateFormat("yyyy-MM-dd")
							.parse(rs.getString("registrationexpirydate"));

					requests2Members.add(new requests2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
							dateOfRegistration, registrationExpiryDate, rs.getString("note")));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		private void table(Stage primaryStage) {
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);
			

			Scene scene = new Scene(bp, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			Label title = new Label("Requests2Member Table");
			title.setTextFill(Color.BLACK);
			title.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));


			
			table.setEditable(true);
			bp.setCenter(table);

			// Initialize requestid column
			TableColumn<requests2member, Integer> requestIdColumn = new TableColumn<>("Request ID");
			requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestid"));

			// Initialize memberid column
			TableColumn<requests2member, Integer> memberIdColumn = new TableColumn<>("Member ID");
			memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberid"));

			// Initialize dateofregistration column
			TableColumn<requests2member, Date> dateOfRegistrationColumn = new TableColumn<>("Date of Registration");
			dateOfRegistrationColumn.setCellValueFactory(new PropertyValueFactory<>("dateofregistration"));
			
			//select date of registration to update it then call method to update it using id in mySQL
			dateOfRegistrationColumn.setCellFactory(TextFieldTableCell.<requests2member, Date>forTableColumn(new DateStringConverter()));
			dateOfRegistrationColumn.setOnEditCommit((CellEditEvent<requests2member, Date> t) -> {
				((requests2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateofregitration(t.getNewValue());
				updateDateofregistration(t.getRowValue().getRequestid(), t.getRowValue().getMemberid(), t.getNewValue());
			});

			// Initialize registrationexpirydate column
			TableColumn<requests2member, Date> registrationExpiryDateColumn = new TableColumn<>("Registration Expiry Date");
			registrationExpiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationexpirydate"));

			//select registration expiry date to update it then call method to update it using id in mySQL
			registrationExpiryDateColumn.setCellFactory(TextFieldTableCell.<requests2member, Date>forTableColumn(new DateStringConverter()));
			registrationExpiryDateColumn.setOnEditCommit((CellEditEvent<requests2member, Date> t) -> {
				((requests2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRegistrationexpirydate(t.getNewValue());
				updateRegistrationexpirationdate(t.getRowValue().getRequestid(), t.getRowValue().getMemberid(), t.getNewValue());
			});
			
			// Initialize note column
			TableColumn<requests2member, String> noteColumn = new TableColumn<>("Note");
			noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
			
			//select note to update it then call method to update it using id in mySQL
			noteColumn.setCellFactory(TextFieldTableCell.<requests2member>forTableColumn());
			noteColumn.setOnEditCommit((CellEditEvent<requests2member, String> t) -> {
				((requests2member) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNote(t.getNewValue());
				updateNote(t.getRowValue().getRequestid(), t.getRowValue().getMemberid(), t.getNewValue());
			});

			table.setItems(requests2MembersList);
			table.getColumns().add(requestIdColumn);
			table.getColumns().add(memberIdColumn);
			table.getColumns().add(dateOfRegistrationColumn);
			table.getColumns().add(registrationExpiryDateColumn);
			table.getColumns().add(noteColumn);

			TextField addRequestId = new TextField();
			addRequestId.setPromptText("Request ID");
			addRequestId.setMaxWidth(requestIdColumn.getPrefWidth());

			TextField addMemberId = new TextField();
			addMemberId.setPromptText("Member ID");
			addMemberId.setMaxWidth(memberIdColumn.getPrefWidth());

			TextField addDateOfRegistration = new TextField();
			addDateOfRegistration.setPromptText("Date of Registration");
			addDateOfRegistration.setMaxWidth(dateOfRegistrationColumn.getPrefWidth());

			TextField addRegistrationExpiryDate = new TextField();
			addRegistrationExpiryDate.setPromptText("Registration Expiry Date");
			addRegistrationExpiryDate.setMaxWidth(registrationExpiryDateColumn.getPrefWidth());

			TextField addNote = new TextField();
			addNote.setPromptText("Note");
			addNote.setMaxWidth(noteColumn.getPrefWidth());

			Button add = new Button("Add");
			add.setOnAction(e -> {
				try {
					Date dateOfRegistration = new SimpleDateFormat("yyyy-MM-dd").parse(addDateOfRegistration.getText());
					Date registrationExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(addRegistrationExpiryDate.getText());

					requests2member request2Member = new requests2member(Integer.valueOf(addRequestId.getText()),
							Integer.valueOf(addMemberId.getText()), dateOfRegistration, registrationExpiryDate,
							addNote.getText());

					requests2MembersList.add(request2Member);
					insertData(request2Member);

					addRequestId.clear();
					addMemberId.clear();
					addDateOfRegistration.clear();
					addRegistrationExpiryDate.clear();
					addNote.clear();
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			});

			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				ObservableList<requests2member> selectedRows = table.getSelectionModel().getSelectedItems();
				ArrayList<requests2member> rows = new ArrayList<>(selectedRows);
				rows.forEach(row -> {
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

			hbox1.getChildren().addAll(addRequestId, addMemberId, addDateOfRegistration, addRegistrationExpiryDate, addNote);
			hbox2.getChildren().addAll(add, delete, back);
			vbox.getChildren().addAll(title, table, hbox1, hbox2);
			bp.setCenter(vbox);;
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			selection();
		}

		private void updateDateofregistration(int requestid, int memberID, Date dateofregistration) {
			try {
				System.out.println("update requests2member set dateofregistration = '" +dateofregistration+ "' where requestid = "+requestid+ "and memberID "+memberID);
				con.connectDB();
				con.ExecuteStatement("update requests2member set dateofregistration = '" +dateofregistration+ "' where requestid = "+requestid+ "and memberID "+memberID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateNote(int requestid, int memberID, String note) {
			try {
				System.out.println("update requests2member set note = '" +note+ "' where requestid = "+requestid+ "and memberID "+memberID);
				con.connectDB();
				con.ExecuteStatement("update requests2member set note = '" +note+ "' where requestid = "+requestid+ "and memberID "+memberID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateRegistrationexpirationdate(int requestid, int memberID, Date registrationexpirationdate) {
			try {
				System.out.println("update requests2member set registrationexpirationdate = '" +registrationexpirationdate+ "' where requestid = "+requestid+ "and memberID "+memberID);
				con.connectDB();
				con.ExecuteStatement("update requests2member set registrationexpirationdate = '" +registrationexpirationdate+ "' where requestid = "+requestid+ "and memberID "+memberID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void insertData(requests2member request) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateOfRegistration = sdf.format(request.getDateofregitration());
				String registrationExpiryDate = sdf.format(request.getRegistrationexpirydate());

				System.out.println("Insert into requests2member(requestid, memberid, dateofregistration, "
						+ "registrationexpirydate, note) values(" + request.getRequestid() + ", " + request.getMemberid()
						+ ", '" + dateOfRegistration + "', '" + registrationExpiryDate + "', '" + request.getNote() + "')");

				con.connectDB();
				con.ExecuteStatement("Insert into requests2member(requestid, memberid, dateofregistration, "
						+ "registrationexpirydate, note) values(" + request.getRequestid() + ", " + request.getMemberid()
						+ ", '" + dateOfRegistration + "', '" + registrationExpiryDate + "', '" + request.getNote()
						+ "');");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void delete(requests2member row) {
			try {
				System.out.println("delete from requests2member where requestid=" + row.getRequestid() + ";");
				con.connectDB();
				con.ExecuteStatement("delete from requests2member where requestid=" + row.getRequestid() + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				System.out.println("This row cannot be deleted because it points to another row");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private void selection() {
			bp.setPadding(new Insets(50, 50, 50, 50));
			
			Button rid = new Button("Search Request ID");
			TextField txt1 = new TextField();
			txt1.setMaxSize(100, 20);
			
			rid.setOnAction(e->{
				ObservableList<requests2member> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from requests2member where requestsid = "+txt1.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						Date dateOfRegistration = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("dateofregistration"));
						Date registrationExpiryDate = new SimpleDateFormat("yyyy-MM-dd")
								.parse(rs.getString("registrationexpirydate"));

						l1.add(new requests2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
								dateOfRegistration, registrationExpiryDate, rs.getString("note")));
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
				ObservableList<requests2member> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from requests2member where requestsid = "+txt2.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						Date dateOfRegistration = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("dateofregistration"));
						Date registrationExpiryDate = new SimpleDateFormat("yyyy-MM-dd")
								.parse(rs.getString("registrationexpirydate"));

						l1.add(new requests2member(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)),
								dateOfRegistration, registrationExpiryDate, rs.getString("note")));
					}
					table.setItems(l1);
				} catch (Exception e1) {
					e1.getStackTrace();
				}
			});
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);
			hbox1.getChildren().addAll(rid, txt1);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.getChildren().addAll(mid, txt2);
			hbox2.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(hbox1, hbox2);
		}
	}
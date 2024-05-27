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
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class RequestsPage {
	
	    BorderPane bp = new BorderPane();
	    VBox vbox = new VBox(15);
		private ConnectionDataBase con = new ConnectionDataBase();
		private ArrayList<Requests> requests;
		private ObservableList<Requests> requestsList;
		TableView<Requests> table = new TableView<>();

		public BorderPane requests(Stage primaryStage) {
			requests = new ArrayList<>();

			try {
				getData();
				requestsList = FXCollections.observableArrayList(requests);
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

			sql = "SELECT requestsID, price, typeofrequest, employeeid FROM requests";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				requests.add(new Requests(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3),
						Integer.parseInt(rs.getString(4))));
				
			}
			
		}

		private void table(Stage primaryStage) {
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);
			

			Scene scene = new Scene(bp, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
			

			bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			Label title = new Label("Requests Table");
			title.setTextFill(Color.BLACK);
			title.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));

			
			table.setEditable(true);
			bp.setCenter(table);

			// Initialize requestsID column
			TableColumn<Requests, Integer> requestsIdColumn = new TableColumn<>("Requests ID");
			requestsIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestsID"));

			// Initialize price column
			TableColumn<Requests, Integer> priceColumn = new TableColumn<>("Price");
			priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			//select price to update it then call method to update it using id in mySQL
			priceColumn.setCellFactory(TextFieldTableCell.<Requests, Integer>forTableColumn(new IntegerStringConverter()));
			priceColumn.setOnEditCommit((CellEditEvent<Requests, Integer> t) -> {
				((Requests) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice(t.getNewValue());
				updatePrice(t.getRowValue().getRequestsID(), t.getNewValue());
			});

			// Initialize typeofrequests column
			TableColumn<Requests, String> typeofrequestsColumn = new TableColumn<>("Type of Requests");
			typeofrequestsColumn.setCellValueFactory(new PropertyValueFactory<>("typeofrequests"));
			
			//select typeofrequests to update it then call method to update it using id in mySQL
			typeofrequestsColumn.setCellFactory(TextFieldTableCell.<Requests>forTableColumn());
			typeofrequestsColumn.setOnEditCommit((CellEditEvent<Requests, String> t) -> {				
				((Requests) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTypeofrequests(t.getNewValue());
				updateTypeofrequests(t.getRowValue().getRequestsID(), t.getNewValue());
			});

			// Initialize employeeid column
			TableColumn<Requests, Integer> employeeIdColumn = new TableColumn<>("Employee ID");
			employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
			
			//select employeeid to update it then call method to update it using id in mySQL
			employeeIdColumn.setCellFactory(TextFieldTableCell.<Requests, Integer>forTableColumn(new IntegerStringConverter()));
			employeeIdColumn.setOnEditCommit((CellEditEvent<Requests, Integer> t) -> {
				((Requests) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmployeeid(t.getNewValue());
				updateEmployeeid(t.getRowValue().getRequestsID(), t.getNewValue());
			});

			table.setItems(requestsList);
			table.getColumns().add(requestsIdColumn);
			table.getColumns().add(priceColumn);
			table.getColumns().add(typeofrequestsColumn);
			table.getColumns().add(employeeIdColumn);

			TextField addRequestsId = new TextField();
			addRequestsId.setPromptText("Requests ID");
			addRequestsId.setMaxWidth(requestsIdColumn.getPrefWidth());

			TextField addPrice = new TextField();
			addPrice.setPromptText("Price");
			addPrice.setMaxWidth(priceColumn.getPrefWidth());

			TextField addTypeOfRequests = new TextField();
			addTypeOfRequests.setPromptText("Type of Requests");
			addTypeOfRequests.setMaxWidth(typeofrequestsColumn.getPrefWidth());

			TextField addEmployeeId = new TextField();
			addEmployeeId.setPromptText("Employee ID");
			addEmployeeId.setMaxWidth(employeeIdColumn.getPrefWidth());

			Button add = new Button("Add");
			add.setOnAction(e -> {
				Requests request = new Requests(Integer.valueOf(addRequestsId.getText()),
						Integer.valueOf(addPrice.getText()), addTypeOfRequests.getText(),
						Integer.valueOf(addEmployeeId.getText()));
				requestsList.add(request);
				insertData(request);
				addRequestsId.clear();
				addPrice.clear();
				addTypeOfRequests.clear();
				addEmployeeId.clear();
			});

			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				ObservableList<Requests> selectedRows = table.getSelectionModel().getSelectedItems();
				ArrayList<Requests> rows = new ArrayList<>(selectedRows);
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

			hbox1.getChildren().addAll(addRequestsId, addPrice, addTypeOfRequests, addEmployeeId);
			hbox2.getChildren().addAll(add, delete, back);
			vbox.getChildren().addAll(title, table, hbox1, hbox2);
			bp.setCenter(vbox);
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			selection();
		}

		private void updateTypeofrequests(int requestsID, String typeofrequests) {
			try {
				System.out.println("update requests set typeofrequests = '" + typeofrequests + "' where requestsID = " + requestsID);
				con.connectDB();
				con.ExecuteStatement("update requests set typeofrequests = '" + typeofrequests + "' where requestsID = " + requestsID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateEmployeeid(int requestsID, Integer employeeid) {
			try {
				System.out.println("update requests set employeeid = " + employeeid + " where requestsID = " + requestsID);
				con.connectDB();
				con.ExecuteStatement("update requests set employeeid = " + employeeid + " where requestsID = " + requestsID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updatePrice(int requestsId, int price) {
			try {
				System.out.println("update requests set price = " + price + " where requestsID = " + requestsId);
				con.connectDB();
				con.ExecuteStatement("update requests set price = " + price + " where requestsID = " + requestsId + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void insertData(Requests request) {
			try {
				System.out.println("Insert into requests(requestsID, price, typeofrequests, employeeid) values("
						+ request.getRequestsID() + ", " + request.getPrice() + ", '" + request.getTypeofrequests() + "', "
						+ request.getEmployeeid() + ")");
				con.connectDB();
				con.ExecuteStatement("Insert into requests(requestsID, price, typeofrequests, employeeid) values("
						+ request.getRequestsID() + ", " + request.getPrice() + ", '" + request.getTypeofrequests() + "', "
						+ request.getEmployeeid() + ");");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void delete(Requests row) {
			try {
				System.out.println("delete from requests where requestsID=" + row.getRequestsID() + ";");
				con.connectDB();
				con.ExecuteStatement("delete from requests where requestsID=" + row.getRequestsID() + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				System.out.println("This row cannot be deleted because it points to another row");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private void selection() {
			bp.setPadding(new Insets(50, 50, 50, 50));
			
			Button id = new Button("Search Request ID");
			TextField txt1 = new TextField();
			txt1.setMaxSize(100, 20);
			
			id.setOnAction(e->{
				ObservableList<Requests> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from Requests where requestsID = "+txt1.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						l1.add(new Requests(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2))
								, rs.getString(3),Integer.parseInt(rs.getString(4))));
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


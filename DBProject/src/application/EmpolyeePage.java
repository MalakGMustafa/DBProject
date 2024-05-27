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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
public class EmpolyeePage {
	
	BorderPane bp = new BorderPane();
	VBox vbox = new VBox(15);
	private ConnectionDataBase con = new ConnectionDataBase();
	private ArrayList<Employee> employee;
	private ObservableList<Employee> emplist;
	TableView<Employee> table = new TableView<Employee>();

	public BorderPane Employee(Stage primaryStage) {
		employee = new ArrayList<>();

			try {
				getData();
				emplist = FXCollections.observableArrayList(employee);
				table(primaryStage);
				primaryStage.show();
			}

			catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return bp;
		}

		// method to get data from table in mySQL
		private void getData() throws ClassNotFoundException, SQLException {
			String sql;
			con.connectDB();
			System.out.println("connected");

			sql = "select employeeID, name, salary, phonenumber, libraryid from Employee";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				int employeeId = Integer.parseInt(rs.getString(1));
				String employeeName = rs.getString(2);
				double employeeSalary = Double.parseDouble(rs.getString(3));
				int employeePhoneNumber = Integer.parseInt(rs.getString(4));
				int employeeLibraryId = Integer.parseInt(rs.getString(5));

				employee.add(
						new Employee(employeeId, employeeName, employeeSalary, employeePhoneNumber, employeeLibraryId));
				
			}
			
		}

		// method to set data in table and edit user interface
		private void table(Stage primaryStage) {
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);

			Scene scene = new Scene(bp, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			Label title = new Label("Employee Table");
			title.setTextFill(Color.BLACK);
			title.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));


			table.setEditable(true);
			bp.setCenter(table);
			// table.setMaxSize(750, 600);

			TableColumn<Employee, Integer> empidcolumn = new TableColumn<Employee, Integer>("employeeID");
			empidcolumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeID"));

			TableColumn<Employee, String> namecolumn = new TableColumn<Employee, String>("name");
			namecolumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));

			namecolumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
			namecolumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
				updateName(t.getRowValue().getEmployeeID(), t.getNewValue());
			});

			TableColumn<Employee, Double> salarycolumn = new TableColumn<Employee, Double>("salary");
			salarycolumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));

			salarycolumn.setCellFactory(TextFieldTableCell.<Employee, Double>forTableColumn(new DoubleStringConverter()));
			salarycolumn.setOnEditCommit((CellEditEvent<Employee, Double> t) -> {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
				updateSalary(t.getRowValue().getEmployeeID(), t.getNewValue());
			});

			TableColumn<Employee, Integer> phonecolumn = new TableColumn<Employee, Integer>("phonenumber");
			phonecolumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phonenumber"));
			
			phonecolumn.setCellFactory(TextFieldTableCell.<Employee, Integer>forTableColumn(new IntegerStringConverter()));
			phonecolumn.setOnEditCommit((CellEditEvent<Employee, Integer> t) -> {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhonenumber(t.getNewValue());
				updatePhone(t.getRowValue().getEmployeeID(), t.getNewValue());
			});

			TableColumn<Employee, Integer> libIdcolumn = new TableColumn<Employee, Integer>("libraryid");
			libIdcolumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("libraryid"));
			
			libIdcolumn.setCellFactory(TextFieldTableCell.<Employee, Integer>forTableColumn(new IntegerStringConverter()));
			libIdcolumn.setOnEditCommit((CellEditEvent<Employee, Integer> t) -> {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLibraryid(t.getNewValue());
				updateLibID(t.getRowValue().getEmployeeID(), t.getNewValue());
			});

			// add columns in the table
			table.setItems(emplist);
			table.getColumns().add(empidcolumn);
			table.getColumns().add(namecolumn);
			table.getColumns().add(salarycolumn);
			table.getColumns().add(phonecolumn);
			table.getColumns().add(libIdcolumn);
			
			TextField addid = new TextField();
			addid.setPromptText("Employee ID");
			addid.setMaxWidth(empidcolumn.getPrefWidth());

			TextField addname = new TextField();
			addname.setPromptText("name");
			addname.setMaxWidth(namecolumn.getPrefWidth());

			TextField addsalary = new TextField();
			addsalary.setPromptText("salary");
			addsalary.setMaxWidth(salarycolumn.getPrefWidth());

			TextField addphonenum = new TextField();
			addphonenum.setPromptText("Phone number");
			addphonenum.setMaxWidth(phonecolumn.getPrefWidth());

			TextField addlibID = new TextField();
			addlibID.setPromptText("Library ID");
			addlibID.setMaxWidth(libIdcolumn.getPrefWidth());

			Button add = new Button("Add");
			add.setOnAction(e -> {
				Employee emp = new Employee(Integer.valueOf(addid.getText()), addname.getText(),
						Double.valueOf(addsalary.getText()), Integer.valueOf(addphonenum.getText()),
						Integer.valueOf(addlibID.getText()));
				emplist.add(emp);
				insertData(emp);
				addid.clear();
				addname.clear();
				addsalary.clear();
				addphonenum.clear();
				addlibID.clear();
			});

			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				ObservableList<Employee> selectrow = table.getSelectionModel().getSelectedItems();
				ArrayList<Employee> rows = new ArrayList<>(selectrow);
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

			hbox1.getChildren().addAll(addid, addname, addsalary, addphonenum, addlibID);
			hbox2.getChildren().addAll(add, delete, back);
			vbox.getChildren().addAll(title, table, hbox1, hbox2);
			bp.setCenter(vbox);;
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			selection();
		}

		// method to update name using library id in mySQL project
		private void updateName(int EmployeeID, String name) {
			try {
				System.out.println("update Employee set name = '" + name + "' where employeeID = " + EmployeeID);
				con.connectDB();
				con.ExecuteStatement("update Employee set name = '" + name + "' where employeeID = " + EmployeeID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateSalary(int EmployeeID, double salary) {
			try {
				System.out.println("update Employee set salary = " + salary + " where EmployeeID = " + EmployeeID);
				con.connectDB();
				con.ExecuteStatement("update Employee set salary = " + salary + " where employeeID = " + EmployeeID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updatePhone(int EmployeeID, int phone) {
			try {
				System.out.println("update Employee set Phone number = '" + phone + "' where EmployeeID = " + EmployeeID);
				con.connectDB();
				con.ExecuteStatement("update Employee set phonenumber = " + phone + " where employeeID = " + EmployeeID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateLibID(int employeeID, int LibraryID) {
			try {
				System.out.println("update Employee set libraryID = " + LibraryID + " where EmployeeID = " + employeeID);
				con.connectDB();
				con.ExecuteStatement("update Employee set libraryid = " + LibraryID + " where employeeID = " + employeeID + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		// method to add data in mySQL project
		private void insertData(Employee emp) {
			try {
				System.out.println("Insert into Library(id, name, salary,phone number,) values(" + emp.getEmployeeID()
						+ ", " + emp.getName() + ", " + emp.getSalary() + ", " + emp.getPhonenumber());
				con.connectDB();
				con.ExecuteStatement("Insert into Library(id, name, salary,phone number,) values(" + emp.getEmployeeID()
						+ ", '" + emp.getName() + "', " + emp.getSalary() + ", " + emp.getPhonenumber()+ ");");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		// method to delete data in mySQL project
		private void delete(Employee row) {
			try {
				System.out.println("delete from Employee where employeeID=" + row.getEmployeeID() + ";");
				con.connectDB();
				con.ExecuteStatement("delete from Employee where employeeID=" + row.getEmployeeID() + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				System.out.println("This row cannot be deleted because it points to another row");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private void selection() {
			bp.setPadding(new Insets(50, 50, 50, 50));
			
			Button id = new Button("Search Employee ID");
			TextField txt1 = new TextField();
			txt1.setMaxSize(100, 20);
			
			id.setOnAction(e->{
				ObservableList<Employee> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from Employee where employeeID = "+txt1.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						int employeeId = Integer.parseInt(rs.getString(1));
						String employeeName = rs.getString(2);
						double employeeSalary = Double.parseDouble(rs.getString(3));
						int employeePhoneNumber = Integer.parseInt(rs.getString(4));
						int employeeLibraryId = Integer.parseInt(rs.getString(5));

						l1.add(
								new Employee(employeeId, employeeName, employeeSalary, employeePhoneNumber, employeeLibraryId));
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


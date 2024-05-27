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

public class SponsorPage {
	
	    BorderPane bp = new BorderPane();
	    VBox vbox = new VBox(15);
		private ConnectionDataBase con = new ConnectionDataBase();
		private ArrayList<Sponsor> sponsors;
		private ObservableList<Sponsor> sponsorList;
		TableView<Sponsor> table = new TableView<>();

		public BorderPane sponsors(Stage primaryStage) {
			sponsors = new ArrayList<>();

			try {
				getData();
				sponsorList = FXCollections.observableArrayList(sponsors);
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

			sql = "SELECT sponsorID, name, phonenumber, occupation, address, SNN, workplace, email FROM sponsor";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				sponsors.add(new Sponsor(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
						rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)), rs.getString(7),
						rs.getString(8)));

			}
		}

		private void table(Stage primaryStage) {
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);
			

			Scene scene = new Scene(bp, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			Label title = new Label("Sponsor Table");
			title.setTextFill(Color.BLACK);
			title.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));


			
			table.setEditable(true);
			bp.setCenter(table);

			// Initialize sponsorID column
			TableColumn<Sponsor, Integer> sponsorIdColumn = new TableColumn<>("sponsorID");
			sponsorIdColumn.setCellValueFactory(new PropertyValueFactory<>("sponsorID"));

			// Initialize name column
			TableColumn<Sponsor, String> nameColumn = new TableColumn<>("name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			
			//select name to update it then call method to update it using id in mySQL
			nameColumn.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
			nameColumn.setOnEditCommit((CellEditEvent<Sponsor, String> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
				updateName(t.getRowValue().getSponsorID(), t.getNewValue());
			});

			// Initialize phonenumber column
			TableColumn<Sponsor, Integer> phoneNumberColumn = new TableColumn<>("Phone Number");
			phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
			
			//select phone number to update it then call method to update it using id in mySQL
			phoneNumberColumn.setCellFactory(TextFieldTableCell.<Sponsor, Integer>forTableColumn(new IntegerStringConverter()));
			phoneNumberColumn.setOnEditCommit((CellEditEvent<Sponsor, Integer> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhonenumber(t.getNewValue());
				updateNum(t.getRowValue().getSponsorID(), t.getNewValue());
			});

			// Initialize occupation column
			TableColumn<Sponsor, String> occupationColumn = new TableColumn<>("Occupation");
			occupationColumn.setCellValueFactory(new PropertyValueFactory<>("occupation"));

			//select address to update it then call method to update it using id in mySQL
			occupationColumn.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
			occupationColumn.setOnEditCommit((CellEditEvent<Sponsor, String> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOccupation(t.getNewValue());
				updateOccupation(t.getRowValue().getSponsorID(), t.getNewValue());
			});
			
			// Initialize address column
			TableColumn<Sponsor, String> addressColumn = new TableColumn<>("Address");
			addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

			//select address to update it then call method to update it using id in mySQL
			addressColumn.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
			addressColumn.setOnEditCommit((CellEditEvent<Sponsor, String> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
				updateAddress(t.getRowValue().getSponsorID(), t.getNewValue());
			});
			
			// Initialize SNN column
			TableColumn<Sponsor, Integer> snnColumn = new TableColumn<>("SNN");
			snnColumn.setCellValueFactory(new PropertyValueFactory<>("SNN"));

			//select SNN to update it then call method to update it using id in mySQL
			snnColumn.setCellFactory(TextFieldTableCell.<Sponsor, Integer>forTableColumn(new IntegerStringConverter()));
			snnColumn.setOnEditCommit((CellEditEvent<Sponsor, Integer> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSNN(t.getNewValue());
				updateSNN(t.getRowValue().getSponsorID(), t.getNewValue());
			});
			
			
			// Initialize workplace column
			TableColumn<Sponsor, String> workplaceColumn = new TableColumn<>("Workplace");
			workplaceColumn.setCellValueFactory(new PropertyValueFactory<>("workplace"));
			
			//select work place to update it then call method to update it using id in mySQL
			workplaceColumn.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
			workplaceColumn.setOnEditCommit((CellEditEvent<Sponsor, String> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWorkplace(t.getNewValue());
				updateWorkPlace(t.getRowValue().getSponsorID(), t.getNewValue());
			});
			
			
			// Initialize email column
			TableColumn<Sponsor, String> emailColumn = new TableColumn<>("Email");
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

			//select email to update it then call method to update it using id in mySQL
			emailColumn.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
			emailColumn.setOnEditCommit((CellEditEvent<Sponsor, String> t) -> {
				((Sponsor) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
				updateEmail(t.getRowValue().getSponsorID(), t.getNewValue());
			});
			
			
			table.setItems(sponsorList);
			table.getColumns().add(sponsorIdColumn);
			table.getColumns().add(nameColumn);
			table.getColumns().add(phoneNumberColumn);
			table.getColumns().add(occupationColumn);
			table.getColumns().add(addressColumn);
			table.getColumns().add(snnColumn);
			table.getColumns().add(workplaceColumn);
			table.getColumns().add(emailColumn);

			TextField addSponsorId = new TextField();
			addSponsorId.setPromptText("Sponsor ID");
			addSponsorId.setMaxWidth(sponsorIdColumn.getPrefWidth());

			TextField addName = new TextField();
			addName.setPromptText("Name");
			addName.setMaxWidth(nameColumn.getPrefWidth());

			TextField addPhoneNumber = new TextField();
			addPhoneNumber.setPromptText("Phone Number");
			addPhoneNumber.setMaxWidth(phoneNumberColumn.getPrefWidth());

			TextField addOccupation = new TextField();
			addOccupation.setPromptText("Occupation");
			addOccupation.setMaxWidth(occupationColumn.getPrefWidth());

			TextField addAddress = new TextField();
			addAddress.setPromptText("Address");
			addAddress.setMaxWidth(addressColumn.getPrefWidth());

			TextField addSNN = new TextField();
			addSNN.setPromptText("SNN");
			addSNN.setMaxWidth(snnColumn.getPrefWidth());

			TextField addWorkplace = new TextField();
			addWorkplace.setPromptText("Workplace");
			addWorkplace.setMaxWidth(workplaceColumn.getPrefWidth());

			TextField addEmail = new TextField();
			addEmail.setPromptText("Email");
			addEmail.setMaxWidth(emailColumn.getPrefWidth());

			Button add = new Button("Add");
			add.setOnAction(e -> {
				Sponsor sponsor = new Sponsor(Integer.valueOf(addSponsorId.getText()), addName.getText(),
						Integer.valueOf(addPhoneNumber.getText()), addOccupation.getText(), addAddress.getText(),
						Integer.valueOf(addSNN.getText()), addWorkplace.getText(), addEmail.getText());

				sponsorList.add(sponsor);
				insertData(sponsor);

				addSponsorId.clear();
				addName.clear();
				addPhoneNumber.clear();
				addOccupation.clear();
				addAddress.clear();
				addSNN.clear();
				addWorkplace.clear();
				addEmail.clear();
			});

			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				ObservableList<Sponsor> selectedRows = table.getSelectionModel().getSelectedItems();
				ArrayList<Sponsor> rows = new ArrayList<>(selectedRows);
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

			hbox1.getChildren().addAll(addSponsorId, addName, addPhoneNumber, addOccupation, addAddress, addSNN,
					addWorkplace, addEmail);
			hbox2.getChildren().addAll(add, delete, back);
			vbox.getChildren().addAll(title, table, hbox1, hbox2);
			bp.setCenter(vbox);
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			selection();
		}
		
		private void updateEmail(int sponsorID, String email) {
			try {
				System.out.println("update Sponsor set email = '" +email+ "' where SponsorID = "+sponsorID);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set email = '" +email+ "' where sponsorID = "+sponsorID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateWorkPlace(int sponsorID, String workplace) {
			try {
				System.out.println("update Sponsor set workplace = '" +workplace+ "' where SponsorID = "+sponsorID);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set workplace = '" +workplace+ "' where sponsorID = "+sponsorID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateSNN(int sponsorID, Integer SNN) {
			try {
				System.out.println("update Sponsor set SNN = " +SNN+ " where SponsorID = "+sponsorID);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set SNN = " +SNN+ " where sponsorID = "+sponsorID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateAddress(int sponsorID, String address) {
			try {
				System.out.println("update Sponsor set address = '" +address+ "' where SponsorID = "+sponsorID);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set address = '" +address+ "' where sponsorID = "+sponsorID+ ";");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void updateOccupation(int sponsorID, String occupation) {
			try {
				System.out.println("update Sponsor set occupation = '" +occupation+ "' where SponsorID = "+sponsorID);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set occupation = '" +occupation+ "' where sponsorID = "+sponsorID+ ";");
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
		private void updateName(int sponsorid, String name) {
			try {
				System.out.println("update Sponsor set name = '" +name+ "' where SponsorID = "+sponsorid);
				con.connectDB();
				con.ExecuteStatement("update Sponsor set name = '" +name+ "' where sponsorID = "+sponsorid+ ";");
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
				System.out.println("update Member set phonenumber = " +num+ " where memberID = "+memberid);
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

		private void insertData(Sponsor sponsor) {
			try {
				System.out.println(
						"Insert into sponsor(sponsorID, name, phonenumber, occupation, address, SNN, workplace, email) "
								+ "values(" + sponsor.getSponsorID() + ", '" + sponsor.getName() + "', "
								+ sponsor.getPhonenumber() + ", '" + sponsor.getOccupation() + "', '" + sponsor.getAddress()
								+ "', " + sponsor.getSNN() + ", '" + sponsor.getWorkplace() + "', '" + sponsor.getEmail()
								+ "')");

				con.connectDB();
				con.ExecuteStatement(
						"Insert into sponsor(sponsorID, name, phonenumber, occupation, address, SNN, workplace, email) "
								+ "values(" + sponsor.getSponsorID() + ", '" + sponsor.getName() + "', "
								+ sponsor.getPhonenumber() + ", '" + sponsor.getOccupation() + "', '" + sponsor.getAddress()
								+ "', " + sponsor.getSNN() + ", '" + sponsor.getWorkplace() + "', '" + sponsor.getEmail()
								+ "');");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void delete(Sponsor row) {
			try {
				System.out.println("delete from sponsor where sponsorID=" + row.getSponsorID() + ";");
				con.connectDB();
				con.ExecuteStatement("delete from sponsor where sponsorID=" + row.getSponsorID() + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				System.out.println("This row cannot be deleted because it points to another row");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private void selection() {
			bp.setPadding(new Insets(50, 50, 50, 50));
			
			Button id = new Button("Search Sponsor ID");
			TextField txt1 = new TextField();
			txt1.setMaxSize(100, 20);
			
			id.setOnAction(e->{
				ObservableList<Sponsor> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from Sponsor where sponsorID = "+txt1.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						l1.add(new Sponsor(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
								rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6)), rs.getString(7),
								rs.getString(8)));
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


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

public class SubtitlePage {
	
	    BorderPane bp = new BorderPane();
	    VBox vbox = new VBox(15);
		private ConnectionDataBase con = new ConnectionDataBase();
		private ArrayList<Subtitle> subtitles;
		private ObservableList<Subtitle> subtitleList;
		TableView<Subtitle> table = new TableView<>();

		public BorderPane subtitles(Stage primaryStage) {
			subtitles = new ArrayList<>();

			try {
				getData();
				subtitleList = FXCollections.observableArrayList(subtitles);
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

			sql = "SELECT bookid, booksubtitle, publisherid FROM Subtitle";
			Statement s = ConnectionDataBase.connection.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				subtitles.add(new Subtitle(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(1))));
			}
		}

		private void table(Stage primaryStage) {
			
			HBox hbox1 = new HBox(15);
			HBox hbox2 = new HBox(15);
			

			Scene scene = new Scene(bp, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			bp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
			Label title = new Label("Subtitle Table");
			title.setTextFill(Color.BLACK);
			title.setFont(Font.font("Arial Narrow", FontWeight.BOLD, FontPosture.ITALIC, 20));


			
			table.setEditable(true);
			bp.setCenter(table);

			// Initialize bookid column
			TableColumn<Subtitle, Integer> bookIdColumn = new TableColumn<>("bookid");
			bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookid"));

			// Initialize booksubtitle column
			TableColumn<Subtitle, String> bookSubtitleColumn = new TableColumn<>("booksubtitle");
			bookSubtitleColumn.setCellValueFactory(new PropertyValueFactory<>("booksubtitle"));

			// Initialize publisherid column
			TableColumn<Subtitle, Integer> publisherIdColumn = new TableColumn<>("publisherid");
			publisherIdColumn.setCellValueFactory(new PropertyValueFactory<>("publisherid"));
			
			//select publisherid to update it then call method to update it using id in mySQL
			publisherIdColumn.setCellFactory(TextFieldTableCell.<Subtitle, Integer>forTableColumn(new IntegerStringConverter()));
			publisherIdColumn.setOnEditCommit((CellEditEvent<Subtitle, Integer> t) -> {
				((Subtitle) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPublisherid(t.getNewValue());
				updatePublisgerid(t.getRowValue().getBookid(), t.getRowValue().getBooksubtitle(), t.getNewValue());
			});

			table.setItems(subtitleList);
			table.getColumns().add(bookIdColumn);
			table.getColumns().add(bookSubtitleColumn);
			table.getColumns().add(publisherIdColumn);

			TextField addBookId = new TextField();
			addBookId.setPromptText("Book ID");
			addBookId.setMaxWidth(bookIdColumn.getPrefWidth());

			TextField addBookSubtitle = new TextField();
			addBookSubtitle.setPromptText("Book Subtitle");
			addBookSubtitle.setMaxWidth(bookSubtitleColumn.getPrefWidth());

			TextField addPublisherId = new TextField();
			addPublisherId.setPromptText("Publisher ID");
			addPublisherId.setMaxWidth(publisherIdColumn.getPrefWidth());

			Button add = new Button("Add");
			add.setOnAction(e -> {
				Subtitle subtitle = new Subtitle(Integer.valueOf(addBookId.getText()), addBookSubtitle.getText(),
						Integer.valueOf(addPublisherId.getText()));

				subtitleList.add(subtitle);
				insertData(subtitle);

				addBookId.clear();
				addBookSubtitle.clear();
				addPublisherId.clear();
			});

			Button delete = new Button("Delete");
			delete.setOnAction(e -> {
				ObservableList<Subtitle> selectedRows = table.getSelectionModel().getSelectedItems();
				ArrayList<Subtitle> rows = new ArrayList<>(selectedRows);
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

			hbox1.getChildren().addAll(addBookId, addBookSubtitle, addPublisherId);
			hbox2.getChildren().addAll(add, delete, back);
			vbox.getChildren().addAll(title, table, hbox1, hbox2);
			bp.setCenter(vbox);
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			selection();
		}
		
		//method to update publisherid using publisher id in mySQL project
		private void updatePublisgerid(int bookid, String subtitle, int publisherid) {
			try {
				System.out.println("update Subtitle set publisherid = " +publisherid+ " where bookID = "+bookid+" and "+subtitle);
				con.connectDB();
				con.ExecuteStatement("update Subtitle set publisherid = " +publisherid+ " where bookID = "+bookid+" and "+subtitle+ "';");
				ConnectionDataBase.connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void insertData(Subtitle subtitle) {
			try {
				System.out.println("Insert into subtitle(bookid, booksubtitle, publisherid) values(" + subtitle.getBookid()
						+ ", '" + subtitle.getBooksubtitle() + "', " + subtitle.getPublisherid() + ")");

				con.connectDB();
				con.ExecuteStatement(
						"Insert into subtitle(bookid, booksubtitle, publisherid) values(" + subtitle.getBookid() + ", '"
								+ subtitle.getBooksubtitle() + "', " + subtitle.getPublisherid() + ");");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private void delete(Subtitle row) {
			try {
				System.out.println("delete from subtitle where bookid=" + row.getBookid() + ";");
				con.connectDB();
				con.ExecuteStatement("delete from subtitle where bookid=" + row.getBookid() + ";");
				ConnectionDataBase.connection.close();
			} catch (SQLException e) {
				System.out.println("This row cannot be deleted because it points to another row");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private void selection() {
			bp.setPadding(new Insets(50, 50, 50, 50));
			
			Button id = new Button("Search Subtitle ID");
			TextField txt1 = new TextField();
			txt1.setMaxSize(100, 20);
			
			id.setOnAction(e->{
				ObservableList<Subtitle> l1 = FXCollections.observableArrayList();
				try {
					String sql;
					con.connectDB();
					System.out.println("connected");
					
					sql = "select * from Subtitle where subtitleID = "+txt1.getText();
					Statement s = ConnectionDataBase.connection.createStatement();
					ResultSet rs = s.executeQuery(sql);
					
					while(rs.next()) {
						l1.add(new Subtitle(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(1))));
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


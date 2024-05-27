package application;
	
import java.text.ParseException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	BorderPane root = new BorderPane();
	HBox hbox1 = new HBox(15);
	HBox hbox2 = new HBox(15);
	VBox vbox = new VBox(15);
	private String userName = "Manager";
	private String pass = "1234";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene = new Scene(root,750,420);
			
			Image image = new Image("libraray_photo.jpeg");
			ImageView imageview = new ImageView(image);
			root.getChildren().addAll(imageview);
			
			Label user = new Label("Username");
			user.setTextFill(Color.WHITE);
			user.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
			
			Label password = new Label("Password");
			password.setTextFill(Color.WHITE);
			password.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
			
			Label message = new Label("Incorrect user name or password");
			message.setTextFill(Color.RED);
			message.setFont(Font.font("Arial Narrow",FontWeight.BOLD ,FontPosture.ITALIC, 20));
			vbox.getChildren().add(message);
			message.setVisible(false);
			
			TextField txt1 = new TextField();
			TextField txt2 = new TextField();
			
			Button login = new Button("LogIn");
			
			hbox1.getChildren().addAll(user, txt1);
			hbox2.getChildren().addAll(password, txt2);
			vbox.getChildren().addAll(hbox1, hbox2, login);
			root.setCenter(vbox);
			vbox.setAlignment(Pos.CENTER);
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		//	Class.forName("com.mysql.cj.jdbc.Driver");
			login.setOnAction(e->{
				if(txt1.getText().equals(userName) && txt2.getText().equals(pass)) {
					actions(primaryStage);
					message.setVisible(false);
				}
				
				else 
					message.setVisible(true);
				
				
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actions(Stage primaryStage) {
		HBox hbox1 = new HBox(15);
		HBox hbox2 = new HBox(15);
		
		Button l = new Button("Library");
		Button b = new Button("Books");
		Button m = new Button("Member");
		Button em = new Button("Employee");
		Button sp = new Button("Sponsor");
		Button r = new Button("Requests");
		Button p = new Button("Publisher");
		Button b2m = new Button("Books2Member");
		Button r2m = new Button("Requests2Member");
		Button st = new Button("SubtitleBooks");
		
		hbox1.getChildren().addAll(l, b, p, st, em);
		hbox2.getChildren().addAll(m, sp, r, b2m, r2m);
		
		l.setOnAction(e-> {
			libpage lib = new libpage();
			lib.library(primaryStage);
		});
		
		m.setOnAction(e-> {
			memberpage mp = new memberpage();
			mp.member(primaryStage);
		});
		
		st.setOnAction(e-> {
			SubtitlePage stp = new SubtitlePage();
			stp.subtitles(primaryStage);
		});
		
		sp.setOnAction(e-> {
			SponsorPage spp = new SponsorPage();
			spp.sponsors(primaryStage);
		});
		
		p.setOnAction(e-> {
			Publisherpage pp= new Publisherpage();
			pp.publisher(primaryStage);
		});
		
		r2m.setOnAction(e-> {
			req2memPage r2mp = new req2memPage();
			r2mp.requests2Members(primaryStage);
		});
		
		r.setOnAction(e-> {
			RequestsPage rp = new RequestsPage();
			rp.requests(primaryStage);
		});
		
		em.setOnAction(e-> {
			libpage lib = new libpage();
			lib.library(primaryStage);
		});
		
		b2m.setOnAction(e-> {
			try {
				Books2memPage b2mp = new Books2memPage();
				b2mp.books2member(primaryStage);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		
		b.setOnAction(e-> {
			booksPage bp = new booksPage();
			bp.books(primaryStage);
		});
		
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox1, hbox2);
	}
	
	public static void main(String[] args) {
		launch(args);  
	}
}

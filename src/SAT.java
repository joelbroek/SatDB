import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import LoginPage;
import LoginPageDelegate;
import DatabaseAccess;

public class SAT extends Application implements LoginPageDelegate {
  private DatabaseAccess DBAccess;
  private LoginPage LoginPage = null;


  public SAT() {
    DBAccess = new DatabaseAccess();
  }

  private void start() {
		LoginPage = new LoginPage();
		LoginPage.showFrame(this);
	}

  public void login(String username, String password) throws IOException {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
      LoginPage.dispose();
			controller.setHandler(dbHandler);
			launch();
    }
    else{
      LoginPage.handleLoginFailed();

			if (LoginPage.hasReachedMaxLoginAttempts()) {
				LoginPage.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

  public static void main(String args[]) {
		SatDB SatDB = new SAT();
		SAT.start();
	}

}
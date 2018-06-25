/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubapplication;

import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Club.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author irondini
 */
public class ApplicationMain extends Application {

    private void load() {
        Club club = null;
        try {
            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            club = (Club) in.readObject();
            for (Member m : club.getMembers()) {
                m.setChecked(new SimpleBooleanProperty(false));
            }
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("Club class not found");
            c.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (club == null) {
            this.map.put("CRUDL", CRUDL.CREATE);
        } else {
            this.myClub = club;
            this.map.put("CRUDL", CRUDL.LIST);
        }
        return;
    }
    public static Club myClub;
    public static Map<String, Object> map = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        this.load();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);

        FXMLDocumentController controller = loader.getController();

        stage.show();
        //controller.initialize();
    }

    @Override
    public void stop() {

        try {
            FileOutputStream fileOut = new FileOutputStream("test.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myClub);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

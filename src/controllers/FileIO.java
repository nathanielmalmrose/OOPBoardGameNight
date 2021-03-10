package controllers;

import models.Player;

import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public static void saveList(ArrayList<Player> player) {
        try {
            FileOutputStream fos = new FileOutputStream("player");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static ArrayList<Player> loadList() {
        ArrayList<Player> player = new ArrayList<>();
        try {
            File file = new File("player");
            if (file.exists()) {

                FileInputStream fis = new FileInputStream("player");
                ObjectInputStream ois = new ObjectInputStream(fis);

                player = (ArrayList) ois.readObject();

                ois.close();
                fis.close();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("List not found.");
            c.printStackTrace();
        }
        return player;
    }
}

package main;
import java.io.*;
import java.util.ArrayList;

public class FileWrapper {

    public static ArrayList<String> readFile(String path) {
        ArrayList<String> contents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Invalid File Provided");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contents;
    }

    public static boolean writeFile(String path, ArrayList<String> contents) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : contents) {
                bw.write(line);
                bw.newLine();
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Invalid File Provided");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean canReadFile(String path) {
        File file = new File(path);
        return file.exists() && file.canRead();
    }

    public static boolean canWriteFile(String path) {
        File file = new File(path);
        return file.exists() && file.canWrite();
    }

}

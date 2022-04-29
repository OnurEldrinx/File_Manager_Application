import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String redColorCode = "\033[0;31m";
        String resetColorCode = "\033[0m";

        String consoleMenu = """
                \n***** Welcome to Java File Manager *****
                1- Select a file
                2- Display content
                3- Display file statistics
                4- Copy to another directory
                5- Exit
                """;


        String filePath = null;
        File file;
        TxtReader txtReader = null;

        Scanner scanner = new Scanner(System.in);

        int menuChoice;



        while (true){

            System.out.println(consoleMenu);
            System.out.print("\nYour Choice:");

            try {

                menuChoice = scanner.nextInt();
                scanner.nextLine(); //dummy

                if(menuChoice == 5){

                    System.out.println("\n\nGOODBYE");
                    break;

                }

                if(!(menuChoice>=1 && menuChoice<=5)){

                    continue;

                }

                switch (menuChoice){

                    case 1:
                        System.out.print("\nEnter the file path: ");
                        filePath = scanner.nextLine();
                        System.out.print("\nSelected file: " + filePath + "\n");
                        file = new File(filePath);
                        txtReader = new TxtReader(file);
                        break;

                    case 2:

                        if(filePath == null){

                            throw new NoFileSelectedException("No file has been selected!");

                        }else{

                            if (txtReader != null) {
                                txtReader.PrintContent();
                            }

                        }

                        break;
                    case 3:

                        if(filePath == null){

                            throw new NoFileSelectedException("No file has been selected!");

                        }else{

                            if (txtReader != null) {
                                txtReader.displayStatistics();
                            }

                        }

                        break;
                    case 4:

                        if(filePath == null){

                            throw new NoFileSelectedException("No file has been selected!");

                        }else {

                            System.out.print("\nEnter destination path: ");
                            String d = scanner.nextLine();

                            if (txtReader != null) {
                                txtReader.CopyToDirectory(d);
                            }


                        }
                        break;
                }




            }catch (InputMismatchException e){

                System.out.println(redColorCode + "Selection must be a number!" + resetColorCode);
                scanner.nextLine(); //dummy input for the string line above.

            } catch (NoFileSelectedException e) {

                System.out.println(redColorCode + e.getMessage() + resetColorCode);

            }


        }





    }
}

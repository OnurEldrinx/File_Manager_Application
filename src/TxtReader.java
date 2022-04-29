import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class TxtReader {

    private File file;
    private final ArrayList<String> fileData;

    private static final String redColorCode = "\033[0;31m";
    private static final String resetColorCode = "\033[0m";

    public TxtReader(File file){

        this.file = file;
        this.fileData = new ArrayList<>();

        String extension = "unknown";

        if (file.getName().split("\\.").length == 2){

            extension = file.getName().split("\\.")[1];

        }


        Scanner fileReader;


        if (!Objects.equals(extension, "txt")){

            try {
                throw new InvalidExtensionException(redColorCode + "Invalid File Extension" + resetColorCode);
            } catch (InvalidExtensionException e) {
                System.out.println(e.getMessage());
            }



        }else{

            try{

                fileReader = new Scanner(file);

                while (fileReader.hasNextLine()){

                    String l = fileReader.nextLine();

                    if(!l.equals("")){
                        fileData.add(l);
                    }

                }

            } catch (FileNotFoundException e) {
                System.out.println(redColorCode + e.getMessage() + resetColorCode);
            }

        }

    }

    public void PrintContent(){

        if(fileData.size() != 0) {
            System.out.println("--------------------------------------------------------------------------------------------");
            for (String line : fileData) {

                System.out.println(line);

            }
            System.out.println("--------------------------------------------------------------------------------------------");
        }
    }

    public void displayStatistics(){

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] letters = alphabet.toCharArray();
        char[] uppercaseLetters = alphabet.toUpperCase().toCharArray();
        int[] frequencies = new int[letters.length];
        Arrays.fill(frequencies,0);

        char[] lineChars;

        for (int i=0;i<letters.length;i++){

            for (int j=0;j<fileData.size();j++){

                lineChars = fileData.get(j).toCharArray();

                for (int k=0;k<lineChars.length;k++){

                    if(lineChars[k] == letters[i] || lineChars[k] == uppercaseLetters[i]){

                        frequencies[i]++;

                    }

                }


            }

        }

        int characterCounter = 0;


        System.out.println("*********************************************************************************************");


        for (int i=0;i< letters.length;i++){

            System.out.println("Frequency of [ " + letters[i] + " ] : " + frequencies[i]);
            characterCounter += frequencies[i];

        }
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Total Character Count : " + characterCounter);
        System.out.println("Line Count : " + fileData.size());
        System.out.println("*********************************************************************************************");

    }

    public void CopyToDirectory(String destination){


        if(!destination.contains(".txt")){

            destination += "\\copiedFile.txt";

        }

        try {

            File copy = new File(destination);

            if(copy.getParentFile().mkdirs()){

                copy.createNewFile();

            }

            Path original = Paths.get(file.getPath());
            Path copied = Paths.get(copy.getPath());

            Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("*********************************************************************************************");
            System.out.println("Successfully Copied");
            System.out.println("*********************************************************************************************");

        } catch (IOException e) {
            System.out.println(redColorCode + e.getMessage() + resetColorCode);
        }




    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}



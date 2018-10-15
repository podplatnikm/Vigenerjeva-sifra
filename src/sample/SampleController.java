package sample;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class SampleController {

    private static final String ABECEDA = "abcčdefghijklmnoprsštuvzžxywq ";
    private static final char POLJE_ABECEDA[][] = napolniPolje();
    private static final HashMap<Character, Integer> MAPA_POLJA = napolniMapo();

    public JFXTextField tfAbeceda;
    public JFXTextArea taVnosBesedila;
    public JFXTextField tfKljuc;
    public JFXTextArea taIzhodBesedila;


    private File datoteka;
    private final FileChooser fileChooserLoad = new FileChooser();
    private final FileChooser fileChooserSave = new FileChooser();

    public void initialize(){
        tfAbeceda.setText("'"+ABECEDA+"'");
    }

    public void odperiDatoteko(ActionEvent actionEvent) throws FileNotFoundException {
        fileChooserLoad.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT datoteka", "*.txt")
        );
        datoteka = fileChooserLoad.showOpenDialog(new Stage());
        if(datoteka.isFile()){
            napolniVnosnoBesedilo();
        }
    }

    private void napolniVnosnoBesedilo() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(datoteka));
        StringBuilder tekstDatoteke = new StringBuilder();
        while (scanner.hasNextLine()){
            tekstDatoteke.append(scanner.nextLine());
        }
        scanner.close();
        taVnosBesedila.setText(tekstDatoteke.toString());
    }

    private static HashMap<Character,Integer> napolniMapo() {
        HashMap<Character, Integer> mapa = new HashMap<>();
        for(int i = 0; i < ABECEDA.length(); i++){
            mapa.put(ABECEDA.charAt(i), i);
        }
        return mapa;
    }

    private static char[][] napolniPolje() {
        String tempAbeceda = ABECEDA;
        int dolzinaAbecede = tempAbeceda.length();

        char poljeAbecede[][] = new char[dolzinaAbecede][dolzinaAbecede];
        for(int i = 0; i < dolzinaAbecede; i++){
            for(int j = 0; j < dolzinaAbecede; j++){
                poljeAbecede[i][j] = tempAbeceda.charAt(j);
            }
            char charZaPremik = tempAbeceda.charAt(0);
            tempAbeceda = tempAbeceda.substring(1) + charZaPremik;
        }

        return poljeAbecede;
    }


    public void sifriraj(ActionEvent actionEvent) {

        /*
         * Formatiranje ključa v tok ključev
         */
        String kljuc;
        if(tfKljuc.getText().equals("")){
            kljuc = "miha";
        }else{
            kljuc = tfKljuc.getText();
        }
        String tokKljuca = pridobiTokKljuca(kljuc, taVnosBesedila.getText());

        //Šifriranje
        StringBuilder sifriranTekst = new StringBuilder();
        try {
            String formatiranTekst = taVnosBesedila.getText().toLowerCase();
            String formatiranTokKljuca = tokKljuca.toLowerCase();

            for (int i = 0; i < formatiranTekst.length(); i++) {

                //Šifriranje števil
                if(Character.isDigit(formatiranTekst.charAt(i))){
                    int stevilo = Character.getNumericValue(formatiranTekst.charAt(i));
                    int steviloInKljuc = stevilo + kljuc.length() % 10;
                    String zadnjaStevka = Integer.toString(steviloInKljuc % 10);
                    sifriranTekst.append(zadnjaStevka);
                    continue;
                }

                //(Šifriranje) neznanih karakterjev
                int crkaTeksta;
                if(MAPA_POLJA.containsKey(formatiranTekst.charAt(i))){ //Če je ta karakter v abecedi
                    crkaTeksta = MAPA_POLJA.get(formatiranTekst.charAt(i));
                }else{ //Čene ga samo prepiše v končni tekst
                    sifriranTekst.append(formatiranTekst.charAt(i));
                    continue;
                }

                //Šifriranje karakterjiev, ki so v abecedi
                int crkaKljuca = MAPA_POLJA.get(formatiranTokKljuca.charAt(i));
                sifriranTekst.append(POLJE_ABECEDA[crkaTeksta][crkaKljuca]);


            }
            taIzhodBesedila.setText(sifriranTekst.toString());
        }catch (Exception e){
            System.out.println("NAPAKA: "+e.getMessage());
        }
    }



    public void desifriraj(ActionEvent actionEvent) {
        // System.out.println(Arrays.deepToString(POLJE_ABECEDA).replace("], ", "]\n").replace("[[","["));

        //Formatiranje ključa v tok ključev
        String kljuc;
        if(tfKljuc.getText().equals("")){
            kljuc = "miha";
        }else{
            kljuc = tfKljuc.getText();
        }
        String tokKljuca = pridobiTokKljuca(kljuc, taVnosBesedila.getText());

        //Šifriranje
        StringBuilder sifriranTekst = new StringBuilder();
        try {
            String formatiranTekst = taVnosBesedila.getText().toLowerCase();
            String formatiranTokKljuca = tokKljuca.toLowerCase();

            for (int i = 0; i < formatiranTekst.length(); i++) {

                //Šifriranje števil
                if(Character.isDigit(formatiranTekst.charAt(i))){
                    int stevilo = Character.getNumericValue(formatiranTekst.charAt(i)) + 10;
                    int steviloInKljuc = stevilo - kljuc.length() % 10;
                    String zadnjaStevka = Integer.toString(steviloInKljuc % 10);
                    sifriranTekst.append(zadnjaStevka);
                    continue;
                }

                //Poskrbino za overflow (IndexOutOfBounds)
                int crka = 30 - MAPA_POLJA.get(formatiranTokKljuca.charAt(i));
                if( crka == 30){
                    crka = 0;
                }

                //Šifriranje karakterjiev, ki so v abecedi
                //int crkaKljuca = MAPA_POLJA.get(formatiranTekst.charAt(i));
                //sifriranTekst.append(POLJE_ABECEDA[crkaKljuca][crka]);

                int crkaKljuca;
                if(MAPA_POLJA.containsKey(formatiranTekst.charAt(i))){
                    crkaKljuca = MAPA_POLJA.get(formatiranTekst.charAt(i));
                }else{
                    sifriranTekst.append(formatiranTekst.charAt(i));
                    continue;
                }
                sifriranTekst.append(POLJE_ABECEDA[crkaKljuca][crka]);
            }
            taIzhodBesedila.setText(sifriranTekst.toString());
        }catch (Exception e){
            System.out.println("NAPAKA: "+e.getMessage());
        }


    }


    private String pridobiTokKljuca(String kljuc, String text) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0, j = 0; i < text.length(); ++i, ++j){
            if(j == kljuc.length())
                j = 0;
            sb.append(kljuc.charAt(j));
        }
        return sb.toString();
    }

    /*public void shraniBesedilo(ActionEvent actionEvent) throws IOException {
        fileChooserSave.setTitle("Shrani izhodno besedilo");
        fileChooserSave.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT datoteka", "*.txt")
        );
        File file = fileChooserSave.showSaveDialog(new Stage());
        if(file != null){
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.write(taIzhodBesedila.getText());
            }catch (Exception e){
                System.out.println("NAPAKA: "+e.getMessage());
                e.printStackTrace();
            }finally {
                if(fileWriter != null){
                    fileWriter.close();
                }

            }
        }

    }*/
}

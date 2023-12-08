package com.example.discrete_cosine_transform_2d;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class mainController {

    File textFile;

    @FXML
    private Label leftT;
    @FXML
    private Label rightT;

    @FXML
    protected void openButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your wav file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        fileChooser.setInitialDirectory(new File("C:\\Users\\User\\Downloads\\project-3-samples\\MM\\Q2\\"));

        textFile = fileChooser.showOpenDialog(null);

        if(textFile != null){

            BufferedReader reader = new BufferedReader(new FileReader(textFile));
            int n = Integer.parseInt(reader.readLine());
            System.out.println(n);

            int[][] arr = new int[n][n];

            //reading the 2d array from the text file
            String line = reader.readLine();
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++) {
                    String[] stringNums = line.split(" ");
                    arr[i][j] = Integer.parseInt(stringNums[j]);
                }
                line = reader.readLine();
            }
            int[][] rightArr = TransposedMatrix(arr, n);

            double[][] DCT_array = DCT(arr, n);

            String outputStr = "";
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    arr[i][j] = (int) Math.round(DCT_array[i][j]);
                    outputStr = outputStr + arr[i][j] + "  |  ";
                }
                outputStr = outputStr + "\n";
            }

            leftT.setText(outputStr);

            //do the collumn first
            double[][] DCT_array_2 = DCT(rightArr, n);
            DCT_array_2 = TransposedDoubleMatrix(DCT_array_2, n);


            outputStr = "";
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    rightArr[i][j] = (int) Math.round(DCT_array_2[i][j]);
                    outputStr = outputStr + rightArr[i][j] + "  |  ";
                }
                outputStr = outputStr + "\n";
            }


            rightT.setText(outputStr);

        }
    }

    public static int[][] TransposedMatrix(int[][] arr, int n) {
        int[][] newArr = new int[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                newArr[i][j] = arr[j][i];
            }
        }
        return newArr;
    }

    public static double[][] TransposedDoubleMatrix(double[][] arr, int n) {
        double[][] newArr = new double[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                newArr[i][j] = arr[j][i];
            }
        }
        return newArr;
    }

    public static double[][] DCT(int[][] arr, int n){
        double[][] DCTarr = new double[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){

                double a = 0;

                if(i == 0){
                    a = 1/Math.sqrt(n);
                } else {
                    a = (Math.sqrt(2))/(Math.sqrt(n));
                }

                double aj = 0;
                if(j == 0){
                    aj = 1/Math.sqrt(n);
                } else {
                    aj = (Math.sqrt(2))/(Math.sqrt(n));
                }

                double sum = 0;
                for(int b=0; b<n; b++){
                    for(int c=0; c<n; c++){
                        sum += arr[b][c] * Math.cos(((2.0 * b+1) * i * Math.PI) / (2.0 * n) ) *
                                Math.cos(((2.0 * c+1) * j * Math.PI) / (2.0 * n) );
                    }
                }

                DCTarr[i][j] = a * aj * sum;
            }
        }

        return DCTarr;
    }



}
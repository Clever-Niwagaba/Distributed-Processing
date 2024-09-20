package Distributed.Matrix;

import java.io.Serializable;

public class InfoBlock implements Serializable {
    int [] row;
    int [][] matrix;
    int row_number;
    int [] results;
    public InfoBlock(int [] row, int [][]matrix, int row_number){
        this.row = row;
        this.matrix = matrix;
        this.row_number = row_number;
    }

    public InfoBlock(int [] results, int row_number){
        this.results = results;
        this.row_number = row_number;

    }
}

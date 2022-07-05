package com.tpa.lab.domain;

import java.util.List;

public class StateMatrix {
    public static final int ROWS = 8;
    private int[][] matrix;

    // TODO: Tamanho das linhas precisa ser flexivel
    public StateMatrix(int column) {
        this.matrix = new int[ROWS][column];
        this.startMatrix();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    private void startMatrix() {
        for (int i = 0; i < ROWS; i++){
            this.setInRowAndColumn(i, 0, 0);
        }
    }

    public void setInRowAndColumn(int row, int column, int weight) {
        this.matrix[row][column] = weight;
    }

    public int getInRowAndColumn(int row, int column) {
        return this.matrix[row][column];
    }

    public boolean containsValueInRowAndColumn(int row, int column) {
        try {
            this.getInRowAndColumn(row, column);
            return true;
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return false;
        }
    }
}

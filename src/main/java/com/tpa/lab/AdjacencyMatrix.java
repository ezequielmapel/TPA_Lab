package com.tpa.lab;

import com.tpa.lab.domain.StateMatrix;

public class AdjacencyMatrix {
    private int dimensions;
    private int[][] adjacencyMatrix;

    private StateMatrix stateMatrix;

    public AdjacencyMatrix(StateMatrix stateMatrix, int dimensions) {
        this.stateMatrix = stateMatrix;
        this.dimensions = dimensions;
        this.adjacencyMatrix = new int[dimensions][dimensions];
    }

    public void transformToAdjacencyMatrix() {
        this.setInitialNode(this.stateMatrix.getInRowAndColumn(1, 0));
        for(int row = 1; row < this.dimensions; row++) {
            this.lookForDepends(row);
        }
    }


    private void setInitialNode(int value) {
        //this.setInRowAndColumn(0,1, value);
        this.setInRowAndColumn(1,0, value);
    }

    public int getLastStateInRow(int row) {
        int lastIndex = 0;
        for(int column = 0; column < this.dimensions; column++) {
            if(this.stateMatrix.containsValueInRowAndColumn(row, column) && this.stateMatrix.getInRowAndColumn(row, column) != 0) {
                lastIndex = column;
            }
        }
        return lastIndex;
    }

    public void lookForDepends(int currentRow) {
        int lastColumnWithValue = this.getLastStateInRow(currentRow);

        for(int row = 0; row < this.dimensions; row++) {
            if(this.isDependent(row, lastColumnWithValue) && row != currentRow) {
                int value = this.stateMatrix.getInRowAndColumn(row, lastColumnWithValue);
                if(value != 0) {
                    this.setAdjacencyByRow(row, currentRow, value);
                    //this.setAdjacencyByColumn(row, currentRow, value);
                }
            }
        }
    }

    private boolean isDependent(int row, int currentColumn) {
        int quantityDepends = 0;

        for(int column = currentColumn; column < this.dimensions; column++) {
            if(this.stateMatrix.containsValueInRowAndColumn(row, column)
                    && this.stateMatrix.getInRowAndColumn(row, column) != 0) {
                quantityDepends++;
            }
        }

        return quantityDepends == 2;
    }

    private void setAdjacencyByRow(int row, int lastColumnWithValue, int value) {
        this.setInRowAndColumn(row, lastColumnWithValue, value);
    }

    private void setAdjacencyByColumn(int row, int lastColumnWithValue, int value) {
        this.setInRowAndColumn(lastColumnWithValue, row , value);
    }

    public void setInRowAndColumn(int row, int column, int value) {
        this.adjacencyMatrix[row][column] = value;
    }

    public int getInRowAndColumn(int row, int column) {
        return this.adjacencyMatrix[row][column];
    }

    public void printMatrix() {
        for(int row = 0; row < this.dimensions; row++) {
            for(int column = 0; column < this.dimensions; column++) {
                System.out.printf("%d,", this.adjacencyMatrix[row][column]);
            }
            System.out.println();
        }
    }
}

package com.tpa.lab;

import com.tpa.lab.domain.Card;

import java.util.List;
import java.util.stream.Collectors;

public class StateMatrix {
    public static final int ROWS = 8;
    private int[][] matrix;
    private List<Card> cards;
    private int indexRow = 2;

    // TODO: Tamanho das linhas precisa ser flexivel
    public StateMatrix(List<Card> cards, int column) {
        this.cards = cards;
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

    public StateMatrix tranformToStateMatrix() {
        Card initial = cards.stream().filter(c -> c.isInicial()).findFirst().get();
        this.setInRowAndColumn(1, 0, initial.getCusto());
        this.getDepentendsOfTaskAccomplished(initial.getRealiza(), 1, 0);

        return this;
    }

    public void getDepentendsOfTaskAccomplished(String taskAccomplished, int row, int col) {
        List<Card> neededs = this.getOnlyDependentCards(taskAccomplished);
        int columnIndex = this.cards.indexOf(this.whoRealizeTheAbility(taskAccomplished));

        for(Card card : neededs) {
            int columnIndexCard = this.cards.indexOf(this.whoRealizeTheAbility(card.getRealiza()));

            this.copyPreviousRow(row, this.cards.size());
            this.setZeroToConflitcts(neededs);
            this.setInRowAndColumn(this.indexRow, columnIndexCard, card.getCusto());

            this.indexRow++;
            this.getDepentendsOfTaskAccomplished(card.getRealiza(), this.indexRow-1, columnIndex);

        }

    }

    private List<Card> getOnlyDependentCards(String taskAccomplished) {
        return this.cards.stream().filter(c -> this.getCardDepentendOfTask(c.getNecessarias(), taskAccomplished))
                .collect(Collectors.toList());
    }

    private boolean getCardDepentendOfTask(List<String> neededs,  String task) {
        return neededs.stream().filter(n -> n.equals(task))
                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .isPresent();
    }

    private void copyPreviousRow(int nextRow, int column) {
        int [][] matrix = this.getMatrix();
        System.arraycopy(matrix[nextRow], 0,matrix[this.indexRow], 0, column);
    }


    private void setZeroToConflitcts(List<Card> conflitcts) {
        for(Card conflitct : conflitcts) {
            int column = this.cards.indexOf(conflitct);
            this.setInRowAndColumn(this.indexRow, column, 0);
        }
    }

    private Card whoRealizeTheAbility(String ability) {
        return this.cards.stream().filter(c -> c.getRealiza().equals(ability)).findFirst().orElse(null);
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

    public void printMatrix() {
        for(int row = 0; row < StateMatrix.ROWS; row++) {
            for(int column = 0; column < this.cards.size(); column++) {
                System.out.printf("%d | ", this.getMatrix()[row][column]);
            }
            System.out.println();
        }
    }

}

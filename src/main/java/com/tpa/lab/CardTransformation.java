package com.tpa.lab;

import com.tpa.lab.domain.Card;
import com.tpa.lab.domain.StateMatrix;

import java.util.List;
import java.util.stream.Collectors;

public class CardTransformation {
    private List<Card> cards;
    private StateMatrix stateMatrix;

    private int indexRow = 1;

    public CardTransformation(List<Card> cards, StateMatrix stateMatrix) {
        this.cards = cards;
        this.stateMatrix = stateMatrix;
    }

    public StateMatrix tranformToStateMatrix() {
        Card initial = cards.stream().filter(c -> c.isInicial()).findFirst().get();
        stateMatrix.setInRowAndColumn(1, 0, initial.getCusto());
        this.getDepentendsOfTaskAccomplished(initial.getRealiza());

        return this.stateMatrix;
    }

    public void getDepentendsOfTaskAccomplished(String taskAccomplished) {
        List<Card> neededs = this.getOnlyDependentCards(taskAccomplished);

        for(Card card : neededs) {
            int columnIndex = this.cards.indexOf(card);
            this.indexRow++;
            this.copyPreviousRow(this.indexRow, columnIndex);
            this.setZeroToConflitcts(neededs);
            this.stateMatrix.setInRowAndColumn(indexRow, columnIndex, card.getCusto());

            this.getDepentendsOfTaskAccomplished(card.getRealiza());
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
        int [][] matrix = this.stateMatrix.getMatrix();
        System.arraycopy(matrix[this.indexRow - 1], 0,matrix[nextRow], 0, column);
    }

    private void setZeroToConflitcts(List<Card> conflitcts) {
        for(Card conflitct : conflitcts) {
            int column = this.cards.indexOf(conflitct);
            this.stateMatrix.setInRowAndColumn(this.indexRow, column, 0);
        }
    }

    public void printMatrix() {
        for(int row = 0; row < StateMatrix.ROWS; row++) {
            for(int column = 0; column < this.cards.size(); column++) {
                System.out.printf("%d | ", this.stateMatrix.getMatrix()[row][column]);
            }
            System.out.println();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Square implements Comparable{
    private int column, row, val, bigSquare;
    private Square next;

    public Square() {
    }
    
    public Square(Square s){
        this(s.getRow(),s.getColumn(),s.getVal());
    }

    public Square(int row, int column, int val) {
        this.column = column;
        this.row = row;
        this.val = val;
        bigSquare = ((column/3)+1) + ((row/3)*3);
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getBigSquare() {
        return bigSquare;
    }

    public Square getNext() {
        return next;
    }

    public void setNext(Square next) {
        this.next = next;
    }
    
    

    @Override
    public int compareTo(Object t) {
        if(t instanceof Square){
            if(val<((Square)t).getVal()){
                return -1;
            }else if(val>((Square)t).getVal()){
                return 1;
            }else{
                return 0;
            }
        }
        return 0;
    }
    
    public String toString(){
        return val + "";
    }
    
}

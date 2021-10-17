
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Sudoku {
    private Square[][] grid;
    private Square[][] unsolved;
    private ArrayList<Square> solvingSquares;
    
    public Sudoku(){
        grid =  new Square[9][9];
        unsolved = new Square[9][9];
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[r].length; c++){
                grid[r][c] = new Square(r, c, 0);
                unsolved[r][c] = new Square(r,c,0);
            }
        }
    }
    
    public Sudoku(String mapName){
        grid = new Square[9][9];
        unsolved = new Square[9][9];
        readFromMap(mapName);
        solvingSquares = getEmptySquares();
    }
    
    public void readFromMap(String mapName){
        try{
            Scanner in = new Scanner(new File(mapName));
            for(int r = 0; r < grid.length; r++){
                for(int c = 0; c < grid[r].length; c++){
                    int digit = in.nextInt();
                    grid[r][c] = new Square(r, c, digit);
                    unsolved[r][c] = new Square(r,c,digit);
                }
                if(in.hasNextLine()) in.nextLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reset(){
        for(int r=0;r<unsolved.length;r++){
            for(int c=0;c<unsolved[0].length;c++){
                grid[r][c]=new Square(unsolved[r][c]);
            }
        }
    }
    
    public boolean solve(){
        //System.out.println("prior:"+toString());
        Square node = new Square(-1,-1,-1), first = new Square();
        for(int r = 0;r < grid.length; r++){
            for(int c = 0; c < grid[r].length;c++){
                if(grid[r][c].getVal()==0){
                    if(node.getRow()<0){
                        node = grid[r][c];
                        first = node;
                    }else{
                        node.setNext(grid[r][c]);
                        node = node.getNext();
                    }
                }
            }
        }
        //System.out.println(first.getNext());
        return solve(first);
    }
    
    private boolean solve(Square s){
        
        //System.out.println(toString());
        if(s==null){
            return true;
        }
        
        ArrayList<Integer> posibilities = getAllSolutions(s);
        //System.out.println("possible moves: "+posibilities.toString());
        //System.out.println("-----");
        if(posibilities.isEmpty()){
            return false;
        }
        for(int i = 0; i < posibilities.size(); i++){
            s.setVal(posibilities.get(i));
            fillInSquare(s);
            if(solve(s.getNext())){
               return true; 
            }
        }
        Square empty = s;
        empty.setVal(0);
        fillInSquare(empty);
        return false;
    }
    
    private void fillInSquare(Square s){
        grid[s.getRow()][s.getColumn()] = s;
    }

    public Square[][] getGrid() {
        return grid;
    }
    
    public ArrayList<Square> getEmptySquares(){
        ArrayList<Square> out = new ArrayList<>();
        for(int r=0;r<grid.length;r++){
            for(int c=0;c<grid[0].length;c++){
                if(grid[r][c].getVal()==0){
                    out.add(grid[r][c]);
                }
            }
        }
        return out;
    }

    public ArrayList<Square> getSolvingSquares() {
        return solvingSquares;
    }
    
    
    
    public ArrayList<Integer> getExistingNumbers(Square s){
        //System.out.println("attempt: (" + s.getRow()+","+s.getColumn()+")");
        Square[] row = new Square[9];
        Square[] col = new Square[9];
        Square[] bigSquare = new Square[9];
        for(int b = 0; b < grid[0].length; b++){
            col[b] = grid[b][s.getColumn()];
        }
        for(int a = 0; a < grid[0].length; a++){
            row[a] = grid[s.getRow()][a];
        }
        //System.out.println("big: "+s.getBigSquare());
        int start = ((s.getBigSquare()-1)/3) * 3;
        int add = 0;
        for(int r = start; r < start+3;r++){
            for(int c = ((s.getBigSquare()-1)%3)*3;c<((s.getBigSquare()-1)%3)*3+3;c++){
                //System.out.println(((s.getBigSquare()%3)-1)*3);
                bigSquare[add] = grid[r][c];
                add++;
            }
        }
//        System.out.println("data in the row: "+Arrays.toString(row));
//        System.out.println("data in the column: "+Arrays.toString(col));
//        System.out.println("data in the bigSquare: "+Arrays.toString(bigSquare));
        
        Arrays.sort(col);
        Arrays.sort(row);
        Arrays.sort(bigSquare);
        
        ArrayList<Integer> used = new ArrayList<>();
        
        int i1=0,i2=0,i3=0;
        
        while(i1<9){
            if(row[i1].getVal()==0){}
            else used.add(row[i1].getVal());
            i1++;
        }
        while(i2<9){
            if(col[i2].getVal()==0) {}
            else used.add(col[i2].getVal());
            i2++;
        }
        while(i3<9){
            if(bigSquare[i3].getVal()==0) {}
            else used.add(bigSquare[i3].getVal());
            i3++;
        }    
        //System.out.println("get from map: " + used.toString());
        HashSet<Integer> seen = new HashSet<>();
        for(int i = 0; i < used.size(); i++){
            //System.out.println(used.toString());
            if(!seen.contains(used.get(i))){
                seen.add(used.get(i));
            }
        }
        ArrayList<Integer> out = new ArrayList<>();
        Iterator<Integer> i = seen.iterator();
        while(i.hasNext()){
            out.add(i.next());
        }
        //System.out.println("after process: "+out.toString());
        return out;
    }

    public Square[][] getUnsolved() {
        return unsolved;
    }
    
    
    
    public boolean isValidSquare(Square s){
        return !getExistingNumbers(s).contains(s.getVal());
    }
    
    public ArrayList<Integer> getAllSolutions(Square s){
        ArrayList<Integer> out = new ArrayList<>();
        for(int i = 1; i <= 9; i++ ){ //could be more efficient
            if(!getExistingNumbers(s).contains(i)){
                out.add(i);
            }
        }
        return out;
    }
    
    @Override
    public String toString(){
        String out = "";
        for(Square[] list:grid){
            for(Square s:list){
                out += s.getVal()+ " ";
            }
            out += "\n";
        }
        return out.trim();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Sudoku test = new Sudoku("map2.txt");
        //System.out.println(test.getAllSolutions(new Square(0,3,0)).toString());
        boolean result = test.solve();
        System.out.println(test);
        System.out.println("result: " + result);
        //System.out.println(test);
    }
    
}

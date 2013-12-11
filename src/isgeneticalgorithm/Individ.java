/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isgeneticalgorithm;

/**
 *
 * @author aipova
 */
public class Individ {
    int[] hromosoma;
    int geneCount;
    public Individ(int gc) {
        this.geneCount = gc;
        hromosoma = new int[gc];
    }
    int fitness;
    
    @Override
    public String toString() {
        String s = "";
        for (int i=0; i<geneCount; i++) {
            s += (char)hromosoma[i];
        }
        return s;
    }
}

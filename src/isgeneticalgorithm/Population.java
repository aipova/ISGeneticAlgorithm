/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package isgeneticalgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aipova
 */
public class Population {
    int size;
    List<Individ> individs = new ArrayList<>();

    public Population(int size) {
        this.size = size;
    }
    
}

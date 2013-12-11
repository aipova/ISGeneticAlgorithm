package isgeneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author aipova
 */
public class ISGeneticAlgorithm {

    static Random rand = new Random();
    static int a = 1040, b = 1103;
    static int geneCount = 3;
    static int populationSize = 100;
    static int[] target = {1052, 1048, 1056};
    static double l = 0.5; // порог отсечения
    static double Pc = 0.8; // вероятность скрещивания
    static double Pm = 0.2; // вероятность мутации

    public static int fitnessFunction(Individ ind) {
        int fitness = 0;
        for (int i = 0; i < target.length; i++) {
            fitness += Math.abs(target[i] - ind.hromosoma[i]);
        }
        return fitness;
    }

    static Comparator<Individ> indorderer = new Comparator<Individ>() {
        public int compare(Individ i1, Individ i2) {
            return i1.fitness - i2.fitness;
        }
    };

    public static int evaluation(Population popul) {
        for (Individ ind : popul.individs) {
            ind.fitness = fitnessFunction(ind);
        }
        Collections.sort(popul.individs, indorderer);
        return popul.individs.get(0).fitness;
    }

    public static List<Individ> selection(double l, Population popul) {
        int ln = (int) Math.round(l * popul.size);
        List<Individ> selectedIndivids = new ArrayList<>();
        for (int i = 0; i < ln; i++) {
            selectedIndivids.add(popul.individs.get(i));
        }
        return selectedIndivids;
    }

    public static Individ[] crossover(Individ p1, Individ p2) {
        Individ[] childrens = new Individ[2];
        int point = rand.nextInt(1);
        Individ ch1 = new Individ(geneCount);
        Individ ch2 = new Individ(geneCount);
        for (int i = 0; i < geneCount; i++) {
            if (i <= point) {
                ch1.hromosoma[i] = p1.hromosoma[i];
                ch2.hromosoma[i] = p2.hromosoma[i];
            } else {
                ch1.hromosoma[i] = p2.hromosoma[i];
                ch2.hromosoma[i] = p1.hromosoma[i];
            }
        }
        childrens[0] = ch1;
        childrens[1] = ch2;
        return childrens;
    }

    public static Population crossing(double pc, List<Individ> selectedInd) {
        Population children = new Population(populationSize);
        int k = 0;
        while (k < populationSize) {
            int i = (int) Math.round(rand.nextDouble() * (selectedInd.size() - 1));
            int j = (int) Math.round(rand.nextDouble() * (selectedInd.size() - 1));
            if (rand.nextDouble() < Pc) {
                // скрещивание
                Individ[] childs = crossover(selectedInd.get(i), selectedInd.get(j));
                children.individs.add(k, childs[0]);
                children.individs.add(k + 1, childs[1]);
            } else {
                children.individs.add(k, selectedInd.get(i));
                children.individs.add(k + 1, selectedInd.get(j));
            }
            k += 2;
        }
        return children;
    }

    public static void mutation(double pm, Population popul) {
        for (Individ ind : popul.individs) {
            for (int gen = 0; gen < ind.geneCount; gen++) {
                if (rand.nextDouble() < Pm) {
                    // мутация
                    ind.hromosoma[gen] = ind.hromosoma[gen] + (int) rand.nextInt(b - a) / 10;
                }
            }
        }
    }

    public static Population formPopulation(int size) {
        Population popul = new Population(populationSize);
        for (int i = 0; i < populationSize; i++) {
            Individ ind = new Individ(3);
            for (int j = 0; j < ind.geneCount; j++) {
                ind.hromosoma[j] = rand.nextInt(b - a) + a;
            }
            popul.individs.add(ind);
        }
        return popul;
    }

    public static Population evolution(int populationSize, double l, double Pc,
            double Pm, int stepCount) {
        int fitness = 1000, steps = 0;
        // сформируем начальную популяцию
        Population parents = formPopulation(populationSize);
        do {
            fitness = evaluation(parents);
            System.out.println("Step " + steps + " - " + parents.individs.get(0)
                    + " [" + fitness + "]");
            List<Individ> selected = selection(l, parents);
            Population children = crossing(Pc, selected);
            mutation(Pm, children);
            parents = children;
            steps++;
            if (steps == stepCount) break;
        } while (fitness != 0);
        return parents;
    }

    public static void main(String[] args) {

        Population p = evolution(populationSize, l, Pc, Pm, 1000);

        
    }

}

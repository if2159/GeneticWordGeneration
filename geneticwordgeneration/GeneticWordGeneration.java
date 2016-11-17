package geneticwordgeneration;

import java.util.Random;

/**
 * @author Ian Fennen
 * This program will genetically generate an uppercase String.
 * The goal of the program was to develop in a non-OOP method.
 */
public class GeneticWordGeneration {

    /**
     * This {@code String} is the word that is being generated. 
     * This word must be in all uppercase and may only be A-Z.
     */
    public static final String FINAL_WORD = "HELLOWORLD";
    /**
     * This {@code int} is the length of the {@code FINAL_WORD}.
     */
    public static final int FINAL_WORD_SIZE = FINAL_WORD.length();
    /**
     * This is the size of the population to be generated.
     */
    public static final int POPULATION_SIZE = 1000;
    /**
     * This {@code String} array contains the population of candidates.
     */
    public static String[] population;
    /**
     * This {@code int} array contains the individual fitnesses of the individuals in the population.
     */
    public static int[] populationFitness;
    /**
     * This {@code int} is the index of the most fit individual in the {@code population} array.
     */
    public static int mostFit;//Index of the most fit 
    /**
     * This {@code Random} is used throughout the program.
     */
    public static Random r = new Random(System.currentTimeMillis());

    
    /**
     * This is the entry point for the program.
     * @param args Command line passed args. These are not used.
     */
    public static void main(String[] args) {
        initPopulation(POPULATION_SIZE);
        int gen = 1;
        while(!Found()){
            System.out.println("Generation: " + gen++ + " and the most fit is: " + populationFitness[mostFit]);
            /*if(populationFitness[mostFit] < 5){
                outputPopulation();
            }*/
            
            generateNewGeneration();
        }
        System.out.println("The final word was found after " + gen + " generations." + mostFit);
        //outputPopulation();
    }
    public static void outputPopulation(){
        for(int i = 0; i < population.length; i++){
            System.out.println("\t\n" + population[i] + " " + populationFitness[i]);
        }
    }
    /**
     * This method will cross two {@code String}s at a random pivot point. It will then change the number of characters 
     * that are incorrect. It will then return either the randomly changed {@code String} or the unchanged {@code String} depending
     * on the fitness of the individuals. Lower fitness value will be returned.
     * @param s1 This {@code String} will be the first part of the new String
     * @param s2 This {@code String} will be the second part of the new String
     * @return The {@code String} that is most fit.
     */
    public static String crossOver(String s1, String s2){
        int location =r.nextInt(FINAL_WORD_SIZE); 
        StringBuilder choice = new StringBuilder(s1.substring(0, location) + s2.substring(location));
        StringBuilder c2 = new StringBuilder(choice);
        for(int i = 0; i<calcIndividualFitness(choice.toString()); i++){
            int loc = r.nextInt(FINAL_WORD_SIZE);
            char randLetter = (char)(r.nextInt(26)+65);
            c2.setCharAt(loc, randLetter);
        }
        StringBuilder finalChoice=null;
        finalChoice = ((calcIndividualFitness(choice.toString()) < calcIndividualFitness(c2.toString()))?(choice):(c2));
        return finalChoice.toString();
    }
    
    /**
     * The will call {@code crossOver(String, String)} for each individual and the most fit individual.
     */
    public static void generateNewGeneration(){
        for(int i = 0; i < population.length; i++){
            population[i] = crossOver(population[mostFit], population[i]);
        }
        calcPopulationFitness(population.length);
    }
    
    /**
     * This method updates the {@code mostFit} variable.
     */
    public static void setMostFit(){
        int lowest = FINAL_WORD.length()+1;
        for(int i = 0; i < populationFitness.length; i++){
            if( lowest > populationFitness[i]){
                lowest = populationFitness[i];
                mostFit = i;
            }
        }
    }
    /**
     * This function will determine whether the final word has been generated or not.
     * @return Whether the final word has been generated.
     */
    public static boolean Found(){
        calcPopulationFitness(population.length);
        boolean found = false;
        for(int i = 0; i < populationFitness.length; i++){
            if(populationFitness[i] == 0){
                found = true;
                break;
            }
        }
        return found;
    }
    /**
     * This function will determine the fitness for the entire population.
     * @param popSize The number of individuals in the population.
     */
    public static void calcPopulationFitness(int popSize){
        for(int i = 0; i< popSize; i++){
            int distance = calcIndividualFitness(population[i]);
            populationFitness[i] = distance;
        }
    }
    /**
     * THis function will calculate the fitness of an individual {@code String}. The closer to 0 the more fit an individual is.
     * @param s This is the {@code String} that you need the fitness of.
     * @return The Levenshtein distance of the passed in variable.
     */
    public static int calcIndividualFitness(String s){
        int sum = 0;
        for(int i = 0; i< s.length(); i++){
            if(s.charAt(i) != FINAL_WORD.charAt(i)){
                sum++;
            }
        }
        return sum;
    }
    
    /**
     * This function will populate the initial {@code population} array.
     * @param popSize The size of the target population.
     */
    public static void initPopulation(int popSize){
        population = new String[popSize];
        populationFitness = new int[popSize];
        for(int i = 0; i < popSize; i++){
            population[i] = generateNewString(FINAL_WORD_SIZE);
        }
        calcPopulationFitness(popSize);
    }
    /**
     * This method will generate a new {@code String} that is all uppercase and between A-Z.
     * @param size The size of the random {@code String}.
     * @return A randomly generated {@code String} of length {@code size}.
     */
    public static String generateNewString(int size){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < size; i++){
            char c = (char)(r.nextInt(26)+65);
            result.append(c);
        }
        return result.toString();
    }
}

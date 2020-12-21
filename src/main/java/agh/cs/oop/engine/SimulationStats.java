package agh.cs.oop.engine;

import agh.cs.oop.mapelement.Genotype;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.util.Pair;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.averagingInt;

public class SimulationStats {
  private final IntegerProperty allAnimals; // to trzeba zapisywać
  private final IntegerProperty allGrasses; // to trzeba zapisywać
  private final ObjectProperty<Pair<Genotype, Integer>> dominatingGenotype;
  private final DoubleProperty averageEnergyPerAnimal; // to trzeba zapisywać
  private final DoubleProperty averageLifespan; // to trzeba zapisywać
  private final DoubleProperty averageNumberOfChildren; // to trzeba zapisywać

  private final Map<Genotype, Integer> genotypeOccurrences = new HashMap<>();
  private final Map<Genotype, Integer> allGenotypeOccurrences = new HashMap<>();
  private HistoricSimulationStat historicStats = new HistoricSimulationStat();

  SimulationStats(int startNumberOfAnimals, int startNumberOfGrasses, int startEnergy) {
    allAnimals = new SimpleIntegerProperty(startNumberOfAnimals);
    allGrasses = new SimpleIntegerProperty(startNumberOfGrasses);
    dominatingGenotype = new SimpleObjectProperty<>();
    averageEnergyPerAnimal = new SimpleDoubleProperty(startEnergy);
    averageLifespan = new SimpleDoubleProperty(-1);
    averageNumberOfChildren = new SimpleDoubleProperty(-1);
  }

  public IntegerProperty allAnimalsProperty() {
    return allAnimals;
  }

  public IntegerProperty allGrassesProperty() {
    return allGrasses;
  }

  public ObjectProperty<Pair<Genotype, Integer>> dominatingGenotypeProperty() {
    return dominatingGenotype;
  }

  public DoubleProperty averageEnergyPerAnimalProperty() {
    return averageEnergyPerAnimal;
  }

  public DoubleProperty averageLifespanProperty() {
    return averageLifespan;
  }

  public DoubleProperty averageNumberOfChildrenProperty() {
    return averageNumberOfChildren;
  }

  public void incrementNumberOfAnimals() {
    allAnimals.setValue(allAnimals.get() + 1);
  }

  public void decrementNumberOfAnimals() {
    allAnimals.setValue(allAnimals.get() - 1);
  }

  public void incrementNumberOfGrasses() {
    allGrasses.setValue(allGrasses.get() + 1);
  }

  public void decrementNumberOfGrasses() {
    allGrasses.setValue(allGrasses.get() - 1);
  }

  public void calcAverageNumOfChildren(List<Animal> animals) {
    averageNumberOfChildren.set(Math.round(animals.stream().collect(averagingInt(Animal::getNumberOfChildren)) * 100) / 100.0);
  }

  public void calcAverageEnergy(List<Animal> animals) {
    averageEnergyPerAnimal.set(Math.round(animals.stream().collect(averagingInt(Animal::getEnergy))));
  }

  public void calcAverageLifespan(List<Animal> animals) {

    averageLifespan.set(Math.round(animals.stream().collect(averagingInt(a -> {
      int lifespan = a.getDeathEpoch() - a.getBirthEpoch();
      return lifespan;
    }))));
  }

  public void addGenotype(Genotype genotype) {
    if (genotypeOccurrences.containsKey(genotype)) {
      genotypeOccurrences.put(genotype, genotypeOccurrences.get(genotype) + 1);
      allGenotypeOccurrences.put(genotype, allGenotypeOccurrences.get(genotype) + 1);
    } else {
      genotypeOccurrences.put(genotype, 1);
      allGenotypeOccurrences.put(genotype, 1);
    }
    var max = genotypeOccurrences.entrySet().stream().max(comparingInt(Map.Entry::getValue)).get();
    dominatingGenotype.setValue(new Pair<>(max.getKey(), max.getValue()));
  }

  public void removeGenotype(Genotype genotype) {
    if (genotypeOccurrences.get(genotype) > 0) {
      genotypeOccurrences.put(genotype, genotypeOccurrences.get(genotype) - 1);
    } else {
      genotypeOccurrences.remove(genotype);
    }
    var max = genotypeOccurrences.entrySet().stream().max(comparingInt(Map.Entry::getValue)).get();
    dominatingGenotype.setValue(new Pair<>(max.getKey(), max.getValue()));
  }

  public void saveSimulationStatSnapshot() {
    historicStats.averageChildrenList.add(averageNumberOfChildren.get());
    historicStats.averageLifeSpanList.add(averageLifespan.get());
    historicStats.averageEnergyPerAnimalList.add(averageEnergyPerAnimal.get());
    historicStats.numberOfGrassesList.add(allGrasses.get());
    historicStats.numberOfAnimalsList.add(allAnimals.get());
    historicStats.allGenotypeOccurrences = allGenotypeOccurrences;
  }

  public HistoricSimulationStat getHistoricSimulationStat() {
    return historicStats;
  }

  public void resetHistoricSimulationStat() {
    this.historicStats = new HistoricSimulationStat();
  }

  public void updateStatsOnEpochEnd(List<Animal> animals, List<Animal> deadAnimals) {
    calcAverageNumOfChildren(animals);
    calcAverageEnergy(animals);
    calcAverageLifespan(deadAnimals);
    saveSimulationStatSnapshot();
  }

  static class HistoricSimulationStat {
    List<Integer> numberOfAnimalsList = new ArrayList<>();
    List<Integer> numberOfGrassesList = new ArrayList<>();
    Map<Genotype, Integer> allGenotypeOccurrences;
    List<Double> averageEnergyPerAnimalList = new ArrayList<>();
    List<Double> averageLifeSpanList = new ArrayList<>();
    List<Double> averageChildrenList = new ArrayList<>();
  }

  public static class SummarySimulationStats implements Serializable {
    double averageNumberOfAnimals;
    double averageNumberOfGrasses;
    String dominatingGenotypeSequence;
    int intNumberOfOccurrencesOfDominatingGenotype;
    double averageAverageEnergyPerAnimal;
    double averageAverageLifeSpan;
    double averageAverageChildren;

    public SummarySimulationStats(HistoricSimulationStat histStats) {
      averageAverageChildren = histStats.averageChildrenList.stream()
        .collect(averagingDouble(a -> a));
      averageNumberOfGrasses = histStats.numberOfGrassesList.stream()
        .collect(averagingInt(a -> a));
      averageNumberOfAnimals = histStats.numberOfAnimalsList.stream()
        .collect(averagingInt(a -> a));
      averageAverageEnergyPerAnimal = histStats.averageEnergyPerAnimalList.stream()
        .collect(averagingDouble(a -> a));
      averageAverageLifeSpan = histStats.averageLifeSpanList.stream()
        .collect(averagingDouble(a -> a));
      Map.Entry<Genotype, Integer> dominatingGenotypeOccurencies =
        histStats.allGenotypeOccurrences.entrySet().stream().max(comparingInt(Map.Entry::getValue)).get();
      dominatingGenotypeSequence = dominatingGenotypeOccurencies.getKey().toString();
      intNumberOfOccurrencesOfDominatingGenotype = dominatingGenotypeOccurencies.getValue();
    }
  }
}
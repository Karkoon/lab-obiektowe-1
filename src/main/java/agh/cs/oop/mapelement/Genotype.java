package agh.cs.oop.mapelement;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Genotype {
  private final static int GENE_TYPES = 8;
  private final static int GENOTYPE_LENGTH = 32;
  private final static ThreadLocalRandom rand = ThreadLocalRandom.current();

  private final int[] data = new int[GENOTYPE_LENGTH];

  public Genotype() {
    for (int i = 0; i < data.length; i++) {
      data[i] = rand.nextInt(8);
    }
    sortAndRepairGenotype();
  }

  public Genotype(Genotype genotypeA, Genotype genotypeB) {
    int partingIndexA = rand.nextInt(1, GENOTYPE_LENGTH - 3);
    int partingIndexB;
    do {
      partingIndexB = rand.nextInt(3, GENOTYPE_LENGTH - 1);
    } while (Math.abs(partingIndexA - partingIndexB) <= 1);

    int temp = Math.min(partingIndexA, partingIndexB);
    partingIndexB = Math.max(partingIndexA, partingIndexB);
    partingIndexA = temp;

    if (rand.nextBoolean()) {
      Genotype tempGen = genotypeA;
      genotypeA = genotypeB;
      genotypeB = tempGen;
    }

    System.arraycopy(genotypeA.data, 0, data, 0, partingIndexA);
    System.arraycopy(genotypeB.data, partingIndexA, data, partingIndexA, partingIndexB - partingIndexA);

    if (rand.nextBoolean()) {
      System.arraycopy(genotypeA.data, partingIndexB, data, partingIndexB, GENOTYPE_LENGTH - partingIndexB);
    } else {
      System.arraycopy(genotypeB.data, partingIndexB, data, partingIndexB, GENOTYPE_LENGTH - partingIndexB);
    }
    sortAndRepairGenotype();
  }

  public int getRandomGene() {
    return data[rand.nextInt(data.length)];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Genotype genotype = (Genotype) o;
    return Arrays.equals(data, genotype.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }

  @Override
  public String toString() {
    return Arrays.stream(data).mapToObj(String::valueOf).collect(Collectors.joining(""));
  }

  private void sortAndRepairGenotype() {
    int[] geneCounters = new int[GENE_TYPES];
    for (int i = 0; i < GENOTYPE_LENGTH; i++) {
      geneCounters[data[i]]++;
    }

    for (int i = 0; i < geneCounters.length; i++) {
      if (geneCounters[i] == 0) {
        int idx;
        do {
          idx = rand.nextInt(GENE_TYPES);
        } while (idx == i || geneCounters[idx] <= 1);
        geneCounters[idx]--;
        geneCounters[i]++;
      }
    }

    int dataIdx = 0;
    for (byte i = 0; i < GENE_TYPES; i++) {
      while (geneCounters[i] > 0) {
        data[dataIdx] = i;
        dataIdx++;
        geneCounters[i]--;
      }
    }
  }

}

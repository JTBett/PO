package hva.util;

import hva.species.Species;
import hva.animals.Animal;
import hva.habitats.Habitat;
import hva.satisfactionStrategies.VetSatisfactionStrategy;
import hva.trees.Trees;
import hva.trees.Perene;
import hva.trees.Caduca;

import hva.vaccines.Vaccine;
import hva.vaccines.VaccinationInstance;

import hva.employees.Employee;
import hva.employees.Zookeeper;
import hva.employees.Vet;

import hva.seasons.Season;
import hva.seasons.Spring;
import hva.seasons.Summer;
import hva.seasons.Autumn;
import hva.seasons.Winter;

import java.io.Serializable;

public abstract class Visitor<T> implements Serializable {

  public abstract T visit(Species s);
  public abstract T visit(Animal a);
  public abstract T visit(Employee e);
  public abstract T visit(Vet v);
  public abstract T visit(Zookeeper z);
  public abstract T visit(Habitat h);
  public abstract T visit(Vaccine v);

}
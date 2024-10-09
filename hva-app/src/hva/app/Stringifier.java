package hva.app;

import hva.animals.Animal;
import hva.employees.Vet;
import hva.employees.Zookeeper;
import hva.species.Species;
import hva.habitats.Habitat;
import hva.vaccines.Vaccine;

import java.util.StringJoiner;

public class Stringifier extends Visitor<String> {
    

    @Override
    public String visit(Species s){
        return new StringJoiner("|")
            .add("ESPÉCIES")
            .add(Integer.toString(s.getKeyId()))
            .add(s.getName())
            .toString();
    }

    @Override
    public String visit(Animal a){
        return new StringJoiner("|")
            .add("ANIMAL")
            .add(Integer.toString(a.getKeyId()))
            .add(a.getName())
            .add(Integer.toString(a.getSpeciesId()))
            .add(Integer.toString(a.getHabitat()))
            .toString();
    }

    @Override
    public String visit(Vet v){
        return new StringJoiner("|")
            .add("VETERINÁRIO")
            .add(Integer.toString(v.getKeyId()))
            .add(v.getName())
            .add(Integer.toString(v.getSpeciesId()))
            .toString();
    }

    @Override
    public String visit(Zookeeper z){
        return new StringJoiner("|")
            .add("TRATADOR")
            .add(Integer.toString(z.getKeyId()))
            .add(z.getName())
            .add(Integer.toString(z.getSpeciesId()))
            .toString();
    }

    @Override
    public String visit(Habitat h){
        return new StringJoiner("|")
            .add("HABITAT")
            .add(Integer.toString(h.getKeyId()))
            .add(h.getName())
            .add(Integer.toString(h.getArea()))
            .toString();
    }

    @Override
    public String visit(Vaccinet v){
        return new StringJoiner("|")
            .add("VACINA")
            .add(Integer.toString(v.getKeyId()))
            .add(v.getName())
            .toString();
    }

}

/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package RailwayOriented.egg;

import java.util.ArrayList;
import java.util.List;

public class Egg {
    private int daysToHatch;
    
    private Yellow yellow;

    public Egg() {
    }

    public Egg(int daysToHatch) {
        this.daysToHatch = daysToHatch;
    }

    public static List<Egg> getEggCarton() {
        List<Egg> eggList = new ArrayList<>();
        eggList.add(new Egg(1));
        eggList.add(new Egg(2));
        eggList.add(new Egg(3));
        eggList.add(new Egg(4));
        return eggList;
    }

    public int getDaysToHatch() {
        return daysToHatch;
    }

    public void setDaysToHatch(int daysToHatch) {
        this.daysToHatch = daysToHatch;
    }

    public Yellow getYellow() {
        return yellow;
    }

    public void setYellow(Yellow yellow) {
        this.yellow = yellow;
    }

    public static class Yellow {
    }
}

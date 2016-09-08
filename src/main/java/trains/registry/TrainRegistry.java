package trains.registry;


import trains.entities.trains.FirstTrain;
import trains.gui.train.GUISteam;

import java.util.ArrayList;
import java.util.List;

public class TrainRegistry {
    public Class trainClass = FirstTrain.class;
    public String model ="";
    public String texture ="";
    public String entityWorldName = ""; //Note: Must be all lowercase


    private TrainRegistry(Class trainClass, String entityWorldName, String model, String texture){
        this.trainClass = trainClass;
        this.entityWorldName = entityWorldName;
        this.model = model;
        this.texture = texture;
    }



    public static List<TrainRegistry> listTrains(){
        List<TrainRegistry> output = new ArrayList<TrainRegistry>();

        output.add(new TrainRegistry(FirstTrain.class, "entityfirsttrain","060e2.obj", "null.png"));

        return output;

    }



}

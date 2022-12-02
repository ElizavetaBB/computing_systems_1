package org.example.delta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClearDeltaMain {
    public static void main(String[] args) {
        String[] stringClearData1 = {
                "0.9675223634976504;0.9023740834488767;0.7133667112712133",
                "0.9061908381264607;0.9658368309771878;0.7141640052448851",
                "0.7460011593691043;0.7875070074414541;0.7320644078958906"
        };

        String[] stringClearData2 = {
                "0.971229343361637;0.913112618827114;0.7114893658698811",
                "0.916855826850029;0.9677757930274445;0.7115073191543687",
                "0.7217656916399581;0.7906217415252691;0.6805949331711157"
        };

        String[] stringClearData3 = {
                "0.9524855980531434;0.9455324946113058;0.7092119486488451",
                "0.9421738327106339;0.9516950579506478;0.7169719098864042",
                "0.7368088285823582;0.7208463315296199;0.7452472979555049"
        };

        String[] stringClearData4 = {
                "0.9469384503166941;0.9433289209200972;0.7092372444204725",
                "0.937839442992228;0.9513654874846689;0.7170925893941987",
                "0.7714150568168447;0.7390527492535004;0.7223222872064714"
        };

        String[] stringClearData5 = {
                "0.9581004286785954;0.9455969967794883;0.7046563342732787",
                "0.9378763523479721;0.9618253708579572;0.7302419124282039",
                "0.6726676777449152;0.6759238279838402;0.6972804516441947"
        };

        String[] stringClearData6 = {
                "0.9238139579777243;0.9267617191235089;0.7034888476771172",
                "0.9237821732487368;0.9267253489424674;0.7018888856638474",
                "0.752117407652988;0.7588473106340593;0.9140484368539662"
        };

        List<Double> currentDoubleData;
        double averagePerClearImage;
        double fullAveragePerClearImage = 0;
        for (String stringData : stringClearData6) {
            currentDoubleData = Arrays.stream(stringData.split(";")).map(Double::parseDouble).collect(Collectors.toList());
            if (currentDoubleData.get(0) > currentDoubleData.get(1)) {
                if (currentDoubleData.get(0) > currentDoubleData.get(2)) {
                    averagePerClearImage = ((currentDoubleData.get(0) - currentDoubleData.get(1)) + (currentDoubleData.get(0) - currentDoubleData.get(2))) / 2;
                    System.out.println("circle average delta = " + averagePerClearImage);
                } else {
                    averagePerClearImage = ((currentDoubleData.get(2) - currentDoubleData.get(0)) + (currentDoubleData.get(2) - currentDoubleData.get(1))) / 2;
                    System.out.println("triangle average delta = " + averagePerClearImage);
                }
            } else {
                if (currentDoubleData.get(1) > currentDoubleData.get(2)) {
                    averagePerClearImage = ((currentDoubleData.get(1) - currentDoubleData.get(0)) + (currentDoubleData.get(1) - currentDoubleData.get(2))) / 2;
                    System.out.println("square average delta = " + averagePerClearImage);
                } else {
                    averagePerClearImage = ((currentDoubleData.get(2) - currentDoubleData.get(0)) + (currentDoubleData.get(2) - currentDoubleData.get(1))) / 2;
                    System.out.println("triangle average delta = " + averagePerClearImage);
                }
            }
            fullAveragePerClearImage += averagePerClearImage;
        }
        fullAveragePerClearImage /= 3;
        System.out.println("full average delta = " + fullAveragePerClearImage);
    }
}

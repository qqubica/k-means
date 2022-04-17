import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<SetRow> data = loadSet("iris.data.csv");

        System.out.print("Input k:\t");
        int k = new Scanner(System.in).nextInt();
        System.out.println();

//        int k =2;

        kMeans(k, data);

    }

    public static void kMeans(int k, List<SetRow> data) {

        Group[] groups = initialize(k, data);

        while (!ittarate(data, groups));

        printGroups(groups);

    }

    public static void printGroups(Group[] groups) {
        for (int i = 0; i < groups.length; i++) {
            System.out.println("Group " + i + " contains:");
            for (int j = 0; j < groups[i].size(); j++) {
                System.out.println(groups[i].get(j));
            }
            System.out.println();
        }
    }

    public static boolean ittarate(List<SetRow> data, Group[] groups) {
        for (int i = 0; i < data.size(); i++) {

            double minDistance = groups[0].getDistance(data.get(i));
            int groupIndex = 0;

            for (int j = 1; j < groups.length; j++) {

                if (minDistance > groups[j].getDistance(data.get(i))) {

                    minDistance = groups[j].getDistance(data.get(i));
                    groupIndex = j;

                }
            }

            groups[groupIndex].add(data.get(i));

        }

        printTotalDistanceSum(groups);

        //true if fould
        return findCentroids(groups);
    }

    public static void printTotalDistanceSum(Group [] groups){
        double totalDistanceSum = 0;

        for (int i = 0; i < groups.length; i++) {

            totalDistanceSum += groups[i].distanceSum();

        }

        System.out.println("Total distance sum\t=\t"+totalDistanceSum);
        System.out.println();
    }

    public static boolean anyChanges(Group group) {
        return group.findCentroids();
    }

    public static boolean findCentroids(Group[] groups) {
        boolean changes = true;
        for (int i = 0; i < groups.length; i++) {
            if (anyChanges(groups[i])) {
                changes = false;
            }
        }
        //true if found
        if (!changes) {
            clearGroups(groups);
        }
        return changes;
    }

    public static void clearGroups(Group[] groups) {
        for (int i = 0; i < groups.length; i++) {
            groups[i].clearGroup();
        }
    }

    public static Group[] initialize(int k, List<SetRow> data) {

        Group groups[] = new Group[k];

        for (int i = 0; i < k; i++) {
            groups[i] = new Group(k);
        }

        for (int i = 0; i < data.size(); i++) {
            groups[i%k].add(data.get(i));
        }

        findCentroids(groups);

        clearGroups(groups);

        return groups;
    }

    public static List<SetRow> loadSet(String path) {

        try {

            Scanner sc = new Scanner(Path.of(path));
            List<SetRow> setList = new LinkedList<>();

            while (sc.hasNextLine()) {

                String[] tmp = sc.nextLine().split(",");
                double[] tmpDouble = new double[tmp.length - 1];

                for (int i = 0; i < tmp.length - 1; i++) {
                    tmpDouble[i] = Double.parseDouble(tmp[i]);
                }

                setList.add(new SetRow(tmpDouble, tmp[tmp.length - 1]));

            }

            return setList;

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Group {
    List<SetRow> group;
    double centroid[];

    public Group(int k) {
        group = new ArrayList<>();
        centroid = new double[k];
    }

    public int size(){
        return group.size();
    }

    public SetRow get(int i){
        return  group.get(i);
    }

    @Override
    public String toString() {
        return "distance sum\t=\t" + distanceSum();
    }

    public double distanceSum(){
        double tmp = 0;
        for (int i = 0; i < group.size(); i++) {
            for (int j = 0; j < centroid.length; j++) {
                tmp += Math.pow(group.get(i).getParameters(j) - centroid[j],2);
            }
        }
        return Math.sqrt(tmp);
    }

    public void clearGroup() {
        group.clear();
    }

    public double getDistance(SetRow setRow) {
        double tmp = 0;

        for (int i = 0; i < setRow.length(); i++) {
            tmp += Math.pow((centroid[i] - setRow.getParameters(i)), 2);
        }

        return Math.sqrt(tmp);
    }

    public boolean findCentroids() {
        if (group.size() == 0) {
            return true;
        }

        double tmp[] = new double[group.get(0).length()];

        for (int i = 0; i < group.size(); i++) {
            for (int j = 0; j < group.get(i).length(); j++) {
                tmp[j] += group.get(i).getParameters(j);
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] /= group.size();
        }


        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] != centroid[i]) {
                centroid = tmp;
                //if something changed true
                return true;
            }
        }

        centroid = tmp;
        return false;
    }

    public boolean add(SetRow setRow) {
        return group.add(setRow);
    }
}

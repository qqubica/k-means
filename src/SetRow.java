public class SetRow {

    double [] parameters;
    String result;

    public SetRow(double[] parameters, String result) {
        this.parameters = parameters;
        this.result = result;
    }

    public int length(){
        return parameters.length;
    }

    public double distanceApart(SetRow check){
        double distance = 0;
        for (int i = 0; i < parameters.length; i++) {
            distance += Math.pow(parameters[i]-check.parameters[i],2);
        }
        return Math.sqrt(distance);
    }

    @Override
    public String toString() {
        String tmp = result + "\t";
        for (int i = 0; i < parameters.length; i++) {
            tmp += parameters[i] + " ";
        }
        return tmp;
    }

    public double getParameters(int i) {
        return parameters[i];
    }

    public int getResult() {
        return result.equals("Iris-virginica") ? 1 : 0;
    }
}

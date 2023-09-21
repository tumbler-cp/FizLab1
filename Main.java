import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static float rho(float x, float avg, float sigma) {
        return (float) ((1/(sigma*Math.sqrt(2*Math.PI))) * Math.exp(-(Math.pow(x-avg, 2))/(2*Math.pow(sigma, 2))));
    }

    public static void main(String[] args) throws IOException {
        Scanner fIn;

        File out = new File("output.csv");
        FileWriter fileWriter = new FileWriter(out);

        File out2 = new File("output.txt");
        FileWriter fileWriter2 = new FileWriter(out2);

        File out3 = new File("parsed.txt");
        FileWriter fileWriter3 = new FileWriter(out3);

        while (true) {
            System.out.print("Путь к csv-файлу: ");
            String fName = new Scanner(System.in).nextLine().trim();
            File file = new File(fName);
            try {
                fIn = new Scanner(file);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
            }
        }
        ArrayList<Float> arr = new ArrayList<>();
        while (fIn.hasNextLine()) {
            String curr = fIn.nextLine();
            String varStr = curr.split(";")[4];
            varStr = varStr.replace(',', '.');
            fileWriter3.append(varStr).append('\n');
            arr.add(Float.parseFloat(varStr));
        }

        float avg = (float) 1 / arr.size();

        for (float x : arr) {
            System.out.println(Math.pow(Math.abs(avg - x), 2));
            fileWriter3.append(String.valueOf(x)).append('\n');
        }

        arr.sort(Float::compareTo);

        HashMap<Float, Integer> map = new HashMap<>();

        for (float a : arr) {
            fileWriter2.append(String.valueOf(a)).append('\n');
            if (map.containsKey(a)) {
                int b = map.get(a);
                map.put(a, b + 1);
            } else {
                map.put(a, 1);
            }
        }

        for (float a : map.keySet()) {
            fileWriter.append(String.valueOf(a)).append(',').append(map.get(a).toString()).append('\n');
        }

        fileWriter.close();
        fileWriter2.close();



        float sum = 0;
        float res;


        for (float x : arr) {
            sum += (float) Math.pow(x - avg, 2);
        }

        //--Среднее квадратичное
        res = (float) Math.sqrt(((double) 1 / (arr.size() - 1)) * sum);

        //--Среднее квадратичное среднего значения
        float resAvg = (float) Math.sqrt(((double) 1 / (arr.size() * (arr.size() - 1))) * sum);

        System.out.println("Среднее арифметическое: " + avg);
        System.out.println("Среднее квадратичное: " + res);
        System.out.println("Среднее квадратичное среднего значения: " + resAvg);
        System.out.println("Сумма разностей: " + sum);

        float p_max = (float) (1/(res*Math.sqrt(2*Math.PI)));
        System.out.println("p_max: " + p_max);


        //--Доверительные интервалы
        //1
        float t11 = avg - res;
        float t12 = avg + res;
        System.out.println("t11: " + t11 + "\nt12: "+t12);

        float t21 = avg - 2*res;
        float t22 = avg + 2*res;
        System.out.println("t21: " + t21 + "\nt22: "+t22);

        float t31 = avg - 3*res;
        float t32 = avg + 3*res;
        System.out.println("t31: " + t31 + "\nt32: "+t32);


    }
}


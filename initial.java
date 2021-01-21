import java.util.*;

public class initial {
    public static void main(String[] args) {
      
      Scanner data = new Scanner(System.in);
      int[][] table = genTable(data);
      System.out.println(Arrays.deepToString(table));
      System.out.println("By row");
      for (int[] n : table) {
        System.out.println(Arrays.toString(n));
      }
      if (args.length>0) { 
        System.out.println(factorial(Integer.parseInt(args[0])));
      }
      System.out.println("\n");
      int n = 8;
      String[] paths = makePaths(5);
      //System.out.println(Arrays.toString(paths));
      System.out.println(paths.length);
      int[] lengths = allLengths(table);
      System.out.println(min(lengths));
    }
    
    /* How do you meticulously check each and every path?
    This is basically the lexicographic problem...
    0
    01 10
    012 021 102 120 201 210
    
    0123 0213 1023 1203 2013 2103
    3012 3021 3102 3120 3201 3210
    0312 0321 1302 1320 2301 2310
    0132 0231 1032 1230 2031 2130
    */
    public static int min(int[] nums) {
      int min = nums[0];
      for (int n : nums) {
        if (n<min) {
          min = n;
        }
      }
      return min;
    }
    public static int[] allLengths(int[][] data) {
      int[] lengths = new int[factorial(data.length)];
      String[] paths = makePaths(data.length-1);
      //System.out.println(Arrays.toString(paths));
      int index = 0;
      for (String path : paths) {
        int totalLength = 0;
        for (int i = 0; i<path.length()-1; i++) {
          int start = (int) (path.charAt(i)) - 48;
          int end = (int) (path.charAt(i+1)) - 48;
          // System.out.print(path + " ");
          // System.out.println(start + ", " + end);
          int pathLength = data[start][end];
          if (pathLength==0) {
            pathLength = data[end][start];
          }
          totalLength += pathLength;
        }
        lengths[index] = totalLength;
        index++;
      }
      return lengths;
    }
    
    public static String[] makePaths(int num) {
      String[] initial = new String[1];
      initial[0] = "0";
      int index = 1;
      while (index<=num) {
        initial = nextPath(index, initial);
        index++;
      }
      return initial;
    }
    public static String[] nextPath(int num, String[] prevPaths) {
      String[] paths = new String[factorial(num+1)];
      int displace = prevPaths.length;
      int i = 0;
      for (int pathIndex = 0; pathIndex < prevPaths.length; pathIndex++) {
        String path = prevPaths[pathIndex];
        for (int stringDex = 0; stringDex <= num; stringDex++) {
          String start = path.substring(0, stringDex);
          String end = path.substring(stringDex, num);
          paths[i] = start + num + end;
          i++;
        }
      }    
      return paths;
    }
    
    public static int factorial(int num) {
      int ret = 1;
      for (int i = 1; i<=num; i++) {
        ret*=i;
      }
      return ret;
    }
    
    public static int[][] genTable(Scanner data) {
      ArrayList<String> cities = new ArrayList<String>();
      int[][] tempTable = new int[2][10];
      int index = 0;
      int row = 0;
      while (data.hasNextLine()) {
        String newScan = data.nextLine();
        Scanner line = new Scanner(newScan);
        String city1 = line.next();
        if (!cities.contains(city1)) {
          cities.add(city1);
        }
        if (cities.contains(city1)) {
          line.next();
          String city2 = line.next();
          line.next();
          int num = line.nextInt();
          if (!cities.contains(city2)) {
            cities.add(city2);
            tempTable[row][index] = num;
            index++;
          } else {
            row++;
            tempTable[row][0] = num;
            break;
          }
        }
      }
      //System.out.println(cities.toString());

      int l = cities.size();

      int[][] table = new int[l][l];
      for (int i = 0; i<table[0].length-1; i++) {
        table[0][i+1] = tempTable[0][i];
      }
      table[1][2] = tempTable[1][0];
      while (data.hasNextLine()) {
        String lineStr = data.nextLine();
        Scanner line = new Scanner(lineStr);
        String startCity = line.next();
        line.next();
        String endCity = line.next();
        line.next();
        int num = line.nextInt();
        int startIndex = cities.indexOf(startCity);
        int endIndex = cities.indexOf(endCity);
        //System.out.print(startCity + ", " + endCity + ", ");
      
        //System.out.println(startIndex + ", " + endIndex);
        table[startIndex][endIndex] = num;
      }
      return table;
    }
}

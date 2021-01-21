import java.util.*;

public class Salesman {
    public static void main(String[] args) {
      
      Scanner data = new Scanner(System.in);
      int[][] table = genTable(data);
      System.out.println(Arrays.deepToString(table));
      System.out.println("By row");
      for (int[] n : table) {
        System.out.println(Arrays.toString(n));
      }
      
      // path generation
      int numPaths = 12000 ; //as the number increases, it is more likely to be correct
      int[][] pathsList = pathGeneration(numPaths,table[1].length) ;

      // remove for list of paths (you may want to decrease the number above)
      /*
      System.out.println("Path generation") ;
      // System.out.println(Arrays.deepToString(pathsList));
      System.out.println("By row") ;
      for (int[] n : pathsList) {
        System.out.println(Arrays.toString(n));
      }
      */
      System.out.println("Shortest path") ;
      System.out.println(pathLength(table,pathsList)) ;
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
      System.out.println(cities.toString());

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

    public static int[][] pathGeneration(int amount, int length) {
      ArrayList<Integer> nums = new ArrayList<Integer>() ;
      for(int i=0; i<length; i++) {
        nums.add(i) ;
      }
      int[][] paths = new int[amount][length] ;
      for(int i=0; i<amount; i++) {
        Collections.shuffle(nums) ;
        for(int j=0; j<length; j++) paths[i][j] = nums.get(j) ;
      }
      return paths ;
    } // generate the amount of paths with a specific length

    public static int pathLength(int[][] data, int paths[][]) {
      int returnValue = 0 ;
      for (int i=0; i<paths.length; i++) {
      int currentLength = 0 ;
        for (int j=0; j<paths[0].length-1; j++) {
          int currentPath = data[paths[i][j]][paths[i][j+1]] ;
          if (currentPath == 0) currentPath = data[paths[i][j+1]][paths[i][j]] ;
          currentLength += currentPath ;
        }
        if ((returnValue == 0) || (currentLength < returnValue)) returnValue = currentLength ;
      }
      return returnValue ;
    } // with the given data table and the list of paths, get the length of the shortest path in the list provided

}

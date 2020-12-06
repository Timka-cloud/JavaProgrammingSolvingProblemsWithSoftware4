package Week4;
import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;


public class BabyBirths {
    /**
     * print all info
     */
    public void printNames(){
        FileResource fr = new FileResource(); // false означает что нет заголовок и с первой строки начинаются данные
        for(CSVRecord record : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(record.get(2));
            if(numBorn <= 100) {
                System.out.println("Name " + record.get(0) + // цифры означает столбец какой
                        " Gender " + record.get(1) +
                        " Num Born " + record.get(2));
            }
        }
    }

    /**
     * Modify the method totalBirths (shown in the video for this project) to also print the number of girls names ,
     * the number of boys names and the total names in the file.
     */
    public void totalBirths () {
        // Allow user to choose file
        FileResource fr = new FileResource();
        // Set variables to initial values
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        // Create empty lists to hold unique girl and boy names
        StorageResource uniqueGirlNames = new StorageResource();
        StorageResource uniqueBoyNames = new StorageResource();
        // For every name
        for (CSVRecord rec : fr.getCSVParser(false)) {
            // Retrieve number of births for that name
            int numBorn = Integer.parseInt(rec.get(2));
            // Add number of births to total number of births
            totalBirths += numBorn;
            // Increment total count of names
            totalNames++;
            // Retrieve name
            String name = rec.get(0);
            // If gender is male
            if (rec.get(1).equals("M")) {
                // Add number of births to total number of births for males
                totalBoys += numBorn;
                // Add name to list of boy names if not already on list
                if (!uniqueBoyNames.contains(name)) {
                    uniqueBoyNames.add(name);
                }
            }
            // Otherwise (if gender is female)
            else {
                // Add number of births to total number of births for females
                totalGirls += numBorn;
                // Add name to list of girl names if not already on list
                if (!uniqueGirlNames.contains(name)) {
                    uniqueGirlNames.add(name);
                }
            }
        }
        System.out.println("Unique boy names: " + uniqueBoyNames.size());
        System.out.println("Unique girl names: " + uniqueGirlNames.size());
        System.out.println("Total names: " + totalNames);
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }

    /**
     *
     * Write the method named getRank that has three parameters: an integer named year, a string named name,
     * and a string named gender (F for female and M for male). This method returns the rank of the name
     * in the file for the given gender,  where rank 1 is the name with the largest number of births.
     * If the name is not in the file, then -1 is returned.  For example, in the file "yob2012short.csv",
     * given the name Mason, the year 2012 and the gender ‘M’, the number returned is 2, as Mason is the boys name
     * with the second highest number of births. Given the name Mason, the year 2012 and the gender ‘F’,
     * the number returned is -1 as Mason does not appear with an F in that file.
     */

    public int getRank(int year, String name, String gender){

        int rank = 1;
        int number = 0;
        int count = 0;
        for(CSVRecord record : getFileParser(year)) {
            if(record.get(1).equals(gender)){ // ищем по полу если пол совпадает а имя нет до добавляем ранк++
                if(record.get(0).equals(name)){ // если имя совпала вернем ранк
                    return rank;
                }
                rank++;
            }
        }


        return -1; // если не нашел то -1

    }

    /**
     * Write the method named getName that has three parameters: an integer named year,
     * an integer named rank, and a string named gender (F for female and M for male).
     * This method returns the name of the person in the file at this rank, for the given gender,
     * where rank 1 is the name with the largest number of births. If the rank does not exist in the file,
     * then “NO NAME”  is returned.

     */

    public String getName(int year, int rank, String gender){ // ищет имя по рангу
        int currentRank = 0;
        String name = "";
        for(CSVRecord record : getFileParser(year)){
            if(record.get(1).equals(gender)){
                if(currentRank == rank){
                    return name;
                }
                name = record.get(0);
                currentRank++;
            }
        }


        return "NO NAME";
    }

    /**
     * What would your name be if you were born in a different year? Write the void method named whatIsNameInYear
     * that has four parameters: a string name, an integer named year representing the year that name was born,
     * an integer named newYear and a string named gender (F for female and M for male). This method determines
     * what name would have been named if they were born in a different year, based on the same popularity.
     * That is, you should determine the rank of name in the year they were born, and then print the name born in newYear
     * that is at the same rank and same gender. For example, using the files "yob2012short.csv" and "yob2014short.csv",
     * notice that in 2012 Isabella is the third most popular girl's name. If Isabella was born in 2014 instead,
     * she would have been named Sophia, the third most popular girl's name that year. The output might look like this:
     * берет имя и ищет ранг его а затем в другом году ищет какое имя он бы получил с этим рангом
     *
     */
    public void whatIsNameInYear (String name, int year, int newYear, String gender){
        int rank = 1;
        FileResource fr = new FileResource("data\\us_babynames_by_year\\yob1974.csv");
        for(CSVRecord record : fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                if(record.get(0).equals(name)){
                    rank = rank;
                    break;

                }
                rank++;
            }
        }
        FileResource fr1 = new FileResource("data\\us_babynames_by_year\\yob2014.csv");
        int currentRank = 0;
        String newName = "";
        for(CSVRecord record1 : fr1.getCSVParser(false)){
            if(record1.get(1).equals(gender)){
                if(currentRank == rank){
                    System.out.println(name + " born in " +  year + " would be "  + newName +  " if she was born in " + newYear);
                    break;
                }
                newName = record1.get(0);
                currentRank++;
            }
        }

    }

    /**
     * Write the method yearOfHighestRank that has two parameters: a string name, and a string named gender
     * (F for female and M for male). This method selects a range of files to process and returns an integer,
     * the year with the highest rank for the name and gender. If the name and gender are not in any of the selected files,
     * it should return -1. For example, calling yearOfHighestRank with name Mason and gender ‘M’ and selecting the three
     * test files above results in returning the year 2012. That is because Mason was ranked the  2nd most popular name in 2012,
     * ranked 4th in 2013 and ranked 3rd in 2014. His highest ranking was in 2012.
     */

    public int yearOfHighestRank(String name, String gender){
        int year = 0;
        int rank = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){

            // Extract current year from file name
            int currentYear = Integer.parseInt(f.getName().substring(3,7));

            // Determine rank of name in current year
            int currentRank = getRank(currentYear, name, gender);
            System.out.println("Rank in year " + currentYear + ": " + currentRank);
            // If current rank isn't invalid
            if (currentRank != -1) {
                // If on first file or if current rank is higher than saved rank
                if (rank == 0 || currentRank < rank) {
                    // Update tracker variables
                    rank = currentRank;
                    year = currentYear;
                }
            }
        }

        if (year == 0) {
            return -1;
        }

        return year;


    }

    /**
     * это метод чтоб передавать год а он выведет файл нужный
     * @param year
     * @return
     */
    public CSVParser getFileParser(int year) {
         // If in testing, use below
        FileResource fr = new FileResource(String.format("yob%sshort.csv", year));
        return fr.getCSVParser(false);

//        // If in production, use below
//      FileResource fr = new FileResource(String.format("data\\us_babynames_by_year\\yob%s.csv", year));
//       return fr.getCSVParser(false);
    }

    /**
     * Write the method getAverageRank that has two parameters: a string name, and a string named gender
     * (F for female and M for male). This method selects a range of files to process and returns a double
     * representing the average rank of the name and gender over the selected files. It should return -1.0
     * if the name is not ranked in any of the selected files. For example calling getAverageRank with name Mason
     * and gender ‘M’ and selecting the three test files above results in returning 3.0, as he is rank 2 in the year 2012,
     * rank 4 in 2013 and rank 3 in 2014.  As another example, calling   getAverageRank with name Jacob and gender ‘M’
     * and selecting the three test files above results in returning 2.66.
     */

    public double getAverageRank(String name, String gender){ // получить средний значение ранга из выбранных файлов
        int sum = 0;
        double countFile = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            countFile++;
            int currentYear = Integer.parseInt(f.getName().substring(3,7));
            int currentRank = getRank(currentYear,name,gender);
            sum += currentRank;
        }
        return sum / countFile;
    }


    /**
     * Write the method getTotalBirthsRankedHigher that has three parameters: an integer named year, a string named name,
     * and a string named gender (F for female and M for male). This method returns an integer, the total number of births
     * of those names with the same gender and same year who are ranked higher than name. For example,
     * if getTotalBirthsRankedHigher accesses the "yob2012short.csv" file with name set to “Ethan”, gender set to “M”,
     * and year set to 2012, then this method should return 15, since Jacob has 8 births and Mason has 7 births,
     * and those are the only two ranked higher than Ethan.
     */
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        // Get number of births for given name and gender
        int numOfBirths = 0;
        for (CSVRecord rec : getFileParser(year)) {
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                numOfBirths = Integer.parseInt(rec.get(2));
            }
        }

        // Add up number of births greater than that for given name and gender
        int totalBirths = 0;
        for (CSVRecord rec : getFileParser(year)) {
            String currentGender = rec.get(1);
            // If name is not given name AND current gender matches param
            // AND current num of births is higher than for given name,
            if (!rec.get(0).equals(name) && currentGender.equals(gender) &&
                    Integer.parseInt(rec.get(2)) >= numOfBirths) {
                // Add number of births to total
                totalBirths += Integer.parseInt(rec.get(2));


            }

        }
        return totalBirths;
    }

    public static void main(String[] args) {
//        FileResource fr = new FileResource();
        BabyBirths bb = new BabyBirths();
//        int c = bb.getRank(2012, "Mason","M", fr);
////        System.out.println(c);
//        String name = bb.getName(2012, 3,"M",fr);
//        System.out.println(name);
//           int year =  bb.yearOfHighestRank( "Mason","M");
//        System.out.println("The most popular in " + year);
//        double average = bb.getAverageRank("Jacob", "M");
//        System.out.println(average);
//        int sumAbove = bb.getTotalBirthsRankedHigher(1900,"Noah","M");
 //       System.out.println(sumAbove);
        int a = bb.getTotalBirthsRankedHigher(2012,"Ethan","M");
        System.out.println(a);

    }
}

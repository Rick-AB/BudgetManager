import java.io.*;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Manager {
    Scanner scanner = new Scanner(System.in);
    StringBuilder builder;
    DecimalFormat df = new DecimalFormat("#.00");
    private double balance = 0;
    private final Map<String, Double> map = new TreeMap<>();
    private final Map<String, Double> foodMap = new TreeMap<>();
    private final Map<String, Double> clothesMap = new TreeMap<>();
    private final Map<String, Double> entertainMap = new TreeMap<>();
    private final Map<String, Double> othersMap = new TreeMap<>();

    public Manager() {

        showMenu();
    }
    private void showMenu() {
        char op;
        do {
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show the list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("7) Analyze (Sort)");
            System.out.println("0) Exit");

            op = scanner.nextLine().charAt(0);
            System.out.println();
            switch (op){
                case '1':
                    addIncome();
                    break;
                case '2':
                    chooseCategory();
                    break;
                case '3':
                    showList();
                    break;
                case '4':
                    getBalance();
                    break;
                case '5':
                    save();
                    break;
                case '6':
                    load();
                    break;
                case '7':
                    analyze();
                    break;
                case '0':
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        }while (true);

    }

    private void analyze() {
        char op;
        do {
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");

            Analyzer analyzer;
            op = scanner.nextLine().charAt(0);
            System.out.println();
            switch (op){
                case '1':
                    analyzer = new Analyzer(new SortAll());
                    printSortedResult(analyzer.sortType.sort(map), "All");
                    break;
                case '2':
                    sortByType();
                    break;
                case '3':
                    chooseType();
                    break;
                case '4':
                    showMenu();
                    break;
                default:
                    System.out.println("Invalid Option!");
            }
        }while (op != '4');


    }

    private void load() {
       // String path = System.getProperty("user.home") + File.separator + "Documents\\purchases.txt";

        File file = new File("purchases.txt");
        String regex = ".*?[$]";

        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                balance = Double.parseDouble(scanner.nextLine());
                if (scanner.nextLine().equals("food")) {
                    String line = scanner.nextLine();
                    String[] arr = line.split(",");
                    double val;
                    for (String item : arr){
                        String key = item.substring(0,item.lastIndexOf("$")+1);
                        String subString = item.replaceAll(regex,"");
                        val = Double.parseDouble(subString);
                        foodMap.put(key, Double.parseDouble(df.format(val)));
                    }

                }
                if (scanner.nextLine().equals("clothes")){
                    String line = scanner.nextLine();
                    String[] arr = line.split(",");
                    double val;
                    for (String item : arr){
                        String key = item.substring(0,item.lastIndexOf("$")+1);
                        String subString = item.replaceAll(regex,"");
                        val = Double.parseDouble(subString);
                        clothesMap.put(key, Double.parseDouble(df.format(val)));
                    }
                }
                if (scanner.nextLine().equals("entertainment")){
                    String line = scanner.nextLine();
                    String[] arr = line.split(",");
                    double val;
                    for (String item : arr){
                        String key = item.substring(0,item.lastIndexOf("$")+1);
                        String subString = item.replaceAll(regex,"");
                        val = Double.parseDouble(subString);
                        entertainMap.put(key, Double.parseDouble(df.format(val)));
                    }
                }
                if (scanner.nextLine().equals("other")){
                    String line = scanner.nextLine();
                    String[] arr = line.split(",");
                    double val;
                    for (String item : arr){
                        String key = item.substring(0,item.lastIndexOf("$")+1);
                        String subString = item.replaceAll(regex,"");
                        val = Double.parseDouble(subString);
                        othersMap.put(key, Double.parseDouble(df.format(val)));
                    }
                }
            }
            map.putAll(foodMap);
            map.putAll(clothesMap);
            map.putAll(entertainMap);
            map.putAll(othersMap);
            System.out.println("Purchases were loaded!");
            System.out.println();
        }catch (FileNotFoundException f){
            System.out.println("File not found: " + file.getName() + "\nPlease save after adding your purchases.");
            System.out.println();
        }

    }

    private void save() {

        //String path = System.getProperty("user.home") + File.separator + "Documents\\purchase.txt";
        File file = new File("purchases.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException o){
                o.getLocalizedMessage();
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(this.balance);
        if (!foodMap.isEmpty()){
            builder.append("\nfood\n");
            for (Map.Entry entry : foodMap.entrySet()){
                builder.append(entry.getKey()).append(df.format(entry.getValue())).append(",");
            }
        }
        if (!clothesMap.isEmpty()){
            builder.append("\nclothes\n");
            for (Map.Entry entry : clothesMap.entrySet()){
                builder.append(entry.getKey()).append(df.format(entry.getValue())).append(",");
            }
        }
        if (!entertainMap.isEmpty()){
            builder.append("\nentertainment\n");
            for (Map.Entry entry : entertainMap.entrySet()){
                builder.append(entry.getKey()).append(df.format(entry.getValue())).append(",");
            }
        }
        if (!othersMap.isEmpty()){
            builder.append("\nother\n");
            for (Map.Entry entry : othersMap.entrySet()){
                builder.append(entry.getKey()).append(df.format(entry.getValue())).append(",");
            }
        }

        try(FileWriter writer = new FileWriter(file)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(builder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("Purchases were saved!");
            System.out.println();
        }catch (FileNotFoundException f){
            System.out.println("File not found: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseCategory(){
        char op;
        do {
            System.out.println("Choose the type of purchase");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) Back");

            op = scanner.nextLine().charAt(0);
            System.out.println();
            switch (op){
                case '1':
                    addFood();
                    break;
                case '2':
                    addClothes();
                    break;
                case '3':
                    addEntertainment();
                    break;
                case '4':
                    addOther();
                    break;
                case '5':
                    showMenu();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }while (op != '5');
    }

    private void addOther() {
        double total = 0;
        builder = new StringBuilder();

        try {
            System.out.println("Enter purchase name:");
            String current = scanner.nextLine();
            builder.append(current).append(" $");
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());
            total += price;
            othersMap.put(builder.toString(), Double.parseDouble(df.format(price)));
            map.put(builder.toString(), price);
            System.out.println("Purchase was added!");
            System.out.println();

            updateBalance(total);
        }catch (NumberFormatException e){
            System.out.println("Invalid Format for price.");
        }

    }

    private void addEntertainment() {
        double total = 0;
        builder = new StringBuilder();

        try {
            System.out.println("Enter purchase name:");
            String current = scanner.nextLine();
            builder.append(current).append(" $");
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());
            total += price;
            entertainMap.put(builder.toString(), Double.parseDouble(df.format(price)));
            map.put(builder.toString(), price);
            System.out.println("Purchase was added!");
            System.out.println();

            updateBalance(total);
        }catch (NumberFormatException e){
            System.out.println("Invalid Format for price.");
        }

    }

    private void addClothes() {
        double total = 0;
        builder = new StringBuilder();

        try {
            System.out.println("Enter purchase name:");
            String current = scanner.nextLine();
            builder.append(current).append(" $");
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());
            total += price;
            clothesMap.put(builder.toString(), Double.parseDouble(df.format(price)));
            map.put(builder.toString(), price);
            System.out.println("Purchase was added!");
            System.out.println();

            updateBalance(total);
        }catch (NumberFormatException e){
            System.out.println("Invalid Format for price.");
        }

    }

    private void addFood() {
        double total = 0;
        builder = new StringBuilder();

        try {
            System.out.println("Enter purchase name:");
            String current = scanner.nextLine();
            builder.append(current).append(" $");
            System.out.println("Enter its price:");
            double price = Double.parseDouble(scanner.nextLine());
            total += price;
            foodMap.put(builder.toString(), Double.parseDouble(df.format(price)));
            map.put(builder.toString(), price);
            System.out.println("Purchase was added!");
            System.out.println();

            updateBalance(total);
        }catch (NumberFormatException e){
            System.out.println("Invalid Format for price.");
        }


    }

    private void getBalance() {
        System.out.printf("Balance: $%.2f\n" ,this.balance);
        System.out.println();
    }

    private void showList() {
        char op;
        do {
            System.out.println("Choose the type of purchases");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) All");
            System.out.println("6) Back");

            op = scanner.nextLine().charAt(0);
            System.out.println();
            switch (op){
                case '1':
                    showFoodList();
                    break;
                case '2':
                    showClothesList();
                    break;
                case '3':
                    showEntertainmentList();
                    break;
                case '4':
                    showOthersList();
                    break;
                case '5':
                    showAll();
                    break;
                case '6':
                    showMenu();
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }while (op != '6');
    }

    private void showAll() {
        System.out.println("All:");
        if (map.isEmpty()){
            System.out.println("Purchase list is empty");
        }else {
            for (Map.Entry entry : map.entrySet()){
                System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
            }
            double total = 0.00;
            for (double price : map.values()){
                total += price;
            }
            System.out.printf("Total sum: $%.2f\n" , total);
        }
        System.out.println();
    }

    private void showOthersList() {
        System.out.println("Other:");
        if (othersMap.isEmpty()){
            System.out.println("Purchase list is empty");
        }else {
            for (Map.Entry entry : othersMap.entrySet()){
                System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
            }
            double total = 0.00;
            for (double price : othersMap.values()){
                total += price;
            }
            System.out.printf("Total sum: $%.2f\n" , total);
        }
        System.out.println();
    }

    private void showEntertainmentList() {
        System.out.println("Entertainment:");
        if (entertainMap.isEmpty()){
            System.out.println("Purchase list is empty");
        }else {
            for (Map.Entry entry : entertainMap.entrySet()){
                System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
            }
            double total = 0.00;
            for (double price : entertainMap.values()){
                total += price;
            }
            System.out.printf("Total sum: $%.2f\n" , total);
        }
        System.out.println();
    }

    private void showClothesList() {
        System.out.println("Clothes:");
        if (clothesMap.isEmpty()){
            System.out.println("Purchase list is empty");
        }else {
            for (Map.Entry entry : clothesMap.entrySet()){
                System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
            }
            double total = 0.00;
            for (double price : clothesMap.values()){
                total += price;
            }
            System.out.printf("Total sum: $%.2f\n" , total);
        }
        System.out.println();
    }

    private void showFoodList() {
        System.out.println("Food:");
        if (foodMap.isEmpty()){
            System.out.println("Purchase list is empty");
        }else {
            for (Map.Entry entry : foodMap.entrySet()){
                System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
            }
            double total = 0.00;
            for (double price : foodMap.values()){
                total += price;
            }
            System.out.printf("Total sum: $%.2f\n" , total);
        }
        System.out.println();
    }

    private void addIncome() {
        System.out.println("Enter income:");
        try {
            this.balance += Double.parseDouble(scanner.nextLine());
            System.out.println("Income was added!");
            System.out.println();
        }catch (NumberFormatException e){
            e.getMessage();
        }


    }
    private void updateBalance(double val){
        this.balance -= val;
    }
    private void printSortedResult(Map<String, Double> map, String type){
        double total = 0;
        System.out.println(type + ":");
        if (map.isEmpty()){
            System.out.println("Purchase list is empty!");
        }
        for (Map.Entry entry : map.entrySet()){
            System.out.println(entry.getKey() + "" + df.format(entry.getValue()));
        }
        for (double price : map.values()){
            total += price;
        }
        if (type.equals("All")){
            System.out.println("Total: $" + total);
        }else {
            System.out.println("Total sum: $" + total);
        }

        System.out.println();
    }
    private void printSortedResult(Map<String, Double> map){
        double total = 0;
        System.out.println("Types:");
        if (map.isEmpty()){
            System.out.println("Purchase list is empty!");
        }
        for (Map.Entry entry : map.entrySet()){
            System.out.println(entry.getKey() + " - $" + df.format(entry.getValue()));
        }
        for (double price : map.values()){
            total += price;
        }
        System.out.println("Total sum: $" + total);
        System.out.println();
    }
    private void chooseType(){
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println();
        Analyzer analyzer;
        char op = scanner.nextLine().charAt(0);
        switch (op){
            case '1':
                analyzer = new Analyzer(new SortCertainType());
                printSortedResult(analyzer.sortType.sort(foodMap), "Food");
                break;
            case '2':
                analyzer = new Analyzer(new SortCertainType());
                printSortedResult(analyzer.sortType.sort(clothesMap), "Clothes");
                break;
            case '3':
                analyzer = new Analyzer(new SortCertainType());
                printSortedResult(analyzer.sortType.sort(entertainMap), "Entertainment");
                break;
            case '4':
                analyzer = new Analyzer(new SortCertainType());
                printSortedResult(analyzer.sortType.sort(othersMap), "Other");
                break;
            default:
                System.out.println("Invalid option!");

        }
    }
    private void sortByType(){
        double foodTotal = 0, clothesTotal = 0, entertainmentTotal = 0, otherTotal = 0;
        Map<String, Double> map = new TreeMap<>();

        for (Map.Entry entry : foodMap.entrySet()){
            foodTotal += Double.parseDouble(df.format(entry.getValue()));
        }
        for (Map.Entry entry : clothesMap.entrySet()){
            clothesTotal += Double.parseDouble(df.format(entry.getValue()));
        }
        for (Map.Entry entry : entertainMap.entrySet()){
            entertainmentTotal += Double.parseDouble(df.format(entry.getValue()));
        }
        for (Map.Entry entry : othersMap.entrySet()){
            otherTotal += Double.parseDouble(df.format(entry.getValue()));
        }

        map.put("Food", foodTotal);
        map.put("Clothes", clothesTotal);
        map.put("Entertainment", entertainmentTotal);
        map.put("Other", otherTotal);

        Analyzer analyzer = new Analyzer(new SortByType());
        printSortedResult(analyzer.sortType.sort(map));
    }
}


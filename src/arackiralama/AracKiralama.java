package arackiralama;

import java.util.Scanner;

public class AracKiralama {
    
    private static final int MAX_VEHICLES = 100;
    private static final int ATTRIBUTE_NUMBER = 6;
    
    static String[][] rentalHistory = new String[MAX_VEHICLES][3];
    static int historyCount = 0;
    
    static void vehicleList(String[][] matris , int qty){
        if (qty == 0){
            System.out.println("\n<!> Liste boþ, görüntülenecek araç bulunmamaktadýr!");
            return;
        }
        System.out.println("\n" + "=".repeat(88)); 
        System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n", "ID", "MARKA", "MODEL", "YAKIT", "GÜNLÜK", "DURUM");
        System.out.println("-".repeat(88));
        
        for (int i = 0; i < qty; i++){
            System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n",
                    matris[i][0], matris[i][1], matris[i][2], matris[i][3], matris[i][4] + "TL", matris[i][5]);
        }
        System.out.println("=".repeat(88));
    }
    
    static void availableVehicles(String[][] matris, int qty){
        if (qty == 0) {
            System.out.println("\n<!> Müsait araç bulunamadý!");
            return;
        }
        boolean found = false;
        for (int i = 0; i < qty; i++) {
            if (matris[i][5].equals("Müsait")) {
                if (!found) {
                    System.out.println("\n" + "=".repeat(88));
                    System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n", "ID", "MARKA", "MODEL", "YAKIT", "GÜNLÜK", "DURUM");
                    System.out.println("-".repeat(88));
                    found = true;
                }
                System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n", 
                        matris[i][0], matris[i][1], matris[i][2], matris[i][3], matris[i][4] + "TL", matris[i][5]);
            }
        }
        if (!found) {
            System.out.println("\n<!> Müsait araç bulunamadý!");
        } else {
            System.out.println("=".repeat(72));
        }
        
    }
    
    static void vehicleRent(String[][] matris, int qty, int vehicleId, Scanner input){
        for (int i = 0; i < qty; i++){
            if (Integer.parseInt(matris[i][0]) == vehicleId){
                if (matris[i][5].equals("Müsait")){
                    int day = -1;
                    while(day <= 0) {
                        System.out.print("Kaç gün kiralayacaksýnýz? :");
                        if(input.hasNextInt()) {
                            day = input.nextInt();
                            if(day <= 0) System.out.println("<!> En az 1 gün kiralamalýsýnýz.");
                        } else {
                            System.out.println("<!> Geçersiz gün sayýsý!");
                            input.next();
                        }
                    }
                    
                    int fee = Integer.parseInt(matris[i][4]);
                    int rawFee = day * fee;
                    int finalFee = rawFee;
                    
                    System.out.println("Hesaplanýyor...");
                    delay(400);
                    System.out.println("-------------------------------------");
                    
                    if (day >= 7){
                        int discountAmount = (int)(rawFee * 0.15);
                        finalFee = rawFee - discountAmount;
                        
                        System.out.println("<TEBRÝKLER> Uzun dönem indirimi kampanyamýzdan yararlanýyorsunuz!");
                        System.out.println("Normal Tutar: " + rawFee);
                        System.out.println("Ýndirim Tutarý: " + discountAmount);
                        System.out.println("-------------------------------------");
                    }
                    
                    System.out.println("Ödenecek toplam tutar: " + finalFee + "TL");
                    System.out.print("Onaylýyor musunuz? (E/H) :");
                    String approve = input.next();
                    
                    if (approve.equalsIgnoreCase("E")){
                         matris[i][5] = "Kirada";
                         
                         String carInfo = matris[i][1] + " " + matris[i][2];
                         
                         rentalHistory[historyCount][0] = carInfo;
                         rentalHistory[historyCount][1] = String.valueOf(day);
                         rentalHistory[historyCount][2] = String.valueOf(finalFee);
                         historyCount++;
                         
                        System.out.println("<OK> Kiralama iþlemi tamamlandý ve faturaya iþlendi!");
                    }else {
                        System.out.println("<!> Ýþlem iptal edildi!");
                    }
                }else {
                    System.out.println("<!> Araç zaten kirada!");
                }
                return;
            }
        }
        System.out.println("<!> Araç bulunamadý!");
    }
    
    static boolean removeVehicle(String[][] matris, int vehicleId, int qty){
        int index = -1;
        for (int i = 0; i < qty; i++){
            if (Integer.parseInt(matris[i][0]) == vehicleId){
                index = i;
                break;
            }
        }
        if (index != -1){
            for (int i = index; i < qty - 1; i++){
                matris[i] = matris[i + 1];
            }
            for (int i = 0; i < qty - 1; i++){
                matris[i][0] = String.valueOf(i + 1);
            }
            matris[qty - 1] = new String[ATTRIBUTE_NUMBER];
            System.out.println("Veriler siliniyor...");
            delay(1000);
            System.out.println("<-> Araç baþarýyla silindi!");
            return true;
        }else {
            System.out.println("<!> Araç bulunamadý!");
            return false;
        }
    }
    
    static void updateStatus(String[][] matris, int qty, int vehicleId, String newStatus){
        for (int i = 0; i < qty; i++){
            if (Integer.parseInt(matris[i][0]) == vehicleId){
                if (matris[i][5].equals(newStatus)){
                    System.out.println("<!> Araç zaten þu an " + "'" + newStatus + "'" + " durumunda!");
                    
                }else {
                    matris[i][5] = newStatus;
                    System.out.println("<OK> Durum güncellendi! Yeni durum: " + newStatus);
                }
                return;
            }
        }
        System.out.println("<!> Bu ID'ye sahip araç bulunamadý!");
    }
    
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    static void showHistory(){
        if (historyCount == 0){
            System.out.println("\n<!> Henüz yapýlmýþ bir kiralama iþlemi yok!");
            return;
        }
        System.out.println("\n--- KÝRALAMA GEÇMÝÞÝ VE CÝRO RAPORU ---");
        System.out.println("=".repeat(60));
        System.out.printf("| %-25s | %-10s | %-15s |\n", "ARAÇ BÝLGÝSÝ", "GÜN", "TUTAR");
        System.out.println("-".repeat(60));
        
        int totalTurnover = 0;
        
        for (int i = 0; i < historyCount; i++) {
            System.out.printf("| %-25s | %-10s | %-15s |\n", rentalHistory[i][0], rentalHistory[i][1] + " GÜN", rentalHistory[i][2] + " TL");
            
            totalTurnover += Integer.parseInt(rentalHistory[i][2]);
        }
        System.out.println("=".repeat(60));
        System.out.println(">>> TOPLAM KAZANÇ (CÝRO): " + totalTurnover + " TL");
    }
    
    static void filterByFuel(String[][] matris, int qty, Scanner input) {
        System.out.println("\n--- YAKIT TÜRÜNE GÖRE FÝLTRELE ---");
        System.out.println("1 - Dizel");
        System.out.println("2 - Benzin");
        System.out.println("3 - Elektrik");
        System.out.print("Seçiminiz: ");
        
        String searchFuel = "";
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine(); 
            switch (choice) {
                case 1 -> searchFuel = "Dizel";
                case 2 -> searchFuel = "Benzin";
                case 3 -> searchFuel = "Elektrik";
                default -> { System.out.println("<!> Geçersiz seçim!"); return; }
            }
        } else { input.next(); return; }

        boolean found = false;
        System.out.println("\n" + "=".repeat(88));
        System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n", "ID", "MARKA", "MODEL", "YAKIT", "GÜNLÜK", "DURUM");
        System.out.println("-".repeat(88));

        for (int i = 0; i < qty; i++) {
            // GÜNCELLENDÝ: Yakýt bilgisi 3. indekste olduðu için orayý kontrol ediyoruz
            if (matris[i][3].equalsIgnoreCase(searchFuel)) {
                System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-10s |\n", 
                        matris[i][0], matris[i][1], matris[i][2], matris[i][3], matris[i][4] + "TL", matris[i][5]);
                found = true;
            }
        }
        if (!found) System.out.println("\n<!> Bu yakýt türünde araç bulunamadý.");
        System.out.println("=".repeat(88));
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String[][] vehicles = new String[MAX_VEHICLES][ATTRIBUTE_NUMBER];
        
        vehicles[0] = new String[]{"1", "Tesla", "Y Model", "Elektrik", "3998", "Müsait"};
        vehicles[1] = new String[]{"2", "Renault", "Clio", "Benzin", "2380", "Müsait"};
        vehicles[2] = new String[]{"3", "Fiat", "Egea", "Dizel", "1505", "Müsait"};
        vehicles[3] = new String[]{"4", "Opel", "Corsa", "Benzin", "1480", "Müsait"};
        
        int numberOfVehicles = 4;
        int lastId = 5;

        System.out.println("Program baþlatýlýyor....");
        delay(1000);
        
        boolean run = true;
        while (run){
            System.out.println("\n========== ARAÇ KÝRALAMA OTOMASYONU ==========");
            System.out.println("1 - Araçlarý Listele");
            System.out.println("2 - Araç Kirala");
            System.out.println("3 - Araç Teslim Et");
            System.out.println("4 - Araç Ekle");
            System.out.println("5 - Araç Sil");
            System.out.println("6 - Gelir Tablosu");
            System.out.println("0 - ÇIKIÞ");
            System.out.println();
            System.out.print("Seçiminiz :");

            if (!input.hasNextInt()){
                System.out.println("<!> Lütfen Sadece Sayý Giriniz!");
                input.next();
                continue;
            }   

            int choose = input.nextInt();
            input.nextLine();

            switch (choose){
                case 1 -> {
                    delay(800);
                    System.out.println("1 - Tüm araçlarý görüntüle");
                    System.out.println("2 - Müsait araçlarý görüntüle");
                    System.out.println("3 - Yakýt türüne göre görüntüle");
                    System.out.print("Seçiminiz :");
                    if(input.hasNextInt()){
                        int c = input.nextInt(); input.nextLine();
                        switch (c) {
                            case 1 -> vehicleList(vehicles, numberOfVehicles);
                            case 2 -> availableVehicles(vehicles, numberOfVehicles);
                            case 3 -> filterByFuel(vehicles, numberOfVehicles, input);
                            default -> System.out.println("Geçersiz seçim.");
                        }
                    }
                }
                case 2 -> { 
                    int rentId = -1;
                    boolean isValid = false;

                    while (!isValid) {
                        System.out.print("Kiralamak istediðiniz aracýn ID'sini giriniz :");
                        if (input.hasNextInt()) {
                            rentId = input.nextInt();
                            isValid = true;
                        } else {
                            System.out.println("<!> Hata: Lütfen sadece sayýsal bir ID giriniz!");
                            input.next();
                        }
                    }
                    vehicleRent(vehicles, numberOfVehicles, rentId, input);
                }
                case 3 -> { 
                    System.out.print("Teslim edilecek aracýn ID'sini giriniz :");
                    int deliveryId = input.nextInt();
                    updateStatus(vehicles, numberOfVehicles, deliveryId, "Müsait");
                }
                case 4 -> { 
                    if (numberOfVehicles < MAX_VEHICLES) {
                    System.out.print("Marka :");
                    String brand = input.nextLine();
                    System.out.print("Model :");
                    String model = input.nextLine();
                    System.out.print("Yakýt Türü (Benzin/Dizel/Elektrik): ");
                    String fuel = input.nextLine();

                    String fee = ""; 
                    
                    boolean isCorrect = false;
                    
                    while (!isCorrect) {
                        System.out.print("Günlük ücret :");
                        fee = input.nextLine();
                        
                        if (fee.isEmpty()) {
                            System.out.println("<!> Ücret alaný boþ býrakýlamaz!");
                            continue;
                        }
                        
                        boolean allDigits = true;
                        for (int i = 0; i < fee.length(); i++) {
                            if (!Character.isDigit(fee.charAt(i))) {
                                allDigits = false;
                                break;
                            }
                        }

                        if (allDigits) {
                            isCorrect = true;
                        }else {
                            System.out.println("<!> Lütfen sadece rakam giriniz!");
                        }
                    }
                    
                        vehicles[numberOfVehicles][0] = String.valueOf(lastId);
                        vehicles[numberOfVehicles][1] = brand;
                        vehicles[numberOfVehicles][2] = model;
                        vehicles[numberOfVehicles][3] = fuel;
                        vehicles[numberOfVehicles][4] = fee;
                        vehicles[numberOfVehicles][5] = "Müsait";

                        numberOfVehicles++;
                        lastId++;
                        System.out.println("Kaydediliyor...");
                        delay(1000);
                        System.out.println("<+> Araç baþarýyla eklendi!");
                        } else {
                            System.out.println("<!> Kapasite dolu!");
                        }
                }
                case 5 -> { 
                    System.out.print("Silinecek araç ID'sini giriniz :");
                    int deleteId = input.nextInt();
                    if (removeVehicle(vehicles, deleteId, numberOfVehicles)){
                        numberOfVehicles--;
                    }
                    lastId = numberOfVehicles + 1;
                }
                case 6 -> showHistory();
                
                case 0 -> { 
                    System.out.println("Programdan çýkýlýyor...");
                    delay(1000);
                    System.out.println("Tekrar bekleriz.");
                    run = false;
                }
                default -> System.out.println("<!> Geçersiz seçim!");
            }
        }
    }
}

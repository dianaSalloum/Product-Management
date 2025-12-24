import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String []args) throws InterruptedException, AllException.InvalidQuantityException, AllException.ItemNotFoundException, AllException.InvalidNumberException, IOException, AllException.InvalidDateException {

                FactoryController controller = new FactoryController();

                // 1️⃣ إضافة مواد خام
                controller.addItem("Steel", "Raw", 10, 20, 5);
                controller.addItem("Plastic", "Raw", 5, 10, 3);
                System.out.println("Items added successfully");
                System.out.println(controller.showItems());
              // 2️⃣ تعريف منتج (سيارة مثلاً)
                Map<Item, Integer> carMap = new HashMap<>();
                try {
                    carMap.put(controller.findItem(1), 5); // Steel
                    carMap.put(controller.findItem(2), 2); // Plastic
                    System.out.println("new product added successfully!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                Product car = new Product("Car", carMap);


               // 3️⃣ إضافة خط إنتاج
               controller.addProductLine("Line-A");
                ProductLine line;
                try {
                    line = controller.findProductLine(1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }

                // 4️⃣ إضافة مهمة (كمية أكبر من المتوفر)
                controller.addMission(
                        car,
                        2, // ← عمداً كبيرة لحتى نشوف WAITING
                        "Client-X",
                        LocalDate.now(),
                        LocalDate.now().plusDays(3),
                        line
                );
                // 5️⃣ تشغيل خط الإنتاج
                controller.changeLineState(line, LineState.ACTIVE);
                Thread t = new Thread(line);
                t.start();

//                // 6️⃣ ننتظر شوي لنشوف النقص
//                Thread.sleep(5000);
//              System.out.println(line.getLastErrorMessage());
//               // 7️⃣ نزود المخزن
//                System.out.println("\n➕ Adding more materials...");
//                try {
//                    controller.editItemQuantity(1, 50); // Steel
//                    controller.editItemQuantity(2, 30); // Plastic
//                    System.out.println("updated the inventory");
//                } catch (Exception e) {
//
//                    System.out.println(e.getMessage());
//                }

                // 8️⃣ نرجّع المهمة تشتغل
                for (Mission m : line.getMissions()) {
                    m.setState(State.IN_PROGRESS);
                }
Thread th=Thread.currentThread();
                // 🔟 النتائج النهائية
                System.out.println("\n✅ Completed Missions:");
                  th.sleep(5000);
                System.out.println(line.getCompletedMissions());

                System.out.println("\n📦 Done Products:");
                System.out.println(controller.specificProductLineDoneProducts(line));
        System.out.println(controller.showAllAccomplishLevels());
        System.out.println("available");
        System.out.println(controller.availableItems());
        controller.removeItems(1);
        System.out.println("after removing");
        System.out.println(controller.showItems());
//        controller.editItem(1,20);
        System.out.println( controller.findItemByName("Steel"));
controller.inventoryToTxtFile("Inventory.Txt");
        System.out.println(controller.showProductLineMissions(line));
        LocalDate s=LocalDate.of(2025,6,5);
        LocalDate e=s.plusDays(3);

        System.out.println(controller.mostOrderedProduct(s,e));

                // ⛔ إيقاف الثريد
                controller.killThread(line);
                System.out.println("\n🛑 Thread Stopped");
            }

        }

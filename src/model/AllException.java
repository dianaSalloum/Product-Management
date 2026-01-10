package model;

public class AllException {

        //just a class for all the exception in our project
        //in Inventory,Item,ProductLine& FactoryController classes
        public static class InvalidQuantityException extends Exception {
            public InvalidQuantityException(String alert) {
                super(alert);
            }
        }

        //in Inventory & ProductLine classes
        public static class LowStockException extends Exception {
            public LowStockException(String alert) {
                super(alert);
            }
        }

        //in ProductLine & FactoryController classes
        public static class MissionNotFoundException extends Exception {
            public MissionNotFoundException(String alert) {
                super(alert);
            }
        }

        //in FactoryController class
        public static class InvalidDateException extends Exception {
            public InvalidDateException(String alert) {
                super(alert);
            }
        }
        //in FactoryController class
        public static class ItemNotFoundException extends Exception{
            public ItemNotFoundException(String alert){
                super(alert);
            }
        }
        //in Item & FactoryController classes
        public static class InvalidNumberException extends Exception{
            public InvalidNumberException(String alert){
                super(alert);
            }
        }
        //in FactoryController class
        public static class ProductLineNotFoundException extends Exception {
            public ProductLineNotFoundException(String alert){
                super(alert);
            }
        }
    }



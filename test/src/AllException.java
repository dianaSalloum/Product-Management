public class AllException {

// (addQuantity + QuantityConsumption) Exception in Item class
    public static class InvalidQuantityException extends Exception {
        public InvalidQuantityException(String alert){
            super (alert);
        }
    }

// (QuantityConsumption) Exception in Item class
    public static class LowStockException extends Exception{
        public LowStockException(String alert){
            super (alert);
        }
    }

//(removeMission) Exception in ProductLine class
    public static class NoMissionException extends Exception{
        public NoMissionException(String alert){
            super (alert);
        }
    }
//(checkDate) Exception in Mission class
    public static class InvalidDate extends Exception{
        public InvalidDate(String alert){
            super(alert);
        }
    }

}

